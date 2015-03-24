package com.example.administrator.helloandroid.pkg_mission;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2015-03-24.
 */
public class Mission_392Page_adapter extends BaseAdapter {

    private Context mContext;
    private List<Calendar> mList;

    private int mSel_Position = -1;

    // Layout을 가져오기 위한 객체
    private LayoutInflater inflater;

    // ViewHolder 패턴
    static class ViewHolder {
        TextView dateView;
    }

    // 이 생성자가 자동으로 안생겼다.
    public Mission_392Page_adapter(Context context, List<Calendar> list) {
        mContext = context;
        mList = list;
    }

    public int get_SelectedCell() {
        return mSel_Position;
    }

    public void set_SelectedCell(int click_Cell) {
        mSel_Position = click_Cell;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 셀을 만들어주는 겟뷰
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            // View 를 처음 로딩할 때, Data 를 처음 셋팅할 때
            inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_grid_view_calendar_cell, null);
            TextView DV = (TextView) convertView.findViewById(R.id.calendar_cell);

            holder.dateView = DV;
            convertView.setTag(holder);
        } else {
            // View, Data 재사용
            holder = (ViewHolder) convertView.getTag();
        }

        // position 위치의 데이터를 취득
        Calendar cal_date = (Calendar) getItem(position);

        if (cal_date == null) {
            holder.dateView.setText("");
        } else {
            String strDate = String.valueOf(cal_date.get(Calendar.DAY_OF_MONTH));
            holder.dateView.setText(strDate);
        }

        // 포지션별 글자색 변경
        if (position % 7 == 0) {
            holder.dateView.setTextColor(Color.RED);
        } else if (position % 7 == 6) {
            holder.dateView.setTextColor(Color.BLUE);
        } else {
            holder.dateView.setTextColor(Color.BLACK);
        }

        // 클릭한 셀 배경색 변경
        if (get_SelectedCell() == position) {
            holder.dateView.setBackgroundColor(Color.YELLOW);
        } else {
            holder.dateView.setBackgroundColor(Color.WHITE);
        }

        // 완성된 뷰 리턴
        return convertView;
    }
}
