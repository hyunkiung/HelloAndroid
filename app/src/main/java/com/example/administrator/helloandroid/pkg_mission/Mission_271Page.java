package com.example.administrator.helloandroid.pkg_mission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;


public class Mission_271Page extends ActionBarActivity {

    private EditText mMainText_ID;
    private EditText mMainText_PW;
    private Button mMainBtn_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_271_page);

        //================================================================
        ////// UI 제어부분 // 텍스트박스 컨트롤후 메뉴 띄우기 호출
        //================================================================
        mMainText_ID = (EditText)findViewById(R.id.mainText_ID);
        mMainText_PW = (EditText)findViewById(R.id.mainText_PW);
        mMainBtn_Login = (Button)findViewById(R.id.mainBtn_Login);

        mMainBtn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_Text = mMainText_ID.getText().toString();
                String pw_Text = mMainText_PW.getText().toString();

                if(id_Text.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    mMainText_ID.setFocusable(true);
                }
                else if (pw_Text.equals("")) {
                    Toast.makeText(getApplicationContext(), "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    mMainText_PW.setFocusable(true);
                }
                else {
                    openMenu_Activity();
                }
            }
        });

    }

    //================================================================
    ////// 메뉴 액티비티 띄우기 // 입력받은 아이디 비밀번호 값 전송
    //================================================================
    public void openMenu_Activity(){
        Intent menu_Intent = new Intent(getApplicationContext(), Mission_271Page_sub1.class);
        menu_Intent.putExtra("mID", mMainText_ID.getText().toString());
        menu_Intent.putExtra("mPW", mMainText_PW.getText().toString());
        startActivityForResult(menu_Intent, 0);
        //finish();
    }

    //================================================================
    ////// 메뉴 액티비티 Intent에서 보내는 값 수신후 토스트 출력
    //================================================================
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), data.getStringExtra("pageName"), Toast.LENGTH_SHORT).show();
        }
    }


}
