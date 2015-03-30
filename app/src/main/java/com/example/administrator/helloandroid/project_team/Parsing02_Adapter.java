package com.example.administrator.helloandroid.project_team;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.List;

/**
 * Created by Administrator on 2015-03-30.
 */
public class Parsing02_Adapter extends BaseAdapter {

    private static final String TAG = "thread";
    private static Parsing05_Image DM = new Parsing05_Image();

    // Layout을 가져오기 위한 객체
    private LayoutInflater inflater;
    private Context mContext;
    private List<Parsing03_Info> mList;

    public Parsing02_Adapter(Context context, List<Parsing03_Info> list) {
        mContext = context;
        mList = list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            // View 를 처음 로딩할 때, Data 를 처음 셋팅할 때
            holder = new ViewHolder();
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_parsing02_list_view, null);

            ImageView mIV_image = (ImageView) view.findViewById(R.id.iv_thumbimage);
            TextView mTV_Title = (TextView) view.findViewById(R.id.tv_title);
            TextView mTV_author = (TextView) view.findViewById(R.id.tv_author);
            TextView mTV_updated = (TextView) view.findViewById(R.id.tv_updated);

            holder.H_image = mIV_image;
            holder.H_title = mTV_Title;
            holder.H_author = mTV_author;
            holder.H_updated = mTV_updated;
            view.setTag(holder);

        } else {
            // View, Data 재사용
            holder = (ViewHolder) view.getTag();
        }

        // position 위치의 데이터를 취득
        Parsing03_Info par_Info = (Parsing03_Info) getItem(position);
        String img_url = "http://img.youtube.com/vi/" + par_Info.getId() + "/2.jpg";
        String title = par_Info.getTitle();
        String author = par_Info.getAuthor();
        String updated = par_Info.getUpdated();

        // 출력 데이터 확인용 로그생성
        Log.d(TAG, title + " / " + author + " / " + updated);

        DM.fetchDrawableOnThread(img_url, holder.H_image);  //비동기 이미지 로더

        if (!TextUtils.isEmpty(title)) {
            holder.H_title.setText(title);
        }

        if (!TextUtils.isEmpty(author)) {
            holder.H_author.setText(author);
        }

        if (!TextUtils.isEmpty(updated)) {
            holder.H_updated.setText(updated);
        }
        return view;
    }

    // ViewHolder 패턴
    static class ViewHolder {
        ImageView H_image;
        TextView H_title;
        TextView H_author;
        TextView H_updated;
    }
}