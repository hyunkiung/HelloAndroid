package com.example.administrator.helloandroid.pkg_mission;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;


public class Mission_333Page extends ActionBarActivity {

    ActionBar action_Bar;
    TableLayout mUrl_Table;
    Button mBtn_Pussy;
    Button mBtn_Move;
    WebView mUrl_View;
    TextView mUrl_Text;
    TextView mUrl_Left;
    TextView mUrl_Right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mission_333_page);

        //================================================================
        ////// 각 오브젝트 선언
        //================================================================
        mUrl_Table = (TableLayout)findViewById(R.id.url_Table);
        mBtn_Pussy = (Button)findViewById(R.id.btn_Pussy);
        mBtn_Move = (Button)findViewById(R.id.btn_Move);
        mUrl_View = (WebView)findViewById(R.id.url_View);
        mUrl_Text = (TextView)findViewById(R.id.url_Text);
        mUrl_Left = (TextView) findViewById(R.id.url_Left);
        mUrl_Right = (TextView) findViewById(R.id.url_Right);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("웅쓰 브라우저");

        //텍스트 박스 글씨 흐르게 해주기
        mUrl_Left.setSelected(true);
        mUrl_Right.setSelected(true);

        //================================================================
        ////// URL박스 컨트롤 버튼 셋팅 (보이기, 감추기)
        //================================================================
        mBtn_Pussy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUrl_Table.getVisibility() == View.VISIBLE) {
                    mUrl_Table.setVisibility(View.GONE);
                    mBtn_Pussy.setText("▼");
                    action_Bar.show();

                } else {
                    mUrl_Table.setVisibility(View.VISIBLE);
                    mBtn_Pussy.setText("▲");
                    action_Bar.hide();
                }
            }
        });

        //================================================================
        ////// URL 읽어서 웹뷰에 전달 버튼 셋팅, 웹뷰 셋팅
        //================================================================
        mBtn_Move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUrl_View.getSettings().setJavaScriptEnabled(true); // 자바스크립트 허용

                String strURL = mUrl_Text.getText().toString();
                // URL이 비어있을때 조건 추가할것.

                mUrl_View.getSettings().setBuiltInZoomControls(true);
                mUrl_View.getSettings().setSupportZoom(true);
                mUrl_View.loadUrl(strURL);

                // 별도의 클래스를 사용하는데 아래 메소드 사용하면 없어도 될듯.
                //mUrl_View.setWebViewClient(new WebViewClientClass());

                // 별도의 클래스 없이 바로 사용 가능
                 mUrl_View.setWebViewClient(new WebViewClient());
            }
        });

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
    ////// 이건 잘 모르겠다.
    //================================================================
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
