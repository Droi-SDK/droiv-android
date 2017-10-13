package com.xiudian.weex.extend;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.alibaba.weex.commons.AbstractWeexActivity;
import com.alibaba.weex.commons.XiudianNavigator;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.utils.WXFileUtils;
import com.xiudian.weex.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenpei on 2017/10/10.
 */

public class TabbarActivity extends AbstractWeexActivity {

    private static final String TAG = "TabbarActivity";

    private static final String DEFAULT_IP = "your_current_IP";
    private static String sCurrentIp = DEFAULT_IP;
    private ProgressBar mProgressBar;
    private View mCoverView;
    private boolean isRenderSuccess = false;

    List<Fragment> mFragmentList = new ArrayList<>();
    BottomNavigationBar bottomNavigationBar;
    ViewPager vp_home;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        vp_home = findViewById(R.id.vp_home);
        mProgressBar = findViewById(R.id.progress);
        mCoverView = findViewById(R.id.cover_view);
        FrameLayout noShow = findViewById(R.id.noshow);
        setContainer(noShow);
//        mCoverView.setVisibility(View.VISIBLE);
//        mProgressBar.setVisibility(View.VISIBLE);
        WXSDKEngine.setActivityNavBarSetter(new XiudianNavigator(mInstance));
        renderPage(WXFileUtils.loadAsset("dist/tabbar.js", this), getIndexUrl());
    }

    private static String getIndexUrl() {
        return "http://" + sCurrentIp + ":12580/examples/build/index.js";
    }

    private class TabbarItem {
        Drawable drawableDefault;
        Drawable drawableChecked;
        String urlDefault;
        String urlChecked;
        String title;
        String uri;
        boolean isOk = true;
        String type;

        TabbarItem(JSONObject jsonObject) {
            try {
                urlDefault = jsonObject.getString("image");
                urlChecked = jsonObject.getString("selectedImage");
                title = jsonObject.getString("title");
                uri = jsonObject.getString("uri");
                type = jsonObject.optString("type", "generic");
            } catch (JSONException e) {
                isOk = false;
                e.printStackTrace();
            }
        }
    }

    void initView(final JSONArray jsonArray) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<TabbarItem> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        TabbarItem item = new TabbarItem(jsonObject);
                        FutureTarget<Drawable> futureDefault = Glide.with(TabbarActivity.this)
                                .load(item.urlDefault).submit();
                        FutureTarget<Drawable> futureChecked = Glide.with(TabbarActivity.this)
                                .load(item.urlChecked).submit();
                        item.drawableDefault = futureDefault.get();
                        item.drawableChecked = futureChecked.get();
                        if (item.isOk) {
                            list.add(item);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                if (list.size() != jsonArray.length()) {
                    Log.w("Xiudian", "Tabbar maybe something wrong!");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
                        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

                        for (int i = 0; i < list.size(); i++) {
                            TabbarItem item = list.get(i);
                            bottomNavigationBar.addItem(new BottomNavigationItem(item.drawableChecked,
                                    item.title).setInactiveIcon(item.drawableDefault));
                            if (item.type.equals("chat")) {
                                mFragmentList.add(new ImFragment());
                            } else {
                                mFragmentList.add(TabbarFragment.newInstance(i, item.uri));
                            }
                        }

                        bottomNavigationBar.initialise();
                        bottomNavigationBar.setTabSelectedListener(
                                new BottomNavigationBar.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(int position) {
                                        vp_home.setCurrentItem(position);
                                    }

                                    @Override
                                    public void onTabUnselected(int position) {

                                    }

                                    @Override
                                    public void onTabReselected(int position) {

                                    }
                                });
                        vp_home.setOffscreenPageLimit(4);
                        vp_home.setAdapter(
                                new FragmentPagerAdapter(getSupportFragmentManager()) {
                                    @Override
                                    public Fragment getItem(int position) {
                                        return mFragmentList.get(position);
                                    }

                                    @Override
                                    public int getCount() {
                                        return mFragmentList.size();
                                    }
                                });
                        //mCoverView.setVisibility(View.GONE);
                        //mProgressBar.setVisibility(View.GONE);
                    }

                });

            }
        }).start();
    }

    public String getPageName() {
        return TAG;
    }

    @Override
    @CallSuper
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        super.onRenderSuccess(instance, width, height);
        isRenderSuccess = true;
        ControllerView vg;
        ViewGroup container = getContainer();
        vg = container.findViewWithTag(ControllerView.TAG);
        initView(vg.getInfo());
    }

    @Override
    @CallSuper
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        super.onException(instance, errCode, msg);
        mCoverView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        isRenderSuccess = false;
    }
}
