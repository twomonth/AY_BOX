package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Logger;
import com.aygames.twomonth.aybox.utils.Util;
import com.lzy.okgo.OkGo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Response;

public class NewsActivity extends AppCompatActivity {

    private TextView tv_count_news,tv_news_time,tv_news_title , tv_newstitle;
    private WebView web_context;
    private ImageView iv_back;
    private String nid ,newsTitle;
    private String title,time,context,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        nid = getIntent().getStringExtra("nid");
        newsTitle = getIntent().getStringExtra("title");
        initView();
        tv_newstitle.setText(newsTitle);
        web_context.loadUrl("http://sdk.aooyou.com/index.php/DataGames/NewsInfo/nid/"+nid);
        Logger.msg("http://sdk.aooyou.com/index.php/DataGames/NewsInfo/nid/"+nid);
        WebSettings webSettings = web_context.getSettings();
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。 这个取决于setSupportZoom(), 若
        web_context.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        initData();
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
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_news_title.setText(title);
                        tv_news_time.setText(Util.getStrTimeString(time));
                        tv_count_news.setText(count);
                        web_context.loadUrl("http://sdk.aooyou.com/index.php/DataGames/NewsInfo/nid/NEW1150043623195");
                        web_context.setWebViewClient(new WebViewClient(){
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }
                        });
                    }
                });
            }
        }.start();
    }

    private void initView() {
        web_context = findViewById(R.id.web_context);
        tv_count_news = findViewById(R.id.tv_count_news);
        tv_news_time = findViewById(R.id.tv_news_time);
        tv_news_title = findViewById(R.id.tv_news_title);
        iv_back = findViewById(R.id.iv_back_news);
        tv_newstitle = findViewById(R.id.tv_newstitle);
    }
}
