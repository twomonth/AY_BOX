package com.aygames.twomonth.aybox.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/19.
 */

public class SmallCode implements Serializable {

    public String username,nickname,create_time;

    public SmallCode(String username,String nickname,String create_time) {
        this.username = username;
        this.nickname = nickname;
        this.create_time = create_time;
    }

}
