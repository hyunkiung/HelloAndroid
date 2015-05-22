
package com.example.administrator.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.helloandroid.pkg_actionbar.ActionBarExamActivity;
import com.example.administrator.helloandroid.pkg_actionbar.CustomActionBar;
import com.example.administrator.helloandroid.pkg_activity.ActivityExamActivity;
import com.example.administrator.helloandroid.pkg_activity.EditTextActivity;
import com.example.administrator.helloandroid.pkg_activity.FirstActivity;
import com.example.administrator.helloandroid.pkg_activity.FrameLayoutExamActivity;
import com.example.administrator.helloandroid.pkg_activity.RelativeLayoutExamActivity;
import com.example.administrator.helloandroid.pkg_activity.SecondActivity;
import com.example.administrator.helloandroid.pkg_activity.SeekBarCustomDesign;
import com.example.administrator.helloandroid.pkg_activity.TableLayoutExamActivity;
import com.example.administrator.helloandroid.pkg_activity.TargetExamActivity;
import com.example.administrator.helloandroid.pkg_adapter.GridView_Calendar;
import com.example.administrator.helloandroid.pkg_adapter.ListViewCustom;
import com.example.administrator.helloandroid.pkg_adapter.ListViewExam;
import com.example.administrator.helloandroid.pkg_adapter.SpinnerExam;
import com.example.administrator.helloandroid.pkg_db.DB01_Create;
import com.example.administrator.helloandroid.pkg_db.DB02_HelperActivity;
import com.example.administrator.helloandroid.pkg_db.DB03_TableActivity;
import com.example.administrator.helloandroid.pkg_db.DB04_SharedPreferences;
import com.example.administrator.helloandroid.pkg_db.DB05_FileSave;
import com.example.administrator.helloandroid.pkg_event.TouchEventActivity;
import com.example.administrator.helloandroid.pkg_file.Exam01_Main;
import com.example.administrator.helloandroid.pkg_fragment.exam01_main;
import com.example.administrator.helloandroid.pkg_fragment.exam02_Main;
import com.example.administrator.helloandroid.pkg_fragment.exam03_ViewPager;
import com.example.administrator.helloandroid.pkg_fragment.exam04_Main;
import com.example.administrator.helloandroid.pkg_fragment.exam05_Main;
import com.example.administrator.helloandroid.pkg_fragment.exam06_SlidingTab;
import com.example.administrator.helloandroid.pkg_fragment.exam07_ActionBarTab;
import com.example.administrator.helloandroid.pkg_location.GPS_exam1_LocationManager;
import com.example.administrator.helloandroid.pkg_location.GPS_exam2_GoogleApiClient;
import com.example.administrator.helloandroid.pkg_location.GPS_exam3_MapMyArea;
import com.example.administrator.helloandroid.pkg_mission.Mission_193Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_271Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_332Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_333Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_392Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_593Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_612Page;
import com.example.administrator.helloandroid.pkg_multimedia.Camera_Intent;
import com.example.administrator.helloandroid.pkg_multimedia.Camera_Surface;
import com.example.administrator.helloandroid.pkg_multimedia.MediaPlayer_Audio;
import com.example.administrator.helloandroid.pkg_multimedia.MediaPlayer_Audio_SDCARD;
import com.example.administrator.helloandroid.pkg_multimedia.MediaPlayer_Video;
import com.example.administrator.helloandroid.pkg_network.Local_Socket_Chat;
import com.example.administrator.helloandroid.pkg_network.Remocon_Client;
import com.example.administrator.helloandroid.pkg_network.Remote_Client;
import com.example.administrator.helloandroid.pkg_notice.Notice_exam1_Notification;
import com.example.administrator.helloandroid.pkg_notice.Notice_exam2_Notification2;
import com.example.administrator.helloandroid.pkg_parsing.Parsing01_Main;
import com.example.administrator.helloandroid.pkg_parsing.xml_Parser_test;
import com.example.administrator.helloandroid.pkg_parsing.xml_json_test;
import com.example.administrator.helloandroid.pkg_result.exam1_ActivityForResult;
import com.example.administrator.helloandroid.pkg_thread.thread01_run;
import com.example.administrator.helloandroid.pkg_thread.thread02_progress;
import com.example.administrator.helloandroid.pkg_thread.thread03_Runnable;
import com.example.administrator.helloandroid.pkg_thread.thread04_AsyncTask;
import com.example.administrator.helloandroid.pkg_thread.thread05_Login;
import com.example.administrator.helloandroid.pkg_thread.thread06_StopWatch;
import com.example.administrator.helloandroid.pkg_thread.thread07_StopWatch;
import com.example.administrator.helloandroid.pkg_thread.thread08_DelayRunnable;
import com.example.administrator.helloandroid.pkg_thread.thread09_Looper;
import com.example.administrator.helloandroid.project_apiTest.Daum_API_Map;
import com.example.administrator.helloandroid.project_apiTest.Google_API_Map;
import com.example.administrator.helloandroid.project_team.TourList_CODEMT_Activity;
import com.example.administrator.helloandroid.project_team.TourList_DB_Activity;
import com.example.administrator.helloandroid.project_team.TourList_LISTMT_Activity;
import com.example.administrator.helloandroid.project_team.TourList_PHOTODT_Activity;

