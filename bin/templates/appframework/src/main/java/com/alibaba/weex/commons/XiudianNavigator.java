package com.alibaba.weex.commons;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.appfram.navigator.IActivityNavBarSetter;
import com.taobao.weex.common.Constants;
import com.taobao.weex.utils.WXLogUtils;

/**
 * Created by chenpei on 2017/10/13.
 */

public class XiudianNavigator implements IActivityNavBarSetter {

    private final static String INSTANCE_ID = "instanceId";
    private final static String TAG = "XiudianNavigator";
    private final static String XIUDIAN = "com.xiudian.android.intent.category.XIUDIAN01";
    private final static String URL = "url";

    public WXSDKInstance mWXSDKInstance;

    public XiudianNavigator(WXSDKInstance wxsdkInstance) {
        if (mWXSDKInstance == null) {
            mWXSDKInstance = wxsdkInstance;
        }
    }

    @Override
    public boolean push(String param) {
        try {
            JSONObject jsonObject = JSON.parseObject(param);
            String url = jsonObject.getString(URL);
            if (!TextUtils.isEmpty(url)) {
                Uri rawUri = Uri.parse(url);
                String scheme = rawUri.getScheme();
                Uri.Builder builder = rawUri.buildUpon();
                if (TextUtils.isEmpty(scheme)) {
                    builder.scheme(Constants.Scheme.HTTP);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
                intent.addCategory(XIUDIAN);
                intent.putExtra(INSTANCE_ID, mWXSDKInstance.getInstanceId());
                mWXSDKInstance.getContext().startActivity(intent);
            }
        } catch (Exception e) {
            WXLogUtils.eTag(TAG, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean pop(String s) {
        if (mWXSDKInstance.getContext() instanceof Activity) {
            ((Activity) mWXSDKInstance.getContext()).finish();
        }
        return true;
    }

    @Override
    public boolean setNavBarRightItem(String s) {
        return false;
    }

    @Override
    public boolean clearNavBarRightItem(String s) {
        return false;
    }

    @Override
    public boolean setNavBarLeftItem(String s) {
        return false;
    }

    @Override
    public boolean clearNavBarLeftItem(String s) {
        return false;
    }

    @Override
    public boolean setNavBarMoreItem(String s) {
        return false;
    }

    @Override
    public boolean clearNavBarMoreItem(String s) {
        return false;
    }

    @Override
    public boolean setNavBarTitle(String s) {
        return false;
    }
}
