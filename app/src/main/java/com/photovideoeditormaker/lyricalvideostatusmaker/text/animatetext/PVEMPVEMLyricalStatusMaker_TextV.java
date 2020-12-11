package com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.photovideoeditormaker.lyricalvideostatusmaker.text.PVEMLyricalStatusMaker_TextViewV;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.util.PVEMLyricalStatusMaker__ChactrUtls;

import java.util.ArrayList;
import java.util.List;

/**
 * base class
 * Created by hanks on 15-12-19.
 */
public abstract class PVEMPVEMLyricalStatusMaker_TextV implements PVEMLyricalStatusMaker__IVText {

    protected Paint mPaint, mOldPaint;

    /**
     * the gap between characters
     */
    protected float[] gaps = new float[100];
    protected float[] oldGaps = new float[100];

    /**
     * current text size
     */
    protected float mTextSize;

    protected CharSequence mText;
    protected CharSequence mOldText;

    protected List<PVEMLyricalStatusMaker_CharDiffRes> differentList = new ArrayList<>();

    protected float oldStartX = 0;
    protected float startX = 0;
    protected float startY = 0;

    protected PVEMLyricalStatusMaker_TextViewV mLyricalVidMakerHTextView;



    public void init(PVEMLyricalStatusMaker_TextViewV lyricalVidMakerHTextView, AttributeSet attrs, int defStyle) {

        mLyricalVidMakerHTextView = lyricalVidMakerHTextView;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mLyricalVidMakerHTextView.getCurrentTextColor());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTypeface(mLyricalVidMakerHTextView.getTypeface());

        mOldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOldPaint.setColor(mLyricalVidMakerHTextView.getCurrentTextColor());
        mOldPaint.setStyle(Paint.Style.FILL);
        mOldPaint.setTypeface(mLyricalVidMakerHTextView.getTypeface());

        mText = mLyricalVidMakerHTextView.getText();
        mOldText = mLyricalVidMakerHTextView.getText();

        mTextSize = mLyricalVidMakerHTextView.getTextSize();

        initVariables();
        mLyricalVidMakerHTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareAnimate();
            }
        }, 50);

    }

    @Override
    public void animateText(CharSequence text) {
        mLyricalVidMakerHTextView.setText(text);
        mOldText = mText;
        mText = text;
        prepareAnimate();
        animatePrepare(text);
        animateStart(text);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setColor(mLyricalVidMakerHTextView.getCurrentTextColor());
        mOldPaint.setColor(mLyricalVidMakerHTextView.getCurrentTextColor());
        drawFrame(canvas);
    }

    private void prepareAnimate() {
        mTextSize = mLyricalVidMakerHTextView.getTextSize();
        mPaint.setTextSize(mTextSize);
        for (int i = 0; i < mText.length(); i++) {
            gaps[i] = mPaint.measureText(mText.charAt(i) + "");
        }

        mOldPaint.setTextSize(mTextSize);
        for (int i = 0; i < mOldText.length(); i++) {
            oldGaps[i] = mOldPaint.measureText(mOldText.charAt(i) + "");
        }

        oldStartX = (mLyricalVidMakerHTextView.getMeasuredWidth() - mLyricalVidMakerHTextView.getCompoundPaddingLeft() - mLyricalVidMakerHTextView.getPaddingLeft() - mOldPaint
                .measureText(mOldText.toString())) / 2f;
        startX = (mLyricalVidMakerHTextView.getMeasuredWidth() - mLyricalVidMakerHTextView.getCompoundPaddingLeft() - mLyricalVidMakerHTextView.getPaddingLeft() - mPaint
                .measureText(mText.toString())) / 2f;
        startY = mLyricalVidMakerHTextView.getBaseline();

        differentList.clear();
        differentList.addAll(PVEMLyricalStatusMaker__ChactrUtls.diff(mOldText, mText));
    }

    public void reset(CharSequence text) {
        animatePrepare(text);
        mLyricalVidMakerHTextView.invalidate();
    }

    protected abstract void initVariables();

    protected abstract void animateStart(CharSequence text);

    protected abstract void animatePrepare(CharSequence text);

    protected abstract void drawFrame(Canvas canvas);


}
