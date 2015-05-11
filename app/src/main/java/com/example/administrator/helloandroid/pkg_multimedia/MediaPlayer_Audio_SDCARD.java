package com.example.administrator.helloandroid.pkg_multimedia;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaPlayer_Audio_SDCARD extends AppCompatActivity implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    //private ArrayList<String> mArray_List;
    private ArrayList<HashMap<String, String>> mArray_List;

    private CustomAdapter mArray_Adapter;

    private MediaPlayer mMP_Player;
    private Button mBTN_Play, mBTN_Stop, mBTN_Prev, mBTN_Next;
    private ListView mLV_mp3list;
    private SeekBar mSB_Progress;

    private int mIdx;
    private boolean wasPlaying;
    private String mSD_Path;
    private String StrException[] = { "Preload", "mypeople" };

    final mSB_ProgressHandler mSB_ProgressHandler = new mSB_ProgressHandler(this);
    private static String TAG = "로그 / 오디오플레이어2 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player_audio_sdcard);

        // 배열 및 미디어플레이어 선언
        mArray_List = new ArrayList<>();
        mMP_Player = new MediaPlayer();

        // 01. SD 카드가 없을 경우 처리
        SDCard_Check();

        // 02. SD 카드의 전체 목록 구하기
        SDCard_Root_List();

        // 03. 재생 상태 만들기
        mIdx = 0;

        // 04. 위젯 선언 및 리스너 생성
        ObjectReady_Setting();

        // 어레이어뎁터 설정, 미리 선언한 array_ITEMS 를 던졌다.
        mArray_Adapter = new CustomAdapter(getApplicationContext(),
                R.layout.simple_list_item_hku, mArray_List);

