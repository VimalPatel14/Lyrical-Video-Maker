package com.photovideoeditormaker.lyricalvideostatusmaker.model;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PVEMLyricalStatusMaker__BmpComp {
    public static int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            i3 /= 2;
            i4 /= 2;
            while (i3 / i5 > i2 && i4 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int i, int i2, int i3) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, i, options);
    }

    public static Bitmap decodeFile(File file, int i, int i2) {
        try {
            Options options = new Options();
            int i3 = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            while ((options.outWidth / i3) / 2 >= i2 && (options.outHeight / i3) / 2 >= i) {
                i3 *= 2;
            }
            Options options2 = new Options();
            options2.inSampleSize = i3;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, options2);
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    public static void decodeAdjustFile(File file, int i, int i2) {
        try {
            Options options = new Options();
            int i3 = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            while ((options.outWidth / i3) / 2 >= i && (options.outHeight / i3) / 2 >= i2) {
                i3 *= 2;
            }
            Options options2 = new Options();
            options2.inSampleSize = calculateInSampleSize(options, i, i2);
            PVEMLyricalStatusMaker_Utls2.bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options2);
        } catch (FileNotFoundException unused) {
        }
    }

    public static Bitmap adjustImageOrientation(File file, Bitmap bitmap) {
        try {
            int attributeInt = new ExifInterface(file.getAbsolutePath()).getAttributeInt("Orientation", 1);
            int i = 0;
            if (attributeInt == 3) {
                i = 180;
            } else if (attributeInt == 6) {
                i = 90;
            } else if (attributeInt == 8) {
                i = 270;
            }
            if (i != 0) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                Matrix matrix = new Matrix();
                matrix.preRotate((float) i);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
            }
            return bitmap.copy(Config.ARGB_8888, true);
        } catch (IOException unused) {
            return null;
        }
    }

    public static Bitmap adjustImageOrientationUri(Context context, Uri uri, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        Cursor query = context.getContentResolver().query(uri, new String[]{"orientation"}, null, null, null);
        if (query.getCount() == 1) {
            query.moveToFirst();
            matrix.preRotate((float) query.getInt(0));
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false).copy(Config.ARGB_8888, true);
    }

    public static void adjustImageOrientationUri(Context context, Uri uri) {
        Matrix matrix = new Matrix();
        Cursor query = context.getContentResolver().query(uri, new String[]{"orientation"}, null, null, null);
        if (query.getCount() == 1) {
            query.moveToFirst();
            matrix.preRotate((float) query.getInt(0));
        }
        PVEMLyricalStatusMaker_Utls2.bitmap = Bitmap.createBitmap(PVEMLyricalStatusMaker_Utls2.bitmap, 0, 0, PVEMLyricalStatusMaker_Utls2.bitmap.getWidth(), PVEMLyricalStatusMaker_Utls2.bitmap.getHeight(), matrix, false);
        PVEMLyricalStatusMaker_Utls2.bitmap.copy(Config.ARGB_8888, true);
    }

    public static Matrix adjustImageOrientationMatrix(Context context, Uri uri) {
        Matrix matrix = new Matrix();
        Cursor query = context.getContentResolver().query(uri, new String[]{"orientation"}, null, null, null);
        if (query.getCount() == 1) {
            query.moveToFirst();
            matrix.preRotate((float) query.getInt(0));
        }
        return matrix;
    }

    public static Bitmap compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }

    public static Bitmap scaleCenterCrop(Bitmap bitmap, int i, int i2) {
        float f = (float) i2;
        float width = (float) bitmap.getWidth();
        float f2 = (float) i;
        float height = (float) bitmap.getHeight();
        float max = Math.max(f / width, f2 / height);
        width *= max;
        max *= height;
        f = (f - width) / 2.0f;
        f2 = (f2 - max) / 2.0f;
        RectF rectF = new RectF(f, f2, width + f, max + f2);
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, null, rectF, null);
        return createBitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), new RectF(0.0f, 0.0f, (float) PVEMLyricalStatusMaker_Utls2.width, (float) PVEMLyricalStatusMaker_Utls2.width), ScaleToFit.FILL);
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return Bitmap.createScaledBitmap(bitmap, (int) (((float) bitmap.getWidth()) * fArr[0]), (int) (((float) bitmap.getHeight()) * fArr[4]), true);
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2) {
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), new RectF(0.0f, 0.0f, (float) i, (float) i2), ScaleToFit.CENTER);
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return Bitmap.createScaledBitmap(bitmap, (int) (((float) bitmap.getWidth()) * fArr[0]), (int) (((float) bitmap.getHeight()) * fArr[4]), true);
    }
}
