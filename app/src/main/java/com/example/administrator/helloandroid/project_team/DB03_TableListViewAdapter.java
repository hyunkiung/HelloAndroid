package com.example.administrator.helloandroid.project_team;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-04-04.
 */
public class DB03_TableListViewAdapter extends BaseAdapter  {

    // 문자열을 보관 할 ArrayList
    private ArrayList<String> m_List;
    private Context mContext;
    private CustomHolder holder;


    // ViewHolder 패턴
    private class CustomHolder {
        TextView mTV_name;
        ImageButton mBtn_drop;
    }

    // 생성자
    public DB03_TableListViewAdapter(Context context, ArrayList<String> arrayList) {
        mContext = context;
        m_List = arrayList;
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public String getItem(int position) {
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
        if(v == null) {
            holder = new CustomHolder();
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_db03_listview_cell, null);
            holder.mTV_name = (TextView)v.findViewById(R.id.tv_tlist_name);
            holder.mBtn_drop = (ImageButton)v.findViewById(R.id.btn_tlist_drop);
            v.setTag(holder);
        }
        else {
            holder = (CustomHolder) v.getTag();
        }

        String tbl = m_List.get(pos);
        // 데이터 여부에 따라 TextView, Button 출력 처리
        if (!TextUtils.isEmpty(tbl)) {
            holder.mTV_name.setText(tbl);
            if(tbl.equals("NONE_TABLE")) {
                holder.mBtn_drop.setVisibility(View.INVISIBLE);
            } else {
                holder.mBtn_drop.setVisibility(View.VISIBLE);
            }
        }

        // 테이블명 클릭 이벤트
        holder.mTV_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, m_List.get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        // DROP 버튼 클릭 이벤트
        holder.mBtn_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DropRequest_Dialog(m_List.get(pos));
            }
        });

        return v;
    }

    // DROP 버튼 처리 다이얼로그 메소드
    public void DropRequest_Dialog(final String tbl) {
        // 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(tbl + "삭제");
        builder.setMessage("테이블을 삭제하시겠습니까??");
        builder.setNegativeButton("취소", null);
        builder.setPositiveButton("요청", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 부모 액티비티에서 받은 컨텍스트의 메소드 실행
                // Activity에서 커스텀뷰를 생성할 때 생성자로 Context를 넘겨 줌으로써 Context를 이용한
                // Activity 메서드 호출 할 수 있게 해주는 방식. 단 메서드는 public 메서드만 접근
                ((DB03_TableActivity) mContext).TABLE_Drop_To_Helper(tbl);
            }
        });
        // 생성한 빌더를 객체에 담아서 실행
        AlertDialog dialog = builder.create();
        //단계2 : 다이얼로그에서 확인버튼을 누르면 텍스트뷰에 전송요청중이라고 띄우고 3초뒤 전송시작이라고 바뀌고 프로그래스바 실행
        dialog.show();

    }

}
