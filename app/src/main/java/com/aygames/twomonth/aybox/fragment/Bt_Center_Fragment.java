package com.aygames.twomonth.aybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Center_Fragment extends Fragment {

    private View view ;

    private GridView gv_type,gv_ticai;
    private RelativeLayout rl_center;
    private String [] strings_type ,strings_ticai;
    private ArrayList list_type,list_ticai;
    private ToggleButton toggle;
    private JSONArray jsonArray_type,jsonArray_ticai;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_center, null);

        initView();
        initData();

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rl_center.setVisibility(View.VISIBLE);
                }else {
                    rl_center.setVisibility(View.GONE);
                }
            }
        });

        gv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String str = jsonArray_type.getString(position);
                    str = URLEncoder.encode(str);
                    Logger.msg("================================"+str);
                    Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
                    toggle.setChecked(false);
                    getGameFromStyle(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    private void initData() {
//        strings_type = getContext().getResources().getStringArray(R.array.type);
//        strings_ticai = getActivity().getResources().getStringArray(R.array.ticai);
        list_type = new ArrayList<HashMap<String, Object>>();
        list_ticai = new ArrayList<HashMap<String ,Object>>();
//        for (int i = 0; i < strings_type.length; i++) {
//            Map<String,Object> map = new HashMap<String, Object>();
//            map.put("text",strings_type[i]);
//            list_type.add(map);
//        }
//        for (int i = 0; i < strings_ticai.length; i++) {
//            Map<String,Object> map = new HashMap<String, Object>();
//            map.put("text",strings_ticai[i]);
//            list_ticai.add(map);
//        }
        final String [] from ={"text"};
        final int [] to = {R.id.tv_center_type_item};
//        SimpleAdapter simpleAdapter_type = new SimpleAdapter(getContext(),list_type,R.layout.item_center_type,from,to);
//        gv_type.setAdapter(simpleAdapter_type);
//        SimpleAdapter simpleAdapter_ticai = new SimpleAdapter(getContext(),list_ticai,R.layout.item_center_type,new String[]{"text"},new int[]{R.id.tv_center_type_item});
//        gv_ticai.setAdapter(simpleAdapter_ticai);
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getLabel").execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    jsonArray_type = jsonObject.getJSONArray("3");
                    jsonArray_ticai = jsonObject.getJSONArray("4");
                    for (int i = 0; i < jsonArray_type.length(); i++) {
                        HashMap<String,Object> map = new HashMap<String,Object>();
                        map.put("text",jsonArray_type.getString(i));
                        list_type.add(map);
                    }
                    for (int i = 0; i < jsonArray_ticai.length(); i++) {
                        HashMap<String,Object> map = new HashMap<String,Object>();
                        map.put("text",jsonArray_ticai.getString(i));
                        list_ticai.add(map);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleAdapter simpleAdapter_type = new SimpleAdapter(getContext(),list_type,R.layout.item_center_type,from,to);
                            gv_type.setAdapter(simpleAdapter_type);
                            SimpleAdapter simpleAdapter_ticai = new SimpleAdapter(getContext(),list_ticai,R.layout.item_center_type,new String[]{"text"},new int[]{R.id.tv_center_type_item});
                            gv_ticai.setAdapter(simpleAdapter_ticai);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void initView() {
        rl_center = view.findViewById(R.id.rl_center_type);
        gv_type = view.findViewById(R.id.gv_type);
        gv_ticai = view.findViewById(R.id.gv_ticai);
        toggle = view.findViewById(R.id.toggle);

    }

    void getGameFromStyle(final String string){
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getGameLabelInfo"+"/label/"+"3"+"/theme/"+string).execute();
                    Logger.msg("+++++++"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
