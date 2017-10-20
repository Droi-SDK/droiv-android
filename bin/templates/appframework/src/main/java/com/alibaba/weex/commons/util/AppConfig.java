package com.alibaba.weex.commons.util;

import android.content.Context;

/**
 * Created by budao on 2016/10/12.
 */
public class AppConfig {
    private static final String TAG = "AppConfig";
    private static AppPreferences sPreferences = new AppPreferences();

    public static void init(Context context) {
        loadAppSetting(context);
    }

    public static String getLaunchUrl() {
        if (isLaunchLocally()) {
            return sPreferences.getString("local_url", "file://assets/dist/index.js");
        }
        return sPreferences.getString("launch_url", "http://127.0.0.1:8080/dist/index.js");
    }

    public static Boolean isLaunchLocally() {
        return sPreferences.getBoolean("launch_locally", false);
    }

    private static void loadAppSetting(Context context) {
        AppConfigXmlParser parser = new AppConfigXmlParser();
        parser.parse(context);
        sPreferences = parser.getPreferences();
    }

    public static String getWeixinId() {
        return sPreferences.getString("weixin_id", "");
    }

    public static String getWeixinKey() {
        return sPreferences.getString("weixin_key", "");
    }

    public static String getQQId() {
        return sPreferences.getString("qq_id", "");
    }

    public static String getQQKey() {
        return sPreferences.getString("qq_key", "");
    }

    public static String getWeixboId() {
        return sPreferences.getString("weibo_id", "");
    }

    public static String getWeiboKey() {
        return sPreferences.getString("weibo_key", "");
    }

    public static String getXiudianId() {return sPreferences.getString("xiudian_id","");}
}
