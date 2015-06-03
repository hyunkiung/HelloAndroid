package com.example.administrator.helloandroid.pkg_multimedia;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015-05-29.
 */
public class CustomPlayer_Player extends LinearLayout implements View.OnClickListener, View.OnTouchListener {
    public static String PLAY_STATE = "FIRST";

    private static String TAG = "로그 / 커스텀플레이어 player ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(getContext(), toast_msg, Toast.LENGTH_SHORT).show(); }

    private Context mContext;
    private boolean wasPlaying;
    private MediaPlayer mMP_Player;
    private ImageButton mBTN_Play, mBTN_Stop, mBTN_Prev, mBTN_Next;
    private ImageButton mBTN_volume, mBTN_loop;
    private LinearLayout mLL_volumeLayout;

    private SeekBar mSB_Progress;
    private SeekBar mSB_Volume;
    private int delayMillisecond = 200;
    final mSB_ProgressHandler mSB_ProgressHandler = new mSB_ProgressHandler(this);
    final mSB_DialogHandler mSB_DialogHandler = new mSB_DialogHandler(this);

    private TextView mTV_elapsetime;
    private TextView mTV_runtime;
    private Date date = new Date();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
    private int min = 0;
    private int sec = 0;
    private int volume_Max = 0;
    private int volume_Current = 0;
    private AudioManager audioManager;
    private AlertDialog alertD;

    private static final int LOOP_NORMAL = 1;
    private static final int LOOP_ONE = 2;
    private static final int LOOP_ALL = 3;
    public int LOOP_STATUS = 1;

    private int icon_play = R.drawable.av_play_circle_outline_white_48dp;
    private int icon_pause = R.drawable.av_pause_circle_outline_white_48dp;
    private int icon_loop_normal = R.drawable.av_loop_normal_48dp;
    private int icon_loop_one = R.drawable.av_loop_one_48dp;
    private int icon_loop_all = R.drawable.av_loop_all_48dp;


    // 생성자 1 - 자바코드
    public CustomPlayer_Player(Context context) {
        this(context, null);
    }

    // 생성자 2 - xml
    public CustomPlayer_Player(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //=============================================================
    // 생성자 3
    //=============================================================
    public CustomPlayer_Player(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.activity_custom_audioplayer, this);

        mMP_Player = new MediaPlayer();
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        ObjectReady_Setting();
    }

    //====================================================
    // 04. 위젯 선언 및 리스너 생성
    //====================================================
    private void ObjectReady_Setting() {
        mTV_elapsetime = (TextView) findViewById(R.id.tv_elapsetime);
        mTV_runtime = (TextView) findViewById(R.id.tv_runtime);
        mSB_Progress = (SeekBar) findViewById(R.id.sb_seekBar);
        mBTN_Play = (ImageButton) findViewById(R.id.btn_play);
        mBTN_Stop = (ImageButton) findViewById(R.id.btn_stop);
        mBTN_Prev = (ImageButton) findViewById(R.id.btn_prev);
        mBTN_Next = (ImageButton) findViewById(R.id.btn_next);
        mBTN_volume = (ImageButton) findViewById(R.id.btn_volume);
        mBTN_loop = (ImageButton) findViewById(R.id.btn_loop);

        mBTN_Play.setOnClickListener(this); // 04-2
        mBTN_Stop.setOnClickListener(this); // 04-2
        mBTN_Prev.setOnClickListener(this); // 04-2
        mBTN_Next.setOnClickListener(this); // 04-2
        mBTN_volume.setOnClickListener(this); // 04-2
        mBTN_loop.setOnClickListener(this); // 04-2

        mMP_Player.setOnCompletionListener(mOnComplete); // 04-3
        mMP_Player.setOnSeekCompleteListener(mOnSeekComplete); // 04-4
        mSB_Progress.setOnSeekBarChangeListener(mOnSeek); // 04-5
        mSB_ProgressHandler.sendEmptyMessageDelayed(0, delayMillisecond); // 04-6
    }

