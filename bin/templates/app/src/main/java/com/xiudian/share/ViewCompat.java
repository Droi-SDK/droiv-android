package com.xiudian.share;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

class ViewCompat {

    private static final long FAKE_FRAME_TIME = 10L;
    private static final ViewCompatImpl IMPL;

    static void postInvalidateOnAnimation(View view) {
        IMPL.postInvalidateOnAnimation(view);
    }

    static void postOnAnimation(View view, Runnable action) {
        IMPL.postOnAnimation(view, action);
    }

    static boolean canScrollHorizontally(View v, int direction) {
        return IMPL.canScrollHorizontally(v, direction);
    }

    public static void setOnApplyWindowInsetsListener(View v, OnApplyWindowInsetsListener listener) {
        IMPL.setOnApplyWindowInsetsListener(v, listener);
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
        return IMPL.onApplyWindowInsets(view, insets);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat insets) {
        return IMPL.dispatchApplyWindowInsets(view, insets);
    }

    static {
        int version = Build.VERSION.SDK_INT;
        if (version >= 24) {
            IMPL = new Api24ViewCompatImpl();
        } else if (version >= 23) {
            IMPL = new MarshmallowViewCompatImpl();
        } else if (version >= 21) {
            IMPL = new LollipopViewCompatImpl();
        } else if (version >= 19) {
            IMPL = new KitKatViewCompatImpl();
        } else if (version >= 18) {
            IMPL = new JbMr2ViewCompatImpl();
        } else if (version >= 17) {
            IMPL = new JbMr1ViewCompatImpl();
        } else if (version >= 16) {
            IMPL = new JBViewCompatImpl();
        } else if (version >= 15) {
            IMPL = new ICSMr1ViewCompatImpl();
        } else if (version >= 14) {
            IMPL = new ICSViewCompatImpl();
        } else if (version >= 11) {
            IMPL = new HCViewCompatImpl();
        } else {
            IMPL = new BaseViewCompatImpl();
        }
    }

    interface OnApplyWindowInsetsListener {
        WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat);
    }

    interface OnApplyWindowInsetsListenerBridge {
        Object onApplyWindowInsets(View paramView, Object paramObject);
    }

    interface ViewCompatImpl {
        boolean canScrollHorizontally(View paramView, int paramInt);

        void postInvalidateOnAnimation(View paramView);

        void postOnAnimation(View paramView, Runnable paramRunnable);

        void setOnApplyWindowInsetsListener(View paramView, ViewCompat.OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener);

        WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat);

        WindowInsetsCompat dispatchApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat);
    }

    static class BaseViewCompatImpl
            implements ViewCompat.ViewCompatImpl {
        public boolean canScrollHorizontally(View v, int direction) {
            return false;
        }

        public void postInvalidateOnAnimation(View view) {
            view.invalidate();
        }

        public void postOnAnimation(View view, Runnable action) {
            view.postDelayed(action, getFrameTime());
        }

        public void setOnApplyWindowInsetsListener(View view, ViewCompat.OnApplyWindowInsetsListener listener) {
        }

        long getFrameTime() {
            return 10L;
        }

        public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
            return insets;
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View v, WindowInsetsCompat insets) {
            return insets;
        }
    }

    @TargetApi(11)
    static class HCViewCompatImpl
            extends ViewCompat.BaseViewCompatImpl {
        long getFrameTime() {
            return ValueAnimator.getFrameDelay();
        }
    }

    @TargetApi(14)
    static class ICSViewCompatImpl
            extends ViewCompat.HCViewCompatImpl {
        public boolean canScrollHorizontally(View v, int direction) {
            return v.canScrollHorizontally(direction);
        }
    }

    static class ICSMr1ViewCompatImpl
            extends ViewCompat.ICSViewCompatImpl {
    }

    @TargetApi(16)
    static class JBViewCompatImpl
            extends ViewCompat.ICSMr1ViewCompatImpl {
        public void postInvalidateOnAnimation(View view) {
            view.postInvalidateOnAnimation();
        }

        public void postOnAnimation(View view, Runnable action) {
            view.postOnAnimation(action);
        }
    }

    private static class JbMr1ViewCompatImpl
            extends ViewCompat.JBViewCompatImpl {
    }

    private static class JbMr2ViewCompatImpl
            extends ViewCompat.JbMr1ViewCompatImpl {
        private JbMr2ViewCompatImpl() {
            super();
        }
    }

    private static class KitKatViewCompatImpl
            extends ViewCompat.JbMr2ViewCompatImpl {
        private KitKatViewCompatImpl() {
            super();
        }
    }

    @TargetApi(20)
    private static class LollipopViewCompatImpl
            extends ViewCompat.KitKatViewCompatImpl {
        private LollipopViewCompatImpl() {
            super();
        }

        public void setOnApplyWindowInsetsListener(View view, final ViewCompat.OnApplyWindowInsetsListener listener) {
            if (listener == null) {
                view.setOnApplyWindowInsetsListener(null);
                return;
            }
            final ViewCompat.OnApplyWindowInsetsListenerBridge bridge = new ViewCompat.OnApplyWindowInsetsListenerBridge() {
                public Object onApplyWindowInsets(View v, Object insets) {
                    WindowInsetsCompat compatInsets = WindowInsetsCompat.wrap(insets);
                    compatInsets = listener.onApplyWindowInsets(v, compatInsets);
                    return WindowInsetsCompat.unwrap(compatInsets);
                }
            };
            view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
                    return (WindowInsets) bridge.onApplyWindowInsets(view, insets);
                }
            });
        }

        public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
            return WindowInsetsCompat.wrap(
                    onApplyWindowInsets(v, WindowInsetsCompat.unwrap(insets)));
        }

        private static Object onApplyWindowInsets(View v, Object insets) {
            WindowInsets unwrapped = (WindowInsets) insets;
            WindowInsets result = v.onApplyWindowInsets(unwrapped);
            if (result != unwrapped) {
                insets = new WindowInsets(result);
            }
            return insets;
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View v, WindowInsetsCompat insets) {
            return WindowInsetsCompat.wrap(dispatchApplyWindowInsets(v, WindowInsetsCompat.unwrap(insets)));
        }

        private static Object dispatchApplyWindowInsets(View v, Object insets) {
            WindowInsets unwrapped = (WindowInsets) insets;
            WindowInsets result = v.dispatchApplyWindowInsets(unwrapped);
            if (result != unwrapped) {
                insets = new WindowInsets(result);
            }
            return insets;
        }
    }

    private static class MarshmallowViewCompatImpl
            extends ViewCompat.LollipopViewCompatImpl {
        private MarshmallowViewCompatImpl() {
            super();
        }
    }

    private static class Api24ViewCompatImpl
            extends ViewCompat.MarshmallowViewCompatImpl {
        private Api24ViewCompatImpl() {
            super();
        }
    }
}
