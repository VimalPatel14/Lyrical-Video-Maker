package com.photovideoeditormaker.lyricalvideostatusmaker.model;

public class LyricalVidMaker_VideoModel {
    private String mp3_content_path;
    private String mp3_name;
    private String mp3_original_path;

    public LyricalVidMaker_VideoModel(String str, String str2, String str3) {
        this.mp3_name = str;
        this.mp3_content_path = str2;
        this.mp3_original_path = str3;
    }

    public String getName() {
        return this.mp3_name;
    }

    public String getContentPath() {
        return this.mp3_content_path;
    }

    public String get_Original_Path() {
        return this.mp3_original_path;
    }
}
