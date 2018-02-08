package com.aygames.twomonth.aybox.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.NewsActivity;
import com.aygames.twomonth.aybox.adapter.NewsBaseAdapter;
import com.aygames.twomonth.aybox.bean.News;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_News_Fragment extends Fragment {

    private View view;
    private Button bt_huodong, bt_gonglue, bt_gonggao, bt_pt_gonggao;
    private ListView listView_news;
    int lastItem , totalItem ,sum;
    private LinearLayout ll_news_button;
    private RelativeLayout rl_fragment_news;
    int y;
    private ArrayList<News> list_news_all = new ArrayList<>();
    private NewsBaseAdapter baseAdapter;
    private int lable = 0 ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_news, null);

        initView();
        initDada();
        bt_huodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor2Text();
                bt_huodong.setTextColor(Color.WHITE);
                lable = 1;
                //清空数据
                list_news_all.clear();
                initDada();

            }
        });
        bt_gonglue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor2Text();
                bt_gonglue.setTextColor(Color.WHITE);
                lable =2;
                list_news_all.clear();
                initDada();
            }
        });
        bt_gonggao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor2Text();
                bt_gonggao.setTextColor(Color.WHITE);
                lable =3;
                list_news_all.clear();
                initDada();
            }
        });
        bt_pt_gonggao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor2Text();
                bt_pt_gonggao.setTextColor(Color.WHITE);
                lable = 4;
                list_news_all.clear();
                initDada();
            }
        });

        listView_news.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Logger.msg("改变了view:"+totalItem+"/"+lastItem+"/"+sum);
                if (lastItem == sum){
                    if (lastItem%20==0){
                        Snackbar.make(view, "正在加载更多......", Snackbar.LENGTH_SHORT).show();
                        getNewsMore(lastItem);
                    }else {
                        Snackbar.make(view, "没有更多了", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Logger.msg("第一个view:"+firstVisibleItem);
//                Logger.msg("可见的view:"+visibleItemCount);
//                Logger.msg("总共view:"+totalItemCount);
                totalItem = visibleItemCount;
                lastItem = firstVisibleItem+visibleItemCount;
                sum = totalItemCount;
            }
        });
        listView_news.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 触摸按下时的操作
//                        Toast.makeText(getContext(),"你摸我了！",Toast.LENGTH_SHORT).show();
                        Logger.msg("手指放上来了："+event.getY());
                        y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if ((int)event.getY()-y>300){
                            ll_news_button.setVisibility(View.VISIBLE);
                        }else if ((int)event.getY()-y<-300){
                            ll_news_button.setVisibility(View.GONE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        // 触摸抬起时的操作
                        break;
                }
                return false;
            }
        });
        listView_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("nid",list_news_all.get(position).newsid);
                intent.putExtra("title",list_news_all.get(position).newsTitle);
                startActivity(intent);
            }
        });


        return view;
    }

    private void initDada() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getNews/start/0"+"/labelname/"+lable).execute();
                    JSONArray jsonArray_news = new JSONArray(response.body().string());
                    Logger.msg("新闻:"+jsonArray_news.toString());
                    for (int i = 0; i < jsonArray_news.length(); i++) {
                        News news = new News();
                        news.newsid=jsonArray_news.getJSONObject(i).getString("nid");
                        news.newsTime = jsonArray_news.getJSONObject(i).getString("create_time");
                        news.newsTitle = jsonArray_news.getJSONObject(i).getString("new_title");
                        news.newsPicture = jsonArray_news.getJSONObject(i).getString("ico_url");
                        list_news_all.add(news);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baseAdapter = new NewsBaseAdapter(getContext(),list_news_all);
                            listView_news.setAdapter(baseAdapter);
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
        bt_huodong = view.findViewById(R.id.bt_huodong);
        bt_gonglue = view.findViewById(R.id.bt_gonglve);
        bt_gonggao = view.findViewById(R.id.bt_gonggao);
        bt_pt_gonggao = view.findViewById(R.id.bt_pt_gonggao);
        listView_news = view.findViewById(R.id.lv_news);
        ll_news_button = view.findViewById(R.id.ll_news_button);
        rl_fragment_news = view.findViewById(R.id.rl_fragment_news);
    }

    void setColor2Text() {
        bt_huodong.setTextColor(Color.BLACK);
        bt_gonglue.setTextColor(Color.BLACK);
        bt_gonggao.setTextColor(Color.BLACK);
        bt_pt_gonggao.setTextColor(Color.BLACK);
    }


    private void getNewsMore(final int item){
        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getNews/start/"+item+"/labelname/"+lable).execute();
                    JSONArray jsonArray_news = new JSONArray(response.body().string());
                    Logger.msg("新闻:"+jsonArray_news.toString());
                    for (int i = 0; i < jsonArray_news.length(); i++) {
                        News news = new News();
                        news.newsid=jsonArray_news.getJSONObject(i).getString("nid");
                        news.newsTime = jsonArray_news.getJSONObject(i).getString("create_time");
                        news.newsTitle = jsonArray_news.getJSONObject(i).getString("new_title");
                        news.newsPicture = jsonArray_news.getJSONObject(i).getString("ico_url");
                        list_news_all.add(news);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baseAdapter.notifyDataSetChanged();
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

    void getNewsOther(final String url){

        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = OkGo.get(url).execute();
                    JSONArray jsonArray_news = new JSONArray(response.body().string());
//                    Logger.msg("新闻:"+jsonArray_news.toString());
                    list_news_all.clear();
                    for (int i = 0; i < jsonArray_news.length(); i++) {
                        News news = new News();
                        news.newsid=jsonArray_news.getJSONObject(i).getString("nid");
                        news.newsTime = jsonArray_news.getJSONObject(i).getString("create_time");
                        news.newsTitle = jsonArray_news.getJSONObject(i).getString("new_title");
                        news.newsPicture = jsonArray_news.getJSONObject(i).getString("ico_url");
                        list_news_all.add(news);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baseAdapter = new NewsBaseAdapter(getContext(),list_news_all);
                            listView_news.setAdapter(baseAdapter);
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

}
