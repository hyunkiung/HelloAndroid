package com.example.administrator.helloandroid.pkg_notice;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class Notice_exam2_Notification2 extends AppCompatActivity implements View.OnClickListener {

    private Button mBTN_NormalProgress;
    private Button mBTN_BigText;
    private Button mBTN_BigPicture;
    private Button mBTN_AddLine;

    private Bitmap bmBigPicture;
    private Notification notification = null;
    private PendingIntent pendingIntent;
    private Intent mIntent;
    private NotificationManagerCompat managerCompat;


    private static String TAG = "로그 / 노티피케이션2 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_exam2_notification2);

        // 위젯, 버튼 리스너 선언
        init();

        mIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://m.naver.com"));
        pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                100, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        managerCompat = NotificationManagerCompat.from(getApplicationContext());

        // setTicker : Notification 실행시 상태바에 보였다 사라지는 텍스트
        // setContentTitle : Notification 창 헤더(제목) 텍스트
        // setContentText : 알리는 메시지 내용 bigText()가 있으면 안보인다.
        // setSmallIcon : 상단 상태바에 알림을 보여주는 아이콘
        // setLargeIcon : Notification 좌측 이미지, 비트맵으로 받는다.
        // setProgress : 프로그래스바 만들기
        // bigPicture : Notification 창 배경 이미지, 비트맵으로 받는다.
        // addAction : Notification 하단에 보이며, 어떤 액션을 취하게 할수 있다.
    }

    //========================================
    // 일반 알림, 프로그래스바 추가
    //========================================
    private void Notice_NormalProgress() {
        notification = new NotificationCompat.Builder(getApplicationContext())
                .setTicker("새로운 알림이 있습니다.")
                .setContentTitle("이곳에 제목을 쓰는것이야")
                .setContentText("본문 내용이 되겠지. 빅텍스트가 있으면 안보이는군.")
                .setSmallIcon(R.drawable.ic_account_balance_wallet_grey600_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.hku2))
                .setProgress(100, 10, false)
                .addAction(R.drawable.ic_action_search, "Action", pendingIntent)
                .build();
        managerCompat.notify(0, notification);
    }

    //===========================================
    // 일반 알림, 프로그래스바 + 빅텍스트 스타일 추가
    //===========================================
    private void Notice_BigText() {
        notification = new NotificationCompat.BigTextStyle(
                new NotificationCompat.Builder(getApplicationContext())
                    .setTicker("새로운 알림이 있습니다.")
                    .setContentTitle("이곳에 제목을 쓰는것이야")
                        .setContentText("본문 내용이 되겠지. 여기서는 이게 안먹혀")
                    .setSmallIcon(R.drawable.ic_account_balance_wallet_grey600_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.hku2))
                    .setProgress(100, 10, false)
                    .addAction(R.drawable.ic_action_search, "Action", pendingIntent))
                .setSummaryText("Android News")
                .bigText("Android 4.1 (Jelly Bean) builds on what's great about Android with improvements to performance and user experience.")
                .build();
        managerCompat.notify(0, notification);
    }

    //===========================================
    // 일반 알림, 프로그래스바 + 빅픽쳐 스타일 추가
    //===========================================
    private void Notice_BigPicture() {
        bmBigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.gtr33);
        notification = new NotificationCompat.BigPictureStyle(
                new NotificationCompat.Builder(getApplicationContext())
                        .setTicker("새로운 알림이 있습니다.")
                        .setContentTitle("이곳에 제목을 쓰는것이야")
                        .setContentText("본문 내용이 되겠지. 여기서는 이게 안먹혀")
                        .setSmallIcon(R.drawable.ic_account_balance_wallet_grey600_24dp)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.hku2))
                        .setProgress(100, 10, false)
                        .addAction(R.drawable.ic_action_search, "액션1", pendingIntent)
                       .addAction(R.drawable.ic_gradient_grey600_24dp, "액션2", pendingIntent))
                .setSummaryText("Android News")
                .bigPicture(bmBigPicture)
                .setBigContentTitle("빅픽쳐에서는 여기가 제목")
                .build();
        managerCompat.notify(0, notification);
    }


    //===========================================
    // 일반 알림, 프로그래스바 + 빅픽쳐 스타일 추가
    //===========================================
    private void Notice_Inbox() {
        notification = new NotificationCompat.InboxStyle(
                new NotificationCompat.Builder(getApplicationContext())
                        .setTicker("새로운 알림이 있습니다.")
                        .setContentTitle("이곳에 제목을 쓰는것이야")
                        .setContentText("본문 내용이 되겠지. 여기서는 이게 안먹혀")
                        .setSmallIcon(R.drawable.ic_account_balance_wallet_grey600_24dp)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.hku2))
                        .setProgress(100, 10, false)
                        .addAction(R.drawable.ic_action_search, "액션1", pendingIntent)
                        .addAction(R.drawable.ic_gradient_grey600_24dp, "액션2", pendingIntent))
                .setSummaryText("Android News")
                .setBigContentTitle("인박스에서는 여기가 제목")
                .addLine("빅텍스트처럼 공지글을 많이 많이 쓸수 있다. 하지만 잘 봐야해.")
                .addLine("라인 단위로 추가가 되는건데.. 한 라인에서 폭이 넘어가면 점점점으로 표기된다고.")
                .addLine("Congrats - you've been selected to join the Google Mobile App Analytics beta!")
                .addLine("Your Cubify Nexus 7 stand from Google I/O")
                .addLine("Case ID: 1843112")
                .build();
        managerCompat.notify(0, notification);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_NormalProgress:
                Notice_NormalProgress();
                break;
            case R.id.btn_BigText:
                Notice_BigText();
                break;
            case R.id.btn_BigPicture:
                Notice_BigPicture();
                break;
            case R.id.btn_AddLine:
                Notice_Inbox();
                break;
        }
    }

    //============================
    // 위젯, 버튼 리스너 메소드
    //============================
    private void init() {
        mBTN_NormalProgress = (Button)findViewById(R.id.btn_NormalProgress);
        mBTN_BigText = (Button)findViewById(R.id.btn_BigText);
        mBTN_BigPicture = (Button)findViewById(R.id.btn_BigPicture);
        mBTN_AddLine = (Button)findViewById(R.id.btn_AddLine);

        mBTN_NormalProgress.setOnClickListener(this);
        mBTN_BigText.setOnClickListener(this);
        mBTN_BigPicture.setOnClickListener(this);
        mBTN_AddLine.setOnClickListener(this);
    }
}
