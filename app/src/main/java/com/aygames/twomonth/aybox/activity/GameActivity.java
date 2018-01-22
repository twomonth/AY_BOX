package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class GameActivity extends AppCompatActivity {

    private ImageView iv_game_back;
    private String GID ;

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
    }

    private void initView() {
        iv_game_back = findViewById(R.id.iv_game_back);
    }

    private void initData() {
        GID = getIntent().getStringExtra("gid");
        new Thread(){
            @Override
            public void run() {
                OkGo.<String>get("").execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });
            }
        }.start();
    }
}
