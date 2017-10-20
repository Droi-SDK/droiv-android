package com.xiudian.share;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.PopupWindow;

public class ShareBoardConfig {

    static int SHAREBOARD_POSITION_TOP = 1;
    public static int SHAREBOARD_POSITION_CENTER = 2;
    public static int SHAREBOARD_POSITION_BOTTOM = 3;
    public static int BG_SHAPE_NONE = 0;
    public static int BG_SHAPE_CIRCULAR = 1;
    public static int BG_SHAPE_ROUNDED_SQUARE = 2;
    boolean mTitleVisibility;
    String mTitleText;
    int mTitleTextColor;
    boolean mCancelBtnVisibility;
    String mCancelBtnText;
    int mCancelBtnColor;
    int mCancelBtnBgColor;
    int mCancelBtnBgPressedColor;
    int mShareboardPosition;
    int mShareboardBgColor;
    int mMenuBgShape;
    int mMenuBgShapeAngle;
    int mMenuBgColor;
    int mMenuBgPressedColor;
    int mMenuTextColor;
    int mMenuIconPressedColor;
    int mTopMargin;
    static final int CENTER_MENU_LEFT_PADDING = 36;
    static final int TITLE_TEXT_SIZE_IN_SP = 16;
    static final int TITLE_TOP_MARGIN = 20;
    static final int MENU_TOP_MARGIN = 20;
    static final int VIEW_PAGER_LEFT_MARGIN = 10;
    static final int MENU_ROW_NUM = 2;
    int mMenuColumnNum;
    private static final int MENU_COLUMN_NUM = 4;
    private static final int MENU_COLUMN_NUM_CENTER = 3;
    private static final int MENU_COLUMN_NUM_HORIZONTAL = 6;
    private static final int MENU_COLUMN_NUM_HORIZONTAL_CENTER = 5;
    static final int MENU_ROW_MARGIN = 20;
    static final int INDICATOR_BOTTOM_MARGIN = 20;
    static final int INDICATOR_SIZE = 3;
    static final int INDICATOR_SPACE = 5;
    boolean mIndicatorVisibility;
    int mIndicatorNormalColor;
    int mIndicatorSelectedColor;
    static final int CANCEL_BTN_HEIGHT = 50;
    static final int CANCEL_BTN_TEXT_SIZE_IN_SP = 15;
    private ShareBoardListener mShareBoardListener;
    private PopupWindow.OnDismissListener mOnDismissListener;

    public ShareBoardConfig() {
        setDefaultValue();
    }

    private void setDefaultValue() {
        int defaultTextColor = Color.parseColor("#575A5C");

        String white = "#ffffff";
        String press = "#22000000";

        String shareboardColor = "#E9EFF2";

        String title = "选择要分享到的平台";
        String btn = "取消分享";

        setShareboardBackgroundColor(Color.parseColor(shareboardColor));
        setShareboardPostion(SHAREBOARD_POSITION_BOTTOM);

        setTitleText(title);
        setTitleTextColor(defaultTextColor);

        int angle = 5;
        setMenuItemBackgroundShape(BG_SHAPE_ROUNDED_SQUARE, angle);
        setMenuItemBackgroundColor(Color.parseColor(white), Color.parseColor(press));
        setMenuItemIconPressedColor(Color.parseColor(press));
        setMenuItemTextColor(defaultTextColor);

        setCancelButtonText(btn);
        setCancelButtonTextColor(defaultTextColor);
        setCancelButtonBackground(Color.parseColor(white), Color.parseColor(press));

        setIndicatorColor(Color.parseColor("#C2C9CC"), Color.parseColor("#0086DC"));
    }

    void setShareBoardlistener(ShareBoardListener mShareBoardListener) {
        this.mShareBoardListener = mShareBoardListener;
    }

    ShareBoardListener getShareBoardlistener() {
        return this.mShareBoardListener;
    }

    void setOrientation(boolean isHorizontal) {
        if (isHorizontal) {
            if (this.mShareboardPosition == SHAREBOARD_POSITION_BOTTOM) {
                this.mMenuColumnNum = 6;
            } else if (this.mShareboardPosition == SHAREBOARD_POSITION_CENTER) {
                this.mMenuColumnNum = 5;
            }
        } else if (this.mShareboardPosition == SHAREBOARD_POSITION_BOTTOM) {
            this.mMenuColumnNum = 4;
        } else if (this.mShareboardPosition == SHAREBOARD_POSITION_CENTER) {
            this.mMenuColumnNum = 3;
        }
    }

