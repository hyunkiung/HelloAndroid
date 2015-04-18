package com.example.administrator.helloandroid.pkg_network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2015-04-17.
 */
public class Local_Socket_Server implements Runnable
{
    private final int PORT = 5000;

    InputStreamReader isr;
    BufferedReader br;

    OutputStreamWriter osw;
    BufferedWriter bw;
    PrintWriter pw;

    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    @Override
    public void run()
    {
        try
        {
            Result_Log("서버 ON...");
            ServerSocket socket = new ServerSocket(PORT);

            while (true)
            {
                Result_Log("Receiving...");
                Socket soc = socket.accept();

                try
                {
                   // 클라이언트->서버
                    isr = new InputStreamReader(soc.getInputStream(), "UTF-8");
                    br = new BufferedReader(isr);
                    String str = br.readLine();
                    Result_Log("클라->서버.\t" + str);

                    // 서버->클라이언트
                    osw = new OutputStreamWriter(soc.getOutputStream(), "UTF-8");
                    bw = new BufferedWriter(osw);
                    pw = new PrintWriter(bw, true);
                    pw.println(str);
                    Result_Log("서버->클라.\t" + str);
                } catch (Exception e) {
                    Result_Log("Error: " + e.toString());
                    e.printStackTrace();
                } finally
                {
                    soc.close();
                    Result_Log("전송완료... ... ...");
                }
            }//while (true)
        } catch (Exception e)
        {
            Result_Log("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Thread serverThread = new Thread(new Local_Socket_Server());
        serverThread.start();
    }
}
