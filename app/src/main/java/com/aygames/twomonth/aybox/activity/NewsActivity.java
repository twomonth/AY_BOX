package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Logger;
import com.aygames.twomonth.aybox.utils.Util;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Response;

public class NewsActivity extends AppCompatActivity {

    private TextView tv_context,tv_count_news,tv_news_time,tv_news_title;
    private ImageView iv_back;
    private String nid;
    private String title,time,context,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        nid = getIntent().getStringExtra("nid");

        Toast.makeText(this,getIntent().getStringExtra("nid"),Toast.LENGTH_SHORT).show();
        initView();
        initData();
        iv_back.setOnClickListener(new View.OnClickListener() {
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
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/NewsInfo/nid/"+nid).execute();
                    JSONObject jsonObject =new JSONObject(response.body().string());
                    Logger.msg("新闻详情："+jsonObject.toString());
                    title = jsonObject.getString("new_title");
                    time = jsonObject.getString("create_time");
                    context = jsonObject.getString("new_centent");
                    count = jsonObject.getString("count");
                    Logger.msg("++++++++++++++:::"+URLDecoder.decode(context));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_news_title.setText(title);
                            tv_news_time.setText(Util.getStrTimeString(time));
                            tv_count_news.setText(count);
                            tv_context.setText(context);
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
        tv_context = findViewById(R.id.tv_context);
        tv_count_news = findViewById(R.id.tv_count_news);
        tv_news_time = findViewById(R.id.tv_news_time);
        tv_news_title = findViewById(R.id.tv_news_title);
        iv_back = findViewById(R.id.iv_back_news);
    }
}
