package com.example.administrator.helloandroid.pkg_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class TargetExamActivity extends ActionBarActivity {

    private TextView printTextView;
    private Button mRollbackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_exam);

        TextView printTextView = (TextView)findViewById(R.id.receiveText);

        //================================================================
        ////// 부모 액티비티 Intent에서 보내는 값 수신
        //================================================================
        final Intent myIentent = getIntent();
        if(myIentent != null){
            //getStringExtra 부모액티비에서 putExtra로 던진 String 값 받음.
            //getIntExtra 부모액티비에서 putExtra로 던진 int 값 받음.
            String targetDate = getIntent().getStringExtra("mData");
            if(TextUtils.isEmpty(targetDate) == false){
                Toast.makeText(getApplicationContext(), targetDate, Toast.LENGTH_SHORT).show();
                printTextView.setText(targetDate);
            }
        }

        //================================================================
        ////// 부모 액티비티 다시 띄우는 버튼
        //================================================================
        Button mRollbackBtn = (Button)findViewById(R.id.rollbckBtn);
        mRollbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIentent = new Intent(getApplicationContext(), ActivityExamActivity.class);
                newIentent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(newIentent);
            }
        });


        //================================================================
        // 철수의 점수를 요청하는 버튼 메소드
        //================================================================
        findViewById(R.id.returnJumsuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int codeNum = getIntent().getIntExtra("code", 0);
                String returnJumsu = "";

                if(codeNum == 0){
                    returnJumsu = "80점";
                }
                else {
                    returnJumsu = "90점";
                }

                Intent pIntent = new Intent();
                pIntent.putExtra("jumsu", returnJumsu);

                setResult(RESULT_OK, pIntent);
                finish();

            }
        });




    }


}
