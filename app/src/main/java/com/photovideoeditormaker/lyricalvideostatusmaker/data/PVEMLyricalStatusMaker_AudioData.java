package com.photovideoeditormaker.lyricalvideostatusmaker.data;

public class PVEMLyricalStatusMaker_AudioData {
    public int counter;
    public long track_Id;
    public String track_Title;
    public String track_data;
    public String track_displayName;
    public long track_duration;

    public long getTrack_Id() {
        return this.track_Id;
    }

    public void setTrack_Id(long j) {
        this.track_Id = j;
    }

    public long getTrack_duration() {
        return this.track_duration;
    }

    public void setTrack_duration(long j) {
        this.track_duration = j;
    }

    public String getTrack_Title() {
        return this.track_Title;
    }

    public void setTrack_Title(String str) {
        this.track_Title = str;
    }

    public String getTrack_data() {
        return this.track_data;
    }

    public void setTrack_data(String str) {
        this.track_data = str;
    }

    public String getTrack_displayName() {
        return this.track_displayName;
    }

    public void setTrack_displayName(String str) {
        this.track_displayName = str;
    }
}
