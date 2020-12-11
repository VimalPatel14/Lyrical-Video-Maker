package com.photovideoeditormaker.lyricalvideostatusmaker.webservice;

import android.util.Log;

import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.data.PVEMLyricalStatusMaker_FinalMaskBmp.EFFECT;
import java.util.ArrayList;

public enum PVEMLyricalStatusMaker_THEME {
    Shine("Shine") {
        public int getThemeDrawable() {
            return R.mipmap.ic_launcher;
        }

        public int getThemeMusic() {
            return R.raw.door_tumse_rahun;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList arrayList = new ArrayList();

            Log.e("vimal","Shine-Yes");

            arrayList.add(EFFECT.CIRCLE_OUT);
            arrayList.add(EFFECT.HORIZONTAL_RECT);
            arrayList.add(EFFECT.CIRCLE_LEFT_BOTTOM);
            arrayList.add(EFFECT.VERTICAL_RECT);
            arrayList.add(EFFECT.DIAMOND_IN);
            arrayList.add(EFFECT.CIRCLE_RIGHT_BOTTOM);
            arrayList.add(EFFECT.ECLIPSE_IN);
            arrayList.add(EFFECT.HORIZONTAL_COLUMN_DOWNMASK);
            arrayList.add(EFFECT.SQUARE_IN);
            arrayList.add(EFFECT.WIND_MILL);
            arrayList.add(EFFECT.SQUARE_OUT);
            arrayList.add(EFFECT.FOUR_TRIANGLE);
            arrayList.add(EFFECT.OPEN_DOOR);
            arrayList.add(EFFECT.LEAF);
            return arrayList;
        }
    },
    CIRCLE_IN("Circle In") {
        public int getThemeDrawable() {
            return R.mipmap.ic_launcher;
        }

        public int getThemeMusic() {
            return R.raw.ek_mulaqat_ho;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList arrayList = new ArrayList();
            arrayList.add(EFFECT.CIRCLE_IN);
            return arrayList;
        }
    },
    Roll2D_BT("Roll2D_BT") {
        public int getThemeDrawable() {
            return R.mipmap.ic_launcher;
        }

        public int getThemeMusic() {
            return R.raw.kahi_kisi_bhi_gali_me_jau_me;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList arrayList = new ArrayList();
            arrayList.add(EFFECT.DIAMOND_IN);
            arrayList.add(EFFECT.ECLIPSE_IN);
            arrayList.add(EFFECT.SQUARE_IN);
            arrayList.add(EFFECT.LEAF);
            return arrayList;
        }
    };
    
    String f16008t;

    public abstract ArrayList<EFFECT> getTheme();

    public abstract int getThemeDrawable();

    public abstract int getThemeMusic();

    private PVEMLyricalStatusMaker_THEME(String str) {
        this.f16008t = "";
        this.f16008t = str;
    }

    public String getName() {
        return this.f16008t;
    }
}
