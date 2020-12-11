package com.photovideoeditormaker.lyricalvideostatusmaker.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Random;

public class PVEMLyricalStatusMaker_FinalMaskBmp {
    public static float ANIMATED_FRAME;
    public static int ANIMATED_FRAME_CAL;
    public static int ORIGANAL_FRAME;
    public static int TOTAL_FRAME = 30;
    static Paint paint = null;
    static int[][] randRect;
    static Random random;

    static {
        ORIGANAL_FRAME = 8;
        ANIMATED_FRAME = 22.0F;
        ANIMATED_FRAME_CAL = (int) (ANIMATED_FRAME - 1.0F);
        randRect = (int[][]) Array.newInstance(Integer.TYPE, new int[]{20, 20});
        random = new Random();
        paint = new Paint();
        //  paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    static Bitmap getHorizontalColumnDownMask(int paramInt1, int paramInt2, int paramInt3) {
        Paint localPaint = new Paint();
        // localPaint.setColor(-16777216);
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        float f = ANIMATED_FRAME_CAL / 2.0F;
        localCanvas.drawRoundRect(new RectF(0.0F, 0.0F, paramInt1 / (ANIMATED_FRAME_CAL / 2.0F) *
                paramInt3, paramInt2 / 2.0F), 0.0F, 0.0F, localPaint);
        if (paramInt3 >= 0.5F + f) {
            paramInt3 = (int) (paramInt3 - f);
            localCanvas.drawRoundRect(new RectF(paramInt1 - paramInt1 /
                    ((ANIMATED_FRAME_CAL - 1) / 2.0F) * paramInt3, paramInt2 /
                    2.0F, paramInt1, paramInt2), 0.0F, 0.0F, localPaint);
        }
        return localBitmap;
    }

    static Bitmap getNewMask(int paramInt1, int paramInt2, int paramInt3) {
        Paint localPaint = new Paint();
        //   localPaint.setColor(-16777216);
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        float f1 = paramInt1 / 18.0F * paramInt3;
        float f2 = paramInt2 / 18.0F * paramInt3;
        Path localPath = new Path();
        localPath.moveTo(paramInt1 / 2, paramInt2 / 2);
        localPath.lineTo(paramInt1 / 2 + f1, paramInt2 / 2);
        localPath.lineTo(paramInt1, 0.0F);
        localPath.lineTo(paramInt1 / 2, paramInt2 / 2 - f2);
        localPath.lineTo(paramInt1 / 2, paramInt2 / 2);
        localPath.moveTo(paramInt1 / 2, paramInt2 / 2);
        localPath.lineTo(paramInt1 / 2 - f1, paramInt2 / 2);
        localPath.lineTo(0.0F, 0.0F);
        localPath.lineTo(paramInt1 / 2, paramInt2 / 2 - f2);
        localPath.lineTo(paramInt1 / 2, paramInt2 / 2);
        localPath.moveTo(paramInt1 / 2, paramInt2 / 2);
        localPath.lineTo(paramInt1 / 2 - f1, paramInt2 / 2);
        localPath.lineTo(0.0F, paramInt2);
        localPath.lineTo(paramInt1 / 2, paramInt2 / 2 + f2);
        localPath.lineTo(paramInt1 / 2, paramInt2 / 2);
        localPath.moveTo(paramInt1 / 2, paramInt2 / 2);
        localPath.lineTo(paramInt1 / 2 + f1, paramInt2 / 2);
        localPath.lineTo(paramInt1, paramInt2);
        localPath.lineTo(paramInt1 / 2, paramInt2 / 2 + f2);
        localPath.lineTo(paramInt1 / 2, paramInt2 / 2);
        localPath.close();
        localCanvas.drawCircle(paramInt1 / 2.0F, paramInt2 / 2.0F, paramInt1 / 18.0F * paramInt3,
                localPaint);
        localCanvas.drawPath(localPath, localPaint);
        return localBitmap;
    }

