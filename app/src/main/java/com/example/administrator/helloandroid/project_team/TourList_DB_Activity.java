package com.example.administrator.helloandroid.project_team;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class TourList_DB_Activity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private TourList_DB_Helper dbHelper;
    private ArrayList<String> ArrayList_TABLE;
    private ArrayAdapter<String> Adapter_TABLE;

    private TextView mTV_DB_Name;
    private TextView mTV_DB_Path;
    private ListView mLV_DataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourlist_db_activity);

        // DB Open, DataBase 정보 출력 호출
        default_Set_and_DB_info();

        // 테이블 목록 조회 호출
        TABLE_Adapter_ReSetting();

        // 테이블 리스트뷰 아이템 클릭 이벤트 호출
        mLV_DataList.setOnItemClickListener(this);
    }



    // 앱 종료시 콜백 함수로 DB Close
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    // DataBase 정보 생성 메소드
    public void default_Set_and_DB_info () {
        mLV_DataList = (ListView) findViewById(R.id.lv_DataList);
        mTV_DB_Name = (TextView) findViewById(R.id.tv_DB_Name);
        mTV_DB_Path = (TextView) findViewById(R.id.tv_DB_Path);

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
        mTV_DB_Name.setText(db_name);
        mTV_DB_Path.setText(db_path);
    }

    // TABLE ListView 셋팅 : TABLE 목록 데이터 조회해서 어뎁터 생성
    public void TABLE_Adapter_ReSetting () {
        //dbHelper.open();
        ArrayList_TABLE = dbHelper.TABLE_TableAll_inDB();
        Adapter_TABLE = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, ArrayList_TABLE);
        mLV_DataList.setAdapter(Adapter_TABLE);

    }

    // 테이블 리스트뷰 아이템 클릭 이벤트
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String dt = Adapter_TABLE.getItem(position);
        showToast(dt);
    }

    //===============================================================
    // 흐름 확인용 로그 출력 메소드
    //===============================================================
    private void Result_Log(String msg) {
        Log.d("로그 : ", msg);
    }

    //===============================================================
    // 메세지 토스트 메소드 (공용)
    //===============================================================
    void showToast(CharSequence toast_msg) {
        Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show();
    }


}
