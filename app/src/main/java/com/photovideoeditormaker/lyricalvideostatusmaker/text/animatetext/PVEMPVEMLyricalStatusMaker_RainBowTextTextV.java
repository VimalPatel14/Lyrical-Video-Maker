package com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;

import com.photovideoeditormaker.lyricalvideostatusmaker.text.util.PVEMLyricalStatusMaker_DisUtls;


/**
 * from http://wuxiaolong.me/2015/11/16/LinearGradientTextView/
 * Created by hanks on 15/12/26.
 */
public class PVEMPVEMLyricalStatusMaker_RainBowTextTextV extends PVEMPVEMLyricalStatusMaker_TextV {
    private int mTextWidth;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private float mTranslate;
    private int dx;

    @Override
    protected void initVariables() {
        mMatrix = new Matrix();
        dx = PVEMLyricalStatusMaker_DisUtls.dp2Px(7);
    }

    @Override
    protected void animateStart(CharSequence text) {

        mLyricalVidMakerHTextView.invalidate();
    }

    @Override
    protected void animatePrepare(CharSequence text) {
        mTextWidth = (int) mPaint.measureText(mText, 0, mText.length());
        mTextWidth = Math.max(PVEMLyricalStatusMaker_DisUtls.dp2Px(100), mTextWidth);
        if (mTextWidth > 0) {
            mLinearGradient = new LinearGradient(0, 0, mTextWidth, 0,
                    new int[]{0xFFFF2B22, 0xFFFF7F22, 0xFFEDFF22, 0xFF22FF22, 0xFF22F4FF, 0xFF2239FF, 0xFF5400F7}, null, Shader.TileMode.MIRROR);
            mPaint.setShader(mLinearGradient);
        }
    }

    @Override
    protected void drawFrame(Canvas canvas) {
        if (mMatrix != null && mLinearGradient != null) {
            mTranslate += dx;
            mMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mMatrix);
            canvas.drawText(mText, 0, mText.length(), startX, startY, mPaint);
            mLyricalVidMakerHTextView.postInvalidateDelayed(100);
        }
    }
}
