package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.graphics.Canvas;

import com.photovideoeditormaker.lyricalvideostatusmaker.utils.PVEMLyricalStatusMaker_AnimLay;

public abstract class PVEMLyricalStatusMaker_Animation {
    protected float h;
    public float totalPaintTime;
    protected PVEMLyricalStatusMaker_AnimLay view;
    protected float w;

    public abstract void handleCanvas(Canvas canvas, float f);

    public PVEMLyricalStatusMaker_Animation(PVEMLyricalStatusMaker_AnimLay mRYTCHER_EnterAnimLayout) {
        this(mRYTCHER_EnterAnimLayout, 2000.0f);
    }

    public PVEMLyricalStatusMaker_Animation(PVEMLyricalStatusMaker_AnimLay mRYTCHER_EnterAnimLayout, float f) {
        this.totalPaintTime = f;
        this.view = mRYTCHER_EnterAnimLayout;
        this.view.setAnim(this);
        this.w = (float) mRYTCHER_EnterAnimLayout.getWidth();
        this.h = (float) mRYTCHER_EnterAnimLayout.getHeight();
    }

    public void startAnimation() {
        this.view.setmIsAnimaionRun(true);
        this.view.setStartTime(System.currentTimeMillis());
        this.view.invalidate();
    }

    public void startAnimation(long j) {
        this.totalPaintTime = (float) j;
        startAnimation();
    }
}
