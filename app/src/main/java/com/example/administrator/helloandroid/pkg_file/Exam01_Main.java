package com.example.administrator.helloandroid.pkg_file;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

public class Exam01_Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_exam01_main);

        Exam01_FileList _FileList = new Exam01_FileList(this);
        _FileList.setOnPathChangedListener(_OnPathChanged);
        _FileList.setOnFileSelected(_OnFileSelected);

        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout01);
        layout.addView(_FileList);

        _FileList.setPath("/");
        _FileList.setFocusable(true);
        _FileList.setFocusableInTouchMode(true);

    }

    private Exam01_OnPathChangedListener _OnPathChanged = new Exam01_OnPathChangedListener() {
        @Override
        public void onChanged(String path) {
            ((TextView) findViewById(R.id.TextView01)).setText(path);
        }
    };

    private Exam01_OnFileSelectedListener _OnFileSelected = new Exam01_OnFileSelectedListener() {
        @Override
        public void onSelected(String path, String fileName) {
            // TODO
        }
    };

}
