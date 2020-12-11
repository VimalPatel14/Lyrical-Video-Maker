package com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext;

import android.graphics.Canvas;

public class PVEMPVEMLyricalStatusMaker_TyperTextTextV extends PVEMPVEMLyricalStatusMaker_TextV {
    private int currentLength;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void animateStart(CharSequence text) {
        currentLength = 0;
        mLyricalVidMakerHTextView.invalidate();
    }

    @Override
    protected void animatePrepare(CharSequence text) {

    }

    @Override
    protected void drawFrame(Canvas canvas) {

        canvas.drawText(mText, 0, currentLength, startX, startY, mPaint);
        if (currentLength < mText.length()) {
            currentLength++;
            mLyricalVidMakerHTextView.postInvalidateDelayed(100);
        }
    }
}
