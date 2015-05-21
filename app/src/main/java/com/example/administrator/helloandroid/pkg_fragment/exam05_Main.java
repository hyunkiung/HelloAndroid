package com.example.administrator.helloandroid.pkg_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.helloandroid.R;

public class exam05_Main extends AppCompatActivity implements View.OnClickListener {

    private Button mBTN_PagerTab;
    private Button mBTN_PagerTitle;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam05_main);

        mBTN_PagerTab = (Button) findViewById(R.id.btn_PagerTab);
        mBTN_PagerTitle = (Button) findViewById(R.id.btn_PagerTitle);

        mBTN_PagerTab.setOnClickListener(this);
        mBTN_PagerTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_PagerTab :
                intent = new Intent(getApplicationContext(), exam05_PagerTab.class);
                startActivity(intent);
                break;
            case R.id.btn_PagerTitle :
                intent = new Intent(getApplicationContext(), exam05_PagerTitle.class);
                startActivity(intent);
                break;
        }
    }
}
