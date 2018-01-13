package com.aygames.twomonth.aybox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;

/**
 * Created by Administrator on 2017/12/5.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView tv_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        tv_register = (TextView) findViewById(R.id.tv_register);
    }

}
