package com.aygames.twomonth.aybox.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.aygames.twomonth.aybox.R;

import com.aygames.twomonth.aybox.activityfb.RebateActivity;
import com.aygames.twomonth.aybox.utils.Constans;
import com.lzy.okgo.OkGo;



import java.io.IOException;

import okhttp3.Response;

public class RebateOrderActivity extends AppCompatActivity {

    private EditText et_smallcode_name,et_gamename,et_service,et_heroname,et_money;
    private Button bt_submit;
    String florderid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_order);

        initView();
        et_smallcode_name.setText(getIntent().getStringExtra("nickname"));
        et_gamename.setText(getIntent().getStringExtra("gamename"));
        et_service.setText(getIntent().getStringExtra("server"));
        et_heroname.setText(getIntent().getStringExtra("heroname"));
        et_money.setText(getIntent().getStringExtra("amount"));
        florderid = getIntent().getStringExtra("florderid");
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {

                        try {
                            Response response = OkGo.get(Constans.URL_REBATEORDER + "server/"+et_service.getText().toString()
                                    +"/heroname/"+et_heroname.getText().toString()
                                    +"/florderid/"+florderid).execute();
                            String code = response.body().toString();
                            if (code.equals("success")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                });
                            }else {
                                Toast.makeText(RebateOrderActivity.this,"提交失败，请联系客服。",Toast.LENGTH_SHORT).show();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
    }

    private void initView() {

        et_smallcode_name = findViewById(R.id.et_smallcode_name);
        et_gamename = findViewById(R.id.et_gamename);
        et_service = findViewById(R.id.et_service);
        et_heroname = findViewById(R.id.et_heroname);
        et_money = findViewById(R.id.et_money);
        bt_submit = findViewById(R.id.bt_submit);
    }
}
