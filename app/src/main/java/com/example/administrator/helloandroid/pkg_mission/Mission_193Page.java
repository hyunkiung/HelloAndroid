package com.example.administrator.helloandroid.pkg_mission;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;


public class Mission_193Page extends ActionBarActivity {

    EditText mInputText;
    TextView mCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_193_page);

        mInputText = (EditText) findViewById(R.id.id_editText);

        // EditText에 입력이 될 때 마다 호출 되는 인터페이스 메소드
        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.d(TAG, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.d(TAG, "afterTextChanged");
                countByte();
            }
        });
    }

    // 입력된 데이터의 바이트 계산후 TextView로 전송
    private void countByte() {
        mCountView = (TextView) findViewById(R.id.countView);
        String temp = mInputText.getText().toString();
        int tempLen = temp.getBytes().length;
        String tempLenS = String.valueOf(tempLen);
        mCountView.setText(tempLenS);
    }

    // 버튼 액션 입력데이터 토스트 메시지 전송
    public void onButtonTrans(View v) {
        mInputText = (EditText) findViewById(R.id.id_editText);
        Toast.makeText(getApplicationContext(), mInputText.getText(), Toast.LENGTH_SHORT).show();
    }

    // 버튼 액션 엑티비티 받고 메인 엑티비티 오픈
    public void onButtonClose(View v) {
        finish();
    }





}
