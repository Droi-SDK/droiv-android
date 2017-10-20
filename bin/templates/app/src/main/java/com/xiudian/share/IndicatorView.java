package com.xiudian.share;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

class IndicatorView extends View {

    private int mIndicatorWidth;
    private int mIndicatorMargin;
    private int mPageCount;
    private int mSelectPosition;
    private float mLeftPosition;
    private Paint mSelectPaint;
    private Paint mNormalPaint;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);

        int desired = getPaddingLeft() + getPaddingRight()
                + this.mIndicatorWidth * this.mPageCount * 2
                + this.mIndicatorMargin * (this.mPageCount - 1);

        this.mLeftPosition = ((getMeasuredWidth() - desired) / 2.0F + getPaddingLeft());
        int width;
        if (mode == MeasureSpec.EXACTLY) {
            width = Math.max(desired, size);
        } else {
            if (mode == Integer.MIN_VALUE) {
                width = Math.min(desired, size);
            } else {
                width = desired;
            }
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            int desired = getPaddingTop() + getPaddingBottom() + this.mIndicatorWidth * 2;
            if (mode == Integer.MIN_VALUE) {
                height = Math.min(desired, size);
            } else {
                height = desired;
            }
        }
        return height;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((this.mSelectPaint == null) || (this.mNormalPaint == null)) {
            return;
        }
        float left = this.mLeftPosition;
        left += this.mIndicatorWidth;
        for (int i = 0; i < this.mPageCount; i++) {
            canvas.drawCircle(left, this.mIndicatorWidth, this.mIndicatorWidth,
                    i == this.mSelectPosition ? this.mSelectPaint : this.mNormalPaint);
            left += this.mIndicatorMargin + this.mIndicatorWidth * 2;
        }
    }

    public void setSelectedPosition(int position) {
        this.mSelectPosition = position;
        invalidate();
    }

    public void setPageCount(int pageCount) {
        this.mPageCount = pageCount;
        invalidate();
    }

    public void setIndicator(int size, int margin) {
        this.mIndicatorMargin = dip2px(margin);
        this.mIndicatorWidth = dip2px(size);
    }

    public void setIndicatorColor(int normalColor, int selectColor) {
        this.mSelectPaint = new Paint();
        this.mSelectPaint.setStyle(Paint.Style.FILL);
        this.mSelectPaint.setAntiAlias(true);
        this.mSelectPaint.setColor(selectColor);

        this.mNormalPaint = new Paint();
        this.mNormalPaint.setStyle(Paint.Style.FILL);
        this.mNormalPaint.setAntiAlias(true);
        this.mNormalPaint.setColor(normalColor);
    }

    protected int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
