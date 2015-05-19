package com.example.administrator.helloandroid.pkg_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.helloandroid.R;

public class exam02_Main extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "로그 / fragment Exam2 Main";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    private int mCurrentFragmentIndex;
    public final static int FRAGMENT_ONE = 0;
    public final static int FRAGMENT_TWO = 1;
    public final static int FRAGMENT_THREE = 2;

    private Button bt_oneFragment;
    private Button bt_twoFragment;
    private Button bt_threeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exam02_main);

        bt_oneFragment = (Button) findViewById(R.id.bt_oneFragment);
        bt_twoFragment = (Button) findViewById(R.id.bt_twoFragment);
        bt_threeFragment = (Button) findViewById(R.id.bt_threeFragment);

        bt_oneFragment.setOnClickListener(this);
        bt_twoFragment.setOnClickListener(this);
        bt_threeFragment.setOnClickListener(this);

        mCurrentFragmentIndex = FRAGMENT_ONE; // Index = 0
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_oneFragment:
                mCurrentFragmentIndex = FRAGMENT_ONE;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.bt_twoFragment:
                mCurrentFragmentIndex = FRAGMENT_TWO;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.bt_threeFragment:
                mCurrentFragmentIndex = FRAGMENT_THREE;
                fragmentReplace(mCurrentFragmentIndex);
                break;

        }
    }

    public void fragmentReplace(int reqNewFragmentIndex) {

        show_Log("fragmentReplace " + reqNewFragmentIndex);

        // 프래그먼트 초기화 후 받은 인덱스 번호에 따라 프래그먼트 받아오기
        Fragment newFragment = null;
        newFragment = getFragment(reqNewFragmentIndex);

        // getSupportFragmentManager 비긴 트랜젝션
        /*
        예전 방식
        import android.app.FragmentTransaction;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        */
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 메인 xml에 있는 LinearLayout을 프래그먼트로 교체한다.
        transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
        transaction.replace(R.id.ll_fragment, newFragment);


        // 교체하는 트랜잭션은 백스택에 저장되고 사용자는 이 트랜잭션을 되돌릴 수 있게 되어
        // 결과적으로 백버튼을 눌러 이전 프레그먼트로 돌아올 수 있다.
        transaction.addToBackStack(null);

        // 트랜젝션을 커밋한다.
        transaction.commit();

    }

    // Fragment 인덱스번호에 맞게 프래그먼트 파일 리턴
    private Fragment getFragment(int idx) {
        Fragment newFragment = null;

        switch (idx) {
            case FRAGMENT_ONE:
                newFragment = new exam02_FragmentOne();
                break;
            case FRAGMENT_TWO:
                newFragment = new exam02_FragmentTwo();
                break;
            case FRAGMENT_THREE:
                newFragment = new exam02_FragmentThree();
                break;

            default:
                show_Log("알수 없는 Fragment 인덱스 번호");
                break;
        }

        return newFragment;
    }
}

/*
add(int, Fragment, String) : FragmentManager에 Fragment가 추가 된다.
	int : 컨테이너 ID로써 해당 fragment가 보여줄 부모 Layout ID가 보통 사용된다.
	Fragment: Activity에 더해질 fragment,
	String: tag 명이 들어가는데 추후 FragmentManager에서 tag명으로 fragment 를 찾아올 수 있다.
	        FragmentManager.findFragmentByTag(String)

remove(Fragment) : 해당 fragment가 존재할 경우 제거 한다. FragmentManager에서 더이상 찾을 수 없다.

hide(Fragment) : 해당 fragment가 존재할 경우 숨긴다. FragmentManager에 관리 되어지며, show를 통해 다시 보여줄 수 있다.

replace(int container, Fragment, String tag):
      container 안에 tag 명으로 존재하는 fragment를 remove(Fragment) 한 후에 같은 태그명으로
      Fragment를 add(int, Fragment, tag)를 호출한다.
      한마디로 container 안에 tag명 fragment를 remove 후에 add 한다.

detach(Fragment): 해당 fragment가 존재할 경우 떼어낸다.
      remove 와 다른 점은 remove의 경우 FragmentManager 안에서 완전히 제거되지만 detach 의 경우 stack에 존재한다.
      추후 attach 를 호출할 경우 다시 보여줄 수 있지만 View 계층에서 다시 생성되기 때문에 remove 와 별 차이가 없어 보인다.

attach(Fragment): 해당 Fragment 가 존재할 경우 다시 View 계층도에 붙인다.

addToBackStack() :
      백스택은 액티비티가 관리하는데, 백버튼을 눌렀을 때 사용자에게 이전 프레그먼트 상태로 되돌려줄 수 있도록 해준다.
      예를 들어 아래 코드는 하나의 프레그먼트를 다른 녀석으로 교체해주면서 백스택에 이전 상태를 저장하고 있다.

마지막으로 commit : 실행
*/
