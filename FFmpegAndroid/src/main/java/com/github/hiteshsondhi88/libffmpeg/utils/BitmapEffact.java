package com.github.hiteshsondhi88.libffmpeg.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;

import java.lang.reflect.Array;


public class BitmapEffact {

	
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1) 
    public static Bitmap fastblur(Context mContext,Bitmap bmp,final int n) {
        final Bitmap bitmap = Bitmap.createBitmap( bmp.getWidth(),  bmp.getHeight(), Bitmap.Config.ARGB_8888);
        new Canvas(bitmap).drawBitmap( bmp, 0.0f, 0.0f, (Paint)null);
        if (n < 1) {
            return bmp;
        }
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final int[] array = new int[width * height];
        bitmap.getPixels(array, 0, width, 0, 0, width, height);
        final int n2 = width - 1;
        final int n3 = height - 1;
        final int n4 = width * height;
        final int n5 = 1 + (n + n);
        final int[] array2 = new int[n4];
        final int[] array3 = new int[n4];
        final int[] array4 = new int[n4];
        final int[] array5 = new int[Math.max(width, height)];
        final int n6 = n5 + 1 >> 1;
        final int n7 = n6 * n6;
        final int[] array6 = new int[n7 * 256];
        for (int i = 0; i < n7 * 256; ++i) {
            array6[i] = i / n7;
        }
        int n8 = 0;
        int n9 = 0;
        final int[][] array7 = (int[][])Array.newInstance(Integer.TYPE, n5, 3);
        final int n10 = n + 1;
        for (int j = 0; j < height; ++j) {
            int n11 = 0;
            int n12 = 0;
            int n13 = 0;
            int n14 = 0;
            int n15 = 0;
            int n16 = 0;
            int n17 = 0;
            int n18 = 0;
            int n19 = 0;
            for (int k = -n; k <= n; ++k) {
                final int n20 = array[n8 + Math.min(n2, Math.max(k, 0))];
                final int[] array8 = array7[k + n];
                array8[0] = (0xFF0000 & n20) >> 16;
                array8[1] = (0xFF00 & n20) >> 8;
                array8[2] = (n20 & 0xFF);
                final int n21 = n10 - Math.abs(k);
                n13 += n21 * array8[0];
                n12 += n21 * array8[1];
                n11 += n21 * array8[2];
                if (k > 0) {
                    n19 += array8[0];
                    n18 += array8[1];
                    n17 += array8[2];
                }
                else {
                    n16 += array8[0];
                    n15 += array8[1];
                    n14 += array8[2];
                }
            }
            int n22 = n;
            for (int l = 0; l < width; ++l) {
                array2[n8] = array6[n13];
                array3[n8] = array6[n12];
                array4[n8] = array6[n11];
                final int n23 = n13 - n16;
                final int n24 = n12 - n15;
                final int n25 = n11 - n14;
                final int[] array9 = array7[(n5 + (n22 - n)) % n5];
                final int n26 = n16 - array9[0];
                final int n27 = n15 - array9[1];
                final int n28 = n14 - array9[2];
                if (j == 0) {
                    array5[l] = Math.min(1 + (l + n), n2);
                }
                final int n29 = array[n9 + array5[l]];
                array9[0] = (0xFF0000 & n29) >> 16;
                array9[1] = (0xFF00 & n29) >> 8;
                array9[2] = (n29 & 0xFF);
                final int n30 = n19 + array9[0];
                final int n31 = n18 + array9[1];
                final int n32 = n17 + array9[2];
                n13 = n23 + n30;
                n12 = n24 + n31;
                n11 = n25 + n32;
                n22 = (n22 + 1) % n5;
                final int[] array10 = array7[n22 % n5];
                n16 = n26 + array10[0];
                n15 = n27 + array10[1];
                n14 = n28 + array10[2];
                n19 = n30 - array10[0];
                n18 = n31 - array10[1];
                n17 = n32 - array10[2];
                ++n8;
            }
            n9 += width;
        }
        for (int n33 = 0; n33 < width; ++n33) {
            int n34 = 0;
            int n35 = 0;
            int n36 = 0;
            int n37 = 0;
            int n38 = 0;
            int n39 = 0;
            int n40 = 0;
            int n41 = 0;
            int n42 = 0;
            int n43 = width * -n;
            for (int n44 = -n; n44 <= n; ++n44) {
                final int n45 = n33 + Math.max(0, n43);
                final int[] array11 = array7[n44 + n];
                array11[0] = array2[n45];
                array11[1] = array3[n45];
                array11[2] = array4[n45];
                final int n46 = n10 - Math.abs(n44);
                n36 += n46 * array2[n45];
                n35 += n46 * array3[n45];
                n34 += n46 * array4[n45];
                if (n44 > 0) {
                    n42 += array11[0];
                    n41 += array11[1];
                    n40 += array11[2];
                }
                else {
                    n39 += array11[0];
                    n38 += array11[1];
                    n37 += array11[2];
                }
                if (n44 < n3) {
                    n43 += width;
                }
            }
            int n47 = n33;
            int n48 = n;
            for (int n49 = 0; n49 < height; ++n49) {
                array[n47] = ((0xFF000000 & array[n47]) | array6[n36] << 16 | array6[n35] << 8 | array6[n34]);
                final int n50 = n36 - n39;
                final int n51 = n35 - n38;
                final int n52 = n34 - n37;
                final int[] array12 = array7[(n5 + (n48 - n)) % n5];
                final int n53 = n39 - array12[0];
                final int n54 = n38 - array12[1];
                final int n55 = n37 - array12[2];
                if (n33 == 0) {
                    array5[n49] = width * Math.min(n49 + n10, n3);
                }
                final int n56 = n33 + array5[n49];
                array12[0] = array2[n56];
                array12[1] = array3[n56];
                array12[2] = array4[n56];
                final int n57 = n42 + array12[0];
                final int n58 = n41 + array12[1];
                final int n59 = n40 + array12[2];
                n36 = n50 + n57;
                n35 = n51 + n58;
                n34 = n52 + n59;
                n48 = (n48 + 1) % n5;
                final int[] array13 = array7[n48];
                n39 = n53 + array13[0];
                n38 = n54 + array13[1];
                n37 = n55 + array13[2];
                n42 = n57 - array13[0];
                n41 = n58 - array13[1];
                n40 = n59 - array13[2];
                n47 += width;
            }
        }
        bitmap.setPixels(array, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
