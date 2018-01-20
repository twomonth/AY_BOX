package com.aygames.twomonth.aybox.activityfb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Logger;

public class WaiterActivity extends AppCompatActivity {

    private ImageView iv_waiter_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);

        String msg = getIntent().getStringArrayListExtra("msg").get(0)+","+getIntent().getStringArrayListExtra("msg").get(1)+","+getIntent().getStringArrayListExtra("msg").get(2);
        Logger.msg("sdk接收信息=>>客服："+ msg+"    "+getIntent().getStringExtra("username"));

        iv_waiter_back = (ImageView) findViewById(R.id.iv_waiter_back);
        iv_waiter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
