package com.papaw.imageprocesstest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

/**
 * Created by duanjunjie on 17-9-4.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {
    public static final String TAG = MyHorizontalScrollView.class.getSimpleName();
    private int windowW;
    private int scrollLeft = 0;

    public MyHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Point point = new Point();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getSize(point);
        windowW = point.x;
        Log.d(TAG, String.format("windowW %d", windowW));
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollLeft = l;
        refresh();
    }

    public void refresh() {
        ViewGroup root = (ViewGroup) getChildAt(0);

        int childSize = root.getChildCount();
        int childWidth = root.getMeasuredWidth() / childSize;

        int leftIndex = scrollLeft / childWidth;
        int rightIndex = (scrollLeft + windowW) / childWidth + 1;

        leftIndex = leftIndex - 2;
        rightIndex = rightIndex + 2;

        if (leftIndex < 0) {
            leftIndex = 0;
        }

        if (rightIndex > childSize - 1) {
            rightIndex = childSize - 1;
        }

        for (int i = 0; i < childSize; i++) {
            GalleryItem child = (GalleryItem) root.getChildAt(i);
            if (i < leftIndex || i > rightIndex) {
                child.recycle();
            } else {
                child.loadBitMap();
            }
        }
    }
}
