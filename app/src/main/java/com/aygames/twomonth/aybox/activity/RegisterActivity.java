package com.aygames.twomonth.aybox.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Constans;
import com.lzy.okhttputils.OkHttpUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/5.
 */

public class RegisterActivity extends AppCompatActivity {
    private ImageView iv_register_back;
    private EditText et_register_telephone,et_register_password;
    private Button bt_register;
    String telephone,password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        telephone = et_register_telephone.getText().toString();
        password = et_register_password.getText().toString();
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        JSONObject json = new JSONObject();
                        try {
                            json.put("telephont",telephone);
                            json.put("password",password);
                            json.put("iemi","iemi");
                            Response response = OkHttpUtils.post(Constans.URL_REGISTER).upJson(json.toString()).execute();
                            response.body();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });


    }

    private void initView() {
        iv_register_back = (ImageView) findViewById(R.id.iv_register_back);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_telephone = (EditText) findViewById(R.id.et_register_telephone);
        bt_register = (Button) findViewById(R.id.bt_register);
    }
}
