package com.example.administrator.helloandroid.pkg_multimedia;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MediaPlayer_Audio_SDCARD extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<String> mArray_List;
    private ArrayAdapter<String> mArray_Adapter;

    private MediaPlayer mMP_Player;
    private Button mBTN_Play;
    private ListView mLV_mp3list;
    private SeekBar mSB_Progress;

    private int mIdx;
    private boolean wasPlaying;
    private String mSD_Path;
    private View oldView;
    private int beforePositoin = -1;

    final mSB_ProgressHandler mSB_ProgressHandler = new mSB_ProgressHandler(this);

    private static String TAG = "로그 / 오디오플레이어2 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }



    // mp3 필터 클래스
    class Mp3Filter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3")); // 확장자가 mp3인지 확인
        }
    }

    private String StrException[] = { "Preload", "mypeople" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player_audio_sdcard);

        // 배열 및 미디어플레이어 선언
        mArray_List = new ArrayList<>();
        mMP_Player = new MediaPlayer();


        // 01. SD 카드가 없을 경우 처리
        SDCard_Check();

        // 02. SD 카드의 전체 목록 구하기
        SDCard_Root_List();

        // 03. 재생 상태 만들기
        mIdx = 0;

        // 04. 위젯 선언 및 리스너 생성
        ObjectReady_Setting();

        // 어레이어뎁터 설정, 미리 선언한 array_ITEMS 를 던졌다.
        mArray_Adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.simple_list_item_hku, mArray_List);
        mLV_mp3list.setAdapter(mArray_Adapter);

        mLV_mp3list.setOnItemClickListener(this);


        // 05. 첫 곡 읽기 및 준비
