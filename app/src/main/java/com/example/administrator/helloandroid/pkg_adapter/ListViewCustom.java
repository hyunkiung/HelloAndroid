
package com.example.administrator.helloandroid.pkg_adapter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.example.administrator.helloandroid.R;

public class ListViewCustom extends ActionBarActivity {

    private ListView m_ListView;
    private ListViewCustom_Adapter m_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewexam);

        // 커스텀 어댑터 생성
        m_Adapter = new ListViewCustom_Adapter();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.myListView);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        // ListView에 아이템 추가
        m_Adapter.add("하스스톤");
        m_Adapter.add("몬스터 헌터");
        m_Adapter.add("디아블로");
        m_Adapter.add("와우");
        m_Adapter.add("리니지");
        m_Adapter.add("안드로이드");
        m_Adapter.add("아이폰");

    }

}
