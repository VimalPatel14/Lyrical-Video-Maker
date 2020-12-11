package com.photovideoeditormaker.lyricalvideostatusmaker.data;

import android.text.TextUtils;

public class PVEMLyricalStatusMaker_ImgData {
    public String folderName;
    public long id;
    public String imageAlbum;
    public int imageCount = 0;
    public String imagePath;
    public boolean isSupported = true;

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String str) {
        this.imagePath = str;
    }

    public String getImageAlbum() {
        return this.imageAlbum;
    }

    public void setImageAlbum(String str) {
        this.imageAlbum = str;
    }

    public int getImageCount() {
        return this.imageCount;
    }

    public void setImageCount(int i) {
        this.imageCount = i;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String toString() {
        if (TextUtils.isEmpty(this.imagePath)) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImageData { imagePath=");
        stringBuilder.append(this.imagePath);
        stringBuilder.append(",folderName=");
        stringBuilder.append(this.folderName);
        stringBuilder.append(",imageCount=");
        stringBuilder.append(this.imageCount);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
