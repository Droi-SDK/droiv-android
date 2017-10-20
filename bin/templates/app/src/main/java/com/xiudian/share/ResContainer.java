package com.xiudian.share;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public final class ResContainer {
    private static ResContainer R = null;
    private Context context = null;
    private Map<String, SocializeResource> mResources;

    private ResContainer(Context paramContext) {
        this.context = paramContext.getApplicationContext();
    }

    public static synchronized ResContainer get(Context paramContext) {
        if (R == null) {
            R = new ResContainer(paramContext);
        }
        return R;
    }

    public int layout(String paramString) {
        return getResourceId(this.context, "layout", paramString);
    }

    public int id(String paramString) {
        return getResourceId(this.context, "id", paramString);
    }

    public int drawable(String paramString) {
        return getResourceId(this.context, "drawable", paramString);
    }

    public int style(String paramString) {
        return getResourceId(this.context, "style", paramString);
    }

    public int string(String paramString) {
        return getResourceId(this.context, "string", paramString);
    }

    public int color(String paramString) {
        return getResourceId(this.context, "color", paramString);
    }

    public int dimen(String paramString) {
        return getResourceId(this.context, "dimen", paramString);
    }

    public int raw(String paramString) {
        return getResourceId(this.context, "raw", paramString);
    }

    public int anim(String paramString) {
        return getResourceId(this.context, "anim", paramString);
    }

    public int styleable(String paramString) {
        return getResourceId(this.context, "styleable", paramString);
    }

    private static String mPackageName = "";

    public ResContainer(Context paramContext, Map<String, SocializeResource> paramMap) {
        this.mResources = paramMap;
        this.context = paramContext;
    }

    public static int getResourceId(Context paramContext, String paramString1, String paramString2) {
        Resources localResources = paramContext.getResources();
        if (TextUtils.isEmpty(mPackageName)) {
            mPackageName = paramContext.getPackageName();
        }
        int i = localResources.getIdentifier(paramString2, paramString1, mPackageName);
        if (i <= 0) {
            //throw new RuntimeException(UmengText.errorWithUrl(UmengText.resError(mPackageName, paramString1, paramString2), "https://at.umeng.com/KzKfWz?cid=476"));
        }
        return i;
    }

    public static String getString(Context paramContext, String paramString) {
        int i = getResourceId(paramContext, "string", paramString);
        return paramContext.getString(i);
    }

    public synchronized Map<String, SocializeResource> batch() {
        if (this.mResources == null) {
            return this.mResources;
        }
        Set<String> localSet = this.mResources.keySet();
        for (String str : localSet) {
            SocializeResource localSocializeResource = (SocializeResource) this.mResources.get(str);
            localSocializeResource.mId = getResourceId(this.context, localSocializeResource.mType, localSocializeResource.mName);
            localSocializeResource.mIsCompleted = true;
        }
        return this.mResources;
    }

    public static class SocializeResource {
        public String mType;
        public String mName;
        public boolean mIsCompleted = false;
        public int mId;

        public SocializeResource(String paramString1, String paramString2) {
            this.mType = paramString1;
            this.mName = paramString2;
        }
    }

    public static int[] getStyleableArrts(Context paramContext, String paramString) {
        return getResourceDeclareStyleableIntArray(paramContext, paramString);
    }

    private static final int[] getResourceDeclareStyleableIntArray(Context paramContext, String paramString) {
        try {
            Field[] arrayOfField1 = Class.forName(paramContext.getPackageName() + ".R$styleable").getFields();
            for (Field localField : arrayOfField1) {
                if (localField.getName().equals(paramString)) {
                    return (int[]) localField.get(null);
                }
            }
        } catch (Throwable localThrowable) {
            localThrowable.printStackTrace();
        }
        return null;
    }
}
