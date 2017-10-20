package com.xiudian.share;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.view.WindowInsets;

class WindowInsetsCompat {

    private static final WindowInsetsCompatImpl IMPL;
    private final Object mInsets;

    private interface WindowInsetsCompatImpl {
        int getSystemWindowInsetLeft(Object paramObject);

        int getSystemWindowInsetTop(Object paramObject);

        int getSystemWindowInsetRight(Object paramObject);

        int getSystemWindowInsetBottom(Object paramObject);

        boolean hasSystemWindowInsets(Object paramObject);

        boolean hasInsets(Object paramObject);

        boolean isConsumed(Object paramObject);

        boolean isRound(Object paramObject);

        WindowInsetsCompat consumeSystemWindowInsets(Object paramObject);

        WindowInsetsCompat replaceSystemWindowInsets(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

        WindowInsetsCompat replaceSystemWindowInsets(Object paramObject, Rect paramRect);

        int getStableInsetTop(Object paramObject);

        int getStableInsetLeft(Object paramObject);

        int getStableInsetRight(Object paramObject);

        int getStableInsetBottom(Object paramObject);

        boolean hasStableInsets(Object paramObject);

        WindowInsetsCompat consumeStableInsets(Object paramObject);

        Object getSourceWindowInsets(Object paramObject);
    }

    private static class WindowInsetsCompatBaseImpl
            implements WindowInsetsCompat.WindowInsetsCompatImpl {
        public int getSystemWindowInsetLeft(Object insets) {
            return 0;
        }

        public int getSystemWindowInsetTop(Object insets) {
            return 0;
        }

        public int getSystemWindowInsetRight(Object insets) {
            return 0;
        }

        public int getSystemWindowInsetBottom(Object insets) {
            return 0;
        }

        public boolean hasSystemWindowInsets(Object insets) {
            return false;
        }

        public boolean hasInsets(Object insets) {
            return false;
        }

        public boolean isConsumed(Object insets) {
            return false;
        }

        public boolean isRound(Object insets) {
            return false;
        }

        public WindowInsetsCompat consumeSystemWindowInsets(Object insets) {
            return null;
        }

        public WindowInsetsCompat replaceSystemWindowInsets(Object insets, int left, int top, int right, int bottom) {
            return null;
        }

        public WindowInsetsCompat replaceSystemWindowInsets(Object insets, Rect systemWindowInsets) {
            return null;
        }

        public int getStableInsetTop(Object insets) {
            return 0;
        }

        public int getStableInsetLeft(Object insets) {
            return 0;
        }

        public int getStableInsetRight(Object insets) {
            return 0;
        }

        public int getStableInsetBottom(Object insets) {
            return 0;
        }

        public boolean hasStableInsets(Object insets) {
            return false;
        }

        public WindowInsetsCompat consumeStableInsets(Object insets) {
            return null;
        }

        public Object getSourceWindowInsets(Object src) {
            return null;
        }
    }

    @TargetApi(20)
    private static class WindowInsetsCompatApi20Impl
            extends WindowInsetsCompat.WindowInsetsCompatBaseImpl {
        public WindowInsetsCompat consumeSystemWindowInsets(Object insets) {
            return new WindowInsetsCompat(((WindowInsets) insets).consumeSystemWindowInsets());
        }

        public int getSystemWindowInsetBottom(Object insets) {
            return ((WindowInsets) insets).getSystemWindowInsetBottom();
        }

        public int getSystemWindowInsetLeft(Object insets) {
            return ((WindowInsets) insets).getSystemWindowInsetLeft();
        }

        public int getSystemWindowInsetRight(Object insets) {
            return ((WindowInsets) insets).getSystemWindowInsetRight();
        }

        public int getSystemWindowInsetTop(Object insets) {
            return ((WindowInsets) insets).getSystemWindowInsetTop();
        }

        public boolean hasInsets(Object insets) {
            return ((WindowInsets) insets).hasInsets();
        }

        public boolean hasSystemWindowInsets(Object insets) {
            return ((WindowInsets) insets).hasSystemWindowInsets();
        }

        public boolean isRound(Object insets) {
            return ((WindowInsets) insets).isRound();
        }

        public WindowInsetsCompat replaceSystemWindowInsets(Object insets, int left, int top, int right, int bottom) {
            return new WindowInsetsCompat(((WindowInsets) insets).replaceSystemWindowInsets(left, top, right, bottom));
        }

        public Object getSourceWindowInsets(Object src) {
            return new WindowInsets((WindowInsets) src);
        }
    }

