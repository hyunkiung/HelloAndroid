package com.example.administrator.helloandroid.pkg_network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2015-04-17.
 */
public class Remote_Client_Socket {
    private static String TAG = "로그 / 소켓 클라이언트 : ";
    public final String NICK = "현기웅";
    private final static String SERVER_HOST = "";
    private final static int SERVER_PORT = 0;

    private Socket mSocket;
    private ClientReciver mReceiveThread;
    private Handler mHandler = null;
    private String resultMessage = "";


    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) { Log.d(TAG, msg); }

    public static void main(String[] args) {
        new Remote_Client_Socket().connect(null, SERVER_HOST, SERVER_PORT);
    }

    public void connect(Handler handler, String sip, int sport) {
        mHandler = handler;
        try {
            Result_Log("error0");
            mSocket = new Socket(sip, sport);
            Result_Log("error0-1");
            mReceiveThread = new ClientReciver(mSocket, NICK, handler);
            mReceiveThread.start();
            Result_Log("error1");
            makeMessage(99, "서버에 접속되었습니다. : " + sip);
        } catch (UnknownHostException e) {
            Result_Log("error2");
            makeMessage(100, "서버 접속 에러입니다. 서버를 가동해주세요.1");
        } catch (IOException e) {
            Result_Log("error3");
            makeMessage(100, "서버 접속 에러입니다. 서버를 가동해주세요.2");
        } catch (RuntimeException e) {
            Result_Log("error : exception");
        }
    }

    public void sendMessage(String message) {
        mReceiveThread.sendMessage(message);
        //Result_Log("sendMessage 밖의 메소드(2)");
    }

    // 핸들러 전달 메소드
    private void makeMessage(int whatType, Object obj)
    {
        Message msg = Message.obtain();
        msg.what = whatType;
        msg.obj  = obj;
        mHandler.sendMessage(msg);
    }

    class ClientReciver extends Thread {

        private DataInputStream mInputStream;
        private DataOutputStream mOutputStream;

        public ClientReciver(Socket socket, String nickName, Handler handler) {
            try {
                mHandler = handler;
                mInputStream = new DataInputStream(socket.getInputStream());

                mOutputStream = new DataOutputStream(socket.getOutputStream());
                mOutputStream.writeUTF(nickName);
                mOutputStream.flush();

                resultMessage = "접속 완료";
                makeMessage(1, resultMessage);

            } catch (IOException e) {
                e.printStackTrace();
                //Result_Log("writeUTF IOException");
            }
        }

        public void sendMessage(String message) {
            if (mOutputStream != null) {
                try {
                    //Result_Log("sendMessage 안의 메소드(1)");
                    mOutputStream.writeUTF(message);
                    mOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void run() {
            try {
                // 계속 듣기만
                while (mInputStream != null) {
                    resultMessage = mInputStream.readUTF();
                    makeMessage(2, resultMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 접속 종료시
                makeMessage(3, "접속 종료");
                mSocket = null;
            }
        }
    }

    // 소켓 통신 종료 처리
    public void socketClose() {
        try {
            mReceiveThread = null;
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
