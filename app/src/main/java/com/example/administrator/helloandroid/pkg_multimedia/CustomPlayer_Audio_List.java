package com.example.administrator.helloandroid.pkg_multimedia;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomPlayer_Audio_List extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        CustomPlayer_Player.MyListener_PlayerPlay,
        CustomPlayer_Player.MyListener_PlayerPrevNext {

    private static String TAG = "로그 / 커스텀플레이어 list ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    private void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private ArrayList<HashMap<String, String>> mArray_List;

    private CustomAdapter mArray_Adapter;
    private ListView mLV_mp3list;
    private String mSD_Path;
    private String StrException[] = { "Preload", "mypeople" };

    private CustomPlayer_Player mCustomPlayer;
    private ActionBar acBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_player_audio_list);

        acBar = getSupportActionBar();
        acBar.hide();

        // 배열 및 미디어플레이어 선언
        mArray_List = new ArrayList<>();

        // 01. SD 카드가 없을 경우 처리
        SDCard_Check();

        // 02. SD 카드의 전체 목록 구하기
        SDCard_Root_List();

        // 04. 위젯 선언 및 리스너 생성
        ObjectReady_Setting();

        // 어레이어뎁터 설정, 미리 선언한 array_ITEMS 를 던졌다.
        mArray_Adapter = new CustomAdapter(getApplicationContext(),
                R.layout.simple_list_item_hku, mArray_List);
        mLV_mp3list.setAdapter(mArray_Adapter);

        // 플레이어 리스너 선언
        mCustomPlayer = (CustomPlayer_Player) findViewById(R.id.CustomPlayer);
        mCustomPlayer.setOnMyListener_PlayerPlay(this);
        mCustomPlayer.setOnMyListener_PlayerPrevNext(this);
    }

    //====================================================
    // 01. SD 카드가 없을 경우 처리
    //====================================================
    public void SDCard_Check() {
        String ext = Environment.getExternalStorageState();
        if (!ext.equals(Environment.MEDIA_MOUNTED)) {
            show_Toast("내장메모리용 SD 카드가 필요합니다.");
            this.finish();
            return;
        } else {
            show_Toast("내장메모리용 SD 카드가 감지되었습니다.");
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
        mLV_mp3list.setOnItemClickListener(this); // 04-1
    }

    //====================================================
    // *** 리스트뷰의 목록 클릭시 플레이 실행 요청 메소드
    //====================================================
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        show_Log("onItemClick 클릭클릭");

        mArray_Adapter.setSelectedPosition(position);
        mArray_Adapter.notifyDataSetChanged();
        mLV_mp3list.smoothScrollToPosition(position);
        HashMap<String, String> HM = mArray_Adapter.getItem(position);
        String filePath = HM.get("path");
        mCustomPlayer.Player_Call(filePath); // 플레이어에게 요청
    }


    //====================================================
    // *** 커스텀플레이어의 플레이 실행 요청 메소드
    //====================================================
    @Override
    public void CustomRun_Play() {
        // 리스트뷰에서 선택이 안된상태에서 플레이버튼을 눌렀을때 0번째를 실행하도록 요청한다.
        int pNum = mArray_Adapter.getSelectedPosition();
        if(pNum < 0) {
            pNum = 0;
        }
        CustomRun_Player_Connect(pNum);
    }

    //====================================================
    // *** 커스텀플레이어의 다음곡 실행 요청 메소드
    //====================================================
    @Override
    public void CustomRun_PrevNext(int value) {
        int nNum = mArray_Adapter.getSelectedPosition() + value;
        show_Log("CustomPlayer_Audio_List - CustomRun_PrevNext() nNum = " + nNum);

        if(nNum < 0) nNum = 0;

        // 리스트 마지막에 왔을때 전곡 반복시 마지막에서 처음으로 돌아간다
        if (mArray_Adapter.getCount() -1 < nNum) {
            if(mCustomPlayer.LOOP_STATUS == 3) {
                nNum = 0;
                CustomRun_Player_Connect(nNum);
            }
        } else {
            CustomRun_Player_Connect(nNum);
        }
    }


    //====================================================
    // *** 커스텀플레이어의 실제 재생 요청 처리
    //====================================================
    private void CustomRun_Player_Connect(int cNum) {
        mArray_Adapter.setSelectedPosition(cNum);
        mArray_Adapter.notifyDataSetChanged();
        mLV_mp3list.smoothScrollToPosition(cNum);
        HashMap<String, String> HM = mArray_Adapter.getItem(cNum);
        String filePath = HM.get("path");
        mCustomPlayer.Player_Call(filePath); // 플레이어에게 요청
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
            mText1.setTextColor(Color.BLACK);

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
        mCustomPlayer.Player_Clear();
    }


}
