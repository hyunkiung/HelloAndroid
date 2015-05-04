package com.example.administrator.helloandroid.pkg_multimedia;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.helloandroid.R;

public class MediaPlayer_Video extends AppCompatActivity implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 비디오플레이어 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private VideoView mVV_show;
    private String filePath;

    private SeekBar mSB_volume;
    private Button mBTN_play;
    private Button mBTN_pause;
    private Button mBTN_stop;

    private int volume_Max = 0;
    private int volume_Current = 0;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player__video);

        // 위젯 셋팅
        mVV_show = (VideoView) findViewById(R.id.vv_show);
        mSB_volume = (SeekBar) findViewById(R.id.sb_volume);
        mBTN_play = (Button) findViewById(R.id.btn_play);
        mBTN_pause = (Button) findViewById(R.id.btn_pause);
        mBTN_stop = (Button) findViewById(R.id.btn_stop);

        // 스토리지에서 파일 경로 셋팅
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath = filePath + "/Download/소찬휘 - Tattoo.avi";
        show_Log(filePath);


        // VideoView에 미디어 컨트롤러 추가
        MediaController mController = new MediaController(this);
        mVV_show.setMediaController(mController);

        // VideoView에 경로 지정
        mVV_show.setVideoPath(filePath);
        // VideoView에 포커스하도록 지정
        mVV_show.requestFocus();

        // 동영상 재생 준비 완료, 재생 완료 리스너
        mVV_show.setOnPreparedListener(this);
        mVV_show.setOnCompletionListener(this);

        // 볼륨 조절 셋팅
        volume_Max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume_Current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSB_volume.setMax(volume_Max);
        mSB_volume.setProgress(volume_Current);
        mSB_volume.setOnSeekBarChangeListener(this);

        // 버튼 리스터 셋팅
        mBTN_play.setOnClickListener(this);
        mBTN_pause.setOnClickListener(this);
        mBTN_stop.setOnClickListener(this);
        // 컨트롤러를 사용하는 경우 버튼으로 MediaPlayer를 제어할 필요는 없다.

    }


    //====================================================
    // 동영상 재생 관련 상속 메소드
    //====================================================
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        show_Toast("동영상 재생 준비가 완료되었습니다.");
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        show_Toast("동영상 재생이 끝났습니다.");
    }


    //====================================================
    // 볼륨 관련 상속 메소드
    //====================================================
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}


    //====================================================
    // 버튼 이벤트 상속 메소드
    //====================================================
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play :
                mVV_show.seekTo(0);
                mVV_show.start();
                break;
            case R.id.btn_pause :
                mVV_show.pause();
                break;
            case R.id.btn_stop :
                mVV_show.stopPlayback();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
