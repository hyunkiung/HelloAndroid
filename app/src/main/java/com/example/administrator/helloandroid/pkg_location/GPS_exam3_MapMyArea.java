package com.example.administrator.helloandroid.pkg_location;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

public class GPS_exam3_MapMyArea extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static String TAG = "로그 / GPS Exam3 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private GoogleMap mGmap;
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Location mCurrentLocation;

    private boolean isCon = false;
    private boolean mRequestingLocationUpdates = false;

    private double mStrLatitude;
    private double mStrLongitude;
    private float mStrAccuracy;
    private String mLastUpdateTime;

    private GPS_exam3_CompassView mCompassView;
    private SensorManager mSensorManager;
    private boolean mCompassEnabled;

    private RelativeLayout mRlayout;
    private int iOrientation = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_exam3_map_my_area);

        mRlayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        mGmap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        // 센서 관리자 객체 참조
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        // 나침반을 표시할 뷰 생성
        boolean sideBottom = true;
        mCompassView = new GPS_exam3_CompassView(this);
        mCompassView.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(sideBottom ? RelativeLayout.ALIGN_PARENT_BOTTOM : RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(5, 150, 0, 0);
        mRlayout.addView(mCompassView, params);

        mCompassEnabled = true;

        GoogleApiClient_Build();
        mGoogleApiClient.connect();
        LocationRequest_Create();

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
            LocationService_Start();
        }

        // 내 위치 자동 표시 enable
        // setMyLocationEnabled : 내위치에 파란점과 내위치로 이동할수 있는 스코프 버튼이 생긴다.
        mGmap.setMyLocationEnabled(true);
        if(mCompassEnabled) {
            mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        }
    }

    // 화면 회전을 하면 onPause - onStop - onDestroy
    // 바탕화면 보기를 하면 onPause - onStop
    @Override
    protected void onPause() {
        super.onPause();
        ServiceStatus_Log("onPause");
        LocationRequest_UpadateStop();

        // 내 위치 자동 표시 disable
        mGmap.setMyLocationEnabled(false);
        if(mCompassEnabled) {
            mSensorManager.unregisterListener(mListener);
        }
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
    }

    protected void LocationRequest_Create() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void LocationRequest_Upadate() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void LocationRequest_UpadateStop() {
        mRequestingLocationUpdates = true;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        ServiceStatus_Log("stopLocationUpdates");
    }

    private void LocationService_Start() {
        if(isCon) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            show_Log("mGoogleApiClient : " + mLastLocation.toString());
        } else {
            LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            mLastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            show_Log("LOCATION_SERVICE / GPS_PROVIDER 로 접속");

            if(mLastLocation == null) {
                mLastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                show_Log("LOCATION_SERVICE / NETWORK_PROVIDER 로 접속");
            }
        }

        if (mLastLocation != null) {
            mStrLatitude = mLastLocation.getLatitude(); //경도
            mStrLongitude = mLastLocation.getLongitude(); //위도
            mStrAccuracy = mLastLocation.getAccuracy(); //신뢰도

        } else {
            show_Log("onConnected : mLastLocation Null");
        }
    }

    //==========================================================
    // GoogleApiClient.ConnectionCallbacks 오버라이드 메소드
    //==========================================================
    @Override
    public void onConnected(Bundle bundle) {
        isCon = true;
        LocationRequest_Upadate();
        ServiceStatus_Log("onConnected");
    }

    //==========================================================
    // GoogleApiClient.ConnectionCallbacks 오버라이드 메소드
    //==========================================================
    @Override
    public void onConnectionSuspended(int i) {
        show_Log("onConnectionSuspended");
    }

    //==========================================================
    // GoogleApiClient.OnConnectionFailedListener 오버라이드 메소드
    //==========================================================
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        isCon = false;
        show_Log("onConnectionFailed");
    }

    //==========================================================
    // LocationListener 오버라이드 메소드
    //==========================================================
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        ServiceStatus_Log("onLocationChanged : " + mLastUpdateTime);
        Location_Show(location.getLatitude(), location.getLongitude());
        //updateUI();
    }


    private void Location_Show(Double latitude, Double longitude) {
        // 현재 위치를 이용해 LatLng 객체 생성
        LatLng myPoint = new LatLng(latitude, longitude);
        double myPoingLat = myPoint.latitude;
        double myPoingLng = myPoint.longitude;

        // 해당위치로 애니매이션효과 적용 이동
        mGmap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(myPoingLat, myPoingLng)));

        // 해당위치 줌 효과
        mGmap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPoint, 15));

        // 지도 유형 설정.
        // 일반 지도인 경우에는 MAP_TYPE_NORMAL
        // 지형 지도인 경우에는 MAP_TYPE_TERRAIN
        // 위성 지도인 경우에는 MAP_TYPE_SATELLITE
        // 위성 지도인 경우에는 MAP_TYPE_HYBRID (Satellite 지도에 주요도시명 표기)
        mGmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 해당 위치에 옵션 마커 생성
        MarkerOptions myMarker = new MarkerOptions();
        myMarker.position(myPoint);
        myMarker.title("코난 여기있다");
        myMarker.snippet("졸리기 시작한다!!");
        myMarker.draggable(true);
        myMarker.visible(true);
        myMarker.alpha(0.9f);
        myMarker.flat(false);
        myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hku3));
        mGmap.addMarker(myMarker);

        LocationRequest_UpadateStop();

    }


    //==========================================================
    // SensorEventListener 오버라이드 메소드
    //==========================================================

    /**
     * 센서의 정보를 받기 위한 리스너 객체 생성
     */
    private final SensorEventListener mListener = new SensorEventListener() {
        private int iOrientation = -1;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        // 센서의 값을 받을 수 있도록 호출되는 메소드
        public void onSensorChanged(SensorEvent event) {
            if (iOrientation < 0) {
                iOrientation = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            }

            mCompassView.setAzimuth(event.values[0] + 90 * iOrientation);
            mCompassView.invalidate();
        }

    };


    //====================================================
    // 서비스 단계별로 상태 로그 출력 메소드
    //====================================================
    private void ServiceStatus_Log(String stopWhere) {
        String isC = String.valueOf(mGoogleApiClient.isConnected());
        String isU = String.valueOf(mRequestingLocationUpdates);
        show_Log(stopWhere + " : isConnected = " + isC + " / mRequestingLocationUpdates = " + isU);
    }
}
