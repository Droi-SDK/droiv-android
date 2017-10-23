package com.xiudian.weex.extend.module;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.droi.sdk.socialize.DroiShareTask;
import com.droi.sdk.socialize.data.MediaImage;
import com.droi.sdk.socialize.data.MediaWebPage;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.xiudian.share.ShareAction;
import com.xiudian.weex.R;

public class XDShareModule extends WXModule {
    @JSMethod(uiThread = true)
    public void share(JSONObject options) {
        String url = "http://www.baidu.com";
        MediaWebPage webPage = new MediaWebPage(url);
        DroiShareTask droiShareTask = new DroiShareTask((Activity) mWXSDKInstance.getContext())
                .addContent(webPage)
                .addTitle("网页分享标题")
                .addSummary("网页分享描述信息")
                .addThumb(new MediaImage(mWXSDKInstance.getContext(), R.drawable.socialize_qq));
        ShareAction shareAction = new ShareAction((Activity) mWXSDKInstance.getContext(), droiShareTask);
        shareAction.open();
    }
}
