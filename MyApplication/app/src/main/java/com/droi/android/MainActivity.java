package com.droi.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

public class MainActivity extends AppCompatActivity implements IWXRenderListener {

    String TAG="demo";
    WXSDKInstance mWXSDKInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        /**
         * WXSample 可以替换成自定义的字符串，针对埋点有效。
         * template 是.we transform 后的 js文件。
         * option 可以为空，或者通过option传入 js需要的参数。例如bundle js的地址等。
         * jsonInitData 可以为空。
         * width 为-1 默认全屏，可以自己定制。
         * height =-1 默认全屏，可以自己定制。
         */
//        mWXSDKInstance.render("WXSample", WXFileUtils.loadFileContent("hello.js", this), null, null, -1, -1, WXRenderStrategy.APPEND_ASYNC);
        mWXSDKInstance.render(WXFileUtils.loadAsset("index.weex.js",this));

//        this is for url from remote server
//        String url = ""
//        mWXSDKInstance.renderByUrl("Sample",url,null,null,WXRenderStrategy.APPEND_ASYNC);
    }


    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        Log.d(TAG,"onViewCreated");
        setContentView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.d(TAG,"onRenderSuccess：width="+width+";height="+height);

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Log.d(TAG,"onRefreshSuccess: width="+width+";height="+height);

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.d(TAG,"onException errcode="+errCode+";errmsg="+msg);

    }
}
