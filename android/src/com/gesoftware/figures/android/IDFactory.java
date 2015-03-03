package com.gesoftware.figures.android;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

public final class IDFactory {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();

            int newValue = result + 1;
            if (newValue > 0x00FFFFFF)
                newValue = 1;

            if (sNextGeneratedId.compareAndSet(result, newValue))
                return result;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getCorrectID() {
        return View.generateViewId();
    }

    public static int generateViewID() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            return generateViewId();

        return getCorrectID();
    }
}
