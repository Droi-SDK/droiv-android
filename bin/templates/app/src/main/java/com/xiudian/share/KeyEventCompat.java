package com.xiudian.share;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.KeyEvent;

class KeyEventCompat {
    private static final KeyEventVersionImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombKeyEventVersionImpl();
        } else {
            IMPL = new BaseKeyEventVersionImpl();
        }
    }

    public static boolean hasModifiers(KeyEvent event, int modifiers) {
        return IMPL.metaStateHasModifiers(event.getMetaState(), modifiers);
    }

    public static boolean hasNoModifiers(KeyEvent event) {
        return IMPL.metaStateHasNoModifiers(event.getMetaState());
    }

    interface KeyEventVersionImpl {
        boolean metaStateHasModifiers(int paramInt1, int paramInt2);

        boolean metaStateHasNoModifiers(int paramInt);
    }

    private static class BaseKeyEventVersionImpl implements KeyEventCompat.KeyEventVersionImpl {
        private static final int META_MODIFIER_MASK = 247;
        private static final int META_ALL_MASK = 247;

        private static int metaStateFilterDirectionalModifiers(int metaState, int modifiers,
                                                               int basic, int left, int right) {
            boolean wantBasic = (modifiers & basic) != 0;
            int directional = left | right;
            boolean wantLeftOrRight = (modifiers & directional) != 0;
            if (wantBasic) {
                if (wantLeftOrRight) {
                    throw new IllegalArgumentException("bad arguments");
                }
                return metaState & (~directional);
            }
            if (wantLeftOrRight) {
                return metaState & (~basic);
            }
            return metaState;
        }

        private int normalizeMetaState(int metaState) {
            if ((metaState & 0xC0) != 0) {
                metaState |= 0x1;
            }
            if ((metaState & 0x30) != 0) {
                metaState |= 0x2;
            }
            return metaState & 0xF7;
        }

        public boolean metaStateHasModifiers(int metaState, int modifiers) {
            metaState = normalizeMetaState(metaState) & 0xF7;
            metaState = metaStateFilterDirectionalModifiers(metaState, modifiers, 1, 64, 128);
            metaState = metaStateFilterDirectionalModifiers(metaState, modifiers, 2, 16, 32);
            return metaState == modifiers;
        }

        public boolean metaStateHasNoModifiers(int metaState) {
            return (normalizeMetaState(metaState) & 0xF7) == 0;
        }
    }

    @TargetApi(11)
    private static class HoneycombKeyEventVersionImpl extends KeyEventCompat.BaseKeyEventVersionImpl {
        private HoneycombKeyEventVersionImpl() {
            super();
        }

        public boolean metaStateHasModifiers(int metaState, int modifiers) {
            return KeyEvent.metaStateHasModifiers(metaState, modifiers);
        }

        public boolean metaStateHasNoModifiers(int metaState) {
            return KeyEvent.metaStateHasNoModifiers(metaState);
        }
    }
}