    //====================================================
    // 버튼 클릭 이벤트 오버라이드
    //====================================================
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play :
                if(PLAY_STATE.equals("FIRST")) {
                    if (mListener_Play != null) {
                        mListener_Play.CustomRun_Play();    // 외부에 콜백이 호출 됨
                        show_Log("CustomPlayer_Player - onClick CustomRun_Play");
                    }
                } else {
                    Player_PlayPause();
                }
                break;
            case R.id.btn_stop :
                if (mMP_Player.isPlaying()) {
                    mMP_Player.seekTo(0);
                    mSB_Progress.setProgress(0);
                    mMP_Player.stop();
                    //mBTN_Play.setText("Play");
                    mBTN_Play.setImageResource(icon_play);
                    mTV_elapsetime.setText("00:00");
                    Player_Prepare();
                    wasPlaying = false;
                    show_Log("btn_stop 클릭 : mMP_Player.isPlaying() = " + mMP_Player.isPlaying());
                }
                break;
            case R.id.btn_prev :
                if (mListener_PrevNext != null) {
                    mListener_PrevNext.CustomRun_PrevNext(-1);    // 외부에 콜백이 호출 됨
                    show_Log("CustomPlayer_Player - onClick CustomRun_PrevNext");
                }
                break;
            case R.id.btn_next :
                if (mListener_PrevNext != null) {
                    mListener_PrevNext.CustomRun_PrevNext(1);    // 외부에 콜백이 호출 됨
                    show_Log("CustomPlayer_Player - onClick CustomRun_PrevNext");
                }
                break;
            case R.id.btn_volume :
                Player_Volume();
                break;
            case R.id.btn_loop :
                Player_Looping();
                break;
        }
    }


    //====================================================
    // 콜백 처리를 위한 리스너 (Play)
    //====================================================
    private MyListener_PlayerPlay mListener_Play;


    public interface MyListener_PlayerPlay {
        public void CustomRun_Play();
    }
    public void setOnMyListener_PlayerPlay(MyListener_PlayerPlay listener) {
        mListener_Play = listener;
        show_Log("setOnMyListener_PlayerPlay / mListener_Play");
    }

    //====================================================
    // 콜백 처리를 위한 리스너 (Prev / Next)
    //====================================================
    private MyListener_PlayerPrevNext mListener_PrevNext;

    public interface MyListener_PlayerPrevNext {
        public void CustomRun_PrevNext(int val);
    }
    public void setOnMyListener_PlayerPrevNext(MyListener_PlayerPrevNext listener) {
        mListener_PrevNext = listener;
        show_Log("setOnMyListener_PlayerPrevNext / mListener_PrevNext");
    }



    //====================================================
    // *** 플레이 처리 (요청 처리, 내부 처리)
    //====================================================
    public void Player_Call(String path) {
        mMP_Player.reset(); //플레이어 리셋
        PLAY_STATE = "SECOND";
        if (!Player_setDataSource(path)) {
            show_Log("파일을 읽을 수 없습니다.");
        } else {
            Player_PlayPause();
        }
    }

    private void Player_PlayPause() {
        if (mMP_Player.isPlaying()) {
            mMP_Player.pause();
            //mBTN_Play.setText("Play");
            mBTN_Play.setImageResource(icon_play);
        } else {
            mMP_Player.start();
            //mBTN_Play.setText("Pause");
            mBTN_Play.setImageResource(icon_pause);
        }
    }

    //====================================================
    // *** 경로의 mp3 탑재 및 읽기 준비
    //====================================================
    private boolean Player_setDataSource(String mp3Path) {
        try {
            mMP_Player.setDataSource(mp3Path);
            show_Log("Player_setDataSource try");
        } catch (IllegalArgumentException e) {
            show_Log("Player_setDataSource IllegalArgumentException");
            return false;
        } catch (IllegalStateException e) {
            show_Log("Player_setDataSource IllegalStateException");
            return false;
        } catch (IOException e) {
            show_Log("Player_setDataSource IOException");
            return false;
        }

        Player_Prepare();
        mSB_Progress.setMax(mMP_Player.getDuration());

        // 선택 파일 재생 시간 표기
        int fullLen = mSB_Progress.getMax();
        min = fullLen / 60000;
        sec = (fullLen % 60000) / 1000;
        date.setMinutes(min);
        date.setSeconds(sec);
        mTV_runtime.setText(timeFormat.format(date.getTime()));
        return true;
    }

    private void Player_Prepare() {
        try {
            mMP_Player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //====================================================
    // 플레이어 자원 해제
    //====================================================
    public void Player_Clear() {
        mMP_Player.release();
        mMP_Player = null;
    }

    //====================================================
    // 플레이어 볼륨 다이얼로그 오픈
    //====================================================
    public void Player_Volume() {
        View innerView = LayoutInflater.from(mContext).inflate(R.layout.activity_custom_audiovolume, null);

        AlertDialog.Builder adialog = new AlertDialog.Builder(mContext);
        adialog.setView(innerView);
        mSB_Volume = (SeekBar) innerView.findViewById(R.id.sb_volumeBar);
        mLL_volumeLayout = (LinearLayout) innerView.findViewById(R.id.ll_volumeLayout);

        volume_Max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume_Current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mSB_Volume.setMax(volume_Max);
        mSB_Volume.setProgress(volume_Current);
        mSB_Volume.setOnSeekBarChangeListener(mOnSeek);

        // 다이얼로그의 화면 터치 이벤트
        mSB_Volume.setOnTouchListener(this);
        mLL_volumeLayout.setOnTouchListener(this);

        // 다이얼로그 생성
        alertD = adialog.create();
        alertD.setTitle("Volume");
        alertD.show();

        mSB_DialogHandler.sendEmptyMessageDelayed(0, 3500);
    }

    //====================================================
    // 플레이어 반복 재생 처리
    //====================================================
    public void Player_Looping() {
        // 반복 재생 셋팅
        switch (LOOP_STATUS) {
            case LOOP_NORMAL :
                LOOP_STATUS = LOOP_ONE;
                mBTN_loop.setImageResource(icon_loop_one);
                break;
            case LOOP_ONE :
                LOOP_STATUS = LOOP_ALL;
                mBTN_loop.setImageResource(icon_loop_all);
                break;
            case LOOP_ALL :
                //mMP_Player.setLooping(false);
                LOOP_STATUS = LOOP_NORMAL;
                mBTN_loop.setImageResource(icon_loop_normal);
                break;
        }
        show_Log("LOOP_STATUS : " + LOOP_STATUS);
    }


    //====================================================
    // 플레이어 볼륨 다이얼로그 터치 이벤트
    //====================================================
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if(action == MotionEvent.ACTION_UP) {
            mSB_DialogHandler.sendEmptyMessageDelayed(0, 3500);
        } else if(action == MotionEvent.ACTION_DOWN) {
            mSB_DialogHandler.sendEmptyMessage(1);
        }
        return false;
    }

    //=============================================================
    // *** 볼륨 다이얼로그 온오프 핸들러 처리를 위한 Handler 상속 클래스
    //=============================================================
    static class mSB_DialogHandler extends Handler {
        private final WeakReference<CustomPlayer_Player> mActivity1;
        mSB_DialogHandler(CustomPlayer_Player activity) {
            mActivity1 = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            CustomPlayer_Player activity1 = mActivity1.get();
            if(activity1 != null) {
                activity1.volume_HandleMessage(msg);
            }
        }
    }

    public void volume_HandleMessage(Message inputMessage) {
        String msg = (String) inputMessage.obj;
        switch(inputMessage.what){
            case 0 :
                if(alertD != null && alertD.isShowing()) {
                    alertD.dismiss();
                }
                show_Log("핸들러 메시지 0 " + msg);
                break;
            case 1 :
                //핸들러.리무브콜백스앤드메세지에 널을 넣어봐라.
                mSB_DialogHandler.removeCallbacksAndMessages(null);
                show_Log("핸들러 메시지 1 " + msg);
                break;
        }
    }

    //========================================================
    // 재생 프로그래스 핸들러 처리를 위한 Handler 상속 클래스
    //========================================================
    static class mSB_ProgressHandler extends Handler {
        private final WeakReference<CustomPlayer_Player> mActivity2;
        mSB_ProgressHandler(CustomPlayer_Player activity) {
            mActivity2 = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            CustomPlayer_Player activity2 = mActivity2.get();
            if(activity2 != null) {
                activity2.custom_HandleMessage(msg);
            }
        }
    }

    public void custom_HandleMessage(Message inputMessage) {
        if (mMP_Player == null) return;
        if (mMP_Player.isPlaying()) {
            mSB_Progress.setProgress(mMP_Player.getCurrentPosition());
        }
        mSB_ProgressHandler.sendEmptyMessageDelayed(0, delayMillisecond);
    }


    //========================================================
    // 04-3. 재생 완료 이벤트 메소드 - 다음곡 연결
    //========================================================
    MediaPlayer.OnCompletionListener mOnComplete = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer arg0) {
            if (mListener_PrevNext != null) {
                if(LOOP_STATUS == LOOP_ONE) {
                    mListener_PrevNext.CustomRun_PrevNext(0);    // 외부에 콜백이 호출 됨
                } else {
                    mListener_PrevNext.CustomRun_PrevNext(1);    // 외부에 콜백이 호출 됨
                }

                show_Log("CustomPlayer_Player - OnCompletionListener CustomRun_PrevNext");
            }
        }
    };

    //========================================================
    // 04-4. 시크바 이동 지점으로 재생 이벤트 메소드
    //========================================================
    MediaPlayer.OnSeekCompleteListener mOnSeekComplete = new MediaPlayer.OnSeekCompleteListener() {
        public void onSeekComplete(MediaPlayer mp) {
            if (wasPlaying) {
                mMP_Player.start();
                show_Log("OnSeekCompleteListener / wasPlaying = " + wasPlaying);
            }
        }
    };

    //========================================================
    // 04-5. 프로그래스바 재생 위치 이동 리스너
    //========================================================
    SeekBar.OnSeekBarChangeListener mOnSeek = new SeekBar.OnSeekBarChangeListener() {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.sb_seekBar:
                    if (fromUser) {
                        mMP_Player.seekTo(progress);
                        show_Log("onProgressChanged / fromUser = " + fromUser);
                    }
                    // 시크바 파일 재생진행 시간 표기
                    mSB_Progress.setProgress(progress);
                    int min = progress / 60000;
                    int sec = (progress % 60000) / 1000;
                    date.setMinutes(min);
                    date.setSeconds(sec);
                    mTV_elapsetime.setText(timeFormat.format(date.getTime()));
                    break;

                case R.id.sb_volumeBar:
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    break;
            }
        }

        // 프로그래스바를 움직이면 아래 메소드 실행후 onProgressChanged 가 실행된다.
        public void onStartTrackingTouch(SeekBar seekBar) {
            switch (seekBar.getId()) {
                case R.id.sb_seekBar:
                    wasPlaying = mMP_Player.isPlaying();
                    if (wasPlaying) {
                        mMP_Player.pause();
                        show_Log("onStartTrackingTouch / wasPlaying = " + wasPlaying);
                    }
                    break;
                case R.id.sb_volumeBar:
                    break;
            }
        }
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };




    //====================================================
    // ViewGroup 컨트롤 오버라이드
    //====================================================
    // ViewGroup 의 위치 결정
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    // ViewGroup 의 크기를 결정
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
