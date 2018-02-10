package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RebateOrderActivity extends AppCompatActivity {

    @Bind(R.id.et_smallcode_name)
    EditText et_smallcode_name;
    @Bind(R.id.et_gamename)
    EditText et_gamename;
    @Bind(R.id.et_service)
    EditText et_service;
    @Bind(R.id.et_heroname)
    EditText et_heroname;
    @Bind(R.id.et_money)
    EditText et_money;

    @Bind(R.id.tv_changeheroname)
    TextView tv_changeheroname;
    @Bind(R.id.tv_changeservice)
    TextView tv_changeservice;

    @Bind(R.id.bt_submit)
    Button bt_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_order);
        ButterKnife.bind(this);


    }
}
