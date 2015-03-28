
package com.example.administrator.helloandroid.pkg_thread;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.administrator.helloandroid.R;

/*
AsyncTask 클래스는 다음과 같이 중요한 callback들을 제공 함으로 상황에 맞게 오버라이딩 해야 한다.

protected void onPreExecute():
   Background 작업이 시작되자마자 UI스레드에서 실행될 코드를 구현해야 함.
   (예. background 작업의 시작을 알리는 text표현, background 작업을 위한 ProgressBar popup등)

protected abstract Result doInBackground(Params… params):
   Background에서 수행할 작업을 구현해야 함. execute(…) 메소드에 입력된 인자들을 전달 받음.

void onProgressUpdate(Progress... values):
   publishProgress(…) 메소드 호출의 callback으로 UI스레드에서 보여지는 background 작업 진행 상황을 update하도록 구현함.
   (예. ProgressBar 증가 등)

void onPostExecute(Result result):
   doInBackground(…)가 리턴하는 값을 바탕으로 UI스레드에 background 작업 결과를 표현하도록 구현 함.
   (예. background작업을 계산한 복잡한 산술식에 대한 답을 UI 위젯에 표현함 등)

void onCancelled():
   AsyncTask:cancel(Boolean) 메소드를 사용해 AsyncTask인스턴스의 background작업을 정지 또는 실행금지 시켰을 때
   실행되는 callback.
   background작업의 실행정지에 따른 리소스복구/정리 등이 구현될 수 있다.

또, AsyncTask 클래스는 background 작업의 시작과 background 작업 중 진행정보의 UI스레드 표현을 위해 다음과 같은 메소드를 제공한다.

final AsyncTask<…> execute(Params… params):
   Background 작업을 시작한다. 꼭 UI스레드에서 호출하여야 함.
   가변인자를 받아들임으로 임의의 개수의 인자를 전달할 수 있으며, 인자들은 doInBackground(…) 메소드로 전달된다.

final void publishProgress(Progress... values):
   Background 작업 수행 중 작업의 진행도를 UI 스레드에 전달 함. doInBackground(…)메소드 내부에서만 호출.
*/

public class thread05_Login extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "thread";
    private ProgressBar mPb_LoginWait;
    private ScrollView mSv_LoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread05_login);

        mPb_LoginWait = (ProgressBar) findViewById(R.id.pb_LoginWait);
        mSv_LoginForm = (ScrollView) findViewById(R.id.sv_LoginForm);

        findViewById(R.id.btn_LoginAction).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_LoginAction :
                new Login_AsyncTask().execute();
                break;
            default :
                break;
        }
    }

    private class Login_AsyncTask extends AsyncTask<Void, Void, Void> {

        // Background 작업 시작전에 UI 작업을 진행 한다 (쓰레드 수행 전 초기화)
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
            mPb_LoginWait.setVisibility(View.VISIBLE);
            mSv_LoginForm.setVisibility(View.INVISIBLE);
        }

        // background 작업 진행 (Thread 실행 내용)
        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "doInBackground");
            try {
                Thread.sleep(2000);
                ///////////////////
                publishProgress();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }


        // UI 업데이트 (Handler 에서 수행할 내용)
        @Override
        protected void onProgressUpdate(Void... values) {
            Log.d(TAG, "onProgressUpdate");
            mPb_LoginWait.setVisibility(View.GONE);
            mSv_LoginForm.setVisibility(View.VISIBLE);
        }

        // Background 작업이 끝난 후 UI 작업을 진행 한다
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute");
            AlertDialog.Builder dialog = new AlertDialog.Builder(thread05_Login.this);
            dialog.setMessage("접속 OK");
            dialog.setNegativeButton("CLOSE", null);
            dialog.show();
        }
    }
}
