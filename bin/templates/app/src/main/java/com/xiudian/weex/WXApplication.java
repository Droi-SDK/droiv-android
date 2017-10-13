package com.xiudian.weex;

import android.app.Application;

import com.alibaba.weex.commons.adapter.GlideImageAdapter;
//import com.alibaba.weex.commons.adapter.ImageAdapter;
import com.alibaba.weex.commons.util.AppConfig;
import com.alibaba.weex.extend.module.WXEventModule;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.Core;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.xiudian.weex.extend.WXTabbar;

public class WXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Core.initialize(this);
        // DroiAnalytics
        //DroiAnalytics.initialize(this);
        //DroiAnalytics.setCrashReport(true);
        //DroiAnalytics.enableActivityLifecycleCallbacks(this);

        //DroiFeedback.initialize(this,"SkEuE51vQazqgQdEOApsAzTynlhVfAKVJX-tvbCcYMkw2an-drip0-kmzFLWxDgT");
        //DroiUpdate.initialize(this,"RkE4DAa9XEe5OD4GV_MC0kgMQETFFsh1_snj0UHVlM_w2WgiZAT4Cxxl_7Ih772W");
        // TODO: 2017/9/25 AppId ApplicationId
        // DroiPush.initialize(this,"");

        //initDebugEnvironment(true, false, "DEBUG_SERVER_HOST");
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        .setImgAdapter(new GlideImageAdapter())
                        .build()
        );

        try {
            WXSDKEngine.registerModule("event", WXEventModule.class);
            WXSDKEngine.registerComponent("mytabbar", WXTabbar.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
        //Fresco.initialize(this);
        AppConfig.init(this);
        //PluginManager.init(this);
    }

    /**
     * @param enable enable remote debugger. valid only if host not to be "DEBUG_SERVER_HOST".
     *               true, you can launch a remote debugger and inspector both.
     *               false, you can  just launch a inspector.
     * @param host   the debug server host, must not be "DEBUG_SERVER_HOST", a ip address or domain will be OK.
     *               for example "127.0.0.1".
     */
    private void initDebugEnvironment(boolean connectable, boolean enable, String host) {
        if (!"DEBUG_SERVER_HOST".equals(host)) {
            WXEnvironment.sDebugServerConnectable = connectable;
            WXEnvironment.sRemoteDebugMode = enable;
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
        }
    }

}
