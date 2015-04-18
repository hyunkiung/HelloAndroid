package com.example.administrator.helloandroid.pkg_network;

import java.io.DataOutput;

/**
 * Created by Administrator on 2015-04-17.
 */
public class Remote_Socket_Info {
    private String nickName;
    private DataOutput output;

    public Remote_Socket_Info(String nickName, DataOutput output) {
        this.nickName = nickName;
        this.output = output;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public DataOutput getOutput() {
        return output;
    }
    public void setOutput(DataOutput output) {
        this.output = output;
    }
}
