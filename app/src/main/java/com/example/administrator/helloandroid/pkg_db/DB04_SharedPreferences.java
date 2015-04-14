package com.example.administrator.helloandroid.pkg_db;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

public class DB04_SharedPreferences extends ActionBarActivity implements View.OnClickListener {

    private static String FILE_NAME = "Test_Preferences2";
    private TextView mTV_Data;
    private boolean result_YN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db04_shared_preferences);

        mTV_Data = (TextView)findViewById(R.id.tv_Data);
        findViewById(R.id.btn_Create).setOnClickListener(this);
        findViewById(R.id.btn_OneDel).setOnClickListener(this);
        findViewById(R.id.btn_AllDel).setOnClickListener(this);

        AllRead_Preferences(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Create :
                result_YN = Create_Preferences();
                AllRead_Preferences(result_YN);
                break;
            case R.id.btn_OneDel :
                result_YN = OneDel_Preferences();
                AllRead_Preferences(result_YN);
                break;
            case R.id.btn_AllDel :
                result_YN =  AllDel_Preferences();
                AllRead_Preferences(result_YN);
                break;
        }

    }

    //=================================================================
    // 사용자 메소드
    //=================================================================


    // 값 저장하기
    private boolean Create_Preferences(){
        SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("data1", "반갑습니다.");
        editor.putString("data2", "안녕하세요.");
        editor.putString("data3", "오늘비온다.");
        editor.putString("data4", "오늘도춥다.");
        editor.putString("key는", "하나씩만 가능하다.");
        return editor.commit();
    }

    // 값(Key Data) 삭제하기
    private boolean OneDel_Preferences(){
        SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("data4");
        return editor.commit();
    }

    // 값(ALL Data) 삭제하기
    private boolean AllDel_Preferences(){
        SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        return editor.commit();
    }

    // 값 불러오기
    private void AllRead_Preferences(boolean yn){
        if(yn == true) {
            SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
            String pData = pref.getAll().toString();
            mTV_Data.setText(pData);
        } else {
            mTV_Data.setText(String.valueOf(yn));
        }
    }

}


/*
아이디나 String 문자열등 저장해야 할 경우 보통은 DB를 써야하는데 DB를 쓰기에는 너무 간단하거나 애매한 경우가 있다.
이럴때 사용할수 있는것중 SharedPreferences 가 있다.
SharedPreferences는 해당 프로세스(어플리케이션)내에 File 형태로 Data를 저장해 준다.
그리고 해당 어플리케이션이 삭제되기 전까지 Data를 보관해 주는 기능을 한다.

SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
"pref" = File 이름.
저장 위치 : data/data/패키지명/Shared_prefs/File 이름.xml
저장시 SharedPreferences 는 (Key, Value) 형태로 사용.
불러올때는 getString(key, value)를 설정한다.
key에 대한 값이 없을 경우 입력한 Value가 Default 값으로 설정된다.

앱 삭제시 SharedPreferences로 생성한 모든 파일이 삭제된다.
값을 저장하고 불러올때 SharedPreferences로 생선한 File 이름 틀리지 않게 주의.
get 메서드를 제외한 Data 저장(put), 삭제(remove, clear) 등을 할 경우에는 commit()을 꼭 호출해야한다.
폰에서 [환경설정] -> [SD 카드 데이터 ] -> [데이터 삭제] 시 SharedPreferences에 저장되 있는 값들은 모두 사라진다.

SharedPreferences 에서 생성한 파일은 앱삭제, 캐쉬삭제, 데이터 삭제 등의 행위를 하지 않으면 파일이 존재한다.
*/
