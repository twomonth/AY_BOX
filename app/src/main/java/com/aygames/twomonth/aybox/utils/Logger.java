package com.aygames.twomonth.aybox.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/1/20.
 */

public class Logger {
    public static  final  String TAG = "aybox";
    public static final boolean b = true;

    public static void msg(String string){
        if (b){
            Log.i(TAG , string);
        }
    }
}
