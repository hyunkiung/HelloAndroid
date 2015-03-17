package com.example.administrator.helloandroid.pkg_event;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class TouchEventActivity extends ActionBarActivity {

    TextView mTouchTest;
    Button mTouchTestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        mTouchTest = (TextView)findViewById(R.id.touchTest);
        mTouchTestBtn = (Button) findViewById(R.id.touchTestBtn);

        mTouchTestBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(Color.BLUE);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(Color.DKGRAY);
                }

                mTouchTest.setText(event.toString());
                return false;
            }
        });

        mTouchTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "클릭클릭", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //mTouchTest.setText(event.toString());
        return super.onTouchEvent(event);
    }





}
