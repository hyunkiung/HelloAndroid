package com.example.administrator.helloandroid.pkg_actionbar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class CustomActionBar extends AppCompatActivity {

    private ActionBar action_Bar;   // 액션바

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 커스텀 액션바 : ";

    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_action_bar);

        // 액션바 선언
        action_Bar = this.getSupportActionBar();


        // 뷰 선언
        View v = getLayoutInflater().inflate(R.layout.activity_custom_bar, null);
        //액션바에 커스텀뷰를 설정
        if (action_Bar != null) {
            action_Bar.setDisplayShowCustomEnabled(true);
            action_Bar.setCustomView(R.layout.activity_custom_bar);
            action_Bar.setCustomView(v, new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT));
            setActionbarTitle();
        }
    }

    private void setActionbarTitle() {
        TextView mTV_title1 = (TextView) findViewById(R.id.tv_title1);
        TextView mTV_title2 = (TextView) findViewById(R.id.tv_title2);
        mTV_title1.setText("타이틀1");
        mTV_title2.setText("여기도 타이틀");
    }


    //================================================ 메뉴관련 오버라이드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_custom_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_menu1 :
                show_Toast("액션1111111111111");
                break;
            case R.id.action_menu2 :
                show_Toast("액션22222");
                break;
            case R.id.action_menu3 :
                show_Toast("액션333");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
