package com.example.administrator.helloandroid.pkg_mission;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.administrator.helloandroid.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Mission_332Page extends ActionBarActivity {

    int year, month, day;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_332_page);

        //================================================================
        ////// 달력 객체에서 년,월,일 생성
        //================================================================
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);

        //================================================================
        ////// 텍스트박스 클릭시 DataPickerDialog 띄우기
        //================================================================
        EditText mBirthText;
        mBirthText = (EditText)findViewById(R.id.text_Birth);
        mBirthText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(Mission_332Page.this, dSet, year, month, day).show();
                }
                return true;
            }
        });
    }

    //================================================================
    ////// DataPickerDialog 셋팅 - 스트링포맷 맞춘후 텍스트박스에 전달
    //================================================================
    private DatePickerDialog.OnDateSetListener dSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            update_Date_Display();
        }
    };

    private void update_Date_Display() {

        EditText birText = (EditText) findViewById(R.id.text_Birth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일");
        Time chosenDate = new Time(); // 20150416T000000Asia/Seoul(0,0,0,-1,1429110000
        chosenDate.set(mDay, mMonth, year);
        long dtDob = chosenDate.toMillis(true); // 밀리세컨드로 변환 / DB 저장하기 좋음
        String strDate = dateFormat.format(dtDob); // 포맷타입 설정

        birText.setText(strDate);
    }

}
