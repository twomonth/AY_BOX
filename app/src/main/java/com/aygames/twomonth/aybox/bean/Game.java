package com.aygames.twomonth.aybox.bean;

import java.io.Serializable;

public class Game implements Serializable {
    public String ico_url;
    public String app_name_cn;
    public String gid;
    public String game_size;
    public String app_type;
    public String publicty;
    public String service;
    public String time;

    public int type;

    public Game (String ico_url,String app_name_cn,String gid){
        this.ico_url = ico_url;
        this.app_name_cn = app_name_cn;
        this.gid = gid;
    }

    public Game(String ico_url, String app_name_cn, String gid, String game_size, String app_type) {
        this.ico_url = ico_url;
        this.app_name_cn = app_name_cn;
        this.gid = gid;
        this.game_size = game_size;
        this.app_type = app_type;
    }

    public Game(String ico_url, String app_name_cn, String gid, String game_size, String app_type,String publicty) {
        this.ico_url = ico_url;
        this.app_name_cn = app_name_cn;
        this.gid = gid;
        this.game_size = game_size;
        this.app_type = app_type;
        this.publicty = publicty;
    }

    public Game(String ico_url,String app_name_cn,String gid, String time, String service ,String app_type,boolean b){
        this.ico_url = ico_url;
        this.app_name_cn = app_name_cn;
        this.gid = gid;
        this.time = time;
        this.service = service;
        this.app_type = app_type;
    }
    public Game(String ico_url,String app_name_cn,String gid, String time, String service ,String app_type,int type){
        this.ico_url = ico_url;
        this.app_name_cn = app_name_cn;
        this.gid = gid;
        this.time = time;
        this.service = service;
        this.app_type = app_type;
        this.type = type;
    }
}