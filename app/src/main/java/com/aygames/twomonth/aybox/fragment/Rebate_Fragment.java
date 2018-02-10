package com.aygames.twomonth.aybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.bean.RebateTask;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/8.
 */

public class Rebate_Fragment extends Fragment {

    private View view;

    RecyclerView recyclerView;
    private ArrayList<RebateTask> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rebate,null);
        recyclerView = view.findViewById(R.id.recycle_rebate_task);
        initData();

        return view;
    }

    private void initData() {
        arrayList = new ArrayList<>();
        try {
            Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getUserFanli/appid/"+ "fy151427284615").execute();
            JSONArray jsonArray = new JSONArray(response.body().string());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RebateTask rebateTask = new RebateTask();
                rebateTask.amount = jsonObject.getString("amount");
                rebateTask.code = jsonObject.getInt("code");
                rebateTask.diff_amount = jsonObject.getString("diff_amount");
                rebateTask.gameid = jsonObject.getString("gameid");
                rebateTask.icon_url = jsonObject.getString("ico_url");
                rebateTask.server = jsonObject.getString("server");
                rebateTask.pay_id = jsonObject.getString("pay_id");
                rebateTask.roleid = jsonObject.getString("roleid");
                rebateTask.nickname = jsonObject.getString("nickname");
                rebateTask.username = jsonObject.getString("username");
                rebateTask.gamename = jsonObject.getString("gamename");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