import java.util.ArrayList;

public class MenuSub extends ActionBarActivity implements
        AdapterView.OnItemClickListener {

    // ===============================================================
    // //// 선언 : 위젯_리스트뷰, 배열_데이터리스트, 어뎁터_어뎁터리스트
    // ===============================================================
    private ListView mPkg_ListView;
    private ArrayList<String> mPkg_ArrayList;
    private ArrayAdapter<String> mPkg_ArrayAdapter;
    private ActionBar action_Bar;
    private Intent mIntent;

    // ===============================================================
    // //// 배열 값 셋팅 (패키지명)
    // ===============================================================
    private static final String[] array_ITEMS_pkg1_NAME = {
            "Activity 예제",
            "EditTextActivity",
            "FirstActivity",
            "FrameLayoutExamActivity",
            "RelativeLayoutExamActivity",
            "SecondActivity",
            "TableLayoutExamActivity",
            "TargetExamActivity",
            "SeekBar Custom Design"
    };
    private static final Class[] array_ITEMS_pkg1_CLASS = {
            ActivityExamActivity.class,
            EditTextActivity.class,
            FirstActivity.class,
            FrameLayoutExamActivity.class,
            RelativeLayoutExamActivity.class,
            SecondActivity.class,
            TableLayoutExamActivity.class,
            TargetExamActivity.class,
            SeekBarCustomDesign.class
    };

    private static final String[] array_ITEMS_pkg2_NAME = {
            "TouchEventActivity 예제"
    };

    private static final Class[] array_ITEMS_pkg2_CLASS = {
            TouchEventActivity.class
    };

    private static final String[] array_ITEMS_pkg3_NAME = {
            "ListView 기본 예제",
            "Spinner 기본 예제",
            "ListView Custom Adapter 연습",
            "GridView, ListView 메모 달력"
    };
    private static final Class[] array_ITEMS_pkg3_CLASS = {
            ListViewExam.class,
            SpinnerExam.class,
            ListViewCustom.class,
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
            "XmlPullParser 테스트",
            "Json Parser + ListView 테스트",
            "Json Parser + Adapter + ImageLoader \n(YouTube 리스트 구현)"
    };
    private static final Class[] array_ITEMS_pkg5_CLASS = {
            xml_Parser_test.class,
            xml_json_test.class,
            Parsing01_Main.class
    };

    private static final String[] array_ITEMS_pkg6_NAME = {
            "미션 193p 입력받은 문자 토스트",
            "미션 271p 인텐트, 다이얼로그",
            "미션 332p 데이트 피커",
            "미션 333p 브라우져, 애니메이션",
            "미션 392p 달력, 해쉬맵",
            "미션 593p 달력, DataBase",
            "미션 612p 동영상리스트, 재생"
    };
    private static final Class[] array_ITEMS_pkg6_CLASS = {
            Mission_193Page.class,
            Mission_271Page.class,
            Mission_332Page.class,
            Mission_333Page.class,
            Mission_392Page.class,
            Mission_593Page.class,
            Mission_612Page.class
    };

    private static final String[] array_ITEMS_pkg7_NAME = {
            "Google Map API 테스트",
            "Daum Map API 테스트"
    };
    private static final Class[] array_ITEMS_pkg7_CLASS = {
            Google_API_Map.class,
            Daum_API_Map.class
    };

    private static final String[] array_ITEMS_pkg8_NAME = {
            "DataBase 테스트",
            "DataBase 목록+컨트롤 use HelperClass",
            "Table 목록+컨트롤 use HelperClass",
            "SharedPreferences로 XML 파일 저장",
            "폰 내외부 저장소에 File 생성, 읽기, 삭제"
    };

    private static final Class[] array_ITEMS_pkg8_CLASS = {
            DB01_Create.class,
            DB02_HelperActivity.class,
            DB03_TableActivity.class,
            DB04_SharedPreferences.class,
            DB05_FileSave.class
    };

    private static final String[] array_ITEMS_pkg9_NAME = {
            "TourList DB, Table",
            "TourList Table CODEMT",
            "TourList Table LISTMT",
            "TourList Table PHOTODT"
    };
    private static final Class[] array_ITEMS_pkg9_CLASS = {
            TourList_DB_Activity.class,
            TourList_CODEMT_Activity.class,
            TourList_LISTMT_Activity.class,
            TourList_PHOTODT_Activity.class
    };

    private static final String[] array_ITEMS_pkg10_NAME = {
            "소켓통신1 - 로컬 서버, 클라이언트",
            "소켓통신2 - 원격 서버, 클라이언트",
            "소켓통신3 - 서버 리모콘 컨트롤"
    };
    private static final Class[] array_ITEMS_pkg10_CLASS = {
            Local_Socket_Chat.class,
            Remote_Client.class,
            Remocon_Client.class
    };

    private static final String[] array_ITEMS_pkg11_NAME = {
            "액션바 색상 바꾸기",
            "커스텀 액션바 만들기"
    };
    private static final Class[] array_ITEMS_pkg11_CLASS = {
            ActionBarExamActivity.class,
            CustomActionBar.class
    };

    private static final String[] array_ITEMS_pkg12_NAME = {
            "카메라 호출하기 (Intent)",
            "카메라 생성하기 (SurFaceView)",
            "플레이어 실습 (Audio Player)",
            "플레이어 실습 (Audio SD Card)",
            "플레이어 실습 (Video Player)"
    };
    private static final Class[] array_ITEMS_pkg12_CLASS = {
            Camera_Intent.class,
            Camera_Surface.class,
            MediaPlayer_Audio.class,
            MediaPlayer_Audio_SDCARD.class,
            MediaPlayer_Video.class
    };

    private static final String[] array_ITEMS_pkg13_NAME = {
            "exam1 - LocationManager",
            "exam2 - GoogleApiClient",
            "exam3 - MyArea on the Map"
    };
    private static final Class[] array_ITEMS_pkg13_CLASS = {
            GPS_exam1_LocationManager.class,
            GPS_exam2_GoogleApiClient.class,
            GPS_exam3_MapMyArea.class
    };

    private static final String[] array_ITEMS_pkg14_NAME = {
            "exam1 - Notification 기본",
            "exam2 - Notification 다양함"
    };
    private static final Class[] array_ITEMS_pkg14_CLASS = {
            Notice_exam1_Notification.class,
            Notice_exam2_Notification2.class
    };

    private static final String[] array_ITEMS_pkg15_NAME = {
            "exam1 - Fragment 버튼과 이미지 호출",
            "exam2 - 페이지 교체(트랜젝션, 커밋)",
            "exam3 - 뷰페이저 (어레이어뎁터)",
            "exam4 - MyViewPager Class",
            "exam5 - PagerTabStrip",
            "exam6 - PagerSlidingTabStrip",
            "exam7 - ActionBar NavigationMode"
    };
    private static final Class[] array_ITEMS_pkg15_CLASS = {
            exam01_main.class,
            exam02_Main.class,
            exam03_ViewPager.class,
            exam04_Main.class,
            exam05_Main.class,
            exam06_SlidingTab.class,
            exam07_ActionBarTab.class
    };

    private static final String[] array_ITEMS_pkg16_NAME = {
            "exam1 - ActivityForResult"
    };
    private static final Class[] array_ITEMS_pkg16_CLASS = {
            exam1_ActivityForResult.class
    };

    private static final String[] array_ITEMS_pkg17_NAME = {
            "exam1 - 기본적인 파일 관리"
    };
    private static final Class[] array_ITEMS_pkg17_CLASS = {
            Exam01_Main.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewexam);

        // 액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("안드로이드 실습  리스트!");

        // 리스트뷰 설정
        mPkg_ListView = (ListView) findViewById(R.id.myListView);

        // 어레이어뎁터 설정, 미리 선언한 array_ITEMS 를 던졌다.
        mPkg_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getItems().first);

        // 리스트뷰에 에러이어뎁터를 셋팅
        mPkg_ListView.setAdapter(mPkg_ArrayAdapter);

        // 리스트뷰의 원클릭 리스너 호출 (메인클래스에서 임플리먼트해서 this로 호출)
        mPkg_ListView.setOnItemClickListener(this);
    }

    // ===============================================================
    // //// Main에서 던진 패키지명에 따라 배열값 셋팅
    // ===============================================================
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
            case "pkg_parsing":
                result = new Pair(array_ITEMS_pkg5_NAME, array_ITEMS_pkg5_CLASS);
                break;
            case "pkg_mission":
                result = new Pair(array_ITEMS_pkg6_NAME, array_ITEMS_pkg6_CLASS);
                break;
            case "project_apiTest":
                result = new Pair(array_ITEMS_pkg7_NAME, array_ITEMS_pkg7_CLASS);
                break;
            case "pkg_db":
                result = new Pair(array_ITEMS_pkg8_NAME, array_ITEMS_pkg8_CLASS);
                break;
            case "project_team":
                result = new Pair(array_ITEMS_pkg9_NAME, array_ITEMS_pkg9_CLASS);
                break;
            case "pkg_network":
                result = new Pair(array_ITEMS_pkg10_NAME, array_ITEMS_pkg10_CLASS);
                break;
            case "pkg_actionbar":
                result = new Pair(array_ITEMS_pkg11_NAME, array_ITEMS_pkg11_CLASS);
                break;
            case "pkg_multimedia":
                result = new Pair(array_ITEMS_pkg12_NAME, array_ITEMS_pkg12_CLASS);
                break;
            case "pkg_location":
                result = new Pair(array_ITEMS_pkg13_NAME, array_ITEMS_pkg13_CLASS);
                break;
            case "pkg_notice":
                result = new Pair(array_ITEMS_pkg14_NAME, array_ITEMS_pkg14_CLASS);
                break;
            case "pkg_fragment":
                result = new Pair(array_ITEMS_pkg15_NAME, array_ITEMS_pkg15_CLASS);
                break;
            case "pkg_result":
                result = new Pair(array_ITEMS_pkg16_NAME, array_ITEMS_pkg16_CLASS);
                break;
            case "pkg_file":
                result = new Pair(array_ITEMS_pkg17_NAME, array_ITEMS_pkg17_CLASS);
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
