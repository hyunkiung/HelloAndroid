package com.example.administrator.helloandroid.pkg_location;

import android.content.Context;
import android.content.IntentSender;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.administrator.helloandroid.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
아래 나열한 순서대로 작동함.
LocationManager : 시스템의 위치기반 서비스 접근 지원 하는 객체 / 주기적으로 바뀌는 단말의 위치정보 수신
LocationListener : 위치정보를 전달 받기 위한 리스너
Location : 위치는 위도(latitude)와 경도(longitude)로, 시간은 UTCtimestamp 로 표시
           그외 선택적으로 고도(altitude),속도(speed)그리고 bearing정보 표시

 */

public class GPS_exam3_MapMyArea extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<LocationSettingsResult> {

    private static String TAG = "로그 / GPS Exam3 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;

    private LocationManager mLocationManager; // LocationManager - NETWORK_PROVIDER 사용하여 내위치 확인
    private Location mNowLocation; // 현재 위치정보
    private LocationRequest mLocationRequest; // 리스너에서 요청해서 받은 위치 정보
    private boolean mLocationUP = false; // 위치갱신 업데이트 상태 플래그
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    protected LocationSettingsRequest mLocationSettingsRequest; //LocationSettingsRequest.Builder
    private RelativeLayout mRlayout; // R.id.mainRelativeLayout

    private double mStrLatitude; // 경도
    private double mStrLongitude; // 위도
    private float mStrAccuracy;  // 신뢰도

    private String mChangeTime; // 변경된 시간
    private Location mChangeLocation; // 변경된 위치

    private Geocoder mGeocoder;
    private SensorManager mSensorManager;
    private GPS_exam3_CompassView mCompassView;
    private boolean mCompassEnabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_exam3_map_my_area);
        show_Log("onCreate : 시작");

        mRlayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

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

        GoogleApiClient_Build();
        LocationRequest_Create();
        LocationRequest_GPS_SettingCreate();

        LocationSetting_PendingResult(); // 위치정보(GPS) 활성화 요청 다이얼로그 생성
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

    //==========================================================
    // ResultCallback<LocationSettingsResult> 오버라이드 메소드
    //==========================================================
    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                show_Log("위치 설정이 만족스럽습니다.");
                NowLocationService_Update();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                show_Log("위치 설정이 만족스럽지 못합니다. 사용자에게 위치 설정을 업그레이드 할 수있는 대화 상자를 표시");
                try {
                    status.startResolutionForResult(GPS_exam3_MapMyArea.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    show_Log("PendingIntent를 요청할 수 없습니다.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                show_Log("위치설정이 충분하지 못해고, 대화상자를 생성할 수 없습니다.");
                break;
        }
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
            show_Log("NowLocationService_Start / mNowLocation = " + mNowLocation + "위치 정보 생성 완료");


            mLocationUP = true;

            mGeocoder = new Geocoder(this, Locale.getDefault());
            mCompassEnabled = true;

            mGoogleMap.setMyLocationEnabled(true);
            if(mCompassEnabled) {
                mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
            }

        } else {
            show_Log("LocationService_Start / mNowLocation = " + mNowLocation);
        }
    }
    // 현재위치 리스너 갱신 시작
    protected void NowLocationService_Update() {
        show_Log("NowLocationService_Update");
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mLocationUP = true;
            }
        });
    }

    // 현재위치 리스너 갱신 종료
    protected void NowLocationService_UpdateStop() {
        show_Log("NowLocationService_UpdateStop");
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mLocationUP = false;
            }
        });
    }

    //====================================================
    // 액티비티 오버라이드 메소드
    //====================================================
    @Override
    protected void onStart() {
        super.onStart();
        show_Log("## Override onStart");
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        show_Log("## Override onResume");
        if (mGoogleApiClient.isConnected() && mLocationUP) {
            NowLocationService_Update();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        show_Log("## Override onPause");
        if (mGoogleApiClient.isConnected()) {
            NowLocationService_UpdateStop();
        }

        // 내 위치 자동 표시 disable
        mGoogleMap.setMyLocationEnabled(false);
        if(mCompassEnabled) {
            mSensorManager.unregisterListener(mListener);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        show_Log("## Override onStop");
        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
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
        show_Log("Override onConnected");
        NowLocationService_Start(); // 현재 위치찾기 호출 getLastLocation
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
        mChangeLocation = location;
        mChangeTime = DateFormat.getTimeInstance().format(new Date());
        String getLat = String.valueOf(mChangeLocation.getLatitude());
        String getLon = String.valueOf(mChangeLocation.getLongitude());
        show_Log("Override onLocationChanged " + getLat + "/" + getLon + "/" + mChangeTime);
        Location_Show(mChangeLocation.getLatitude(), mChangeLocation.getLongitude());
    }


    private void Location_Show(Double latitude, Double longitude) {
        // 현재 위치를 이용해 LatLng 객체 생성
        LatLng myPoint = new LatLng(latitude, longitude);
        double myPoingLat = myPoint.latitude;
        double myPoingLng = myPoint.longitude;

        Address addrData = GetGeocoder(myPoingLat, myPoingLng);
        String addr_Full = addrData.getAddressLine(0);
        String addr_LL = "경도 : " + addrData.getLatitude() + " / 위도 : " + addrData.getLongitude();

        // addrData
        // addressLines=[0:"대한민국 경기도 수원시 팔달구 인계동 744-16"],
        // feature=744-16,
        // admin=경기도,
        // sub-admin=null,
        // locality=수원시,
        // thoroughfare=인계동,
        // postalCode=null,
        // countryCode=KR,
        // countryName=대한민국,
        // hasLatitude=true,
        // latitude=37.2741941,
        // hasLongitude=true,
        // longitude=127.022565,
        // phone=null,
        // url=null,
        // extras=null

        // 해당위치로 애니매이션효과 적용 이동
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(myPoingLat, myPoingLng)));

        // 해당위치 줌 효과
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPoint, 18));

        // 지도 유형 설정.
        // 일반 지도인 경우에는 MAP_TYPE_NORMAL
        // 지형 지도인 경우에는 MAP_TYPE_TERRAIN
        // 위성 지도인 경우에는 MAP_TYPE_SATELLITE
        // 위성 지도인 경우에는 MAP_TYPE_HYBRID (Satellite 지도에 주요도시명 표기)
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 해당 위치에 옵션 마커 생성
        MarkerOptions myMarker = new MarkerOptions();
        myMarker.position(myPoint);
        myMarker.title(addr_Full);
        myMarker.snippet(addr_LL);
        myMarker.draggable(true);
        myMarker.visible(true);
        myMarker.alpha(0.9f);
        myMarker.flat(false);
        myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hku3));

        //NowLocationService_UpdateStop();
        mGoogleMap.addMarker(myMarker).showInfoWindow();
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

    private Address GetGeocoder(double lat, double lng) {
        List<Address> addressList;
        Address address = null;

        try {
            addressList = mGeocoder.getFromLocation(lat, lng, 1);
            address = addressList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    //====================================================
    // 서비스 단계별로 상태 로그 출력 메소드
    //====================================================
    private void ServiceStatus_Log(String stopWhere) {
        String isC = String.valueOf(mGoogleApiClient.isConnected());
        show_Log(stopWhere + "#GoogleApiClient = " + isC);
    }

    //====================================================
    // GPS 활성화 체크 메소드
    //====================================================
    // 01. GPS 활성화 체크
    //private boolean mGPS_Enabled = false;
    //mGPS_Enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    //if(!mGPS_Enabled) {
    //GPS_SettingCheck();
    //}

    /*
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
    */

}
