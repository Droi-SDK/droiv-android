package com.xiudian.weex;

import android.app.Application;

import com.alibaba.weex.commons.adapter.GlideImageAdapter;
//import com.alibaba.weex.commons.adapter.ImageAdapter;
import com.alibaba.weex.commons.util.AppConfig;
import com.alibaba.weex.extend.module.WXEventModule;
import com.droi.sdk.socialize.DroiSocialize;
import com.droi.sdk.socialize.platform.PlatformConfig;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.xiudian.weex.extend.component.XDTabbar;
import com.xiudian.weex.extend.component.XDVideo;
import com.xiudian.weex.extend.module.XDEventModule;
import com.xiudian.weex.extend.module.XDNavigatorModule;
import com.xiudian.weex.extend.module.XDShareModule;

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
        // DroiPush.initialize(this,"");
        // DroiSocialize.initialize(getApplicationContext());
        //initDebugEnvironment(true, false, "DEBUG_SERVER_HOST");
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        .setImgAdapter(new GlideImageAdapter())
                        .build()
        );

        try {
            WXSDKEngine.registerModule("xdevent", XDEventModule.class); // 可能用不到
            WXSDKEngine.registerModule("xdnavigator", XDNavigatorModule.class);
            WXSDKEngine.registerModule("xdshare", XDShareModule.class);
            WXSDKEngine.registerComponent("xdtabbar", XDTabbar.class);
            WXSDKEngine.registerComponent("xdvideo", XDVideo.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
        //Fresco.initialize(this);
        DroiSocialize.initialize(getApplicationContext());
        AppConfig.init(this);
        //PluginManager.init(this);

//        PlatformConfig.registerWeixin(AppConfig.getWeixinId(), AppConfig.getWeixinKey());
//        // 注册QQ
//        PlatformConfig.registerQQZone(AppConfig.getQQId(), AppConfig.getQQKey());
//        // 注册新浪
//        PlatformConfig.registerSinaWeibo(AppConfig.getWeixboId(), "http://sns.whalecloud.com", "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");

        // 注册微信
        PlatformConfig.registerWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        // 注册QQ
        PlatformConfig.registerQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        // 注册新浪
        PlatformConfig.registerSinaWeibo("3921700954", "http://sns.whalecloud.com", "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");
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
