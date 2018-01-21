package com.aygames.twomonth.aybox.activityfb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Logger;

public class ShareActivity extends AppCompatActivity {

    private ImageView iv_share_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Logger.msg("sdk分享数据：" + getIntent().getStringExtra("username")+","+getIntent().getStringExtra("gameid"));
        iv_share_back = (ImageView) findViewById(R.id.iv_share_back);
        iv_share_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
