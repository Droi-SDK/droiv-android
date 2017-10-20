package com.xiudian.share;

import com.droi.sdk.socialize.platform.PLATFORMS;

public final class PlatformBean {

    public String mKeyword;
    public String mShowWord;
    public String mIcon;
    public String mGrayIcon;
    public int mIndex;
    public PLATFORMS mPlatform;

    public PlatformBean() {
    }

    public static PlatformBean toSnsPlatform(PLATFORMS platform) {
        PlatformBean bean = new PlatformBean();
        if (platform.toString().equals("WEIXIN")) {
            bean.mShowWord = "droi_socialize_key_wechat";
            bean.mIcon = "droi_socialize_wechat";
            bean.mGrayIcon = "droi_socialize_wechat";
            bean.mIndex = 0;
            bean.mKeyword = "wechat";
        } else if (platform.toString().equals("WEIXIN_CIRCLE")) {
            bean.mShowWord = "droi_socialize_key_wechat_circle";
            bean.mIcon = "droi_socialize_wechat_circle";
            bean.mGrayIcon = "droi_socialize_wechat_circle";
            bean.mIndex = 0;
            bean.mKeyword = "wxcircle";
        } else if (platform.toString().equals("WEIXIN_FAVORITE")) {
            bean.mShowWord = "droi_socialize_key_wechat_favorite";
            bean.mIcon = "droi_socialize_wechat_favorite";
            bean.mGrayIcon = "droi_socialize_wechat_favorite";
            bean.mIndex = 0;
            bean.mKeyword = "wechatfavorite";
        } else if (platform.toString().equals("QQ")) {
            bean.mShowWord = "droi_socialize_key_qq";
            bean.mIcon = "droi_socialize_qq";
            bean.mGrayIcon = "droi_socialize_qq";
            bean.mIndex = 0;
            bean.mKeyword = "qq";
        } else if (platform.toString().equals("QZONE")) {
            bean.mShowWord = "droi_socialize_key_qzone";
            bean.mIcon = "droi_socialize_qzone";
            bean.mGrayIcon = "droi_socialize_qzone";
            bean.mIndex = 0;
            bean.mKeyword = "qzone";
        } else if (platform.toString().equals("SINA")) {
            bean.mShowWord = "droi_socialize_key_sina";
            bean.mIcon = "droi_socialize_sina";
            bean.mGrayIcon = "droi_socialize_sina";
            bean.mIndex = 0;
            bean.mKeyword = "sina";
        } else if (platform.toString().equals("SMS")) {
            bean.mShowWord = "umeng_socialize_sms";
            bean.mIcon = "umeng_socialize_sms";
            bean.mGrayIcon = "umeng_socialize_sms";
            bean.mIndex = 1;
            bean.mKeyword = "sms";
        } else if (platform.toString().equals("EMAIL")) {
            bean.mShowWord = "umeng_socialize_mail";
            bean.mIcon = "umeng_socialize_gmail";
            bean.mGrayIcon = "umeng_socialize_gmail";
            bean.mIndex = 2;
            bean.mKeyword = "email";
        }

        bean.mPlatform = platform;
        return bean;
    }
}
