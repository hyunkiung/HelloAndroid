
package com.example.administrator.helloandroid.project_team;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

// 생성한 db 파일 확인은..
// DDMS 실행, 좌측에서 실행한 디바이스를 선택한후 우측의 파일탐색기에서
// data폴더 확장, 다시 data 폴더를 확장하면 패키지 목록이 나오고
// 그곳에서 프로젝트 패키지명을 찾아라. com.example.administrator.helloandroid
// 패키지 트리를 확장하면 database 폴더가 보이고 그 안에 db파일이 있음.
// db 파일을 선택한후 우측 상단의 pull.. 버튼을 클릭해서 저장
// SQL Lite로 오픈해서 db 확인.
// DB02 생성해서 컨트롤하는 코딩을 업글해보자.


public class DB01_Create extends ActionBarActivity implements View.OnClickListener {

    //btn_db_Create  et_db_Name   btn_tbl_Create  et_tbl_Name  tv_db_Status
    private  String db_Name, table_Name;
    private TextView mTV_db_Status;
    private EditText mET_db_Name, mET_tbl_Name;

    boolean database_Created = false;
    boolean table_Created = false;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db01_create);

        mET_db_Name = (EditText) findViewById(R.id.et_db_Name);
        mET_tbl_Name = (EditText) findViewById(R.id.et_tbl_Name);
        mTV_db_Status = (TextView) findViewById(R.id.tv_db_Status);

        findViewById(R.id.btn_db_Create).setOnClickListener(this);
        findViewById(R.id.btn_tbl_Create).setOnClickListener(this);
    }

    //===========================================================
    //=== 버튼 클릭 이벤트
    //===========================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_db_Create :
                db_Name = mET_db_Name.getText().toString();
                DB_Create(db_Name);
                break;
            case R.id.btn_tbl_Create :
                table_Name = mET_tbl_Name.getText().toString();
                Table_Create(table_Name);
                int count = Table_Insert_SQL(table_Name);
                Result_Print(count + "개의 데이터를 입력함.");
                break;
            default :
                break;
        }

    }

    //===========================================================
    //=== DB 생성 메소드
    //===========================================================
    private void DB_Create(String db_nm) {
        Result_Print(db_nm + " 라는 이름의 DB 생성");

        // db가 존재하는지 확인하는 조건 필요.
        try {
            db = openOrCreateDatabase(db_nm, Activity.MODE_PRIVATE, null);

            database_Created = true;
            Result_Print("데이터베이스 생성 오키");
        } catch(Exception ex) {
            ex.printStackTrace();
            Result_Print("데이터베이스 생성 에러");
        }
    }

    //===========================================================
    //=== TABLE 생성 메소드
    //===========================================================
    private void Table_Create(String tbl_nm) {
        Result_Print(tbl_nm + " 라는 이름의 TABLE 생성");

        // 테이블이 존재하는지 확인하는 조건 필요.
        // 테이블 생성 쿼리 실행
        db.execSQL("create table if not exists " + tbl_nm + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " phone text);" );

        table_Created = true;
    }

    //===========================================================
    //=== TABLE 데이터 입력 (쿼리 실행 방식)
    //===========================================================
    private int Table_Insert_SQL(String tbl_nm) {
        Result_Print(tbl_nm + " 라는 테이블에 데이터를 입력 시작 (쿼리 방식)");

        int count = 3;
        db.execSQL("insert into " + tbl_nm + "(name, age, phone) values ('John', 20, '010-7788-1234');");
        db.execSQL("insert into " + tbl_nm + "(name, age, phone) values ('Mike', 35, '010-8888-1111');");
        db.execSQL("insert into " + tbl_nm + "(name, age, phone) values ('Sean', 26, '010-6677-4321');");

        return count;
    }

    //===========================================================
    //=== TABLE 데이터 입력 (지정 테이블 Param Put 방식)
    //===========================================================
    private int Table_Insert_Param(String tbl_nm) {
        Result_Print(tbl_nm + " 라는 테이블에 데이터를 입력 시작 (파람풋 방식)");

        //int count = 1;
        ContentValues recordValues = new ContentValues();

        recordValues.put("name", "Rice");
        recordValues.put("age", 43);
        recordValues.put("phone", "010-3322-9811");
        int rowPosition = (int) db.insert(tbl_nm, null, recordValues);

        return rowPosition;
    }

    //===========================================================
    //=== TABLE 데이터 업데이트 (지정 테이블 Param Put 방식)
    //===========================================================
    private int Table_Update_Param(String tbl_nm) {
        Result_Print(tbl_nm + " 라는 테이블의 데이터를 수정함");

        ContentValues recordValues = new ContentValues();
        recordValues.put("age", 43);
        String[] whereArgs = {"Rice"};
        int rowAffected = db.update(tbl_nm, recordValues, "name = ?", whereArgs);

        return rowAffected;
    }

    //===========================================================
    //=== TABLE 데이터 삭제 (지정 테이블 Param Put 방식)
    //===========================================================
    private int Table_Delete_Param(String tbl_nm) {
        Result_Print(tbl_nm + " 라는 테이블의 데이터를 삭제함");

        String[] whereArgs = {"Rice"};
        int rowAffected = db.delete(tbl_nm, "name = ?", whereArgs);

        return rowAffected;
    }

    private void Result_Print(String msg) {
        Log.d("SampleDatabase", msg);
        mTV_db_Status.append("\n" + msg);
    }

}
