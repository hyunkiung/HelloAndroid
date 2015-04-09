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
import java.util.Locale;

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
                                    "   ,contents text" +
                                    "   ,weather text" +
                                    "   ,companion text" +
                                    "   ,location text" +
                                    "   ,pid integer default 0" +
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
    private ArrayList<String> Helper_TABLE_List;

    private TourList_CODEMT_info CODEMT_infoClass;
    private ArrayList<TourList_CODEMT_info> CODEMT_List;

    private TourList_LISTMT_info LISTMT_infoClass;
    private ArrayList<TourList_LISTMT_info> LISTMT_List;

    private TourList_PHOTODT_info PHOTODT_infoClass;
    private ArrayList<TourList_PHOTODT_info> PHOTODT_List;


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
        db.setLocale(Locale.KOREA); // SQLite 다국어처리 NO_LOCALIZED_COLLATORS
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
    //==================================================
    // Table Control Method End ========================
    //==================================================


    //==============================================================
    // Table Control TT_CODEMT Method Start ========================
    //==============================================================
    // TT_CODEMT : Insert Data
    public long CODEMT_insertColumn(String key, String value, String wdt){
        Result_Log("CODEMT_insertColumn() : " + key + value + wdt);
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        values.put("wdt", wdt);
        long addResult = db.insert(TNAME_CODEMT, null, values);
        return addResult;
    }

    // TT_CODEMT : Delete Data
    public int CODEMT_deleteColumn(int id){
        Result_Log("CODEMT_deleteColumn() : _id = " + id);
        int delCount = db.delete(TNAME_CODEMT, "_id =" + id, null);
        return delCount;
    }

    // TT_CODEMT : Update Data
    public int CODEMT_updateColumn(int id, String key, String value, String wdt) {
        Result_Log("CODEMT_deleteColumn() : _id = " + id);
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        values.put("wdt", wdt);
        int upCount = db.update(TNAME_CODEMT, values, "_id ="+id, null);
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
    //==============================================================
    // Table Control TT_CODEMT Method End ==========================
    //==============================================================


    //==============================================================
    // Table Control TT_LISTMT Method Start ========================
    //==============================================================
    // TT_LISTMT : Insert Data
    public long LISTMT_insertColumn(String title1, String title2, String title3, String contents, String weather, String companion, String location, String tdt, String wdt, String edt){
        Result_Log("LISTMT_insertColumn()" + title1 + title2 + title3);
        ContentValues values = new ContentValues();
        values.put("title1", title1);
        values.put("title2", title2);
        values.put("title3", title3);
        values.put("contents", contents);
        values.put("weather", weather);
        values.put("companion", companion);
        values.put("location", location);
        values.put("tdt", tdt);
        values.put("wdt", wdt);
        values.put("edt", edt);
        long addResult = db.insert(TNAME_LISTMT, null, values);
        return addResult;
    }

    // TT_LISTMT : Delete Data
    public int LISTMT_deleteColumn(int id){
        Result_Log("LISTMT_deleteColumn() : _id = " + id);
        int delCount = db.delete(TNAME_LISTMT, "_id =" + id, null);
        return delCount;
    }

    // TT_LISTMT : Update Data
    public int LISTMT_updateColumn(int id, String title1, String title2, String title3, String contents, String weather, String companion, String location, String tdt,String edt) {
        Result_Log("LISTMT_updateColumn() : _id = " + id);
        ContentValues values = new ContentValues();
        values.put("title1", title1);
        values.put("title2", title2);
        values.put("title3", title3);
        values.put("contents", contents);
        values.put("weather", weather);
        values.put("companion", companion);
        values.put("location", location);
        values.put("tdt", tdt);
        values.put("edt", edt);
        int upCount = db.update(TNAME_LISTMT, values, "_id = "+id, null);
        return upCount;
    }

    // TT_LISTMT : Update Data : 대표사진 수정 (pid)
    public int LISTMT_updatePhoto(int id, int pid) {
        Result_Log("LISTMT_updatePhoto() : _id = " + id + ", pid = " + pid);
        ContentValues values = new ContentValues();
        values.put("pid", pid);
        int upCount = db.update(TNAME_LISTMT, values, "_id = "+id, null);
        return upCount;
    }

    // TT_LISTMT : Select All Data
    public ArrayList<TourList_LISTMT_info> LISTMT_selectColumn_All(int sel_id) {
        Result_Log("LISTMT_selectColumn_All() : " + TNAME_LISTMT);
        LISTMT_List = new ArrayList<>();
        // pid로 PHOTODT 조인해서 사진 경로 추가할것
        if(sel_id == 0) {
            sqlQuery = " select a._id, a.title1, a.title2, a.title3, a.contents, a.weather, " +
                       "       a.companion, a.location, a.pid, a.tdt, a.wdt, a.edt, b.FullUrl as pFullUrl " +
                       " from " + TNAME_LISTMT + " a " +
                       " left join " + TNAME_PHOTODT + " b " +
                       " ON a.pid = b._id";
        } else {
            sqlQuery = " select a._id, a.title1, a.title2, a.title3, a.contents, a.weather, " +
                       "       a.companion, a.location, a.pid, a.tdt, a.wdt, a.edt, b.FullUrl as pFullUrl " +
                       " from " + TNAME_LISTMT + " a " +
                       " left join " + TNAME_PHOTODT + " b " +
                       " ON a.pid = b._id " +
                       " where a._id = " + sel_id;
        }
        Result_Log(sqlQuery);
        Cursor cur = db.rawQuery(sqlQuery, null);

        if (cur.getCount() == 0) {
            //Helper_TABLE_List.add("NONE_DATA", "NONE_DATA", "NONE_DATA", "NONE_DATA");
        } else {
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                LISTMT_infoClass = new TourList_LISTMT_info(
                        cur.getInt(cur.getColumnIndex("_id")),
                        cur.getString(cur.getColumnIndex("title1")),
                        cur.getString(cur.getColumnIndex("title2")),
                        cur.getString(cur.getColumnIndex("title3")),
                        cur.getString(cur.getColumnIndex("contents")),
                        cur.getString(cur.getColumnIndex("weather")),
                        cur.getString(cur.getColumnIndex("companion")),
                        cur.getString(cur.getColumnIndex("location")),
                        cur.getInt(cur.getColumnIndex("pid")),
                        cur.getString(cur.getColumnIndex("tdt")),
                        cur.getString(cur.getColumnIndex("wdt")),
                        cur.getString(cur.getColumnIndex("edt")),
                        cur.getString(cur.getColumnIndex("pFullUrl"))
                );
                LISTMT_List.add(LISTMT_infoClass);
                cur.moveToNext();
            }
        }
        cur.close();
        Result_Log("LISTMT_selectColumn_All() while == " + LISTMT_List);
        return LISTMT_List;
    }
    //==============================================================
    // Table Control TT_LISTMT Method End ==========================
    //==============================================================


    //==============================================================
    // Table Control TT_PHOTODT Method Start =======================
    //==============================================================
    // TT_PHOTODT : Insert Data
    public long PHOTODT_insertColumn(int mid, String fullurl, String wdt){
        Result_Log("PHOTODT_insertColumn()" + mid + fullurl + wdt);
        ContentValues values = new ContentValues();
        values.put("mid", mid);
        values.put("FullUrl", fullurl);
        values.put("wdt", wdt);
        long addResult = db.insert(TNAME_PHOTODT, null, values);
        return addResult;
    }

    // TT_PHOTODT : Delete Data
    public int PHOTODT_deleteColumn(int id){
        Result_Log("PHOTODT_deleteColumn() : _id = " + id);
        int delCount = db.delete(TNAME_PHOTODT, "_id=" + id, null);
        return delCount;
    }

    // TT_PHOTODT : Update Data
    public int PHOTODT_updateColumn(int id, int mid, String fullurl, String wdt) {
        Result_Log("PHOTODT_deleteColumn() : _id = " + id + ", mid=" + mid);
        ContentValues values = new ContentValues();
        values.put("mid", mid);
        values.put("FullUrl", fullurl);
        values.put("wdt", wdt);
        int upCount = db.update(TNAME_PHOTODT, values, "_id="+id, null);
        return upCount;
    }

    // TT_PHOTODT : Select All Data
    public ArrayList<TourList_PHOTODT_info> PHOTODT_selectColumn_All(int sel_id) {
        Result_Log("PHOTODT_selectColumn_All() : " + TNAME_PHOTODT);
        PHOTODT_List = new ArrayList<>();

        if(sel_id == 0) {
            sqlQuery = "select _id, mid, FullUrl, wdt from " + TNAME_PHOTODT;
        } else {
            sqlQuery = "select _id, mid, FullUrl, wdt from " + TNAME_PHOTODT + " where _id=" + sel_id;
        }
        Cursor cur = db.rawQuery(sqlQuery, null);

        if (cur.getCount() == 0) {
            //Helper_TABLE_List.add("NONE_DATA", "NONE_DATA", "NONE_DATA", "NONE_DATA");
        } else {
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                PHOTODT_infoClass = new TourList_PHOTODT_info(
                        cur.getInt(cur.getColumnIndex("_id")),
                        cur.getInt(cur.getColumnIndex("mid")),
                        cur.getString(cur.getColumnIndex("FullUrl")),
                        cur.getString(cur.getColumnIndex("wdt"))
                );
                PHOTODT_List.add(PHOTODT_infoClass);
                Result_Log("PHOTODT_selectColumn_All() while == " + PHOTODT_infoClass);
                cur.moveToNext();
            }
        }
        cur.close();
        return PHOTODT_List;
    }
    //==============================================================
    // Table Control TT_PHOTODT Method End =========================
    //==============================================================


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
