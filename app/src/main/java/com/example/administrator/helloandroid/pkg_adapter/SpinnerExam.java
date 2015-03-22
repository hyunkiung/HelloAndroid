
package com.example.administrator.helloandroid.pkg_adapter;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class SpinnerExam extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private TextView mSpinner_txt1;
    private Spinner mSpinner1;
    private ActionBar action_Bar;
    private ArrayAdapter<String> mSpinner_ArrayAdapter;

    //===============================================================
    ////// 배열 값 셋팅 (패키지명)
    //===============================================================
    public static final String[] array_ITEMS = {
            "Red",
            "Blue",
            "Green",
            "Brown",
            "Black",
            "White",
            "Orange",
            "Yellow"
    };

    void showToast(CharSequence toast_msg) {
        Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_exam);

        //액션바 선언 및 타이틀 설정
        action_Bar = this.getSupportActionBar();
        action_Bar.setTitle("안드로이드 실습 스피너 연습!");

        // 텍스트뷰, 스피너 설정
        mSpinner_txt1 = (TextView) findViewById(R.id.spinner_txt1);
        mSpinner1 = (Spinner) findViewById(R.id.spinner1);

        // 리스트뷰의 아이템 셀렉트 리스너 호출 (메인클래스에서 임플리먼트해서 this로 호출)
        mSpinner1.setOnItemSelectedListener(this);

        // 어레이어뎁터 설정, 미리 선언한 array_ITEMS 를 던졌다.
        mSpinner_ArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, array_ITEMS);

        // 어레이어뎁터 설정, 드롭다운 형태 설정
        mSpinner_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 어뎁터 설정
        mSpinner1.setAdapter(mSpinner_ArrayAdapter);

    }

    //===============================================================
    ////// implements한 OnItemSelectedListener의 셀렉트 액션
    //===============================================================
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //mSpinner_txt1.setText(array_ITEMS[position]);
        showToast("Spinner1: position=" + position + " id=" + id);
    }

    //===============================================================
    ////// implements한 OnItemSelectedListener의 논셀렉트 액션
    //===============================================================
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //mSpinner_txt1.setText("");
        showToast("Nothing Selected");
    }

}
