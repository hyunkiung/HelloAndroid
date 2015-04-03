package com.example.administrator.helloandroid.project_team;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2015-04-02.
 */

public class DB02_HelperClass {

    // DB 정보 선언
    public static String DATABASE_NAME = "customer.db";
    public static String TABLE_NAME = "";
    public static int DATABASE_VERSION = 1;

    // DB 관련 객체 선언
    private Open_Helper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // 부가적인 객체들
    private Context activity_Context;
    private ArrayList<String> sql_DataList;
    private String sqlQuery;

    // DB02_DB_HelperClass 생성자
    public DB02_HelperClass(Context context) {
        this.activity_Context = context;
        this.opener = new Open_Helper(context, DATABASE_NAME, null, DATABASE_VERSION);
        //db = opener.getWritableDatabase();
        //db = opener.getReadableDatabase(); // 읽기 전용 모드로 데이터베이스를 오픈
    }

    // ★★ 커스텀 메소드 ★★ DB 오픈 메소드
    public void open() {
        db = opener.getWritableDatabase();
    }

    // ★★ 커스텀 메소드 ★★ DB 종료 메소드
    public void close() {
        opener.close();
    }



    //==================================================
    // DataBase Control Method Start ===================
    //==================================================
    // ★★ 커스텀 메소드 ★★ DB Helper 오픈 메소드
    // DB 생성 확인용 메소드이고, 실제 클래스 사용시는 상위클래스의 생성자에서 db 객체에 오픈해서 시작함.
    // getWritableDatabase() 읽기, 쓰기 모드로 데이터베이스를 오픈.
    // 만약 처음으로 호출되는 경우에는 onCreate() -> onOpen() 순서로 호출.
    // 만약 DB 버전이 달라지는 경우에는 onUpgrade() -> onOpen() 순서로 호출.
    public String DB_Open() {
        Result_Log("상위클래스 메소드 DB_Open : " + DATABASE_NAME);
        opener.getWritableDatabase();
        return "DB_Open 실행 결과 " + DATABASE_NAME + " DB 생성 / 존재 여부 : " + DB_ExistentCheck();
    }

    // ★★ 커스텀 메소드 ★★ DB 존재 확인 메소드
    public String DB_ExistentCheck() {
        Result_Log("상위클래스 메소드 DB_ExistentCheck : " + DATABASE_NAME);
        if (Arrays.binarySearch(activity_Context.databaseList(), DATABASE_NAME) > 0) {
            return "Y"; // DB 존재
        } else {
            return "N"; // DB 없음
        }
    }

    // ★★ 커스텀 메소드 ★★ DB 삭제 메소드
    public String DB_Drop() {
        Result_Log("상위클래스 메소드 DB_Drop : " + DATABASE_NAME);
        if (DB_ExistentCheck() == "Y") {
            activity_Context.deleteDatabase(DATABASE_NAME);
        }
        return "DB_Drop 실행 결과 " + DATABASE_NAME + "DB 삭제 / 존재 여부 : " + DB_ExistentCheck();
    }
    //==================================================
    // DataBase Control Method End =====================
    //==================================================



    //==================================================
    // Table Control Method Start ======================
    //==================================================
    // ★★ 커스텀 메소드 ★★ TABLE 존재 확인 메소드
    public int TABLE_ExistentCheck(String tbl_nm) {
        Result_Log("상위클래스 메소드 TABLE_ExistentCheck : " + tbl_nm);
        sqlQuery = "select name from sqlite_master where name = ?";
        String[] args = {tbl_nm};
        Cursor cursor = db.rawQuery(sqlQuery, args);
        int cc = cursor.getCount();
        cursor.close();
        return cc;
    }

