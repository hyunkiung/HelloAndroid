package com.example.administrator.helloandroid.pkg_db;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DB05_FileSave extends ActionBarActivity implements View.OnClickListener {

    private EditText mET_filename;
    private TextView mTV_Internal;
    private TextView mTV_External;
    private TextView mTV_path_Internal;
    private TextView mTV_path_External;
    private TextView mTV_path1;
    private TextView mTV_path2;

    private String FILE_NAME;
    private String FILE_PATH_EXTERNAL;
    private String FILE_CONTENT;

    // 메세지 토스트 메소드 (공용)
    void showToast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db05_file_save);

        mET_filename = (EditText) findViewById(R.id.et_filename);
        mTV_Internal = (TextView) findViewById(R.id.tv_Internal);
        mTV_External = (TextView) findViewById(R.id.tv_External);
        mTV_path_Internal = (TextView) findViewById(R.id.tv_path_Internal );
        mTV_path_External = (TextView) findViewById(R.id.tv_path_External);
        mTV_path1 = (TextView) findViewById(R.id.tv_path1);
        mTV_path2 = (TextView) findViewById(R.id.tv_path2);

        String path1 = Environment.getExternalStorageDirectory().toString();
        String path2 = Environment.getExternalStorageDirectory().getAbsoluteFile().toString();

        mTV_path1.setText("Environment.getExternalStorageDirectory() : \n  ==> " + path1);
        mTV_path2.setText("Environment.getExternalStorageDirectory().getAbsoluteFile() :  \n" +
                "  ==> " + path2);

        mTV_path_Internal.setText(getFilesDir().toString());
        mTV_path_External.setText(getExternalFilesDir(null).toString());

        // 이건 직접 경로를 설정해서 파일 생성할때 사용
        // FILE_PATH_EXTERNAL = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"TEST_TEXT_WRITE";
        // mTV_path_External.setText(FILE_PATH_EXTERNAL);

        findViewById(R.id.btn_Internal).setOnClickListener(this);
        findViewById(R.id.btn_External).setOnClickListener(this);
        findViewById(R.id.btn_filedel).setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Internal :
                CreateFile_Internal();
                break;
            case R.id.btn_External :
                CreateFile_External();
                break;
            case R.id.btn_filedel :
                DeleteFile_External();
                break;
        }
    }

    // 내부저장소 파일 저장 메소드
    // 저장 경로 ::::: /data/data/패키지명/files/
    private void CreateFile_Internal() {
        FILE_NAME = mET_filename.getText().toString();
        File file = new File(getFilesDir(), FILE_NAME);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file, "UTF-8");
            pw.println("1)파일 저장하는거 테스트중");
            pw.println("2)FileInputStream으로 파일을 가져와서");
            pw.println("3)InputStreamReader으로 읽어서");
            pw.println("4)BufferedReader에 담았다가");
            pw.println("5)readLine으로 한줄씩 읽는다.");
            pw.close();

            // 파일 내용 읽기 메소드 호출
            ReadFile_Internal(file.exists());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 내부저장소 파일 내용 읽기 메소드
    private void ReadFile_Internal(boolean fyn) {
        if(fyn) {
            try {
                FileInputStream fis = new FileInputStream(getFilesDir()+"/"+FILE_NAME);
                BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));

                FILE_CONTENT = "";
                String temp = "";

                try {
                    while((temp = bfr.readLine()) != null) {
                        temp += "\r\n";
                        FILE_CONTENT = FILE_CONTENT + temp;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mTV_Internal.setText(FILE_CONTENT);

        } else {
            showToast("파일이 없습니다.");
        }
    }


    // 외부저장소 파일 저장 메소드
    private void CreateFile_External() {
        // 외부 저장소가 존재하면..
        if(isExternalStorageWritable()) {
            FILE_NAME = mET_filename.getText().toString();
            File file = new File(getExternalFilesDir(null), FILE_NAME);

            try {
                if(!file.exists()) {
                    file.createNewFile();
                }
                // 외부 저장도 내부 저장처럼 PrintWriter 사용해도 되지만 append 를 위해 다른걸 써본다.
                // File getAbsoluteFile() : 파일의 절대 경로를 넘겨준다.
                // String getAbsolutePath() : 파일의 절대 경로를 문자열로 넘겨준다.
                // 인자중 두번째 true는 append 하겠다는것.
                //FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(), true);

                FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fileWritter);
                bw.write("데이터를 추가한다. 추가추가!");
                mTV_path_External.setText(file.getAbsolutePath());
                FILE_PATH_EXTERNAL = file.getAbsolutePath();
                bw.close();

                // 파일 내용 읽기 메소드 호출
                ReadFile_External(file.exists());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    // 외부저장소 파일 내용 읽기 메소드
    private void ReadFile_External(boolean fyn) {
        if(fyn) {
            try {
                Result_Log("FILE_PATH_EXTERNAL ==> " + FILE_PATH_EXTERNAL);
                FileInputStream fis = new FileInputStream(FILE_PATH_EXTERNAL);


                byte[] data = new byte[fis.available()];
                while(fis.read(data) != -1) {;}
                fis.close();

                mTV_External.setText(new String(data));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            showToast("파일이 없습니다.");
        }

    }

    // 파일 삭제 메소드
    private void DeleteFile_External() {
        FILE_NAME = mET_filename.getText().toString();
        File file_in = new File(getFilesDir(), FILE_NAME);
        if(file_in.exists()) {
            file_in.delete();
            showToast("1) 내부저장소에서 " + FILE_NAME + " 파일 삭제 완료!");
        } else {
           showToast("내부저장소에는 "+ FILE_NAME +" 파일이 없습니다.");
        }

        File file_ex = new File(getExternalFilesDir(null), FILE_NAME);
        if(file_ex.exists()) {
            file_ex.delete();
            showToast("2) 외부저장소에서 "+ FILE_NAME +" 파일 삭제 완료!");
        } else {
            showToast("외부저장소에는 "+ FILE_NAME +" 파일이 없습니다.");
        }
    }



    //============================================================
    // 외부 저장소 확인 메소드
    //============================================================
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }


}
