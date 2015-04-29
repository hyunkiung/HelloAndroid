package com.example.administrator.helloandroid.pkg_multimedia;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaPlayer_Audio extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    // Thread 알아둘것.
    // wait(): 여러개의 쓰레드가 다시 깨우기 전까지 정지. Syncronized(동기화) 처리 용.
    // notify()/notifyAll(): wait()에 의해 정지된 쓰레드를 순서대로/전부 깨움.

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 오디오플레이어 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private Button mBTN_file;
    private Button mBTN_play;
    private Button mBTN_pause;
    private Button mBTN_stop;
    private TextView mTV_filename;
    private TextView mTV_thread;
    private TextView mTV_elapsetime;
    private TextView mTV_runtime;
    private TextView mTV_duration;
    private SeekBar mSB_progress;
    private CheckBox mCHK_loop;

    private MediaPlayer mPlayer;
    private Uri file_uri;
    private Date date = new Date();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");

    private String file_name;
    private String play_mode = "play";
    private boolean play_loop = false;
    private int min = 0;
    private int sec = 0;
    private int delayMillisecond = 1000;

    final mProgressHandler mProgressHandler = new mProgressHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player_audio);

        setdisplay_TimeOff();
        init();
    }

    // 위젯 선언부
    private void init() {
        mBTN_file = (Button) findViewById(R.id.btn_file);
        mBTN_play = (Button) findViewById(R.id.btn_play);
        mBTN_pause = (Button) findViewById(R.id.btn_pause);
        mBTN_stop = (Button) findViewById(R.id.btn_stop);
        mTV_filename = (TextView) findViewById(R.id.tv_filename);
        mTV_thread = (TextView) findViewById(R.id.tv_thread);
        mTV_elapsetime = (TextView) findViewById(R.id.tv_elapsetime);
        mTV_runtime = (TextView) findViewById(R.id.tv_runtime);
        mTV_duration = (TextView) findViewById(R.id.tv_duration);
        mSB_progress = (SeekBar) findViewById(R.id.sb_progress);
        mCHK_loop = (CheckBox) findViewById(R.id.chk_loop);

        mBTN_file.setOnClickListener(this);
        mBTN_play.setOnClickListener(this);
        mBTN_pause.setOnClickListener(this);
        mBTN_stop.setOnClickListener(this);
        mCHK_loop.setOnClickListener(this);

        mBTN_play.setEnabled(false);
        mBTN_pause.setEnabled(false);
        mBTN_stop.setEnabled(false);
    }


    //========================================================
    // 핸들러 처리를 위한 Handler 상속 클래스
    //========================================================
    static class mProgressHandler extends Handler {
        private final WeakReference<MediaPlayer_Audio> mActivity;
        mProgressHandler(MediaPlayer_Audio activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MediaPlayer_Audio activity = mActivity.get();
            if(activity != null) {
                activity.sub_HandleMessage(msg);
            }
        }
    }

    private void sub_HandleMessage(Message i_msg) {
        //String msg = (String) i_msg.obj;
        String handler_status;
        if (mPlayer == null) return;
        if (mPlayer.isPlaying()) {
            mSB_progress.setProgress(mPlayer.getCurrentPosition());
            handler_status = "getCurrentPosition() : " +  String.valueOf(mPlayer.getCurrentPosition());
            mTV_thread.setText(handler_status);
            mProgressHandler.sendEmptyMessageDelayed(0, delayMillisecond);
        } else if (play_mode.equals("play")){
            media_Stop();
        }
    }


    //=============================================================
    // seekbar 메소드 영역 START
    //=============================================================
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mPlayer.seekTo(progress);
            show_Log("onProgressChanged / mPlayer.seekTo() = " + String.valueOf(progress));
        }
        //show_Log(String.valueOf(progress));
        mSB_progress.setProgress(progress);
        int min = progress / 60000;
        int sec = (progress % 60000) / 1000;
        date.setMinutes(min);
        date.setSeconds(sec);
        mTV_elapsetime.setText(timeFormat.format(date.getTime()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {  }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {  }


    //=============================================================
    // 버튼 실행 메소드 영역 START
    //=============================================================
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_file :
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(Intent.createChooser(intent, "파일선택..."), 0);
                break;
            case R.id.btn_play :
                media_Play();
                break;
            case R.id.btn_pause :
                media_Pause();
                break;
            case R.id.btn_stop :
                media_Stop();
                break;
            case R.id.chk_loop :
                media_Loop();
                break;
        }
    }

    // 플레이 반복 메소드
    private void media_Loop() {
        if(mCHK_loop.isChecked()) {
            play_loop = true;
            if (mPlayer != null) {
                mPlayer.setLooping(true);
            }
        } else {
            play_loop = false;
            if (mPlayer != null) {
                mPlayer.setLooping(false);
            }
        }
    }

    // 플레이어 재생 메소드
    private void media_Play() {
        try {
            if (play_mode.equals("play")) {
                if (mPlayer != null) {
                    mPlayer.release();
                    mPlayer = null;
                }

                // 미디어플레이어 준비
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(this, file_uri);
                mPlayer.prepare();

                // 시크바 초기 셋팅
                mSB_progress.setMax(mPlayer.getDuration());
                mSB_progress.setOnSeekBarChangeListener(this);
                mProgressHandler.sendEmptyMessageDelayed(0, delayMillisecond);
                mPlayer.start();

                // 반복재생 설정
                if(play_loop) {
                    mPlayer.setLooping(true);
                }

                int fullLen = mSB_progress.getMax();
                min = fullLen / 60000;
                sec = (fullLen % 60000) / 1000;
                date.setMinutes(min);
                date.setSeconds(sec);
                mTV_runtime.setText(timeFormat.format(date.getTime()));
                mTV_duration.setText(String.valueOf(mPlayer.getDuration()));
            } else {
                mPlayer.start();
            }
            mBTN_file.setEnabled(false);
            mBTN_play.setEnabled(false);
            mBTN_pause.setEnabled(true);
            mBTN_stop.setEnabled(true);
        } catch (IOException e) {
            // IOException 이 났을 때 다른 익셉션을 발생시켜서 죽인다
            throw new RuntimeException("io exception");
        }
    }

    // 플레이어 일시정지 메소드
    private void media_Pause() {
        if (mPlayer != null) {
            mPlayer.pause();
            mBTN_play.setEnabled(true);
            mBTN_play.setText("RESUME");
            play_mode = "pause";
            mBTN_pause.setEnabled(false);
        }
    }

    // 플레이어 정지 메소드
    private void media_Stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            mBTN_file.setEnabled(true);
            mBTN_play.setEnabled(true);
            mBTN_play.setText("PLAY");
            play_mode = "play";
            mBTN_pause.setEnabled(false);
            mBTN_stop.setEnabled(false);
            mSB_progress.setProgress(0);
            mTV_elapsetime.setText("00:00");
            seekbar_Close();
        }
    }

    // btn_file 클릭시 Intent 결과
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            file_uri = data.getData();
            mTV_filename.setText(getFileName_FormUri(data.getDataString()));
        }
    }

    // Uri로 파일명 읽어오기
    public String getFileName_FormUri(String data)
    {
        Cursor c = getContentResolver().query(Uri.parse(data), null, null, null, null);
        c.moveToFirst();
        int c_index = c.getColumnIndex(MediaStore.MediaColumns.DATA);
        if (c_index < 0) {
            file_name = "error";
        } else {
            String fPath = c.getString(c_index);
            Uri uri = Uri.fromFile(new File(fPath));
            c.close();
            String str_uri = uri.toString();
            file_name = str_uri.substring(str_uri.lastIndexOf("/") + 1);

            // 한글 깨짐 처리
            try {
                file_name = URLDecoder.decode(file_name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            mBTN_play.setText("PLAY");
            play_mode = "play";
            mBTN_play.setEnabled(true);
            mBTN_pause.setEnabled(false);
            mBTN_stop.setEnabled(false);
            mSB_progress.setProgress(0);
        }
        return file_name;
    }

    @Override
    protected void onDestroy() {
        setdisplay_TimeOn();
        seekbar_Close();
        super.onDestroy();
    }

    // Thread 종료 처리
    private void seekbar_Close() {
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    // 앱 실행중 화면 꺼짐 제거 메소드
    private void setdisplay_TimeOff() {
        /* private int displayOnOff_time;
        ====== TYPE1 : ========================================================
        ============== 시스템 환경 조작 방법  : 폰마다 엔트리 벨류가 다를것 같다. ====
        ContentResolver cr = getContentResolver();
        displayOnOff_time = 0;
        try {
            displayOnOff_time = Settings.System.getInt(cr, Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        //내폰 Set screen_off_timeout in entry value : 30000 (30초)
        //내폰 Set screen_off_timeout in entry value : 2147483647 (항상 켜짐)
        show_Log("displayOnOff_time (1) : " + displayOnOff_time);
        Settings.System.putInt(cr, Settings.System.SCREEN_OFF_TIMEOUT, -1); // "-1 : 항상켜짐"
        */

        /* protected PowerManager.WakeLock mWakeLock;
        ====== TYPE2 : ========================================================
        ============== 파워서비스 조작 방법 : 화면은 안꺼지지만 조명은 어두워진다. ===
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
        */

        //====== TYPE3 : 레이아웃 속성 플래그 변경 ============================
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // 앱 종료시 화면 꺼짐 활성 메소드
    private void setdisplay_TimeOn() {
        /*============== TYPE1 : 시스템 환경 조작 해제 ================
        ContentResolver cr = getContentResolver();
        if (displayOnOff_time != 0) {
            // 꺼짐 활성화전에 저장해둔 타임으로 복원시켜준다.
            Settings.System.putInt(cr, Settings.System.SCREEN_OFF_TIMEOUT, displayOnOff_time);
        } */

        /*============== TYPE2 : 파워서비스 조작 해제 ================
        this.mWakeLock.release(); */

        //============ TYPE3 : 레이아웃 속성 플래그 변경 =============
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}














