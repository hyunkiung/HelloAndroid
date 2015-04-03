package com.example.administrator.helloandroid.pkg_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-03-25.
 *
 어뎁터 파일에서 생성자를 생성하는 타입이 이러하면
 public CalendarAdapter(Context context, List<Calendar> list) {
     mContext = context;
     mList = list;
 }

 액티비티 파일에서 생성자를 호출해서 던지는 방식이 이렇다.
 mCalendarAdapter = new CalendarAdapter(getApplicationContext(), mItems);
 */

public class GridView_ListView_Adapter extends BaseAdapter {

    private Context time_Context;
    private ArrayList<String> time_list;
    private LA_ViewHolder vi_Holder;

    // ViewHolder 패턴
    static class LA_ViewHolder {
        TextView mHolder_TV;
    }

    // 생성자.
    public GridView_ListView_Adapter(Context context, ArrayList<String> arrayList) {
        time_Context = context;
        time_list = arrayList;
    }


    @Override
    public int getCount() {
        return time_list.size();
    }

    @Override
    public Object getItem(int position) {
        return time_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // ListView에 뿌려질 한줄의 Row를 설정
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            vi_Holder = new LA_ViewHolder();
            LayoutInflater inflater = (LayoutInflater)time_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_grid_view_listview_cell, null);
            vi_Holder.mHolder_TV = (TextView)v.findViewById(R.id.tv_time_text);
            v.setTag(vi_Holder);
        }
        else {
            vi_Holder = (LA_ViewHolder) v.getTag();
        }

        vi_Holder.mHolder_TV.setText(getItem(position).toString());
        vi_Holder.mHolder_TV.setTextColor(Color.BLACK);
        vi_Holder.mHolder_TV.setLines(Color.BLUE);
        vi_Holder.mHolder_TV.setTextSize(15);

        return v;
    }

}