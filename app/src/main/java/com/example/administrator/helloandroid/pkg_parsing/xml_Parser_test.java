package com.example.administrator.helloandroid.pkg_parsing;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class xml_Parser_test extends ActionBarActivity {

    private Builder listDialog;
    EditText urltest;
    String urlStr,tv,readLine,item;
    String tagName,title,body,link = null;
    ListView screen;
    StringBuffer sb;
    Button bt;
    int eventType;
    XmlPullParser xpp;
    XmlPullParserFactory factory;
    AlertDialog.Builder dialogBuilder;
    ArrayList<String> arrList,arrList2;
    ArrayAdapter<String> adapter,adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parser_test);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        bt = (Button)findViewById(R.id.bt);
        screen = (ListView)findViewById(R.id.screen);
        urltest = (EditText)findViewById(R.id.urltest);

        arrList = new ArrayList<String>();
        arrList2 = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrList);
        screen.setAdapter(adapter);

        dialog();

        urltest.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {
                dialog();
                return false;
            }
        });

        screen.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
                Log.d("fureun", parent.getItemAtPosition(position).toString());
                Log.d("fureun", arrList2.get(position).toString());
                dialogBuilder.setTitle(parent.getItemAtPosition(position).toString());
                dialogBuilder.setMessage(arrList2.get(position).toString());
                dialogBuilder.setPositiveButton("확인", null);
                dialogBuilder.show();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.clear();
                arrList.clear();
                adapter.notifyDataSetChanged();
                urlStr = urltest.getText().toString();
                NetworkThread thread = new NetworkThread();
                thread.setDaemon(true);
                thread.start();
                Log.d("fureun","작동중");
            }
        });
    }

    class NetworkThread extends Thread {
        public void run() {
            stream();
        }
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what ==0){
                Log.e("fureun", "list size: "+adapter.getCount());
                adapter.notifyDataSetChanged();
            }
        }
    };

    public void parsing(){
        try{
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(tv.trim()));
            eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    String tagName2 = xpp.getName();
                    if(tagName2.equals("item")){
                        while (eventType != XmlPullParser.END_DOCUMENT){
                            if(eventType == XmlPullParser.START_TAG){
                                tagName = xpp.getName();
                            }
                            else if(eventType == XmlPullParser.TEXT){
                                if(tagName!=null){
                                    if(tagName.equals("title")){
                                        title = xpp.getText().trim();
                                        Log.d("fureun", title+"("+title.length()+")");
                                        if (title.length() > 0) {
                                            arrList.add(title);
                                        }
                                    }else if(tagName.equals("description")){
                                        link = xpp.getText().trim();
                                        Log.d("fureun",link);
                                        if(link.length()>0){
                                            arrList2.add(link);
                                        }
                                    }
                                }
                            }
                            eventType = xpp.next();
                        }
                        Log.d("fureun","End document");
                    }
                }
                eventType = xpp.next();
            }
        }catch (Exception e){
            Log.e("fureun",e.toString());
        }
    }

    public void stream(){
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(isr);
            sb = new StringBuffer();

            while (true) {
                readLine = buf.readLine();
                if (readLine == null)
                    break;
                sb.append(readLine);
                sb.append("\n");
            }
            tv = sb.toString();
            parsing();

            handler.sendEmptyMessage(0);
            try{
                Thread.sleep(1000);
            }catch (Exception e){}
        }
        catch (Exception e) {
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public void dialog(){ //다이얼로그 메시지 출력부
        final String[] items = {"증권","경제/금융","부동산","산업","국제","정치","사회","스포츠/문화","사설/칼럼"}; //다이얼로그에 리스트 추가

        listDialog = new AlertDialog.Builder(this);
        listDialog.setTitle("목차").setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int val) {
                // TODO Auto-generated method stub
                Toast.makeText(xml_Parser_test.this, "선택 : " + items[val], Toast.LENGTH_SHORT).show();
                if(items[val].equals("증권")){
                    urltest.setText("http://rss.hankyung.com/new/news_stock.xml");

                }else if(items[val].equals("경제/금융")){
                    urltest.setText("http://rss.hankyung.com/new/news_economy.xml");
                }else if(items[val].equals("부동산")){
                    urltest.setText("http://rss.hankyung.com/new/news_estate.xml");
                }else if(items[val].equals("산업")){
                    urltest.setText("http://rss.hankyung.com/new/news_industry.xml");
                }else if(items[val].equals("국제")){
                    urltest.setText("http://rss.hankyung.com/new/news_intl.xml");
                }else if(items[val].equals("정치")){
                    urltest.setText("http://rss.hankyung.com/new/news_politics.xml");
                }else if(items[val].equals("사회")){
                    urltest.setText("http://rss.hankyung.com/new/news_society.xml");
                }else if(items[val].equals("스포츠/문화")){
                    urltest.setText("http://rss.hankyung.com/new/news_sports.xml");
                }else if(items[val].equals("사설/칼럼")){
                    urltest.setText("http://rss.hankyung.com/new/news_column.xml");
                }
            }
        }).setNegativeButton("직접입력",null).show();
    }
}
