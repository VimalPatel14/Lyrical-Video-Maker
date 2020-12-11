package com.photovideoeditormaker.lyricalvideostatusmaker.model;

import android.net.Uri;

public class PVEMLyricalStatusMaker_AllLstFile {
    private int Folder_Size;
    private String bucketId;
    private String bucketName;
    private int count;
    private int imgId;
    private Uri imgUri;

    public PVEMLyricalStatusMaker_AllLstFile(String str, String str2, int i, int i2, Uri uri, int i3) {
        this.bucketId = str;
        this.bucketName = str2;
        this.count = i;
        this.imgId = i2;
        this.imgUri = uri;
        this.Folder_Size = i3;
    }

    public String getbucketId() {
        return this.bucketId;
    }

    public String getFile_bucketName() {
        return this.bucketName;
    }

    public int getFile_count() {
        return this.count;
    }

    public int getFile_imgId() {
        return this.imgId;
    }

    public Uri getFile_imgUri() {
        return this.imgUri;
    }

    public int get_Folder_Size() {
        return this.Folder_Size;
    }
}
