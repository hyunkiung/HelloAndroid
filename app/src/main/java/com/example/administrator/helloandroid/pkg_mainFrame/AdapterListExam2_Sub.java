
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
import com.example.administrator.helloandroid.pkg_mission.Mission_392Page;
import com.example.administrator.helloandroid.pkg_thread.thread01_run;
import com.example.administrator.helloandroid.pkg_thread.thread02_progress;
import com.example.administrator.helloandroid.pkg_thread.thread03_Runnable;
import com.example.administrator.helloandroid.pkg_thread.thread04_AsyncTask;
import com.example.administrator.helloandroid.pkg_thread.thread05_Login;
import com.example.administrator.helloandroid.pkg_thread.thread06_StopWatch;
import com.example.administrator.helloandroid.pkg_thread.thread07_StopWatch;
import com.example.administrator.helloandroid.pkg_thread.thread08_DelayRunnable;
import com.example.administrator.helloandroid.pkg_thread.thread09_Looper;
import com.example.administrator.helloandroid.project_apiTest.Google_API_Map;
import com.example.administrator.helloandroid.project_team.DB01_Create;
import com.example.administrator.helloandroid.project_team.DB02_DB_SearchUseHelper;
import com.example.administrator.helloandroid.project_team.Parsing01_Main;
import com.example.administrator.helloandroid.project_team.xml_Parser_test;
import com.example.administrator.helloandroid.project_team.xml_json_test;

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
            "listView 기본 예제",
            "Custom BaseAdapter 연습",
            "Spinner 실습",
            "GridView, ListView 메모 달력"
    };
    private static final Class[] array_ITEMS_pkg3_CLASS = {
            listView_01_Default.class,
            item_useBaseAdapter.class,
            SpinnerExam.class,
            GridView_Calendar.class
    };

    private static final String[] array_ITEMS_pkg4_NAME = {
            "스레드와 핸들러 클래스의 기본 구조",
            "프로그래스바를 이용한 싱글, 멀티 Thread",
            "Runnable을 이용한 핸들러 처리",
            "AsyncTask를 사용한 프로그래스바",
            "AsyncTask를 사용한 Login 접속 로딩",
            "AsyncTask를 사용한 스탑워치(계산 느림)",
            "AsyncTask를 사용한 스탑워치(정상)",
            "Runnable을 이용한 실행 지연하기",
            "Looper 사용해보기"
    };
    private static final Class[] array_ITEMS_pkg4_CLASS = {
            thread01_run.class,
            thread02_progress.class,
            thread03_Runnable.class,
            thread04_AsyncTask.class,
            thread05_Login.class,
            thread06_StopWatch.class,
            thread07_StopWatch.class,
            thread08_DelayRunnable.class,
            thread09_Looper.class
    };

    private static final String[] array_ITEMS_pkg5_NAME = {
            "미션 193p 입력받은 문자 토스트",
            "미션 271p 인텐트, 다이얼로그",
            "미션 332p 데이트 피커",
            "미션 333p 브라우져, 애니메이션",
            "미션 392p 달력,해쉬맵"
    };
    private static final Class[] array_ITEMS_pkg5_CLASS = {
            Mission_193Page.class,
            Mission_271Page.class,
            Mission_332Page.class,
            Mission_333Page.class,
            Mission_392Page.class
    };

    private static final String[] array_ITEMS_pkg6_NAME = {
            "XmlPullParser 테스트",
            "Json Parser + ListView 테스트",
            "Json Parser + Adapter + ImageLoader \n(YouTube 리스트 구현)",
            "DataBase 테스트",
            "DataBase Helper Class 사용해보기"
    };
    private static final Class[] array_ITEMS_pkg6_CLASS = {
            xml_Parser_test.class,
            xml_json_test.class,
            Parsing01_Main.class,
            DB01_Create.class,
            DB02_DB_SearchUseHelper.class

    };

    private static final String[] array_ITEMS_pkg7_NAME = {
            "GoogleMap 테스트"

    };
    private static final Class[] array_ITEMS_pkg7_CLASS = {
            Google_API_Map.class

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_list_exam1__main);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("안드로이드 실습  리스트!");

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
            case "pkg_thread":
                result = new Pair(array_ITEMS_pkg4_NAME, array_ITEMS_pkg4_CLASS);
                break;
            case "pkg_mission":
                result = new Pair(array_ITEMS_pkg5_NAME, array_ITEMS_pkg5_CLASS);
                break;
            case "project_team":
                result = new Pair(array_ITEMS_pkg6_NAME, array_ITEMS_pkg6_CLASS);
                break;
            case "project_apiTest":
                result = new Pair(array_ITEMS_pkg7_NAME, array_ITEMS_pkg7_CLASS);
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
