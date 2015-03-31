package com.example.administrator.helloandroid.project_team;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Parsing06_YouTube extends YouTubeBaseActivity implements  YouTubePlayer.OnInitializedListener  {

    private YouTubePlayerView ytpv;
    private YouTubePlayer ytp;
    final String serverKey="AIzaSyBRiCUg_e0RACNHRFF-M4NQ7Zz4KM7lCWs"; //콘솔에서 받아온 서버키를 넣어줍니다

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing06__you_tube);

        ytpv = (YouTubePlayerView) findViewById(R.id.youtubeplayer);
        ytpv.initialize(serverKey, this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        ytp = youTubePlayer;

        Intent gt =getIntent();
        ytp.loadVideo(gt.getStringExtra("id"));

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Initialization Fail", Toast.LENGTH_LONG).show();
    }
}

/*
안드로이드 스튜디오에 구글 api 사용하기 (교재 참고는 621)
1. Android SDK Manager 실행 - Android Support Library 업데이트

2. Gradle Script 에서 build.gradle (Module:app) 파일에 아래 코드 추가
dependencies {
compile fileTree(dir: 'libs', include: ['*.jar'])
compile 'com.android.support:appcompat-v7:21.0.3'
compile 'com.google.android.gms:play-services:7.0.0'
}

2번은 SDK 업데이트 했다면 File - Project Structure에서 app 추가 해도 된다.

3. AndroidManifest.xml 파일의 Application 엘리먼트 아래에 아래 태그 추가
    <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />

3번은 2번하고 난뒤에 리빌드하면 자동으로 생기는것 같다.

4. cmd 창 열고 C:\Users\Administrator\.android 폴더로 이동

5. cmd에서 아래 정보 입력하고 엔터하면 인증서 지문 생성됨.
keytool -list -v -keystore debug.keystore -storepass android -keypass android

배치 파일이 없다고 나오는 경우에는
Java가 설치된 폴더 경로를 맞춰서 먼저 패스를 잡아주고 다시 실행
set PATH=%PATH%;"C:\Program Files (x86)\Java\jre1.8.0_40\bin"

6. 출력된 인증서 지문들중 SHA1을 따로 복사 해놓음
SHA1: D6:BD:B9:D6:5A:E1:A9:69:B6:21:4C:1D:92:7A:52:F6:81:A7:B0:BE

7. https://console.developers.google.com 사이트로 이동

8. 새프로젝트 생성

9. 프로젝트가 생성되면 API 및 인증 메뉴의 API로 이동
사용하고자하는 API를 추가하면 됨. 일단 Google Maps Android API 추가

10. 다시 API 및 인증 메뉴의 사용자 인증 정보로 이동

11. 새키만들기 클릭 - Android키 선택

12. 6번에서 생성한 SHA1 키에 프로젝트 풀네임을 추가로 붙여서 빈칸에 입력하고 만들기
D6:BD:B9:D6:5A:E1:A9:69:B6:21:4C:1D:92:7A:52:F6:81:A7:B0:BE;com.example.administrator.helloandroid

13. 만들고나면 화면에 API키가 보이고 이걸 복사
AIzaSyBRiCUg_e0RACNHRFF-M4NQ7Zz4KM7lCWs

14. AndroidManifest.xml 파일의 Application 엘리먼트 아래에 키를 추가한 아래 태그 추가
    <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="AIzaSyBRiCUg_e0RACNHRFF-M4NQ7Zz4KM7lCWs"/>

15. AndroidManifest.xml 파일에 맵 컨트롤 권한 태그도 넣어줌.
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

etc)
 구글 플레이어는 API에서 YouTube Data API v3를 추가하고
 https://developers.google.com/youtube/android/player/downloads/
 위링크에서 파일을 받아서 압축 풀고 YouTubeAndroidPlayerApi.jar 파일을
 프로젝트의 app - libs 폴더에 넣어주고, Gradld 재싱크.

*/