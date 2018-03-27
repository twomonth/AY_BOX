package com.aygames.twomonth.aybox.wxapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneTimeline;

/**
 * Created by Administrator on 2018/3/20.
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(getApplicationContext(), Constans.APP_ID,true);
        api.handleIntent(getIntent(), this);
        api.registerApp(Constans.APP_ID);

    }
    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Toast.makeText(this,"resp.errCode:" + resp.errCode + ",resp.errStr:"
                + resp.errStr,Toast.LENGTH_SHORT).show();
        Logger.msg("resp.errCode:" + resp.errCode + ",resp.errStr:"
                + resp.errStr);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //分享拒绝
                break;
        }
    }

    /**
     * 微信好友分享
     */
    private void share2wecht(int code){
        api = WXAPIFactory.createWXAPI(getApplicationContext(), Constans.APP_ID,true);
        api.handleIntent(getIntent(), this);
        api.registerApp(Constans.APP_ID);
        WXWebpageObject webpage =  new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com/";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "手游迷";
        msg.description = "官方描述";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_down_all);
        msg.thumbData = bmpToByteArray(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        if (code == 1){
            req.scene = WXSceneSession;
        }else {
            req.scene = WXSceneTimeline;
        }

        api.sendReq(req);
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

}