//        if (!Player_setDataSource(mIdx)) {
//            Toast.makeText(this, "파일을 읽을 수 없습니다.", Toast.LENGTH_LONG).show();
//            finish();
//        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String List_FilePath = mArray_Adapter.getItem(position);

        String zzz = "position = " +  position + " / id = " + id + "List_FilePath = " + List_FilePath;
        //show_Log(zzz);
        //String vvv = "view = " + String.valueOf(adapterView);
        //show_Log(vvv);

        test(view, position);



        String blogUrl = (String) mLV_mp3list.getItemAtPosition(position);
        show_Log("blogUrl ;;; " + blogUrl);



        mMP_Player.reset();

        if (!Player_setDataSource(List_FilePath)) {
            Toast.makeText(this, "파일을 읽을 수 없습니다.", Toast.LENGTH_LONG).show();
            //finish();
        } else {

            if (mMP_Player.isPlaying()) {
                mMP_Player.pause();
                mBTN_Play.setText("Play");
            } else {
                mMP_Player.start();
                mBTN_Play.setText("Pause");
            }
        }
    }


    public void test(View view, int ClickPosition) {

        show_Log("beforePositoin : " + beforePositoin + " // ClickPosition : " + ClickPosition);

//        mLV_mp3list.setBackgroundColor(Color.TRANSPARENT);
//        mArray_Adapter.notifyDataSetChanged();
//
//        mLV_mp3list.getChildAt(ClickPosition).setBackgroundColor(Color.DKGRAY);


        if(beforePositoin != ClickPosition) {
            mLV_mp3list.getChildAt(ClickPosition).setBackgroundColor(Color.DKGRAY);
            if (beforePositoin >= 0) {
                mLV_mp3list.getChildAt(beforePositoin).setBackgroundColor(Color.TRANSPARENT);
            }
            beforePositoin = ClickPosition;

            show_Log("beforePositoin : " + beforePositoin + " // ClickPosition : " + ClickPosition);
        }

//        if (oldView != view) {
//            view.setBackgroundColor(Color.DKGRAY);
//            if (oldView != null) {
//                oldView.setBackgroundColor(Color.TRANSPARENT);
//            }
//            oldView = view;
//        }


    }


    //====================================================
    // 01. SD 카드가 없을 경우 처리
    //====================================================
    public void SDCard_Check() {
        String ext = Environment.getExternalStorageState();
        if (!ext.equals(Environment.MEDIA_MOUNTED)) {
            show_Toast("SD 카드가 필요합니다.");
            this.finish();
            return;
        } else {
            show_Toast("SD 카드가 감지되었습니다.");
        }
    }

    //====================================================
    // 02. SD 카드의 전체 목록 구하기
    //====================================================
    private void SDCard_Root_List() {
        // SD 카드 루트의 MP3 파일 목록을 구한다.
        mSD_Path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File SearchRoot = new File(mSD_Path);
        show_Log("SearchRoot : " + SearchRoot);
        // 지정 루트 폴더 하위폴더를 반복하여 검색
        File[] file_Root_List = SearchRoot.listFiles(); // sd카드 목록 다 가져오기
        for (int i = 0; i < file_Root_List.length; i++) {
            // 02-1. 제외 폴더 체크
            if(Folder_Exception(file_Root_List[i].getName()))
            {
                // 02-2. 폴더와 파일 구분 재귀메소드 호출
                FolderFile_Recursive_Extract_MP3(String.valueOf(file_Root_List[i]));
            }
        }
    }

    // 02-1. 제외 폴더 체크
    private boolean Folder_Exception(String FNM) {
        String YN = "N";
        for (int e = 0; e < StrException.length; e++) {
            if(StrException[e].equals(FNM)) {
                YN = "Y";
                break;
            }
        }

        if (YN.equals("N")) {
            return true;
        } else {
            return false;
        }
    }

    // 02-2. 폴더와 파일 구분 재귀메소드 호출
    private void FolderFile_Recursive_Extract_MP3(String flist) {
        String fullList;
        File FF = new File(flist);
        if(FF.isDirectory()) {
            String[] LL = FF.list();
            for(int i = 0; i < LL.length; i++) {
                fullList = flist + "/" + String.valueOf(LL[i]);
                FolderFile_Recursive_Extract_MP3(fullList);
                //show_Log("FolderFile_Check 폴더 : " + fullList + "@@");
            }
        } else {
            if (flist.endsWith(".mp3")) {
                //show_Log("FolderFile_Check 파일 : " + flist);
                mArray_List.add(flist);
            }
        }
    }

    //====================================================
    // 04. 위젯 선언 및 리스너 생성
    //====================================================
    private void ObjectReady_Setting() {
        // 버튼들의 클릭 리스너 등록
        mLV_mp3list = (ListView) findViewById(R.id.lv_mp3list);
        mBTN_Play = (Button) findViewById(R.id.btn_play);
        mBTN_Play.setOnClickListener(mClickPlay);
        findViewById(R.id.btn_stop).setOnClickListener(mClickStop);
        findViewById(R.id.btn_prev).setOnClickListener(mClickPrevNext);
        findViewById(R.id.btn_next).setOnClickListener(mClickPrevNext);

        // 완료 리스너, 시크바 변경 리스너 등록
        mMP_Player.setOnCompletionListener(mOnComplete);
        mMP_Player.setOnSeekCompleteListener(mOnSeekComplete);
        mSB_Progress = (SeekBar) findViewById(R.id.sb_seekBar);
        mSB_Progress.setOnSeekBarChangeListener(mOnSeek);
        mSB_ProgressHandler.sendEmptyMessageDelayed(0, 200);

    }

    //====================================================
    // 05. 첫 곡 읽기 및 준비 (항상 준비 상태여야 한다)
    //====================================================
    private boolean Player_setDataSource(String mp3Path) {
        try {
            //mMP_Player.setDataSource(mArray_List.get(idx));
            mMP_Player.setDataSource(mp3Path);
            show_Log("Player_setDataSource try--");
        } catch (IllegalArgumentException e) {
            show_Log("Player_setDataSource IllegalArgumentException--11");
            return false;
        } catch (IllegalStateException e) {
            show_Log("Player_setDataSource IllegalStateException--11");
            return false;
        } catch (IOException e) {
            show_Log("Player_setDataSource IOException--11");
            return false;
        }

        //show_Log("Player_setDataSource 첫번째 선곡 : " + mArray_List.get(idx));
        show_Log("Player_setDataSource 첫번째 선곡 : " + mp3Path);

        if (!Player_Prepare()) {
            show_Log("Player_setDataSource !Player_Prepare() 프리페어를 실패하네..11");
            return false;
        }

        //mTV_FileName.setText("파일 : " + mArray_List.get(idx));
        mSB_Progress.setMax(mMP_Player.getDuration());
        return true;
    }

    private boolean Player_Prepare() {
        try {
            mMP_Player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            show_Log("Player_Prepare() IOException--22");
            return false;
        }
        return true;
    }













    // 재생 및 일시 정지
    Button.OnClickListener mClickPlay = new View.OnClickListener() {
        public void onClick(View v) {
            if (mMP_Player.isPlaying() == false) {
                mMP_Player.start();
                mBTN_Play.setText("Pause");
            } else {
                mMP_Player.pause();
                mBTN_Play.setText("Play");
            }
        }
    };

    // 재생 정지. 재시작을 위해 미리 준비해 놓는다.
    Button.OnClickListener mClickStop = new View.OnClickListener() {
        public void onClick(View v) {
            mMP_Player.stop();
            mBTN_Play.setText("Play");
            mSB_Progress.setProgress(0);
            Player_Prepare();
        }
    };

    Button.OnClickListener mClickPrevNext = new View.OnClickListener() {
        public void onClick(View v) {
            boolean wasPlaying = mMP_Player.isPlaying();

            if (v.getId() == R.id.btn_prev) {
                mIdx = (mIdx == 0 ? mArray_List.size() - 1:mIdx - 1);
            } else {
                mIdx = (mIdx == mArray_List.size() - 1 ? 0:mIdx + 1);
            }

            mMP_Player.reset();
            //Player_setDataSource(mIdx);

            // 이전에 재생중이었으면 다음 곡 바로 재생
            if (wasPlaying) {
                mMP_Player.start();
                mBTN_Play.setText("Pause");
            }
        }
    };

    // 재생 완료되면 다음곡으로
    MediaPlayer.OnCompletionListener mOnComplete = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer arg0) {
            mIdx = (mIdx == mArray_List.size() - 1 ? 0:mIdx + 1);
            mMP_Player.reset();
            //Player_setDataSource(mIdx);
            mMP_Player.start();
        }
    };



    // 위치 이동 완료 처리
    MediaPlayer.OnSeekCompleteListener mOnSeekComplete = new MediaPlayer.OnSeekCompleteListener() {
        public void onSeekComplete(MediaPlayer mp) {
            if (wasPlaying) {
                mMP_Player.start();
            }
        }
    };









    //========================================================
    // 핸들러 처리를 위한 Handler 상속 클래스
    //========================================================
    static class mSB_ProgressHandler extends Handler {
        private final WeakReference<MediaPlayer_Audio_SDCARD> mActivity;
        mSB_ProgressHandler(MediaPlayer_Audio_SDCARD activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MediaPlayer_Audio_SDCARD activity = mActivity.get();
            if(activity != null) {
                activity.sub_HandleMessage(msg);
            }
        }
    }

    private void sub_HandleMessage(Message inputMessage) {
        //String msg = (String) inputMessage.obj;
        if (mMP_Player == null) return;
        if (mMP_Player.isPlaying()) {
            mSB_Progress.setProgress(mMP_Player.getCurrentPosition());
        }
        mSB_ProgressHandler.sendEmptyMessageDelayed(0, 200);
    }


    //========================================================
    // 프로그래스바 재생 위치 이동 리스너
    //========================================================
    SeekBar.OnSeekBarChangeListener mOnSeek = new SeekBar.OnSeekBarChangeListener() {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mMP_Player.seekTo(progress);
            }
        }

        // 프로그래스바를 움직이면 아래 메소드 실행후 onProgressChanged 가 실행된다.
        public void onStartTrackingTouch(SeekBar seekBar) {
            wasPlaying = mMP_Player.isPlaying();
            if (wasPlaying) {
                mMP_Player.pause();
            }
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };


    //========================================================
    // 액티비티 종료 처리
    //========================================================
    public void onDestroy() {
        super.onDestroy();
        if (mMP_Player != null) {
            mMP_Player.release();
            mMP_Player = null;
        }
    }

    // 에러 발생시 메시지 출력
    /*
    MediaPlayer.OnErrorListener mOnError = new MediaPlayer.OnErrorListener() {
        public boolean onError(MediaPlayer mp, int what, int extra) {
            String err = "OnError occured. what = " + what + " ,extra = " + extra;
            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            return false;
        }
    }; */
}
