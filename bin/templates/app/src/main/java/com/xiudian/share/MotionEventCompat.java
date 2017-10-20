package com.xiudian.share;

import android.view.MotionEvent;

class MotionEventCompat {
    static final int ACTION_MASK = 255;
    static final int ACTION_POINTER_INDEX_MASK = 65280;
    static final int ACTION_POINTER_INDEX_SHIFT = 8;
    static final int ACTION_POINTER_DOWN = 5;
    static final int ACTION_POINTER_UP = 6;

    static int getActionIndex(MotionEvent event) {
        return (event.getAction() & 0xFF00) >> 8;
    }
}

