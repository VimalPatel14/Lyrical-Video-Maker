package com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.animation.DecelerateInterpolator;

import com.photovideoeditormaker.lyricalvideostatusmaker.text.util.PVEMLyricalStatusMaker_DisUtls;


/**
 * Created by hanks on 15-12-17.
 */
public class PVEMPVEMLyricalStatusMaker_LineTextTextV extends PVEMPVEMLyricalStatusMaker_TextV {

    float progress = 0;
    float ANIMA_DURATION = 800;

    float mTextHeight = 0;

    Paint linePaint;
    float padding; // distance between text and line
    float gap;
    PointF p1 = new PointF();
    PointF p2 = new PointF();
    PointF p3 = new PointF();
    PointF p4 = new PointF();
    PointF p5 = new PointF();
    PointF p6 = new PointF();
    PointF p7 = new PointF();
    PointF p8 = new PointF();
    int xLineLength;

    private float distWidth; // line width when animation end
    private float distHeight;

    private float yLineLength;
    private float xLineWidth;

    private int yLineShort;
    private int xLineShort;

    @Override
    protected void initVariables() {

        xLineWidth = PVEMLyricalStatusMaker_DisUtls.dp2px(mLyricalVidMakerHTextView.getContext(), 1.5f);
        padding = PVEMLyricalStatusMaker_DisUtls.dp2px(mLyricalVidMakerHTextView.getContext(), 15);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(mLyricalVidMakerHTextView.getCurrentTextColor());
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(xLineWidth);

    }

    @Override
    protected void animateStart(CharSequence text) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1)
                .setDuration((long) ANIMA_DURATION);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                mLyricalVidMakerHTextView.invalidate();
            }
        });
        valueAnimator.start();
        progress = 0;
    }

    @Override
    protected void animatePrepare(CharSequence text) {

        Rect bounds = new Rect();
        mPaint.getTextBounds(mText.toString(), 0, mText.length(), bounds);
        mTextHeight = bounds.height();

        distWidth = bounds.width() + padding * 2 + xLineWidth;
        distHeight = bounds.height() + padding * 2 + xLineWidth;

        xLineLength = mLyricalVidMakerHTextView.getWidth();
        yLineLength = mLyricalVidMakerHTextView.getHeight();
    }

    @Override
    protected void drawFrame(Canvas canvas) {
        float percent = progress;

        xLineLength = (int) (mLyricalVidMakerHTextView.getWidth() - (mLyricalVidMakerHTextView.getWidth() - distWidth + gap) * percent);
        yLineLength = (int) (mLyricalVidMakerHTextView.getHeight() - (mLyricalVidMakerHTextView.getHeight() - distHeight + gap) * percent);

        p1.x = (mLyricalVidMakerHTextView.getWidth() / 2 + distWidth / 2 - gap + xLineWidth / 2) * percent;
        p1.y = (mLyricalVidMakerHTextView.getHeight() - distHeight) / 2;
        canvas.drawLine(p1.x - xLineLength, p1.y, p1.x, p1.y, linePaint);

        p2.x = (mLyricalVidMakerHTextView.getWidth() / 2 + distWidth / 2);
        p2.y = (mLyricalVidMakerHTextView.getHeight() / 2 + distHeight / 2 - gap + xLineWidth / 2) * percent;
        canvas.drawLine(p2.x, p2.y - yLineLength, p2.x, p2.y, linePaint);

        p3.x = mLyricalVidMakerHTextView.getWidth() - (mLyricalVidMakerHTextView.getWidth() / 2 + distWidth / 2 - gap + xLineWidth / 2) * percent;
        p3.y = (mLyricalVidMakerHTextView.getHeight() + distHeight) / 2;
        canvas.drawLine(p3.x + xLineLength, p3.y, p3.x, p3.y, linePaint);

        p4.x = (mLyricalVidMakerHTextView.getWidth() / 2 - distWidth / 2);
        p4.y = mLyricalVidMakerHTextView.getHeight() - (mLyricalVidMakerHTextView.getHeight() / 2 + distHeight / 2 + gap + xLineWidth / 2) * percent;
        canvas.drawLine(p4.x, p4.y + yLineLength, p4.x, p4.y, linePaint);


        xLineShort = (int) ((distWidth + gap) * (1 - percent));
        yLineShort = (int) ((distHeight + gap) * (1 - percent));

        p5.x = (mLyricalVidMakerHTextView.getWidth() / 2 + distWidth / 2);
        p5.y = (mLyricalVidMakerHTextView.getHeight() - distHeight) / 2;
        canvas.drawLine(p5.x - xLineShort, p5.y, p5.x, p5.y, linePaint);

        p6.x = (mLyricalVidMakerHTextView.getWidth() / 2 + distWidth / 2);
        p6.y = (mLyricalVidMakerHTextView.getHeight() / 2 + distHeight / 2);
        canvas.drawLine(p6.x, p6.y - yLineShort, p6.x, p6.y, linePaint);

        p7.x = mLyricalVidMakerHTextView.getWidth() - (mLyricalVidMakerHTextView.getWidth() / 2 + distWidth / 2 - gap);
        p7.y = (mLyricalVidMakerHTextView.getHeight() + distHeight) / 2;
        canvas.drawLine(p7.x + xLineShort, p7.y, p7.x, p7.y, linePaint);

        p8.x = (mLyricalVidMakerHTextView.getWidth() / 2 - distWidth / 2);
        p8.y = mLyricalVidMakerHTextView.getHeight() - (mLyricalVidMakerHTextView.getHeight() / 2 + distHeight / 2 - gap);
        canvas.drawLine(p8.x, p8.y + yLineShort, p8.x, p8.y, linePaint);

        canvas.drawText(mText, 0, mText.length(), startX, startY, mPaint);

    }
}
