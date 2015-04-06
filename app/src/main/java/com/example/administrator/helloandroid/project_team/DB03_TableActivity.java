package com.example.administrator.helloandroid.project_team;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
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

public class DB03_TableActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String default_DB_NAME = "default.db"; // 초기값 할당.
    private String action_DB_NAME = "";
    private String default_TV_Text = "의 테이블 내역";
    private String strTable;
    private String strFiled1, strFiled2, strFiled3;
    private String strDataType1, strDataType2, strDataType3;

    private ActionBar action_Bar;
    private Spinner mSP_db_list;
    private TextView mTV_sel_db;
    private EditText mET_table_name, mET_field_name1, mET_field_name2, mET_field_name3;
    private Spinner mSP_data_type1, mSP_data_type2, mSP_data_type3;
    private ListView mLV_DataList;

    private DB03_TableHelperClass dbHelper, dbHelper_Table;
    private ArrayList<String> ArrayList_DB;
    private ArrayList<String> ArrayList_TABLE;
    private ArrayAdapter<String> Adapter_DB;
    private ArrayAdapter<String> Adapter_TYPE;
    private DB03_TableListViewAdapter Adapter_TABLE;

    // DataType 배열값 셋팅
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
        setContentView(R.layout.activity_db03_tableactivity);

        //액션바 선언 및 타이틀 설정, 위젯 선언
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("DataBase 컨트롤 + 목록");

        mLV_DataList = (ListView) findViewById(R.id.lv_DataList);
        mSP_db_list = (Spinner) findViewById(R.id.sp_db_list);
        mTV_sel_db = (TextView) findViewById(R.id.tv_sel_db);
        mTV_sel_db.setText(default_TV_Text);

        mET_table_name = (EditText) findViewById(R.id.et_table_name);
        mET_field_name1 = (EditText) findViewById(R.id.et_field_name1);
        mET_field_name2 = (EditText) findViewById(R.id.et_field_name2);
        mET_field_name3 = (EditText) findViewById(R.id.et_field_name3);

        mSP_data_type1 = (Spinner) findViewById(R.id.sp_data_type1);
        mSP_data_type2 = (Spinner) findViewById(R.id.sp_data_type2);
        mSP_data_type3 = (Spinner) findViewById(R.id.sp_data_type3);

        // 버튼 액션 셋팅
        mSP_db_list.setOnItemSelectedListener(this);
        findViewById(R.id.btn_table_Create).setOnClickListener(this);

        // DB 스피너 셋팅 콜
        DB_Adapter_ReSetting();

        // DataType 스피너 셋팅 콜
        TYPE_Adapter_ReSetting();

        // TABLE 커스텀 리스트뷰 셋팅 콜
        //TABLE_Adapter_ReSetting(); // Create에서는 에러난다.


    }

    // DataType Spinner 셋팅 : 데이터타입 어뎁터 생성
    public void TYPE_Adapter_ReSetting () {
        Adapter_TYPE = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, array_ITEMS);
        mSP_data_type1.setAdapter(Adapter_TYPE);
        mSP_data_type2.setAdapter(Adapter_TYPE);
        mSP_data_type3.setAdapter(Adapter_TYPE);
    }

    // DB Spinner 셋팅 : DB 목록 데이터 조회해서 어뎁터 생성
    public void DB_Adapter_ReSetting () {
        dbHelper= new DB03_TableHelperClass(this, default_DB_NAME);
        dbHelper.open();
        ArrayList_DB = dbHelper.DB_List();
        Adapter_DB = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ArrayList_DB);
        mSP_db_list.setAdapter(Adapter_DB);
        dbHelper.close();
    }

    // TABLE ListView 셋팅 : TABLE 목록 데이터 조회해서 어뎁터 생성
    public void TABLE_Adapter_ReSetting () {
        dbHelper_Table= new DB03_TableHelperClass(this, action_DB_NAME);
        dbHelper_Table.open();
        ArrayList_TABLE = dbHelper_Table.TABLE_TableAll_inDB();
        Adapter_TABLE = new DB03_TableListViewAdapter(this, ArrayList_TABLE);
        mLV_DataList.setAdapter(Adapter_TABLE);
        dbHelper_Table.close();
    }

    // TABLE DROP 처리 메소드
    public void TABLE_Drop_To_Helper (String tbl) {
        dbHelper_Table= new DB03_TableHelperClass(this, action_DB_NAME);
        dbHelper_Table.open();
        String c = dbHelper_Table.TABLE_Drop(tbl);
        showToast(c);
        dbHelper_Table.close();
        TABLE_Adapter_ReSetting();
    }


    // 테이블 생성 액션 처리 메소드
    @Override
    public void onClick(View v) {
        EditText_getText();
        dbHelper_Table = new DB03_TableHelperClass(this, action_DB_NAME);
        dbHelper_Table.open();
        String c = dbHelper_Table.Table_Create(strTable, strFiled1, strDataType1, strFiled2, strDataType2, strFiled3, strDataType3);
        dbHelper_Table.close();
        showToast(c);
        TABLE_Adapter_ReSetting();
        EditText_reSet();
    }

    // EditText 데이터 변수 저장 메소드
    public void EditText_getText() {
        strTable = mET_table_name.getText().toString();
        strFiled1 = mET_field_name1.getText().toString();
        strFiled2 = mET_field_name2.getText().toString();
        strFiled3 = mET_field_name3.getText().toString();
    }

    // EditText 초기화 메소드
    public void EditText_reSet() {
        mET_table_name.setText("");
        mET_field_name1.setText("");
        mET_field_name2.setText("");
        mET_field_name3.setText("");
    }


    // 스피너 셀렉트 액션 처리 메소드
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_db_list :
                String sel = Adapter_DB.getItem(position) + default_TV_Text;
                action_DB_NAME = Adapter_DB.getItem(position);
                mTV_sel_db.setText(sel);
                TABLE_Adapter_ReSetting();
                break;
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
