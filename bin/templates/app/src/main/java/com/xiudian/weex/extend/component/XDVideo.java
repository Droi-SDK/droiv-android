package com.xiudian.weex.extend.component;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.adapter.URIAdapter;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by chenpei on 2017/10/19.
 */

public class XDVideo extends WXComponent<FrameLayout> {

    private JZVideoPlayerStandard mWrapper;
    private boolean mAutoPlay;

    public XDVideo(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected FrameLayout initComponentHostView(@NonNull Context context) {
        JZVideoPlayerStandard video = new JZVideoPlayerStandard(context);
        mWrapper =video;
        return video;
    }

    @WXComponentProp(name = Constants.Name.SRC)
    public void setSrc(String src) {
        if (TextUtils.isEmpty(src) || getHostView() == null) {
            return;
        }

        if (!TextUtils.isEmpty(src)) {
            WXSDKInstance instance = getInstance();
            //instance.rewriteUri(Uri.parse(src), URIAdapter.VIDEO)
            mWrapper.setUp(src, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
            //mVideo.getProgressBar().setVisibility(View.VISIBLE);
        }
    }

    @WXComponentProp(name = Constants.Name.AUTO_PLAY)
    public void setAutoPlay(boolean autoPlay) {
        mAutoPlay = autoPlay;
        if(autoPlay){
            //mWrapper.createIfNotExist();
            mWrapper.startVideo();
        }
    }
}
