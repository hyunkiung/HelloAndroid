package com.example.administrator.helloandroid.pkg_file;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Exam05_MediaDB extends AppCompatActivity {

    private static String TAG = "로그 / File Exam5";
    private static void show_Log(String msg) { Log.d(TAG, msg); }

    private int mFragPosition = 0;
    private String audioFilePath;
    private TextView mTV_count;
    private ContentResolver mConResolver;
    private Cursor mAudioCurosor;
    private Exam05_MediaDB_info mAudioInfo;
    private ArrayList<Exam05_MediaDB_info> mAudioArryList;
    private Exam05_MediaDB_Adapter mAudioAdapter;
    private ListView mLV_DataList;

    private int mSelectedPosition = -1;
    private String mSD_Path = null;
    private String StrException[] = { "Preload", "mypeople" };

    private File fileCur = null;
    private String StrExternal[] = { "ext_card", "external_sd", "ext_sd"
            , "external", "extSdCard", "externalSdCard", "external_SD", "sdcard1" };

    //====================================================
    // 내장 메모리 카드 체크
    //====================================================
    private void FindInSdCard_Check() {
        String ext = Environment.getExternalStorageState();
        if (!ext.equals(Environment.MEDIA_MOUNTED)) {
            mSD_Path = null;
        } else {
            mSD_Path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        //show_Log("in check mSD_Path = " + mSD_Path);
    }

    //====================================================
    // 외장 메모리 카드 체크
    //====================================================
    private void FindOutSdCard_Check() {
        for( String sPathCur : Arrays.asList(StrExternal)) {
            fileCur = new File( "/storage/", sPathCur);
            if( fileCur.isDirectory() && fileCur.canWrite()) {
                mSD_Path = fileCur.getAbsolutePath();
                break;
            }
        }

        if (mSD_Path == null) {
            fileCur = null;
            for( String sPathCur : Arrays.asList(StrExternal)) {
                fileCur = new File( "/mnt/", sPathCur);
                if( fileCur.isDirectory() && fileCur.canWrite()) {
                    mSD_Path = fileCur.getAbsolutePath();
                    break;
                }
            }
        }
        //show_Log("out check mSD_Path = " + mSD_Path);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam05_media_db);

        // 선택 프래그먼트에 따라 내부, 외부 SD 저장소 선택
        if (mFragPosition == 0) {
            FindInSdCard_Check();
        } else {
            FindOutSdCard_Check();
        }
        audioFilePath = mSD_Path;

        mAudioArryList = new ArrayList<>();
        mTV_count = (TextView) findViewById(R.id.tv_count);
        mLV_DataList = (ListView) findViewById(R.id.lv_DataList);

        AudioCursor_Select();

        mAudioAdapter = new Exam05_MediaDB_Adapter(getApplicationContext(), mAudioArryList, mSelectedPosition);
        mLV_DataList.setAdapter(mAudioAdapter);
    }




    private void AudioCursor_Select() {
        //----------------------------------------------------------------
        // MediaStore.Audio.Media 로 컨텐츠 검색 후, MediaStore.Audio.AudioColumns.ALBUM_ID 로
        // 다시 앨범 테이블을 검색해야 원하는 썸네일을 얻을 수 있다.
        String[] projection = {
                Audio.AudioColumns._ID,             // 레코드의 PK
                Audio.AudioColumns.DATA,            // 데이터스트림, 파일의 경로
                Audio.AudioColumns.ALBUM,           // 앨범명
                Audio.AudioColumns.ALBUM_ID,        // 앨범 아이디
                Audio.AudioColumns.ARTIST,          // 가수명
                Audio.AudioColumns.TITLE,           // 제목
                Audio.AudioColumns.DISPLAY_NAME,    // 파일표시명
                Audio.AudioColumns.DURATION,        // 총재생시간
                MediaStore.MediaColumns.MIME_TYPE,  // 마임 타입
                Audio.AudioColumns.SIZE,            // 파일 크기
                //Audio.AudioColumns.TRACK,           // 앨범 트랙위치
                //Audio.AudioColumns.YEAR,            // 발표년도
                //Audio.AudioColumns.BOOKMARK,        // 마지막 재생 위치
                //Audio.AudioColumns.IS_MUSIC,        // 음악파일 여부
                //Audio.AudioColumns.DATE_ADDED,      // 추가날짜, 초단위
                //Audio.AudioColumns.DATE_MODIFIED }; // 최종 갱신날짜, 초단위
        };

        String selection = Audio.Media.DATA + " like ?";
        String[] selectionArgs = { audioFilePath + "/%" };
        String sortOrder = Audio.Media.DATA + " ASC, " + Audio.Media.TITLE + " ASC";

        // SELECT _id, _data, album, album_id, artist, title, _display_name, mime_type, track, year, bookmark,
        // duration, _size, date_added, date_modified FROM audio
        // WHERE ( mime_type NOT IN('application/vnd.oma.drm.message',
        //                          'application/vnd.oma.drm.content',
        //                          'application/vnd.oma.drm.dcf'))
        // AND (_data like ?) ORDER BY title ASC
        mConResolver = getContentResolver();
        mAudioCurosor = mConResolver.query(Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
        mAudioCurosor.getCount();

        mTV_count.setText("카운트 = " + mAudioCurosor.getCount());

        if (mAudioCurosor.getCount() > 0) {
            mAudioCurosor.moveToFirst();
            for (int i = 0; i < mAudioCurosor.getCount(); i++) {
                mAudioCurosor.moveToPosition(i);
                mAudioInfo = new Exam05_MediaDB_info();

                mAudioInfo.setInfo_ID(mAudioCurosor.getLong(0));
                mAudioInfo.setInfo_DATA(mAudioCurosor.getString(1));
                mAudioInfo.setInfo_ALBUM(mAudioCurosor.getString(2));
                mAudioInfo.setInfo_ALBUM_ID(mAudioCurosor.getInt(3));
                mAudioInfo.setInfo_ARTIST(mAudioCurosor.getString(4));
                mAudioInfo.setInfo_TITLE(mAudioCurosor.getString(5));
                mAudioInfo.setInfo_DISPLAY_NAME(mAudioCurosor.getString(6));
                mAudioInfo.setInfo_DURATION(mAudioCurosor.getLong(7));
                mAudioInfo.setInfo_MIME_TYPE(mAudioCurosor.getLong(8));
                mAudioInfo.setInfo_SIZE(mAudioCurosor.getLong(9));

                Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                Uri sAlbumArtUri = ContentUris.withAppendedId(sArtworkUri, mAudioCurosor.getInt(3));

                //Uri album_uri = ContentUris.withAppendedId(Audio.Media.EXTERNAL_CONTENT_URI, mAudioCurosor.getLong(0));
                mAudioInfo.setInfo_ALBUM_URI(sAlbumArtUri.toString());

                mAudioArryList.add(mAudioInfo);
                //show_Log("aaaaaaaaaaaa " + album_uri.toString());
            }
        }
        mAudioCurosor.close();
    }
}

/*
query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

uri : 어떤 데이터를 가져올지 설정한다. 미리 정의되어 있는 URI를 넣으면 되는데 대표적인 주소들은 아래와 같다.
     Images.Media.EXTERNAL_CONTENT_URI
     Video.Media.EXTERNAL_CONTENT_URI
     Audio.Media.EXTERNAL_CONTENT_URI
     MediaStore.Files.getContentUri(“external”) : 모든 파일 정보
     외장 메모리카드만 뜻하는 것이 아니라 내부 메모리에 사용자 저장영역도 EXTERNAL에 포함 (INTERNAL은 시스템 내부)

projection : 원하는 Column을 문자열 배열 형식으로 지정할 수 있다. null 로 두면 모든 컬럼을 가져온다.

selection : 쿼리에 조건을 지정한다. SQL문의 WHERE 절에 들어가는 문장을 그대로 사용할 수 있다.

selectionArgs : selection 에서 ? 로 해놓은 문자열을 데이터로 치환시킬 수 있도록 인자로 넣을 수 있다.
                변수등을 활용해 동적으로 쿼리를 변화시킬 때 유용하다.

sortOrder : 정렬할 수 있다. ORDER BY 절로 사용하면 되며 DESC, ASC 등의 문자열로 처리 할 수있다.

String[] projection = {Images.ImageColumns.DATA, Images.ImageColumns.DATE_ADDED};
String selection = Images.ImageColumns.DATE_ADDED +" > ?";
String before24hour = ((new Date().getTime() - (60 * 60 * 24 * 1000)) / 1000)+"";
String[] selectionArgs = { before24hour };
String sortOrder = Images.ImageColumns.DATE_ADDED + " DESC";
mImageCursor = cr.query(Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);

DATE_ADDED > ? 문장은 ? 부분을 selectionArgs 에 나중에 변수로 지정하겠다는 의미

--------------------------------------------------------------------------------------------------
MediaColumns 인터페이스  (_COUNT, _ID 두 필드는 BaseColumns 상속받은 것이며 나머지는 미디어 DB가 정의)
--------------------------------------------------------------------------------------------------
_COUNT(_count)      int             레코드 개수
_ID(_id)            long            레코드의 pk
DATA(_data)         DATA_STREAM     데이터 스트림. 파일의 경로
SIZE(_size)         long            파일 크기
DISPLAY_NAME        text            파일 표시명
MIME_TYPE           text            마임 타입
TITLE(title)        text            제목
DATE_ADDED          long            추가 날짜. 초단위
DATE_MODIFIED       long            최후 갱신 날짜. 초단위


--------------------------------------------------------------------------------------------------
AudioColumns 인터페이스
--------------------------------------------------------------------------------------------------
ALBUM(album)        text            앨범명
ARTIST(artist)      text            가수명
BOOKMARK(bookmark)  long            마지막 재생 위치
DURATION(duration)  long            총 재생 시간
IS_MUSIC(is_music)  int             음악 파일 여부
TRACK(track)        int             앨범내의 트랙 위치
YEAR(year)          int             발표 년도

*/
