package com.example.administrator.helloandroid.pkg_location;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.administrator.helloandroid.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import java.text.DateFormat;
import java.util.Date;

/*
아래 나열한 순서대로 작동함.
LocationManager : 시스템의 위치기반 서비스 접근 지원 하는 객체 / 주기적으로 바뀌는 단말의 위치정보 수신
LocationListener : 위치정보를 전달 받기 위한 리스너
Location : 위치는 위도(latitude)와 경도(longitude)로, 시간은 UTCtimestamp 로 표시
           그외 선택적으로 고도(altitude),속도(speed)그리고 bearing정보 표시

 */

public class GPS_exam3_MapMyArea extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static String TAG = "로그 / GPS Exam3 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    private boolean mGPS_Enabled = false;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;

    private LocationManager mLocationManager; // LocationManager - NETWORK_PROVIDER 사용하여 내위치 확인
    private Location mNowLocation; // 현재 위치정보
    private LocationRequest mLocationRequest;

    private Location mCurrentLocation;

    private boolean isCon = false;
    private boolean mLocationUP = false;

    private double mStrLatitude;
    private double mStrLongitude;
    private float mStrAccuracy;
    private String mLastUpdateTime;

    private GPS_exam3_CompassView mCompassView;
    private SensorManager mSensorManager;
    private boolean mCompassEnabled;
    private RelativeLayout mRlayout;
    private Geocoder mGeocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_exam3_map_my_area);
        show_Log("onCreate : 시작");

        mRlayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // 01. GPS 활성화 체크
        mGPS_Enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!mGPS_Enabled) {
            GPS_SettingCheck();
        }

        GoogleApiClient_Build();
        mGoogleApiClient.connect();

        LocationRequest_Create();

        show_Log("onCreate : 끝");
    }


    //===================================================================
    // GoogleApi, Location 수행 관련 메소드
    //===================================================================
    protected synchronized void GoogleApiClient_Build() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        show_Log("synchronized GoogleApiClient_Build");
    }

    protected void LocationRequest_Create() {
        show_Log("LocationRequest_Create");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //====================================================
    // GPS 활성화 체크 메소드
    //====================================================
    private void GPS_SettingCheck() {
        show_Log("GPS 가동 여부 : " + mGPS_Enabled);
        new AlertDialog.Builder(this)
                .setTitle("GPS설정")
                .setMessage("GPS가 꺼져 있습니다. \nGPS를 활성화하시겠습니까?")
                .setNegativeButton("닫기", null)
                .setPositiveButton("GPS 켜기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                }).show();
    }


    //====================================================
    // 현재위치 찾기 시작
    //====================================================
    protected void NowLocationService_Start() {
        if(mNowLocation == null) {
            mNowLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            show_Log("LOCATION_SERVICE mGoogleApiClient = " + mNowLocation);
        }

        if(mNowLocation == null) {
            mNowLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            show_Log("LOCATION_SERVICE / GPS_PROVIDER = " + mNowLocation);
        }

        if(mNowLocation == null) {
            mNowLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            show_Log("LOCATION_SERVICE / NETWORK_PROVIDER = " + mNowLocation);
        }

        if (mNowLocation != null) {
            mStrLatitude = mNowLocation.getLatitude(); //경도
            mStrLongitude = mNowLocation.getLongitude(); //위도
            mStrAccuracy = mNowLocation.getAccuracy(); //신뢰도
            show_Log("LocationService_Start / mNowLocation 위치 정보 생성 완료");

        } else {
            show_Log("LocationService_Start / mNowLocation = NULL");
        }
    }
    // 현재위치 리스너 갱신 시작
    protected void NowLocationService_Update() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    // 현재위치 리스너 갱신 종료
    protected void NowLocationService_UpdateStop() {
        show_Log("NowLocationService_UpdateStop");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    //====================================================
    // 액티비티 오버라이드 메소드
    //====================================================
    @Override
    public void onResume() {
        super.onResume();
        show_Log("## Override onResume");

        if(mNowLocation == null) {
            NowLocationService_Start();
        } else if (mNowLocation != null) {

            NowLocationService_Update(); // 일단 업데이트는 막아놓자
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        show_Log("## Override onPause");
        if(mGoogleApiClient.isConnected()) {
            //mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        show_Log("@@ Override onDestroy");
    }

    //==========================================================
    // GoogleApiClient.ConnectionCallbacks 오버라이드 메소드
    //==========================================================
    @Override
    public void onConnected(Bundle bundle) {
        show_Log("Override onConnected : mNowLocation = " + mNowLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        show_Log("Override onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        show_Log("Override onConnectionFailed");
    }

    //==========================================================
    // LocationListener 오버라이드 메소드
    //==========================================================
    @Override
    public void onLocationChanged(Location location) {
        show_Log("Override onLocationChanged");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        String getLat = String.valueOf(location.getLatitude());
        String getLon = String.valueOf(location.getLongitude());

        show_Log("Override onLocationChanged " + getLat + "/" + getLon + "/" + mLastUpdateTime);
    }



    //==========================================================
    // SensorEventListener 오버라이드 메소드
    //==========================================================


    //====================================================
    // 서비스 단계별로 상태 로그 출력 메소드
    //====================================================
    private void ServiceStatus_Log(String stopWhere) {
        String isC = String.valueOf(mGoogleApiClient.isConnected());
        show_Log(stopWhere + "#GoogleApiClient = " + isC);
    }
}
