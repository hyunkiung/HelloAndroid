
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

public class TourList_PHOTODT_Activity extends ActionBarActivity {

    private TourList_DB_Helper dbHelper;
    private EditText mET_mid, mET_FullUrl, mET_wdt;
    private EditText mET_edit_mid, mET_edit_FullUrl, mET_edit_wdt;
    private ListView mLV_DataList;
    private String now_time;
    private int update_id;

    private TourList_PHOTODT_Adapter Adapter_PHOTODT;
    private ArrayList<TourList_PHOTODT_info> ArrayList_PHOTODT;
    private ArrayList<TourList_PHOTODT_info> ArrayList_oneData;

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    // 메세지 토스트 메소드 (공용)
    void showToast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourlist_photodt_activity);

        // DB Open, DataBase 정보 출력 호출
        default_Set_and_DB_info();

        // PHOTODT 테이블 조회 출력 호출
        PHOTODT_ListView_SelectAll();

        // 시스템시간 에디트박스 초기 셋팅
        now_time = dbHelper.SystemTime_Now();
        mET_wdt.setText(now_time);

        // 레코드 등록 버튼 액션
        findViewById(R.id.btn_record_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PHOTODT 테이블 입력 호출
                PHOTODT_ListView_Insert();
            }
        });

        // 레코드 수정 버튼 액션
        findViewById(R.id.btn_record_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PHOTODT 테이블 수정 호출
                PHOTODT_ListView_Update();
            }
        });

        // 레코드 수정 리스트뷰 클릭 액션
        mLV_DataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                update_id = Adapter_PHOTODT.getItem_id(position);
                // PHOTODT 테이블 선택값 조회 출력 호출
                PHOTODT_ListView_SelectOne(update_id);
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
        mET_mid = (EditText) findViewById(R.id.et_mid);
        mET_FullUrl = (EditText) findViewById(R.id.et_FullUrl);
        mET_wdt = (EditText) findViewById(R.id.et_wdt);
        mET_edit_mid = (EditText) findViewById(R.id.et_edit_mid);
        mET_edit_FullUrl = (EditText) findViewById(R.id.et_edit_FullUrl);
        mET_edit_wdt = (EditText) findViewById(R.id.et_edit_wdt);

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

    // Table PHOTODT 조회 메소드 (전체 조회)
    public void PHOTODT_ListView_SelectAll() {
        ArrayList_PHOTODT = new ArrayList<>();
        ArrayList_PHOTODT = dbHelper.PHOTODT_selectColumn_All(0);
        Adapter_PHOTODT = new TourList_PHOTODT_Adapter(this, ArrayList_PHOTODT);
        mLV_DataList.setAdapter(Adapter_PHOTODT);
        Result_Log("PHOTODT_ListView_Set() = " + ArrayList_PHOTODT);
    }

    // Table PHOTODT 선택값 조회 메소드 (1개 조회)
    public void PHOTODT_ListView_SelectOne(int uid) {
        ArrayList_oneData = new ArrayList<>();
        ArrayList_oneData = dbHelper.PHOTODT_selectColumn_All(uid);
        mET_edit_mid.setText(String.valueOf(ArrayList_oneData.get(0).mid));
        mET_edit_FullUrl.setText(ArrayList_oneData.get(0).FullUrl);
        mET_edit_wdt.setText(ArrayList_oneData.get(0).wdt);
        Result_Log("update 진행할 _id = " + update_id);
    }

    // Table PHOTODT 리스트뷰 리셋 메소드
    public void PHOTODT_ListView_ReSet() {
        ArrayList_PHOTODT.clear();
        ArrayList_PHOTODT = dbHelper.PHOTODT_selectColumn_All(0);
        Adapter_PHOTODT.reset_ArrayList(ArrayList_PHOTODT);
        Adapter_PHOTODT.notifyDataSetChanged();
        Result_Log("PHOTODT_ListView_ReSet() = " + Adapter_PHOTODT);
    }

    // Table PHOTODT 입력 메소드
    public void PHOTODT_ListView_Insert() {
        int mid = Integer.valueOf(mET_mid.getText().toString());
        String FullUrl = mET_FullUrl.getText().toString();
        String wdt = mET_wdt.getText().toString();
        if (!FullUrl.equals("") && !wdt.equals("")) {
            long addResult = dbHelper.PHOTODT_insertColumn(mid, FullUrl, wdt);
            showToast(String.valueOf(addResult) + "번 데이터가 등록되었습니다.");
            // 리스트뷰 데이터 리셋
            PHOTODT_ListView_ReSet();
        } else {
            showToast("값을 입력해주세요.");
        }
    }

    // Table PHOTODT 수정 메소드
    public void PHOTODT_ListView_Update() {
        int mid = Integer.valueOf(mET_edit_mid.getText().toString());
        String FullUrl = mET_edit_FullUrl.getText().toString();
        String wdt = mET_edit_wdt.getText().toString();
        if (!FullUrl.equals("") && !wdt.equals("")) {
            int upResult = dbHelper.PHOTODT_updateColumn(update_id, mid, FullUrl, wdt);
            showToast(upResult + "개의 데이터가 수정되었습니다.");
            PHOTODT_ListView_ReSet();
        } else {
            showToast("값을 입력해주세요.");
        }
    }

    // Table PHOTODT 삭제 메소드
    public void PHOTODT_ListView_Del(int id) {
        int delResult = dbHelper.PHOTODT_deleteColumn(id);
        showToast(delResult + "개의 데이터가 삭제되었습니다.");
        PHOTODT_ListView_ReSet();
    }
}
