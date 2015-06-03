package com.example.administrator.helloandroid.pkg_location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;


public class GPS_exam4_Speed extends ActionBarActivity {

    private static String TAG = "로그 / GPS Exam3 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    private TextView mTV_Message;

    private LocationManager lm;
    private LocationListener ll;
    double mySpeed, maxSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_exam4_speed);

        mTV_Message = (TextView)findViewById(R.id.tv_message);
        maxSpeed = mySpeed = 0;
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new SpeedoActionListener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

    }

    private class SpeedoActionListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                mySpeed = location.getSpeed();
                if (mySpeed > maxSpeed) {
                    maxSpeed = mySpeed;
                }
                mTV_Message.setText("\nCurrent Speed : " + mySpeed + " km/h, Max Speed : "
                        + maxSpeed + " km/h");
            } else {
                mTV_Message.setText("위치를 알수가 없다");
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    }

}
