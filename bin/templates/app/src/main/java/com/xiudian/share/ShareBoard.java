package com.xiudian.share;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.droi.sdk.socialize.platform.PLATFORMS;

import java.util.List;

public class ShareBoard
        extends PopupWindow {
    private ShareBoardConfig mShareBoardConfig;

    public ShareBoard(Context context, List<PlatformBean> platforms) {
        this(context, platforms, null);
    }

    public ShareBoard(Context context, List<PlatformBean> platforms, ShareBoardConfig config) {
        super(context);
        setWindowLayoutMode(-1, -1);

        boolean isHorizontal = false;
        if (context.getResources().getConfiguration().orientation == 2) {
            isHorizontal = true;
        }
        if (config == null) {
            config = new ShareBoardConfig();
        }
        this.mShareBoardConfig = config;
        config.setOrientation(isHorizontal);

        DroiActionFrame umActionBoard = new DroiActionFrame(context);
        umActionBoard.setSnsPlatformData(platforms, config);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        umActionBoard.setLayoutParams(params);
        umActionBoard.setDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                ShareBoard.this.dismiss();
            }
        });
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                PopupWindow.OnDismissListener onDismissListener = ShareBoard.this.mShareBoardConfig != null ? ShareBoard.this.mShareBoardConfig.getOnDismissListener() : null;
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
            }
        });
        setContentView(umActionBoard);
        setFocusable(true);

        saveShareboardConfig(context, config);
    }

    private void saveShareboardConfig(Context context, ShareBoardConfig config) {
        if ((context == null) || (config == null)) {
            return;
        }
        String position = config.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM ? "0" : "1";
        String shape = null;
        if (config.mMenuBgShape == ShareBoardConfig.BG_SHAPE_NONE) {
            shape = "0";
        } else if (config.mMenuBgShape == ShareBoardConfig.BG_SHAPE_CIRCULAR) {
            shape = "1";
        } else if (config.mMenuBgShape == ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE) {
            if (config.mMenuBgShapeAngle != 0) {
                shape = "2";
            } else {
                shape = "3";
            }
        }
        if ((!TextUtils.isEmpty(position)) || (!TextUtils.isEmpty(shape))) {
            //TODO
            //SocializeSpUtils.putShareBoardConfig(context, shape + ";" + position);
        }
    }

    public void setShareBoardlistener(final ShareBoardListener shareBoardListener) {
        if (this.mShareBoardConfig == null) {
            return;
        }
        ShareBoardListener wrapListener = new ShareBoardListener() {
            public void onclick(PlatformBean snsPlatform, PLATFORMS share_media) {
                ShareBoard.this.setOnDismissListener(null);
                ShareBoard.this.dismiss();
                if (shareBoardListener != null) {
                    shareBoardListener.onclick(snsPlatform, share_media);
                }
            }
        };
        this.mShareBoardConfig.setShareBoardlistener(wrapListener);
    }
}