    public static Paint getPaint() {
        // paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

    static float getRad(int paramInt1, int paramInt2) {
        return (float) Math.sqrt((paramInt1 * paramInt1 + paramInt2 * paramInt2) / 4);
    }

    public static void reintRect() {
        int i = (int) ANIMATED_FRAME;
        int j = (int) ANIMATED_FRAME;
        randRect = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i, j});
        i = 0;
        if (i >= randRect.length)
            return;
        j = 0;
        while (true) {
            if (j >= randRect[i].length) {
                i += 1;
                break;
            }
            randRect[i][j] = 0;
            j += 1;
        }
    }

    public enum EFFECT {
        CIRCLE_LEFT_BOTTOM("CIRCLE_LEFT_BOTTOM", 2, "Circle left bottom") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f = (float) Math.sqrt(paramInt1 * paramInt1 + paramInt2 * paramInt2);
                localCanvas.drawCircle(0.0F, paramInt2, f / ANIMATED_FRAME_CAL * paramInt3,
                        localPaint);
                drawText(localCanvas);
                return localBitmap;
            }
        },
        CIRCLE_RIGHT_BOTTOM("CIRCLE_RIGHT_BOTTOM", 3, "Circle right bottom") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f = (float) Math.sqrt(paramInt1 * paramInt1 + paramInt2 * paramInt2);
                localCanvas.drawCircle(paramInt1, paramInt2, f / ANIMATED_FRAME_CAL * paramInt3,
                        localPaint);
                drawText(localCanvas);
                return localBitmap;
            }
        },
        CIRCLE_IN("CIRCLE_IN", 4, "Circle in") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = PVEMLyricalStatusMaker_FinalMaskBmp.getRad(paramInt1 * 2, paramInt2 * 2);
                float f2 = f1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL;
                float f3 = paramInt3;
                // localPaint.setColor(-16777216);
                //localCanvas.drawColor(-16777216);
                // localPaint.setColor(0);
                localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                localCanvas.drawCircle(paramInt1 / 2.0F, paramInt2 / 2.0F, f1 - f2 * f3, localPaint);
                drawText(localCanvas);
                return localBitmap;
            }
        },
        CIRCLE_OUT("CIRCLE_OUT", 5, "Circle out") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = PVEMLyricalStatusMaker_FinalMaskBmp.getRad(paramInt1 * 2, paramInt2 * 2) / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL;
                float f2 = paramInt3;
                localCanvas.drawCircle(paramInt1 / 2.0F, paramInt2 / 2.0F, f1 * f2, localPaint);
                drawText(localCanvas);
                return localBitmap;
            }
        },
        CROSS_IN("CROSS_IN", 6, "Cross in") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //   localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                float f2 = paramInt2 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(0.0F, 0.0F);
                localPath.lineTo(f1, 0.0F);
                localPath.lineTo(f1, f2);
                localPath.lineTo(0.0F, f2);
                localPath.lineTo(0.0F, 0.0F);
                localPath.close();
                localPath.moveTo(paramInt1, 0.0F);
                localPath.lineTo(paramInt1 - f1, 0.0F);
                localPath.lineTo(paramInt1 - f1, f2);
                localPath.lineTo(paramInt1, f2);
                localPath.lineTo(paramInt1, 0.0F);
                localPath.close();
                localPath.moveTo(paramInt1, paramInt2);
                localPath.lineTo(paramInt1 - f1, paramInt2);
                localPath.lineTo(paramInt1 - f1, paramInt2 - f2);
                localPath.lineTo(paramInt1, paramInt2 - f2);
                localPath.lineTo(paramInt1, paramInt2);
                localPath.close();
                localPath.moveTo(0.0F, paramInt2);
                localPath.lineTo(f1, paramInt2);
                localPath.lineTo(f1, paramInt2 - f2);
                localPath.lineTo(0.0F, paramInt2 - f2);
                localPath.lineTo(0.0F, 0.0F);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                // drawText(localCanvas);
                return localBitmap;
            }
        },
        CROSS_OUT("CROSS_OUT", 7, "Cross out") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                float f2 = paramInt2 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(paramInt1 / 2.0F + f1, 0.0F);
                localPath.lineTo(paramInt1 / 2.0F + f1, paramInt2 / 2.0F - f2);
                localPath.lineTo(paramInt1, paramInt2 / 2.0F - f2);
                localPath.lineTo(paramInt1, paramInt2 / 2.0F + f2);
                localPath.lineTo(paramInt1 / 2.0F + f1, paramInt2 / 2.0F + f2);
                localPath.lineTo(paramInt1 / 2.0F + f1, paramInt2);
                localPath.lineTo(paramInt1 / 2.0F - f1, paramInt2);
                localPath.lineTo(paramInt1 / 2.0F - f1, paramInt2 / 2.0F - f2);
                localPath.lineTo(0.0F, paramInt2 / 2.0F - f2);
                localPath.lineTo(0.0F, paramInt2 / 2.0F + f2);
                localPath.lineTo(paramInt1 / 2.0F - f1, paramInt2 / 2.0F + f2);
                localPath.lineTo(paramInt1 / 2.0F - f1, 0.0F);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                drawText(localCanvas);
                return localBitmap;
            }
        },
        DIAMOND_IN("DIAMOND_IN", 8, "Diamond in") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //    localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                Path localPath = new Path();
                float f1 = paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                float f2 = paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                localPath.moveTo(paramInt1 / 2.0F, paramInt2 / 2.0F - f2);
                localPath.lineTo(paramInt1 / 2.0F + f1, paramInt2 / 2.0F);
                localPath.lineTo(paramInt1 / 2.0F, paramInt2 / 2.0F + f2);
                localPath.lineTo(paramInt1 / 2.0F - f1, paramInt2 / 2.0F);
                localPath.lineTo(paramInt1 / 2.0F, paramInt2 / 2.0F - f2);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                return localBitmap;
            }
        },
        DIAMOND_OUT("DIAMOND_OUT", 9, "Diamond out") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                //  localPaint.setColor(0);
                localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                // localCanvas.drawColor(-16777216);

                Path localPath = new Path();
                float f1 = paramInt1 - paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                float f2 = paramInt2 - paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                Log.d("path1234_paramInt1", String.valueOf(paramInt1));
                Log.d("path1234_paramInt2", String.valueOf(paramInt2));
                Log.d("path1234_paramInt3", String.valueOf(paramInt3));
                Log.d("path1234_f1", String.valueOf(f1));
                Log.d("path1234_f2", String.valueOf(f2));
                Log.d("path1234_MOVEx", String.valueOf(paramInt1 / 2.0F));
                Log.d("path1234_MOVEy", String.valueOf(paramInt2 / 2.0F - f2));
                Log.d("path1234_LINE1_x", String.valueOf(paramInt1 / 2.0F + f1));
                Log.d("path1234_LINE1_y", String.valueOf(paramInt2 / 2.0F));
                Log.d("path1234_LINE2_x", String.valueOf(paramInt1 / 2.0F));
                Log.d("path1234_LINE2_y", String.valueOf(paramInt2 / 2.0F + f2));
                Log.d("path1234_LINE3_x", String.valueOf(paramInt1 / 2.0F - f1));
                Log.d("path1234_LINE3_y", String.valueOf(paramInt2 / 2.0F));
                Log.d("path1234_LINE4_x", String.valueOf(paramInt1 / 2.0F));
                Log.d("path1234_LINE4_y", String.valueOf(paramInt2 / 2.0F - f2));
                Log.d("path1234_", "---------------------------------------------------");
                //   if (f1 > 0 && f2 > 0) {
                localPath.moveTo(paramInt1 / 2.0F, paramInt2 / 2.0F - f2);
                localPath.lineTo(paramInt1 / 2.0F + f1, paramInt2 / 2.0F);
                localPath.lineTo(paramInt1 / 2.0F, paramInt2 / 2.0F + f2);
                localPath.lineTo(paramInt1 / 2.0F - f1, paramInt2 / 2.0F);
                localPath.lineTo(paramInt1 / 2.0F, paramInt2 / 2.0F - f2);
                localPath.close();
                localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                localCanvas.drawPath(localPath, localPaint);
                drawText(localCanvas);
                //   }


                return localBitmap;
            }
        },
        ECLIPSE_IN("ECLIPSE_IN", 10, "Eclipse in") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt2 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                float f2 = paramInt1 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                RectF localRectF1 = new RectF(-f2, 0.0F, f2, paramInt2);
                RectF localRectF2 = new RectF(0.0F, -f1, paramInt1, f1);
                RectF localRectF3 = new RectF(paramInt1 - f2, 0.0F, paramInt1 + f2, paramInt2);
                RectF localRectF4 = new RectF(0.0F, paramInt2 - f1, paramInt1, paramInt2 + f1);
                localCanvas.drawOval(localRectF1, PVEMLyricalStatusMaker_FinalMaskBmp.paint);
                localCanvas.drawOval(localRectF2, PVEMLyricalStatusMaker_FinalMaskBmp.paint);
                localCanvas.drawOval(localRectF3, PVEMLyricalStatusMaker_FinalMaskBmp.paint);
                localCanvas.drawOval(localRectF4, PVEMLyricalStatusMaker_FinalMaskBmp.paint);
                drawText(localCanvas);
                return localBitmap;
            }
        },
        FOUR_TRIANGLE("FOUR_TRIANGLE", 11, "Four triangle") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //   localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                float f2 = paramInt2 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(0.0F, f2);
                localPath.lineTo(0.0F, 0.0F);
                localPath.lineTo(f1, 0.0F);
                localPath.lineTo(paramInt1, paramInt2 - f2);
                localPath.lineTo(paramInt1, paramInt2);
                localPath.lineTo(paramInt1 - f1, paramInt2);
                localPath.lineTo(0.0F, f2);
                localPath.close();
                localPath.moveTo(paramInt1 - f1, 0.0F);
                localPath.lineTo(paramInt1, 0.0F);
                localPath.lineTo(paramInt1, f2);
                localPath.lineTo(f1, paramInt2);
                localPath.lineTo(0.0F, paramInt2);
                localPath.lineTo(0.0F, paramInt2 - f2);
                localPath.lineTo(paramInt1 - f1, 0.0F);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                return localBitmap;
            }
        },
        HORIZONTAL_RECT("HORIZONTAL_RECT", 12, "Horizontal rect") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                Paint localPaint = new Paint();
                //  localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                float f1 = paramInt1 / 10.0F;
                float f2 = f1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL;
                float f3 = paramInt3;
                paramInt1 = 0;
                while (true) {
                    if (paramInt1 >= 10) {
                        drawText(localCanvas);
                        return localBitmap;
                    }
                    localCanvas.drawRect(new Rect((int) (paramInt1 * f1), 0,
                            (int) (paramInt1 * f1 + f2 * f3), paramInt2), localPaint);
                    paramInt1 += 1;
                }
            }
        },
        HORIZONTAL_COLUMN_DOWNMASK("HORIZONTAL_COLUMN_DOWNMASK", 13, "Horizontal column downmask") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f = PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL / 2.0F;
                localCanvas.drawRoundRect(new RectF(0.0F, 0.0F,
                                paramInt1 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL / 2.0F) * paramInt3, paramInt2 / 2.0F),
                        0.0F, 0.0F, localPaint);
                if (paramInt3 >= 0.5F + f) {
                    paramInt3 = (int) (paramInt3 - f);
                    localCanvas.drawRoundRect(new RectF(paramInt1 - paramInt1 /
                                    ((PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL - 1) / 2.0F) *
                                    paramInt3, paramInt2 / 2.0F, paramInt1, paramInt2),
                            0.0F, 0.0F, localPaint);
                }
                return localBitmap;
            }
        },
        LEAF("LEAF", 14, "LEAF") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                localPaint.setStrokeCap(Paint.Cap.BUTT);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                float f1 = paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                float f2 = paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                Canvas localCanvas = new Canvas(localBitmap);
                Path localPath = new Path();
                localPath.moveTo(0.0F, paramInt2);
                localPath.cubicTo(0.0F, paramInt2, paramInt1 / 2.0F - f1, paramInt2 / 2.0F - f2, paramInt1, 0.0F);
                localPath.cubicTo(paramInt1, 0.0F, paramInt1 / 2.0F + f1, paramInt2 / 2.0F + f2, 0.0F, paramInt2);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                drawText(localCanvas);
                return localBitmap;
            }
        },
        OPEN_DOOR("OPEN_DOOR", 15, "OPEN_DOOR") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                localPaint.setStrokeCap(Paint.Cap.BUTT);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                float f1 = paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                float f2 = paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                Canvas localCanvas = new Canvas(localBitmap);
                Path localPath = new Path();
                localPath.moveTo(paramInt1 / 2, 0.0F);
                localPath.lineTo(paramInt1 / 2 - f1, 0.0F);
                localPath.lineTo(paramInt1 / 2 - f1 / 2.0F, paramInt2 / 6);
                localPath.lineTo(paramInt1 / 2 - f1 / 2.0F, paramInt2 - paramInt2 / 6);
                localPath.lineTo(paramInt1 / 2 - f1, paramInt2);
                localPath.lineTo(paramInt1 / 2 + f1, paramInt2);
                localPath.lineTo(paramInt1 / 2 + f1 / 2.0F, paramInt2 - paramInt2 / 6);
                localPath.lineTo(paramInt1 / 2 + f1 / 2.0F, paramInt2 / 6);
                localPath.lineTo(paramInt1 / 2 + f1, 0.0F);
                localPath.lineTo(paramInt1 / 2 - f1, 0.0F);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                drawText(localCanvas);
                return localBitmap;
            }
        },
        PIN_WHEEL("PIN_WHEEL", 16, "PIN_WHEEL") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //  localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                float f2 = paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(paramInt1 / 2.0F, paramInt2 / 2.0F);
                localPath.lineTo(0.0F, paramInt2);
                localPath.lineTo(f1, paramInt2);
                localPath.close();
                localPath.moveTo(paramInt1 / 2.0F, paramInt2 / 2.0F);
                localPath.lineTo(paramInt1, paramInt2);
                localPath.lineTo(paramInt1, paramInt2 - f2);
                localPath.close();
                localPath.moveTo(paramInt1 / 2.0F, paramInt2 / 2.0F);
                localPath.lineTo(paramInt1, 0.0F);
                localPath.lineTo(paramInt1 - f1, 0.0F);
                localPath.close();
                localPath.moveTo(paramInt1 / 2.0F, paramInt2 / 2.0F);
                localPath.lineTo(0.0F, 0.0F);
                localPath.lineTo(0.0F, f2);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                return localBitmap;
            }
        },

        SKEW_LEFT_MEARGE("SKEW_LEFT_MEARGE", 18, "SKEW_LEFT_MEARGE") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.TOTAL_FRAME * paramInt3;
                float f2 = paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.TOTAL_FRAME * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(0.0F, f2);
                localPath.lineTo(f1, 0.0F);
                localPath.lineTo(0.0F, 0.0F);
                localPath.close();
                localPath.moveTo(paramInt1 - f1, paramInt2);
                localPath.lineTo(paramInt1, paramInt2 - f2);
                localPath.lineTo(paramInt1, paramInt2);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                return localBitmap;
            }
        },
        SKEW_LEFT_SPLIT("SKEW_LEFT_SPLIT", 19, "SKEW_LEFT_SPLIT") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                float f2 = paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(0.0F, f2);
                localPath.lineTo(0.0F, 0.0F);
                localPath.lineTo(f1, 0.0F);
                localPath.lineTo(paramInt1, paramInt2 - f2);
                localPath.lineTo(paramInt1, paramInt2);
                localPath.lineTo(paramInt1 - f1, paramInt2);
                localPath.lineTo(0.0F, f2);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                return localBitmap;
            }
        },
        SKEW_RIGHT_SPLIT("SKEW_RIGHT_SPLIT", 20, "SKEW_RIGHT_SPLIT") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //  localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                float f2 = paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(paramInt1 - f1, 0.0F);
                localPath.lineTo(paramInt1, 0.0F);
                localPath.lineTo(paramInt1, f2);
                localPath.lineTo(f1, paramInt2);
                localPath.lineTo(0.0F, paramInt2);
                localPath.lineTo(0.0F, paramInt2 - f2);
                localPath.lineTo(paramInt1 - f1, 0.0F);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                return localBitmap;
            }
        },
        SKEW_RIGHT_MEARGE("SKEW_RIGHT_MEARGE", 21, "SKEW_RIGHT_MEARGE") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                float f2 = paramInt2 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(0.0F, paramInt2 - f2);
                localPath.lineTo(f1, paramInt2);
                localPath.lineTo(0.0F, paramInt2);
                localPath.close();
                localPath.moveTo(paramInt1 - f1, 0.0F);
                localPath.lineTo(paramInt1, f2);
                localPath.lineTo(paramInt1, 0.0F);
                localPath.close();
                localCanvas.drawPath(localPath, localPaint);
                return localBitmap;
            }
        },
        SQUARE_IN("SQUARE_IN", 22, "SQUARE_IN") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //  localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                float f2 = paramInt2 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                Path localPath = new Path();
                localPath.moveTo(0.0F, 0.0F);
                localPath.lineTo(0.0F, paramInt2);
                localPath.lineTo(f1, paramInt2);
                localPath.lineTo(f1, 0.0F);
                localPath.moveTo(paramInt1, paramInt2);
                localPath.lineTo(paramInt1, 0.0F);
                localPath.lineTo(paramInt1 - f1, 0.0F);
                localPath.lineTo(paramInt1 - f1, paramInt2);
                localPath.moveTo(f1, f2);
                localPath.lineTo(f1, 0.0F);
                localPath.lineTo(paramInt1 - f1, 0.0F);
                localPath.lineTo(paramInt1 - f1, f2);
                localPath.moveTo(f1, paramInt2 - f2);
                localPath.lineTo(f1, paramInt2);
                localPath.lineTo(paramInt1 - f1, paramInt2);
                localPath.lineTo(paramInt1 - f1, paramInt2 - f2);
                localCanvas.drawPath(localPath, localPaint);
                return localBitmap;
            }
        },
        SQUARE_OUT("SQUARE_OUT", 23, "SQUARE_OUT") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Paint localPaint = new Paint();
                //  localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                float f1 = paramInt1 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                float f2 = paramInt2 / (PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * 2.0F) * paramInt3;
                localCanvas.drawRect(new RectF(paramInt1 / 2 - f1, paramInt2 / 2 - f2, paramInt1 / 2 + f1, paramInt2 / 2 + f2), localPaint);
                return localBitmap;
            }
        },
        VERTICAL_RECT("VERTICAL_RECT", 24, "VERTICAL_RECT") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                float f1 = paramInt2 / 10.0F;
                float f2 = paramInt3 * f1 / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL;
                paramInt2 = 0;
                while (true) {
                    if (paramInt2 >= 10) {
                        drawText(localCanvas);
                        return localBitmap;
                    }
                    localCanvas.drawRect(new Rect(0, (int) (paramInt2 * f1), paramInt1, (int) (paramInt2 * f1 + f2)), localPaint);
                    paramInt2 += 1;
                }
            }
        },
        WIND_MILL("WIND_MILL", 25, "WIND_MILL") {
            @Override
            public Bitmap getMask(int paramInt1, int paramInt2, int paramInt3) {
                float f = PVEMLyricalStatusMaker_FinalMaskBmp.getRad(paramInt1, paramInt2);
                Paint localPaint = new Paint();
                // localPaint.setColor(-16777216);
                localPaint.setAntiAlias(true);
                localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                RectF localRectF = new RectF();
                localRectF.set(paramInt1 / 2.0F - f, paramInt2 / 2.0F - f, paramInt1 / 2.0F + f, paramInt2 / 2.0F + f);
                f = 90.0F / PVEMLyricalStatusMaker_FinalMaskBmp.ANIMATED_FRAME_CAL * paramInt3;
                localCanvas.drawArc(localRectF, 90.0F, f, true, localPaint);
                localCanvas.drawArc(localRectF, 180.0F, f, true, localPaint);
                localCanvas.drawArc(localRectF, 270.0F, f, true, localPaint);
                localCanvas.drawArc(localRectF, 360.0F, f, true, localPaint);
                drawText(localCanvas);
                return localBitmap;
            }
        };


        String name = "";

        EFFECT(String ab, int i, String s) {
            Object localObject = null;
            /// this.name = localObject;
        }

        public void drawText(Canvas paramCanvas) {
        }

        public abstract Bitmap getMask(int paramInt1, int paramInt2, int paramInt3);
    }


}
