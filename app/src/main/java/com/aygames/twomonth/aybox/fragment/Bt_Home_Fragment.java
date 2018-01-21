package com.aygames.twomonth.aybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.bean.Game;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Home_Fragment extends Fragment {

    private View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_home, null);

        //读取网络数据游戏列表
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getGames").execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray_banner = jsonObject.getJSONArray("banner");
                    JSONArray jsojArary_listtj = jsonObject.getJSONArray("listtj");
                    JSONArray jsojArary_gameall = jsonObject.getJSONArray("gameall");

                    Logger.msg("轮播："+jsonArray_banner.toString());
                    Logger.msg("列表："+jsojArary_listtj.toString());
                    Logger.msg("所有："+jsojArary_gameall.toString());

//                    ArrayList list_banner = new ArrayList();
//                    for (int i = 0 ;i < jsonArray_banner.length();i++){
//                        JSONObject jsonObject_game = jsonArray_banner.getJSONObject(i);
//                        Game game = new Game(jsonObject_game.getString("ico_url"),jsonObject_game.getString("app_name_cn"));
//                        list_banner.add(game);
//                    }

                } catch (IOException e) {

                } catch (JSONException e) {

                }
            }
        }.start();


        return view;
    }
}
