package com.example.administrator.helloandroid.pkg_location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

public class GPS_exam2_GoogleApiClient extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, LocationListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / GPS Exam2 ";
    private String REQUESTING_LOCATION_UPDATES_KEY = "REQUESTING_LOCATION_UPDATES_KEY";
    private String LOCATION_KEY = "LOCATION_KEY";
    private String LAST_UPDATED_TIME_STRING_KEY = "LAST_UPDATED_TIME_STRING_KEY";

    private void show_Log(String msg) { Log.d(TAG, msg); }

    // 메세지 토스트 메소드 (공용)
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }
    private GoogleApiClient mGoogleApiClient;

    private Button mBTN_gps;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    private TextView mAccuracyText;
    private TextView mLastUpTimeText;
    private TextView mWhatConText;
    private Location mLastLocation;


    private Location mCurrentLocation;
    private LocationRequest mLocationRequest;

    private boolean isCon = false;
    private boolean mRequestingLocationUpdates = false;
    private String mLastUpdateTime;
    private String mLastConnectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_exam2_google_api_client);

        mBTN_gps = (Button)findViewById(R.id.btn_gps);
        mLatitudeText = (TextView)findViewById(R.id.tv_Latitude);
        mLongitudeText = (TextView)findViewById(R.id.tv_Longitude);
        mAccuracyText = (TextView)findViewById(R.id.tv_Accuracy);
        mWhatConText = (TextView)findViewById(R.id.tv_WhatCon);
        mLastUpTimeText = (TextView)findViewById(R.id.tv_LastUpTime);
        mBTN_gps.setOnClickListener(this);

        buildGoogleApiClient();
        mGoogleApiClient.connect();
        createLocationRequest();

        ServiceStatus_Log("onCreate");
        updateValuesFromBundle(savedInstanceState);
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }


    //====================================================
    // GoogleApiClient 오버라이드 메소드
    //====================================================
    @Override
    public void onConnected(Bundle bundle) {
        isCon = true;
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
        startLocationUpdates();
        ServiceStatus_Log("onConnected");

    }

    @Override
    public void onConnectionSuspended(int i) {
        show_Toast("onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // 플레이서비스가 설치되어 있지 않은 경우
        isCon = false;
        show_Toast("onConnectionFailed");
    }


    //====================================================
    // GPS 시작 버튼 이벤트 메소드
    //====================================================
    @Override
    public void onClick(View view) {
        if(isCon) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastConnectType = mLastLocation.toString();
            mWhatConText.setText("mGoogleApiClient : " + mLastConnectType);
        } else {
            LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            mLastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mWhatConText.setText("LOCATION_SERVICE / GPS_PROVIDER 로 접속");

            if(mLastLocation == null) {
                mLastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                mWhatConText.setText("LOCATION_SERVICE / NETWORK_PROVIDER 로 접속");
            }

        }

        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude())); //경도
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude())); //위도
            mAccuracyText.setText(String.valueOf(mLastLocation.getAccuracy())); //신뢰도

        } else {
            mAccuracyText.setText("onConnected : mLastLocation Null");
        }

    }


    //====================================================
    // LocationListener 오버라이드 메소드
    //====================================================
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
    }

    private void updateUI() {
        mLastConnectType = mCurrentLocation.toString();
        mWhatConText.setText(mLastConnectType);
        mLatitudeText.setText(String.valueOf(mCurrentLocation.getLatitude()));
        mLongitudeText.setText(String.valueOf(mCurrentLocation.getLongitude()));
        mAccuracyText.setText(String.valueOf(mCurrentLocation.getAccuracy()));
        mLastUpTimeText.setText(mLastUpdateTime);
        show_Log(mLastConnectType);
    }

    protected void stopLocationUpdates() {
        mRequestingLocationUpdates = true;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        ServiceStatus_Log("stopLocationUpdates");
    }

    //====================================================
    // 저장된 인스턴스 상태를 출력하는 메소드
    //====================================================
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY);
                //setButtonsEnabledState();
            }

            // Update the value of mCurrentLocation from the Bundle and update the
            // UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that
                // mCurrentLocationis not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
            updateUI();
            ServiceStatus_Log("updateValuesFromBundle");
        }
    }


    //====================================================
    // 액티비티 오버라이드 메소드
    //====================================================
    @Override
    public void onResume() {
        super.onResume();
        mRequestingLocationUpdates = false;
        ServiceStatus_Log("onResume");

        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    // 화면 회전을 하면 onPause - onStop - onDestroy
    // 바탕화면 보기를 하면 onPause - onStop
    @Override
    protected void onPause() {
        super.onPause();
        ServiceStatus_Log("onPause");
        stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        show_Log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        show_Log("onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        show_Log("onBackPressed");
    }


    // 액티비티의 상태를 저장 및 복원하는 콜백 메소드
    // 복원시에는 onCreate한테 번들을 주는거야
    // onCreate에는 받은 번들에서 데이터를 꺼내는 메소드가 추가로 필요하겠지
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
        ServiceStatus_Log("onSaveInstanceState");
    }


    //====================================================
    // 서비스 단계별로 상태 로그 출력 메소드
    //====================================================
    private void ServiceStatus_Log(String stopWhere) {
        String isC = String.valueOf(mGoogleApiClient.isConnected());
        String isU = String.valueOf(mRequestingLocationUpdates);
        show_Log(stopWhere + " : isConnected = " + isC + " / mRequestingLocationUpdates = " + isU);
    }
}
