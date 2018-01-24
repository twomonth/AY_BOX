package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.DownloadAdapter;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;


public class DownloadingActivity extends AppCompatActivity implements XExecutor.OnAllTaskEndListener{

    private OkDownload okDownload;
    private DownloadAdapter adapter;
    private RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloading);

        recyclerView = findViewById(R.id.recycleView_download);
        initData();
    }

    private void initData() {
        okDownload = OkDownload.getInstance();
        adapter = new DownloadAdapter(this);
        adapter.updateData(DownloadAdapter.TYPE_ING);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        okDownload.addOnAllTaskEndListener(this);
    }

    @Override
    public void onAllTaskEnd() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
