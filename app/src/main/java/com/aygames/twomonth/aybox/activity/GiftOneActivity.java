package com.aygames.twomonth.aybox.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.GiftOneBaseAdapter;
import com.aygames.twomonth.aybox.bean.GiftItem;
import com.aygames.twomonth.aybox.utils.Logger;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class GiftOneActivity extends AppCompatActivity {

    private ImageView iv_icon_gift , iv_giftone_back;
    private Button bt_game_particulars;
    private ListView listView;
    private TextView tv_gamename_gift;
    private String gid;
    private ArrayList<GiftItem> list;
    private String giftCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_one);

        gid = getIntent().getStringExtra("gid");
        Logger.msg("礼包页面启动获取参数"+gid);
        initView();
//        initData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String giftid = list.get(position).giftid;
                getGiftCode(giftid);
            }
        });

        bt_game_particulars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiftOneActivity.this, GameActivity.class);
                intent.putExtra("gid",gid);
                startActivity(intent);
            }
        });

        iv_giftone_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/LibaoInfo/gid/"+gid).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    final String icon_url=jsonObject.getString("ico_url");
                    final String gamename = jsonObject.getString("app_name_cn");
                    list = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("gift_list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        GiftItem giftItem = new GiftItem();
                        giftItem.giftid = jsonArray.getJSONObject(i).getString("lb_id");
                        giftItem.giftname = jsonArray.getJSONObject(i).getString("libao_name");
                        giftItem.giftinfo = jsonArray.getJSONObject(i).getString("libao_info");
                        list.add(giftItem);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_gamename_gift.setText(gamename);
                            Glide.with(getApplication()).load(icon_url).error(R.mipmap.ic_launcher).into(iv_icon_gift);
                            GiftOneBaseAdapter giftOneBaseAdapter = new GiftOneBaseAdapter(getApplicationContext(),list);
                            listView.setAdapter(giftOneBaseAdapter);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    Toast.makeText(GiftOneActivity.this,"youxiweizhaodao ",Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }

    private void initView() {
        iv_icon_gift = findViewById(R.id.iv_icon_gift);
        bt_game_particulars = findViewById(R.id.bt_game_particulars);
        listView = findViewById(R.id.lv_gift_content);
        tv_gamename_gift =  findViewById(R.id.tv_gamename_gift);
        iv_giftone_back = findViewById(R.id.iv_giftone_back);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_gift,null);
                        final TextView tv_giftcode = view1.findViewById(R.id.tv_giftcode);
                        Button bt_copy = view1.findViewById(R.id.bt_copy);
                        AlertDialog.Builder builder = new AlertDialog.Builder(GiftOneActivity.this);
                        builder.setView(view1);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        tv_giftcode.setText(giftCode);
                        bt_copy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(tv_giftcode.getText());
                                Toast.makeText(getApplicationContext(), "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }.start();
    }
}
