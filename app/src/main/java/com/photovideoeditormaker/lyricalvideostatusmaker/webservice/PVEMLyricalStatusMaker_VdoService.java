package com.photovideoeditormaker.lyricalvideostatusmaker.webservice;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;


import com.github.hiteshsondhi88.libffmpeg.FileUtils;
import com.github.hiteshsondhi88.libffmpeg.Util;
import com.photovideoeditormaker.lyricalvideostatusmaker.activity.PVEMLyricalStatusMaker_SongLyricsActivity;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.utill.PVEMLyricalStatusMaker_PrefMnger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PVEMLyricalStatusMaker_VdoService extends IntentService {
    public static final int NOTIFICATION_ID = 1001;
    public static long mDeleteFileCount;
    public static String videoname;
    PVEMLyricalStatusMaker_PrefMnger application;
    private File audioFile;
    private File audioIp;
    int last;

    String timeRe;
    private float toatalSecond;


    class AnonymousClass1 implements Runnable {
        final String val$videoPath;

        AnonymousClass1(String str) {
            this.val$videoPath = str;
        }

        public void run() {
            PVEMLyricalStatusMaker_ProgressReceiver onProgressReceiver = PVEMLyricalStatusMaker_VdoService.this.application.getOnProgressReceiver();
            if (onProgressReceiver != null) {
                onProgressReceiver.onVideoProgressUpdate(100);
                onProgressReceiver.onProgressFinish(this.val$videoPath);
            }
        }
    }


    private void createVideo() {
        long startTime = System.currentTimeMillis();

        toatalSecond = ((PVEMLyricalStatusMaker_PrefMnger.second
                * PVEMLyricalStatusMaker_PrefMnger.selectedImages.size() - 1));
        if (PVEMLyricalStatusMaker_PrefMnger.musicData != null) {

            joinAudio();
        }

        while (!PVEMLyricalStatusMaker_ImgService.isImageComplate) {
            continue;
        }

        Log.e("createVideo", "video create start");
        String folderPath = Environment.getExternalStorageDirectory()
                .getPath()
                + "/"
                + getResources()
                .getString(R.string.app_name);
        File f = new File(folderPath);
        if (!f.exists())
            f.mkdir();

        File videoIp = new File(f, "video.txt");
//        videoIp.delete();

        Log.e("vimal", PVEMLyricalStatusMaker_PrefMnger.selectedImages+"");

        for (int i = 0; i < PVEMLyricalStatusMaker_PrefMnger.selectedImages.size(); i++) {
            appendVideoLog(String.format("file '%s'",
                    PVEMLyricalStatusMaker_PrefMnger.selectedImages.get(i)));
        }
        File output_video = new File(folderPath + "/FinalVideos");
        if (!output_video.exists())
            output_video.mkdir();
        final String videoPath = new File(output_video,
                getVideoName()).getAbsolutePath();
        Log.d("video122_path", videoPath);

        String[] inputCode;
//        if (PVEMLyricalStatusMaker_PrefMnger.musicData != null) {

//            inputCode = new String[]{FileUtils.getFFmpeg(this), "-r",
//                    "" + ((15 * 2) / PVEMLyricalStatusMaker_PrefMnger.second), "-f", "concat",
//                    "-safe", "0", "-i", videoIp.getAbsolutePath(), "-i",
//                    audioFile.getAbsolutePath(), "-strict", "experimental",
//                    "-r", "30", "-t", "" + toatalSecond, "-c:v", "libx264",
//                    "-preset", "ultrafast",
//
//                    "-pix_fmt", "yuv420p", "-ac", "2", videoPath};



//        } else {
            inputCode = new String[]{FileUtils.getFFmpeg(this), "-safe", "0",
                    "-r","" + ((15 * 1.18) / PVEMLyricalStatusMaker_PrefMnger.second), "-f", "concat",
                    "-i", videoIp.getAbsolutePath(), "-i",
                    PVEMLyricalStatusMaker_SongLyricsActivity.audioPath, "-r", "30", "-c:v",
                    "libx264", "-preset", "ultrafast", "-pix_fmt", "yuv420p",
                    videoPath};
//        }

        System.gc();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(inputCode);
            while (true) {
                if (Util.isProcessCompleted(process)) {
                    break;
                }
                String line;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                while ((line = reader.readLine()) != null) {

                    Log.e("process123", line);
                    final int incr = durationToprogtess(line);// (i*100)/application.videoImages.size();

                    int p = 25 + (int) (75f * incr / 100f);
//                    mBuilder.setProgress(100, p, false);
//                    mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Util.destroyProcess(process);
        }
//

        try {
            File videoFile = new File(videoPath);

            long fileSize = videoFile.length();
            String mimeType = "video/mp4";

            //	String artist = "" + getResources().getText(R.string.artist_name);

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DATA, videoPath);
            values.put(MediaStore.MediaColumns.SIZE, fileSize);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);

            //values.put(VideoColumns.ARTIST, artist);
            values.put(MediaStore.Video.VideoColumns.DURATION, toatalSecond * 1000l);

            // Insert it into the database
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(videoPath);
            getContentResolver().insert(uri, values);

        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(videoPath))));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        application.clearAllSelection();

        PVEMLyricalStatusMaker_PrefMnger.videoImages.clear();
        // allAlbum = null;
        PVEMLyricalStatusMaker_PrefMnger.selectedImages.clear();

        //	buildNotification(videoPath);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                PVEMLyricalStatusMaker_ProgressReceiver receiver = PVEMLyricalStatusMaker_PrefMnger.onProgressReceiver;
                if (receiver != null) {
                    receiver.onVideoProgressUpdate(100);
                    receiver.onProgressFinish(videoPath);
                }
            }
        });
        deleteTempDir();

        stopSelf();
    }

    public PVEMLyricalStatusMaker_VdoService() {
        this(PVEMLyricalStatusMaker_VdoService.class.getName());
    }

    public PVEMLyricalStatusMaker_VdoService(String str) {
        super(str);
        this.timeRe = "\\btime=\\b\\d\\d:\\d\\d:\\d\\d.\\d\\d";
        this.last = 0;
    }

    protected void onHandleIntent(Intent intent) {
        this.application = PVEMLyricalStatusMaker_PrefMnger.getInstance();
        createVideo();
    }

    private int durationToprogtess(String str) {
        Matcher matcher = Pattern.compile(this.timeRe).matcher(str);
        if (!TextUtils.isEmpty(str)) {
            if (str.contains("time=")) {
                int i = 0;
                while (matcher.find()) {
                    String group = matcher.group();
                    String[] split = group.substring(group.lastIndexOf(61) + 1).split(":");
                    float floatValue = ((Float.valueOf(split[0]).floatValue() * ((float) 3600)) + (Float.valueOf(split[1]).floatValue() * ((float) 60))) + Float.valueOf(split[2]).floatValue();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("totalSecond:");
                    stringBuilder.append(floatValue);
                    Log.e("time", stringBuilder.toString());
                    i = (int) ((100.0f * floatValue) / this.toatalSecond);
                    updateInMili(floatValue);
                }
                this.last = i;
                return i;
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("not contain time ");
        stringBuilder2.append(str);
        Log.e("time", stringBuilder2.toString());
        return this.last;
    }

    private void updateInMili(float f) {
        final double d = (((double) f) * 100.0d) / ((double) this.toatalSecond);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                PVEMLyricalStatusMaker_ProgressReceiver onProgressReceiver = PVEMLyricalStatusMaker_VdoService.this.application.getOnProgressReceiver();
                if (onProgressReceiver != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("progress __");
                    stringBuilder.append(d);
                    Log.e("timeToatal", stringBuilder.toString());
                    onProgressReceiver.onVideoProgressUpdate((int) d);
                }
            }
        });
    }

    private void joinAudio() {
        String folderPath = Environment.getExternalStorageDirectory()
                .getPath()
                + "/"
                + getResources()
                .getString(R.string.app_name);
        File f = new File(folderPath);
        if (!f.exists())
            f.mkdir();
        audioIp = new File(f, "audio.txt");
        audioFile = new File(f, "audio.mp3");
        audioFile.delete();
        audioIp.delete();
        // appendLog("===============================================");
        for (int d = 0;/* d<application.getSelectedImages().size() */ ; d++) {
            appendAudioLog(String.format("file '%s'",
                    PVEMLyricalStatusMaker_PrefMnger.musicData.track_data));



            if (PVEMLyricalStatusMaker_PrefMnger.isFromSdCardAudio) {
                if (toatalSecond * 1000 <= PVEMLyricalStatusMaker_PrefMnger.musicData.track_duration
                        * d) {
                    break;
                }
            } else {
                if (toatalSecond * 1000 <= PVEMLyricalStatusMaker_PrefMnger.track_duration
                        * d) {
                    break;
                }
            }
        }

        // appendLog("Joid Audio");
        // appendLog("===============================================");
        String[] inputCode = new String[]{FileUtils.getFFmpeg(this), "-f",
                "concat", "-safe", "0", "-i", audioIp.getAbsolutePath(),
                // "-t",""+toatalSecond,
                "-c", "copy", "-preset", "ultrafast", "-ac", "2",
                audioFile.getAbsolutePath()};

        Process process = null;
        try {

            process = Runtime.getRuntime().exec(inputCode);
            while (true) {
                if (Util.isProcessCompleted(process)) {
                    break;
                }
                String line;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    Log.i("audio", line);
                    // appendLog(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("audio", "io", e);
        } finally {
            Util.destroyProcess(process);
        }
    }

    private String getVideoName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MMM_dd_HH_mm_ss", Locale.ENGLISH);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("video_");
        stringBuilder.append(simpleDateFormat.format(new Date()));
        stringBuilder.append(".mp4");
        return stringBuilder.toString();
    }

    public void appendVideoLog(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        File file = new File(stringBuilder.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, "video.txt");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("File append ");
        stringBuilder2.append(str);
        Log.d("FFMPEG", stringBuilder2.toString());
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2, true));
            bufferedWriter.append(str);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void appendVideoLogtxt(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        File file = new File(stringBuilder.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, "video1.txt");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("File append ");
        stringBuilder2.append(str);
        Log.d("FFMPEG", stringBuilder2.toString());
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2, true));
            bufferedWriter.append(str);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void appendAudioLog(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        File file = new File(stringBuilder.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, "audio.txt");
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2, true));
            bufferedWriter.append(str);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void deleteTempDir() {
        int length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        stringBuilder.append("/temp");
        File file = new File(stringBuilder.toString());
        int i = 0;
        if (file.exists()) {
            for (final File file2 : file.listFiles()) {
                new Thread() {
                    public void run() {
                        PVEMLyricalStatusMaker_VdoService.deleteFile(file2);
                    }
                }.start();
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        stringBuilder.append("/temp2");
        file = new File(stringBuilder.toString());
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            length = listFiles.length;
            while (i < length) {
                final File file3 = listFiles[i];
                new Thread() {
                    public void run() {
                        PVEMLyricalStatusMaker_VdoService.deleteFile(file3);
                    }
                }.start();
                i++;
            }
        }
    }

    public static boolean deleteFile(File mFile) {
        boolean idDelete = false;
        if (mFile == null)
            return idDelete;

        if (mFile.exists()) {
            if (mFile.isDirectory()) {
                File[] children = mFile.listFiles();
                if (children != null && children.length > 0) {
                    for (File child : children) {
                        mDeleteFileCount += (long) child.length();
                        idDelete = deleteFile(child);
                    }
                }
                mDeleteFileCount += (long) mFile.length();
                idDelete = mFile.delete();
            } else {
                mDeleteFileCount += (long) mFile.length();
                idDelete = mFile.delete();
            }
        }
        return idDelete;
    }
}
