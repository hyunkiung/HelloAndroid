package com.example.administrator.helloandroid.pkg_network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-04-20.
 */
public class Remote_Client_Adapter extends BaseAdapter {

    // 문자열을 보관 할 ArrayList
    private ArrayList<Remote_Client_Info> m_List;
    private Context mContext;
    private CustomHolder holder;
    private Remote_Client mRemoteClient;
    private String sysName = mRemoteClient.SYSTEM_NAME;

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 클라이언트 아답터 : ";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    // ViewHolder 패턴
    private class CustomHolder {
        ImageView mIMG_chat_icon;
        TextView mTV_chat_nick;
        TextView mTV_chat_message;
        TextView mTV_chat_message_me;
        TextView mTV_chat_wdt;
        TextView mTV_chat_wdt_me;
        TextView mTV_chat_message_sys;
        LinearLayout mLL_you;
        LinearLayout mLL_me;
        LinearLayout mLL_system;
    }

    // 생성자
    public Remote_Client_Adapter(Context context, ArrayList<Remote_Client_Info> arrayList) {
        mContext = context;
        m_List = arrayList;
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int pos = position; // 테이블 이름
        View v = convertView;

        Remote_Client_Info chat_Info = (Remote_Client_Info) getItem(position);
        String nick = chat_Info.getNick();
        String message = chat_Info.getMessage();
        String wdt = chat_Info.getWdt();
        String who = chat_Info.getWho();

        if(v == null) {
            holder = new CustomHolder();
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.activity_remote_client_adapter_listview, null);

            holder.mLL_you = (LinearLayout)v.findViewById(R.id.ll_you);
            holder.mLL_me = (LinearLayout)v.findViewById(R.id.ll_me);
            holder.mLL_system = (LinearLayout)v.findViewById(R.id.ll_system);

            holder.mIMG_chat_icon = (ImageView)v.findViewById(R.id.img_chat_icon);
            holder.mTV_chat_nick = (TextView)v.findViewById(R.id.tv_chat_nick);
            holder.mTV_chat_message = (TextView)v.findViewById(R.id.tv_chat_message);
            holder.mTV_chat_message_me = (TextView)v.findViewById(R.id.tv_chat_message_me);
            holder.mTV_chat_message_sys = (TextView)v.findViewById(R.id.tv_chat_message_sys);
            holder.mTV_chat_wdt = (TextView)v.findViewById(R.id.tv_chat_wdt);
            holder.mTV_chat_wdt_me = (TextView)v.findViewById(R.id.tv_chat_wdt_me);

            v.setTag(holder);
        }
        else {
            holder = (CustomHolder) v.getTag();
        }

        if (!TextUtils.isEmpty(who)) {
            holder.mTV_chat_nick.setText(who);
        }

        if (!TextUtils.isEmpty(message)) {
            holder.mTV_chat_message.setText(message);
            holder.mTV_chat_message_me.setText(message);
            holder.mTV_chat_message_sys.setText(message + " " + wdt);
        }

        if (!TextUtils.isEmpty(wdt)) {
            holder.mTV_chat_wdt.setText(wdt);
            holder.mTV_chat_wdt_me.setText(wdt);
        }

        //===============================================
        // 접속자별로 레이아웃 표현
        //===============================================
        // 접속자가 나인 경우
        if(who.equals(nick)) {
            holder.mLL_me.setVisibility(View.VISIBLE);
            holder.mLL_you.setVisibility(View.GONE);
            holder.mLL_system.setVisibility(View.GONE);

        // 접속자가 시스템인 경우
        } else if (who.equals(sysName)) {
            holder.mLL_me.setVisibility(View.GONE);
            holder.mLL_you.setVisibility(View.GONE);
            holder.mLL_system.setVisibility(View.VISIBLE);

        // 접속자가 상대방인 경우
        } else {
            holder.mLL_me.setVisibility(View.GONE);
            holder.mLL_you.setVisibility(View.VISIBLE);
            holder.mLL_system.setVisibility(View.GONE);
        }

        return v;
    }
}
