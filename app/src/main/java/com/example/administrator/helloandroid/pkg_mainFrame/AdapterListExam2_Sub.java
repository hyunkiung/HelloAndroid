
package com.example.administrator.helloandroid.pkg_mainFrame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.helloandroid.R;
import com.example.administrator.helloandroid.pkg_activity.ActivityExamActivity;
import com.example.administrator.helloandroid.pkg_activity.EditTextActivity;
import com.example.administrator.helloandroid.pkg_activity.FirstActivity;
import com.example.administrator.helloandroid.pkg_activity.FrameLayoutExamActivity;
import com.example.administrator.helloandroid.pkg_activity.RelativeLayoutExamActivity;
import com.example.administrator.helloandroid.pkg_activity.SecondActivity;
import com.example.administrator.helloandroid.pkg_activity.TableLayoutExamActivity;
import com.example.administrator.helloandroid.pkg_activity.TargetExamActivity;
import com.example.administrator.helloandroid.pkg_adapter.GridView_Calendar;
import com.example.administrator.helloandroid.pkg_adapter.SpinnerExam;
import com.example.administrator.helloandroid.pkg_adapter.item_useBaseAdapter;
import com.example.administrator.helloandroid.pkg_adapter.listView_01_Default;
import com.example.administrator.helloandroid.pkg_event.TouchEventActivity;
import com.example.administrator.helloandroid.pkg_mission.Mission_193Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_271Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_332Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_333Page;

import java.util.ArrayList;

public class AdapterListExam2_Sub extends ActionBarActivity implements AdapterView.OnItemClickListener {

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
    private static final String[] array_ITEMS_pkg1_NAME = {
            "Activity 예제",
            "EditTextActivity",
            "FirstActivity",
            "FrameLayoutExamActivity",
            "RelativeLayoutExamActivity",
            "SecondActivity",
            "TableLayoutExamActivity",
            "TargetExamActivity"
    };
    private static final Class[] array_ITEMS_pkg1_CLASS = {
            ActivityExamActivity.class,
            EditTextActivity.class,
            FirstActivity.class,
            FrameLayoutExamActivity.class,
            RelativeLayoutExamActivity.class,
            SecondActivity.class,
            TableLayoutExamActivity.class,
            TargetExamActivity.class
    };

    private static final String[] array_ITEMS_pkg2_NAME = {
            "TouchEventActivity 예제"
    };
    private static final Class[] array_ITEMS_pkg2_CLASS = {
            TouchEventActivity.class
    };

    private static final String[] array_ITEMS_pkg3_NAME = {
            "listView_01_Default 예제",
            "item_useBaseAdapter 디버깅중",
            "Spinner 실습",
            "GridView로 달력만들기"
    };
    private static final Class[] array_ITEMS_pkg3_CLASS = {
            listView_01_Default.class,
            item_useBaseAdapter.class,
            SpinnerExam.class,
            GridView_Calendar.class
    };

    private static final String[] array_ITEMS_pkg4_NAME = {
            "Mission_193Page 실습",
            "Mission_271Page 실습",
            "Mission_332Page 실습",
            "Mission_333Page 실습"
    };
    private static final Class[] array_ITEMS_pkg4_CLASS = {
            Mission_193Page.class,
            Mission_271Page.class,
            Mission_332Page.class,
            Mission_333Page.class
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_list_exam1__main);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("안드로이드 실습2  리스트!");

        // 리스트뷰 설정
        mPkg_ListView = (ListView) findViewById(R.id.pkg_ListView);

        // 어레이어뎁터 설정, 미리 선언한 array_ITEMS 를 던졌다.
        mPkg_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getItems().first);


        // 리스트뷰에 에러이어뎁터를 셋팅
        mPkg_ListView.setAdapter(mPkg_ArrayAdapter);

        // 리스트뷰의 원클릭 리스너 호출 (메인클래스에서 임플리먼트해서 this로 호출)
        mPkg_ListView.setOnItemClickListener(this);
    }


    //===============================================================
    ////// Main에서 던진 패키지명에 따라 배열값 셋팅
    //===============================================================
    private Pair<String[], Class[]> getItems() {
        Pair<String[], Class[]> result = null;
        String menu = getIntent().getStringExtra("menu");
        switch (menu) {
            case "pkg_activity":
                result = new Pair(array_ITEMS_pkg1_NAME, array_ITEMS_pkg1_CLASS);
                break;
            case "pkg_event":
                result = new Pair(array_ITEMS_pkg2_NAME, array_ITEMS_pkg2_CLASS);
                break;
            case "pkg_adapter":
                result = new Pair(array_ITEMS_pkg3_NAME, array_ITEMS_pkg3_CLASS);
                break;
            case "pkg_mission":
                result = new Pair(array_ITEMS_pkg4_NAME, array_ITEMS_pkg4_CLASS);
                break;
            default:
                break;
        }
        return result;
    }

    // 자기 자신에게 임플리먼트된 AdapterView.OnItemClickListener의 실행 메소드
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mIntent = new Intent(getApplicationContext(), getItems().second[position]);
        startActivity(mIntent);
    }
}