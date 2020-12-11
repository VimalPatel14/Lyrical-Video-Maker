package com.photovideoeditormaker.lyricalvideostatusmaker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import java.io.IOException;

public class PVEMLyricalStatusMaker_ImgScaUtilities {

    public enum ScalingLogic {
        CROP,
        FIT
    }

    public static Bitmap decodeResource(Resources resources, int i, int i2, int i3, ScalingLogic scalingLogic) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, i2, i3, scalingLogic);
        return BitmapFactory.decodeResource(resources, i, options);
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int i, int i2, ScalingLogic scalingLogic) {
        Rect calculateSrcRect = calculateSrcRect(bitmap.getWidth(), bitmap.getHeight(), i, i2, scalingLogic);
        Rect calculateDstRect = calculateDstRect(bitmap.getWidth(), bitmap.getHeight(), i, i2, scalingLogic);
        Bitmap createBitmap = Bitmap.createBitmap(calculateDstRect.width(), calculateDstRect.height(), Config.ARGB_8888);
        new Canvas(createBitmap).drawBitmap(bitmap, calculateSrcRect, calculateDstRect, new Paint(2));
        return createBitmap;
    }

    public static int calculateSampleSize(int i, int i2, int i3, int i4, ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.FIT) {
            if (((float) i) / ((float) i2) > ((float) i3) / ((float) i4)) {
                return i / i3;
            }
            return i2 / i4;
        } else if (((float) i) / ((float) i2) > ((float) i3) / ((float) i4)) {
            return i2 / i4;
        } else {
            return i / i3;
        }
    }

    public static Rect calculateSrcRect(int i, int i2, int i3, int i4, ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingLogic.CROP) {
            return new Rect(0, 0, i, i2);
        }
        float f = ((float) i3) / ((float) i4);
        float f2 = (float) i;
        float f3 = (float) i2;
        if (f2 / f3 > f) {
            i3 = (int) (f3 * f);
            i = (i - i3) / 2;
            return new Rect(i, 0, i3 + i, i2);
        }
        i3 = (int) (f2 / f);
        i2 = (i2 - i3) / 2;
        return new Rect(0, i2, i, i3 + i2);
    }

    public static Rect calculateDstRect(int i, int i2, int i3, int i4, ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingLogic.FIT) {
            return new Rect(0, 0, i3, i4);
        }
        float f = ((float) i) / ((float) i2);
        float f2 = (float) i3;
        float f3 = (float) i4;
        if (f > f2 / f3) {
            return new Rect(0, 0, i3, (int) (f2 / f));
        }
        return new Rect(0, 0, (int) (f3 * f), i4);
    }

    public static Bitmap ConvetrSameSize(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, i, i2);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRect(rect, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(newscaleBitmap(bitmap, i, i2), 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap ConvetrSameSizeTransBg(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, i, i2);
        canvas.drawBitmap(newscaleBitmap(bitmap, i, i2), 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap ConvetrSameSize(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, i, i2);
        canvas.drawBitmap(newscaleBitmap(bitmap, i, i2), 0.0f, 0.0f, paint);
        return bitmap2;
    }

    public static Bitmap ConvetrSameSize(Bitmap bitmap, Bitmap bitmap2, int i, int i2, float f, float f2) {
        bitmap2 = bitmap2.copy(bitmap2.getConfig(), true);
        Canvas canvas = new Canvas(PVEMLyricalStatusMaker_FastBlr.doBlur(bitmap2, 25, true));
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, i, i2);
        canvas.drawBitmap(newscaleBitmap(bitmap, i, i2, f, f2), 0.0f, 0.0f, paint);
        paint.setColor(-1996488705);
        paint.setStrokeWidth(120.0f);
        paint.setTextSize(120.0f);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
        return bitmap2;
    }

    private static Bitmap newscaleBitmapnewww(Bitmap bitmap, int i, int i2, float f, float f2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        Canvas canvas = new Canvas(createBitmap);
        float f3 = (float) i;
        float f4 = f3 / width;
        float f5 = (float) i2;
        float f6 = f5 / height;
        f5 = (f5 - (height * f4)) / 2.0f;
        if (f5 < 0.0f) {
            f3 = (f3 - (width * f6)) / 2.0f;
            f4 = f6;
            f5 = 0.0f;
        } else {
            f3 = 0.0f;
        }
        Matrix matrix = new Matrix();
        matrix.postTranslate(f * f3, f2 + f5);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("xTranslation :");
        stringBuilder.append(f3);
        stringBuilder.append(" yTranslation :");
        stringBuilder.append(f5);
        Log.d("translation", stringBuilder.toString());
        matrix.preScale(f4, f4);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, matrix, paint);
        return createBitmap;
    }

    private static Bitmap newscaleBitmap(Bitmap bitmap, int i, int i2, float f, float f2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        Canvas canvas = new Canvas(createBitmap);
        float f3 = (float) i;
        float f4 = f3 / width;
        float f5 = (float) i2;
        float f6 = f5 / height;
        f5 = (f5 - (height * f4)) / 2.0f;
        float f7 = 0.0f;
        if (f5 < 0.0f) {
            f5 = (f3 - (width * f6)) / 2.0f;
            f4 = f6;
        } else {
            f7 = f5;
            f5 = 0.0f;
        }
        if (width == height) {
            Log.v("ifcondition", "ifcondition");
            if (width > f3) {
                i -= 100;
                bitmap = Bitmap.createScaledBitmap(bitmap, i, i, false);
                width = (float) i;
            }
            i = ((int) (f3 - width)) / 2;
            Matrix matrix = new Matrix();
            matrix.postTranslate(2.0f, 2.0f);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("xTranslation :");
            stringBuilder.append(f5);
            stringBuilder.append(" yTranslation :");
            stringBuilder.append(f7);
            Log.d("translation", stringBuilder.toString());
            matrix.preScale(f4, f4);
            float f8 = (float) i;
            canvas.drawBitmap(bitmap, f8, f8, null);
            return createBitmap;
        }
        Matrix matrix2 = new Matrix();
        matrix2.postTranslate(f * f5, f2 + f7);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("xTranslation :");
        stringBuilder2.append(f5);
        stringBuilder2.append(" yTranslation :");
        stringBuilder2.append(f7);
        Log.d("translation", stringBuilder2.toString());
        matrix2.preScale(f4, f4);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, matrix2, paint);
        return createBitmap;
    }

    public static Bitmap addShadow(Bitmap bitmap, int i, int i2, float f, float f2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() + (i2 * 2), bitmap.getHeight(), Config.ALPHA_8);
        Matrix matrix = new Matrix();
        float f3 = (float) i2;
        matrix.setRectToRect(new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), new RectF(0.0f, 0.0f, (((float) bitmap.getWidth()) - f) - f3, ((float) bitmap.getHeight()) - f2), ScaleToFit.CENTER);
        Matrix matrix2 = new Matrix(matrix);
        matrix2.postTranslate(f, f2);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        canvas.drawBitmap(bitmap, matrix, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
        canvas.drawBitmap(bitmap, matrix2, paint);
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(f3, Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setMaskFilter(blurMaskFilter);
        paint.setFilterBitmap(true);
        Bitmap createBitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        canvas2.drawBitmap(createBitmap, 0.0f, 0.0f, paint);
        canvas2.drawBitmap(bitmap, matrix, null);
        createBitmap.recycle();
        return createBitmap2;
    }

    public static Bitmap scaleCenterCrop(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == i && height == i2) {
            return bitmap;
        }
        float f = (float) i;
        float f2 = (float) width;
        float f3 = (float) i2;
        float f4 = (float) height;
        float max = Math.max(f / f2, f3 / f4);
        f2 *= max;
        max *= f4;
        f = (f - f2) / 2.0f;
        f3 = (f3 - max) / 2.0f;
        RectF rectF = new RectF(f, f3, f2 + f, max + f3);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, null, rectF, null);
        return createBitmap;
    }

    @SuppressLint({"NewApi"})
    public static Bitmap blurBitmap(Bitmap bitmap, Context context) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        RenderScript create = RenderScript.create(context);
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
        create2.setRadius(25.0f);
        create2.setInput(createFromBitmap);
        create2.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        bitmap.recycle();
        create.destroy();
        return createBitmap;
    }

    public static Bitmap overlay(Bitmap bitmap, Bitmap bitmap2, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        Paint paint = new Paint();
        paint.setAlpha(i);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    private static Bitmap newscaleBitmap(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        Canvas canvas = new Canvas(createBitmap);
        float f = (float) i;
        float f2 = f / width;
        float f3 = (float) i2;
        float f4 = (f3 - (height * f2)) / 2.0f;
        if (f4 < 0.0f) {
            f2 = f3 / height;
            f = (f - (width * f2)) / 2.0f;
            f4 = 0.0f;
        } else {
            f = 0.0f;
        }
        Matrix matrix = new Matrix();
        matrix.postTranslate(f, f4);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("xTranslation :");
        stringBuilder.append(f);
        stringBuilder.append(" yTranslation :");
        stringBuilder.append(f4);
        Log.d("translation", stringBuilder.toString());
        matrix.preScale(f2, f2);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, matrix, paint);
        return createBitmap;
    }

    public static Bitmap checkBitmap(String path) {
        int orientation = 1;
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        Bitmap bm = BitmapFactory.decodeFile(path, new BitmapFactory.Options());

        String orientString = path;
        try {
            orientString = new ExifInterface(path).getAttribute("Orientation");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (orientString != null) {
            orientation = Integer.parseInt(orientString);
        }
        int rotationAngle = 0;
        if (orientation == 6) {
            rotationAngle = 90;
        }
        if (orientation == 3) {
            rotationAngle = 180;
        }
        if (orientation == 8) {
            try {
                rotationAngle = 270;
            }catch (OutOfMemoryError e){
                rotationAngle = 270;
            }

        }
        Matrix matrix = new Matrix();
        matrix.setRotate((float) rotationAngle, ((float) bm.getWidth()) / 2.0f, ((float) bm.getHeight()) / 2.0f);
        return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
    }
}
