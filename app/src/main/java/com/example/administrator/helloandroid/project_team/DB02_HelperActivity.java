package com.example.administrator.helloandroid.project_team;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class DB02_HelperActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private DB02_HelperClass dbHelper;

    private TextView mTV_status;
    private String result = "DB02_DB_HelperClass 리턴값 확인용 TextView";
    private ActionBar action_Bar;
    private String default_DB_NAME = "default.db"; // 초기값 할당.
    private ArrayList<String> act_ArrayList;
    private ArrayAdapter<String> act_Adapter;
    private ListView mLV_DataList;
    private EditText mET_db_name;

    //===============================================================
    ////// 메세지 토스트 메소드 (공용)
    //===============================================================
    void showToast(CharSequence toast_msg) {
        Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db02_helper_activity);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("DataBase 컨트롤 + 목록");

        // 위젯 선언
        mTV_status = (TextView) findViewById(R.id.tv_status);
        mTV_status.setText(result);
        mLV_DataList = (ListView) findViewById(R.id.lv_DataList);
        mET_db_name = (EditText) findViewById(R.id.et_db_name);

        // 리스트뷰 어뎁터 셋팅 호출
        Adapter_ReSetting();

        //================================================================
        // ★ HelperClass DB 테스트 메소드 사용법
        // result = dbHelper.DB_Open(); // getWritableDatabase이 알아서 처리하기 때문에 무조건 true
        // result = dbHelper.DB_Drop(); // 해당 DB 삭제
        // result = dbHelper.DB_ExistentCheck(); // 해당 DB 있는지 확인 리턴값 Y,N
        // dbHelper.close(); // DB 사용후 종료
        // 공통으로 사용하는 DB가 아닌경우 모든 액션별로 클래스를 새로 생성해야 한다.
        findViewById(R.id.tv_db_open).setOnClickListener(this);
        findViewById(R.id.tv_db_drop).setOnClickListener(this);
        findViewById(R.id.tv_db_check).setOnClickListener(this);
        //================================================================
        mLV_DataList.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_db_open:
                onClick_BackStart("CreateDB");
                Adapter_ReSetting();
                mET_db_name.setText("");
                break;
            case R.id.tv_db_drop:
                onClick_BackStart("DropDB");
                Adapter_ReSetting();
                mET_db_name.setText("");
                break;
            case R.id.tv_db_check:
                onClick_BackStart("CheckDB");
                Adapter_ReSetting();
                mET_db_name.setText("");
                break;
            default:
                break;
        }
    }

    // 버튼 클릭시 실제 적용되는 로직 메소드
    public void onClick_BackStart(String md) {
        String tempDB = mET_db_name.getText().toString();
        if (tempDB.equals("")) {
            result = "DB 이름을 입력하세요.";
        } else {
            // 실행하려는 DB로 헬퍼클래스 오픈
            dbHelper = new DB02_HelperClass(this, tempDB);
            switch (md) {
                case "CreateDB" :
                    result = dbHelper.DB_Open();
                    break;
                case "DropDB" :
                    result = dbHelper.DB_Drop();
                    break;
                case "CheckDB" :
                    result = dbHelper.DB_ExistentCheck();
                    break;
                default:
                    break;
            }
            dbHelper.close();
        }
        mTV_status.setText(result);

    }


    // 데이터 조회해서 어뎁터 생성
    public void Adapter_ReSetting () {
        // 1. DB02_DB_HelperClass 객체 사용 선언
        dbHelper= new DB02_HelperClass(this, default_DB_NAME);
        dbHelper.open();
        // 2. dbHelper의 조회결과값 배열 저장
        act_ArrayList = dbHelper.DB_List();
        // 3. 어댑터 생성후 배열 셋팅 context 를 this로 넘겼다. 어뎁터에서 역참조로 메소드를 실행하려고.★★★
        act_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, act_ArrayList);
        // 4. 리스트뷰에 어댑터 연결
        mLV_DataList.setAdapter(act_Adapter);
        // 5. DB 사용 종료
        dbHelper.close();
    }

    //리스트뷰 아이템 클릭 이벤트
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mET_db_name.setText(act_Adapter.getItem(position));
    }
}
