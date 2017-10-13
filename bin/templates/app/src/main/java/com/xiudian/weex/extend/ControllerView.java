package com.xiudian.weex.extend;

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

public class ControllerView extends FrameLayout {
    private JSONArray info;
    private String hostId;
    public final static String TAG = "lychee";//前端页面需要定义的tag

    public ControllerView(@NonNull Context context) {
        super(context);
    }

    public ControllerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ControllerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ControllerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
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
