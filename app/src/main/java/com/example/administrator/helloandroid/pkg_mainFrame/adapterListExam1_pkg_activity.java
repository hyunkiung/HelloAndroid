
package com.example.administrator.helloandroid.pkg_mainFrame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.helloandroid.BuildConfig;
import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class adapterListExam1_pkg_activity extends ActionBarActivity {

    //================================================================
    ////// 선언 : 위젯_리스트뷰, 배열_데이터리스트, 어뎁터_어뎁터리스트
    //================================================================
    private ListView m1_Pkg_ListView;
    private ArrayList<String> m1_Pkg_ArrayList;
    private ArrayAdapter<String> m1_Pkg_ArrayAdapter;
    private ActionBar action_Bar;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_list_exam1_pkg_activity);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("pkg_activity 액티비티 리스트");

        // 1. 데이터 준비
        m1_Pkg_ArrayList = new ArrayList<>();
        m1_Pkg_ArrayList.add("ActivityExamActivity");
        m1_Pkg_ArrayList.add("EditTextActivity");
        m1_Pkg_ArrayList.add("FirstActivity");
        m1_Pkg_ArrayList.add("FrameLayoutExamActivity");
        m1_Pkg_ArrayList.add("RelativeLayoutExamActivity");
        m1_Pkg_ArrayList.add("SecondActivity");
        m1_Pkg_ArrayList.add("TableLayoutExamActivity");
        m1_Pkg_ArrayList.add("TargetExamActivity");


        // 2. 어뎁터 생성
        m1_Pkg_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, m1_Pkg_ArrayList);

        // 3.리스트뷰 생성후 어댑터 연결
        m1_Pkg_ListView = (ListView)findViewById(R.id.pkg_ListView);
        m1_Pkg_ListView.setAdapter(m1_Pkg_ArrayAdapter);

        // 4. 클릭이벤트 추가
        m1_Pkg_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Class c = null;
                try {
                    //c = Class.forName("com.suwonsmartapp.hello.activity." + m1_Pkg_ArrayList.get(position));
                    c = Class.forName(BuildConfig.APPLICATION_ID + ".pkg_activity." + m1_Pkg_ArrayList.get(position));
                    mIntent = new Intent(getApplicationContext(), c);
                    startActivity(mIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "에러에요", Toast.LENGTH_SHORT).show();
                }

//                switch (position) {
//                    case 0:
//                        mIntent = new Intent(getApplicationContext(), ActivityExamActivity.class);
//                        break;
//
//                    case 1:
//                        mIntent = new Intent(getApplicationContext(), EditTextActivity.class);
//                        break;
//
//                    case 2:
//                        mIntent = new Intent(getApplicationContext(), FirstActivity.class);
//                        break;
//
//                    case 3:
//                        mIntent = new Intent(getApplicationContext(), FrameLayoutExamActivity.class);
//                        break;
//
//                    case 4:
//                        mIntent = new Intent(getApplicationContext(), RelativeLayoutExamActivity.class);
//                        break;
//
//                    case 5:
//                        mIntent = new Intent(getApplicationContext(), SecondActivity.class);
//                        break;
//
//                    case 6:
//                        mIntent = new Intent(getApplicationContext(), TableLayoutExamActivity.class);
//                        break;
//
//                    case 7:
//                        mIntent = new Intent(getApplicationContext(), TargetExamActivity.class);
//                        break;
//
//                    default:
//                        break;
//                }
//
//                if(position >= 0) {
//                    startActivity(mIntent);
//                }
            }
        });
    }

}
