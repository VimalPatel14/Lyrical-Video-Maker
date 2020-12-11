package com.photovideoeditormaker.lyricalvideostatusmaker.webservice;

public interface PVEMLyricalStatusMaker_ProgressReceiver {
    void onImageProgressUpdate(float i);

    void onProgressFinish(String str);

    void onVideoProgressUpdate(int i);
}
