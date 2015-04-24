package com.example.administrator.helloandroid.pkg_network;

/**
 * Created by Administrator on 2015-04-20.
 */
public class Remote_Client_Info {
    private String nick;
    private String message;
    private String wdt;
    private String who;

    public Remote_Client_Info(String nick, String message, String wdt, String who) {
        this.nick = nick;
        this.message = message;
        this.wdt = wdt;
        this.who = who;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWdt() {
        return wdt;
    }

    public void setWdt(String wdt) {
        this.wdt = wdt;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
