package com.photovideoeditormaker.lyricalvideostatusmaker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class PVEMLyricalStatusMaker_PgrVier extends HorizontalScrollView {
    private OnLayoutListener mListener;

    private interface OnLayoutListener {
        void onLayout();
    }

    public PVEMLyricalStatusMaker_PgrVier(Context context) {
        super(context);
    }

    public PVEMLyricalStatusMaker_PgrVier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void fullScrollOnLayout(final int i) {
        this.mListener = new OnLayoutListener() {
            public void onLayout() {
                fullScroll(i);
                mListener = null;
            }
        };
    }

    @SuppressLint({"WrongCall"})
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mListener != null) {
            this.mListener.onLayout();
        }
    }
}
