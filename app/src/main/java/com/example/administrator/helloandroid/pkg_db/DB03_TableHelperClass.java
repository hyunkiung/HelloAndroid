package com.example.administrator.helloandroid.pkg_db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Administrator on 2015-04-06.
 */
public class DB03_TableHelperClass {

    // DB 정보 선언
    public String DATABASE_NAME = "default.db";
    public String TABLE_NAME = "";
    public int DATABASE_VERSION = 1;
    private String sqlQuery = "";

    // DB 관련 객체 선언
    private DB_Helper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // 부가적인 객체들
    private Context activity_Context;
    private ArrayList<String> Helper_DB_List;
    private ArrayList<String> Helper_TABLE_List;

    public DB03_TableHelperClass(Context context, String dbNM) {
        this.activity_Context = context;
        DATABASE_NAME = dbNM;
        this.opener = new DB_Helper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Class Open
    public DB03_TableHelperClass open() throws SQLException {
        db = opener.getWritableDatabase();
        return this;
    }


    public void close() {
        opener.close();
    }

    // ★★ 커스텀 메소드 ★★ DB 내역 메소드
    public ArrayList<String> DB_List() {
        Result_Log("상위클래스 메소드 DB_List : " + DATABASE_NAME);
        // 1. 배열 선언
        String[] dblist = activity_Context.databaseList();
        // 2. 배열 정렬
        Arrays.sort(dblist);
        // 3. ArrayList로 컨버트
        Helper_DB_List = new ArrayList<>(Arrays.asList(dblist));

        // Transaction시 Atomic commit과 Rollback 기능을 지원하는 journal 파일 제거
        for(Iterator<String> it = Helper_DB_List.iterator() ; it.hasNext() ; )
        {
            String value = it.next();
            if(value.endsWith("journal"))
            {
                it.remove();
            }
        }
        return Helper_DB_List;
    }


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
        Helper_TABLE_List = new ArrayList<>();
        // 2. 쿼리 생성
        String strSQL = "select name from sqlite_master where type = 'table' "
                + "and name not in ('android_metadata', 'sqlite_sequence') "
                + "order by name asc";
        // 3. 커서에 쿼리 실행 수행
        Cursor cursor = db.rawQuery(strSQL, null);
        // 4. cursor가 비어 있으면 빈데이터 넣어주고 값 존재시 조회 데이터 add
        if (cursor.getCount() == 0) {
            Helper_TABLE_List.add("NONE_TABLE");
        } else {
            // 5. 첫번째 행으로 이동한뒤 다음행이 있을때까지 반복
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Helper_TABLE_List.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        // 6. 커서 닫고, 배열리턴
        cursor.close();
        return Helper_TABLE_List;

        //cursor 를 리턴해서 받는 어댑터가 있다. CursorAdapter
    }

    // ★★ 커스텀 메소드 ★★ TABLE 삭제 메소드
    public String TABLE_Drop(String tbl_Name) {
        Result_Log("상위클래스 메소드 TABLE_Drop : " + tbl_Name);
        sqlQuery = "drop table if exists " + tbl_Name;
        db.execSQL(sqlQuery);
        String result = "테이블이 존재합니다.";
        int cc = TABLE_ExistentCheck(tbl_Name);
        if (cc == 0) {
            result = "삭제되었습니다.";
        }
        return result;
    }
    //==================================================
    // Table Control Method End ========================
    //==================================================


    //=================================================
    // SQLiteOpenHelper Class Start ===================
    //=================================================
    public class DB_Helper extends SQLiteOpenHelper {

        public DB_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    //=================================================
    // SQLiteOpenHelper Class End =====================
    //=================================================


    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) {
        Log.d("로그 : ", msg);
    }
}
