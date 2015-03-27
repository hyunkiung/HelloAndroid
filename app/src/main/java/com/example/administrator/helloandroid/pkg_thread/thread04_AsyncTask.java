
package com.example.administrator.helloandroid.pkg_thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

/*
 AsyncTask는 Class에 있는 것으로 UI부분과 Thread 처리부분을 동시에 사용할 수 있다
 임의의 클래스에 AsyncTask를 상속받아 사용하며, Params, Progress, Result가 인수다.

 Params : execute()나 executeOnExecuter()의 메소드를 사용해서 파라미터를 클래스에 전달 하도록 하는 것.
 파라미터를 전달하면 doInBackground의 params를 이용해 값을 사용할 수 있다.
 만약에 String을 입력했으면 doInBackground의 파라미터 데이터타입이 String...으로 될 것이다.
 "..." 은 가변인자 이기 때문에 값 전달 할때 여러개를 전달하든지 하나를 전달하던지 할 수 있고,
 값을 읽어들이는 doInBackground에서는 "..."타입은 배열 형태이기 때문에 param[0] 이런식으로 값을 받아온다.

 Progress : onProgressUpdate()라는 메소드에서 사용
 Result : doInBackground에서 처리가 다 끝났으면 onPostExecute를 호출하게 되는데, 호출할 때 어떤 데이터 타입으로
 값을 넘겨줄 것 인지 알려주는 곳이다. 만약 Integer로 바꾸게되면 doInBackground() 메소드의 return type도
 Integer 형태가 될 것이고, onPostExecute()의 result 파라미터도 Integer 형태가 될 것이다.

 onPreExecute()와 onPostExecute() 메소드는 필수가 아니기 때문에 따로 구현.
 onPreExecute() = "나 시작할게요!" Dialog로 이야기하자면 show() 메소드를 불러온 것과 같은 뜻.
 보통 Loading을 표시를 시작할 때 사용.
 doInBackground() = 실제 값을 불러오는 Thread의 역할을 하는 곳.
 네트워크 통신을 심거나 Multi Processing을 할 기능들을 넣어줌.
 다 완료가 되었다면! 마지막으로,
 onPostExecute() = "나 이제 끝내요!" Dialog로 말하자면 dismiss() 메소드를 불러온 것.
 여기에서 UI를 갱신하는 기능을 넣어주면 완료.
 */
public class thread04_AsyncTask extends ActionBarActivity implements View.OnClickListener {

//    private static final String TAG = thread04_AsyncTask.class.getSimpleName();
    private static final String TAG = "thread";
    private ProgressBar mProgress1;
    private TextView mTv_stats1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread04_async_task);

        mProgress1 = (ProgressBar) findViewById(R.id.progress1);
        mTv_stats1 = (TextView) findViewById(R.id.tv_stats1);

        findViewById(R.id.btn_start1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start1:
                // AsyncTask 실행
                new My_AsyncTask().execute();
                break;
        }

    }

    private class My_AsyncTask extends AsyncTask {

        private int progress;

        // Background 작업 시작전에 UI 작업을 진행 한다 (쓰레드 수행 전 초기화)
        @Override
        protected void onPreExecute() {
            progress = 0;
            mProgress1.setProgress(progress);
            Log.d(TAG, "onPreExecute");
        }

        // background 작업 진행 (Thread 실행 내용)
        @Override
        protected Object doInBackground(Object[] params) {
            // Background 작업 조건
            while (mProgress1.getProgress() < mProgress1.getMax()) {
                Log.d(TAG, "doInBackground");
                progress += 2;

                mProgress1.setProgress(progress);

                // onProgressUpdate 가 수행 되도록 하는 메소드
                publishProgress(progress);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        // UI 업데이트 (Handler 에서 수행할 내용)
        @Override
        protected void onProgressUpdate(Object[] values) {
            mTv_stats1.setText("진행률 : " + values[0]);

            Log.d(TAG, "onProgressUpdate");
        }

        // Background 작업이 끝난 후 UI 작업을 진행 한다
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

}
