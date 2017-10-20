package com.xiudian.weex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xiudian.weex.im.ImFragment;
import com.xiudian.weex.extend.TabbarFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenpei on 2017/10/10.
 */

public class TabbarActivity extends AppCompatActivity {

    private static final String TAG = "TabbarActivity";

    List<Fragment> mFragmentList = new ArrayList<>();
    BottomNavigationBar bottomNavigationBar;
    ViewPager vp_home;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);
        Intent intent = getIntent();
        String tabbarString = intent.getStringExtra("tabbar");
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        vp_home = findViewById(R.id.vp_home);
        initView(tabbarString);
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

    void initView(String tabbarString) {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(tabbarString);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        final List<TabbarItem> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            TabbarItem item = new TabbarItem(jsonObject);
            item.drawableDefault = stringToDrawable(item.urlDefault);
            item.drawableChecked = stringToDrawable(item.urlChecked);
            if (item.isOk) {
                list.add(item);
            }
        }

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
    }

    public Drawable stringToDrawable(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BitmapDrawable(bitmap);
    }

}
