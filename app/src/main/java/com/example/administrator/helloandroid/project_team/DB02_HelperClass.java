package com.example.administrator.helloandroid.project_team;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Administrator on 2015-04-02.
 */

public class DB02_HelperClass {

    // DB 정보 선언
    public String DATABASE_NAME = "";
    public String TABLE_NAME = "";
    public int DATABASE_VERSION = 1;

    // DB 관련 객체 선언
    private Open_Helper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // 부가적인 객체들
    private Context activity_Context;
    private ArrayList<String> sql_DataList;



    // DB02_DB_HelperClass 생성자
    public DB02_HelperClass(Context context, String dbNM) {
        this.activity_Context = context;
        DATABASE_NAME = dbNM;
        this.opener = new Open_Helper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ★★ 커스텀 메소드 ★★ DB 오픈 메소드
    public void open() {
        opener.getWritableDatabase();
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
        this.open();
        return "DB_Open 실행 결과 " + DATABASE_NAME + " DB 생성 / 존재 여부 : " + DB_ExistentCheck();
    }

    // ★★ 커스텀 메소드 ★★ DB 존재 확인 메소드
    public String DB_ExistentCheck() {
        //Arrays.binarySearch 는 정렬되어있는 배열에서만 이진검색 (배열이니까 0번부터다)
        String[] dblist = activity_Context.databaseList();
        Arrays.sort(dblist);
        Arrays.binarySearch(dblist, DATABASE_NAME);
        if (Arrays.binarySearch(dblist, DATABASE_NAME) >= 0) {
            return "Y"; // DB 존재
        } else {
            return "N"; // DB 없음
        }
    }

    // ★★ 커스텀 메소드 ★★ DB 삭제 메소드
    public String DB_Drop() {
        Result_Log("상위클래스 메소드 DB_Drop : " + DATABASE_NAME);
        String result = "";
        if (DATABASE_NAME.equals("default.db")) {
            result = "default.db는 삭제하지 마세요.";
        } else if (DB_ExistentCheck() == "Y") {
            activity_Context.deleteDatabase(DATABASE_NAME);
            result = "DB_Drop 실행 결과 " + DATABASE_NAME + "DB 삭제 / 존재 여부 : " + DB_ExistentCheck();
        }
        return result;
    }

    // ★★ 커스텀 메소드 ★★ DB 내역 메소드
    public ArrayList<String> DB_List() {
        Result_Log("상위클래스 메소드 DB_List : " + DATABASE_NAME);
        // 1. 배열 선언
        String[] dblist = activity_Context.databaseList();
        // 2. 배열 정렬
        Arrays.sort(dblist);
        // 3. ArrayList로 컨버트
        sql_DataList = new ArrayList<>(Arrays.asList(dblist));

        // Transaction시 Atomic commit과 Rollback 기능을 지원하는 journal 파일 제거
        for(Iterator<String> it = sql_DataList.iterator() ; it.hasNext() ; )
        {
            String value = it.next();
            if(value.endsWith("journal"))
            {
                it.remove();
            }
        }
        return sql_DataList;
    }
    //==================================================
    // DataBase Control Method End =====================
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
    }

}

