package com.photovideoeditormaker.lyricalvideostatusmaker.text.util;

import com.photovideoeditormaker.lyricalvideostatusmaker.text.animatetext.PVEMLyricalStatusMaker_CharDiffRes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hanks on 15-12-14.
 */
public class PVEMLyricalStatusMaker__ChactrUtls {

    public static List<PVEMLyricalStatusMaker_CharDiffRes> diff(CharSequence oldText, CharSequence newText) {

        List<PVEMLyricalStatusMaker_CharDiffRes> differentList = new ArrayList<>();
        Set<Integer> skip = new HashSet<>();

        for (int i = 0; i < oldText.length(); i++) {
            char c = oldText.charAt(i);
            for (int j = 0; j < newText.length(); j++) {
                if (!skip.contains(j) && c == newText.charAt(j)) {
                    skip.add(j);
                    PVEMLyricalStatusMaker_CharDiffRes different = new PVEMLyricalStatusMaker_CharDiffRes();
                    different.c = c;
                    different.fromIndex = i;
                    different.moveIndex = j;
                    differentList.add(different);
                    break;
                }
            }
        }
        return differentList;
    }

    public static int needMove(int index, List<PVEMLyricalStatusMaker_CharDiffRes> differentList) {
        for (PVEMLyricalStatusMaker_CharDiffRes different : differentList) {
            if (different.fromIndex == index) {
                return different.moveIndex;
            }
        }
        return -1;
    }

    public static boolean stayHere(int index, List<PVEMLyricalStatusMaker_CharDiffRes> differentList) {
        for (PVEMLyricalStatusMaker_CharDiffRes different : differentList) {
            if (different.moveIndex == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    public static float getOffset(int from, int move, float progress, float startX, float oldStartX, float[] gaps, float[] oldGaps) {

        float dist = startX;
        for (int i = 0; i < move; i++) {
            dist += gaps[i];
        }

        float cur = oldStartX;
        for (int i = 0; i < from; i++) {
            cur += oldGaps[i];
        }

        return cur + (dist - cur) * progress;

    }

}
