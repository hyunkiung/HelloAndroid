
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

public class TourList_LISTMT_Adapter extends BaseAdapter {

    private ArrayList<TourList_LISTMT_info> m_List;
    private Context mContext;
    private CustomHolder holder;

    // ViewHolder 패턴
    private class CustomHolder {
        TextView mTV_id;
        TextView mTV_title1;
        TextView mTV_title2;
        TextView mTV_title3;
        TextView mTV_contents;
        TextView mTV_weather;
        TextView mTV_companion;
        TextView mTV_location;
        TextView mTV_pid;
        TextView mTV_tdt;
        TextView mTV_wdt;
        TextView mTV_edt;
        TextView mBTN_record_del;
    }

    public TourList_LISTMT_Adapter() {}

    public TourList_LISTMT_Adapter(Context context, ArrayList<TourList_LISTMT_info> arrayList) {
        mContext = context;
        m_List = arrayList;
    }

    public void reset_ArrayList(ArrayList<TourList_LISTMT_info> arrayList){
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
            v = inflater.inflate(R.layout.activity_tourlist_listmt_adapter, null);
            holder.mTV_id = (TextView)v.findViewById(R.id.tv_id);
            holder.mTV_title1 = (TextView)v.findViewById(R.id.tv_title1);
            holder.mTV_title2 = (TextView)v.findViewById(R.id.tv_title2);
            holder.mTV_title3 = (TextView)v.findViewById(R.id.tv_title3);
            holder.mTV_contents = (TextView)v.findViewById(R.id.tv_contents);
            holder.mTV_weather = (TextView)v.findViewById(R.id.tv_weather);
            holder.mTV_companion = (TextView)v.findViewById(R.id.tv_companion);
            holder.mTV_location = (TextView)v.findViewById(R.id.tv_location);
            holder.mTV_pid = (TextView)v.findViewById(R.id.tv_pid);
            holder.mTV_tdt = (TextView)v.findViewById(R.id.tv_tdt);
            holder.mTV_wdt = (TextView)v.findViewById(R.id.tv_wdt);
            holder.mTV_edt = (TextView)v.findViewById(R.id.tv_edt);
            holder.mBTN_record_del = (TextView)v.findViewById(R.id.btn_record_del);

            v.setTag(holder);
        }
        else {
            holder = (CustomHolder) v.getTag();
        }

        holder.mTV_id.setText(String.valueOf(m_List.get(pos)._id));
        //Toast.makeText(mContext, m_List.get(pos).title1.toString(), Toast.LENGTH_SHORT).show();
        holder.mTV_title1.setText(m_List.get(pos).title1.toString());
        holder.mTV_title2.setText(m_List.get(pos).title2.toString());
        holder.mTV_title3.setText(m_List.get(pos).title3.toString());
        holder.mTV_contents.setText(m_List.get(pos).contents.toString());
        holder.mTV_weather.setText(m_List.get(pos).weather.toString());
        holder.mTV_companion.setText(m_List.get(pos).companion.toString());
        holder.mTV_location.setText(m_List.get(pos).location.toString());
        holder.mTV_pid.setText(String.valueOf(m_List.get(pos).pid));
        holder.mTV_tdt.setText(m_List.get(pos).tdt.toString());
        holder.mTV_wdt.setText(m_List.get(pos).wdt.toString());
        holder.mTV_edt.setText(m_List.get(pos).edt.toString());

        holder.mBTN_record_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TourList_LISTMT_Activity) mContext).LISTMT_ListView_Del(m_List.get(pos)._id);
            }
        });

        return v;
    }

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) {
        Log.d("로그 : ", msg);
    }

}
