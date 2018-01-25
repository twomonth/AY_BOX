package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.DownloadAdapter;
import com.aygames.twomonth.aybox.adapter.DownloadedAdapter;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;
public class DownloadingActivity extends AppCompatActivity implements XExecutor.OnAllTaskEndListener{

    private OkDownload okDownload;
    private DownloadAdapter adapter;
    private DownloadedAdapter adapter_downloaded;
    private RecyclerView recyclerView ;
    private RecyclerView recycle_downloaded;
    private ImageView iv_download_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloading);

        iv_download_back = findViewById(R.id.iv_download_back);
        recyclerView = findViewById(R.id.recycleView_download);
        recycle_downloaded = findViewById(R.id.recycle_downloaded);
        initData();

        iv_download_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        okDownload = OkDownload.getInstance();

        adapter = new DownloadAdapter(this);
        adapter.updateData(DownloadAdapter.TYPE_ING);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter_downloaded = new DownloadedAdapter(this);
        adapter_downloaded.updateData(DownloadedAdapter.TYPE_FINISH);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recycle_downloaded.setLayoutManager(linearLayoutManager2);
        recycle_downloaded.setAdapter(adapter_downloaded);

        okDownload.addOnAllTaskEndListener(this);
    }

    @Override
    public void onAllTaskEnd() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
