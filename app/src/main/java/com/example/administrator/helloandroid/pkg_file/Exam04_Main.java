package com.example.administrator.helloandroid.pkg_file;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

public class Exam04_Main extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, ResultCallback<LocationSettingsResult>, GoogleApiClient.OnConnectionFailedListener {

    private static String TAG = "로그 / File Exam4";
    private static void show_Log(String msg) { Log.d(TAG, msg); }

    private long startTime = -1;
    private Location beforeLocation;
    private Location curLocation;
    private TextView mTV_Message;

    private GoogleApiClient mGoogleApiClient;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest; // 리스너에서 요청해서 받은 위치 정보
    private boolean mLocationUP = false; // 위치갱신 업데이트 상태 플래그
    protected LocationSettingsRequest mLocationSettingsRequest; //LocationSettingsRequest.Builder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_exam04_main);

        mTV_Message = (TextView)findViewById(R.id.tv_message);

        GoogleApiClient_Build();
        LocationRequest_Create();
        LocationRequest_GPS_SettingCreate();

        LocationSetting_PendingResult(); // 위치정보(GPS) 활성화 요청 다이얼로그 생성
        SpeedStart();
    }

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

    protected void LocationRequest_GPS_SettingCreate() {
        show_Log("LocationRequest_GPS_SettingCreate");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void LocationSetting_PendingResult() {
        show_Log("LocationSetting_PendingResult");
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    public void SpeedStart() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        String provider = mLocationManager.getBestProvider(criteria, true);

        if (!mLocationManager.isProviderEnabled(provider)
                && mLocationManager.getLastKnownLocation(provider) != null) {
            mLocationManager.requestLocationUpdates(provider, 1000, 10, this);
        } else {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            provider = mLocationManager.getBestProvider(criteria, true);
            mLocationManager.requestLocationUpdates(provider, 1000, 10, this);
        }

        if(beforeLocation == null) {
            beforeLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            show_Log("LOCATION_SERVICE mGoogleApiClient = " + beforeLocation);
        }

        if(beforeLocation == null) {
            beforeLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            show_Log("LOCATION_SERVICE / GPS_PROVIDER = " + beforeLocation);
        }

        if(beforeLocation == null) {
            beforeLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            show_Log("LOCATION_SERVICE / NETWORK_PROVIDER = " + beforeLocation);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if(beforeLocation == null) {
            beforeLocation = location;
        }
        show_Log("onLocationChanged beforeLocation = " + beforeLocation);


        // GPS 변경에 따른 코딩 구현.
        if (startTime == -1) {
            startTime = location.getTime();
            show_Log("onLocationChanged startTime = " + startTime);
        }

        // 현재 위치 거리 및 속도 구하기.
        float distance[] = new float[1];
        Location.distanceBetween(beforeLocation.getLatitude(), beforeLocation.getLongitude(),
                location.getLatitude(), location.getLongitude(), distance);

        long delay = location.getTime() - startTime;
        double speed = distance[0]/delay;
        double speedKMH = speed * 3600;
        show_Log("delay = " + delay + " / speed = " + speed + " / speedKMH = " + speedKMH);
        mTV_Message.setText("속도 : " + String.valueOf(speedKMH));

        // 전 위치 저장.


        beforeLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }



    // google api 오버라이드
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    //result.setResultCallback(this); 오버라이드
    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {

    }

    //addOnConnectionFailedListener(this) 오버라이드
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
