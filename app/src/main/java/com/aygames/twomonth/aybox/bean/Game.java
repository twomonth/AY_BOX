package com.aygames.twomonth.aybox.bean;

import java.io.Serializable;

public class Game implements Serializable {
    public String ico_url;
    public String app_name_cn;
    public String gid;
    public String game_size;
    public String app_type;

    public Game (String ico_url,String app_name_cn){
        this.ico_url = ico_url;
        this.app_name_cn = app_name_cn;
    }

    public Game(String ico_url, String app_name_cn, String gid, String game_size, String app_type) {
        this.ico_url = ico_url;
        this.app_name_cn = app_name_cn;
        this.gid = gid;
        this.game_size = game_size;
        this.app_type = app_type;
    }
}