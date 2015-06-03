package com.example.administrator.helloandroid.pkg_file;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Exam02_Main extends AppCompatActivity implements  AdapterView.OnItemClickListener {

    private String externalSdCard = null;
    private File fileCur = null;
    //private ArrayList<String> mArray;
    private String StrExternal[] = { "ext_card", "external_sd", "ext_sd"
          , "external", "extSdCard", "externalSdCard", "external_SD", "sdcard1" };

    private void findExtSd() {
        for( String sPathCur : Arrays.asList(StrExternal)) {
            fileCur = new File( "/mnt/", sPathCur);
            if( fileCur.isDirectory() && fileCur.canWrite()) {
                externalSdCard = fileCur.getAbsolutePath();
                break;
            }
        }

        if (externalSdCard == null) {
            fileCur = null;
            for( String sPathCur : Arrays.asList(StrExternal)) {
                fileCur = new File( "/storage/", sPathCur);
                if( fileCur.isDirectory() && fileCur.canWrite()) {
                    externalSdCard = fileCur.getAbsolutePath();
                    break;
                }
            }
        }
    }




    public static String sPathRoot = "/";
    public static String sPathSdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String sPathExtSdcard = null;
    public static String sPathMusic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    public static String sPathDCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    public static String sPathPicture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    public static String sPathDocument = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    public static String sPathDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();;

    private ListView mListView;

    // 첫 화면
    private ArrayList<Map<String, String>> mTitleList;
    private SimpleAdapter mAdapter;

    // 히스토리 관리 : 다음 화면으로 가기 전에 현재 mCurrentPath 를 push
    // mCurrentPath = 다음 화면 path
    private Stack<String> mFileStack;

    // 현재의 full path
    private String mCurrentPath = "";

    private TextView mTvCurrentPath;

    private static String TAG = "로그 / 파일 Exam2";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_exam02_main);

        mListView = (ListView) findViewById(R.id.lv_filetree);
        mTvCurrentPath = (TextView) findViewById(R.id.tv_currentPath);

        findExtSd();
        show_Log("findExtSd() 결과값" + externalSdCard);
        sPathExtSdcard = externalSdCard;

        mFileStack = new Stack<>();
        setUpHome();

    }

    /**
     * 첫 번째 화면 구성
     */
    private void setUpHome() {
        Map<String, String> root = new HashMap<>();
        root.put("title", "루트");
        root.put("path", sPathRoot);

        Map<String, String> sdcard = new HashMap<>();
        sdcard.put("title", "내부 저장소");
        sdcard.put("path", sPathSdcard);

        Map<String, String> extsdcard = new HashMap<>();
        extsdcard.put("title", "SD 카드");
        extsdcard.put("path", sPathExtSdcard);

        Map<String, String> music = new HashMap<>();
        music.put("title", "음악");
        music.put("path", sPathMusic);

        Map<String, String> dcim = new HashMap<>();
        dcim.put("title", "사진");
        dcim.put("path", sPathDCIM);

        Map<String, String> picture = new HashMap<>();
        picture.put("title", "그림");
        picture.put("path", sPathPicture);

        Map<String, String> document = new HashMap<>();
        document.put("title", "문서");
        document.put("path", sPathDocument);

        Map<String, String> download = new HashMap<>();
        download.put("title", "다운로드");
        download.put("path", sPathDownload);

        mTitleList = new ArrayList<>();
        mTitleList.add(root);
        mTitleList.add(sdcard);
        mTitleList.add(extsdcard);
        mTitleList.add(music);
        mTitleList.add(dcim);
        mTitleList.add(picture);
        mTitleList.add(document);
        mTitleList.add(download);

        mAdapter = new SimpleAdapter(getApplicationContext(),
                mTitleList,
                android.R.layout.simple_list_item_2,
                new String[] {
                        "title", "path"
                },
                new int[] {
                        android.R.id.text1, android.R.id.text2
                });

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        //show_Log("루트 : " + sPathSdcard);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item = mListView.getAdapter().getItem(position);

        if (item instanceof Map) {
            Map mapData = (Map) item;
            String path = (String) mapData.get("path");

            mFileStack.push("");
            setCurrentPath(path);
            showFileList(path);

        } else if (item instanceof File) {
            // 디렉토리를 클릭했을 때는 그 안으로 들어간다
            File fileData = (File) item;
            if (fileData.isDirectory()) {

                // 히스토리에 path 를 삽입
                mFileStack.push(mCurrentPath);
                setCurrentPath(fileData.getAbsolutePath());
                showFileList(fileData.getAbsolutePath());

            } else {
                try {
                    // 파일인 경우, 해당 파일의 MIME TYPE 을 설정하여 chooser를 호출
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(fileData), getMimeType(fileData.getAbsolutePath()));
                    startActivity(Intent.createChooser(intent, "파일선택..."));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "실행할 앱이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showFileList(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();

        if (files == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Exam02_Main.this);
            builder.setTitle("오류")
                    .setMessage("파일/폴더를 열 수 없습니다!")
                    .setPositiveButton("확인", null)
                    .show();
            mFileStack.pop();
            return;
        }

        List<File> fileList = new ArrayList<>();
        for (File f : files) {
            if (f != null) {
                fileList.add(f);
            }
        }

        Collections.sort(fileList);
        Collections.sort(fileList, mFolderAscComparator);

        Exam02_FileAdapter fileAdapter = new Exam02_FileAdapter(getApplicationContext(), fileList);
        mListView.setAdapter(fileAdapter);
    }

//    Comparator<File> mDescComparator = new Comparator<File>() {
//        @Override
//        public int compare(File lhs, File rhs) {
//            String left = lhs.getName();
//            String right = rhs.getName();
//
//            return right.compareTo(left);
//        }
//    };

    // left, right
    // file, file = return 0 : not change
    // file, directory = return 1 : change
    // directory, file = return -1 : change
    // directory, directory = return 0 : not change

    Comparator<File> mFolderAscComparator = new Comparator<File>() {
        @Override
        public int compare(File lhs, File rhs) {
            if (!lhs.isDirectory() && rhs.isDirectory()) {
                return 1;
            } else if (lhs.isDirectory() && !rhs.isDirectory()) {
                return -1;
            }
            return 0;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 스택이 비었으면 (뒤로 갈 게 없다) --> 죽인다
            if (!mFileStack.empty()) {
                // 스택이 안 비었으면, 뒤로 간다
                String prevPath = mFileStack.pop();
                setCurrentPath(prevPath);
                if (prevPath.equals("")) {
                    setUpHome();
                } else {
                    showFileList(prevPath);
                }
                return false;
            }

        }

        return super.onKeyDown(keyCode, event);
    }

    private void setCurrentPath(String path) {
        mCurrentPath = path;
        mTvCurrentPath.setText(mCurrentPath);
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension.toLowerCase());
        }
        return type;
    }
}
