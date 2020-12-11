package com.photovideoeditormaker.lyricalvideostatusmaker.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.activity.PVEMLyricalStatusMaker_Animation;

public class PVEMLyricalStatusMaker_AnimLay extends FrameLayout {
    private PVEMLyricalStatusMaker_Animation anim;
    private boolean mIsAnimaionRun = false;
    private boolean mIsVisibleAtFirst = true;
    private long startTime = 0;

    protected void initialize() {
    }

    public PVEMLyricalStatusMaker_AnimLay(Context context) {
        super(context);
        initialize();
    }

    public PVEMLyricalStatusMaker_AnimLay(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AspectRatioFrameLayout);
        this.mIsVisibleAtFirst = obtainStyledAttributes.getBoolean(0, true);
        obtainStyledAttributes.recycle();
        initialize();
    }

    public PVEMLyricalStatusMaker_AnimLay(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScaleLayout);
        this.mIsVisibleAtFirst = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        initialize();
    }

    public void setAnim(PVEMLyricalStatusMaker_Animation mRYTCHER_Anim) {
        this.anim = mRYTCHER_Anim;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long j) {
        this.startTime = j;
    }

    public boolean ismIsAnimaionRun() {
        return this.mIsAnimaionRun;
    }

    public void setmIsAnimaionRun(boolean z) {
        this.mIsAnimaionRun = z;
    }

    protected void dispatchDraw(Canvas canvas) {
        if (this.mIsVisibleAtFirst && !this.mIsAnimaionRun) {
            super.dispatchDraw(canvas);
        } else if (this.mIsVisibleAtFirst || this.mIsAnimaionRun) {
            float currentTimeMillis = ((float) (System.currentTimeMillis() - this.startTime)) / this.anim.totalPaintTime;
            if (currentTimeMillis > 1.0f) {
                currentTimeMillis = 1.0f;
            }
            this.anim.handleCanvas(canvas, currentTimeMillis);
            super.dispatchDraw(canvas);
            if (currentTimeMillis < 1.0f) {
                invalidate();
            } else {
                this.mIsAnimaionRun = false;
                this.mIsVisibleAtFirst = true;
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 || !this.mIsAnimaionRun) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        this.mIsAnimaionRun = false;
        return true;
    }
}
