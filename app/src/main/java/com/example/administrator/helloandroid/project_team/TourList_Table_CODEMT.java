
package com.example.administrator.helloandroid.project_team;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class TourList_Table_CODEMT extends ActionBarActivity {

    private TourList_DB_Helper dbHelper;
    private EditText mET_key, mET_value, mET_wdt;
    private ListView mLV_DataList;
    private String now_time;

    private TourList_Adapter_CODEMT Adapter_CODEMT;
    private ArrayList<TourList_info_CODEMT> ArrayList_CODEMT;

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    // 메세지 토스트 메소드 (공용)
    void showToast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourlist_table_codemt);

        // DB Open, DataBase 정보 출력 호출
        default_Set_and_DB_info();

        // CODEMT 테이블 조회 출력 호출
        CODEMT_ListView_Set();

        // 시스템시간 에디트박스 초기 셋팅
        now_time = dbHelper.SystemTime_Now();
        mET_wdt.setText(now_time);

        // 레코드 등록 버튼 액션
        findViewById(R.id.btn_record_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODEMT_ListView_In();
            }
        });

        // 레코드 수정 리스트뷰 액션
        //mLV_DataList.setOnItemClickListener(this);
        mLV_DataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast("324");
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
        mET_key = (EditText) findViewById(R.id.et_key);
        mET_value = (EditText) findViewById(R.id.et_value);
        mET_wdt = (EditText) findViewById(R.id.et_wdt);

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

    // CODEMT 조회 메소드
    public void CODEMT_ListView_Set() {
        ArrayList_CODEMT = new ArrayList<>();
        ArrayList_CODEMT = dbHelper.CODEMT_selectColumn_All();
        Adapter_CODEMT = new TourList_Adapter_CODEMT(this, ArrayList_CODEMT);
        mLV_DataList.setAdapter(Adapter_CODEMT);
        Result_Log("CODEMT_ListView_Set() = " + ArrayList_CODEMT);
    }

    // CODEMT 리스트뷰 리셋 메소드
    public void CODEMT_ListView_ReSet() {
        ArrayList_CODEMT.clear();
        ArrayList_CODEMT = dbHelper.CODEMT_selectColumn_All();
        Adapter_CODEMT.reset_ArrayList(ArrayList_CODEMT);
        Adapter_CODEMT.notifyDataSetChanged();
        Result_Log("CODEMT_ListView_ReSet() = " + Adapter_CODEMT);
    }

    // CODEMT 입력 메소드
    public void CODEMT_ListView_In() {
        String key = mET_key.getText().toString();
        String value = mET_value.getText().toString();
        String wdt = mET_wdt.getText().toString();
        if (!key.equals("") && !value.equals("") && !wdt.equals("")) {
            long addResult = dbHelper.CODEMT_insertColumn(key, value, wdt);
            showToast(String.valueOf(addResult) + "번 데이터가 등록되었습니다.");
            // 리스트뷰 데이터 리셋
            CODEMT_ListView_ReSet();
        } else {
            showToast("값을 입력해주세요.");
        }
    }

    // CODEMT 삭제 메소드
    public void CODEMT_ListView_Del(int id) {
        int delResult = dbHelper.CODEMT_deleteColumn(id);
        showToast(delResult + "개의 데이터가 삭제되었습니다.");
        CODEMT_ListView_ReSet();
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        showToast("33333333333333");
//
////        Object dt = Adapter_CODEMT.getItem(position);
////        showToast(String.valueOf(dt));
//    }

}
