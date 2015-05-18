package com.example.administrator.helloandroid.pkg_notice;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;
import com.example.administrator.helloandroid.pkg_multimedia.MediaPlayer_Audio_SDCARD;

public class Notice_exam1_Notification extends AppCompatActivity implements View.OnClickListener {

    private Button mBTN_notice;
    private String BigMessage = "비를 맞으며 우뚝선모습, 떠나려하는 내님이련가. 겨울비 내려와 머리를 적시네.";

    private static String TAG = "로그 / 노티피케이션1 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_exam1_notification);

        mBTN_notice = (Button)findViewById(R.id.btn_notice);
        mBTN_notice.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        // 인텐트 선언
        Intent intent = new Intent(getApplicationContext(), MediaPlayer_Audio_SDCARD.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // 폰에 내장된 기본 소리
        //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        // raw 폴더에 넣은 커스텀 소리
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.hangouts_video_call);

        show_Log(String.valueOf(uri));

        // Notification.Builder 작성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.hku1);
        builder.setContentTitle("타이틀");
        builder.setContentText("내용");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setSound(uri);
        builder.setLights(Color.BLUE, 500, 500);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(BigMessage));
        // 진동기능 추가
        //<uses-permission android:name="ANDROID.PERMISSION.VIBRATE" /> 필요
        // 진동 패턴 배열 : 짝수 번째가 대기시간, 홀수 번째가 진동시간
        builder.setVibrate(new long[] { 400, 200, 400, 200, 500, 500, 500, 500});
        // 진동, 소리, 알림 불빛

        //builder.setDefaults(NotificationCompat.DEFAULT_ALL); //기본 설정 적용시
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(0, builder.build());
    }
}
