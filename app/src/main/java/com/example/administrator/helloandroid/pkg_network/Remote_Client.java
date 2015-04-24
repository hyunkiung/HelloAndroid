package com.example.administrator.helloandroid.pkg_network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Remote_Client extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    public static String SYSTEM_NAME = "SYSTEM";

    private ActionBar action_Bar;   // 액션바
    private TextView mTV_status;
    private EditText mET_message;
    private Button mBTN_send;

    private String msg;
    private Remote_Server sendServer;
    private Remote_Client_Socket sendCient;

    private ListView mLV_chat;
    private ArrayList<Remote_Client_Info> mArray_List;
    private Remote_Client_Adapter mArray_Adapter;
    private Remote_Client_Info chat_Info;

    private String i_nick = "socket";
    private String i_message = "Socket Start";
    private String i_wdt = "00.00.00";
    private String i_who = SYSTEM_NAME;
    private boolean LocalServerRun = false;
    private boolean LocalSocketRun = false;
    private boolean RemoteSocketRun = false;

    private LinearLayout mLL_menu1;
    private LinearLayout mLL_menu2;
    private LinearLayout mLL_menu3;

    private ImageView mIV_menu1;
    private ImageView mIV_menu2;
    private ImageView mIV_menu3;


    // 핸들러 클래스 선언
    final Message_Handler_Call mHandler = new Message_Handler_Call(this);

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 소켓 액티비티 : ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_client);

        init(); // 위젯 선언 콜
        ActionBarSet(); // 액션바 셋팅 콜

        // ArrayList, ArrayAdapter
        mArray_List = new ArrayList<>();
        chat_Info = new Remote_Client_Info(i_nick, i_message, i_wdt, i_who);
        mArray_List.add(chat_Info);
        mArray_Adapter = new Remote_Client_Adapter(this, mArray_List);
        mLV_chat.setAdapter(mArray_Adapter);
    }

    // 선언 메소드
    private void init() {
        // 위젯 선언
        mLV_chat = (ListView) findViewById(R.id.lv_chat);
        mET_message = (EditText) findViewById(R.id.et_message);
        mTV_status = (TextView) findViewById(R.id.tv_status);
        mBTN_send = (Button) findViewById(R.id.btn_send);
        mBTN_send.setOnClickListener(this); // 메시지 보내기 버튼 리스너
    }

    // 액션바 설정 메소드
    private void ActionBarSet() {
        action_Bar = this.getSupportActionBar();
        View v = getLayoutInflater().inflate(R.layout.activity_remote_client_actionbar, null);
        //액션바에 커스텀뷰를 설정
        if (action_Bar != null) {
            action_Bar.setDisplayShowCustomEnabled(true);
            action_Bar.setCustomView(R.layout.activity_custom_bar);
            action_Bar.setCustomView(v, new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT));

            TextView mTV_title = (TextView) findViewById(R.id.tv_title);
            mTV_title.setText("소켓통신 채팅");

            mLL_menu1 = (LinearLayout) findViewById(R.id.ll_menu1);
            mLL_menu2 = (LinearLayout) findViewById(R.id.ll_menu2);
            mLL_menu3 = (LinearLayout) findViewById(R.id.ll_menu3);

            mIV_menu1 = (ImageView) findViewById(R.id.iv_menu1);
            mIV_menu2 = (ImageView) findViewById(R.id.iv_menu2);
            mIV_menu3 = (ImageView) findViewById(R.id.iv_menu3);

            mLL_menu1.setOnClickListener(this);
            mLL_menu2.setOnClickListener(this);
            mLL_menu3.setOnClickListener(this);
        }
    }

    //========================================================
    // 메시지 전송 액션 처리 메소드 (보내기버튼, 엔터키 공통 사용)
    //========================================================
    private void SendAction() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                msg = mET_message.getText().toString();
                if (msg.length() > 0) { sendCient.sendMessage(msg); }
            }
        }).start();
        mET_message.setText("");
    }

    //========================================================
    // 액션바 메뉴 처리 메소드
    //========================================================
    private void localServer_OnOff(boolean status) {
        LocalServerRun = status;
        if(status) {
            mIV_menu1.setImageResource(R.drawable.ic_account_balance_wallet_grey600_24dp);
        } else {
            mIV_menu1.setImageResource(R.drawable.ic_account_balance_wallet_black_24dp);
        }
    }

    private void localSocket_OnOff(boolean status) {
        LocalSocketRun = status;
        if(status) {
            mIV_menu2.setImageResource(R.drawable.ic_gradient_grey600_24dp);
        } else {
            mIV_menu2.setImageResource(R.drawable.ic_gradient_black_24dp);
        }
        show_Log("OnOff : LocalServerRun = " + LocalServerRun + " / LocalSocketRun = " + LocalSocketRun + " / RemoteSocketRun = " +RemoteSocketRun );
    }

    private void remoteSocket_OnOff(boolean status) {
        RemoteSocketRun = status;
        if(status) {
            mIV_menu3.setImageResource(R.drawable.ic_dvr_grey600_24dp);
        } else {
            mIV_menu3.setImageResource(R.drawable.ic_dvr_black_24dp);
        }
        show_Log("OnOff : LocalServerRun = " + LocalServerRun + " / LocalSocketRun = " + LocalSocketRun + " / RemoteSocketRun = " +RemoteSocketRun );
    }


    private void localSocket_Close() {
        if(LocalSocketRun) {
            sendServer = null;
            sendCient.socketClose();
            LocalServerRun = false;
            LocalSocketRun = false;
        }
        show_Log("Close : LocalServerRun = " + LocalServerRun + " / LocalSocketRun = " + LocalSocketRun + " / RemoteSocketRun = " +RemoteSocketRun );
    }

    private void remoteSocket_Close() {
        if(RemoteSocketRun) {
            sendCient.socketClose();
            RemoteSocketRun = false;
        }
        show_Log("Close : LocalServerRun = " + LocalServerRun + " / LocalSocketRun = " + LocalSocketRun + " / RemoteSocketRun = " +RemoteSocketRun );
    }

    //========================================================
    // 버튼 액션
    //========================================================
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 로컬 서버 가동
            case R.id.ll_menu1 :
                remoteSocket_OnOff(false);
                remoteSocket_Close();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendServer = new Remote_Server();
                        sendServer.start();
                    }
                }).start();
                localServer_OnOff(true);
                break;

            // 로컬 서버 접속
            case R.id.ll_menu2 :
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendCient = new Remote_Client_Socket();
                        sendCient.connect(mHandler, "127.0.0.1", 5000);
                    }
                }).start();
                localSocket_OnOff(true);
                break;

            // 학원 서버 접속
            case R.id.ll_menu3 :
                localServer_OnOff(false);
                localSocket_OnOff(false);
                localSocket_Close();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendCient = new Remote_Client_Socket();
                        sendCient.connect(mHandler, "192.168.1.5", 5000);
                    }
                }).start();

                remoteSocket_OnOff(true);
                break;

            // 메시지 전송
            case R.id.btn_send :
                SendAction();
                break;
        }
    }


    //========================================================
    // 핸들러 처리를 위한 Handler 상속 클래스
    //========================================================
    static class Message_Handler_Call extends Handler {
        private final WeakReference<Remote_Client> mActivity;
        Message_Handler_Call(Remote_Client activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Remote_Client activity = mActivity.get();
            if(activity != null) {
                activity.sub_HandleMessage(msg);
            }
        }
    }

    private void sub_HandleMessage(Message inputMessage) {
        String msg = (String) inputMessage.obj;
        switch(inputMessage.what){
            case 1 :
                show_Log("접속되었나?? " + msg);
                break;
            case 2 :
                show_Log("메시지 잘 받아오나?? " + msg);
                break;
            case 3 :
                show_Log("종료되었나?? " + msg);
                break;
            case 99 :
                show_Log("서버 접속 완료 " + msg);
            case 100 :
                show_Log("서버 접속 에러 " + msg);
                break;
        }

        // 닉네임이 포함된 첫 시작이면 채팅이고 아니면 시스템 알림
        if (msg.substring(0, 1).equals("[")) {
            i_nick = sendCient.NICK;
            i_message = msg.substring(msg.indexOf("]") + 2);
            i_who = msg.substring(1, msg.indexOf("]"));
            i_wdt = System_ChatTime_Now();
        } else {
            i_message = msg;
            i_who = SYSTEM_NAME;
        }

        chat_Info = new Remote_Client_Info(i_nick, i_message, i_wdt, i_who);
        mArray_List.add(chat_Info);
        mArray_Adapter.notifyDataSetChanged();
        mLV_chat.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 소켓 접속이후 액티비티 종료시 소켓 종료
        localSocket_Close();
        remoteSocket_Close();
    }





    // 시스템 시간 리턴 메소드
    public String System_ChatTime_Now() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("aa KK:mm");
        String time = dateFormat.format(new Date(System.currentTimeMillis()));
        return time;
    }

    //========================================================
    // 에디트텍스트 키보드 액션
    //========================================================
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER) {
            SendAction();
            return true;
        }
        return false;
    }

}
