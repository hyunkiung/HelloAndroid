package com.example.administrator.helloandroid.pkg_network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

public class Remote_Socket_Chat extends ActionBarActivity implements View.OnClickListener, View.OnKeyListener {

    //private TextView mTV_chat;
    private TextView mTV_status;
    private EditText mET_message;
    private Button mBTN_con_server;
    private Button mBTN_con_remote;
    private Button mBTN_send;

    private String msg;
    public Handler mHandler;
    private Remote_Socket_Server sendServer;
    private Remote_Socket_Thread sendThread;

    private ListView mLV_chat;
    private ArrayList<String> mArray_List;
    private ArrayAdapter<String> mArray_Adapter;


    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    // 메세지 토스트 메소드 (공용)
    void showToast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_socket_chat);

        mLV_chat = (ListView) findViewById(R.id.lv_chat);
        //mTV_chat = (TextView) findViewById(R.id.tv_chat);
        mET_message = (EditText) findViewById(R.id.et_message);
        mTV_status = (TextView) findViewById(R.id.tv_status);
        mBTN_con_server = (Button) findViewById(R.id.btn_con_server);
        mBTN_con_remote = (Button) findViewById(R.id.btn_con_remote);
        mBTN_send = (Button) findViewById(R.id.btn_send);

        mBTN_con_server.setOnClickListener(this); // 서버 가동 리스너
        mBTN_con_remote.setOnClickListener(this); // 원격 서버 접속 리스너
        mBTN_send.setOnClickListener(this); // 메시지 보내기 버튼 리스너
        mET_message.setOnKeyListener(this); // 엔터키로 메시지 보내기 리스너

        // Socket 클라이언트 메시지 출력 핸들러 호출
        Message_Handler_Call();

        // ArrayList, ArrayAdapter
        mArray_List = new ArrayList<>();
        mArray_List.add("채팅창 시작합니다.");
        mArray_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, mArray_List);
        mLV_chat.setAdapter(mArray_Adapter);

    }


    //========================================================
    // 메시지 전송 액션 처리 메소드 (보내기버튼, 엔터키 공통 사용)
    //========================================================
    private void SendAction() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                msg = mET_message.getText().toString();
                if (msg.length() > 0) { sendThread.sendMessage(msg); }
            }
        }).start();
        mET_message.setText("");
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

    //========================================================
    // 버튼 액션
    //========================================================
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 서버 가동
            case R.id.btn_con_server :
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendServer = new Remote_Socket_Server();
                        sendServer.start();
                    }
                }).start();
                break;

            // 서버 접속
            case R.id.btn_con_remote :
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendThread = new Remote_Socket_Thread();
                        sendThread.connect(mHandler);

                    }
                }).start();
                break;

            // 메시지 전송
            case R.id.btn_send :
                SendAction();
                break;
        }
    }

    private void Message_Handler_Call() {
        // 실시간 메시지 리시브 핸들러
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                String msg = (String) inputMessage.obj;
                switch(inputMessage.what){
                    case 1 :
                        Result_Log("접속되었나?? " + msg);
                        break;
                    case 2 :
                        Result_Log("메시지 잘 받아오나?? " + msg);
                        break;
                    case 3 :
                        Result_Log("종료되었나?? " + msg);
                        break;
                    case 99 :
                        Result_Log("서버 접속 완료 " + msg);
                    case 100 :
                        Result_Log("서버 접속 에러 " + msg);
                        break;
                }
                msg = msg.replace("\r\n", "");
                mArray_List.add(msg);
                mArray_Adapter.notifyDataSetChanged();
                mLV_chat.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                //mTV_chat.append("\n" + msg);
            }
        };
    }

}
