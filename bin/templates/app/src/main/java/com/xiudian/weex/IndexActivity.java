package com.xiudian.weex;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.weex.commons.AbstractWeexActivity;
import com.alibaba.weex.commons.util.AppConfig;
import com.alibaba.weex.constants.Constants;
import com.droi.sdk.socialize.DroiShareTask;
import com.droi.sdk.socialize.data.MediaImage;
import com.droi.sdk.socialize.data.MediaWebPage;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXRenderErrorCode;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXSoInstallMgrSdk;
import com.xiudian.share.ShareAction;
import com.xiudian.weex.extend.XDNavBarSetter;

import cn.jzvd.JZVideoPlayer;

public class IndexActivity extends AbstractWeexActivity {

    private static final String TAG = "IndexActivity";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0x1;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0x2;
    private static final String DEFAULT_IP = "your_current_IP";
    private static String sCurrentIp = DEFAULT_IP; // your_current_IP

    private ProgressBar mProgressBar;
    private TextView mTipView;
    private BroadcastReceiver mReloadReceiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        mProgressBar = findViewById(R.id.index_progressBar);
        mTipView = findViewById(R.id.index_tip);
        mProgressBar.setVisibility(View.VISIBLE);
        mTipView.setVisibility(View.VISIBLE);

        if (!WXSoInstallMgrSdk.isCPUSupport()) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mTipView.setText(R.string.cpu_not_support_tip);
            return;
        }
        if (TextUtils.equals(sCurrentIp, DEFAULT_IP)) {
            renderPage(WXFileUtils.loadAsset("dist/index.js", this), getIndexUrl());
        } else {
            renderPageByURL(getIndexUrl());
        }
        //WXSDKEngine.setActivityNavBarSetter(new XDNavBarSetter(mInstance));
        mReloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                createWeexInstance();
                if (TextUtils.equals(sCurrentIp, DEFAULT_IP)) {
                    renderPage(WXFileUtils.loadAsset("dist/index.js", getApplicationContext()), getIndexUrl());
                } else {
                    renderPageByURL(getIndexUrl());
                }
                mProgressBar.setVisibility(View.VISIBLE);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mReloadReceiver, new IntentFilter(WXSDKEngine.JS_FRAMEWORK_RELOAD));
        //requestWeexPermission();
    }

//    private void requestWeexPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (TextUtils.equals(sCurrentIp, DEFAULT_IP)) {
            getMenuInflater().inflate(R.menu.main_scan, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
//                if (!TextUtils.equals(sCurrentIp, DEFAULT_IP)) {
//                    createWeexInstance();
//                    renderPageByURL(getIndexUrl());
//                    mProgressBar.setVisibility(View.VISIBLE);
//                }
                //分享示例
                shareWebPage();
                break;
            case R.id.action_scan:
                startScan();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareWebPage() {
        String url = "http://www.baidu.com";
        MediaWebPage webPage = new MediaWebPage(url);
        DroiShareTask droiShareTask = new DroiShareTask(this)
                .addContent(webPage)
                .addTitle("网页分享标题")
                .addSummary("网页分享描述信息")
                .addThumb(new MediaImage(getApplicationContext(), R.drawable.socialize_qq));

        ShareAction shareAction = new ShareAction(this, droiShareTask);
        //shareAction.setDisplayList(PLATFORMS.QQ, PLATFORMS.WEIXIN);
        shareAction.open();
    }

    void startScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setPrompt(getString(R.string.capture_qrcode_prompt));
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                handleDecodeInternally(result.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleDecodeInternally(String code) {
        if (!TextUtils.isEmpty(code)) {
            Uri uri;
            try {
                uri = Uri.parse(code);
            } catch (RuntimeException e) {
                return;
            }
            if (uri.getQueryParameterNames().contains("bundle")) {
                WXEnvironment.sDynamicMode = uri.getBooleanQueryParameter("debug", false);
                WXEnvironment.sDynamicUrl = uri.getQueryParameter("bundle");
                String tip = WXEnvironment.sDynamicMode ? "Has switched to Dynamic Mode" : "Has switched to Normal Mode";
                Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
                finish();
            } else if (uri.getQueryParameterNames().contains("_wx_devtool")) {
                WXEnvironment.sRemoteDebugProxyUrl = uri.getQueryParameter("_wx_devtool");
                WXEnvironment.sDebugServerConnectable = true;
                WXSDKEngine.reload();
                Toast.makeText(this, "devtool", Toast.LENGTH_SHORT).show();
            } else if (code.contains("_wx_debug")) {
                uri = Uri.parse(code);
                String debug_url = uri.getQueryParameter("_wx_debug");
                WXSDKEngine.switchDebugModel(true, debug_url);
                finish();
            } else {
                Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Constants.ACTION_OPEN_URL + AppConfig.getXiudianId());
                intent.setPackage(getPackageName());
                intent.setData(Uri.parse(code));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScan();
        } else if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {
            Toast.makeText(this, "request camara permission fail!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {
        super.onRenderSuccess(wxsdkInstance, i, i1);
        mProgressBar.setVisibility(View.GONE);
        mTipView.setVisibility(View.GONE);
    }

    @Override
    public void onException(WXSDKInstance wxsdkInstance, String s, String s1) {
        super.onException(wxsdkInstance, s, s1);
        mProgressBar.setVisibility(View.GONE);
        mTipView.setVisibility(View.VISIBLE);
        if (TextUtils.equals(s, WXRenderErrorCode.WX_NETWORK_ERROR)) {
            mTipView.setText(R.string.index_tip);
        } else {
            mTipView.setText("render error:" + s1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReloadReceiver);
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    private static String getIndexUrl() {
        return "http://" + sCurrentIp + ":12580/examples/build/index.js";
    }
}
