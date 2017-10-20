package com.xiudian.share;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SocializeViewPager extends ViewGroup {

    private static final String TAG = "ViewPager";
    private float mLastMotionX;
    private float mLastMotionY;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mActivePointerId = -1;
    private static final int INVALID_POINTER = -1;
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mCloseEnough;
    private static final int CLOSE_ENOUGH = 2;
    private int mTouchSlop;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private int mFlingDistance;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int DEFAULT_WIDTH_FACTOR = 1;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private int mOffscreenPageLimit = 1;
    private List<ItemInfo> mItems = new ArrayList();
    private final ItemInfo mTempItem = new ItemInfo();
    private int mCurPosition;
    private SocializePagerAdapter mAdapter;
    private PagerObserver mObserver;
    private int mExpectedAdapterCount;
    private boolean mPopulatePending;
    private boolean mFirstLayout;
    private int mRestoredCurItem = -1;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mDefaultGutterSize;
    private int mGutterSize;
    private float mFirstOffset;
    private float mLastOffset;
    private boolean mInLayout;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private boolean mCalledSuper;
    private boolean mIsScrollStarted;
    private Scroller mScroller;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2;
    private final Rect mTempRect = new Rect();
    private final Runnable mEndScrollRunnable = new Runnable() {
        public void run() {
            SocializeViewPager.this.setScrollState(0);
            SocializeViewPager.this.populate();
        }
    };
    private int mScrollState = 0;
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() {
        public int compare(ItemInfo lhs, ItemInfo rhs) {
            return lhs.position - rhs.position;
        }
    };
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0F;
            return t * t * t * t * t + 1.0F;
        }
    };

    public SocializeViewPager(Context context) {
        super(context);
        init();
    }

    public SocializeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SocializeViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public SocializeViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        float density = context.getResources().getDisplayMetrics().density;

        this.mTouchSlop = configuration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = ((int) (400.0F * density));
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mCloseEnough = ((int) (2.0F * density));

        this.mFlingDistance = ((int) (25.0F * density));
        this.mDefaultGutterSize = ((int) (16.0F * density));

        ViewCompat.setOnApplyWindowInsetsListener(this, new ViewCompat.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat originalInsets) {
                WindowInsetsCompat applied = ViewCompat.onApplyWindowInsets(v, originalInsets);
                if (applied.isConsumed()) {
                    return applied;
                }
                Rect res = SocializeViewPager.this.mTempRect;
                res.left = applied.getSystemWindowInsetLeft();
                res.top = applied.getSystemWindowInsetTop();
                res.right = applied.getSystemWindowInsetRight();
                res.bottom = applied.getSystemWindowInsetBottom();

                int i = 0;
                for (int count = SocializeViewPager.this.getChildCount(); i < count; i++) {
                    WindowInsetsCompat childInsets = ViewCompat.dispatchApplyWindowInsets(
                            SocializeViewPager.this.getChildAt(i), applied);
                    res.left = Math.min(childInsets.getSystemWindowInsetLeft(), res.left);
                    res.top = Math.min(childInsets.getSystemWindowInsetTop(), res.top);
                    res.right = Math.min(childInsets.getSystemWindowInsetRight(), res.right);
                    res.bottom = Math.min(childInsets.getSystemWindowInsetBottom(), res.bottom);
                }
                return applied.replaceSystemWindowInsets(res.left, res.top, res.right, res.bottom);
            }
        });
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 0xFF;
        if ((action == 3) || (action == 1)) {
            resetTouch();
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (action) {
            case 2:
                int activePointerId = this.mActivePointerId;
                if (activePointerId != -1) {
                    int pointerIndex = ev.findPointerIndex(activePointerId);
                    float x = ev.getX(pointerIndex);
                    float dx = x - this.mLastMotionX;
                    float xDiff = Math.abs(dx);
                    float y = ev.getY(pointerIndex);
                    float yDiff = Math.abs(y - this.mInitialMotionY);
                    if ((dx != 0.0F) && (!isGutterDrag(this.mLastMotionX, dx)) &&
                            (canScroll(this, false, (int) dx, (int) x, (int) y))) {
                        this.mLastMotionX = x;
                        this.mLastMotionY = y;
                        this.mIsUnableToDrag = true;
                        return false;
                    }
                    if ((xDiff > this.mTouchSlop) && (xDiff * 0.5F > yDiff)) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        setScrollState(1);
                        this.mLastMotionX = (dx > 0.0F ? this.mInitialMotionX + this.mTouchSlop
                                : this.mInitialMotionX - this.mTouchSlop);
                        this.mLastMotionY = y;
                    } else if (yDiff > this.mTouchSlop) {
                        this.mIsUnableToDrag = true;
                    }
                    if (this.mIsBeingDragged) {
                        if (performDrag(x)) {
                            ViewCompat.postInvalidateOnAnimation(this);
                        }
                    }
                }
                break;
            case 0:
                this.mLastMotionX = (this.mInitialMotionX = ev.getX());
                this.mLastMotionY = (this.mInitialMotionY = ev.getY());
                this.mActivePointerId = ev.getPointerId(0);
                this.mIsUnableToDrag = false;
                this.mIsScrollStarted = true;
                this.mScroller.computeScrollOffset();
                if ((this.mScrollState == 2) &&
                        (Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough)) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    populate();
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                } else {
                    completeScroll(false);
                    this.mIsBeingDragged = false;
                }
                break;
            case 6:
                onSecondaryPointerUp(ev);
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if ((ev.getAction() == 0) && (ev.getEdgeFlags() != 0)) {
            return false;
        }
        if ((this.mAdapter == null) || (this.mAdapter.getCount() == 0)) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        int action = ev.getAction();
        boolean needsInvalidate = false;
        switch (action & 0xFF) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                this.mLastMotionX = (this.mInitialMotionX = ev.getX());
                this.mLastMotionY = (this.mInitialMotionY = ev.getY());
                this.mActivePointerId = ev.getPointerId(0);
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                    if (pointerIndex == -1) {
                        needsInvalidate = resetTouch();
                        break;
                    }
                    float x = ev.getX(pointerIndex);
                    float xDiff = Math.abs(x - this.mLastMotionX);
                    float y = ev.getY(pointerIndex);
                    float yDiff = Math.abs(y - this.mLastMotionY);
                    if ((xDiff > this.mTouchSlop) && (xDiff > yDiff)) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        this.mLastMotionX = (x - this.mInitialMotionX > 0.0F ?
                                this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop);
                        this.mLastMotionY = y;
                        setScrollState(1);
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    int activePointerIndex = ev.findPointerIndex(this.mActivePointerId);
                    float x = ev.getX(activePointerIndex);
                    needsInvalidate |= performDrag(x);
                }
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
                    this.mPopulatePending = true;
                    int width = getClientWidth();
                    int scrollX = getScrollX();
                    ItemInfo ii = infoForCurrentScrollPosition();
                    float marginOffset = 0.0F;
                    int currentPage = ii.position;
                    float pageOffset = (scrollX / width - ii.offset) / 1.0F;
                    int activePointerIndex = ev.findPointerIndex(this.mActivePointerId);
                    float x = ev.getX(activePointerIndex);
                    int totalDelta = (int) (x - this.mInitialMotionX);
                    int nextPage = determineTargetPage(currentPage, pageOffset, initialVelocity, totalDelta);
                    setCurrentItemInternal(nextPage, true, true, initialVelocity);
                    needsInvalidate = resetTouch();
                }
                break;
            case 3:
                if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurPosition, true, 0, false);
                    needsInvalidate = resetTouch();
                }
                break;
            case 5:
                int index = MotionEventCompat.getActionIndex(ev);
                float x = ev.getX(index);
                this.mLastMotionX = x;
                this.mActivePointerId = ev.getPointerId(index);
                break;
            case 6:
                onSecondaryPointerUp(ev);
                this.mLastMotionX = ev.getX(ev.findPointerIndex(this.mActivePointerId));
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private boolean resetTouch() {
        this.mActivePointerId = -1;
        endDrag();
        return true;
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private boolean isGutterDrag(float x, float dx) {
        return ((x < this.mGutterSize) && (dx > 0.0F)) || ((x > getWidth() - this.mGutterSize) && (dx < 0.0F));
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if ((v instanceof ViewGroup)) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            int count = group.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if ((x + scrollX >= child.getLeft()) && (x + scrollX < child.getRight()) &&
                        (y + scrollY >= child.getTop()) && (y + scrollY < child.getBottom()) &&
                        (canScroll(child, true, dx, x + scrollX - child.getLeft(), y + scrollY - child
                                .getTop()))) {
                    return true;
                }
            }
        }
        return (checkV) && (ViewCompat.canScrollHorizontally(v, -dx));
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private boolean performDrag(float x) {
        boolean needsInvalidate = false;

        float deltaX = this.mLastMotionX - x;
        this.mLastMotionX = x;

        float oldScrollX = getScrollX();
        float scrollX = oldScrollX + deltaX;
        int width = getClientWidth();

        float leftBound = width * this.mFirstOffset;
        float rightBound = width * this.mLastOffset;
        boolean leftAbsolute = true;
        boolean rightAbsolute = true;

        ItemInfo firstItem = (ItemInfo) this.mItems.get(0);
        ItemInfo lastItem = (ItemInfo) this.mItems.get(this.mItems.size() - 1);
        if (firstItem.position != 0) {
            leftAbsolute = false;
            leftBound = firstItem.offset * width;
        }
        if (lastItem.position != this.mAdapter.getCount() - 1) {
            rightAbsolute = false;
            rightBound = lastItem.offset * width;
        }
        if (scrollX < leftBound) {
            if (leftAbsolute) {
                needsInvalidate = true;
            }
            scrollX = leftBound;
        } else if (scrollX > rightBound) {
            if (rightAbsolute) {
                needsInvalidate = true;
            }
            scrollX = rightBound;
        }
        this.mLastMotionX += scrollX - (int) scrollX;
        scrollTo((int) scrollX, getScrollY());
        pageScrolled((int) scrollX);
        return needsInvalidate;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mLastMotionX = ev.getX(newPointerIndex);
            this.mActivePointerId = ev.getPointerId(newPointerIndex);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private int determineTargetPage(int currentPage, float pageOffset, int velocity, int deltaX) {
        int targetPage;
        if ((Math.abs(deltaX) > this.mFlingDistance) && (Math.abs(velocity) > this.mMinimumVelocity)) {
            targetPage = velocity > 0 ? currentPage : currentPage + 1;
        } else {
            float truncator = currentPage >= this.mCurPosition ? 0.4F : 0.6F;
            targetPage = currentPage + (int) (pageOffset + truncator);
        }
        if (this.mItems.size() > 0) {
            ItemInfo firstItem = (ItemInfo) this.mItems.get(0);
            ItemInfo lastItem = (ItemInfo) this.mItems.get(this.mItems.size() - 1);

            targetPage = Math.max(firstItem.position, Math.min(targetPage, lastItem.position));
        }
        return targetPage;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        if ((this.mScroller != null) && (!this.mScroller.isFinished())) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int measuredWidth = getMeasuredWidth();
        int maxGutterSize = measuredWidth / 10;
        this.mGutterSize = Math.min(maxGutterSize, this.mDefaultGutterSize);
        int childWidthSize = measuredWidth - getPaddingLeft() - getPaddingRight();
        int childHeightSize = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if ((lp == null) || (!lp.isDecor)) {
                    int widthSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
                    child.measure(widthSpec, childHeightMeasureSpec);
                }
            }
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            recomputeScrollPosition(w, oldw, 0, 0);
        }
    }

    private void recomputeScrollPosition(int width, int oldWidth, int margin, int oldMargin) {
        if ((oldWidth > 0) && (!this.mItems.isEmpty())) {
            if (!this.mScroller.isFinished()) {
                this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
            } else {
                int widthWithMargin = width - getPaddingLeft() - getPaddingRight() + margin;
                int oldWidthWithMargin = oldWidth - getPaddingLeft() - getPaddingRight() + oldMargin;
                int xpos = getScrollX();
                float pageOffset = xpos / oldWidthWithMargin;
                int newOffsetPixels = (int) (pageOffset * widthWithMargin);
                scrollTo(newOffsetPixels, getScrollY());
            }
        } else {
            ItemInfo ii = infoForPosition(this.mCurPosition);
            float scrollOffset = ii != null ? Math.min(ii.offset, this.mLastOffset) : 0.0F;
            int scrollPos = (int) (scrollOffset * (width - getPaddingLeft() - getPaddingRight()));
            if (scrollPos != getScrollX()) {
                completeScroll(false);
                scrollTo(scrollPos, getScrollY());
            }
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int width = r - l;
        int height = b - t;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int childWidth = width - paddingLeft - paddingRight;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                ItemInfo ii;
                if ((!lp.isDecor) && ((ii = infoForChild(child)) != null)) {
                    int loff = (int) (childWidth * ii.offset);
                    int childLeft = paddingLeft + loff;
                    int childTop = paddingTop;
                    if (lp.needsMeasure) {
                        lp.needsMeasure = false;
                        int widthSpec = View.MeasureSpec.makeMeasureSpec((int) (childWidth * lp.widthFactor), MeasureSpec.EXACTLY);
                        int heightSpec = View.MeasureSpec.makeMeasureSpec(height - paddingTop - paddingBottom, MeasureSpec.EXACTLY);
                        child.measure(widthSpec, heightSpec);
                    }
                    child.layout(childLeft, childTop, childLeft + child
                            .getMeasuredWidth(), childTop + child
                            .getMeasuredHeight());
                }
            }
        }
        if (this.mFirstLayout) {
            scrollToItem(this.mCurPosition, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!checkLayoutParams(params)) {
            params = generateLayoutParams(params);
        }
        LayoutParams lp = (LayoutParams) params;
        lp.isDecor |= false;
        if (this.mInLayout) {
            if ((lp != null) && (lp.isDecor)) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            lp.needsMeasure = true;
            addViewInLayout(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    private void populate() {
        populate(this.mCurPosition);
    }

    private void populate(int position) {
        ItemInfo oldCurInfo = null;
        if (this.mCurPosition != position) {
            oldCurInfo = infoForPosition(this.mCurPosition);
            this.mCurPosition = position;
        }
        if (this.mAdapter == null) {
            return;
        }
        if (this.mPopulatePending) {
            return;
        }
        if (getWindowToken() == null) {
            return;
        }
        this.mAdapter.startUpdate(this);

        int pageLimit = this.mOffscreenPageLimit;
        int startPos = Math.max(0, this.mCurPosition - pageLimit);
        int N = this.mAdapter.getCount();
        int endPos = Math.min(N - 1, this.mCurPosition + pageLimit);
        if (N != this.mExpectedAdapterCount) {
            String resName;
            try {
                resName = getResources().getResourceName(getId());
            } catch (Resources.NotFoundException e) {
                resName = Integer.toHexString(getId());
            }
            throw new IllegalStateException("The application's SocializePagerAdapter changed the adapter's contents without calling SocializePagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + N + " Pager id: " + resName + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
        }
        int curIndex = -1;
        ItemInfo curItem = null;
        for (curIndex = 0; curIndex < this.mItems.size(); curIndex++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(curIndex);
            if (ii.position >= this.mCurPosition) {
                if (ii.position != this.mCurPosition) {
                    break;
                }
                curItem = ii;
                break;
            }
        }
        if ((curItem == null) && (N > 0)) {
            curItem = addNewItem(this.mCurPosition, curIndex);
        }
        if (curItem != null) {
            float extraWidthLeft = 0.0F;
            int itemIndex = curIndex - 1;
            ItemInfo ii = itemIndex >= 0 ? (ItemInfo) this.mItems.get(itemIndex) : null;
            int clientWidth = getClientWidth();

            float leftWidthNeeded = clientWidth <= 0 ? 0.0F : 1.0F + getPaddingLeft() / clientWidth;
            Log.d("ViewPager", "populate leftWidthNeeded:" + leftWidthNeeded);
            for (int pos = this.mCurPosition - 1; pos >= 0; pos--) {
                if ((extraWidthLeft >= leftWidthNeeded) && (pos < startPos)) {
                    if (ii == null) {
                        break;
                    }
                    if ((pos == ii.position) && (!ii.scrolling)) {
                        this.mItems.remove(itemIndex);
                        this.mAdapter.destroyItem(this, pos, ii.object);

                        itemIndex--;
                        curIndex--;
                        ii = itemIndex >= 0 ? (ItemInfo) this.mItems.get(itemIndex) : null;
                    }
                } else if ((ii != null) && (pos == ii.position)) {
                    extraWidthLeft += 1.0F;
                    itemIndex--;
                    ii = itemIndex >= 0 ? (ItemInfo) this.mItems.get(itemIndex) : null;
                } else {
                    addNewItem(pos, itemIndex + 1);
                    extraWidthLeft += 1.0F;
                    curIndex++;
                    ii = itemIndex >= 0 ? (ItemInfo) this.mItems.get(itemIndex) : null;
                }
            }
            float extraWidthRight = 1.0F;
            itemIndex = curIndex + 1;
            if (extraWidthRight < 2.0F) {
                ii = itemIndex < this.mItems.size() ? (ItemInfo) this.mItems.get(itemIndex) : null;

                float rightWidthNeeded = clientWidth <= 0 ? 0.0F : getPaddingRight() / clientWidth + 2.0F;
                for (int pos = this.mCurPosition + 1; pos < N; pos++) {
                    if ((extraWidthRight >= rightWidthNeeded) && (pos > endPos)) {
                        if (ii == null) {
                            break;
                        }
                        if ((pos == ii.position) && (!ii.scrolling)) {
                            this.mItems.remove(itemIndex);
                            this.mAdapter.destroyItem(this, pos, ii.object);

                            ii = itemIndex < this.mItems.size() ? (ItemInfo) this.mItems.get(itemIndex) : null;
                        }
                    } else if ((ii != null) && (pos == ii.position)) {
                        extraWidthRight += 1.0F;
                        itemIndex++;
                        ii = itemIndex < this.mItems.size() ? (ItemInfo) this.mItems.get(itemIndex) : null;
                    } else {
                        addNewItem(pos, itemIndex);
                        itemIndex++;
                        extraWidthRight += 1.0F;
                        ii = itemIndex < this.mItems.size() ? (ItemInfo) this.mItems.get(itemIndex) : null;
                    }
                }
            }
            calculatePageOffsets(curItem, curIndex, oldCurInfo);
        }
        this.mAdapter.setPrimaryItem(this, this.mCurPosition, curItem != null ? curItem.object : null);
        this.mAdapter.finishUpdate(this);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            lp.childIndex = i;
            if ((!lp.isDecor) && (lp.widthFactor == 0.0F)) {
                ItemInfo ii = infoForChild(child);
                if (ii != null) {
                    lp.widthFactor = 1.0F;
                    lp.position = ii.position;
                }
            }
        }
        if (hasFocus()) {
            View currentFocused = findFocus();
            ItemInfo ii = currentFocused != null ? infoForAnyChild(currentFocused) : null;
            if ((ii == null) || (ii.position != this.mCurPosition)) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    ii = infoForChild(child);
                    if ((ii != null) && (ii.position == this.mCurPosition) &&
                            (child.requestFocus(2))) {
                        break;
                    }
                }
            }
        }
    }

    private void calculatePageOffsets(ItemInfo curItem, int curIndex, ItemInfo oldCurInfo) {
        int N = this.mAdapter.getCount();
        float marginOffset = 0.0F;
        if (oldCurInfo != null) {
            int oldCurPosition = oldCurInfo.position;
            if (oldCurPosition < curItem.position) {
                int itemIndex = 0;
                ItemInfo ii = null;
                float offset = oldCurInfo.offset + 1.0F + 0.0F;
                for (int pos = oldCurPosition + 1; (pos <= curItem.position) && (itemIndex < this.mItems.size()); pos++) {
                    ii = (ItemInfo) this.mItems.get(itemIndex);
                    while ((pos > ii.position) && (itemIndex < this.mItems.size() - 1)) {
                        itemIndex++;
                        ii = (ItemInfo) this.mItems.get(itemIndex);
                    }
                    while (pos < ii.position) {
                        offset += this.mAdapter.getPageWidth(pos) + 0.0F;
                        pos++;
                    }
                    ii.offset = offset;
                    offset += 1.0F;
                }
            } else if (oldCurPosition > curItem.position) {
                int itemIndex = this.mItems.size() - 1;
                ItemInfo ii = null;
                float offset = oldCurInfo.offset;
                for (int pos = oldCurPosition - 1; (pos >= curItem.position) && (itemIndex >= 0); pos--) {
                    ii = (ItemInfo) this.mItems.get(itemIndex);
                    while ((pos < ii.position) && (itemIndex > 0)) {
                        itemIndex--;
                        ii = (ItemInfo) this.mItems.get(itemIndex);
                    }
                    while (pos > ii.position) {
                        offset -= this.mAdapter.getPageWidth(pos) + 0.0F;
                        pos--;
                    }
                    offset -= 1.0F;
                    ii.offset = offset;
                }
            }
        }
        int itemCount = this.mItems.size();
        float offset = curItem.offset;
        int pos = curItem.position - 1;
        this.mFirstOffset = (curItem.position == 0 ? curItem.offset : -3.4028235E38F);
        this.mLastOffset = (curItem.position == N - 1 ? curItem.offset + 1.0F - 1.0F : Float.MAX_VALUE);
        for (int i = curIndex - 1; i >= 0; pos--) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            while (pos > ii.position) {
                offset -= this.mAdapter.getPageWidth(pos--) + 0.0F;
            }
            offset -= 1.0F;
            ii.offset = offset;
            if (ii.position == 0) {
                this.mFirstOffset = offset;
            }
            i--;
        }
        offset = curItem.offset + 1.0F + 0.0F;
        pos = curItem.position + 1;
        for (int i = curIndex + 1; i < itemCount; pos++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            while (pos < ii.position) {
                offset += this.mAdapter.getPageWidth(pos++) + 0.0F;
            }
            if (ii.position == N - 1) {
                this.mLastOffset = (offset + 1.0F - 1.0F);
            }
            ii.offset = offset;
            offset += 1.0F;
            i++;
        }
    }

    public void setAdapter(SocializePagerAdapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.setViewPagerObserver(null);
            this.mAdapter.startUpdate(this);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo ii = (ItemInfo) this.mItems.get(i);
                this.mAdapter.destroyItem(this, ii.position, ii.object);
            }
            this.mAdapter.finishUpdate(this);
            this.mItems.clear();
            removeAllViews();
            this.mCurPosition = 0;
            scrollTo(0, 0);
        }
        this.mAdapter = adapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean wasFirstLayout = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!wasFirstLayout) {
                populate();
            } else {
                requestLayout();
            }
        }
    }

    public interface OnPageChangeListener {
        void onPageScrolled(int paramInt1, float paramFloat, int paramInt2);

        void onPageSelected(int paramInt);

        void onPageScrollStateChanged(int paramInt);
    }

    static class ItemInfo {
        Object object;
        int position;
        boolean scrolling;
        float offset;
    }

    private class PagerObserver extends DataSetObserver {
        PagerObserver() {
        }

        public void onChanged() {
            SocializeViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            SocializeViewPager.this.dataSetChanged();
        }
    }

    void dataSetChanged() {
        int adapterCount = this.mAdapter.getCount();
        this.mExpectedAdapterCount = adapterCount;

        boolean needPopulate = (this.mItems.size() < this.mOffscreenPageLimit * 2 + 1) && (this.mItems.size() < adapterCount);
        int newCurrItem = this.mCurPosition;

        boolean isUpdating = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            int newPos = this.mAdapter.getItemPosition(ii.object);
            if (newPos != -1) {
                if (newPos == -2) {
                    this.mItems.remove(i);
                    i--;
                    if (!isUpdating) {
                        this.mAdapter.startUpdate(this);
                        isUpdating = true;
                    }
                    this.mAdapter.destroyItem(this, ii.position, ii.object);
                    needPopulate = true;
                    if (this.mCurPosition == ii.position) {
                        newCurrItem = Math.max(0, Math.min(this.mCurPosition, adapterCount - 1));
                        needPopulate = true;
                    }
                } else if (ii.position != newPos) {
                    if (ii.position == this.mCurPosition) {
                        newCurrItem = newPos;
                    }
                    ii.position = newPos;
                    needPopulate = true;
                }
            }
        }
        if (isUpdating) {
            this.mAdapter.finishUpdate(this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (needPopulate) {
            setCurrentItemInternal(newCurrItem, false, true);
            requestLayout();
        }
    }

    public void setCurrentItem(int item) {
        this.mPopulatePending = false;
        setCurrentItemInternal(item, !this.mFirstLayout, false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        this.mPopulatePending = false;
        setCurrentItemInternal(item, smoothScroll, false);
    }

    public int getCurrentItem() {
        return this.mCurPosition;
    }

    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
        setCurrentItemInternal(item, smoothScroll, always, 0);
    }

    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
        if ((this.mAdapter == null) || (this.mAdapter.getCount() <= 0)) {
            return;
        }
        if ((!always) && (this.mCurPosition == item) && (this.mItems.size() != 0)) {
            return;
        }
        if (item < 0) {
            item = 0;
        } else if (item >= this.mAdapter.getCount()) {
            item = this.mAdapter.getCount() - 1;
        }
        int pageLimit = this.mOffscreenPageLimit;
        if ((item > this.mCurPosition + pageLimit) || (item < this.mCurPosition - pageLimit)) {
            for (int i = 0; i < this.mItems.size(); i++) {
                ((ItemInfo) this.mItems.get(i)).scrolling = true;
            }
        }
        boolean dispatchSelected = this.mCurPosition != item;
        if (this.mFirstLayout) {
            this.mCurPosition = item;
            if (dispatchSelected) {
                dispatchOnPageSelected(item);
            }
            requestLayout();
        } else {
            populate(item);
            scrollToItem(item, smoothScroll, velocity, dispatchSelected);
        }
    }

    private void scrollToItem(int item, boolean smoothScroll, int velocity, boolean dispatchSelected) {
        ItemInfo curInfo = infoForPosition(item);
        int destX = 0;
        if (curInfo != null) {
            int width = getClientWidth();
            destX = (int) (width * Math.max(this.mFirstOffset,
                    Math.min(curInfo.offset, this.mLastOffset)));
        }
        if (smoothScroll) {
            smoothScrollTo(destX, 0, velocity);
            if (dispatchSelected) {
                dispatchOnPageSelected(item);
            }
        } else {
            if (dispatchSelected) {
                dispatchOnPageSelected(item);
            }
            completeScroll(false);
            scrollTo(destX, 0);
            pageScrolled(destX);
        }
    }

    void smoothScrollTo(int x, int y) {
        smoothScrollTo(x, y, 0);
    }

    void smoothScrollTo(int x, int y, int velocity) {
        if (getChildCount() == 0) {
            return;
        }
        boolean wasScrolling = (this.mScroller != null) && (!this.mScroller.isFinished());
        int sx;
        if (wasScrolling) {
            sx = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
        } else {
            sx = getScrollX();
        }
        int sy = getScrollY();
        int dx = x - sx;
        int dy = y - sy;
        if ((dx == 0) && (dy == 0)) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollState(2);

        int width = getClientWidth();
        int halfWidth = width / 2;
        float distanceRatio = Math.min(1.0F, 1.0F * Math.abs(dx) / width);

        float distance = halfWidth + halfWidth * distanceInfluenceForSnapDuration(distanceRatio);

        velocity = Math.abs(velocity);
        int duration;
        if (velocity > 0) {
            duration = 4 * Math.round(1000.0F * Math.abs(distance / velocity));
        } else {
            float pageWidth = width * this.mAdapter.getPageWidth(this.mCurPosition);
            float pageDelta = Math.abs(dx) / pageWidth;
            duration = (int) ((pageDelta + 1.0F) * 100.0F);
        }
        duration = Math.min(duration, 600);

        this.mIsScrollStarted = false;
        this.mScroller.startScroll(sx, sy, dx, dy, duration);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void computeScroll() {
        this.mIsScrollStarted = true;
        if ((!this.mScroller.isFinished()) && (this.mScroller.computeScrollOffset())) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            if ((oldX != x) || (oldY != y)) {
                scrollTo(x, y);
                if (!pageScrolled(x)) {
                    this.mScroller.abortAnimation();
                    scrollTo(0, y);
                }
            }
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }
        completeScroll(true);
    }

    private void completeScroll(boolean postEvents) {
        boolean needPopulate = this.mScrollState == 2;
        if (needPopulate) {
            boolean wasScrolling = !this.mScroller.isFinished();
            if (wasScrolling) {
                this.mScroller.abortAnimation();
                int oldX = getScrollX();
                int oldY = getScrollY();
                int x = this.mScroller.getCurrX();
                int y = this.mScroller.getCurrY();
                if ((oldX != x) || (oldY != y)) {
                    scrollTo(x, y);
                    if (x != oldX) {
                        pageScrolled(x);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if (ii.scrolling) {
                needPopulate = true;
                ii.scrolling = false;
            }
        }
        if (needPopulate) {
            if (postEvents) {
                ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
            } else {
                this.mEndScrollRunnable.run();
            }
        }
    }

    private boolean pageScrolled(int xpos) {
        if (this.mItems.size() == 0) {
            if (this.mFirstLayout) {
                return false;
            }
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0F, 0);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
            return false;
        }
        ItemInfo ii = infoForCurrentScrollPosition();
        int width = getClientWidth();
        int widthWithMargin = width;
        int currentPage = ii.position;
        float pageOffset = (xpos / width - ii.offset) / 1.0F;

        int offsetPixels = (int) (pageOffset * widthWithMargin);

        this.mCalledSuper = false;
        onPageScrolled(currentPage, pageOffset, offsetPixels);
        if (!this.mCalledSuper) {
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        return true;
    }

    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        dispatchOnPageScrolled(position, offset, offsetPixels);
        this.mCalledSuper = true;
    }

    float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5F;
        f = (float) (f * 0.4712389167638204D);
        return (float) Math.sin(f);
    }

    void setScrollState(int newState) {
        if (this.mScrollState == newState) {
            return;
        }
        this.mScrollState = newState;
        dispatchOnScrollStateChanged(newState);
    }

    private void dispatchOnPageScrolled(int position, float offset, int offsetPixels) {
        if (this.mOnPageChangeListeners != null) {
            int i = 0;
            for (int z = this.mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageScrolled(position, offset, offsetPixels);
                }
            }
        }
    }

    private void dispatchOnPageSelected(int position) {
        if (this.mOnPageChangeListeners != null) {
            int i = 0;
            for (int z = this.mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageSelected(position);
                }
            }
        }
    }

    private void dispatchOnScrollStateChanged(int state) {
        if (this.mOnPageChangeListeners != null) {
            int i = 0;
            for (int z = this.mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        }
    }

    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList();
        }
        this.mOnPageChangeListeners.add(listener);
    }

    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.remove(listener);
        }
    }

    public void clearOnPageChangeListeners() {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.clear();
        }
    }

    private int getClientWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                ItemInfo ii = infoForChild(child);
                if ((ii != null) && (ii.position == this.mCurPosition) &&
                        (child.dispatchPopulateAccessibilityEvent(event))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return (super.dispatchKeyEvent(event)) || (executeKeyEvent(event));
    }

    public boolean executeKeyEvent(KeyEvent event) {
        boolean handled = false;
        if (event.getAction() == 0) {
            switch (event.getKeyCode()) {
                case 21:
                    handled = arrowScroll(17);
                    break;
                case 22:
                    handled = arrowScroll(66);
                    break;
                case 61:
                    if (Build.VERSION.SDK_INT >= 11) {
                        if (KeyEventCompat.hasNoModifiers(event)) {
                            handled = arrowScroll(2);
                        } else if (KeyEventCompat.hasModifiers(event, 1)) {
                            handled = arrowScroll(1);
                        }
                    }
                    break;
            }
        }
        return handled;
    }

    public boolean arrowScroll(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        } else if (currentFocused != null) {
            boolean isChild = false;
            for (ViewParent parent = currentFocused.getParent(); (parent instanceof ViewGroup); parent = parent.getParent()) {
                if (parent == this) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {
                StringBuilder sb = new StringBuilder();
                sb.append(currentFocused.getClass().getSimpleName());
                for (ViewParent parent = currentFocused.getParent(); (parent instanceof ViewGroup); parent = parent.getParent()) {
                    sb.append(" => ").append(parent.getClass().getSimpleName());
                }
                Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + sb
                        .toString());
                currentFocused = null;
            }
        }
        boolean handled = false;

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
        if ((nextFocused != null) && (nextFocused != currentFocused)) {
            if (direction == 17) {
                int nextLeft = getChildRectInPagerCoordinates(this.mTempRect, nextFocused).left;
                int currLeft = getChildRectInPagerCoordinates(this.mTempRect, currentFocused).left;
                if ((currentFocused != null) && (nextLeft >= currLeft)) {
                    handled = pageLeft();
                } else {
                    handled = nextFocused.requestFocus();
                }
            } else if (direction == 66) {
                int nextLeft = getChildRectInPagerCoordinates(this.mTempRect, nextFocused).left;
                int currLeft = getChildRectInPagerCoordinates(this.mTempRect, currentFocused).left;
                if ((currentFocused != null) && (nextLeft <= currLeft)) {
                    handled = pageRight();
                } else {
                    handled = nextFocused.requestFocus();
                }
            }
        } else if ((direction == 17) || (direction == 1)) {
            handled = pageLeft();
        } else if ((direction == 66) || (direction == 2)) {
            handled = pageRight();
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    private Rect getChildRectInPagerCoordinates(Rect outRect, View child) {
        if (outRect == null) {
            outRect = new Rect();
        }
        if (child == null) {
            outRect.set(0, 0, 0, 0);
            return outRect;
        }
        outRect.left = child.getLeft();
        outRect.right = child.getRight();
        outRect.top = child.getTop();
        outRect.bottom = child.getBottom();

        ViewParent parent = child.getParent();
        while (((parent instanceof ViewGroup)) && (parent != this)) {
            ViewGroup group = (ViewGroup) parent;
            outRect.left += group.getLeft();
            outRect.right += group.getRight();
            outRect.top += group.getTop();
            outRect.bottom += group.getBottom();

            parent = group.getParent();
        }
        return outRect;
    }

    boolean pageLeft() {
        if (this.mCurPosition > 0) {
            setCurrentItem(this.mCurPosition - 1, true);
            return true;
        }
        return false;
    }

    boolean pageRight() {
        if ((this.mAdapter != null) && (this.mCurPosition < this.mAdapter.getCount() - 1)) {
            setCurrentItem(this.mCurPosition + 1, true);
            return true;
        }
        return false;
    }

    ItemInfo addNewItem(int position, int index) {
        ItemInfo ii = new ItemInfo();
        ii.position = position;
        ii.object = this.mAdapter.instantiateItem(this, position);
        if ((index < 0) || (index >= this.mItems.size())) {
            this.mItems.add(ii);
        } else {
            this.mItems.add(index, ii);
        }
        return ii;
    }

    ItemInfo infoForChild(View child) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(child, ii.object)) {
                return ii;
            }
        }
        return null;
    }

    ItemInfo infoForAnyChild(View child) {
        ViewParent parent;
        while ((parent = child.getParent()) != this) {
            if ((parent == null) || (!(parent instanceof View))) {
                return null;
            }
            child = (View) parent;
        }
        return infoForChild(child);
    }

    ItemInfo infoForPosition(int position) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if (ii.position == position) {
                return ii;
            }
        }
        return null;
    }

    private ItemInfo infoForCurrentScrollPosition() {
        int width = getClientWidth();
        float scrollOffset = width > 0 ? getScrollX() / width : 0.0F;
        float marginOffset = 0.0F;
        int lastPos = -1;
        float lastOffset = 0.0F;
        float lastWidth = 0.0F;
        boolean first = true;

        ItemInfo lastItem = null;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if ((!first) && (ii.position != lastPos + 1)) {
                ii = this.mTempItem;
                ii.offset = (lastOffset + lastWidth + 0.0F);
                ii.position = (lastPos + 1);
                i--;
            }
            float offset = ii.offset;

            float leftBound = offset;
            float rightBound = offset + 1.0F + 0.0F;
            if ((first) || (scrollOffset >= leftBound)) {
                if ((scrollOffset < rightBound) || (i == this.mItems.size() - 1)) {
                    return ii;
                }
            } else {
                return lastItem;
            }
            first = false;
            lastPos = ii.position;
            lastOffset = offset;
            lastItem = ii;
        }
        return lastItem;
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        int focusableCount = views.size();

        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == VISIBLE) {
                    ItemInfo ii = infoForChild(child);
                    if ((ii != null) && (ii.position == this.mCurPosition)) {
                        child.addFocusables(views, direction, focusableMode);
                    }
                }
            }
        }
        if ((descendantFocusability != 262144) ||
                (focusableCount == views.size())) {
            if (!isFocusable()) {
                return;
            }
            if (((focusableMode & 0x1) == 1) &&
                    (isInTouchMode()) && (!isFocusableInTouchMode())) {
                return;
            }
            if (views != null) {
                views.add(this);
            }
        }
    }

    public void addTouchables(ArrayList<View> views) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                ItemInfo ii = infoForChild(child);
                if ((ii != null) && (ii.position == this.mCurPosition)) {
                    child.addTouchables(views);
                }
            }
        }
    }

    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        int count = getChildCount();
        int index;
        int increment;
        int end;
        if ((direction & 0x2) != 0) {
            index = 0;
            increment = 1;
            end = count;
        } else {
            index = count - 1;
            increment = -1;
            end = -1;
        }
        for (int i = index; i != end; i += increment) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                ItemInfo ii = infoForChild(child);
                if ((ii != null) && (ii.position == this.mCurPosition) &&
                        (child.requestFocus(direction, previouslyFocusedRect))) {
                    return true;
                }
            }
        }
        return false;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return generateDefaultLayoutParams();
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return ((p instanceof LayoutParams)) && (super.checkLayoutParams(p));
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public boolean isDecor;
        public int gravity;
        float widthFactor = 0.0F;
        boolean needsMeasure;
        int position;
        int childIndex;

        public LayoutParams() {
            super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, SocializeViewPager.LAYOUT_ATTRS);
            this.gravity = a.getInteger(0, 48);
            a.recycle();
        }
    }

    static final int[] LAYOUT_ATTRS = {16842931};
}
