
package com.example.administrator.helloandroid.pkg_adapter;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class GridView_Calendar extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //===============================================================
    ////// 뷰 액티비티와 인포클래스, 커스텀어뎁터 사용 // 만들면서도 헤깔림!
    ////// 그리드뷰안에 텍스트뷰를 넣고 그 텍스트뷰를 디자인하기 위해서는
    ////// getView가 필요하고, 그거 하나 사용하기 위해서 이랬다는..ㅜㅜ
    //===============================================================
    // GridView_Calendar ↔ GridView_DayInfo ↔ GridView_Adapter
    //===============================================================
    ////// 기본 선언
    //===============================================================
    private ActionBar action_Bar;   // 액션바
    private GridView mGrid_View;    // 달력 그리드뷰
    private TextView mText_YM;      // 현재 년월 표기 텍스트뷰

    private ArrayList<GridView_DayInfo> mGrid_DataList; // 달력 데이터 배열
    private GridView_Adapter mGrid_ArrayAdapter;        // 그리드뷰 어뎁터

    private Calendar Now_Calendar;                      // 현재달 달력
    private Calendar Before_Calendar;                   // 이전달 달력
    private Calendar Next_Calendar;                     // 다음달 달력

    private int now_Year, now_Month, now_Day, now_DayMax;   // 현재 년,월,일
    private int before_Year, before_Month, before_DayMax;   // 이전 년,월,일
    private int next_Year, next_Month;                      // 다음 년,월,일

    private HashMap<String, String> mmMap;
    private TextView mSeld_Text_Num;
    private EditText mDay_Content_et;

    private Button mBtn_before_M;
    private Button mBtn_next_M;
    private Button mDay_Content_Save;
    //===============================================================
    ////// 달력 헤더 배열 값 셋팅 (요일)
    //===============================================================
    public static final String[] array_WEEK = { "일", "월", "화", "수", "목", "금", "토" };

    //===============================================================
    ////// 메세지 토스트 메소드 (공용)
    //===============================================================
    void showToast(CharSequence toast_msg) {
        Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_calendar);

        mmMap = new HashMap<>();

        // 1. 액션바 선언 및 타이틀 설정 ================================
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("안드로이드 실습3 그리드뷰로 달력만들기!");

        // 2. 달력 (오늘 셋팅) =========================================
        Now_Calendar = Calendar.getInstance();
        now_Year = Now_Calendar.get(Calendar.YEAR);
        now_Month = Now_Calendar.get(Calendar.MONTH);
        now_Day = Now_Calendar.get(Calendar.DAY_OF_MONTH);
        Now_Calendar.set(now_Year, now_Month, now_Day);

        // 3. 뷰 설정 ============================================
        mText_YM = (TextView) findViewById(R.id.text_YM);
        mSeld_Text_Num = (TextView)findViewById(R.id.seld_Text_Num);
        mDay_Content_et = (EditText)findViewById(R.id.day_Content_et);
        mBtn_before_M = (Button) findViewById(R.id.btn_before_M);
        mBtn_next_M = (Button) findViewById(R.id.btn_next_M);
        mDay_Content_Save = (Button) findViewById(R.id.day_Content_Save);

        // 4. 달력 스타트 (첫로딩) =================================
        Before_Calendar = getBefore_Calendar(Now_Calendar);
        Next_Calendar = getNext_Calendar(Now_Calendar);
        CalCreate_AdapterSet(Before_Calendar, Now_Calendar, Next_Calendar);

        // 이전달 버튼 클릭 리스너 (3개의 달력을 모두 -1 개월)
        mBtn_before_M.setOnClickListener(this);

        // 다음달 버튼 클릭 리스너 (3개의 달력을 모두 +1 개월)
        mBtn_next_M.setOnClickListener(this);

        // 일자별 일정 저장 버튼 리스너
        mDay_Content_Save.setOnClickListener(this);

    }

    //===============================================================
    ////// 클릭 이벤트
    //===============================================================
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_before_M:
                Before_Calendar = getBefore_Calendar(Before_Calendar);
                Now_Calendar = getBefore_Calendar(Now_Calendar);
                Next_Calendar = getBefore_Calendar(Next_Calendar);
                CalCreate_AdapterSet(Before_Calendar, Now_Calendar, Next_Calendar);
                break;

            case R.id.btn_next_M:
                Before_Calendar = getNext_Calendar(Before_Calendar);
                Now_Calendar = getNext_Calendar(Now_Calendar);
                Next_Calendar = getNext_Calendar(Next_Calendar);
                CalCreate_AdapterSet(Before_Calendar, Now_Calendar, Next_Calendar);
                break;

            case R.id.day_Content_Save:
                String textM_Key = mSeld_Text_Num.getText().toString();
                String textM_Val = mDay_Content_et.getText().toString();
                mmMap.put(textM_Key, textM_Val);
                break;
        }
    }

    //===============================================================
    ////// 아이템 클릭 이벤트 메소드
    //===============================================================
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 1.어레이어댑터의 셀렉트 포지션 호출
        mGrid_ArrayAdapter.setSelectedPosition(position, id);

        // 2.어뎁터의 풀일자 받아오기
        GridView_DayInfo FDD = mGrid_ArrayAdapter.getItem(position);
        String ftext = FDD.getFullDay();

        // 3. 텍스트뷰에 포지션의 값주기
        mSeld_Text_Num.setText(ftext);

        // 4. 포지션값에 있는 해쉬맵 데이터 가져오기
        String hashMap_data = mmMap.get(ftext);

        // 5. 에디트박스에 해쉬맵 데이터 출력
        mDay_Content_et.setText(hashMap_data);
    }


    //===============================================================
    ////// 달력 생성 어뎁터 셋팅 실행
    //===============================================================
    public void CalCreate_AdapterSet(Calendar BeforeCalendar, Calendar NowCalendar, Calendar NextCalendar) {

        // 1. 현재달 달력 정보 생성 =================================
        now_Year = NowCalendar.get(Calendar.YEAR);
        now_Month = NowCalendar.get(Calendar.MONTH) + 1;
        String strNow_YM = String.valueOf(now_Year) + "년 " + String.valueOf(now_Month) + "월";
        mText_YM.setText(strNow_YM);
        now_DayMax = NowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now_DayWeek = getMonth_LastDayWeek(now_Year, now_Month, now_DayMax);

        // 2. 이전달 달력 정보 생성 =================================
        before_Year = BeforeCalendar.get(Calendar.YEAR);
        before_Month = BeforeCalendar.get(Calendar.MONTH) + 1;
        String strBefore_YM = String.valueOf(before_Year) + "년 " + String.valueOf(before_Month) + "월";
        mBtn_before_M.setText(strBefore_YM);
        before_DayMax = BeforeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int last_DayWeek = getMonth_LastDayWeek(before_Year, before_Month, before_DayMax);

        // 3. 다음달 달력 정보 생성 =================================
        next_Year = NextCalendar.get(Calendar.YEAR);
        next_Month = NextCalendar.get(Calendar.MONTH) + 1;
        String strNext_YM = String.valueOf(next_Year) + "년 " + String.valueOf(next_Month) + "월";
        mBtn_next_M.setText(strNext_YM);

        // 4. 데이터 배열 생성 ======================================
        mGrid_DataList = new ArrayList<>();

        // 5. 일요일~토요일까지 헤더 배열 추가 ===============================
        for(int w = 0; w < 7; w++){
            GridView_DayInfo week_Header = new GridView_DayInfo();
            week_Header.setWeek(array_WEEK[w]);
            mGrid_DataList.add(week_Header);
        }

        // 6. 달력 앞 빈칸을 지난달 마지막 날짜부터 앞으로 채우기 ========
        if (last_DayWeek < 7) {
            // 반복 횟수 시작점은 지난달 마지막 숫자에서 요일 리턴값 (마지막주 1번 요일값과 같음)
            // +1은 0번째부터 채우기 때문.
            int last_Month_sday = before_DayMax - last_DayWeek + 1 ;
            for (int dd = last_Month_sday; dd <= before_DayMax; dd++) {
                GridView_DayInfo add_LastDay = new GridView_DayInfo();
                add_LastDay.setLastDay(Integer.toString(dd));
                mGrid_DataList.add(add_LastDay);
            }
        }

        // 7. 선택한 월의 맥스 일수 만큼 배열 데이터 생성 ===============
        for (int ii = 1; ii <= now_DayMax; ii++) {
            GridView_DayInfo dayInfo = new GridView_DayInfo();
            dayInfo.setDay(Integer.toString(ii));

            // ★★★ 날짜 풀타입 형태 저장시키기 ★★★
            String FD = String.valueOf(now_Year) + "년" + String.valueOf(now_Month) + "월" + String.valueOf(ii) + "일";
            dayInfo.setFullDay(FD);
            mGrid_DataList.add(dayInfo);
        }

        // 8. 달력 끝 빈칸을 다음달 시작일부터 남은셀 끝까지 채우기 =======
        if (now_DayWeek < 7) {
            int next_Month_eday = 7 - now_DayWeek ;
            for (int nn = 1; nn <= next_Month_eday; nn++) {
                GridView_DayInfo add_NextDay;
                add_NextDay = new GridView_DayInfo();
                add_NextDay.setNextDay(Integer.toString(nn));
                mGrid_DataList.add(add_NextDay);
            }
        }

        // 7. 어뎁터에 배열 데이터를 셋팅 ==============================
        mGrid_ArrayAdapter = new GridView_Adapter(getApplicationContext(), R.layout.activity_grid_view_calendar_cell, mGrid_DataList );

        // 8. 그리드뷰에 어뎁터를 셋팅하여 출력 =========================
        mGrid_View = (GridView) findViewById(R.id.grid_View);
        mGrid_View.setAdapter(mGrid_ArrayAdapter);

        // 그리드뷰의 아이템 클릭 리스너 호출
        mGrid_View.setOnItemClickListener(this);

    }

    // 이전달 달력 생성
    private Calendar getBefore_Calendar(Calendar calendar) {
        Calendar B_CAL = Calendar.getInstance();
        B_CAL.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        B_CAL.add(Calendar.MONTH, -1);
        B_CAL.set(B_CAL.get(Calendar.YEAR), B_CAL.get(Calendar.MONTH), 1);
        return B_CAL;
    }

    // 다음달 달력 생성
    private Calendar getNext_Calendar(Calendar calendar) {
        Calendar NX_CAL = Calendar.getInstance();
        NX_CAL.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        NX_CAL.add(Calendar.MONTH, + 1);
        NX_CAL.set(NX_CAL.get(Calendar.YEAR), NX_CAL.get(Calendar.MONTH), 1);
        return NX_CAL;
    }

    // 특정일 요일 구하기
    private int getMonth_LastDayWeek(int yy, int mm, int dd) {
        Calendar LDW_CAL = Calendar.getInstance();
        LDW_CAL.set(yy, mm-1, dd);
        int result_w = LDW_CAL.get(Calendar.DAY_OF_WEEK);
        return result_w;
    }


}

