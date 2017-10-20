package com.xiudian.share;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SocializeImageView extends ImageButton {

    private boolean mIsSelected;
    private int mNormalColor;
    private int mPressedColor;
    private int mIconPressedColor;
    private boolean mIsPressEffect;
    private int mBgShape;
    public static int BG_SHAPE_NONE = 0;
    public static int BG_SHAPE_CIRCULAR = 1;
    public static int BG_SHAPE_ROUNDED_SQUARE = 2;
    protected Paint mNormalPaint;
    protected Paint mPressedPaint;
    private RectF mSquareRect;
    private int mAngle;

    public SocializeImageView(Context context) {
        super(context);
        init();
    }

    public SocializeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SocializeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public SocializeImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT < 16) {
            setBackgroundDrawable(null);
        } else {
            setBackground(null);
        }
        setClickable(false);
        setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public void setBackgroundShape(int shapeType) {
        setBackgroundShape(shapeType, 0);
    }

    public void setBackgroundShape(int shapeType, int angle) {
        this.mBgShape = shapeType;
        if (shapeType != BG_SHAPE_ROUNDED_SQUARE) {
            this.mAngle = 0;
        } else {
            float density = getResources().getDisplayMetrics().density;
            this.mAngle = ((int) (angle * density + 0.5F));
        }
    }

    public void setBackgroundColor(int normalColor) {
        setBackgroundColor(normalColor, 0);
    }

    public void setBackgroundColor(int normalColor, int pressColor) {
        this.mNormalColor = normalColor;
        this.mPressedColor = pressColor;
        setPressEffectEnable(pressColor != 0);
        if (this.mNormalColor != 0) {
            this.mNormalPaint = new Paint();
            this.mNormalPaint.setStyle(Paint.Style.FILL);
            this.mNormalPaint.setAntiAlias(true);
            this.mNormalPaint.setColor(normalColor);
        }
        if (this.mPressedColor != 0) {
            this.mPressedPaint = new Paint();
            this.mPressedPaint.setStyle(Paint.Style.FILL);
            this.mPressedPaint.setAntiAlias(true);
            this.mPressedPaint.setColor(pressColor);
        }
    }

    public void setPressedColor(int pressColor) {
        setPressEffectEnable(pressColor != 0);
        this.mIconPressedColor = pressColor;
    }

    public void setPressEffectEnable(boolean isEnable) {
        this.mIsPressEffect = isEnable;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (!this.mIsPressEffect) {
            return;
        }
        if (isPressed()) {
            if (BG_SHAPE_NONE == this.mBgShape) {
                if (this.mIconPressedColor != 0) {
                    setColorFilter(this.mIconPressedColor, PorterDuff.Mode.SRC_ATOP);
                }
            } else {
                this.mIsSelected = true;
                invalidate();
            }
        } else if (BG_SHAPE_NONE == this.mBgShape) {
            clearColorFilter();
        } else {
            this.mIsSelected = false;
            invalidate();
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.mBgShape == BG_SHAPE_NONE) {
            super.onDraw(canvas);
            return;
        }
        if (this.mIsSelected) {
            if ((this.mIsPressEffect) && (this.mPressedPaint != null)) {
                if (this.mBgShape == BG_SHAPE_CIRCULAR) {
                    drawCircle(canvas, this.mPressedPaint);
                } else if (this.mBgShape == BG_SHAPE_ROUNDED_SQUARE) {
                    drawRect(canvas, this.mPressedPaint);
                }
            }
        } else if (this.mBgShape == BG_SHAPE_CIRCULAR) {
            drawCircle(canvas, this.mNormalPaint);
        } else if (this.mBgShape == BG_SHAPE_ROUNDED_SQUARE) {
            drawRect(canvas, this.mNormalPaint);
        }
        super.onDraw(canvas);
    }

    private void drawCircle(Canvas canvas, Paint paint) {
        int radius = getMeasuredWidth() / 2;
        canvas.drawCircle(radius, radius, radius, paint);
    }

    private void drawRect(Canvas canvas, Paint paint) {
        if (this.mSquareRect == null) {
            this.mSquareRect = new RectF();
            this.mSquareRect.left = 0.0F;
            this.mSquareRect.top = 0.0F;
            this.mSquareRect.right = getMeasuredWidth();
            this.mSquareRect.bottom = getMeasuredWidth();
        }
        canvas.drawRoundRect(this.mSquareRect, this.mAngle, this.mAngle, paint);
    }

    protected int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}

