package com.xiudian.share;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

public abstract class SocializePagerAdapter {

    private final DataSetObservable mObservable = new DataSetObservable();
    private DataSetObserver mViewPagerObserver;
    public static final int POSITION_UNCHANGED = -1;
    public static final int POSITION_NONE = -2;

    public abstract int getCount();

    public void startUpdate(ViewGroup container) {
        startUpdate(container);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        return instantiateItem(container, position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        destroyItem(container, position, object);
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        setPrimaryItem(container, position, object);
    }

    public void finishUpdate(ViewGroup container) {
        finishUpdate(container);
    }

    @Deprecated
    public void startUpdate(View container) {
    }

    @Deprecated
    public Object instantiateItem(View container, int position) {
        throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
    }

    @Deprecated
    public void destroyItem(View container, int position, Object object) {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    @Deprecated
    public void setPrimaryItem(View container, int position, Object object) {
    }

    @Deprecated
    public void finishUpdate(View container) {
    }

    public abstract boolean isViewFromObject(View paramView, Object paramObject);

    public Parcelable saveState() {
        return null;
    }

    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    public int getItemPosition(Object object) {
        return -1;
    }

    public void notifyDataSetChanged() {
        synchronized (this) {
            if (this.mViewPagerObserver != null) {
                this.mViewPagerObserver.onChanged();
            }
        }
        this.mObservable.notifyChanged();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        this.mObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.mObservable.unregisterObserver(observer);
    }

    void setViewPagerObserver(DataSetObserver observer) {
        synchronized (this) {
            this.mViewPagerObserver = observer;
        }
    }

    public CharSequence getPageTitle(int position) {
        return null;
    }

    public float getPageWidth(int position) {
        return 1.0F;
    }
}

