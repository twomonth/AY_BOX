package com.aygames.twomonth.aybox.activityfb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Logger;

public class RebateActivity extends AppCompatActivity {

    private ImageView iv_rebater_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate);

        String msg = getIntent().getStringArrayListExtra("msg").get(0)+","+getIntent().getStringArrayListExtra("msg").get(1)+","+getIntent().getStringArrayListExtra("msg").get(2);
        Logger.msg("sdk接收信息=>>返利："+ msg);

        iv_rebater_back = (ImageView) findViewById(R.id.iv_rebater_back);
        iv_rebater_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
