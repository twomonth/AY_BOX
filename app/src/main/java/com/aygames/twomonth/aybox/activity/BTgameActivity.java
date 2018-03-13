package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;

public class BTgameActivity extends AppCompatActivity {

    private ImageView iv_btgames_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btgames);

        initView();
        initData();

        iv_btgames_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
    }

    private void initView() {
        iv_btgames_back = findViewById(R.id.iv_btgames_back);
    }
}
