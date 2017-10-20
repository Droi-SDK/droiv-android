package com.xiudian.weex.extend.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alibaba.weex.commons.WXAnalyzerDelegate;
import com.alibaba.weex.commons.util.AssertUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.xiudian.weex.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenpei on 2017/10/10.
 */

public class TabbarFragment extends Fragment implements IWXRenderListener {

    private static final String TAG = "ControllerViewFragment";

    FrameLayout fragmentFl;

    protected WXAnalyzerDelegate mWxAnalyzerDelegate;
    private ViewGroup mContainer;
    protected WXSDKInstance mInstance;
    private Context _mActivity;

    private String sUri;
    int index;

    public static TabbarFragment newInstance(int index, String uri) {
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putString("uri", uri);
        TabbarFragment fragment = new TabbarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TabbarFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _mActivity = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        index = args.getInt("index");
        sUri = args.getString("uri");
        Log.i("chenpei", index + "uri:" + sUri);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createWeexInstance();
        mInstance.onActivityCreate();
        mWxAnalyzerDelegate = new WXAnalyzerDelegate(_mActivity);
        mWxAnalyzerDelegate.onCreate();
        View layout = inflater.inflate(R.layout.fragment_tabbar, container, false);
        fragmentFl = layout.findViewById(R.id.fragment_fl);
        setContainer(fragmentFl);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (sUri != null) {
            renderPageByURL(sUri);
        }
    }

    public void createWeexInstance() {
        destroyWeexInstance();
        mInstance = new WXSDKInstance(_mActivity);
        mInstance.registerRenderListener(this);
    }

    public void destroyWeexInstance() {
        if (mInstance != null) {
            mInstance.registerRenderListener(null);
            mInstance.destroy();
            mInstance = null;
        }
    }

    public String getPageName() {
        return TAG;
    }

    public void renderPage(String template, String source) {
        renderPage(template, source, null);
    }

    public void renderPage(String template, String source, String jsonInitData) {
        AssertUtil.throwIfNull(mContainer, new RuntimeException("Can't render page, container is null"));
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, source);
        mInstance.setTrackComponent(true);
        mInstance.render(
                getPageName(),
                template,
                options,
                jsonInitData,
                WXRenderStrategy.APPEND_ASYNC);
    }

    public void renderPageByURL(String url) {
        renderPageByURL(url, null);
    }

    public void renderPageByURL(String url, String jsonInitData) {
        AssertUtil.throwIfNull(mContainer, new RuntimeException("Can't render page, container is null"));
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, url);
        mInstance.setTrackComponent(true);
        mInstance.renderByUrl(
                url.substring(url.length() - 16, url.length() - 11),
                url,
                options,
                jsonInitData,
                WXRenderStrategy.APPEND_ASYNC);
    }


    public final void setContainer(ViewGroup container) {
        mContainer = container;
    }

    public final ViewGroup getContainer() {
        return mContainer;
    }

    public String getsUri() {
        return sUri == null ? "" : sUri;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mInstance != null) {
            mInstance.onActivityStart();
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mInstance != null) {
            mInstance.onActivityResume();
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mInstance != null) {
            mInstance.onActivityPause();
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mInstance != null) {
            mInstance.onActivityStop();
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mInstance != null) {
            mInstance.onActivityDestroy();
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        View wrappedView = null;
        if (mWxAnalyzerDelegate != null) {
            wrappedView = mWxAnalyzerDelegate.onWeexViewCreated(instance, view);
        }
        if (wrappedView != null) {
            view = wrappedView;
        }
        if (mContainer != null) {
            mContainer.removeAllViews();
            mContainer.addView(view);
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onWeexRenderSuccess(instance);
        }
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onException(instance, errCode, msg);
        }
    }

}
