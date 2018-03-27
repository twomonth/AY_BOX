package com.aygames.twomonth.aybox.tencent;

import com.aygames.twomonth.aybox.utils.Logger;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/3/22.
 */

public class BaseUiListener implements IUiListener{
    @Override
    public void onComplete(Object o) {
        Logger.msg("腾讯返回："+o.toString());
    }

    @Override
    public void onError(UiError uiError) {
        Logger.msg("腾讯返回："+uiError.errorMessage);
    }

    @Override
    public void onCancel() {

    }
}
