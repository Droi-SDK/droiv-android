package com.xiudian.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.xiudian.weex.extend.view.TabbarControllerView;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by chenpei on 2017/10/11.
 */

public class XDTabbar extends WXComponent {
    private String instanceId;
    private String tag;

    public XDTabbar(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
        this.instanceId = instance.getInstanceId();
    }

    @Override
    protected View initComponentHostView(@NonNull Context context) {
        TabbarControllerView view = new TabbarControllerView(context);
        view.setHostId(instanceId);
        return view;
    }

    @WXComponentProp(name = "info")
    public void setInfo(List<Object> info) {
        JSONArray jsonArray = new JSONArray(info);
        TabbarControllerView cv = (TabbarControllerView) getRealView();
        cv.setTag(TabbarControllerView.TAG);
        cv.setInfo(jsonArray);
    }
}
