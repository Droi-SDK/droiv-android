package com.xiudian.share;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.droi.sdk.socialize.platform.PLATFORMS;

import java.util.ArrayList;
import java.util.List;

class ShareBoardMenuHelper {

    private static String TAG = ShareBoardMenuHelper.class.getSimpleName();
    private ShareBoardConfig mShareBoardConfig;

    public ShareBoardMenuHelper(ShareBoardConfig shareBoardConfig) {
        this.mShareBoardConfig = shareBoardConfig;
    }

    public List<PlatformBean[][]> formatPageData(List<PlatformBean> menuData) {
        int pageSize = this.mShareBoardConfig.mMenuColumnNum * 2;

        int menuSize = menuData.size();
        List<PlatformBean[][]> result = new ArrayList();
        if (menuSize < this.mShareBoardConfig.mMenuColumnNum) {
            PlatformBean[][] page = new PlatformBean[1][menuSize];
            for (int i = 0; i < menuData.size(); i++) {
                page[0][i] = ((PlatformBean) menuData.get(i));
            }
            result.add(page);
            return result;
        }
        int pageCount = menuSize / pageSize;

        int lastPageRowNum = -1;

        int lastPageMenuSize = menuSize % pageSize;
        if (lastPageMenuSize != 0) {
            lastPageRowNum = lastPageMenuSize / this.mShareBoardConfig.mMenuColumnNum + (lastPageMenuSize % this.mShareBoardConfig.mMenuColumnNum != 0 ? 1 : 0);

            pageCount++;
        }
        for (int i = 0; i < pageCount; i++) {
            int rowNum;
            if ((i == pageCount - 1) && (lastPageRowNum != -1)) {
                rowNum = lastPageRowNum;
            } else {
                rowNum = 2;
            }
            PlatformBean[][] page = new PlatformBean[rowNum][this.mShareBoardConfig.mMenuColumnNum];
            result.add(page);
        }
        int menuCount = 0;
        for (int i = 0; i < result.size(); i++) {
            PlatformBean[][] page = (PlatformBean[][]) result.get(i);
            int rowNum = page.length;
            for (int row = 0; row < rowNum; row++) {
                PlatformBean[] column = page[row];
                for (int col = 0; col < column.length; col++) {
                    if (menuCount < menuSize) {
                        column[col] = ((PlatformBean) menuData.get(menuCount));
                    }
                    menuCount++;
                }
            }
        }
        return result;
    }

    public View createPageLayout(Context context, PlatformBean[][] pageData) {
        LinearLayout pageLayout = new LinearLayout(context);
        pageLayout.setOrientation(LinearLayout.VERTICAL);
        pageLayout.setGravity(48);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);

        pageLayout.setLayoutParams(layoutParams);
        for (int i = 0; i < pageData.length; i++) {
            PlatformBean[] rowData = pageData[i];
            View rowView = createRowLayout(context, rowData, i != 0);
            pageLayout.addView(rowView);
        }
        return pageLayout;
    }

    private View createRowLayout(Context context, PlatformBean[] rowData, boolean isHasTopMargin) {
        LinearLayout rowLayout = new LinearLayout(context);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setGravity(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        if (isHasTopMargin) {
            layoutParams.topMargin = dip2px(context, 20.0F);
        }
        rowLayout.setLayoutParams(layoutParams);
        for (int i = 0; i < rowData.length; i++) {
            View btn = createBtnView(context, rowData[i]);
            rowLayout.addView(btn);
        }
        return rowLayout;
    }

    private View createBtnView(Context context, final PlatformBean snsPlatform) {
        LinearLayout container = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0F;
        container.setLayoutParams(layoutParams);
        container.setGravity(17);
        if (snsPlatform != null) {
            ResContainer resContainer = ResContainer.get(context);
            View btn = LayoutInflater.from(context).inflate(resContainer.layout("socialize_share_menu_item"), null);
            SocializeImageView imageView = (SocializeImageView) btn.findViewById(resContainer.id("socialize_image_view"));
            TextView btnText = (TextView) btn.findViewById(resContainer.id("socialize_text_view"));
            if ((this.mShareBoardConfig.mMenuBgColor != 0) && (this.mShareBoardConfig.mMenuBgShape != ShareBoardConfig.BG_SHAPE_NONE)) {
                imageView.setBackgroundColor(this.mShareBoardConfig.mMenuBgColor, this.mShareBoardConfig.mMenuBgPressedColor);

                imageView.setBackgroundShape(this.mShareBoardConfig.mMenuBgShape, this.mShareBoardConfig.mMenuBgShapeAngle);
            } else {
                imageView.setPadding(0, 0, 0, 0);
            }
            if (this.mShareBoardConfig.mMenuIconPressedColor != 0) {
                imageView.setPressedColor(this.mShareBoardConfig.mMenuIconPressedColor);
            }
            String text = "";
            try {
                text = ResContainer.getString(context, snsPlatform.mShowWord);
            } catch (Exception e) {
                PLATFORMS platform = snsPlatform.mPlatform;
                String targetPlat = platform == null ? "" : platform.toString();
                Log.d(TAG, "fetch btn str failed,platform is:" + targetPlat);
            }
            if (!TextUtils.isEmpty(text)) {
                btnText.setText(ResContainer.getString(context, snsPlatform.mShowWord));
            }
            btnText.setGravity(17);

            int rId = 0;
            try {
                rId = ResContainer.getResourceId(context, "drawable", snsPlatform.mIcon);
            } catch (Exception e) {
                PLATFORMS platform = snsPlatform.mPlatform;
                String targetPlat = platform == null ? "" : platform.toString();
                Log.d(TAG, "fetch icon id failed,platform is:" + targetPlat);
            }
            if (rId != 0) {
                imageView.setImageResource(rId);
            }
            if (this.mShareBoardConfig.mMenuTextColor != 0) {
                btnText.setTextColor(this.mShareBoardConfig.mMenuTextColor);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    PLATFORMS platform = snsPlatform.mPlatform;
                    if ((ShareBoardMenuHelper.this.mShareBoardConfig != null) && (ShareBoardMenuHelper.this.mShareBoardConfig.getShareBoardlistener() != null)) {
                        ShareBoardMenuHelper.this.mShareBoardConfig.getShareBoardlistener().onclick(snsPlatform, platform);
                    }
                }
            });
            container.addView(btn);
        }
        return container;
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}

