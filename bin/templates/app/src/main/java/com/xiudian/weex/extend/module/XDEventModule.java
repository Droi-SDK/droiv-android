package com.xiudian.weex.extend.module;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.weex.commons.util.AppConfig;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.xiudian.weex.WXPageActivity;

import java.util.HashMap;
import java.util.Map;

import static com.xiudian.weex.extend.module.XDNavigatorModule.XIUDIAN;

public class XDEventModule extends WXModule {

    private static final String WEEX_ACTION = "com.xiudain.android.intent.action.WEEX";

    @JSMethod(uiThread = true)
    public void openURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String scheme = Uri.parse(url).getScheme();
        StringBuilder builder = new StringBuilder();
        if (TextUtils.equals("http", scheme) || TextUtils.equals("https", scheme) || TextUtils.equals("file", scheme)) {
            builder.append(url);
        } else {
            builder.append("http:");
            builder.append(url);
        }

        Uri uri = Uri.parse(builder.toString());
        String XDCategory = XIUDIAN + AppConfig.getXiudianId();
        Intent intent = new Intent(mWXSDKInstance.getContext(), WXPageActivity.class);
        intent.setAction(WEEX_ACTION);
        intent.setData(uri);
        intent.addCategory(XDCategory);
        mWXSDKInstance.getContext().startActivity(intent);

        if (mWXSDKInstance.checkModuleEventRegistered("event", this)) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("param1", "param1");
            params.put("param2", "param2");
            params.put("param3", "param3");
            mWXSDKInstance.fireModuleEvent("event", this, params);
        }
    }

    /*
     * a test method for macaca case, you can fire globalEvent when download finish、device shaked and so on.
     * @param event event name
    * */
    @JSMethod(uiThread = true)
    public void fireNativeGlobalEvent(String event, JSCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("eventParam", "value");

        mWXSDKInstance.fireGlobalEventCallback(event, params);
        if (null != callback) {
            Map<String, Boolean> result = new HashMap<String, Boolean>();
            result.put("ok", true);
            callback.invoke(result);
        }
    }
}