    // ★★ 커스텀 메소드 ★★ TABLE 생성 메소드
    public String Table_Create(String tbl_nm, String field1, String type1, String field2, String type2, String field3, String type3) {
        Result_Log("상위클래스 메소드 Table_Create : " + tbl_nm);
        String result, t1, t2, t3, tt;
        String t4 = "";
        String t5 = "";
        String t6 = "";

        if(tbl_nm.equals("")) {
            result = "테이블명을 입력해주세요.";
        } else if (TABLE_ExistentCheck(tbl_nm) > 0 ) {
            result = "테이블명이 존재합니다.";
        } else {
            t1 = "create table if not exists ";
            t2 = tbl_nm;
            t3 = "( _id integer PRIMARY KEY autoincrement ";
            if(!field1.equals("")) {
                t4 = "," + field1 + " " + type1 ;
            }
            if(!field2.equals("")) {
                t5 = "," + field2 + " " + type2 ;
            }
            if(!field3.equals("")) {
                t6 = "," + field3 + " " + type3 ;
            }
            t6 = "); ";
            tt = t1 + t2 + t3 + t4 + t5 + t6;
            db.execSQL(tt);
            result = "테이블이 생성되었습니다.";
        }
        return result;
    }

    // ★★ 커스텀 메소드 ★★ TABLE 내역 메소드
    public ArrayList<String> TABLE_TableAll_inDB() {
        Result_Log("상위클래스 메소드 TABLE_TableAll_inDB : " + DATABASE_NAME);
        // 1. 배열선언
        sql_DataList = new ArrayList<>();
        // 2. 쿼리 생성
        String strSQL = "select name from sqlite_master where type = 'table' "
                            + "and name not in ('android_metadata', 'sqlite_sequence') "
                            + "order by name asc";
        // 3. 커서에 쿼리 실행 수행
        Cursor cursor = db.rawQuery(strSQL, null);
        // 4. cursor가 비어 있으면 빈데이터 넣어주고 값 존재시 조회 데이터 add
        if (cursor.getCount() == 0) {
            sql_DataList.add("데이터가 없습니다.");
        } else {
            // 5. 첫번째 행으로 이동한뒤 다음행이 있을때까지 반복
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                sql_DataList.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        // 6. 커서 닫고, 배열리턴
        cursor.close();
        return sql_DataList;

        //cursor 를 리턴해서 받는 어댑터가 있다. CursorAdapter
    }
    //==================================================
    // Table Control Method End ========================
    //==================================================



    //=================================================
    // SQLiteOpenHelper Class Start ===================
    //=================================================
    public class Open_Helper extends SQLiteOpenHelper {
        // 생성자
        // 파라미터 :
        //   1. 컨텍스트로 액티비티 안에서 만들경우 this로 지정 가능
        //   2. 데이터베이스 이름
        //   3. 데이터 조회시 리턴하는 커서를 만들어낼 CursorFactory 객체
        //   4. 버전정보 (DB 업그레이드를 위해서 기존 DB와 버전 정보를 다르게 지정하여 스키마나 데이터를 변경)
        public Open_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
//            mContext = context;
        }

        // 상속 메소드 : DB 생성 메소드
        @Override
        public void onCreate(SQLiteDatabase db) {
            Result_Log("상속 메소드 DB onCreate : " + DATABASE_NAME);
        }

        // 상속 메소드 : DB 확인 메소드
        @Override
        public void onOpen(SQLiteDatabase db) {
            Result_Log("상속 메소드 DB onOpen : " + DATABASE_NAME);
        }

        // 상속 메소드 : DB 버전 확인 메소드
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Result_Log("상속 메소드 DB onUpgrade : " + DATABASE_NAME + " / 버젼 : " + DATABASE_VERSION);
        }
    }
    //=================================================
    // SQLiteOpenHelper Class End =====================
    //=================================================




    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) {
        Log.d("로그 : ", msg);
        //mTV_db_Status.append("\n" + msg);
    }





    // ★★ 커스텀 메소드 ★★ TABLE ALL 데이터 조회 메소드
    public void TABLE_SelectAll() {
        Result_Log("테이블 TABLE_SelectAll : " + TABLE_NAME);



        //String name = edit_name.getText().toString();
//        Cursor cursor;
//        cursor = db.rawQuery("SELECT name, tel FROM contact where name='" + name + "';", null);
//
//        while (cursor.moveToNext()) {
//            String tel = cursor.getString(1);
//            edit_tel.setText(tel);
//        }

    }


}

