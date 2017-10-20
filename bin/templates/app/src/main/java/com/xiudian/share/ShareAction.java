package com.xiudian.share;

/**
 * Created by chenpei on 2017/10/17.
 */


import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

import com.droi.sdk.socialize.DroiShareTask;
import com.droi.sdk.socialize.platform.PLATFORMS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShareAction {

    private ShareBoardListener boardListener = null;
    private Activity activity;
    private List<PLATFORMS> displayList = null;
    private List<PlatformBean> platformList = new ArrayList();
    private int gravity = 80;
    private View showAtView = null;
    private ShareBoard mShareBoard;
    private DroiShareTask mDroiShareTask;

    public ShareAction(Activity activity, DroiShareTask droiShareTask) {
        if (activity != null) {
            this.activity = activity;
        }
        mDroiShareTask = droiShareTask;
    }

    public ShareAction setShareboardclickCallback(ShareBoardListener listener) {
        this.boardListener = listener;
        return this;
    }

    public ShareAction setDisplayList(PLATFORMS... list) {
        this.displayList = Arrays.asList(list);
        this.platformList.clear();
        for (PLATFORMS temp : this.displayList) {
            this.platformList.add(PlatformBean.toSnsPlatform(temp));
        }
        return this;
    }

    public PlatformBean createSnsPlatform(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt) {
        PlatformBean localSnsPlatform = new PlatformBean();
        localSnsPlatform.mShowWord = paramString1;
        localSnsPlatform.mIcon = paramString3;
        localSnsPlatform.mGrayIcon = paramString4;
        localSnsPlatform.mIndex = paramInt;
        localSnsPlatform.mKeyword = paramString2;
        return localSnsPlatform;
    }

    public ShareAction addButton(String showword, String Keyword, String icon, String Grayicon) {
        this.platformList.add(createSnsPlatform(showword, Keyword, icon, Grayicon, 0));
        return this;
    }

    public ShareAction withShareBoardDirection(View view, int gravity) {
        this.gravity = gravity;
        this.showAtView = view;
        return this;
    }

    public void open(ShareBoardConfig config) {
        if (this.platformList.size() != 0) {
            try {
                this.mShareBoard = new ShareBoard(this.activity, this.platformList, config);
                if (this.boardListener == null) {
                    this.mShareBoard.setShareBoardlistener(this.defaultShareBoardListener);
                } else {
                    this.mShareBoard.setShareBoardlistener(this.boardListener);
                }
                this.mShareBoard.setFocusable(true);
                this.mShareBoard.setBackgroundDrawable(new BitmapDrawable());
                if (this.showAtView == null) {
                    this.showAtView = this.activity.getWindow().getDecorView();
                }
                this.mShareBoard.showAtLocation(this.showAtView, this.gravity, 0, 0);
            } catch (Exception e) {
                Log.e("ShareAction", e.toString());
            }
        } else {
            this.platformList.add(PlatformBean.toSnsPlatform(PLATFORMS.WEIXIN));
            this.platformList.add(PlatformBean.toSnsPlatform(PLATFORMS.WEIXIN_CIRCLE));
            this.platformList.add(PlatformBean.toSnsPlatform(PLATFORMS.QQ));
            this.platformList.add(PlatformBean.toSnsPlatform(PLATFORMS.QZONE));
            this.platformList.add(PlatformBean.toSnsPlatform(PLATFORMS.SINA));
            this.mShareBoard = new ShareBoard(this.activity, this.platformList, config);
            if (this.boardListener == null) {
                this.mShareBoard.setShareBoardlistener(this.defaultShareBoardListener);
            } else {
                this.mShareBoard.setShareBoardlistener(this.boardListener);
            }
            this.mShareBoard.setFocusable(true);
            this.mShareBoard.setBackgroundDrawable(new BitmapDrawable());
            if (this.showAtView == null) {
                this.showAtView = this.activity.getWindow().getDecorView();
            }
            this.mShareBoard.showAtLocation(this.showAtView, 80, 0, 0);
        }
    }

    public void open() {
        open(null);
    }

    public void close() {
        if (this.mShareBoard != null) {
            this.mShareBoard.dismiss();
            this.mShareBoard = null;
        }
    }

    private ShareBoardListener defaultShareBoardListener = new ShareBoardListener() {
        public void onclick(PlatformBean snsPlatform, PLATFORMS share_media) {
            mDroiShareTask.setPlatform(share_media);
            mDroiShareTask.start();
        }
    };

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) {
            return null;
        }
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = (location.left + v.getWidth());
        location.bottom = (location.top + v.getHeight());
        return location;
    }
}

