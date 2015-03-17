
package com.example.administrator.helloandroid.pkg_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;


public class FirstActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button buttonT = (Button)findViewById(R.id.thisBtnWhy);
        buttonT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClick_Need(v);
                ((Button) findViewById(R.id.thisBtnWhy)).setTextSize(15);
            }
        });
    }



    // 버튼1 onButtonClick_Naver 이벤트 메소드
    public void onButtonClick_Naver(View v) {
        Intent intentTest = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
        startActivity(intentTest);
    }

    // 버튼2 onButtonClick_TEL 이벤트 메소드
    public void onButtonClick_TEL(View v) {
//        Intent intentTest = new Intent(Intent.ACTION_GET_CONTENT, Uri.parse("audio/*"));
//        startActivity(intentTest);

//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.setType("audio/*");
//        startActivityForResult(i, 0);
    }

    // 버튼3 화면의 버튼 클릭시 발생하는 이벤트
    public void onButtonClick(View v) {
        Toast.makeText(getApplicationContext(), "클릭이벤트로 버튼클릭했어요", Toast.LENGTH_SHORT).show();
    }

    // 버튼4 thisBtnWhy ID로 onCreate에서 생성한 버튼 객체를 이용하여 실행하는 메소드
    public void onButtonClick_Need(View v) {
        Toast tt = Toast.makeText(getApplicationContext(), "id로 버튼클릭했어요", Toast.LENGTH_SHORT);
        tt.setGravity(Gravity.CENTER, 0, 200);
        // 상수값, x, y
        tt.show();
    }

    // 버튼5 메시지 재정의
    public void onButtonClick_Text(View v) {
        Toast ttText = Toast.makeText(getApplicationContext(), "id로 버튼클릭했어요", Toast.LENGTH_SHORT);
        ttText.setGravity(Gravity.CENTER, 0, 0);
        // 상수값, x, y
        ttText.getHorizontalMargin();
        ttText.setText("버튼 토스트 메시지 재정의 합니다!!");
        ttText.show();
    }
}
