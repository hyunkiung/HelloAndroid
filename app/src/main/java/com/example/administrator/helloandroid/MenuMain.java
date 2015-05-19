
package com.example.administrator.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuMain extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //===============================================================
    ////// 선언 : 위젯_리스트뷰, 배열_데이터리스트, 어뎁터_어뎁터리스트
    //===============================================================
    private ListView mPkg_ListView;
    private ArrayAdapter<String> mPkg_ArrayAdapter;
    private ActionBar action_Bar;
    private Intent mIntent;

    //===============================================================
    ////// 배열 값 셋팅 (패키지명)
    //===============================================================
    public static final String[] array_NAMES = {
            "연습1 - 액티비티 (Activity)",
            "연습2 - 이벤트 (Event)",
            "연습3 - 아답터 (Adapter)",
            "연습4 - 스레드 (Thread)",
            "연습5 - 파싱 (Parsing)",
            "연습6 - 데이터베이스 (DB)",
            "연습7 - 네트워크통신 (Network)",
            "연습8 - 액션바 (ActionBar)",
            "연습9 - 멀티미디어 (MultiMedia)",
            "연습10 - 위치기반서비스 (Location)",
            "연습11 - 알림 서비스 (Notification)",
            "연습12 - 프래그먼트 (Fragment)",
            "연습13 - 결과값 (Activity Result, Intent)",
            "실습1 - 미션 (Mission)",
            "실습2 - API테스트 (API)",
            "실습3 - 팀프로젝트 (TourList)"
    };

    //===============================================================
    ////// 배열 값 셋팅 (클래스명)
    //===============================================================
    public static final String[] array_ITEMS = {
            "pkg_activity",
            "pkg_event",
            "pkg_adapter",
            "pkg_thread",
            "pkg_parsing",
            "pkg_db",
            "pkg_network",
            "pkg_actionbar",
            "pkg_multimedia",
            "pkg_location",
            "pkg_notice",
            "pkg_fragment",
            "pkg_result",
            "pkg_mission",
            "project_apiTest",
            "project_team"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewexam);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("안드로이드 실습 패키지 리스트!");

        // 리스트뷰 설정
        mPkg_ListView = (ListView) findViewById(R.id.myListView);

        // 어레이어뎁터 설정, 미리 선언한 array_ITEMS 를 던졌다.
        mPkg_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.simple_list_item_hku, getItems().first);


        // 리스트뷰에 에러이어뎁터를 셋팅
        mPkg_ListView.setAdapter(mPkg_ArrayAdapter);

        // 리스트뷰의 원클릭 리스너 호출 (메인클래스에서 임플리먼트해서 this로 호출)
        mPkg_ListView.setOnItemClickListener(this);
    }

    // ===============================================================
    // //// Main에서 던진 패키지명에 따라 배열값 셋팅
    // ===============================================================
    private Pair<String[], String[]> getItems() {
        Pair<String[], String[]> result = new Pair(array_NAMES, array_ITEMS);
        return result;
    }

    // 자기 자신에게 임플리먼트된 AdapterView.OnItemClickListener의 실행 메소드
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mIntent = new Intent(getApplicationContext(), MenuSub.class);
        mIntent.putExtra("menu", getItems().second[position]);
        startActivity(mIntent);
    }
}

