package com.example.jacob.locateus.Data;

public class Message {
    private String user, msg;
    private long msgDate;

    public Message() {
    }

    public Message(String user, String msg, long msgDate) {
        this.user = user;
        this.msg = msg;
        this.msgDate = msgDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(long msgDate) {
        this.msgDate = msgDate;
    }


}
