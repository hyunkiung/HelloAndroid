
package com.example.administrator.helloandroid.pkg_mainFrame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class adapterListExam1_Main extends ActionBarActivity {

    //================================================================
    ////// 선언 : 위젯_리스트뷰, 배열_데이터리스트, 어뎁터_어뎁터리스트
    //================================================================
    private ListView mPkg_ListView;
    private ArrayList<String> mPkg_ArrayList;
    private ArrayAdapter<String> mPkg_ArrayAdapter;
    private ActionBar action_Bar;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_list_exam1__main);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("안드로이드 실습 패키지 리스트");

        // 1. 데이터 준비
        mPkg_ArrayList = new ArrayList<>();
        mPkg_ArrayList.add("pkg_activity");
        mPkg_ArrayList.add("pkg_event");
        mPkg_ArrayList.add("pkg_ListView");
        mPkg_ArrayList.add("pkg_mission");

        //패키지 정보 가져오기 packageName
        //String pnm = getPackageName();
        //mPkg_ArrayList.add(pnm);

        // 2. 어뎁터 생성
        mPkg_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, mPkg_ArrayList);

        // 3.리스트뷰 생성후 어댑터 연결
        mPkg_ListView = (ListView)findViewById(R.id.pkg_ListView);
        mPkg_ListView.setAdapter(mPkg_ArrayAdapter);

        // 4. 클릭이벤트 추가
        mPkg_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        mIntent = new Intent(getApplicationContext(), adapterListExam1_pkg_activity.class);
                        break;

                    case 1:
                        mIntent = new Intent(getApplicationContext(), adapterListExam1_pkg_event.class);
                        break;

                    case 2:
                        mIntent = new Intent(getApplicationContext(), adapterListExam1_pkg_ListView.class);
                        break;

                    case 3:
                        mIntent = new Intent(getApplicationContext(), adapterListExam1_pkg_mission.class);
                        break;

                    default:
                        break;
                }

                if(position >= 0) {
                    startActivity(mIntent);
                }
            }
        });
    }

}
