
package com.example.administrator.helloandroid.pkg_file;

/**
 * Created by Administrator on 2015-06-04.
 */
public class Exam05_MediaDB_info {

    private long info_ID;
    private String info_DATA;
    private String info_ALBUM;
    private int info_ALBUM_ID;
    private String info_ARTIST;
    private String info_TITLE;
    private String info_DISPLAY_NAME;
    private String info_ALBUM_URI;
    private long info_DURATION;
    private long info_MIME_TYPE;
    private long info_SIZE;


    public Exam05_MediaDB_info() {
    }

    public long getInfo_ID() {
        return info_ID;
    }

    public void setInfo_ID(long info_ID) {
        this.info_ID = info_ID;
    }

    public String getInfo_DATA() {
        return info_DATA;
    }

    public void setInfo_DATA(String info_DATA) {
        this.info_DATA = info_DATA;
    }

    public String getInfo_ALBUM() {
        return info_ALBUM;
    }

    public void setInfo_ALBUM(String info_ALBUM) {
        this.info_ALBUM = info_ALBUM;
    }

    public int getInfo_ALBUM_ID() {
        return info_ALBUM_ID;
    }

    public void setInfo_ALBUM_ID(int info_ALBUM_ID) {
        this.info_ALBUM_ID = info_ALBUM_ID;
    }

    public String getInfo_ARTIST() {
        return info_ARTIST;
    }

    public void setInfo_ARTIST(String info_ARTIST) {
        this.info_ARTIST = info_ARTIST;
    }

    public String getInfo_TITLE() {
        return info_TITLE;
    }

    public void setInfo_TITLE(String info_TITLE) {
        this.info_TITLE = info_TITLE;
    }

    public String getInfo_DISPLAY_NAME() {
        return info_DISPLAY_NAME;
    }

    public void setInfo_DISPLAY_NAME(String info_DISPLAY_NAME) {
        this.info_DISPLAY_NAME = info_DISPLAY_NAME;
    }

    public long getInfo_DURATION() {
        return info_DURATION;
    }

    public void setInfo_DURATION(long info_DURATION) {
        this.info_DURATION = info_DURATION;
    }

    public long getInfo_MIME_TYPE() {
        // 리턴 마임타입
        return info_MIME_TYPE;
    }

    public void setInfo_MIME_TYPE(long info_MIME_TYPE) {
        // 저장 마임타입
        this.info_MIME_TYPE = info_MIME_TYPE;
    }

    public String getInfo_ALBUM_URI() {
        // 리턴 앨범 URI
        return info_ALBUM_URI;
    }

    public void setInfo_ALBUM_URI(String info_ALBUM_URI) {
        // 저장 앨범 URI
        this.info_ALBUM_URI = info_ALBUM_URI;
    }

    public long getInfo_SIZE() {
        // 리턴 사이즈
        return info_SIZE;
    }

    public void setInfo_SIZE(long info_SIZE) {
        // 저장 사이즈
        this.info_SIZE = info_SIZE;
    }
}
