package com.example.administrator.helloandroid.pkg_result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

public class exam1_Open1 extends AppCompatActivity implements View.OnClickListener {

    private Button mBTN_close;
    private TextView mTV_request;

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 결과값1 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam1_open1);

        mBTN_close  = (Button) findViewById(R.id.btn_close);
        mTV_request  = (TextView) findViewById(R.id.tv_request);

        final Intent gintent = getIntent();
        if(gintent != null){
            int cd_value = getIntent().getIntExtra("CD", 0);
            show_Log(String.valueOf(cd_value));

            if(cd_value == 0){
                mTV_request.setText("값을 못받았나봐요");
            } else {
                mTV_request.setText("부모에게서 받은 요청값은 : " + cd_value);
            }
        }


        mBTN_close.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent r_intent = new Intent();
        r_intent.putExtra("name", "OPEN1");
        setResult(RESULT_OK, r_intent);
        finish();
    }
}
