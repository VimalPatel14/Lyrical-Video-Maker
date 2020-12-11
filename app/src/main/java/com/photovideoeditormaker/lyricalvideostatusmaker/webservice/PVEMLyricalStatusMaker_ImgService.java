package com.photovideoeditormaker.lyricalvideostatusmaker.webservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.data.PVEMLyricalStatusMaker_FinalMaskBmp;
import com.photovideoeditormaker.lyricalvideostatusmaker.utill.PVEMLyricalStatusMaker_PrefMnger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PVEMLyricalStatusMaker_ImgService extends IntentService {

    public static boolean isImageComplate = false;
    public static final Object mLock = new Object();
    ArrayList<String> arrayList;
    PVEMLyricalStatusMaker_PrefMnger application;
    int totalImages;
    public static Context mcontext;
    public static String selectedTheme;
    public static final String ACTION_CREATE_NEW_THEME_IMAGES = "ACTION_CREATE_NEW_THEME_IMAGES";
    public static final String ACTION_UPDATE_THEME_IMAGES = "ACTION_UPDATE_THEME_IMAGES";

    public PVEMLyricalStatusMaker_ImgService() {
        this(PVEMLyricalStatusMaker_ImgService.class.getName());
    }

    public PVEMLyricalStatusMaker_ImgService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = PVEMLyricalStatusMaker_PrefMnger.getInstance();
        mcontext = this;
        Log.e("vimal", "onCreate" + " Yes");
    }

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        selectedTheme = intent.getStringExtra("selected_theme");

        PVEMLyricalStatusMaker_PrefMnger banglaMyApplication = this.application;
        Log.e("selectedtheme111", PVEMLyricalStatusMaker_PrefMnger.selectedTheme.toString());
        arrayList = application.selectedImages;

        // Log.i("MovieappsImageCreatorService", "selected Thme"+selectedTheme);
//        arrayList = PVEMLyricalStatusMaker_PrefMnger.selectedImages;


        PVEMLyricalStatusMaker_PrefMnger.videoImages = new ArrayList<String>();
