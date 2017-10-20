package com.umeng.soexample.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.OAuthProvider;
import com.droi.sdk.socialize.DroiSocializeProxy;
import com.droi.sdk.socialize.platform.PLATFORMS;
import com.droi.sdk.socialize.wechat.WechatCallbackActivity;

public class WXEntryActivity extends WechatCallbackActivity {
    OAuthProvider authProvider;
    private final String TAG = "DroiSocial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = this;
        authProvider = OAuthProvider.createAuthProvider(OAuthProvider.AuthProvider.Weixin, getApplicationContext());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e(TAG, "onNewIntent in WXEntryActivity is called...");
        super.onNewIntent(intent);
        setIntent(intent);
        if (authProvider != null) {
            authProvider.handleActivityResult(0, 0, intent);
        }
    }

    private boolean isWechatInstalled() {
        DroiError error = new DroiError();
        boolean result = DroiSocializeProxy.getInstance(getApplicationContext()).isClientAppInstalled(PLATFORMS.WEIXIN, error);
        if (error.isOk() && result) {
            return true;
        }
        return false;
    }

}
