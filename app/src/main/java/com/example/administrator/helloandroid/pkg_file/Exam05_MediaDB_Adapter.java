package com.example.administrator.helloandroid.pkg_file;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015-06-04.
 */
public class Exam05_MediaDB_Adapter extends BaseAdapter {

    private static String TAG = "로그 / File Exam5";
    private static void show_Log(String msg) { Log.d(TAG, msg); }

    // Layout을 가져오기 위한 객체
    private ArrayList<Exam05_MediaDB_info> mArrayList;
    private Context mContext;
    private CustomHolder mHolder;
    private int mSelPosition;
    private Date date = new Date();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");


    public Exam05_MediaDB_Adapter(Context context, ArrayList<Exam05_MediaDB_info> mAudioInfo, int mSelectedPosition) {
        mContext = context;
        mArrayList = mAudioInfo;
        mSelPosition = mSelectedPosition;
    }

    // ViewHolder 패턴
    private class CustomHolder {
        ImageView mIV_albumimage;
        TextView mTV_title;
        TextView mTV_artist;
        TextView mTV_size;
        TextView mTV_duration;
    }


    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_exam05_media_db_adapter, null);

            mHolder = new CustomHolder();
            mHolder.mIV_albumimage = (ImageView)v.findViewById(R.id.iv_albumimage);
            mHolder.mTV_title = (TextView)v.findViewById(R.id.tv_title);
            mHolder.mTV_artist = (TextView)v.findViewById(R.id.tv_artist);
            mHolder.mTV_size = (TextView)v.findViewById(R.id.tv_size);
            mHolder.mTV_duration = (TextView)v.findViewById(R.id.tv_duration);
            v.setTag(mHolder);
        }
        else {
            mHolder = (CustomHolder) v.getTag();
        }

        Exam05_MediaDB_info mAudioInfo = mArrayList.get(position);

        // 앨범 썸네일
        int albumid = mAudioInfo.getInfo_ALBUM_ID();
        AudioLoadBitmap audioLoadBitmap = new AudioLoadBitmap(mContext);
        audioLoadBitmap.loadBitmap(albumid, mHolder.mIV_albumimage);

        // 제목
        mHolder.mTV_title.setText(mAudioInfo.getInfo_DISPLAY_NAME());
        // 가수
        mHolder.mTV_artist.setText(mAudioInfo.getInfo_ARTIST());

        // 파일크기
        double numInt = mAudioInfo.getInfo_SIZE();
        numInt = numInt / 1048576;
        numInt = Math.floor(numInt * 100) / 100;
        mHolder.mTV_size.setText(numInt + "MB");

        // 재생시간
        int duration = (int) mAudioInfo.getInfo_DURATION();
        int min = duration / 60000;
        int sec = (duration % 60000) / 1000;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        mHolder.mTV_duration.setText(timeFormat.format(cal.getTime()));

        return v;
    }




}
