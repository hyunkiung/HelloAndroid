package com.example.administrator.helloandroid.pkg_network;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015-04-17.
 */
public class Remote_Socket_Server {

    private static final int PORT = 5000;

    private List<Remote_Socket_Info> mClientList;		// Client 목록 리스트
    private ServerSocket mServerSocket;			// 서버 소켓

    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    public Remote_Socket_Server() {
        // 동기화 된 ArrayList
        mClientList = Collections.synchronizedList(new ArrayList<Remote_Socket_Info>());
    }

    public void start() {
        Socket socket;

        try {
            mServerSocket = new ServerSocket(PORT);
            Result_Log("Remote_Socket_Server : Server Start : " + mServerSocket);

            while (true) {
                socket = mServerSocket.accept();
                Result_Log("Remote_Socket_Server : " + socket.getInetAddress() + "에서 접속함");

                new ServerReciver(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addClient(Remote_Socket_Info client) {
        mClientList.add(client);
        sendToAll(client.getNickName() + " 님이 접속하였습니다. " + mClientList.size() + "명 접속중");
    }

    private void removeClient(Remote_Socket_Info client) {
        mClientList.remove(client);
        sendToAll(client.getNickName() + " 님이 퇴장하였습니다. " + mClientList.size() + "명 접속중");
    }

    private void sendToAll(String message) {
        Result_Log("Remote_Socket_Server : sendToAll() : " + message);

        // 멀티 스레딩 처리
        // 여러 쓰레드에서 mClientList 에 접근 시 하나의 쓰레드만 사용하도록 하는 방법
        synchronized (mClientList) {
            //for (int i = 0; i < mClientList.size(); i++)
            for (Remote_Socket_Info client : mClientList) {
                try {
                    client.getOutput().writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ServerReciver extends Thread {
        private DataInputStream mInputStream;
        private DataOutputStream mOutputStream;

        private Remote_Socket_Info mRemote_Socket_Info;

        public ServerReciver(Socket socket) {
            try {
                mInputStream = new DataInputStream(socket.getInputStream());
                mOutputStream = new DataOutputStream(socket.getOutputStream());

                String nickName = mInputStream.readUTF();

                mRemote_Socket_Info = new Remote_Socket_Info(nickName, mOutputStream);

                addClient(mRemote_Socket_Info);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // 계속 듣기만
                while (mInputStream != null) {
                    sendToAll(mInputStream.readUTF());
                }
            } catch (IOException e) {

            } finally {
                // 접속 종료시
                removeClient(mRemote_Socket_Info);
            }
        }
    }

    public static void main(String[] args) {
        new Remote_Socket_Server().start();
    }
}
