
package com.example.administrator.helloandroid.project_team;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.administrator.helloandroid.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Parsing01_Main extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = Parsing01_Main.class.getSimpleName();
    private Parsing02_Adapter mAdapter;
    private ArrayList<Parsing03_Info> mParsing03_Info;
    private ListView mListView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mListView.setAdapter(mAdapter);
        }
    };

    String urlString, id, title, author, updated;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing01_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mListView = (ListView) findViewById(R.id.lv_recipe);
        mListView.setOnItemClickListener(this);

        mParsing03_Info = new ArrayList<>();

        // 0. ProgressDialog 띄우기
        mProgressBar.setVisibility(View.VISIBLE);

        // 1. web에 데이터 요청. 무조건 Thread사용해야 함
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();

                // q(검색어), orderby(정렬방법), max-results(결과개수), alt(데이터형),
                // format(포함해야 할 동영상 포맷형식), start-index(검색결과 배열중 첫번째 인덱스)
                String str_Url = "https://gdata.youtube.com/feeds/api/videos";
                String str_Search = "?q=이문세";
                String str_ListCount = "&max-results=50";
                String str_ListPage = "&start-index=1";
                String str_Etc = "&orderby=relevance&v=2&alt=json&format=1,6&safeSearch=none";

                urlString = str_Url + str_Search + str_ListCount + str_ListPage + str_Etc;

                try {
                    URI url = new URI(urlString);

                    HttpGet httpGet = new HttpGet();
                    httpGet.setURI(url);

                    // 응답을 받는 객체
                    HttpResponse response = httpClient.execute(httpGet);
                    String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

                    // 2. 요청 받은 내용을 파싱
                    jsonParsing(responseString);

                    // final. ProgressDialog 를 dismiss()
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });

                } catch (URISyntaxException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    // ================================================================
    // //// JSON 데이터 파싱 메소드 (JSON URL을 받는다)
    // ================================================================
    private void jsonParsing(String responseString) {
        try {
            // JSON 구문을 파싱해서 JSONArray 객체를 생성
            JSONObject jsonObject = new JSONObject(responseString);
            JSONObject jsonResult = jsonObject.getJSONObject("feed");
            JSONArray jsonArray = jsonResult.getJSONArray("entry");

            // 배열에서 엘리먼트 데이터 추출
            for (int i = 0; i < jsonArray.length(); i++) {
                String info = jsonArray.getJSONObject(i).getJSONObject("id").getString("$t");
                //tag:youtube.com,2008:video:KBkb2umZ9HU (4번째 id 데이터)
                id = info.split(":")[3];
                title = jsonArray.getJSONObject(i).getJSONObject("title").getString("$t");
                author = jsonArray.getJSONObject(i).getJSONArray("author").getJSONObject(0)
                        .getJSONObject("name").getString("$t");
                updated = jsonArray.getJSONObject(i).getJSONObject("updated").getString("$t");
                updated = updated.substring(0, 10);

                //Log.d(TAG, updated);
                Parsing03_Info par_Info = new Parsing03_Info(id, title, author, updated);
                mParsing03_Info.add(par_Info);
            }

            // 3. 파싱한 데이터를 어댑터에 설정
            mAdapter = new Parsing02_Adapter(Parsing01_Main.this, mParsing03_Info);

            // 4. 어댑터를 리스트 뷰에 설정 (UI 갱신)
            mHandler.sendEmptyMessage(0);

            Log.d(TAG, mParsing03_Info.toString());

        } catch (JSONException e) {
            Log.d("tag", "Parse Error");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 유튜브 동영상 플레이어 연결
        String videoId = mParsing03_Info.get(position).getId();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }
}

// 추가로 해볼것들
// 검색어 입력해서 URL 쿼리 만들어 조회하기
// 드래그해서 다음 페이지 받아오기
// youtube 재생 api 연동하기



/************************
 * JSON 데이터 *************************************************
 * *******************
 * ****************************************************************
 "id":{"$t":"tag:youtube.com,2008:video:KBkb2umZ9HU"},
 "published":{"$t":"2009-12-15T14:15:40.000Z"},
 "updated":{"$t":"2015-03-26T22:56:06.000Z"},
 "category":[
    {"scheme":"http://schemas.google.com/g/2005#kind","term":"http://gdata.youtube.com/schemas/2007#video"},
    {"scheme":"http://gdata.youtube.com/schemas/2007/categories.cat","term":"Music","label":"음악"}],
 "title":{"$t":"김규민 - 옛 이야기 (1991年)"},
 "content":{"type":"application/x-shockwave-flash",
 "src":"https://www.youtube.com/v/KBkb2umZ9HU?version=3&f=videos&app=youtube_gdata"},
 "link":[
     {"rel":"alternate","type":"text/html",
     "href":"https://www.youtube.com/watch?v=KBkb2umZ9HU&feature=youtube_gdata"},
     {"rel":"http://gdata.youtube.com/schemas/2007#video.related","type":"application/atom+xml",
     "href":"https://gdata.youtube.com/feeds/api/videos/KBkb2umZ9HU/related?v=2"},
     {"rel":"http://gdata.youtube.com/schemas/2007#mobile","type":"text/html",
     "href":"https://m.youtube.com/details?v=KBkb2umZ9HU"},
     {"rel":"http://gdata.youtube.com/schemas/2007#uploader","type":"application/atom+xml",
    "href":"https://gdata.youtube.com/feeds/api/users/N7gUXtFDTk1t7TTP-JtwAA?v=2"},
    {"rel":"self","type":"application/atom+xml",
    "href":"https://gdata.youtube.com/feeds/api/videos/KBkb2umZ9HU?v=2"}],
 "author":[
     {"name":{"$t":"pops8090"},
     "uri":{"$t":"https://gdata.youtube.com/feeds/api/users/pops8090"},
     "yt$userId":{"$t":"N7gUXtFDTk1t7TTP-JtwAA"}}],
 "yt$accessControl":[
     {"action":"comment","permission":"allowed"},
     {"action":"commentVote","permission":"allowed"},
     {"action":"videoRespond","permission":"moderated"},
     {"action":"rate","permission":"allowed"},
     {"action":"embed","permission":"allowed"},
     {"action":"list","permission":"allowed"},
     {"action":"autoPlay","permission":"allowed"},
     {"action":"syndicate","permission":"allowed"}],
 "gd$comments":{"gd$feedLink":{"rel":"http://gdata.youtube.com/schemas/2007#comments",
 "href":"https://gdata.youtube.com/feeds/api/videos/KBkb2umZ9HU/comments?v=2","countHint":37}},
 "yt$hd":{},
 "media$group":{
 "media$category":[
 {"$t":"Music","label":"음악","scheme":"http://gdata.youtube.com/schemas/2007/categories.cat"}],
 "media$content":[
 {"url":"https://www.youtube.com/v/KBkb2umZ9HU?version=3&f=videos&app=youtube_gdata",
 "type":"application/x-shockwave-flash",
 "medium":"video","isDefault":"true",
 "expression":"full","duration":281,"yt$format":5},
 {"url":"rtsp://r4---sn-a5m7zu7k.c.youtube.com/CiILENy73wIaGQl19Jnp2hsZKBMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
 "type":"video/3gpp","medium":"video",
 "expression":"full","duration":281,"yt$format":1},
 {"url":"rtsp://r4---sn-a5m7zu7k.c.youtube.com/CiILENy73wIaGQl19Jnp2hsZKBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
 "type":"video/3gpp","medium":"video",
 "expression":"full","duration":281,"yt$format":6}],
 "media$credit":[
 {"$t":"pops8090","role":"uploader","scheme":"urn:youtube","yt$display":"pops8090"}],
 "media$description":{"$t":"옛 이야기 (1991) 작사:박주연 / 작곡:하광훈 / 코러스:이승철, 하광훈.","type":"plain"},
 "media$keywords":{},
 "media$license":{"$t":"youtube","type":"text/html","href":"http://www.youtube.com/t/terms"},
 "media$player":{"url":"https://www.youtube.com/watch?v=KBkb2umZ9HU&feature=youtube_gdata_player"},
 "media$thumbnail":[
 {"url":"https://i.ytimg.com/vi/KBkb2umZ9HU/default.jpg",
 "height":90,"width":120,"time":"00:02:20.500","yt$name":"default"},
 {"url":"https://i.ytimg.com/vi/KBkb2umZ9HU/mqdefault.jpg",
 "height":180,"width":320,"yt$name":"mqdefault"},
 {"url":"https://i.ytimg.com/vi/KBkb2umZ9HU/hqdefault.jpg",
 "height":360,"width":480,"yt$name":"hqdefault"},
 {"url":"https://i.ytimg.com/vi/KBkb2umZ9HU/sddefault.jpg",
 "height":480,"width":640,"yt$name":"sddefault"},
 {"url":"https://i.ytimg.com/vi/KBkb2umZ9HU/1.jpg",
 "height":90,"width":120,"time":"00:01:10.250","yt$name":"start"},
 {"url":"https://i.ytimg.com/vi/KBkb2umZ9HU/2.jpg",
 "height":90,"width":120,"time":"00:02:20.500","yt$name":"middle"},
 {"url":"https://i.ytimg.com/vi/KBkb2umZ9HU/3.jpg",
 "height":90,"width":120,"time":"00:03:30.750","yt$name":"end"}],
 "media$title":{"$t":"김규민 - 옛 이야기 (1991年)","type":"plain"},
 "yt$aspectRatio":{"$t":"widescreen"},
 "yt$duration":{"seconds":"281"},
 "yt$uploaded":{"$t":"2009-12-15T14:15:40.000Z"},
 "yt$uploaderId":{"$t":"UCN7gUXtFDTk1t7TTP-JtwAA"},
 "yt$videoid":{"$t":"KBkb2umZ9HU"}},
 "gd$rating":{"average":4.8110237,"max":5,"min":1,"numRaters":381,"rel":"http://schemas.google.com/g/2005#overall"},
 "yt$statistics":{"favoriteCount":"0","viewCount":"208994"},
 "yt$rating":{"numDislikes":"18","numLikes":"363"}}
 */