//        mArray_Adapter = new CustomAdapter(getApplicationContext(),
//                R.layout.simple_list_item_hku, mArray_List);
        mLV_mp3list.setAdapter(mArray_Adapter);
    }

    //====================================================
    // 01. SD 카드가 없을 경우 처리
    //====================================================
    public void SDCard_Check() {
        String ext = Environment.getExternalStorageState();
        if (!ext.equals(Environment.MEDIA_MOUNTED)) {
            show_Toast("SD 카드가 필요합니다.");
            this.finish();
            return;
        } else {
            show_Toast("SD 카드가 감지되었습니다.");
        }
    }

    //====================================================
    // 02. SD 카드의 전체 목록 구하기
    //====================================================
    private void SDCard_Root_List() {
        // SD 카드 루트의 MP3 파일 목록을 구한다.
        mSD_Path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File SearchRoot = new File(mSD_Path);
        show_Log("SearchRoot : " + SearchRoot);
        // 지정 루트 폴더 하위폴더를 반복하여 검색
        File[] file_Root_List = SearchRoot.listFiles(); // mSD_Path 아래 목록 다 가져오기
        for (int i = 0; i < file_Root_List.length; i++) {
            // 02-1. 제외 폴더 체크
            if(Folder_Exception(file_Root_List[i].getName()))
            {
                // 02-2. 폴더와 파일 구분 재귀메소드 호출
                FolderFile_Recursive_Extract_MP3(String.valueOf(file_Root_List[i]));
            }
        }
    }

    //====================================================
    // 02-1. 제외 폴더 체크
    //====================================================
    private boolean Folder_Exception(String FNM) {
        String YN = "N";
        for (int e = 0; e < StrException.length; e++) {
            if(StrException[e].equals(FNM)) {
                YN = "Y";
                break;
            }
        }

        if (YN.equals("N")) {
            return true;
        } else {
            return false;
        }
    }

    //====================================================
    // 02-2. 폴더와 파일 구분 재귀메소드 호출
    //====================================================
    private void FolderFile_Recursive_Extract_MP3(String flist) {

        HashMap<String, String> hashMap = new HashMap<>();

        String fullList;
        File FF = new File(flist);


        if(FF.isDirectory()) {
            String[] LL = FF.list();
            for(int i = 0; i < LL.length; i++) {
                fullList = flist + "/" + String.valueOf(LL[i]);
                FolderFile_Recursive_Extract_MP3(fullList);
            }
        } else {
            if (flist.endsWith(".mp3") || flist.endsWith(".MP3")) {
                hashMap.put("name", FF.getName());
                hashMap.put("path", flist);
                //mArray_List.add(flist);
                mArray_List.add(hashMap);
            }
        }
    }

    //====================================================
    // 04. 위젯 선언 및 리스너 생성
    //====================================================
    private void ObjectReady_Setting() {
        mLV_mp3list = (ListView) findViewById(R.id.lv_mp3list);
        mSB_Progress = (SeekBar) findViewById(R.id.sb_seekBar);
        mBTN_Play = (Button) findViewById(R.id.btn_play);
        mBTN_Stop = (Button) findViewById(R.id.btn_stop);
        mBTN_Prev = (Button) findViewById(R.id.btn_prev);
        mBTN_Next = (Button) findViewById(R.id.btn_next);

        mLV_mp3list.setOnItemClickListener(this); // 04-1
        mBTN_Play.setOnClickListener(this); // 04-2
        mBTN_Stop.setOnClickListener(this); // 04-2
        mBTN_Prev.setOnClickListener(this); // 04-2
        mBTN_Next.setOnClickListener(this); // 04-2

        mMP_Player.setOnCompletionListener(mOnComplete); // 04-3
        mMP_Player.setOnSeekCompleteListener(mOnSeekComplete); // 04-4
        mSB_Progress.setOnSeekBarChangeListener(mOnSeek); // 04-5
        mSB_ProgressHandler.sendEmptyMessageDelayed(0, 200); // 04-6
    }


    //====================================================
    // *** 경로의 mp3 탑재 및 읽기 준비
    //====================================================
    private boolean Player_setDataSource(String mp3Path) {
        try {
            //mMP_Player.setDataSource(mArray_List.get(idx));
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
    // 04-1. 리스트뷰 아이템 클릭 이벤트 메소드
    //====================================================
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Player_Play(position);
    }

    //========================================================
    // 04-2. 버튼 클릭 이벤트 메소드
    //========================================================
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play :
                // 다시 검토
                if(mMP_Player == null) {
                    mMP_Player = new MediaPlayer();
                    show_Log("btn_play 클릭 : mMP_Player = null / 새로 생성");
                }
                if (mMP_Player.isPlaying()) {
                    mMP_Player.pause();
                    mBTN_Play.setText("Play");
                    show_Log("btn_play 클릭 : mMP_Player.isPlaying() = " + mMP_Player.isPlaying());
                } else {
                    mMP_Player.seekTo(0);
                    mMP_Player.start();
                    mBTN_Play.setText("Pause");
                    show_Log("btn_play 클릭 : mMP_Player.isPlaying() = " + mMP_Player.isPlaying());
                }
                break;

            case R.id.btn_stop :
                if (mMP_Player.isPlaying()) {
                    mSB_Progress.setProgress(0);
                    mBTN_Play.setText("Play");
                    mMP_Player.stop();
                    Player_Prepare();
                    show_Log("btn_stop 클릭 : mMP_Player.isPlaying() = " + mMP_Player.isPlaying());
                }
                break;

            case R.id.btn_prev :
                Player_Play(mArray_Adapter.getSelectedPosition() - 1);
                break;
            case R.id.btn_next :
                Player_Play(mArray_Adapter.getSelectedPosition() + 1);
                break;
        }
    }

    //========================================================
    // 04-3. 재생 완료 이벤트 메소드
    //========================================================
    MediaPlayer.OnCompletionListener mOnComplete = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer arg0) {
            Player_Play(mArray_Adapter.getSelectedPosition() + 1);
        }
    };

    //========================================================
    // 04-4. 시크바 이동 지점으로 재생 이벤트 메소드
    //========================================================
    MediaPlayer.OnSeekCompleteListener mOnSeekComplete = new MediaPlayer.OnSeekCompleteListener() {
        public void onSeekComplete(MediaPlayer mp) {
            if (wasPlaying) {
                mMP_Player.start();
            }
        }
    };

    //========================================================
    // 04-5. 프로그래스바 재생 위치 이동 리스너
    //========================================================
    SeekBar.OnSeekBarChangeListener mOnSeek = new SeekBar.OnSeekBarChangeListener() {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mMP_Player.seekTo(progress);
            }
        }
        // 프로그래스바를 움직이면 아래 메소드 실행후 onProgressChanged 가 실행된다.
        public void onStartTrackingTouch(SeekBar seekBar) {
            wasPlaying = mMP_Player.isPlaying();
            if (wasPlaying) {
                mMP_Player.pause();
            }
        }
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    //========================================================
    // 04-6. 핸들러 처리를 위한 Handler 상속 클래스
    //========================================================
    static class mSB_ProgressHandler extends Handler {
        private final WeakReference<MediaPlayer_Audio_SDCARD> mActivity;
        mSB_ProgressHandler(MediaPlayer_Audio_SDCARD activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            MediaPlayer_Audio_SDCARD activity = mActivity.get();
            if(activity != null) {
                activity.sub_HandleMessage(msg);
            }
        }
    }

    private void sub_HandleMessage(Message inputMessage) {
        //String msg = (String) inputMessage.obj;
        if (mMP_Player == null) return;
        if (mMP_Player.isPlaying()) {
            mSB_Progress.setProgress(mMP_Player.getCurrentPosition());
        }
        mSB_ProgressHandler.sendEmptyMessageDelayed(0, 200);
    }

    //========================================================
    // *** 플레이어 재생 처리 메소드
    //========================================================
    private void Player_Play(int positionNum) {
        //커스텀 어뎁터에서 선택한 포지션값으로 뷰 색상 변경
        mArray_Adapter.setSelectedPosition(positionNum);
        mArray_Adapter.notifyDataSetChanged();

        // 스크롤 변경시 목록 올리고 내리는 기능
        //mLV_mp3list.setSelection(positionNum - 7);
        mLV_mp3list.smoothScrollToPosition(positionNum);
        //mLV_mp3list.smoothScrollToPositionFromTop(positionNum, 50, 1000);

        //String filePath = mArray_Adapter.getItem(positionNum);

        HashMap<String, String> HM = mArray_Adapter.getItem(positionNum);
        String filePath = HM.get("path");


        show_Log("filePath==" + filePath);

        mMP_Player.reset(); //플레이어 리셋

        if (!Player_setDataSource(filePath)) {
            Toast.makeText(this, "파일을 읽을 수 없습니다.", Toast.LENGTH_LONG).show();
        } else {
            if (mMP_Player.isPlaying()) {
                mMP_Player.pause();
                mBTN_Play.setText("Play");
            } else {
                mMP_Player.start();
                mBTN_Play.setText("Pause");
            }
        }
    }


    //====================================================
    // *** 커스텀어뎁터 클래스 (어레이어뎁터 확장)
    //====================================================
    private class CustomAdapter extends ArrayAdapter<HashMap<String, String>> {

        private int mSelectedPosition = -1;
        private TextView mText1;
        private Context mContext;
        private ArrayList<HashMap<String, String>> mList;

        public CustomAdapter(Context context, int resource, ArrayList<HashMap<String, String>> items) {
            super(context, resource, items);
            mContext = context;
            mList = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            HashMap<String, String> HM = mList.get(position);
            String name = HM.get("name");

            mText1 = (TextView)convertView.findViewById(android.R.id.text1);
            mText1.setText(name);

            if(position == mSelectedPosition) {
                convertView.setBackgroundColor(Color.DKGRAY);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

            return convertView;
        }

        public void setSelectedPosition(int position) {
            mSelectedPosition = position;
        }

        public int getSelectedPosition() {
            return mSelectedPosition;
        }
    }

    //========================================================
    // *** 액티비티 종료 처리
    //========================================================
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerClose();
    }

    //========================================================
    // *** 플레이어 종료 처리
    //========================================================
    private void MediaPlayerClose() {
        if (mMP_Player != null) {
            mMP_Player.release();
            mMP_Player = null;
        }
    }
}
