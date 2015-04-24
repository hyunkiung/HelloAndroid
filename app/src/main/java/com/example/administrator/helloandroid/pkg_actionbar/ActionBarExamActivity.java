package com.example.administrator.helloandroid.pkg_actionbar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.helloandroid.R;

public class ActionBarExamActivity extends ActionBarActivity {
/*
values -> styles.xml 에 아래 코드 추가. AppCompat 사용해야함.
<style name="AppTheme" parent="Base.Theme.AppCompat">
    <item name="colorPrimary">@color/hku_primary</item>
    <item name="colorPrimaryDark">@color/hku_primary_dark</item>
    <item name="colorAccent">@color/hku_accent</item>
</style>

롤리팝 이상 V21은 styles.xml(v21) 적용
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_exam);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar_exam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
