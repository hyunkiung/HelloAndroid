package com.example.administrator.helloandroid.pkg_network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2015-04-17.
 */

public class Local_Socket_Runnable implements Runnable {

    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    private String TAG = "TCP_Thread";
    private String msg;

    public Local_Socket_Runnable(String msg)
    {
        this.msg = msg;
    }

    // ==================================================
    // 스레드 & Runnable
    // ==================================================
    private final String IP = "localhost";
    public String msg_recive = new String();
    private BufferedWriter buffWrite;
    private PrintWriter printWrite;
    private BufferedReader buffRead = null;


    @Override
    public void run() {
        try
        {
            //Log.d(TAG, "run()");
            setSocket(IP, 5000);
            outStreamWrite = new OutputStreamWriter(soc.getOutputStream());// 출력 스트림
            buffWrite = new BufferedWriter(outStreamWrite);
            printWrite = new PrintWriter(buffWrite, true);
            printWrite.println(msg);//서버로 메시지 전송
            printWrite.flush();

            inStreamRead = new InputStreamReader(soc.getInputStream());// 입력 스트림
            buffRead = new BufferedReader(inStreamRead);
            msg_recive = buffRead.readLine();
        } catch (Exception e)
        {
            // TODO: handle exception
        } finally
        {
            try
            {
                soc.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    // ==================================================
    // GET & SET
    // ==================================================
    public String getMsg()
    {
        return msg_recive;
    }

    // ==================================================
    // 소켓통신
    // ==================================================
    private Socket soc;
    private OutputStreamWriter outStreamWrite;
    private InputStreamReader inStreamRead;

    private void setSocket(String ip, int port) throws IOException
    {
        try
        {
            soc = new Socket(ip, port);// 소켓 생성 & 연결 요청
        } catch (IOException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
