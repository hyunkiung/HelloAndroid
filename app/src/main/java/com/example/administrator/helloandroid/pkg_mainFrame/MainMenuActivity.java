package com.example.administrator.helloandroid.pkg_mainFrame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.example.administrator.helloandroid.R;
import com.example.administrator.helloandroid.pkg_ListView.item_useBaseAdapter;
import com.example.administrator.helloandroid.pkg_ListView.listView_01_Default;
import com.example.administrator.helloandroid.pkg_activity.ActivityExamActivity;
import com.example.administrator.helloandroid.pkg_activity.EditTextActivity;
import com.example.administrator.helloandroid.pkg_activity.FirstActivity;
import com.example.administrator.helloandroid.pkg_activity.FrameLayoutExamActivity;
import com.example.administrator.helloandroid.pkg_activity.SecondActivity;
import com.example.administrator.helloandroid.pkg_activity.TableLayoutExamActivity;
import com.example.administrator.helloandroid.pkg_event.TouchEventActivity;
import com.example.administrator.helloandroid.pkg_mission.Mission_193Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_271Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_332Page;
import com.example.administrator.helloandroid.pkg_mission.Mission_333Page;

public class MainMenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    //////////// 메뉴버튼1
    public void onButtonClick_Open1(View v) {
        Intent openIntent = new Intent(getApplicationContext(), FirstActivity.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼2
    public void onButtonClick_Open2(View v) {
        Intent openIntent = new Intent(getApplicationContext(), SecondActivity.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼3
    public void onButtonClick_Open3(View v) {
        Intent openIntent = new Intent(getApplicationContext(), TableLayoutExamActivity.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼4
    public void onButtonClick_Open4(View v) {
        Intent openIntent = new Intent(getApplicationContext(), FrameLayoutExamActivity.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼5
    public void onButtonClick_Open5(View v) {
        Intent openIntent = new Intent(getApplicationContext(), EditTextActivity.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼6
    public void onButtonClick_Open6(View v) {
        Intent openIntent = new Intent(getApplicationContext(), Mission_193Page.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼7
    public void onButtonClick_Open7(View v) {
        Intent openIntent = new Intent(getApplicationContext(), ActivityExamActivity.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼8
    public void onButtonClick_Open8(View v) {
        Intent openIntent = new Intent(getApplicationContext(), Mission_271Page.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼9
    public void onButtonClick_Open9(View v) {
        Intent openIntent = new Intent(getApplicationContext(), TouchEventActivity.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼10
    public void onButtonClick_Open10(View v) {
        Intent openIntent = new Intent(getApplicationContext(), Mission_332Page.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼11
    public void onButtonClick_Open11(View v) {
        Intent openIntent = new Intent(getApplicationContext(), Mission_333Page.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼12
    public void onButtonClick_Open12(View v) {
        Intent openIntent = new Intent(getApplicationContext(), listView_01_Default.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼13
    public void onButtonClick_Open13(View v) {
        Intent openIntent = new Intent(getApplicationContext(), item_useBaseAdapter.class);
        startActivity(openIntent);
    }

    //////////// 메뉴버튼14
    public void onButtonClick_Open14(View v) {
    }

    //////////// 메뉴버튼15
    public void onButtonClick_Open15(View v) {
    }


}
