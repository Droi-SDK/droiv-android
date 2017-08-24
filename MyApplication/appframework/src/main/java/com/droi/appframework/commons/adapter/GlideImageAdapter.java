package com.droi.appframework.commons.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * Created by zhouzhongbo on 2017/8/23.
 */

public class GlideImageAdapter implements IWXImgLoaderAdapter {

    public GlideImageAdapter() {
    }

    @Override
    public void setImage(final String url,final ImageView view, WXImageQuality quality,final WXImageStrategy strategy) {
        {

            WXSDKManager.getInstance().postOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(view==null||view.getLayoutParams()==null){
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


                    /*
                    * get placeholder resource ??
                    *  may be we need rewrite error holder&placeholder
                    */

//                    if(!TextUtils.isEmpty(strategy.placeHolder)){
//                        Glide.with(WXEnvironment.getApplication()).load(Uri.parse(strategy.placeHolder)).into(view);
//                    }
                    Glide.with(WXEnvironment.getApplication()).load(temp).into(view);
                }
            },0);
        }
    }
}
