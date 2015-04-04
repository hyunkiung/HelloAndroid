package com.example.administrator.helloandroid.project_team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-04-04.
 */
public class DB02_HelperAdapter extends BaseAdapter {

    // 문자열을 보관 할 ArrayList
    private ArrayList<String> m_List;
    private Context mContext;
    private CustomHolder holder;

    // ViewHolder 패턴
    private class CustomHolder {
        TextView mTV_name;
        ImageButton mBtn_drop;
    }

    // 생성자
    public DB02_HelperAdapter(Context context, ArrayList<String> arrayList) {
        mContext = context;
        m_List = arrayList;
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position; // 테이블 이름

        View v = convertView;
        if(v == null) {
            holder = new CustomHolder();
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_db02_listview_cell, null);
            holder.mTV_name = (TextView)v.findViewById(R.id.tv_tlist_name);
            holder.mBtn_drop = (ImageButton)v.findViewById(R.id.btn_tlist_drop);
            v.setTag(holder);
        }
        else {
            holder = (CustomHolder) v.getTag();
        }

        holder.mTV_name.setText(m_List.get(pos));

        // 테이블명 클릭 이벤트
        holder.mTV_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, m_List.get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        // DROP 버튼 클릭 이벤트
        holder.mBtn_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "드랍 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

//    // 외부에서 아이템 추가 요청 시 사용
//    public void add(String _msg) {
//        m_List.add(_msg);
//    }
//
//    // 외부에서 아이템 삭제 요청 시 사용
//    public void remove(int _position) {
//        m_List.remove(_position);
//    }
}
