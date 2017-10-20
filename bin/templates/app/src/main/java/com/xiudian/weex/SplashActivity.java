package com.xiudian.weex;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.weex.commons.AbstractWeexActivity;
import com.bumptech.glide.Glide;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.utils.WXFileUtils;
import com.xiudian.weex.extend.ControllerView;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by chenpei on 2017/10/12.
 */

public class SplashActivity extends AbstractWeexActivity {

    private static final int sleepTime = 5000;
    TextView mCountDownTextView;
    private MyCountDownTimer mCountDownTimer;

    private static final String DEFAULT_IP = "your_current_IP";
    private static String sCurrentIp = DEFAULT_IP;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView image = findViewById(R.id.splash_pic);
        mCountDownTextView = findViewById(R.id.start_skip_count_down);
        FrameLayout mSkipLayout = findViewById(R.id.start_skip);
        Glide.with(SplashActivity.this)
                .load(R.drawable.splash_pic)
                .apply(centerCropTransform())
                .into(image);
        mCountDownTimer = new MyCountDownTimer(sleepTime, 1000);
        mCountDownTimer.start();
//        mSkipLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCountDownTimer != null) {
//                    mCountDownTimer.cancel();
//                }
//                startIndexActivity();
//            }
//        });
        FrameLayout noShow = findViewById(R.id.noshow);
        setContainer(noShow);
        renderPage(WXFileUtils.loadAsset("dist/tabbar.js", this), getIndexUrl());
    }

    private static String getIndexUrl() {
        return "http://" + sCurrentIp + ":12580/examples/build/index.js";
    }

    @Override
    @CallSuper
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        super.onRenderSuccess(instance, width, height);
        //isRenderSuccess = true;
        ControllerView vg;
        ViewGroup container = getContainer();
        vg = container.findViewWithTag(ControllerView.TAG);
        //initView(vg.getInfo());
        jsonArray = vg.getInfo();
        startIndexActivity();
    }

    JSONArray jsonArray;

    @Override
    @CallSuper
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        super.onException(instance, errCode, msg);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    void startIndexActivity() {
        Intent intent = new Intent(SplashActivity.this, IndexActivity.class);
        intent.putExtra("tabbar",jsonArray.toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture + 200, countDownInterval);
        }

        public void onFinish() {
            mCountDownTextView.setText(secondFormat(0));
            startIndexActivity();
        }

        public void onTick(long millisUntilFinished) {
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            String secondStringUntilFinished = decimalFormat.format(((float) millisUntilFinished / 1000) - 1);
            BigDecimal secondUntilFinished = new BigDecimal(secondStringUntilFinished)
                    .setScale(0, BigDecimal.ROUND_HALF_UP);
            String secondUntilFinishedString = secondFormat(secondUntilFinished.toBigInteger().intValue());
            Log.i("chenpei", "secondUntilFinishedString:" + secondUntilFinishedString);
            mCountDownTextView.setText(secondUntilFinishedString);
        }
    }

    String secondFormat(int secondUntilFinished) {
        return String.format(getString(R.string.click_to_skip),
                secondUntilFinished);
    }
}
