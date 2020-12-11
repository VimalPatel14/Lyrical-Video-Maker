package com.photovideoeditormaker.lyricalvideostatusmaker.utill;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;

import com.photovideoeditormaker.lyricalvideostatusmaker.webservice.PVEMLyricalStatusMaker_ProgressReceiver;
import com.photovideoeditormaker.lyricalvideostatusmaker.webservice.PVEMLyricalStatusMaker_THEME;
import com.photovideoeditormaker.lyricalvideostatusmaker.data.PVEMLyricalStatusMaker_ImgData;
import com.photovideoeditormaker.lyricalvideostatusmaker.data.PVEMLyricalStatusMaker_AudioData;

import java.util.ArrayList;
import java.util.HashMap;

public class PVEMLyricalStatusMaker_PrefMnger extends Application {
    public static int VIDEO_HEIGHT = 1280;
    public static int VIDEO_WIDTH = 720;
    private static PVEMLyricalStatusMaker_PrefMnger instance = null;
    public static boolean isBreak = false;
    public static int track_duration;
    static Editor prefEditor;
    static SharedPreferences preferences;
    public static HashMap<String, ArrayList<PVEMLyricalStatusMaker_ImgData>> allAlbum;
    private ArrayList<String> allFolder;
    public boolean isEditModeEnable = false;
    public static boolean isFromSdCardAudio = false;
    public int min_pos = Integer.MAX_VALUE;
    public static PVEMLyricalStatusMaker_AudioData musicData;
    public static PVEMLyricalStatusMaker_ProgressReceiver onProgressReceiver;
    public static float second = 2.0f;
    private String selectedFolderId = "";
    public static ArrayList<String> selectedImages = new ArrayList();
    public static PVEMLyricalStatusMaker_THEME selectedTheme = PVEMLyricalStatusMaker_THEME.Shine;
    public static ArrayList<String> videoImages = new ArrayList();
    public static ArrayList<PVEMLyricalStatusMaker_ImgData> arrayList;

    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("videomaker", 0);
        prefEditor = preferences.edit();
        prefEditor.commit();
        instance = this;
    }

    public void onTerminate() {
        super.onTerminate();
    }

    public void setMusicData(PVEMLyricalStatusMaker_AudioData mRYTCHER_MusicData) {
        this.isFromSdCardAudio = false;
        this.musicData = mRYTCHER_MusicData;
    }

    public PVEMLyricalStatusMaker_AudioData getMusicData() {
        return this.musicData;
    }

    public static void setBackgroundColor(int i) {
        prefEditor.putInt("backgrondcolor", i);
        prefEditor.commit();
    }

    public static int getBackgroundColor() {
        return preferences.getInt("backgrondcolor", -16777216);
    }

    public static void setVerion(int i) {
        prefEditor.putInt("versioncode", i);
        prefEditor.commit();
    }

    public float getSecond() {
        return this.second;
    }

    public void setSecond(float f) {
        this.second = f;
    }

    public static int getVersion() {
        return preferences.getInt("versioncode", 19);
    }

    public static void setMusicExtension(String str) {
        prefEditor.putString("musicextension", str);
        prefEditor.commit();
    }

    public static String getMusicExtension() {
        return preferences.getString("musicextension", "");
    }

    public static void setisMusic(Boolean bool) {
        prefEditor.putBoolean("ismusic", bool.booleanValue());
        prefEditor.commit();
    }

    public static Boolean getisMusic() {
        return Boolean.valueOf(preferences.getBoolean("ismusic", false));
    }

    public static void setCounter(int i) {
        prefEditor.putInt("counter", i);
        prefEditor.commit();
    }

    public static int getCounter() {
        return preferences.getInt("counter", 0);
    }

    public static void setCropIndex(int i) {
        prefEditor.putInt("cropindex", i);
        prefEditor.commit();
    }

    public static int getCropIndex() {
        return preferences.getInt("cropindex", 0);
    }

    public static void setIndexId(int i) {
        prefEditor.putInt("indexid", i);
        prefEditor.commit();
    }

    public static String getImageMode() {
        return preferences.getString("imgmode", "portrait");
    }

    public static void setImageMode(String str) {
        prefEditor.putString("imgmode", str);
        prefEditor.commit();
    }

    public static int getIndexId() {
        return preferences.getInt("indexid", 0);
    }

    public static String getDropAuthToken() {
        return preferences.getString("authtoken", "");
    }

    public static void setDropAuthToken(String str) {
        prefEditor.putString("authtoken", str);
        prefEditor.commit();
    }

    public static void setAuth(Boolean bool) {
        prefEditor.putBoolean("useoath", bool.booleanValue());
        prefEditor.commit();
    }

    public static Boolean getAuth() {
        return Boolean.valueOf(preferences.getBoolean("useoath", false));
    }

    public static void setDropboxUserName(String str) {
        prefEditor.putString("dropboxusername", str);
        prefEditor.commit();
    }

    public static String getDropboxUserName() {
        return preferences.getString("dropboxusername", "");
    }

    public static void setInstaUserName(String str) {
        prefEditor.putString("instausername", str);
        prefEditor.commit();
    }

    public static String getInstaUserName() {
        return preferences.getString("instausername", "");
    }

    public static void setFbUserName(String str) {
        prefEditor.putString("fbusername", str);
        prefEditor.commit();
    }

    public static String getFbUserName() {
        return preferences.getString("fbusername", "");
    }

    public static void setRateNoCounter(int i) {
        prefEditor.putInt("ratecounter", i);
        prefEditor.commit();
    }

    public static int getRateNoCounter() {
        return preferences.getInt("ratecounter", 0);
    }

    public static void setRateYesCounter(int i) {
        prefEditor.putInt("rateyescounter", i);
        prefEditor.commit();
    }

    public static int getRateYesCounter() {
        return preferences.getInt("rateyescounter", 0);
    }

    public static void setVideoCounter(int i) {
        prefEditor.putInt("vcounter", i);
        prefEditor.commit();
    }

    public static int getVideoCounter() {
        return preferences.getInt("vcounter", 0);
    }

    public static void setAtOnce(boolean z) {
        prefEditor.putBoolean("atonce", z);
        prefEditor.commit();
    }

    public static Boolean getAtOnce() {
        return Boolean.valueOf(preferences.getBoolean("atonce", false));
    }

    public static void setThemeName(String str) {
        prefEditor.putString("themename", str);
        prefEditor.commit();
    }

    public static String getThemeName() {
        return preferences.getString("themename", "");
    }

    public static void setShowCaseCounter(int i) {
        prefEditor.putInt("showcase", i);
        prefEditor.commit();
    }

    public static int getShowCaseCounter() {
        return preferences.getInt("showcase", 0);
    }

    public static PVEMLyricalStatusMaker_PrefMnger getInstance() {
        return instance;
    }

    public void initArray() {
        this.videoImages = new ArrayList();
    }

    public ArrayList<String> getSelectedImages() {
        return this.selectedImages;
    }

    public void clearAllSelection() {
        this.videoImages.clear();
        this.allAlbum = null;
        getSelectedImages().clear();
        System.gc();
        getFolderList();
    }

    public void setSelectedFolderId(String str) {
        this.selectedFolderId = str;
    }

    public String getSelectedFolderId() {
        return this.selectedFolderId;
    }

    public void setOnProgressReceiver(PVEMLyricalStatusMaker_ProgressReceiver mRYTCHER_OnProgressReceiver) {
        this.onProgressReceiver = mRYTCHER_OnProgressReceiver;
    }

    public PVEMLyricalStatusMaker_ProgressReceiver getOnProgressReceiver() {
        return this.onProgressReceiver;
    }

    public void setCurrentTheme(String str) {
        Editor edit = getSharedPreferences("theme", 0).edit();
        edit.putString("current_theme", str);
        edit.commit();
    }

    public String getCurrentTheme() {
        return getSharedPreferences("theme", 0).getString("current_theme", PVEMLyricalStatusMaker_THEME.Shine.toString());
    }

    public void getFolderList() {
        this.allFolder = new ArrayList();
        this.allAlbum = new HashMap();
        Cursor query = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "bucket_display_name", "bucket_id", "datetaken"}, null, null, "bucket_display_name DESC");
        if (query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("bucket_display_name");
            int columnIndex2 = query.getColumnIndex("bucket_id");
            setSelectedFolderId(query.getString(columnIndex2));
            do {
                PVEMLyricalStatusMaker_ImgData mRYTCHER_ImageData = new PVEMLyricalStatusMaker_ImgData();
                mRYTCHER_ImageData.imagePath = query.getString(query.getColumnIndex("_data"));
                if (!mRYTCHER_ImageData.imagePath.endsWith(".gif")) {
                    String string = query.getString(columnIndex);
                    String string2 = query.getString(columnIndex2);
                    if (!this.allFolder.contains(string2)) {
                        this.allFolder.add(string2);
                    }
                    ArrayList arrayList = (ArrayList) this.allAlbum.get(string2);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    mRYTCHER_ImageData.folderName = string;
                    arrayList.add(mRYTCHER_ImageData);
                    this.allAlbum.put(string2, arrayList);
                }
            } while (query.moveToNext());
        }
    }

    public static boolean isMyServiceRunning(Context context, Class<?> cls) {
        for (RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public static ArrayList<PVEMLyricalStatusMaker_ImgData> getImageByAlbum(String folderId) {
        arrayList = (ArrayList) getAllAlbum().get(folderId);
        if (arrayList == null) {
            return new ArrayList();
        }
        return arrayList;
    }


    public static HashMap<String, ArrayList<PVEMLyricalStatusMaker_ImgData>> getAllAlbum() {
        return allAlbum;
    }
}
