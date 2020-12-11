package com.photovideoeditormaker.lyricalvideostatusmaker.model;

import android.net.Uri;

public class PVEMLyricalStatusMaker_GridFile {
    public Uri imgUri;

    public PVEMLyricalStatusMaker_GridFile(Uri uri) {
        this.imgUri = uri;
    }

    public Uri getFile_imgUri() {
        return this.imgUri;
    }
}