    public ShareBoardConfig setTitleVisibility(boolean visibility) {
        this.mTitleVisibility = visibility;
        return this;
    }

    public ShareBoardConfig setTitleText(String title) {
        if (TextUtils.isEmpty(title)) {
            setTitleVisibility(false);
        } else {
            setTitleVisibility(true);
            this.mTitleText = title;
        }
        return this;
    }

    public ShareBoardConfig setTitleTextColor(int color) {
        this.mTitleTextColor = color;
        return this;
    }

    public ShareBoardConfig setCancelButtonVisibility(boolean visibility) {
        this.mCancelBtnVisibility = visibility;
        return this;
    }

    public ShareBoardConfig setCancelButtonText(String text) {
        if (TextUtils.isEmpty(text)) {
            setCancelButtonVisibility(false);
        } else {
            setCancelButtonVisibility(true);
            this.mCancelBtnText = text;
        }
        return this;
    }

    public ShareBoardConfig setCancelButtonTextColor(int color) {
        this.mCancelBtnColor = color;
        return this;
    }

    public ShareBoardConfig setCancelButtonBackground(int normalColor) {
        setCancelButtonBackground(normalColor, 0);
        return this;
    }

    public ShareBoardConfig setCancelButtonBackground(int normalColor, int pressedColor) {
        this.mCancelBtnBgColor = normalColor;
        this.mCancelBtnBgPressedColor = pressedColor;
        return this;
    }

    public ShareBoardConfig setShareboardBackgroundColor(int color) {
        this.mShareboardBgColor = color;
        return this;
    }

    public ShareBoardConfig setShareboardPostion(int position) {
        if ((position != SHAREBOARD_POSITION_BOTTOM) && (position != SHAREBOARD_POSITION_CENTER) && (position != SHAREBOARD_POSITION_TOP)) {
            position = SHAREBOARD_POSITION_BOTTOM;
        }
        this.mShareboardPosition = position;
        return this;
    }

    public ShareBoardConfig setMenuItemBackgroundShape(int shape) {
        setMenuItemBackgroundShape(shape, 0);
        return this;
    }

    public ShareBoardConfig setMenuItemBackgroundShape(int shape, int angle) {
        if ((shape != BG_SHAPE_CIRCULAR) && (shape != BG_SHAPE_ROUNDED_SQUARE)) {
            shape = BG_SHAPE_NONE;
        }
        this.mMenuBgShape = shape;
        this.mMenuBgShapeAngle = angle;
        return this;
    }

    public ShareBoardConfig setMenuItemBackgroundColor(int normalColor) {
        setMenuItemBackgroundColor(normalColor, 0);
        return this;
    }

    public ShareBoardConfig setMenuItemBackgroundColor(int normalColor, int pressedColor) {
        this.mMenuBgColor = normalColor;
        this.mMenuBgPressedColor = pressedColor;
        return this;
    }

    public ShareBoardConfig setMenuItemTextColor(int color) {
        this.mMenuTextColor = color;
        return this;
    }

    public ShareBoardConfig setMenuItemIconPressedColor(int color) {
        this.mMenuIconPressedColor = color;
        return this;
    }

    public ShareBoardConfig setIndicatorColor(int normalColor) {
        setIndicatorColor(normalColor, 0);
        return this;
    }

    public ShareBoardConfig setIndicatorColor(int normalColor, int selectedColor) {
        if (normalColor != 0) {
            this.mIndicatorNormalColor = normalColor;
        }
        if (selectedColor != 0) {
            this.mIndicatorSelectedColor = selectedColor;
        }
        setIndicatorVisibility(true);
        return this;
    }

    public ShareBoardConfig setIndicatorVisibility(boolean visibility) {
        this.mIndicatorVisibility = visibility;
        return this;
    }

    public ShareBoardConfig setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.mOnDismissListener = listener;
        return this;
    }

    PopupWindow.OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    public ShareBoardConfig setStatusBarHeight(int statsBarHeight) {
        this.mTopMargin = statsBarHeight;
        return this;
    }

    int calculateMenuHeightInDp(int menuSize) {
        int itemHeight = 75;
        int lineSpacing = 20;
        int bottomPadding = 20;
        int row;
        if (menuSize <= this.mMenuColumnNum) {
            row = 1;
        } else {
            if (menuSize <= this.mMenuColumnNum * 2) {
                row = 2;
            } else {
                row = 2;
            }
        }
        int sumHeight = itemHeight * row + lineSpacing * (row - 1) + bottomPadding;
        return sumHeight;
    }
}

