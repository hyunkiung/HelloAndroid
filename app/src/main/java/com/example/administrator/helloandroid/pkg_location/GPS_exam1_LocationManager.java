package com.example.administrator.helloandroid.pkg_location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class GPS_exam1_LocationManager extends AppCompatActivity implements View.OnClickListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / GPS Exam1 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private TextView mTV_WhatCon;
    private TextView mTV_LastLocation;
    private TextView mTV_Latitude;
    private TextView mTV_Longitude;
    private TextView mTV_Accuracy;
    private Button mBTN_gps;
    private LocationManager mLmanager;
    private long minTime = 5000; //5초
    private float minDistance = 0; //1미터
    private GPSListener gpsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_exam1_location_manager);

        mTV_WhatCon = (TextView)findViewById(R.id.tv_WhatCon);
        mTV_LastLocation = (TextView)findViewById(R.id.tv_LastLocation);
        mTV_Latitude = (TextView)findViewById(R.id.tv_Latitude);
        mTV_Longitude = (TextView)findViewById(R.id.tv_Longitude);
        mTV_Accuracy = (TextView)findViewById(R.id.tv_Accuracy);

        mBTN_gps = (Button)findViewById(R.id.btn_gps);
        mBTN_gps.setOnClickListener(this);

        mLmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // GPS 사용 해제 (새로 할당하고 해제해야 에러메시지가 안나옴)
        //mLmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLmanager.removeUpdates(gpsListener);
    }

    @Override
    public void onClick(View view) {
        gpsListener = new GPSListener();
        mLmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);
        mLmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

        Location lastLocation = mLmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastLocation != null) {
            Double mLatitude = lastLocation.getLatitude(); // 경도
            Double mLongitude = lastLocation.getLongitude(); // 위도
            float mAccurac = lastLocation.getAccuracy(); // 신뢰도
            String txtLastLocation = "Last 경도 : " + mLatitude + " / 위도 : " + mLongitude + " / 신뢰도 : " + mAccurac;
            mTV_LastLocation.setText(txtLastLocation);
        } else {
            mTV_LastLocation.setText("마지막 위치정보가 없습니다");
        }
    }

    //=====================================================
    // LocationListener 리스너 클래스 정의
    //=====================================================
    private class GPSListener implements LocationListener {

        // 위치값 갱신시 이벤트 발생
        @Override
        public void onLocationChanged(Location location) {

            Double mLatitude = location.getLatitude(); // 경도
            Double mLongitude = location.getLongitude(); // 위도
            float mAccurac = location.getAccuracy(); // 신뢰도

            mTV_Latitude.setText("경도 : " + mLatitude);
            mTV_Longitude.setText("위도 : " + mLongitude);
            mTV_Accuracy.setText("신뢰도 : " + mAccurac);

            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                mTV_WhatCon.setText("접속방법 - GPS_PROVIDER");
            } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                mTV_WhatCon.setText("접속방법 - NETWORK_PROVIDER");
            } else {
                mTV_WhatCon.setText("접속방법 - ?????");
            }

            show_Log("GPSListener - onLocationChanged");
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            show_Log("GPSListener - onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String s) {
            show_Log("GPSListener - onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String s) {
            show_Log("GPSListener - onProviderDisabled");
        }
    }
}
