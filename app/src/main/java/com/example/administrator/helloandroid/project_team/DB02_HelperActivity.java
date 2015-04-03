package com.example.administrator.helloandroid.project_team;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class DB02_HelperActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private DB02_HelperClass dbHelper;
    private DB02_HelperClass dbHelper_btn, dbHelper_Table;

    private TextView mTV_status;
    private EditText mET_table_name, mET_field_name1, mET_field_name2, mET_field_name3;

    private String result = "DB02_DB_HelperClass 리턴값 확인용 TextView";
    private ArrayList<String> m_ArrayList;
    private ArrayAdapter<String> mDB_Adapter;
    private ArrayAdapter<String> mSpinner_ArrayAdapter;
    private ListView mLV_DataList;
    private Spinner mSP_data_type1, mSP_data_type2, mSP_data_type3;

    private String tbl_Name = "test_table";
    private String strTable;
    private String strFiled1, strFiled2, strFiled3;
    private String strDataType1, strDataType2, strDataType3;

    public static final String[] array_ITEMS = {
            "integer", "float", "text", "varchar", "date" };

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

        // 위젯 선언
        mTV_status = (TextView) findViewById(R.id.tv_status);
        mLV_DataList = (ListView) findViewById(R.id.lv_DataList);
        mTV_status.setText(result);

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
        findViewById(R.id.btn_table_Create).setOnClickListener(this);

        //================================================================
        //=== 테이블 생성 위젯과 액션
        //================================================================
        mET_table_name = (EditText) findViewById(R.id.et_table_name);
        mET_field_name1 = (EditText) findViewById(R.id.et_field_name1);
        mET_field_name2 = (EditText) findViewById(R.id.et_field_name2);
        mET_field_name3 = (EditText) findViewById(R.id.et_field_name3);

        mSP_data_type1 = (Spinner) findViewById(R.id.sp_data_type1);
        mSP_data_type2 = (Spinner) findViewById(R.id.sp_data_type2);
        mSP_data_type3 = (Spinner) findViewById(R.id.sp_data_type3);

        mSP_data_type1.setOnItemSelectedListener(this);
        mSP_data_type2.setOnItemSelectedListener(this);
        mSP_data_type3.setOnItemSelectedListener(this);

        // 스피너 어레이어뎁터 설정 - 미리 선언한 array_ITEMS 를 던졌다.
        mSpinner_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, array_ITEMS);
        // 스피너 어레이어뎁터 설정 - 드롭다운 형태 설정
        mSpinner_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너 어레이어뎁터 설정
        mSP_data_type1.setAdapter(mSpinner_ArrayAdapter);
        mSP_data_type2.setAdapter(mSpinner_ArrayAdapter);
        mSP_data_type3.setAdapter(mSpinner_ArrayAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_db_open :
                dbHelper_btn = new DB02_HelperClass(this);
                dbHelper_btn.DATABASE_NAME = "test.db";
                result = dbHelper_btn.DB_Open();
                mTV_status.setText(result);
                dbHelper_btn.close();
                break;
            case R.id.tv_db_drop :
                dbHelper_btn = new DB02_HelperClass(this);
                dbHelper_btn.DATABASE_NAME = "test.db";
                result = dbHelper_btn.DB_Drop();
                mTV_status.setText(result);
                dbHelper_btn.close();
                break;
            case R.id.tv_db_check :
                dbHelper_btn = new DB02_HelperClass(this);
                dbHelper_btn.DATABASE_NAME = "test.db";
                result = dbHelper_btn.DB_ExistentCheck();
                mTV_status.setText(result);
                dbHelper_btn.close();
                break;
            case R.id.btn_table_Create :
                EditText_getText();
                dbHelper_Table = new DB02_HelperClass(this);
                dbHelper_Table.open();
                String c = dbHelper_Table.Table_Create(strTable, strFiled1, strDataType1, strFiled2, strDataType2, strFiled3, strDataType3);
                dbHelper_Table.close();
                showToast(c);
                Adapter_ReSetting();
                break;
            default:
                break;
        }
    }


    // 데이터 조회해서 어뎁터 생성
    public void Adapter_ReSetting () {
        // 1. DB02_DB_HelperClass 객체 사용 선언
        dbHelper= new DB02_HelperClass(this);
        dbHelper.open();
        // 2. Data 요청 결과 배열 선언
        //m_ArrayList = new ArrayList<>();
        // 3. dbHelper의 조회결과값 배열 저장
        m_ArrayList = dbHelper.TABLE_TableAll_inDB();
        // 4. 어댑터 생성후 배열 셋팅
        mDB_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, m_ArrayList);
        // 5. 리스트뷰에 어댑터 연결
        mLV_DataList.setAdapter(mDB_Adapter);
        // 6. DB 사용 종료
        dbHelper.close();
    }

    // EditText 데이터 변수 담기
    public void EditText_getText() {
        strTable = mET_table_name.getText().toString();
        strFiled1 = mET_field_name1.getText().toString();
        strFiled2 = mET_field_name2.getText().toString();
        strFiled3 = mET_field_name3.getText().toString();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_data_type1 :
                strDataType1 = (String) mSP_data_type1.getSelectedItem();
                break;
            case R.id.sp_data_type2 :
                strDataType2 = (String) mSP_data_type2.getSelectedItem();
                break;
            case R.id.sp_data_type3 :
                strDataType3 = (String) mSP_data_type3.getSelectedItem();
                break;
            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
