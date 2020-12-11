package com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext;

import android.graphics.Canvas;
import android.util.AttributeSet;

import com.photovideoeditormaker.lyricalvideostatusmaker.text.PVEMLyricalStatusMaker_TextViewV;


/**
 * interface used in HTextView
 * Created by hanks on 15-12-14.
 */
public interface PVEMLyricalStatusMaker__IVText {
    void init(PVEMLyricalStatusMaker_TextViewV lyricalVidMakerHTextView, AttributeSet attrs, int defStyle);

    void animateText(CharSequence text);

    void onDraw(Canvas canvas);

    void reset(CharSequence text);
}
