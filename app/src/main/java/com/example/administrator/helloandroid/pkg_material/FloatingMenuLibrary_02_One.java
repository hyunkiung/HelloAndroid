package com.example.administrator.helloandroid.pkg_material;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class FloatingMenuLibrary_02_One extends AppCompatActivity  {

    private static String TAG = "로그 / FloatingButton ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private FloatingActionMenu menu1;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_menu_library_02__one);

        menu1 = (FloatingActionMenu) findViewById(R.id.menu1);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab1.setOnClickListener(FabClickListener);
        fab2.setOnClickListener(FabClickListener);
        fab3.setOnClickListener(FabClickListener);
    }


    private View.OnClickListener FabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab1:
                    text = fab1.getLabelText();
                    break;
                case R.id.fab2:
                    text = fab2.getLabelText();
                    break;
                case R.id.fab3:
                    text = fab3.getLabelText();
                    break;
            }

            show_Toast(text);
        }
    };
}
