package com.aygames.twomonth.aybox.activity;


import android.content.Intent;

import android.os.Environment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.PictureAdapter;
import com.aygames.twomonth.aybox.bean.ApkModel;
import com.aygames.twomonth.aybox.listener.LogDownloadListener;
import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.Logger;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


public class GameActivity extends AppCompatActivity {

    private ImageView iv_game_back, iv_game_icon;
    private Button bt_game_download;
    private String GID, name, icon, size, introduced, url;
    private PictureAdapter pictureAdapter;
    private int type;
    private TextView tv_game_yxjj, tv_game_name, tv_game_size, tv_game_type;
    private RecyclerView recycle_game_yxjt;
    private JSONArray jsonArray_type, jsonArray_picture, jsonArray_welfare;
    private JSONObject jsonObject;
    private ArrayList list_type;
    private ArrayList list_welfare;
    private ArrayList list_picture;
    private TextView tv_game_welfare;
    private StringBuffer stringBuffer, stringBuffer_welfare;
    ApkModel apk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        initData();

        iv_game_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下载按钮
        bt_game_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetRequest<File> request = OkGo.<File>get(apk.url);
                OkDownload.request(apk.url, request)//
                        .fileName(apk.name+".apk")
//                        .priority(apk.priority)//
                        .extra1(apk)//
                        .save()//
                        .register(new LogDownloadListener() {
                        })//
                        .start();

                Intent intent = new Intent(GameActivity.this,DownloadingActivity.class);
                startActivity(intent);
            }
        });



    }

    private void initView() {
        iv_game_back = findViewById(R.id.iv_game_back);
        bt_game_download = findViewById(R.id.bt_game_download);
        tv_game_yxjj = findViewById(R.id.tv_game_yxjj);
        recycle_game_yxjt = findViewById(R.id.recycle_game_yxjt);
        iv_game_icon = findViewById(R.id.iv_game_icon);
        tv_game_name = findViewById(R.id.tv_game_name);
        tv_game_size = findViewById(R.id.tv_game_size);
        tv_game_type = findViewById(R.id.tv_game_type);
        tv_game_welfare = findViewById(R.id.tv_game_welfare);
    }

    private void initData() {

        //初始化下载器
        OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/twomonth/");
        OkDownload.getInstance().getThreadPool().setCorePoolSize(2);
        Logger.msg("下载路径："+OkDownload.getInstance().getFolder());
        //从数据库中护肤数据
        List<Progress> progressList = com.lzy.okgo.db.DownloadManager.getInstance().getAll();
        OkDownload.restore(progressList);

        GID = getIntent().getStringExtra("gid");
        type = getIntent().getIntExtra("type", 0);
        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = OkGo.get(Constans.URL_GAME + GID).execute();
//                    Logger.msg("游戏获取数据："+response.body().string());
                    jsonObject = new JSONObject(response.body().string());
                    name = jsonObject.getString("name");
                    icon = jsonObject.getString("image");
                    size = jsonObject.getString("size");
                    introduced = jsonObject.getString("introduced");
                    url = jsonObject.getString("url");

                    jsonArray_type = jsonObject.getJSONArray("type");
                    list_type = new ArrayList();
                    for (int i = 0; i < jsonArray_type.length(); i++) {
                        list_type.add(jsonArray_type.getString(i));
                    }
                    jsonArray_picture = jsonObject.getJSONArray("picture");
                    list_picture = new ArrayList();
                    for (int i = 0; i < jsonArray_picture.length(); i++) {
                        list_picture.add(jsonArray_picture.getString(i));
                    }
                    jsonArray_welfare = jsonObject.getJSONArray("welfare");
                    list_welfare = new ArrayList();
                    for (int i = 0; i < jsonArray_welfare.length(); i++) {
                        list_welfare.add(jsonArray_welfare.getString(i));
                    }
                    stringBuffer = new StringBuffer();
                    stringBuffer_welfare = new StringBuffer();
                    for (int i = 0; i < list_welfare.size(); i++) {
                        stringBuffer_welfare.append(list_welfare.get(i) + "\n");
                    }
                    for (int i = 0; i < list_type.size(); i++) {
                        stringBuffer.append("*" + list_type.get(i));
                    }
                    Logger.msg("游戏类型：" + stringBuffer.toString());
                    apk = new ApkModel(name,url,icon);
//                    ArrayAdapter arrayAdapter = new ArrayAdapter(GameActivity.this,android.R.layout.simple_list_item_1,list_welfare);
//                    lv_welfare.setAdapter(arrayAdapter);
//                    JSONArray jsonArray = jsonObject.getJSONArray("type");
//                    String test = jsonObject.getString("type");
//                    String welfare = jsonObject.getString("welfare");
//                    String picture = jsonObject.getString("picture");
//                    Logger.msg("======:"+jsonArray.getString(0)+jsonArray.getString(1));
//                    Logger.msg(test+"\n"+welfare+"\n"+picture+"\n");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(GameActivity.this).load(icon).error(R.mipmap.ic_launcher).into(iv_game_icon);
                        tv_game_name.setText(name);
                        tv_game_size.setText(size);
                        tv_game_yxjj.setText("    " + introduced);
                        pictureAdapter = new PictureAdapter(GameActivity.this, list_picture);
                        recycle_game_yxjt.setAdapter(pictureAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GameActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recycle_game_yxjt.setLayoutManager(linearLayoutManager);

                        tv_game_type.setText(stringBuffer.toString());
                        tv_game_welfare.setText(stringBuffer_welfare.toString());

                        pictureAdapter.setOnItemClickListener(new PictureAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getApplication(),"111",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                    }
                });
            }
        }.start();
    }


//    /** 检查SD卡权限 */
//    protected void checkSDCardPermission() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_PERMISSION_STORAGE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //获取权限
//            } else {
//                showToast("权限被禁止，无法下载文件！");
//            }
//        }
//    }

}
