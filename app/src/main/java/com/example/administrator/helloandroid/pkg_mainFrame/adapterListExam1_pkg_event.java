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
import com.example.administrator.helloandroid.pkg_event.TouchEventActivity;

import java.util.ArrayList;

public class adapterListExam1_pkg_event extends ActionBarActivity {

    //================================================================
    ////// 선언 : 위젯_리스트뷰, 배열_데이터리스트, 어뎁터_어뎁터리스트
    //================================================================
    private ListView m2_Pkg_ListView;
    private ArrayList<String> m2_Pkg_ArrayList;
    private ArrayAdapter<String> m2_Pkg_ArrayAdapter;
    private ActionBar action_Bar;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_list_exam1_pkg_event);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("pkg_event 액티비티 리스트");

        // 1. 데이터 준비
        m2_Pkg_ArrayList = new ArrayList<>();
        m2_Pkg_ArrayList.add("TouchEventActivity");


        // 2. 어뎁터 생성
        m2_Pkg_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, m2_Pkg_ArrayList);

        // 3.리스트뷰 생성후 어댑터 연결
        m2_Pkg_ListView = (ListView)findViewById(R.id.pkg_ListView);
        m2_Pkg_ListView.setAdapter(m2_Pkg_ArrayAdapter);

        // 4. 클릭이벤트 추가
        m2_Pkg_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        mIntent = new Intent(getApplicationContext(), TouchEventActivity.class);
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
