package com.example.administrator.helloandroid.pkg_material;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class FloatingButton extends AppCompatActivity implements View.OnClickListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / FloatingButton ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private FloatingActionButton mBTN_FA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button);

        mBTN_FA = (FloatingActionButton) findViewById(R.id.fab);
        mBTN_FA.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        show_Toast("눌렀다.");
    }
}
