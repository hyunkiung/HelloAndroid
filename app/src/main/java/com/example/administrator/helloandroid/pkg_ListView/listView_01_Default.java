
package com.example.administrator.helloandroid.pkg_ListView;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

// 기본 ArrayAdapter를 사용한 연습

public class listView_01_Default extends ActionBarActivity {

    //================================================================
    ////// 선언 : 위젯_리스트뷰, 배열_데이터리스트, 어뎁터_어뎁터리스트
    //================================================================
    private ListView m_MyListView;
    private ArrayList<String> m_DataList;
    private ArrayAdapter<String> m_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_01__default);

        // 1.데이터 준비
        m_DataList = new ArrayList<>();
        for (int ii = 0; ii <= 20; ii++ ) {
            m_DataList.add("test adapter ArrayNumber :: " + ii);
        }

        // 2.어댑터 생성
        m_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, m_DataList);

        // 3.리스트뷰 생성후 어댑터 연결
        m_MyListView = (ListView) findViewById(R.id.myListView);
        m_MyListView.setAdapter(m_Adapter);



        // 5.리스트뷰에 클릭이벤트 추가
        m_MyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), m_Adapter.getItem(position), Toast.LENGTH_SHORT).show();

                Toast.makeText(
                        listView_01_Default.this,
                        "position : " + position + ", id : " + id + ", data->text : "
                                + m_DataList.get(position) + ", adapter->text : "
                                + m_Adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

        // 위5번 리스너 사용은 아래와 같이 분리할수 있다.
        //m_MyListView.setOnItemClickListener(onClick_List_Item);
    }

    // 분리된 아이템클릭 리스너
//    private AdapterView.OnItemClickListener onClick_List_Item = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(getApplicationContext(), m_Adapter.getItem(position), Toast.LENGTH_SHORT).show();
//        }
//    };

}