//        application.initArray();
        isImageComplate = false;
        createImages();

    }

    private void createImages() {

        Log.e("vimal", "createImages" + " Yes");

        Bitmap newFirstBmp;
        Bitmap localObject3;
        Bitmap localObject4;
        Bitmap localObject2;
        String folderPath = Environment.getExternalStorageDirectory()
                .getPath()
                + "/"
                + getResources()
                .getString(R.string.app_name);


        totalImages = arrayList.size();
        Log.e("totalImages", totalImages + "");
        for (int i = 0; i < arrayList.size() - 1; i++) {
            if (PVEMLyricalStatusMaker_PrefMnger.isBreak) {
                break;
            }

            //File imgDir = FileUtils.getImageDirectory("temp", i);

            File f = new File(folderPath + "/tmp");
            if (!f.exists())
                f.mkdirs();

            File imageDir = new File(f, String.format("IMG_%03d", i));
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            Log.d("image111_size", String.valueOf(imageDir));
            Bitmap firstBitmap = checkBitmap(arrayList.get(i));
            Bitmap temp = scaleCenterCrop(firstBitmap,
                    PVEMLyricalStatusMaker_PrefMnger.VIDEO_WIDTH, PVEMLyricalStatusMaker_PrefMnger.VIDEO_HEIGHT);
            newFirstBmp = ConvetrSameSizeNew(firstBitmap, temp,
                    PVEMLyricalStatusMaker_PrefMnger.VIDEO_WIDTH,
                    PVEMLyricalStatusMaker_PrefMnger.VIDEO_HEIGHT);

            temp.recycle();
            temp = null;
            firstBitmap.recycle();
            firstBitmap = null;
            System.gc();
            localObject3 = checkBitmap((arrayList.get(i + 1)));
            localObject4 = scaleCenterCrop((Bitmap) localObject3, PVEMLyricalStatusMaker_PrefMnger.VIDEO_WIDTH, PVEMLyricalStatusMaker_PrefMnger.VIDEO_HEIGHT);
            localObject2 = ConvetrSameSizeNew((Bitmap) localObject3, (Bitmap) localObject4, PVEMLyricalStatusMaker_PrefMnger.VIDEO_WIDTH, PVEMLyricalStatusMaker_PrefMnger.VIDEO_HEIGHT);
            ((Bitmap) localObject4).recycle();
            ((Bitmap) localObject3).recycle();
            System.gc();
            PVEMLyricalStatusMaker_FinalMaskBmp.EFFECT effect = PVEMLyricalStatusMaker_PrefMnger.selectedTheme.getTheme().get(i % PVEMLyricalStatusMaker_PrefMnger.selectedTheme.getTheme().size());
            Canvas canvas123;
            for (int m = 0; m < PVEMLyricalStatusMaker_FinalMaskBmp.TOTAL_FRAME; m++) {
                Bitmap bitmap123 = Bitmap.createBitmap(PVEMLyricalStatusMaker_PrefMnger.VIDEO_WIDTH, PVEMLyricalStatusMaker_PrefMnger.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Paint p1 = new Paint();
                p1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                canvas123 = new Canvas(bitmap123);
                canvas123.drawBitmap(newFirstBmp, 0.0F, 0.0F, null);
                Log.d("image111_effect", effect.toString());
                canvas123.drawBitmap(effect.getMask(PVEMLyricalStatusMaker_PrefMnger.VIDEO_WIDTH, PVEMLyricalStatusMaker_PrefMnger.VIDEO_HEIGHT, m), 0.0F, 0.0F, p1);
                Bitmap b123 = Bitmap.createBitmap(PVEMLyricalStatusMaker_PrefMnger.VIDEO_WIDTH, PVEMLyricalStatusMaker_PrefMnger.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                canvas123 = new Canvas(b123);
                canvas123.drawBitmap(localObject2, 0.0F, 0.0F, null);
                canvas123.drawBitmap(bitmap123, 0.0F, 0.0F, new Paint());


                //	if(m<7){
                File file = new File(imageDir, String.format("img%02d.jpg", m));
                FileOutputStream var5;
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    var5 = new FileOutputStream(file);
                    b123.compress(Bitmap.CompressFormat.JPEG, 100, var5);
                    var5.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (PVEMLyricalStatusMaker_PrefMnger.isBreak) {
                    break;
                } else {
                    PVEMLyricalStatusMaker_PrefMnger.videoImages.add(file.getAbsolutePath());

                }
                updateImageProgress();
            }
            //	}

        }
        Glide.get(this).clearDiskCache();
        isImageComplate = true;

        stopSelf();

    }

    private void updateImageProgress() {
        int totalImageSize = (totalImages - 1) * PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL;
        final float progress = (100f * PVEMLyricalStatusMaker_PrefMnger.videoImages.size() / (float) totalImageSize);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                PVEMLyricalStatusMaker_ProgressReceiver receiver = PVEMLyricalStatusMaker_PrefMnger.onProgressReceiver;
                updateNotification((int) progress);
                if (receiver != null) {
                    receiver.onImageProgressUpdate(progress);
                }
            }
        });
    }

    private void updateNotification(int progress) {
        int p = (int) (25f * progress / 100f);

    }

    public void appendVideoLog(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        File file = new File(stringBuilder.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, "video.txt");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("File append ");
        stringBuilder2.append(str);
        Log.d("FFMPEG", stringBuilder2.toString());
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2, true));
            bufferedWriter.append(str);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static Bitmap ConvetrSameSizeNew(Bitmap originalImage, Bitmap bgBitmap,
                                            int mDisplayWidth, int mDisplayHeight) {
        Bitmap cs = null;
//		cs = bgBitmap.copy(Config.ARGB_8888, true);

        cs = doBlur(bgBitmap, 25, true);
        Canvas comboImage = new Canvas(cs);
        final Paint paint = new Paint();
        float originalWidth = originalImage.getWidth(), originalHeight = originalImage
                .getHeight();
        float scale = mDisplayWidth / originalWidth;
        float scaleY = mDisplayHeight / originalHeight;
        float xTranslation = 0.0f, yTranslation = (mDisplayHeight - originalHeight
                * scale) / 2.0f;
        if (yTranslation < 0) {
            yTranslation = 0;
            scale = mDisplayHeight / originalHeight;
            xTranslation = (mDisplayWidth - originalWidth * scaleY) / 2.0f;
        }
        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);
        comboImage.drawBitmap(originalImage, transformation, paint);

        return cs;
    }

    public static Bitmap doBlur(Bitmap sentBitmap, int radius,
                                boolean canReuseInBitmap) {


        if (Build.VERSION.SDK_INT >= 17) {
            return blurBitmap(sentBitmap, radius, mcontext);
        }


        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }
        if (radius < 1) {
            return (null);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    public static Bitmap blurBitmap(Bitmap bitmap, int radius, Context context) {


        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs,
                Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        blurScript.setRadius(radius);
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        allOut.copyTo(outBitmap);
        rs.destroy();
        return outBitmap;
    }

    public static Bitmap checkBitmap(String path) {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(path, opts);
        ExifInterface exif;
        try {
            exif = new ExifInterface(path);
            String orientString = exif
                    .getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer
                    .parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            Log.d("bitmap111_orientString", orientString);
            Log.d("bitmap111_orientation", String.valueOf(orientation));
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                rotationAngle = 270;
            Log.d("bitmap111_rotationAngle", String.valueOf(orientation));
            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2,
                    (float) bm.getHeight() / 2);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0,
                    bounds.outWidth, bounds.outHeight, matrix, true);
            return rotatedBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap scaleCenterCrop(Bitmap source, int newWidth,
                                         int newHeight) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        Log.d("bitmap123_sourceWidth", String.valueOf(sourceWidth));
        Log.d("bitmap123_sourceHeight", String.valueOf(sourceHeight));
        Log.d("bitmap123_newWidth", String.valueOf(newWidth));
        Log.d("bitmap123_newHeight", String.valueOf(newHeight));
        if (sourceWidth == newWidth && sourceHeight == newHeight) {
            return source;
        }

        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;
        RectF targetRect = new RectF(left, top, left + scaledWidth, top
                + scaledHeight);
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight,
                source.getConfig());

        Canvas canvas = new Canvas(dest);

        canvas.drawBitmap(source, null, targetRect, null);
        return dest;
    }
}
