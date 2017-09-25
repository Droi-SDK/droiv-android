package com.alibaba.weex.commons.adapter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * Created by chenpei on 2017/9/25.
 */

public class GlideImageAdapter implements IWXImgLoaderAdapter {

    public GlideImageAdapter() {

    }

    @Override
    public void setImage(final String url, final ImageView view,
                         WXImageQuality quality, final WXImageStrategy strategy) {
        WXSDKManager.getInstance().postOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (view == null || view.getLayoutParams() == null) {
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }
                String temp = url;
                if (url.startsWith("//")) {
                    temp = "http:" + url;
                }
                if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
                    return;
                }

                if (!TextUtils.isEmpty(strategy.placeHolder)) {
                    RequestManager glide = Glide.with(WXEnvironment.getApplication());
                    glide.load(Uri.parse(strategy.placeHolder)).into(view);
                    view.setTag(strategy.placeHolder.hashCode(), glide);
                }

                Glide.with(WXEnvironment.getApplication())
                        .load(temp)
                        .thumbnail(0.1f)
                        .into(new DrawableImageViewTarget(view) {
                            @Override
                            public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                super.onResourceReady(resource, transition);
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, true, null);
                                }
                                if (!TextUtils.isEmpty(strategy.placeHolder)) {
                                    ((RequestManager) view.getTag(strategy.placeHolder.hashCode())).clear(view);
                                }
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, false, null);
                                }
                            }
                        });
            }
        }, 0);
    }
}
