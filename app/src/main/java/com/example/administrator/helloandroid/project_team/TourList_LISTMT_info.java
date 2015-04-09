package com.example.administrator.helloandroid.project_team;

/**
 * Created by Administrator on 2015-04-09.
 */
public class TourList_LISTMT_info {

    public int _id;
    public String title1;
    public String title2;
    public String title3;
    public String contents;
    public String weather;
    public String companion;
    public String location;
    public int pid;
    public String tdt;
    public String wdt;
    public String edt;
    public String pFullUrl;

    public TourList_LISTMT_info() {
    }

    public TourList_LISTMT_info(int _id, String title1, String title2, String title3,
                                String contents, String weather, String companion, String location,
                                int pid, String tdt, String wdt, String edt, String pFullUrl) {
        this._id = _id;
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.contents = contents;
        this.weather = weather;
        this.companion = companion;
        this.location = location;
        this.pid = pid;
        this.tdt = tdt;
        this.wdt = wdt;
        this.edt = edt;
        this.pFullUrl = pFullUrl;
    }
}


