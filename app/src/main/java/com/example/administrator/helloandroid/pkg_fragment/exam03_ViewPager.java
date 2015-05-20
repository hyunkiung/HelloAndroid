package com.example.administrator.helloandroid.pkg_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class exam03_ViewPager extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "로그 / fragment Exam3 Main";
    public void show_Log(String msg) { Log.d(TAG, msg); }
    private static void show_Log_static(String msg) { Log.d(TAG, msg); }

    private ViewPager mViewPager;
    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam03_viewpager);

        mViewPager = (ViewPager)findViewById(R.id.viewPager);

        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.gtr1);
        data.add(R.drawable.gtr2);
        mMyAdapter = new MyAdapter(getSupportFragmentManager(), data);
        mViewPager.setAdapter(mMyAdapter);

        findViewById(R.id.btn_change_adapter).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        show_Log("클릭했음");
        ArrayList<Integer> data1 = new ArrayList<>();
        data1.add(R.drawable.mustang1);
        data1.add(R.drawable.mustang2);
        data1.add(R.drawable.mustang3);
        data1.add(R.drawable.mustang4);
        //mMyAdapter.setData 를 사용하면 데이터는 변경되어도 UI는 액션이 있어야 변경된다.
        //또한 현재 보고 있던 이미지뷰는 변경되지 않기 때문에 MyAdapter를 다시 생성한다.
        mMyAdapter = new MyAdapter(getSupportFragmentManager(), data1);
        mViewPager.setAdapter(mMyAdapter);
        mMyAdapter.notifyDataSetChanged();
    }

    //===================================================================
    //FragmentPagerAdapter를 확장한 MyAdapter 클래스
    //===================================================================
    public class MyAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Integer> mData;

        public MyAdapter(FragmentManager fm, ArrayList<Integer> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.getInstance(mData.get(position));
        }

        @Override
        public int getCount() {
            show_Log("MyAdapter getCount() : " + mData.size());
            return mData.size();
        }

        public void setData(ArrayList<Integer> data) {
            mData = data;
        }
    }


    //===================================================================
    //프래그먼트를 확장한 이미지뷰를 생성하는 클래스
    //===================================================================
    public static class ImageFragment extends Fragment {
        private ImageView mImageView;

        // 싱글턴 패턴
        public static Fragment getInstance(int resId) {
            ImageFragment fragment = new ImageFragment();
            Bundle args = new Bundle();
            args.putInt("resId", resId);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_exam03_image, container, false);
            mImageView = (ImageView)rootView.findViewById(R.id.iv_image);

            int resId = getArguments().getInt("resId");
            mImageView.setImageResource(resId);
            show_Log_static("Fragment onCreateView : " + resId);
            return rootView;
        }
    }
}
