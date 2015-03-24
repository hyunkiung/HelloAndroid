
package com.example.administrator.helloandroid.pkg_mission;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Mission_392Page extends ActionBarActivity  implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private TextView mTv_this_month;
    private GridView mGv_Calendar;
    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private ArrayList<Calendar> mArrayList;
    private Mission_392Page_adapter mAdapter;

    private HashMap<Calendar, String> Cal_HashMap;
    private EditText mEt_schedule;
    private TextView mTv_ClickDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_392_page);

        mGv_Calendar = (GridView) findViewById(R.id.gv_Calendar);
        mTv_this_month = (TextView) findViewById(R.id.tv_this_month);
        mEt_schedule = (EditText) findViewById(R.id.et_schedule);
        mTv_ClickDay = (TextView) findViewById(R.id.tv_ClickDay);

        // 현재 년월 데이터 호출
        create_thisYearMonth();

        // 생성된 년월로 달력 셋팅 호출
        create_Calendar(mYear, mMonth);

        // 버튼 이벤트 호출
        findViewById(R.id.btn_before_month).setOnClickListener(this);
        findViewById(R.id.btn_next_month).setOnClickListener(this);
        findViewById(R.id.btn_save_schedule).setOnClickListener(this);
        mGv_Calendar.setOnItemClickListener(this);

        Cal_HashMap = new HashMap<>();

    }

    // 현재 년월 데이터 셋팅
    public void create_thisYearMonth() {
        if (mCalendar == null) {
            mCalendar = GregorianCalendar.getInstance(); //그레고리수정
        }
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
    }

    // 생성된 년월로 달력 셋팅
    public void create_Calendar(int yy, int mm) {
        // 인스턴스 값으로 달력 셋팅
        mCalendar.set(Calendar.YEAR, yy);

        mCalendar.set(Calendar.MONTH, mm);

        // 셋팅한 달의 요일값 추출
        int firstDay_Week = mCalendar.get(Calendar.DAY_OF_WEEK);

        // 이번달 마지막날 숫자값 추출
        int LastDay_Num = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        mArrayList = new ArrayList<>();

        // 달력 앞부분 공백 배열 추가
        for (int i = 1; i < firstDay_Week - 1; i++) {
            mArrayList.add(null);
        }
        // 달력 배열을 그레고리 칼렌다로 추가.
        for (int i = 1; i <= LastDay_Num; i++) {
            mArrayList.add(new GregorianCalendar(yy, mm, i));
        }

        // 어댑터 셋팅
        mAdapter = new Mission_392Page_adapter(getApplicationContext(), mArrayList);

        // GridView에 어댑터 설정
        mGv_Calendar.setAdapter(mAdapter);

        // 선택달 년월 출력 TextView 변경
        mm = mm + 1;
        mTv_this_month.setText(yy + "년 " + mm + "월");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_before_month :
                // 로딩된 달력에서 월을 -1 해주고.
                mCalendar.add(Calendar.MONTH, -1);
                // 현재 년월을 다시 셋팅하고
                create_thisYearMonth();
                // 셋팅된 년월로 달력을 다시 만든다.
                create_Calendar(mYear, mMonth);
                break;

            case R.id.btn_next_month :
                // 로딩된 달력에서 월을 +1 해주고.
                mCalendar.add(Calendar.MONTH, 1);
                // 현재 년월을 다시 셋팅하고
                create_thisYearMonth();
                // 셋팅된 년월로 달력을 다시 만든다.
                create_Calendar(mYear, mMonth);
                break;

            case R.id.btn_save_schedule :
                // 칼렌다로 넘겼기 때문에 칼렌다로 받는다.
                Calendar CellCal = (Calendar) mAdapter.getItem(mAdapter.get_SelectedCell());
                Cal_HashMap.put(CellCal, mEt_schedule.getText().toString());
                break;
        }
    }

    // 셀 아이템 클릭시 발생 이벤트 메소드
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 달력 셀 클릭시 발생하는 이벤트 호출
        mAdapter.set_SelectedCell(position);

        Calendar CellData = (Calendar) mAdapter.getItem(position);
        int C_Y = CellData.get(Calendar.YEAR);
        int C_M = CellData.get(Calendar.MONTH) + 1;
        int C_D = CellData.get(Calendar.DATE);

        String C_YMD = String.valueOf(C_Y) + String.valueOf(C_M) + String.valueOf(C_D);
        mTv_ClickDay.setText(C_YMD);

        String val = Cal_HashMap.get(mAdapter.getItem(position));
        mEt_schedule.setText(val);
    }
}
