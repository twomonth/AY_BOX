package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.service.AyboxService;

public class UserActivity extends AppCompatActivity {

    private TextView tv_telephone,tv_passport;
    private ImageView iv_user_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();

        tv_passport.setText(AyboxService.passport);
        tv_telephone.setText(AyboxService.telephone);
        iv_user_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tv_passport = (TextView) findViewById(R.id.tv_passport);
        tv_telephone = (TextView) findViewById(R.id.tv_telephone);
        iv_user_back = (ImageView) findViewById(R.id.iv_user_back);
    }
}
