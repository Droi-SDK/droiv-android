package com.xiudian.weex.extend.module;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

public class XDNavigationBarModule extends WXModule {

    @JSMethod(uiThread = true)
    public void setRightItem(JSONObject options, JSCallback success, JSCallback failure) {

    }
}
