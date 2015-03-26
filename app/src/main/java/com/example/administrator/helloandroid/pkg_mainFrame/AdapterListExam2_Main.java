
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

public class AdapterListExam2_Main extends ActionBarActivity implements AdapterView.OnItemClickListener {

    //===============================================================
    ////// 선언 : 위젯_리스트뷰, 배열_데이터리스트, 어뎁터_어뎁터리스트
    //===============================================================
    private ListView mPkg_ListView;
    private ArrayList<String> mPkg_ArrayList;
    private ArrayAdapter<String> mPkg_ArrayAdapter;
    private ActionBar action_Bar;
    private Intent mIntent;

    //===============================================================
    ////// 배열 값 셋팅 (패키지명)
    //===============================================================
    public static final String[] array_ITEMS = {
            "pkg_activity",
            "pkg_event",
            "pkg_adapter",
            "pkg_thread",
            "pkg_mission"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_list_exam1__main);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("안드로이드 실습2 패키지 리스트!");

        // 리스트뷰 설정
        mPkg_ListView = (ListView) findViewById(R.id.pkg_ListView);

        // 어레이어뎁터 설정, 미리 선언한 array_ITEMS 를 던졌다.
        mPkg_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, array_ITEMS);


        // 리스트뷰에 에러이어뎁터를 셋팅
        mPkg_ListView.setAdapter(mPkg_ArrayAdapter);

        // 리스트뷰의 원클릭 리스너 호출 (메인클래스에서 임플리먼트해서 this로 호출)
        mPkg_ListView.setOnItemClickListener(this);
    }

    // 자기 자신에게 임플리먼트된 AdapterView.OnItemClickListener의 실행 메소드
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mIntent = new Intent(getApplicationContext(), AdapterListExam2_Sub.class);
        mIntent.putExtra("menu", array_ITEMS[position]);
        startActivity(mIntent);
    }
}

