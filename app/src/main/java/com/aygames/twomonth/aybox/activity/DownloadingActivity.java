package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aygames.twomonth.aybox.R;
import com.lzy.okserver.OkDownload;

public class DownloadingActivity extends AppCompatActivity {

    private OkDownload okDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloading);

        initData();
    }

    private void initData() {
        okDownload = OkDownload.getInstance();
    }
}
