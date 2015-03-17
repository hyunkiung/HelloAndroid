
package com.example.administrator.helloandroid.pkg_mission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class Mission_271Page_sub1 extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_271_page_sub1);

        //================================================================
        ////// 오브젝트 선언
        //================================================================
        TextView mSubAccount_ID = (TextView)findViewById(R.id.subAccount_ID);
        TextView mSubAccount_PW = (TextView)findViewById(R.id.subAccount_PW);
        Button mClose_Btn = (Button)findViewById(R.id.closeMenu);
        Button mMenu1Btn = (Button)findViewById(R.id.menu1Btn);
        Button mMenu2Btn = (Button)findViewById(R.id.menu2Btn);
        Button mMenu3Btn = (Button)findViewById(R.id.menu3Btn);
        Button mMenu4Btn = (Button)findViewById(R.id.menu4Btn);

        //================================================================
        ////// 부모 액티비티 Intent에서 보내는 값 수신
        //================================================================
        final Intent sub_Intent = getIntent();
        if(sub_Intent != null){
            String str_ID = sub_Intent.getStringExtra("mID");
            String str_PW = sub_Intent.getStringExtra("mPW");

            if(TextUtils.isEmpty(str_ID) == false){
                mSubAccount_ID.setText("입력하신 아이디는 " + str_ID);
            }

            if(TextUtils.isEmpty(str_PW) == false){
                mSubAccount_PW.setText("입력하신 패스워드는 " + str_PW);
            }
        }

        //================================================================
        ////// 액티비티 닫고 메인 액티비티 onActivityResult 메시지 전송
        //================================================================
        mClose_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent close_Intent = new Intent(getApplicationContext(), Mission_271Page.class);
                close_Intent.putExtra("pageName", "메뉴화면에서 메인 띄움");
                setResult(RESULT_OK, close_Intent);
                finish();
            }
        });


        //================================================================
        // 고객관리 메뉴별 다이얼로그 띄우기
        // setPositiveButton 긍정버튼
        // setNegativeButton 부정버튼
        // setNeutralButton 중립버튼
        //================================================================
        mMenu1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Mission_271Page_sub1.this)
                        .setTitle("고객 관리")
                        .setMessage("고객 관리 다이얼로그 입니다.")
                        .setPositiveButton("확인시 값전달", null)
                        .setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Mission_271Page_sub1.this, "계속 하세요~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelable(false) // Back 버튼 동작 제어
                        .show();

            }
        });


        //================================================================
        // 매출관리 메뉴별 다이얼로그 띄우기
        //================================================================
        mMenu2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn_TitleText = ((Button)v).getText().toString();
                AlertDialog.Builder dBuilder = new AlertDialog.Builder(Mission_271Page_sub1.this);
                dBuilder.setTitle(btn_TitleText);
                dBuilder.setMessage(btn_TitleText + " 다이얼로그 입니다.");

                dBuilder.setPositiveButton("확인시 값전달", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Mission_271Page_sub1.this, "값을 전달할께요.", Toast.LENGTH_SHORT).show();
                    }
                });

                dBuilder.setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Mission_271Page_sub1.this, "취소했어요.", Toast.LENGTH_SHORT).show();
                    }
                });

                dBuilder.setCancelable(false); // Back 버튼 동작 제어
                dBuilder.show();
            }
        });


        //================================================================
        // 상품관리 메뉴별 다이얼로그 띄우기
        //================================================================
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn_TitleText = ((Button) v).getText().toString();
                AlertDialog.Builder dBuilder = new AlertDialog.Builder(Mission_271Page_sub1.this);
                dBuilder.setTitle(btn_TitleText);
                dBuilder.setMessage(btn_TitleText + " 다이얼로그 입니다.");

                dBuilder.setPositiveButton("확인시 값전달", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Mission_271Page_sub1.this, "값을 전달할께요.", Toast.LENGTH_SHORT).show();
                    }
                });

                dBuilder.setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Mission_271Page_sub1.this, "취소했어요.", Toast.LENGTH_SHORT).show();
                    }
                });

                dBuilder.setCancelable(false); // Back 버튼 동작 제어
                dBuilder.show();
            }
        };

        mMenu3Btn.setOnClickListener(onClickListener);
        mMenu4Btn.setOnClickListener(onClickListener);

    }

}
