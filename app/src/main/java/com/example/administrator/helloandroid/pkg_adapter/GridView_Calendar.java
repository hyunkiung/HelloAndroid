
package com.example.administrator.helloandroid.pkg_adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
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
    private TextView mText_YM;      // 현재 년월 표기 텍스트뷰

    private GridView mGrid_Header;                      // 헤더 뷰
    private ArrayList<GridView_DayInfo> mArray_Header;  // 헤더 배열
    private GridView_Adapter mAdapter_Header;           // 헤더 어뎁터

    private GridView mGrid_Calendar;                        // 달력 그리드뷰
    private ArrayList<GridView_DayInfo> mArray_Calendar;    // 달력 데이터 배열
    private GridView_Adapter mAdapter_Calendar;             // 달력 그리드뷰 어뎁터

    private Calendar Now_Calendar;                      // 현재달 달력
    private Calendar Before_Calendar;                   // 이전달 달력
    private Calendar Next_Calendar;                     // 다음달 달력

    private int now_Year, now_Month, now_DayMax;   // 현재 년,월,일
    private int before_Year, before_Month, before_DayMax;   // 이전 년,월,일
    private int next_Year, next_Month;                      // 다음 년,월,일

    private HashMap<String, ArrayList<String>> mmMap;  // 해쉬맵
    private TextView mSeld_Text_Num;        // 선택한 정보 출력뷰

    private Button mBtn_before_M;           // 이전달 버튼
    private Button mBtn_next_M;             // 다음달 버튼

    private ListView mSeld_List_View;           // 리스트뷰
    private ListView_Adapter mSeld_Adapter;     // 리스트뷰 어뎁터

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
        action_Bar.setTitle("실습3 일정달력!");

        // 2. 뷰 설정 ============================================
        mText_YM = (TextView) findViewById(R.id.text_YM);
        mSeld_Text_Num = (TextView)findViewById(R.id.seld_Text_Num);
        mSeld_List_View = (ListView)findViewById(R.id.seld_List_View);
        mBtn_before_M = (Button) findViewById(R.id.btn_before_M);
        mBtn_next_M = (Button) findViewById(R.id.btn_next_M);

        // 3. 달력 스타트 (첫로딩) =================================
        Calendar loadCal = Calendar.getInstance();
        Now_Calendar = get_Calendar(loadCal, 0);
        Before_Calendar = get_Calendar(Now_Calendar, -1);
        Next_Calendar = get_Calendar(Now_Calendar, +1);
        CalCreate_AdapterSet(Before_Calendar, Now_Calendar, Next_Calendar);

        // 이전달 버튼 클릭 리스너 (3개의 달력을 모두 -1 개월)
        mBtn_before_M.setOnClickListener(this);

        // 다음달 버튼 클릭 리스너 (3개의 달력을 모두 +1 개월)
        mBtn_next_M.setOnClickListener(this);
    }

    //===============================================================
    ////// 버튼 클릭 이벤트
    //===============================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_before_M:
                Before_Calendar = get_Calendar(Before_Calendar, -1);
                Now_Calendar = get_Calendar(Now_Calendar, -1);
                Next_Calendar = get_Calendar(Next_Calendar, -1);
                CalCreate_AdapterSet(Before_Calendar, Now_Calendar, Next_Calendar);
                break;

            case R.id.btn_next_M:
                Before_Calendar = get_Calendar(Before_Calendar, +1);
                Now_Calendar = get_Calendar(Now_Calendar, +1);
                Next_Calendar = get_Calendar(Next_Calendar, +1);
                CalCreate_AdapterSet(Before_Calendar, Now_Calendar, Next_Calendar);
                break;
        }
    }


    //===============================================================
    ////// 아이템 클릭 이벤트 메소드
    //===============================================================
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 1.어레이어댑터의 셀렉트 포지션 호출
        mAdapter_Calendar.setSelectedPosition(position, id);

        // 2.어뎁터의 풀일자 받아오기
        GridView_DayInfo FDD = mAdapter_Calendar.getItem(position);
        String ftext = FDD.getFullDay();

        // 3. 텍스트뷰에 포지션의 값주기
        mSeld_Text_Num.setText(ftext);

        // 4. 해쉬맵 일정 가져오기
        ArrayList<String> temp_ArrayList = mmMap.get(ftext);

        if (temp_ArrayList == null) {
            mSeld_List_View.setAdapter(null);
        } else {
            mSeld_Adapter = new ListView_Adapter(getApplicationContext(), temp_ArrayList);
            mSeld_List_View.setAdapter(mSeld_Adapter);
        }
    }

    //===============================================================
    ////// 달력 생성 어뎁터 셋팅 실행
    //===============================================================
    public void CalCreate_AdapterSet(Calendar BeforeCalendar, Calendar NowCalendar, Calendar NextCalendar) {

        // 1. 현재달 달력 정보 생성 =================================
        now_Year = NowCalendar.get(Calendar.YEAR);  //★
        now_Month = NowCalendar.get(Calendar.MONTH) + 1;  //★
        now_DayMax = NowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); //★
        int now_DayWeek = getMonth_LastDayWeek(now_Year, now_Month, now_DayMax); //★
        String strNow_YM = String.valueOf(now_Year) + "년 " + String.valueOf(now_Month) + "월";
        mText_YM.setText(strNow_YM);

        // 2. 이전달 달력 정보 생성 =================================
        before_Year = BeforeCalendar.get(Calendar.YEAR);
        before_Month = BeforeCalendar.get(Calendar.MONTH) + 1;
        before_DayMax = BeforeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); //★
        int last_DayWeek = getMonth_LastDayWeek(before_Year, before_Month, before_DayMax); //★
        String strBefore_YM = String.valueOf(before_Year) + "년 " + String.valueOf(before_Month) + "월";
        mBtn_before_M.setText(strBefore_YM);

        // 3. 다음달 달력 정보 생성 =================================
        next_Year = NextCalendar.get(Calendar.YEAR);
        next_Month = NextCalendar.get(Calendar.MONTH) + 1;
        String strNext_YM = String.valueOf(next_Year) + "년 " + String.valueOf(next_Month) + "월";
        mBtn_next_M.setText(strNext_YM);

        // 4. 데이터 배열 생성 ======================================
        mArray_Header = new ArrayList<>();
        mArray_Calendar = new ArrayList<>();

        // 5. 일요일~토요일까지 헤더 배열 추가 =============================== 아답터에서 생성
        for(int w = 0; w < 7; w++){
            GridView_DayInfo week_Header = new GridView_DayInfo();
            week_Header.setWeek(array_WEEK[w]);
            mArray_Header.add(week_Header);
        }

        // 6. 달력 앞 빈칸을 지난달 마지막 날짜부터 앞으로 채우기 ========
        if (last_DayWeek < 7) {
            // 반복 횟수 시작점은 지난달 마지막 숫자에서 요일 리턴값 (마지막주 1번 요일값과 같음)
            // +1은 0번째부터 채우기 때문.
            int last_Month_sday = before_DayMax - last_DayWeek + 1 ;
            for (int dd = last_Month_sday; dd <= before_DayMax; dd++) {
                GridView_DayInfo add_LastDay = new GridView_DayInfo();
                add_LastDay.setLastDay(Integer.toString(dd));
                mArray_Calendar.add(add_LastDay);
            }
        }

        // 7. 선택한 월의 맥스 일수 만큼 배열 데이터 생성 ===============
        for (int ii = 1; ii <= now_DayMax; ii++) {
            GridView_DayInfo dayInfo = new GridView_DayInfo();
            dayInfo.setDay(Integer.toString(ii));

            // ★★★ 날짜 풀타입 형태 저장시키기 ★★★
            String FD = String.valueOf(now_Year) + "년" + String.valueOf(now_Month) + "월" + String.valueOf(ii) + "일";
            dayInfo.setFullDay(FD);
            mArray_Calendar.add(dayInfo);
        }

        // 8. 달력 끝 빈칸을 다음달 시작일부터 남은셀 끝까지 채우기 =======
        if (now_DayWeek < 7) {
            int next_Month_eday = 7 - now_DayWeek ;
            for (int nn = 1; nn <= next_Month_eday; nn++) {
                GridView_DayInfo add_NextDay;
                add_NextDay = new GridView_DayInfo();
                add_NextDay.setNextDay(Integer.toString(nn));
                mArray_Calendar.add(add_NextDay);
            }
        }

        // 9. 어뎁터에 배열 데이터를 셋팅 ==============================
        mAdapter_Header = new GridView_Adapter(getApplicationContext(),
                R.layout.activity_grid_view_calendar_cell, mArray_Header );

        mAdapter_Calendar = new GridView_Adapter(getApplicationContext(),
                R.layout.activity_grid_view_calendar_cell, mArray_Calendar );

        // 10. 그리드뷰에 어뎁터를 셋팅하여 출력 =========================
        mGrid_Header = (GridView) findViewById(R.id.grid_Header);
        mGrid_Header.setAdapter(mAdapter_Header);

        mGrid_Calendar = (GridView) findViewById(R.id.grid_View);
        mGrid_Calendar.setAdapter(mAdapter_Calendar);

        // 그리드뷰의 아이템 클릭 리스너 호출
        mGrid_Calendar.setOnItemClickListener(this);

    }

    //===============================================================
    ////// 달력 생성
    //===============================================================
    private Calendar get_Calendar(Calendar calendar, int month) {
        Calendar G_CAL = Calendar.getInstance();
        G_CAL.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        if (month != 0) {
            G_CAL.add(Calendar.MONTH, month);
        }
        G_CAL.set(G_CAL.get(Calendar.YEAR), G_CAL.get(Calendar.MONTH), 1);
        return G_CAL;
    }

    //===============================================================
    ////// 특정일 요일 구하기
    //===============================================================
    private int getMonth_LastDayWeek(int yy, int mm, int dd) {
        Calendar LDW_CAL = Calendar.getInstance();
        LDW_CAL.set(yy, mm-1, dd);
        int result_w = LDW_CAL.get(Calendar.DAY_OF_WEEK);
        return result_w;
    }

    //===============================================================
    ////// 다이얼로그 생성 메소드
    //===============================================================
    private void showSchedule_InputDialog(final String day_key) {
        AlertDialog.Builder ad_Builder = new AlertDialog.Builder(GridView_Calendar.this);
        LayoutInflater ad_Inflater = getLayoutInflater();

        View view = ad_Inflater.inflate(R.layout.activity_grid_view_inputdialog, null);
        final EditText dialog_in_text = (EditText)view.findViewById(R.id.dialog_et_text);
        final EditText dialog_in_hour = (EditText)view.findViewById(R.id.dialog_et_hour);
        final EditText dialog_in_min = (EditText)view.findViewById(R.id.dialog_et_min);

        ad_Builder.setTitle(day_key);
        ad_Builder.setView(view)
                .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // <<< 저장처리 >>>

                        // 1. 다이얼로그의 값 변수에 저장
                        String val_text = dialog_in_text.getText().toString();
                        String val_hour = dialog_in_hour.getText().toString();
                        String val_min = dialog_in_min.getText().toString();

                        String val_Content = String.valueOf(val_hour) + ":"
                                + String.valueOf(val_min) + " "
                                + String.valueOf(val_text);

                        // 2. 각 뷰의 값이 있는지 확인
                        // 2.1 값이 다 존재하면 선택한 키가 배열키라고 선언
                        // 2.2 만일 해당키의 배열에 값이 없다면 새로운 배열을 선언
                        // 2.3 배열에 데이터를 add
                        // 2.4 선택한셀의 데이터어뎁터를 생성하고 키 배열을 담아준다.
                        // 2.5 생성한 어뎁터를 리스트뷰에 셋팅하고
                        // 2.6 해쉬맵에 키와 배열을 담아준다.

                        if (!TextUtils.isEmpty(val_text)
                                && !TextUtils.isEmpty(val_hour)
                                && !TextUtils.isEmpty(val_min)) {

                            ArrayList<String> array_value = mmMap.get(day_key);
                            if (array_value == null) {
                                array_value = new ArrayList<>();
                            }

                            array_value.add(val_Content);
                            mSeld_Adapter = new ListView_Adapter(getApplicationContext(), array_value);

                            mSeld_List_View.setAdapter(mSeld_Adapter);

                            mmMap.put(day_key, array_value);
                        }
                    }
                })
                .setNegativeButton("닫기", null);
        ad_Builder.show();
    }


    //===============================================================
    ////// 메뉴 생성 옵션
    //===============================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gridcalendar, menu);
        return true;
    }

    //===============================================================
    ////// 메뉴 선택
    //===============================================================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_SelDay_input) {
            String ftext = mSeld_Text_Num.getText().toString();
            if (!TextUtils.isEmpty(ftext)) {
                showSchedule_InputDialog(ftext);
                return true;
            } else {
                showToast("일자를 클릭하세요.");
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

