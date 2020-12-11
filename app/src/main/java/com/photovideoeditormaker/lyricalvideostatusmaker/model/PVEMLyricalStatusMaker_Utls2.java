package com.photovideoeditormaker.lyricalvideostatusmaker.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import java.util.ArrayList;

public class PVEMLyricalStatusMaker_Utls2 {
    public static final float DEFAULT_FONT_SCALE = 1.0f;
    public static final float HUE_CYAN = 180.0f;
    public static final float HUE_GREEN = 120.0f;
    public static final float HUE_ORANGE = 30.0f;
    public static final float HUE_VIOLET = 270.0f;
    public static int VIDEO_HEIGHT = 1280;
    public static int VIDEO_WIDTH = 720;
    public static int audioDuration = 0;
    public static String audioName = "";
    public static int audioSelected = -1;
    public static Bitmap bitmap = null;
    public static int filterIndex = -1;
    public static int framePostion = -1;
    public static int height = 0;
    public static ArrayList<PVEMLyricalStatusMaker_SelectImage> imageUri = new ArrayList();
    public static int imgCount = 0;
    public static Boolean ovalshape = null;
    public static int pos = -1;
    public static Boolean rectangleshape;
    public static Typeface tf;
    public static int width;

    public static boolean isInternetConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
                NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(1);
                if ((networkInfo == null || !networkInfo.isConnectedOrConnecting()) && (networkInfo2 == null || !networkInfo2.isConnectedOrConnecting())) {
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getPath(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(context.getResources().getString(R.string.app_name));
        return stringBuilder.toString();
    }

    public static float getTransCut(String str) {
        int parseInt = Integer.parseInt(str);
        if (parseInt < 3) {
            return 5.0f;
        }
        if (parseInt < 5) {
            return 2.0f;
        }
        if (parseInt < 8) {
            return 1.0f;
        }
        if (parseInt < 11) {
            return 0.8f;
        }
        if (parseInt < 16) {
            return 0.5f;
        }
        if (parseInt < 21) {
            return 0.3f;
        }
        return parseInt < 26 ? 0.2f : 0.1f;
    }

    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
