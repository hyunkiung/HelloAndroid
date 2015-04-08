package com.example.administrator.helloandroid.project_team;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HYUN.KI.UNG on 2015-04-07.
 */
public class TourList_DB_Helper {

    public String DATABASE_NAME = "TourList.db";
    public int DATABASE_VERSION = 1;
    public String DATABASE_PATH = "";

    private String TNAME_CODEMT = "TT_CODEMT";
    private String TNAME_LISTMT = "TT_LISTMT";
    private String TNAME_PHOTODT = "TT_PHOTODT";

    private String TCREATE_CODEMT = "create table if not exists "+ TNAME_CODEMT +
                                    " (" +
                                    "    _id integer PRIMARY KEY autoincrement" +
                                    "   ,key text" +
                                    "   ,value text" +
                                    "   ,wdt text" +
                                    " )";

    private String TCREATE_LISTMT = "create table if not exists "+ TNAME_LISTMT +
                                    " (" +
                                    "    _id integer PRIMARY KEY autoincrement" +
                                    "   ,title1 text" +
                                    "   ,title2 text" +
                                    "   ,title3 text" +
                                    "   ,content text" +
                                    "   ,weather text" +
                                    "   ,companion text" +
                                    "   ,location text" +
                                    "   ,tdt text" +
                                    "   ,wdt text" +
                                    "   ,edt text" +
                                    " )";

    private String TCREATE_PHOTODT = "create table if not exists "+ TNAME_PHOTODT +
                                    " (" +
                                    "    _id integer PRIMARY KEY autoincrement" +
                                    "   ,mid integer" +
                                    "   ,FullUrl text" +
                                    "   ,wdt text" +
                                    " )";


    private String sqlQuery = "";

    // DB 관련 객체 선언
    private DB_Helper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // 부가적인 객체들
    private Context activity_Context;

    private TourList_CODEMT_info CODEMT_infoClass;
    private ArrayList<TourList_CODEMT_info> CODEMT_List;
    private ArrayList<String> Helper_TABLE_List;

    public TourList_DB_Helper(Context context) {
        Result_Log("TourList_DB_Helper() 생성자");
        this.activity_Context = context;
        opener = new DB_Helper(context);
    }

    // Class Open
    public boolean open() throws SQLException {
        Result_Log("TourList_DB_Helper --> open()");
        db = opener.getWritableDatabase();
        DATABASE_VERSION = db.getVersion();
        DATABASE_PATH = db.getPath();
        return true;
    }

    // Class Close
    public void close() {
        Result_Log("TourList_DB_Helper --> close()");
        opener.close();
    }

    //==================================================
    // Table Control Method Start ======================
    //==================================================
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
    }

    // TT_CODEMT : Insert Data
    public long CODEMT_insertColumn(String key, String value, String wdt){
        Result_Log("CODEMT_insertColumn()" + key + value + wdt);
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        values.put("wdt", wdt);
        long addResult = db.insert(TNAME_CODEMT, null, values);
        return addResult;
    }

    // TT_CODEMT : Delete Data
    public int CODEMT_deleteColumn(int id){
        Result_Log("CODEMT_deleteColumn() : key = " + id);
        int delCount = db.delete(TNAME_CODEMT, "_id=" + id, null);
        return delCount;
    }

    // TT_CODEMT : Update Data
    public int CODEMT_updateColumn(int id, String key, String value, String wdt) {
        Result_Log("CODEMT_deleteColumn() : key = " + id);
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        values.put("wdt", wdt);
        int upCount = db.update(TNAME_CODEMT, values, "_id="+id, null);
        return upCount;
    }

    // TT_CODEMT : Select All Data
    public ArrayList<TourList_CODEMT_info> CODEMT_selectColumn_All(int sel_id) {
        Result_Log("CODEMT_selectColumn_All() : " + TNAME_CODEMT);
        CODEMT_List = new ArrayList<>();

        if(sel_id == 0) {
            sqlQuery = "select _id, key, value, wdt from " + TNAME_CODEMT;
        } else {
            sqlQuery = "select _id, key, value, wdt from " + TNAME_CODEMT + " where _id=" + sel_id;
        }
        Cursor cur = db.rawQuery(sqlQuery, null);

        if (cur.getCount() == 0) {
            //Helper_TABLE_List.add("NONE_DATA", "NONE_DATA", "NONE_DATA", "NONE_DATA");
        } else {
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                CODEMT_infoClass = new TourList_CODEMT_info(
                        cur.getInt(cur.getColumnIndex("_id")),
                        cur.getString(cur.getColumnIndex("key")),
                        cur.getString(cur.getColumnIndex("value")),
                        cur.getString(cur.getColumnIndex("wdt"))
                );
                CODEMT_List.add(CODEMT_infoClass);
                Result_Log("CODEMT_selectColumn_All() while == " + CODEMT_infoClass);
                cur.moveToNext();
            }
        }
        cur.close();
        return CODEMT_List;
    }
    //==================================================
    // Table Control Method End ========================
    //==================================================




    //=================================================
    // SQLiteOpenHelper Class Start ===================
    //=================================================
    public class DB_Helper extends SQLiteOpenHelper {

        public DB_Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Result_Log("DB_Helper --> onCreate()");
            try {
                db.execSQL(TCREATE_CODEMT);
                db.execSQL(TCREATE_LISTMT);
                db.execSQL(TCREATE_PHOTODT);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Result_Log("DB_Helper --> onUpgrade()");
            db.execSQL("DROP TABLE IF EXISTS " + TNAME_CODEMT);
            db.execSQL("DROP TABLE IF EXISTS " + TNAME_LISTMT);
            db.execSQL("DROP TABLE IF EXISTS " + TNAME_PHOTODT);
            onCreate(db);
            // 버젼 변경은 단일 DB만 사용할때 할것.
        }
    }
    //=================================================
    // SQLiteOpenHelper Class End =====================
    //=================================================

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) {
        Log.d("로그 : " , msg);
    }

    // 시스템 시간 리턴 메소드
    public String SystemTime_Now() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(new Date(System.currentTimeMillis()));
        return time;
    }



}
