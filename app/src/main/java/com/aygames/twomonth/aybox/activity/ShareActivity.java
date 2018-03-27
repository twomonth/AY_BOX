package com.aygames.twomonth.aybox.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.tencent.BaseUiListener;
import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.Logger;
import com.aygames.twomonth.aybox.wxapi.WXEntryActivity;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneTimeline;

public class ShareActivity extends AppCompatActivity implements WbShareCallback{

    private Button bt_weichate,bt_qq,bt_pengyouquan,bt_qqkongjian,bt_sinaweibo;
    IWXAPI iwxapi;
    Tencent mTencent;
    Bundle params  = new Bundle();
    BaseUiListener baseUiListener ;
    WbShareHandler wbShareHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initView();

        bt_weichate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share2wecht(1);
            }
        });
        bt_pengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share2wecht(2);
            }
        });
        bt_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share2QQ();
            }
        });
        bt_qqkongjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share2Qzeon();
            }
        });
        bt_sinaweibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wbShareHandler = new WbShareHandler(ShareActivity.this);
                wbShareHandler.registerApp();
                WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
                weiboMultiMessage.imageObject = getImageObj();
                wbShareHandler.shareMessage(weiboMultiMessage,false);

            }
        });
    }

    private void initView() {
        bt_pengyouquan = findViewById(R.id.bt_pengyouquan);
        bt_weichate = findViewById(R.id.bt_weichate);
        bt_qq = findViewById(R.id.bt_qq);
        bt_qqkongjian = findViewById(R.id.bt_qqkongjian);
        bt_sinaweibo = findViewById(R.id.bt_sinaweibo);
    }

    /**
     * 微信好友分享
     */
    private void share2wecht(int code){
        iwxapi = WXAPIFactory.createWXAPI(getApplicationContext(), Constans.APP_ID,true);
        iwxapi.registerApp(Constans.APP_ID);
        WXWebpageObject webpage =  new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com/";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "手游迷";
        msg.description = "官方描述";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_down_all);
        msg.thumbData = bmpToByteArray(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        if (code == 1){
            req.scene = WXSceneSession;
        }else {
            req.scene = WXSceneTimeline;
        }

        iwxapi.sendReq(req);
    }
    /**
     * 微信好友分享
     */
    private void share2QQ(){
        mTencent = Tencent.createInstance("1106460053", getApplicationContext());
        baseUiListener = new BaseUiListener();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "福利盒子");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "手游迷平台游戏大全");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.ofwan.com");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://cdn.symi.cn/Public/Settings/logo/2018-01-23/5a66ffb768bda.png");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "简介222222");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
        mTencent.shareToQQ(this, params, baseUiListener);
    }
    /**
     * Qzeon
     */
    private void share2Qzeon(){
        mTencent = Tencent.createInstance("1106460053", getApplicationContext());
        baseUiListener = new BaseUiListener();
        ArrayList arrayList = new ArrayList();
        arrayList.add("http://cdn.symi.cn/Public/Settings/logo/2018-01-23/5a66ffb768bda.png");
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "福利盒子");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "手游迷平台游戏大全");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.ofwan.com");//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, arrayList);
        mTencent.shareToQzone(this, params, new BaseUiListener());

    }


    /**
     * 得到Bitmap的byte
     *
     * @param bmp 图片
     * @return 返回压缩的图片
     */
    private static byte[] bmpToByteArray(Bitmap bmp) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 构建一个唯一标志
     *
     * @param type 分享的类型分字符串
     * @return 返回唯一字符串
     */
    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        baseUiListener = new BaseUiListener();
        Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
        Logger.msg("tencent"+requestCode+resultCode+data.toString());
        Log.i("tencent",requestCode+resultCode+data.toString());
//        if (requestCode == Constants.REQUEST_API) {
//            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
//                Tencent.handleResultData(data, baseUiListener);
//            }
//        }
    }


    @Override
    public void onWbShareSuccess() {

    }

    @Override
    public void onWbShareCancel() {

    }

    @Override
    public void onWbShareFail() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.msg("sina:"+"分享成功");
    }



    /**
     * 获取图片信息对象
     *
     * @return ImageObject
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        URL url;
        try {
            url = new URL("http://b.zol-img.com.cn/sjbizhi/images/9/320x510/1452499239911.jpeg");//这里输入图片地址
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                if (bitmap.getByteCount() > 4096000) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = bitmap.getByteCount() / 4096000;//缩放比例
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageObject.setImageObject(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imageObject;
    }
}
