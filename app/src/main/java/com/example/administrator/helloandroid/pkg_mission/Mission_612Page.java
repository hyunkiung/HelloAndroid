package com.example.administrator.helloandroid.pkg_mission;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class Mission_612Page extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 비디오플레이어 ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    //==============================================================
    private ListView mLV_VideoList;
    private Mission_612Page_adapter Adapter_Video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_612_page);

        mLV_VideoList  = (ListView) findViewById(R.id.lv_VidoeList);

        // 아답터 셋팅
        Adapter_Video = new Mission_612Page_adapter(getApplicationContext(), null, true);
        mLV_VideoList.setAdapter(Adapter_Video);

        // 클릭 이벤트 리스너
        mLV_VideoList.setOnItemClickListener(this);
        mLV_VideoList.setOnItemLongClickListener(this);

        // 로더 초기화
        getSupportLoaderManager().initLoader(0, null, this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Adapter_Video.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        Adapter_Video.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(0);
    }

    // 리스트뷰 클릭 이벤트 메소드 (액티비티 바로 실행)
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor mCursor = (Cursor)Adapter_Video.getItem(i);
        String mData = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

        Intent mIntent = new Intent(getApplicationContext(), Mission_612Page_video.class);
        mIntent.setData(Uri.parse(mData));
        startActivity(mIntent);

    }

    // 리스트뷰 롱클릭 이벤트 메소드 (액티비티 선택 실행)
    // 액티비티 호출할때 액티비티 포 리절트로 호출하고
    // 돌려줄때 셋리절트로 돌려준다.
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor mCursor = (Cursor)Adapter_Video.getItem(i);
        String mData = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

        try {
            Intent mIntent = new Intent(Intent.ACTION_VIEW);
            mIntent.setDataAndType(Uri.parse(mData), "video/*");
            startActivity(Intent.createChooser(mIntent, "실행할 앱을 선택하세요."));
//            startActivity(mIntent);
        } catch (ActivityNotFoundException e) {
            show_Log("Application");
        }
        return true;
    }
}
