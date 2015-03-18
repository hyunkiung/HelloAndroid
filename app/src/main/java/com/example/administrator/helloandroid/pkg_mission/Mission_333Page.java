package com.example.administrator.helloandroid.pkg_mission;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;


public class Mission_333Page extends ActionBarActivity {

    ActionBar action_Bar;
    TableLayout mUrl_Table;
    Button mBtn_Pussy;
    Button mBtn_Move;
    WebView mUrl_View;
    TextView mUrl_inText;
    TextView mUrl_Left;
    TextView mUrl_Right;
    Animation mTranslate_top_to_down;
    Animation mTranslate_down_to_top;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_333_page);

        //================================================================
        ////// 각 오브젝트 선언
        //================================================================
        mUrl_Table = (TableLayout)findViewById(R.id.url_Table);
        mBtn_Pussy = (Button)findViewById(R.id.btn_Pussy);
        mBtn_Move = (Button)findViewById(R.id.btn_Move);
        mUrl_View = (WebView)findViewById(R.id.url_View);
        mUrl_inText = (TextView)findViewById(R.id.url_inputText);
        mUrl_Left = (TextView) findViewById(R.id.url_Left);
        mUrl_Right = (TextView) findViewById(R.id.url_Right);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("웅쓰 브라우저");

        //텍스트 박스 글씨 흐르게 해주기
        mUrl_Left.setSelected(true);
        mUrl_Right.setSelected(true);

        // 애니메이션 객체 생성
        mTranslate_top_to_down = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_down);
        mTranslate_down_to_top = AnimationUtils.loadAnimation(this, R.anim.translate_down_to_top);

        //======================================================================================
        ////// URL박스 컨트롤 버튼 셋팅 (보이기, 감추기), 생성된 애니메이션객체를 감추는 오브젝트에 실행
        //======================================================================================
        mBtn_Pussy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUrl_Table.getVisibility() == View.VISIBLE) {
                    mUrl_Table.startAnimation(mTranslate_down_to_top);
                    mUrl_Table.setVisibility(View.GONE);
                    mBtn_Pussy.setText("▼");
                    action_Bar.show();

                } else {
                    mUrl_Table.startAnimation(mTranslate_top_to_down);
                    mUrl_Table.setVisibility(View.VISIBLE);
                    mBtn_Pussy.setText("▲");
                    action_Bar.hide();
                }
            }
        });

        //================================================================
        ////// 실행 버튼 클릭시 WebOpen() 호출
        //================================================================
        mBtn_Move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebOpen();
            }
        });

        //================================================================
        ////// 텍스트박스 엔터키 입력시 WebOpen() 호출
        //================================================================
        mUrl_inText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    WebOpen();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    //================================================================
    ////// 웹페이지 오픈 메소드 : URL 읽어서 웹뷰에 전달 버튼 셋팅, 웹뷰 셋팅
    //================================================================
    public void WebOpen() {

        String strURL = mUrl_inText.getText().toString();
        // URL이 비어있을때 조건 추가
        if(!strURL.equals("")) {
            mUrl_View.getSettings().setJavaScriptEnabled(true); // 자바스크립트 허용
            mUrl_View.getSettings().setBuiltInZoomControls(true); // 줌 컨트롤 허용
            mUrl_View.getSettings().setSupportZoom(true); // 슈퍼줌 허용
            mUrl_View.loadUrl(strURL); // url 로딩

            // 별도의 클래스를 사용하는데 아래 WebViewClient 메소드 사용하면 없어도 될듯.
            //mUrl_View.setWebViewClient(new WebViewClientClass());

            // 별도의 클래스 없이 바로 사용 가능
            mUrl_View.setWebViewClient(new WebViewClient());
        }
        else {
            Toast.makeText(getApplicationContext(), "주소를 입력해주세요.!!", Toast.LENGTH_SHORT).show();
        }

        //http://가 없을때 처리
    }


    //================================================================
    ////// BACK 버튼 인식시키기
    //================================================================
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mUrl_View.canGoBack()) {
            mUrl_View.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //================================================================
    ////// 이건 없어도 된다. WebViewClient가 기본제공. 이건 확장형 클래스
    //================================================================
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
