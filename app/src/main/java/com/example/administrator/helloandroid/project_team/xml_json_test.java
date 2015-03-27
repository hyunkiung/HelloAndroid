
package com.example.administrator.helloandroid.project_team;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class xml_json_test extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "Parser TAG : ";

    private ListView mLv_json;

    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    // JSON 데이터 주소
    private static String url = "http://api.learn2crack.com/android/jsonos/";
    // JSON 노드 이름
    private static final String TAG_OS = "android";
    private static final String TAG_VER = "ver";
    private static final String TAG_NAME = "name";
    private static final String TAG_API = "api";
    JSONArray android = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_json_test);

        mLv_json = (ListView)findViewById(R.id.lv_json);
        oslist = new ArrayList<HashMap<String, String>>();

        findViewById(R.id.btn_getJson).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getJson :
                new JSONParse().execute();
                break;
            default :
                break;
        }
    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        //JSON 데이터 로딩시 보이는 다이얼로그
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(xml_json_test.this);
            pDialog.setMessage("데이터를 가져오는 중입니다...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            // xml_json_Parser 클래스 객체 선언
            xml_json_Parser jParser = new xml_json_Parser();
            // 클래스에 JSON URL 던지고 json 오브젝트 객체로 받음.
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // JSON URL로 부터 배열 데이터 받아옴
                android = json.getJSONArray(TAG_OS);
                for(int i = 0; i < android.length(); i++) {
                    JSONObject c = android.getJSONObject(i);

                    // 변수에 JSON 아이템 저장
                    String str_ver = c.getString(TAG_VER);
                    String str_name = c.getString(TAG_NAME);
                    String str_api = c.getString(TAG_API);

                    // 해쉬맵 키에 데이터 추가
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_VER, str_ver);
                    map.put(TAG_NAME, str_name);
                    map.put(TAG_API, str_api);
                    oslist.add(map);

                    // 리스트 어뎁터에 배열 데이터 저장, 커스텀 리스트뷰 사용
                    ListAdapter adapter = new SimpleAdapter(xml_json_test.this, oslist,
                            R.layout.activity_xml_json_list_cell,
                            new String[] { TAG_VER, TAG_NAME, TAG_API }, new int[] {
                            R.id.vers,R.id.name, R.id.api});
                    mLv_json.setAdapter(adapter);

                    // 리스트뷰 클릭 리스너
                    mLv_json.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(xml_json_test.this, "클릭한것은 : " + oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
