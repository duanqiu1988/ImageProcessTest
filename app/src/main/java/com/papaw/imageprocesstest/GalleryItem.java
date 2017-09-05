package com.papaw.imageprocesstest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by duanjunjie on 17-9-4.
 */

public class GalleryItem extends FrameLayout {
    private ImageView imageView;
    private ProgressBar progress;
    private Bitmap origin;
    private Bitmap processedBm;
    private BitMapProcessor processor;
    private boolean recycled = true;
    private boolean loading = false;
    private LoadBmTask task;

    public GalleryItem(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        imageView = new ImageView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.rightMargin = 16;
        imageView.setLayoutParams(params);
        addView(imageView);
        progress = new ProgressBar(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        progress.setLayoutParams(params);
        addView(progress);
    }

    public void setBitMap(Bitmap bitMap, final BitMapProcessor processor) {
        origin = bitMap;
        this.processor = processor;
    }

    private class LoadBmTask extends AsyncTask<Bitmap, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            progress.setVisibility(VISIBLE);
            imageView.setVisibility(INVISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            return processor.process(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            loading = false;
            processedBm = bitmap;
            imageView.setImageBitmap(processedBm);
            progress.setVisibility(GONE);
            imageView.setVisibility(VISIBLE);
        }
    }

    protected synchronized void loadBitMap() {
        if (recycled && !loading) {
            task = new LoadBmTask();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, origin);
            recycled = false;
            loading = true;
        }
    }

    protected synchronized void recycle() {
        if (!recycled) {
            if (loading) {
                task.cancel(true);
                loading = false;
            }
            imageView.setImageBitmap(null);
            if (processedBm != null && !processedBm.isRecycled()) {
                processedBm.recycle();
                processedBm = null;
            }
            recycled = true;
        }
    }

    interface BitMapProcessor {
        Bitmap process(Bitmap bm);
    }
}
