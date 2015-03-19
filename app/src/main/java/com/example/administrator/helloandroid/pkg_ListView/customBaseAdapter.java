
package com.example.administrator.helloandroid.pkg_ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.util.ArrayList;

// BaseAdapter를 확장으로 사용하면 경고표시 뜬다.
// alt+enter로 임플리먼트 매소드를 생성하면 아래 메소드들이 오버라이드 된다.
// getCount, getItem, getItemId, getView
public class customBaseAdapter extends BaseAdapter {

    // 문자열을 보관 할 ArrayList
    private ArrayList<String>   m_List;

    // 생성자
    public customBaseAdapter() {
        m_List = new ArrayList<>();
    }

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return m_List.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        View conV = convertView;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( conV == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            conV = inflater.inflate(R.layout.activity_item_use_base_adapter, parent, false);

            // TextView에 현재 position의 문자열 추가
            TextView textV = (TextView) convertView.findViewById(R.id.lv02_TxtV);

            // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
            //bug  text.setText(m_List.get(position));
            textV.setTag(m_List.get(pos));

            // 버그버그버그버그
            // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★


//            // 버튼을 터치 했을 때 이벤트 발생
//            Button btn = (Button) convertView.findViewById(R.id.lv02_Btn);
//            btn.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // 터치 시 해당 아이템 이름 출력
//                    Toast.makeText(context, m_List.get(pos), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            // 리스트 아이템을 터치 했을 때 이벤트 발생
//            convertView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // 터치 시 해당 아이템 이름 출력
//                    Toast.makeText(context, "리스트 클릭 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
//            convertView.setOnLongClickListener(new View.OnLongClickListener() {
//
//                @Override
//                public boolean onLongClick(View v) {
//                    // 터치 시 해당 아이템 이름 출력
//                    Toast.makeText(context, "리스트 롱 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
        }
        else{

        }

        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(String _msg) {
        m_List.add(_msg);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_List.remove(_position);
    }

}
