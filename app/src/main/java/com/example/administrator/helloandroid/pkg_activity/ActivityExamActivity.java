
package com.example.administrator.helloandroid.pkg_activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

public class ActivityExamActivity extends ActionBarActivity {

    private static final int REQUEST_CODE_CHULSU = 0;
    private static final int REQUEST_CODE_SOONE = 1;
    private static String TAG = ActivityExamActivity.class.getSimpleName();

    private Button mMoveBtn;
    private Button mDataBtn;
    private EditText mEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_exam);

        mMoveBtn = (Button)findViewById(R.id.moveBtn);
        mDataBtn = (Button)findViewById(R.id.moveDataBtn);
        mEditText = (EditText)findViewById(R.id.dataEditText);

        //================================================================
        // 새로운 액티비티 오픈
        //================================================================
        mMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), TargetExamActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mIntent);
            }
        });

        //================================================================
        // 새로운 액티비티에 putExtra 데이터 던지는 메소드
        //================================================================
        mDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), TargetExamActivity.class);
                String tData = mEditText.getText().toString();
                mIntent.putExtra("mData", tData);
                startActivity(mIntent);
            }
        });

        //================================================================
        // 철수의 점수를 요청하는 버튼 메소드
        //================================================================
        findViewById(R.id.resultBtn_Chulsu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chulsu_Intent = new Intent(getApplicationContext(), TargetExamActivity.class);
                chulsu_Intent.putExtra("code", REQUEST_CODE_CHULSU);
                startActivityForResult(chulsu_Intent, REQUEST_CODE_CHULSU);
            }
        });

        //================================================================
        // 순이의 점수를 요청하는 버튼 메소드
        //================================================================
        findViewById(R.id.resultBtn_Soone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent soone_Intent = new Intent(getApplicationContext(), TargetExamActivity.class);
                soone_Intent.putExtra("code", REQUEST_CODE_SOONE);
                startActivityForResult(soone_Intent, REQUEST_CODE_SOONE);
            }
        });

        //================================================================
        // 다이얼로그 띄우기 연습
        //================================================================
        Button mDiallogBtn = (Button)findViewById(R.id.diallogBtn);
        mDiallogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogN = new AlertDialog.Builder(ActivityExamActivity.this);
                dialogN.setMessage("이것은 다이얼로그");
                dialogN.setTitle("타이틀");
                dialogN.setPositiveButton("확인", null);
                dialogN.setNegativeButton("취소", null);
                dialogN.setNeutralButton("무슨버튼", null);
                dialogN.create();
                dialogN.show();
            }
        });
        //================================================================
        Log.d(TAG, "Message_onCreate");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView resultTextView = (TextView)findViewById(R.id.resultView_Jumsu);

        if(requestCode == REQUEST_CODE_CHULSU && resultCode == RESULT_OK) {
            resultTextView.setText(data.getStringExtra("jumsu"));
            //Toast.makeText(getApplicationContext(), data.getStringExtra("data"), Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == REQUEST_CODE_SOONE && resultCode == RESULT_OK) {
                resultTextView.setText(data.getStringExtra("jumsu"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Message_onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Message_onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Message_onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Message_onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Message_onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Message_onDestroy");
    }


}
