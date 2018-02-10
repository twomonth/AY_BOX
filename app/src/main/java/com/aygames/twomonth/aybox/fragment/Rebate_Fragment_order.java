package com.aygames.twomonth.aybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/8.
 */

public class Rebate_Fragment_order extends Fragment {

    private View view;

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rebate_order,null);
        recyclerView = view.findViewById(R.id.recycle_rebate_order);
        initData();

        return view;
    }

    private void initData() {
//        try {
//            Response response = OkGo.get(""+ AyboxService.passport).execute();
//            JSONArray jsonArray = new JSONArray(response.body().string());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
