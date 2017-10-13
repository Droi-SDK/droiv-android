package com.xiudian.weex.extend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by chenpei on 2017/10/11.
 */

public class WXTabbar extends WXComponent {
    private String instanceId;
    private String tag;

    public WXTabbar(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
        this.instanceId = instance.getInstanceId();
    }

    @Override
    protected View initComponentHostView(@NonNull Context context) {
        ControllerView view = new ControllerView(context);
        view.setHostId(instanceId);
        return view;
    }

    @WXComponentProp(name = "info")
    public void setInfo(List<Object> info) {
        JSONArray jsonArray = new JSONArray(info);
        ControllerView cv = (ControllerView) getRealView();
        cv.setTag(ControllerView.TAG);
        cv.setInfo(jsonArray);
    }
}
