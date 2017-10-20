package com.xiudian.share;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

class DroiActionFrame extends LinearLayout {

    private ShareBoardConfig mConfig;
    private PopupWindow.OnDismissListener mDismissListener;

    public DroiActionFrame(Context context) {
        super(context);
    }

    public DroiActionFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(11)
    public DroiActionFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public DroiActionFrame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setSnsPlatformData(List<PlatformBean> platforms) {
        ShareBoardConfig defaultConfig = new ShareBoardConfig();
        setSnsPlatformData(platforms, defaultConfig);
    }

    public void setSnsPlatformData(List<PlatformBean> platforms, ShareBoardConfig config) {
        if (config == null) {
            this.mConfig = new ShareBoardConfig();
        } else {
            this.mConfig = config;
        }
        init(platforms);
    }

    private void init(List<PlatformBean> platforms) {
        setBackgroundColor(Color.argb(50, 0, 0, 0));

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        alphaAnimation.setDuration(100L);
        setAnimation(alphaAnimation);

        setOrientation(VERTICAL);
        if (this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM) {
            setGravity(80);
        } else if (this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_CENTER) {
            setGravity(17);
            int leftPadding = dip2px(36.0F);
            setPadding(leftPadding, 0, leftPadding, 0);
        }
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (DroiActionFrame.this.mDismissListener != null) {
                    DroiActionFrame.this.mDismissListener.onDismiss();
                }
            }
        });
        View shareMenuLayout = createShareboardLayout(platforms);
        if (shareMenuLayout == null) {
            return;
        }
        shareMenuLayout.setClickable(true);
        addView(shareMenuLayout);
    }

    private View createShareboardLayout(List<PlatformBean> platforms) {
        LinearLayout shareMenuLayout = new LinearLayout(getContext());
        shareMenuLayout.setBackgroundColor(this.mConfig.mShareboardBgColor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        if ((this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_CENTER) &&
                (this.mConfig.mTopMargin != 0)) {
            layoutParams.topMargin = this.mConfig.mTopMargin;
        }
        shareMenuLayout.setOrientation(VERTICAL);
        shareMenuLayout.setLayoutParams(layoutParams);
        if (this.mConfig.mTitleVisibility) {
            View titleView = createShareTitle();
            shareMenuLayout.addView(titleView);
        }
        int pageHeight = this.mConfig.calculateMenuHeightInDp(platforms.size());

        ViewPager viewPager = createViewPagerInstance();
        if (viewPager != null) {
            SocializeMenuPagerAdapter adapter = new SocializeMenuPagerAdapter(getContext(), this.mConfig);
            adapter.setData(platforms);

            settingMenuLayout(viewPager, pageHeight);
            shareMenuLayout.addView(viewPager);

            viewPager.setAdapter(adapter);

            final IndicatorView indicator = this.mConfig.mIndicatorVisibility ? createIndicatorView() : null;
            if (indicator != null) {
                indicator.setPageCount(adapter.getCount());
                shareMenuLayout.addView(indicator);
            }
            ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    if (indicator != null) {
                        indicator.setSelectedPosition(position);
                    }
                }

                public void onPageScrollStateChanged(int state) {
                }
            };
            if (verifyMethodExists()) {
                viewPager.addOnPageChangeListener(listener);
            } else {
                viewPager.setOnPageChangeListener(listener);
            }
        } else {
            SocializeViewPager socializeViewPager = createSocializeViewPagerInstance();
            if (socializeViewPager == null) {
                return null;
            }
            SocializeMenuAdapter adapter = new SocializeMenuAdapter(getContext(), this.mConfig);
            adapter.setData(platforms);

            settingMenuLayout(socializeViewPager, pageHeight);
            shareMenuLayout.addView(socializeViewPager);

            socializeViewPager.setAdapter(adapter);

            final IndicatorView indicator = this.mConfig.mIndicatorVisibility ? createIndicatorView() : null;
            if (indicator != null) {
                indicator.setPageCount(adapter.getCount());
                shareMenuLayout.addView(indicator);
            }
            socializeViewPager.addOnPageChangeListener(new SocializeViewPager.OnPageChangeListener() {
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    if (indicator != null) {
                        indicator.setSelectedPosition(position);
                    }
                }

                public void onPageScrollStateChanged(int state) {
                }
            });
        }
        if (this.mConfig.mCancelBtnVisibility) {
            View cancelBtn = createCancelBtn();
            shareMenuLayout.addView(cancelBtn);
        }
        return shareMenuLayout;
    }

    private View createShareTitle() {
        TextView title = new TextView(getContext());
        title.setText(this.mConfig.mTitleText);
        title.setTextColor(this.mConfig.mTitleTextColor);
        title.setTextSize(16.0F);

        title.setGravity(17);
        title.setMaxLines(1);
        title.setEllipsize(TextUtils.TruncateAt.END);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);

        layoutParams.topMargin = dip2px(20.0F);
        title.setLayoutParams(layoutParams);
        return title;
    }

    private void settingMenuLayout(View viewPager, int pageHeight) {
        int padding = dip2px(20.0F);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, dip2px(pageHeight));
        layoutParams.topMargin = padding;
        layoutParams.leftMargin = (layoutParams.rightMargin = dip2px(10.0F));
        viewPager.setLayoutParams(layoutParams);
        viewPager.setPadding(0, 0, 0, padding);
    }

    public IndicatorView createIndicatorView() {
        int padding = dip2px(20.0F);
        IndicatorView indicatorView = new IndicatorView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.bottomMargin = padding;
        indicatorView.setLayoutParams(layoutParams);
        indicatorView.setIndicatorColor(this.mConfig.mIndicatorNormalColor, this.mConfig.mIndicatorSelectedColor);
        indicatorView.setIndicator(3, 5);
        return indicatorView;
    }

    public View createCancelBtn() {
        TextView cancelBtn = new TextView(getContext());
        cancelBtn.setText(this.mConfig.mCancelBtnText);
        cancelBtn.setTextColor(this.mConfig.mCancelBtnColor);
        cancelBtn.setClickable(true);
        cancelBtn.setTextSize(15.0F);
        cancelBtn.setGravity(17);
        if (this.mConfig.mCancelBtnBgPressedColor != 0) {
            if (Build.VERSION.SDK_INT >= 16) {
                cancelBtn.setBackground(getBtnBg());
            } else {
                cancelBtn.setBackgroundDrawable(getBtnBg());
            }
        } else {
            cancelBtn.setBackgroundColor(this.mConfig.mCancelBtnBgColor);
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (DroiActionFrame.this.mDismissListener != null) {
                    DroiActionFrame.this.mDismissListener.onDismiss();
                }
            }
        });
        int height = dip2px(50.0F);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, height);
        cancelBtn.setLayoutParams(layoutParams);
        return cancelBtn;
    }

    private StateListDrawable getBtnBg() {
        ColorDrawable normalColor = new ColorDrawable(this.mConfig.mCancelBtnBgColor);
        ColorDrawable pressedColor = new ColorDrawable(this.mConfig.mCancelBtnBgPressedColor);
        StateListDrawable bg = new StateListDrawable();
        int pressed = 16842919;
        bg.addState(new int[]{pressed}, pressedColor);
        bg.addState(new int[0], normalColor);
        return bg;
    }

    private int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    void setDismissListener(PopupWindow.OnDismissListener dismissListener) {
        this.mDismissListener = dismissListener;
    }

    private ViewPager createViewPagerInstance() {
        ViewPager viewPager = null;
        try {
            Class<?> viewPagerClass = Class.forName("android.support.v4.view.ViewPager");
            Class<?>[] parTypes = new Class[1];
            parTypes[0] = Context.class;
            Constructor constructor = viewPagerClass.getConstructor(parTypes);

            Object[] pars = new Object[1];
            pars[0] = getContext();
            return (ViewPager) constructor.newInstance(pars);
        } catch (Exception e) {
            Log.e("DroiActionFrame", "DroiActionFrame create ViewPager Instance error:" + e);
        }
        return viewPager;
    }

    private boolean verifyMethodExists() {
        try {
            Class<?> viewPagerClass = Class.forName("android.support.v4.view.ViewPager");
            Method method = viewPagerClass.getMethod("addOnPageChangeListener", new Class[]{ViewPager.OnPageChangeListener.class});
            if (method != null) {
                return true;
            }
        } catch (Exception e) {
            Log.e("DroiActionFrame", "DroiActionFrame verifyMethodExists addOnPageChangeListener error:" + e);
        }
        return false;
    }

    private SocializeViewPager createSocializeViewPagerInstance() {
        SocializeViewPager viewPager = null;
        try {
            Class<?> viewPagerClass = Class.forName("com.umeng.socialize.shareboard.widgets.SocializeViewPager");
            Class<?>[] parTypes = new Class[1];
            parTypes[0] = Context.class;
            Constructor constructor = viewPagerClass.getConstructor(parTypes);

            Object[] pars = new Object[1];
            pars[0] = getContext();
            return (SocializeViewPager) constructor.newInstance(pars);
        } catch (Exception e) {
            Log.e("DroiActionFrame", "DroiActionFrame create SocializeViewPager Instance error:" + e);
            Log.e("DroiActionFrame", "need android support v4");
        }
        return viewPager;
    }
}

