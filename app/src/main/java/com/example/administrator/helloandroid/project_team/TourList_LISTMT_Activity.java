
package com.example.administrator.helloandroid.project_team;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class TourList_LISTMT_Activity extends ActionBarActivity {

    private TourList_DB_Helper dbHelper;
    private EditText mET_title1, mET_title2, mET_title3;
    private EditText mET_contents, mET_weather, mET_companion, mET_location;
    private EditText mET_pid, mET_tdt, mET_wdt, mET_edt;
    private Button mBTN_record_save, mBTN_pid_save;
    private ListView mLV_DataList;
    private String now_time;
    private int update_id;
    private String title1, title2, title3, contents, weather, companion, location, tdt, wdt, edt;
    private int pid;
    private String writeModeFlag = "W";

    private TourList_LISTMT_Adapter Adapter_LISTMT;
    private ArrayList<TourList_LISTMT_info> ArrayList_LISTMT;
    private ArrayList<TourList_LISTMT_info> ArrayList_oneData;

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    // 메세지 토스트 메소드 (공용)
    void showToast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourlist_listmt_activity);

        // DB Open, DataBase 정보 출력 호출
        default_Set_and_DB_info();

        // LISTMT 테이블 조회 출력 호출
        LISTMT_ListView_SelectAll();

        // 시스템시간 에디트박스 초기 셋팅
        now_time = dbHelper.SystemTime_Now();
        mET_tdt.setText(now_time.substring(0, 10));
        mET_wdt.setText(now_time);
        mET_edt.setText(now_time);

        // 레코드 등록 버튼 액션
        mBTN_record_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (writeModeFlag == "W") {
                    LISTMT_ListView_Insert();
                } else {
                    LISTMT_ListView_Update();
                }
            }
        });

        // 대표사진 번호 변경 버튼 액션
        mBTN_pid_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LISTMT_ListView_UpdatePhoto();
            }
        });


        // 레코드 수정 리스트뷰 클릭 액션
        mLV_DataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                update_id = Adapter_LISTMT.getItem_id(position);
                // CODEMT 테이블 선택값 조회 출력 호출
                LISTMT_ListView_SelectOne(update_id);
                writeModeFlag = "E"; // 입력모드 플레그 변경
                mBTN_record_save.setText("데이터 수정");
            }
        });
    }

    // 앱 종료시 콜백 함수로 DB Close
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }


    //=======================================================================
    // Custom Method Start ==================================================
    //=======================================================================

    // DataBase 정보 생성 메소드
    public void default_Set_and_DB_info () {
        mLV_DataList = (ListView) findViewById(R.id.lv_DataList);
        mET_title1 = (EditText) findViewById(R.id.et_title1);
        mET_title2 = (EditText) findViewById(R.id.et_title2);
        mET_title3 = (EditText) findViewById(R.id.et_title3);
        mET_contents = (EditText) findViewById(R.id.et_contents);
        mET_weather = (EditText) findViewById(R.id.et_weather);
        mET_companion = (EditText) findViewById(R.id.et_companion);
        mET_location = (EditText) findViewById(R.id.et_location);
        mET_pid = (EditText) findViewById(R.id.et_pid);
        mET_tdt = (EditText) findViewById(R.id.et_tdt);
        mET_wdt = (EditText) findViewById(R.id.et_wdt);
        mET_edt = (EditText) findViewById(R.id.et_edt);
        mBTN_record_save = (Button) findViewById(R.id.btn_record_save);
        mBTN_pid_save = (Button) findViewById(R.id.btn_pid_save);

        dbHelper = new TourList_DB_Helper(this);
        boolean openReturn = dbHelper.open();
        String db_name, db_path;
        if(openReturn == true) {
            Result_Log("dbHelper.open() = true");
            db_name = "접속 DB : " + dbHelper.DATABASE_NAME + " (Version : "+ dbHelper.DATABASE_VERSION +")";
            db_path = dbHelper.DATABASE_PATH;
        } else {
            Result_Log("dbHelper.open() = false");
            db_name = "DB Connection Error";
            db_path = "DB Connection Error";
        }
    }

    // Table LISTMT 조회 메소드 (전체 조회)
    public void LISTMT_ListView_SelectAll() {
        ArrayList_LISTMT = new ArrayList<>();
        ArrayList_LISTMT = dbHelper.LISTMT_selectColumn_All(0);
        Adapter_LISTMT = new TourList_LISTMT_Adapter(this, ArrayList_LISTMT);
        mLV_DataList.setAdapter(Adapter_LISTMT);
        Result_Log("CODEMT_ListView_Set() = " + ArrayList_LISTMT);
    }

    // Table LISTMT 선택값 조회 메소드 (1개 조회)
    public void LISTMT_ListView_SelectOne(int uid) {
        ArrayList_oneData = new ArrayList<>();
        ArrayList_oneData = dbHelper.LISTMT_selectColumn_All(uid);
        title1 = ArrayList_oneData.get(0).title1;
        title2 = ArrayList_oneData.get(0).title2;
        title3 = ArrayList_oneData.get(0).title3;
        contents = ArrayList_oneData.get(0).contents;
        weather = ArrayList_oneData.get(0).weather;
        companion = ArrayList_oneData.get(0).companion;
        location = ArrayList_oneData.get(0).location;
        tdt = ArrayList_oneData.get(0).tdt;
        wdt = ArrayList_oneData.get(0).wdt;
        edt = ArrayList_oneData.get(0).edt;
        pid = ArrayList_oneData.get(0).pid;
        Trans_Variable_to_Object(title1, title2, title3, contents, weather, companion, location, tdt, wdt, edt, pid);
        Result_Log("update 진행할 _id = " + update_id);
    }

    // Table LISTMT 리스트뷰 리셋 메소드
    public void LISTMT_ListView_ReSet() {
        ArrayList_LISTMT.clear();
        ArrayList_LISTMT = dbHelper.LISTMT_selectColumn_All(0);
        Adapter_LISTMT.reset_ArrayList(ArrayList_LISTMT);
        Adapter_LISTMT.notifyDataSetChanged();
        Result_Log("CODEMT_ListView_ReSet() = " + Adapter_LISTMT);
    }

    // Table LISTMT 입력 메소드
    public void LISTMT_ListView_Insert() {
        Trans_Object_to_Variable();
        if (!title1.equals("") && !title2.equals("") && !title3.equals("")) {
            long addResult = dbHelper.LISTMT_insertColumn(title1, title2, title3, contents, weather, companion, location, tdt, wdt, edt);
            showToast(String.valueOf(addResult) + "번 데이터가 등록되었습니다.");
            // 리스트뷰 데이터 리셋
            LISTMT_ListView_ReSet();
        } else {
            showToast("타이틀 값은 3개 모두 입력해주세요.");
        }
    }

    // Table LISTMT 수정 메소드
    public void LISTMT_ListView_Update() {
        Trans_Object_to_Variable();
        if (!title1.equals("") && !title2.equals("") && !title3.equals("")) {
            int upResult = dbHelper.LISTMT_updateColumn(update_id, title1, title2, title3, contents, weather, companion, location, tdt, edt);
            showToast(upResult + "개의 데이터가 수정되었습니다.");
            LISTMT_ListView_ReSet();

            writeModeFlag = "W"; // 입력모드 플레그 변경
            mBTN_record_save.setText("레코드 추가");
            Trans_Variable_to_Object("", "", "", "", "", "", "", "", "", "", 0);

        } else {
            showToast("타이틀 값은 3개 모두 입력해주세요.");
        }
    }

    // Table LISTMT 대표사진 수정 메소드
    public void LISTMT_ListView_UpdatePhoto() {
        Trans_Object_to_Variable();
        if (pid >= 0) {
            int upResult = dbHelper.LISTMT_updatePhoto(update_id, pid);
            showToast(upResult + "개의 데이터가 수정되었습니다.");
            LISTMT_ListView_ReSet();

            writeModeFlag = "W"; // 입력모드 플레그 변경
            mBTN_record_save.setText("레코드 추가");
            Trans_Variable_to_Object("", "", "", "", "", "", "", "", "", "", 0);
            update_id = 0;
        } else {
            showToast("정수를 입력해주세요.");
        }
    }

    // Table LISTMT 삭제 메소드
    public void LISTMT_ListView_Del(int id) {
        int delResult = dbHelper.LISTMT_deleteColumn(id);
        showToast(delResult + "개의 데이터가 삭제되었습니다.");
        LISTMT_ListView_ReSet();
    }

    // EditText 값 변수 저장 처리 메소드
    public void Trans_Object_to_Variable() {
        title1 = mET_title1.getText().toString();
        title2 = mET_title2.getText().toString();
        title3 = mET_title3.getText().toString();
        contents = mET_contents.getText().toString();
        weather = mET_weather.getText().toString();
        companion = mET_companion.getText().toString();
        location = mET_location.getText().toString();
        tdt = mET_tdt.getText().toString();
        wdt = mET_wdt.getText().toString();
        edt = mET_edt.getText().toString();
        pid = Integer.valueOf(mET_pid.getText().toString());
    }

    // 변수 값 EditText 전달 처리 메소드
    public void Trans_Variable_to_Object(String t1, String t2, String t3, String con, String wea, String com, String lo, String td, String wd, String ed, int pi) {
        mET_title1.setText(t1);
        mET_title2.setText(t2);
        mET_title3.setText(t3);
        mET_contents.setText(con);
        mET_weather.setText(wea);
        mET_companion.setText(com);
        mET_location.setText(lo);
        mET_tdt.setText(td);
        mET_wdt.setText(wd);
        mET_edt.setText(ed);
        mET_pid.setText(String.valueOf(pi));
    }
}
