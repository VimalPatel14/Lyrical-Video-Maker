package com.photovideoeditormaker.lyricalvideostatusmaker.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMPVEMLyricalStatusMaker__EvaporateTextTextV;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMPVEMLyricalStatusMaker_FallTextTextV;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMLyricalStatusMaker__IVText;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMPVEMLyricalStatusMaker_LineTextTextV;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PixelateTextPVEMLyricalStatusMaker;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMPVEMLyricalStatusMaker_RainBowTextTextV;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMPVEMLyricalStatusMaker_ScaleTextTextV;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMPVEMLyricalStatusMaker_SparkleTextTextV;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMPVEMLyricalStatusMaker_TyperTextTextV;


/**
 * Animate TextView
 */
public class PVEMLyricalStatusMaker_TextViewV extends TextView {

    private PVEMLyricalStatusMaker__IVText mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_ScaleTextTextV();
    private AttributeSet attrs;
    private int defStyle;
    private int animateType;

    public PVEMLyricalStatusMaker_TextViewV(Context context) {
        super(context);
        init(null, 0);
    }

    public PVEMLyricalStatusMaker_TextViewV(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PVEMLyricalStatusMaker_TextViewV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        this.attrs = attrs;
        this.defStyle = defStyle;

        // Get the attributes array
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PVEMLyricalStatusMaker_TextViewV);
        animateType = typedArray.getInt(R.styleable.PVEMLyricalStatusMaker_TextViewV_animateType, 0);
        final String fontAsset = typedArray.getString(R.styleable.PVEMLyricalStatusMaker_TextViewV_fontAsset);

        if (!this.isInEditMode()) {
            // Set custom typeface
            if (fontAsset != null && !fontAsset.trim().isEmpty()) {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), fontAsset));
            }
        }


        switch (animateType) {
            case 0:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_ScaleTextTextV();
                break;
            case 1:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker__EvaporateTextTextV();
                break;
            case 2:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_FallTextTextV();
                break;
            case 3:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_SparkleTextTextV();
                break;
            case 4:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_RainBowTextTextV();
                break;
            case 5:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_LineTextTextV();
                break;
            case 6:
                mLyricalVidMakerIHText = new PixelateTextPVEMLyricalStatusMaker();
                break;
            case 7:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_TyperTextTextV();
                break;
            case 8:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_RainBowTextTextV();
                break;
            //            <enum name="scale" value="0"/>
            //            <enum name="evaporate" value="1"/>
            //            <enum name="fall" value="2"/>
            //            <enum name="sparkle" value="3"/>
            //            <enum name="anvil" value="4"/>
            //            <enum name="line" value="5"/>
            //            <enum name="pixelate" value="6"/
        }
        typedArray.recycle();
        initHText(attrs, defStyle);
    }

    private void initHText(AttributeSet attrs, int defStyle) {
        mLyricalVidMakerIHText.init(PVEMLyricalStatusMaker_TextViewV.this, attrs, defStyle);
    }

    public void animateText(CharSequence text) {
        mLyricalVidMakerIHText.animateText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mLyricalVidMakerIHText.onDraw(canvas);
    }

    public void reset(CharSequence text) {
        mLyricalVidMakerIHText.reset(text);
    }

    public void setAnimateType(PVEMLyricalStatusMaker__TextViewVType type) {
        switch (type) {
            case SCALE:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_ScaleTextTextV();
                break;
            case EVAPORATE:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker__EvaporateTextTextV();
                break;
            case FALL:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_FallTextTextV();
                break;
            case PIXELATE:
                mLyricalVidMakerIHText = new PixelateTextPVEMLyricalStatusMaker();
                break;
            case ANVIL:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_RainBowTextTextV();
                break;
            case SPARKLE:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_SparkleTextTextV();
                break;
            case LINE:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_LineTextTextV();
                break;
            case TYPER:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_TyperTextTextV();
                break;
            case RAINBOW:
                mLyricalVidMakerIHText = new PVEMPVEMLyricalStatusMaker_RainBowTextTextV();
                break;
        }

        initHText(attrs, defStyle);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        state.animateType = animateType;
        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(state);
        animateType = ss.animateType;
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int animateType;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            animateType = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(animateType);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }

}
