package com.xiudian.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import cn.jzvd.JZVideoPlayerStandard;

public class XDVideo extends WXComponent<FrameLayout> {

    private JZVideoPlayerStandard mView;
    private int mType;
    private String mUrl = "";
    private String mThumbUrl = "";
    private String mTitle = "";

    public XDVideo(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected FrameLayout initComponentHostView(@NonNull Context context) {
        JZVideoPlayerStandard video = new JZVideoPlayerStandard(context);
        mView = video;
        return video;
    }

    @WXComponentProp(name = Constants.Name.SRC)
    public void setSrc(String src) {
        if (TextUtils.isEmpty(src) || getHostView() == null) {
            return;
        }
        if (!TextUtils.isEmpty(src)) {
            mUrl = src;
            mView.setUp(mUrl, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, mTitle);
        }
    }

    @WXComponentProp(name = "thumbUrl")
    public void setThumb(String thumbUrl) {
        if (TextUtils.isEmpty(thumbUrl) || getHostView() == null) {
            return;
        }
        if (!TextUtils.isEmpty(thumbUrl)) {
            mThumbUrl = thumbUrl;
            WXSDKInstance instance = getInstance();
            Glide.with(instance.getContext())
                    .load(mThumbUrl)
                    .into(mView.thumbImageView);
        }
    }

    @WXComponentProp(name = "title")
    public void setTitle(String title) {
        if (TextUtils.isEmpty(title) || getHostView() == null) {
            return;
        }
        if (!TextUtils.isEmpty(title)) {
            mTitle = title;
            mView.setUp(mUrl, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, mTitle);
        }
    }

    @WXComponentProp(name = "type")
    public void setType(int type) {
        mType = type;
        if (mType == 1) {
            mView.currentTimeTextView.setVisibility(View.GONE);
            mView.progressBar.setVisibility(View.GONE);
            mView.totalTimeTextView.setVisibility(View.GONE);
            mView.clarity.setVisibility(View.GONE);
            mView.fullscreenButton.setVisibility(View.GONE);
        }
    }
}
