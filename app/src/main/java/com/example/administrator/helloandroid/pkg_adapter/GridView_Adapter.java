package com.example.administrator.helloandroid.pkg_adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

/**
 * Hyun Ki Ung on 2015-03-22
 */
public class GridView_Adapter extends ArrayAdapter<GridView_DayInfo> {

    // ViewHolder 패턴
    static class ViewHolder {
        TextView mHolderView;
    }

    // Layout을 가져오기 위한 객체
    private LayoutInflater inflater;

    public GridView_Adapter(Context context, int resource, ArrayList<GridView_DayInfo> dayList) {
        super(context, resource, dayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("CustomAdapter", "position : " + position);

        String TextColor;
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            // View 를 처음 로딩할 때, Data 를 처음 셋팅할 때
            inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_grid_view_calendar_cell, null);
            TextView mCalendar_cell = (TextView)view.findViewById(R.id.calendar_cell);

            holder = new ViewHolder();
            holder.mHolderView = mCalendar_cell;
            view.setTag(holder);
        } else {
            // View, Data 재사용
            holder = (ViewHolder) view.getTag();
        }

        // position 위치의 데이터를 취득
        GridView_DayInfo gDayInfo = getItem(position);
        String strWeek = gDayInfo.getWeek();
        String strLastDay = gDayInfo.getLastDay();
        String strDay = gDayInfo.getDay();
        String strNextDay = gDayInfo.getNextDay();

        // 요일 헤더 셋팅
        if (!TextUtils.isEmpty(strWeek)) {
            holder.mHolderView.setText(strWeek);
            holder.mHolderView.setBackgroundColor(Color.parseColor("#333333"));
            TextColor = WeekColor_Change(position);
            holder.mHolderView.setTextColor(Color.parseColor(TextColor));
        }

        // 지난달 추가 셀 셋팅
        if (!TextUtils.isEmpty(strLastDay)) {
            holder.mHolderView.setText(strLastDay);
            holder.mHolderView.setBackgroundColor(Color.parseColor("#777777"));
            holder.mHolderView.setTextColor(Color.parseColor("#AAAAAA"));
        }

        // 요일 셋팅
        if (!TextUtils.isEmpty(strDay)) {
            holder.mHolderView.setText(strDay);
            TextColor = WeekColor_Change(position);
            holder.mHolderView.setTextColor(Color.parseColor(TextColor));
        }

        // 다음달 추가 셀 셋팅
        if (!TextUtils.isEmpty(strNextDay)) {
            holder.mHolderView.setText(strNextDay);
            holder.mHolderView.setBackgroundColor(Color.parseColor("#777777"));
            holder.mHolderView.setTextColor(Color.parseColor("#AAAAAA"));
        }

        // 애니메이션 적용
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_left_to_right);
        view.findViewById(R.id.calendar_cell).startAnimation(animation);

        // 홀더 뷰 리턴
        return view;
    }

    //===============================================================
    ////// 토요일, 일요일 텍스트 색상 변경 메소드
    //===============================================================
    public String WeekColor_Change(int poNum) {
        String result_Color;
        if (poNum % 7 == 6) {
            result_Color = "#0000FF";
        }
        else if (poNum % 7 == 0) {
            result_Color = "#FF0000";
        }
        else {
            result_Color = "#FFFFFF";
        }
        return result_Color;
    }

}
