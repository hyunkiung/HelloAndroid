package com.example.administrator.helloandroid.pkg_material;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class CoordinatorLayout_Snackbar extends AppCompatActivity implements View.OnClickListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / FloatingButton ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private FloatingActionButton mBTN_FA;
    private View cdLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_snackbar);
        mBTN_FA = (FloatingActionButton) findViewById(R.id.fab);
        mBTN_FA.setOnClickListener(this);
        cdLayout = findViewById(R.id.fab_position);
        //final View coordinatorLayout = rootView.findViewById(R.id.fab_position); 프래그먼트에서 사용시


    }

    @Override
    public void onClick(View view) {
        Snackbar.make(cdLayout, "스낵바입니다", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_Toast("눌렀다.!!!");
                    }
                }).show();
    }
}
