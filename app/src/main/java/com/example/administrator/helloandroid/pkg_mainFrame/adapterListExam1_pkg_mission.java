
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
import com.example.administrator.helloandroid.pkg_mission.Mission_193Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_271Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_332Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_333Page;

import java.util.ArrayList;

public class adapterListExam1_pkg_mission extends ActionBarActivity {


    //================================================================
    ////// 선언 : 위젯_리스트뷰, 배열_데이터리스트, 어뎁터_어뎁터리스트
    //================================================================
    private ListView m4_Pkg_ListView;
    private ArrayList<String> m4_Pkg_ArrayList;
    private ArrayAdapter<String> m4_Pkg_ArrayAdapter;
    private ActionBar action_Bar;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_list_exam1_pkg_mission);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("pkg_mission 액티비티 리스트");

        // 1. 데이터 준비
        m4_Pkg_ArrayList = new ArrayList<>();
        m4_Pkg_ArrayList.add("Mission_193Page");
        m4_Pkg_ArrayList.add("Mission_271Page");
        m4_Pkg_ArrayList.add("Mission_332Page");
        m4_Pkg_ArrayList.add("Mission_333Page");

        // 2. 어뎁터 생성
        m4_Pkg_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, m4_Pkg_ArrayList);

        // 3.리스트뷰 생성후 어댑터 연결
        m4_Pkg_ListView = (ListView)findViewById(R.id.pkg_ListView);
        m4_Pkg_ListView.setAdapter(m4_Pkg_ArrayAdapter);

        // 4. 클릭이벤트 추가
        m4_Pkg_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        mIntent = new Intent(getApplicationContext(), Mission_193Page.class);
                        break;

                    case 1:
                        mIntent = new Intent(getApplicationContext(), Mission_271Page.class);
                        break;

                    case 2:
                        mIntent = new Intent(getApplicationContext(), Mission_332Page.class);
                        break;

                    case 3:
                        mIntent = new Intent(getApplicationContext(), Mission_333Page.class);
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