    @TargetApi(21)
    private static class WindowInsetsCompatApi21Impl
            extends WindowInsetsCompat.WindowInsetsCompatApi20Impl {
        public WindowInsetsCompat consumeStableInsets(Object insets) {
            return new WindowInsetsCompat(((WindowInsets) insets).consumeStableInsets());
        }

        public int getStableInsetBottom(Object insets) {
            return ((WindowInsets) insets).getStableInsetBottom();
        }

        public int getStableInsetLeft(Object insets) {
            return ((WindowInsets) insets).getStableInsetLeft();
        }

        public int getStableInsetRight(Object insets) {
            return ((WindowInsets) insets).getStableInsetRight();
        }

        public int getStableInsetTop(Object insets) {
            return ((WindowInsets) insets).getStableInsetTop();
        }

        public boolean hasStableInsets(Object insets) {
            return ((WindowInsets) insets).hasStableInsets();
        }

        public boolean isConsumed(Object insets) {
            return ((WindowInsets) insets).isConsumed();
        }

        public WindowInsetsCompat replaceSystemWindowInsets(Object insets, Rect systemWindowInsets) {
            return new WindowInsetsCompat(((WindowInsets) insets).replaceSystemWindowInsets(systemWindowInsets));
        }
    }

    static {
        int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            IMPL = new WindowInsetsCompatApi21Impl();
        } else if (version >= 20) {
            IMPL = new WindowInsetsCompatApi20Impl();
        } else {
            IMPL = new WindowInsetsCompatBaseImpl();
        }
    }

    WindowInsetsCompat(Object insets) {
        this.mInsets = insets;
    }

    public WindowInsetsCompat(WindowInsetsCompat src) {
        this.mInsets = (src == null ? null : IMPL.getSourceWindowInsets(src.mInsets));
    }

    public int getSystemWindowInsetLeft() {
        return IMPL.getSystemWindowInsetLeft(this.mInsets);
    }

    public int getSystemWindowInsetTop() {
        return IMPL.getSystemWindowInsetTop(this.mInsets);
    }

    public int getSystemWindowInsetRight() {
        return IMPL.getSystemWindowInsetRight(this.mInsets);
    }

    public int getSystemWindowInsetBottom() {
        return IMPL.getSystemWindowInsetBottom(this.mInsets);
    }

    public boolean hasSystemWindowInsets() {
        return IMPL.hasSystemWindowInsets(this.mInsets);
    }

    public boolean hasInsets() {
        return IMPL.hasInsets(this.mInsets);
    }

    public boolean isConsumed() {
        return IMPL.isConsumed(this.mInsets);
    }

    public boolean isRound() {
        return IMPL.isRound(this.mInsets);
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        return IMPL.consumeSystemWindowInsets(this.mInsets);
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int left, int top, int right, int bottom) {
        return IMPL.replaceSystemWindowInsets(this.mInsets, left, top, right, bottom);
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect systemWindowInsets) {
        return IMPL.replaceSystemWindowInsets(this.mInsets, systemWindowInsets);
    }

    public int getStableInsetTop() {
        return IMPL.getStableInsetTop(this.mInsets);
    }

    public int getStableInsetLeft() {
        return IMPL.getStableInsetLeft(this.mInsets);
    }

    public int getStableInsetRight() {
        return IMPL.getStableInsetRight(this.mInsets);
    }

    public int getStableInsetBottom() {
        return IMPL.getStableInsetBottom(this.mInsets);
    }

    public boolean hasStableInsets() {
        return IMPL.hasStableInsets(this.mInsets);
    }

    public WindowInsetsCompat consumeStableInsets() {
        return IMPL.consumeStableInsets(this.mInsets);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        WindowInsetsCompat other = (WindowInsetsCompat) o;
        return this.mInsets == null ? false : other.mInsets == null ? true : this.mInsets.equals(other.mInsets);
    }

    public int hashCode() {
        return this.mInsets == null ? 0 : this.mInsets.hashCode();
    }

    static WindowInsetsCompat wrap(Object insets) {
        return insets == null ? null : new WindowInsetsCompat(insets);
    }

    static Object unwrap(WindowInsetsCompat insets) {
        return insets == null ? null : insets.mInsets;
    }
}
