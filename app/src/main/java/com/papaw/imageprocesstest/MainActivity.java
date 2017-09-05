package com.papaw.imageprocesstest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.papaw.imageprocesstest.BitmapUtil.FLIP_HORIZONTAL;
import static com.papaw.imageprocesstest.BitmapUtil.FLIP_VERTICAL;
import static com.papaw.imageprocesstest.BitmapUtil.applyBlackFilter;
import static com.papaw.imageprocesstest.BitmapUtil.applyFleaEffect;
import static com.papaw.imageprocesstest.BitmapUtil.applyGaussianBlur;
import static com.papaw.imageprocesstest.BitmapUtil.applyHueFilter;
import static com.papaw.imageprocesstest.BitmapUtil.applyMeanRemoval;
import static com.papaw.imageprocesstest.BitmapUtil.applyReflection;
import static com.papaw.imageprocesstest.BitmapUtil.applySaturationFilter;
import static com.papaw.imageprocesstest.BitmapUtil.applyShadingFilter;
import static com.papaw.imageprocesstest.BitmapUtil.applySnowEffect;
import static com.papaw.imageprocesstest.BitmapUtil.boost;
import static com.papaw.imageprocesstest.BitmapUtil.createContrast;
import static com.papaw.imageprocesstest.BitmapUtil.createSepiaToningEffect;
import static com.papaw.imageprocesstest.BitmapUtil.decreaseColorDepth;
import static com.papaw.imageprocesstest.BitmapUtil.doBrightness;
import static com.papaw.imageprocesstest.BitmapUtil.doColorFilter;
import static com.papaw.imageprocesstest.BitmapUtil.doGamma;
import static com.papaw.imageprocesstest.BitmapUtil.doGreyscale;
import static com.papaw.imageprocesstest.BitmapUtil.doHighlightImage;
import static com.papaw.imageprocesstest.BitmapUtil.doInvert;
import static com.papaw.imageprocesstest.BitmapUtil.emboss;
import static com.papaw.imageprocesstest.BitmapUtil.engrave;
import static com.papaw.imageprocesstest.BitmapUtil.flip;
import static com.papaw.imageprocesstest.BitmapUtil.mark;
import static com.papaw.imageprocesstest.BitmapUtil.replaceColor;
import static com.papaw.imageprocesstest.BitmapUtil.rotate;
import static com.papaw.imageprocesstest.BitmapUtil.roundCorner;
import static com.papaw.imageprocesstest.BitmapUtil.sharpen;
import static com.papaw.imageprocesstest.BitmapUtil.smooth;
import static com.papaw.imageprocesstest.BitmapUtil.tintImage;

public class MainActivity extends AppCompatActivity {
    static final String TAG = MainActivity.class.getSimpleName();
    ImageView origin;
    LinearLayout gallery;
    TextView info;
    Bitmap originBM;
    MyHorizontalScrollView myScroll;
    int itemW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        myScroll = (MyHorizontalScrollView) findViewById(R.id.myScroll);
        info = (TextView) findViewById(R.id.info);
        origin = (ImageView) findViewById(R.id.origin);
        gallery = (LinearLayout) findViewById(R.id.gallery);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        itemW = point.y / 3;
        LinearLayout.LayoutParams originParams = (LinearLayout.LayoutParams) origin.getLayoutParams();
        originParams.width = itemW;
        originParams.height = itemW;
        origin.setLayoutParams(originParams);
        originBM = BitmapFactory.decodeResource(getResources(), R.drawable.albert_einstein);
        info.setText(originBM.getWidth() + "x" + originBM.getHeight());
        origin.setImageBitmap(originBM);

        initItems();
        gallery.postDelayed(new Runnable() {
            @Override
            public void run() {
                myScroll.refresh();
            }
        }, 500);
    }

    private void initItems() {
        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doHighlightImage(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doInvert(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doGreyscale(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doGamma(bm, 0.6, 0.6, 0.6);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doGamma(bm, 1.8, 1.8, 1.8);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doColorFilter(bm, 1, 0, 0);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doColorFilter(bm, 0, 1, 0);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doColorFilter(bm, 0, 0, 1);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doColorFilter(bm, 0.5, 0.5, 0.5);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doColorFilter(bm, 1.5, 1.5, 1.5);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return createSepiaToningEffect(bm, 100, 1, 0, 0);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return createSepiaToningEffect(bm, 100, 0, 1, 0);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return createSepiaToningEffect(bm, 100, 0, 0, 1);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return createSepiaToningEffect(bm, 100, 0.5, 0.4, 0);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return decreaseColorDepth(bm, 32);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return decreaseColorDepth(bm, 64);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return decreaseColorDepth(bm, 128);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return createContrast(bm, 50);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return createContrast(bm, 100);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return rotate(bm, 90);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return rotate(bm, 135);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doBrightness(bm, 60);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return doBrightness(bm, -60);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyGaussianBlur(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return sharpen(bm, 10);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyMeanRemoval(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return smooth(bm, 5);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return emboss(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return engrave(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return boost(bm, 1, 0.5f);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return boost(bm, 2, -0.5f);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return boost(bm, 3, -0.33f);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return roundCorner(bm, 400);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return mark(bm, "Hello Einstein", new Point(900, 1500), Color.WHITE, (int) (255 * 0.7f), 100, true);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return flip(bm, FLIP_VERTICAL);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return flip(bm, FLIP_HORIZONTAL);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return replaceColor(bm, 0x00000000, 0xFFFFFFFF);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return tintImage(bm, 45);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return tintImage(bm, 90);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return tintImage(bm, 180);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyFleaEffect(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyBlackFilter(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applySnowEffect(bm);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyShadingFilter(bm, Color.parseColor("#87ceeb"));
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyShadingFilter(bm, Color.parseColor("#7cfc00"));
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyShadingFilter(bm, Color.parseColor("#ee82ee"));
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applySaturationFilter(bm, 5);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyHueFilter(bm, 3);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyHueFilter(bm, 9);
            }
        });

        addItem(originBM, new GalleryItem.BitMapProcessor() {
            @Override
            public Bitmap process(Bitmap bm) {
                return applyReflection(bm);
            }
        });
    }

    private void addItem(Bitmap bitmap, GalleryItem.BitMapProcessor processor) {
        GalleryItem item = new GalleryItem(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemW, itemW);
        item.setLayoutParams(params);
        gallery.addView(item);
        item.setBitMap(bitmap, processor);
    }
}
