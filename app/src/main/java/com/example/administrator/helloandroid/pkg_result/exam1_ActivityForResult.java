package com.example.administrator.helloandroid.pkg_result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

public class exam1_ActivityForResult extends AppCompatActivity implements View.OnClickListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / GPS Exam1 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    private Button mBTN_open1;
    private Button mBTN_open2;
    private TextView mTV_result1;
    private TextView mTV_result2;
    public static final int REQUEST_CODE_ACTIVITY1 = 1000;
    public static final int REQUEST_CODE_ACTIVITY2 = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam1_activity_for_result);

        mBTN_open1  = (Button) findViewById(R.id.btn_open1);
        mBTN_open2  = (Button) findViewById(R.id.btn_open2);
        mTV_result1  = (TextView) findViewById(R.id.tv_result1);
        mTV_result2  = (TextView) findViewById(R.id.tv_result2);

        mBTN_open1.setOnClickListener(this);
        mBTN_open2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_open1 :
                OpenActivity(REQUEST_CODE_ACTIVITY1, exam1_Open1.class);
                break;
            case R.id.btn_open2 :
                OpenActivity(REQUEST_CODE_ACTIVITY2, exam1_Open2.class);
                break;
        }
    }

    // 액티비티 띄우기 메소드
    protected void OpenActivity(int code, Class className) {
        Intent intent = new Intent(getApplicationContext(), className);
        intent.putExtra("CD", code);
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            String name = data.getExtras().getString("name");
            String val = null;

            if(requestCode == REQUEST_CODE_ACTIVITY1) {
                val = "던진 코드값은 : " + REQUEST_CODE_ACTIVITY1 + "이고, 받은 name값은 : " + name + "이다.";
                mTV_result1.setText(val);
            } else {
                val = "던진 코드값은 : " + REQUEST_CODE_ACTIVITY2 + "이고, 받은 name값은 : " + name + "이다.";
                mTV_result2.setText(val);
            }

        }
    }
}
