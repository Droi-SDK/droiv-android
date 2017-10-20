package com.xiudian.weex.extend.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import org.json.JSONArray;

/**
 * Created by lychee on 17-4-6.
 */

public class TabbarControllerView extends FrameLayout {
    private JSONArray info;
    private String hostId;
    public final static String TAG = "lychee";//前端页面需要定义的tag

    public TabbarControllerView(@NonNull Context context) {
        super(context);
    }

    public TabbarControllerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabbarControllerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabbarControllerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public JSONArray getInfo() {
        return info;
    }

    public void setInfo(JSONArray info) {
        this.info = info;
    }
}
