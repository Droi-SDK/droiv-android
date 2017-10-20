package com.xiudian.share;

import android.os.Build;
import android.view.VelocityTracker;

class VelocityTrackerCompat {

    private static final VelocityTrackerVersionImpl IMPL;

    interface VelocityTrackerVersionImpl {
        float getXVelocity(VelocityTracker paramVelocityTracker, int paramInt);
    }

    private static class BaseVelocityTrackerVersionImpl
            implements VelocityTrackerCompat.VelocityTrackerVersionImpl {
        public float getXVelocity(VelocityTracker tracker, int pointerId) {
            return tracker.getXVelocity();
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombVelocityTrackerVersionImpl();
        } else {
            IMPL = new BaseVelocityTrackerVersionImpl();
        }
    }

    private static class HoneycombVelocityTrackerVersionImpl
            implements VelocityTrackerCompat.VelocityTrackerVersionImpl {
        public float getXVelocity(VelocityTracker tracker, int pointerId) {
            return tracker.getXVelocity(pointerId);
        }
    }

    static float getXVelocity(VelocityTracker tracker, int pointerId) {
        return IMPL.getXVelocity(tracker, pointerId);
    }
}

