package com.aygames.twomonth.aybox.fragment;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.GiftOneActivity;
import com.aygames.twomonth.aybox.adapter.GiftBaseAdapter;
import com.aygames.twomonth.aybox.bean.Gift;
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

public class Bt_Gift_Fragment extends Fragment {
    private View view ;
    private Button bt_gift_center,bt_gift_new;
    private ListView lv_gift;
    private ArrayList<Gift> giftArrayList;
    private String giftCode;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_gift, null);

        initView();
        initData();

        bt_gift_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_gift_center.setBackgroundResource(R.drawable.bt_back_gift_left);
                bt_gift_new.setBackgroundResource(R.drawable.bt_back_gift_right_no);
                initData();
            }
        });

        bt_gift_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_gift_center.setBackgroundResource(R.drawable.bt_back_gift_left_no);
                bt_gift_new.setBackgroundResource(R.drawable.bt_back_gift_right);
                initData2();
            }
        });




        return view;
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                giftArrayList = new ArrayList();
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getLibaolist").execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Logger.msg("礼包——————"+jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Gift gift = new Gift();
                        gift.app_name_cn = jsonArray.getJSONObject(i).getString("app_name_cn");
                        gift.ico_url = jsonArray.getJSONObject(i).getString("ico_url");
                        gift.gid = jsonArray.getJSONObject(i).getString("gid");
                        gift.lbcount = jsonArray.getJSONObject(i).getString("lbcount");
                        gift.publicity = jsonArray.getJSONObject(i).getString("publicity");
                        giftArrayList.add(gift);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GiftBaseAdapter giftBaseAdapter = new GiftBaseAdapter(getContext(),giftArrayList);
                            lv_gift.setAdapter(giftBaseAdapter);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lv_gift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(),GiftOneActivity.class);
                        intent.putExtra("gid",giftArrayList.get(position).gid);
                        startActivity(intent);
                    }
                });
            }
        }.start();
    }

    private void initData2() {
        new Thread(){
            @Override
            public void run() {
                giftArrayList = new ArrayList();
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getNewsTop").execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Logger.msg("礼包——————"+jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Gift gift = new Gift();
                        gift.app_name_cn = jsonArray.getJSONObject(i).getString("gamename")+jsonArray.getJSONObject(i).getString("libaoname");
                        gift.ico_url = jsonArray.getJSONObject(i).getString("ico_url");
                        gift.gid = jsonArray.getJSONObject(i).getString("lbid");
                        gift.lbcount = jsonArray.getJSONObject(i).getString("libaonum");
                        gift.publicity = jsonArray.getJSONObject(i).getString("publicity");
                        giftArrayList.add(gift);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GiftBaseAdapter giftBaseAdapter = new GiftBaseAdapter(getContext(),giftArrayList);
                            lv_gift.setAdapter(giftBaseAdapter);
                            lv_gift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    getGiftCode(giftArrayList.get(position).gid);
                                }
                            });
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
        bt_gift_center = view.findViewById(R.id.bt_gift_center);
        bt_gift_new = view.findViewById(R.id.bt_gift_new);
        lv_gift = view.findViewById(R.id.lv_gift);

    }

    void getGiftCode(final String giftId){
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getLb/lbid/"+giftId).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    int code = jsonObject.getInt("code");
                    if (code == 0){
                        giftCode = jsonObject.getString("cdk");
                    }else {
                        giftCode = "礼包已抢空";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_gift,null);
                        final TextView tv_giftcode = view1.findViewById(R.id.tv_giftcode);
                        Button bt_copy = view1.findViewById(R.id.bt_copy);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setView(view1);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        tv_giftcode.setText(giftCode);
                        bt_copy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager cm = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(tv_giftcode.getText());
                                Toast.makeText(getContext(), "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }.start();
    }

}
