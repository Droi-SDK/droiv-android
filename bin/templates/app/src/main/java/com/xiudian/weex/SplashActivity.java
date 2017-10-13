package com.xiudian.weex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

import com.xiudian.weex.extend.TabbarActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by chenpei on 2017/10/12.
 */

public class SplashActivity extends Activity {

    private static final int sleepTime = 5000;
    TextView mCountDownTextView;
    private MyCountDownTimer mCountDownTimer;

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
        mSkipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                startIndexActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void startIndexActivity() {
        startActivity(new Intent(SplashActivity.this, IndexActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
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
