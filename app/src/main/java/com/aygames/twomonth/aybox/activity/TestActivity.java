package com.aygames.twomonth.aybox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.lzy.okserver.task.XExecutor;

import java.io.File;

public class TestActivity extends AppCompatActivity implements XExecutor.OnAllTaskEndListener{

    String url;
    DownloadTask task;
    Button bt_start,bt_pause,bt_remove,bt_restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();

        url = getIntent().getStringExtra("url");

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRequest<File> request = OkGo.<File>get(url);
                task = OkDownload.request("taskTag",request).save();
                DownloadListener downloadListener = new DownloadListener(task) {
                    @Override
                    public void onStart(Progress progress) {
                        Logger.msg("onStart:"+progress);
                    }

                    @Override
                    public void onProgress(Progress progress) {
                        Logger.msg("onProgress:"+progress);
                    }

                    @Override
                    public void onError(Progress progress) {
                        Logger.msg("onError:"+progress);
                    }

                    @Override
                    public void onFinish(File file, Progress progress) {
                        Logger.msg("onFinish:"+progress);

                    }

                    @Override
                    public void onRemove(Progress progress) {
                        Logger.msg("onRemove:"+progress);

                    }
                };
                task.register(downloadListener);
            }
        });

        bt_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.pause();
            }
        });

        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.remove(true);
            }
        });

        bt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.restart();
            }
        });


    }

    private void initView() {
        bt_start = findViewById(R.id.bt_start);
        bt_pause = findViewById(R.id.bt_pause);
        bt_remove = findViewById(R.id.bt_remove);
        bt_restart = findViewById(R.id.bt_restart);
    }

    @Override
    public void onAllTaskEnd() {

    }
}
