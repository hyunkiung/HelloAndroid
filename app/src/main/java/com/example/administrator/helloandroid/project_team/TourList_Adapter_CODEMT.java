package com.example.administrator.helloandroid.project_team;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class TourList_Adapter_CODEMT extends BaseAdapter {

    private ArrayList<TourList_info_CODEMT> m_List;
    private Context mContext;
    private CustomHolder holder;

    // ViewHolder 패턴
    private class CustomHolder {
        TextView mTV_id;
        TextView mTV_key;
        TextView mTV_value;
        TextView mTV_wdt;
        Button mBTN_record_del;
    }

    public TourList_Adapter_CODEMT(Context context, ArrayList<TourList_info_CODEMT> arrayList) {
        mContext = context;
        m_List = arrayList;
    }

    public void reset_ArrayList(ArrayList<TourList_info_CODEMT> arrayList){
        this.m_List = arrayList;
    }

    //===================================================== 오버라이드 메소드

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
            v = inflater.inflate(R.layout.activity_tourlist_adapter_codemt, null);
            holder.mTV_id = (TextView)v.findViewById(R.id.tv_id);
            holder.mTV_key = (TextView)v.findViewById(R.id.tv_key);
            holder.mTV_value = (TextView)v.findViewById(R.id.tv_value);
            holder.mTV_wdt = (TextView)v.findViewById(R.id.tv_wdt);
            holder.mBTN_record_del = (Button)v.findViewById(R.id.btn_record_del);

            v.setTag(holder);
        }
        else {
            holder = (CustomHolder) v.getTag();
        }

        holder.mTV_id.setText(String.valueOf(m_List.get(pos)._id));
        holder.mTV_key.setText(m_List.get(pos).key);
        holder.mTV_value.setText(m_List.get(pos).value);
        holder.mTV_wdt.setText(m_List.get(pos).wdt);

        holder.mBTN_record_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TourList_Table_CODEMT) mContext).CODEMT_ListView_Del(m_List.get(pos)._id);
            }
        });

        return v;
    }

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) {
        Log.d("로그 : ", msg);
    }

}
