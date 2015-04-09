
package com.example.administrator.helloandroid.project_team;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class TourList_PHOTODT_Adapter extends BaseAdapter {

    private ArrayList<TourList_PHOTODT_info> m_List;
    private Context mContext;
    private CustomHolder holder;

    // ViewHolder 패턴
    private class CustomHolder {
        TextView mTV_id;
        TextView mTV_mid;
        TextView mTV_FullUrl;
        TextView mTV_wdt;
        TextView mBTN_record_del;
    }

    public TourList_PHOTODT_Adapter() {}

    public TourList_PHOTODT_Adapter(Context context, ArrayList<TourList_PHOTODT_info> arrayList) {
        mContext = context;
        m_List = arrayList;
    }

    public void reset_ArrayList(ArrayList<TourList_PHOTODT_info> arrayList){
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

    public int getItem_id(int position) {
        return m_List.get(position)._id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position; // 테이블 이름

        View v = convertView;
        if(v == null) {
            holder = new CustomHolder();
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_tourlist_photodt_adapter, null);
            holder.mTV_id = (TextView)v.findViewById(R.id.tv_id);
            holder.mTV_mid = (TextView)v.findViewById(R.id.tv_mid);
            holder.mTV_FullUrl = (TextView)v.findViewById(R.id.tv_FullUrl);
            holder.mTV_wdt = (TextView)v.findViewById(R.id.tv_wdt);
            holder.mBTN_record_del = (TextView)v.findViewById(R.id.btn_record_del);

            v.setTag(holder);
        }
        else {
            holder = (CustomHolder) v.getTag();
        }

        holder.mTV_id.setText(String.valueOf(m_List.get(pos)._id));
        holder.mTV_mid.setText(String.valueOf(m_List.get(pos).mid));
        holder.mTV_FullUrl.setText(m_List.get(pos).FullUrl);
        holder.mTV_wdt.setText(m_List.get(pos).wdt);

        holder.mBTN_record_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TourList_PHOTODT_Activity) mContext).PHOTODT_ListView_Del(m_List.get(pos)._id);
            }
        });

        return v;
    }

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) {
        Log.d("로그 : ", msg);
    }

}
