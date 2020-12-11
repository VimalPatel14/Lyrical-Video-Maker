package com.github.hiteshsondhi88.libffmpeg;

import android.content.Context;
import android.os.Environment;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;

public   class FileUtils {

    public static File mSdCard = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    private static String app_name="ValentinePhotoVideoMaker";
    public static File APP_DIRECTORY = new File(mSdCard, app_name);
    public static final File TEMP_DIRECTORY = new File(APP_DIRECTORY, ".temp");
    public static final File TEMP_IMG_DIRECTORY = new File(APP_DIRECTORY, ".temp_image");

    public static final File TEMP_DIRECTORY_AUDIO = new File(APP_DIRECTORY, ".temp_audio");
    public static final File TEMP_VID_DIRECTORY = new File(TEMP_DIRECTORY, ".temp_vid");
    static final String ffmpegFileName = "ffmpeg";
    public static final File frameFile = new File(APP_DIRECTORY, ".frame.png");
    public static long mDeleteFileCount = 0;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;
    static class AnonymousClass1 extends Thread {
        private final File val$child;

        AnonymousClass1(File file) {
            this.val$child = file;
        }

        public void run() {
            FileUtils.deleteFile(this.val$child);
        }
    }

    static {
        if (!TEMP_DIRECTORY.exists()) {
            TEMP_DIRECTORY.mkdirs();
        }
        if (!TEMP_VID_DIRECTORY.exists()) {
            TEMP_VID_DIRECTORY.mkdirs();
        }
    }

   public static boolean copyBinaryFromAssetsToData(Context context, String fileNameFromAssets, String outputFileName) {

		// create files directory under /data/data/package name
		File filesDirectory = getFilesDirectory(context);

		InputStream is;
		try {
			is = context.getAssets().open(fileNameFromAssets);
			// copy ffmpeg file from assets to files dir
			final FileOutputStream os = new FileOutputStream(new File(filesDirectory, outputFileName));
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

			int n;
			while(EOF != (n = is.read(buffer))) {
				os.write(buffer, 0, n);
			}

            Util.close(os);
            Util.close(is);

			return true;
		} catch (IOException e) {
			Log.e("issue in coping binary from assets to data. ", e);
		}
        return false;
	}

	static File getFilesDirectory(Context context) {
		// creates files directory under data/data/package name
        return context.getFilesDir();
	}

    public static String getFFmpeg(Context context) {
        return getFilesDirectory(context).getAbsolutePath() + File.separator + FileUtils.ffmpegFileName;
    }

    static String getFFmpeg(Context context, Map<String,String> environmentVars) {
        String ffmpegCommand = "";
        if (environmentVars != null) {
            for (Map.Entry<String, String> var : environmentVars.entrySet()) {
                ffmpegCommand += var.getKey()+"="+var.getValue()+" ";
            }
        }
        ffmpegCommand += getFFmpeg(context);
        return ffmpegCommand;
    }
    public static String getDuration(long milliseconds) {
        String format = BuildConfig.FLAVOR;
        String secondsString = BuildConfig.FLAVOR;
        String minutesString = BuildConfig.FLAVOR;
        int hours = (int) (milliseconds / 3600000);
        int minutes = ((int) (milliseconds % 3600000)) / 60000;
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            format = hours + ":";
        }
        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = BuildConfig.FLAVOR + minutes;
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = BuildConfig.FLAVOR + seconds;
        }
        return format + minutesString + ":" + secondsString;
    }
    static String SHA1(String file) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return SHA1(is);
        } catch (IOException e) {
            Log.e(e);
        } finally {
            Util.close(is);
        }
        return null;
    }
    public static void appendLog(File parent, String text) {
        File logFile = parent;
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    public static void deleteTempDir() {
        for (File child : TEMP_DIRECTORY.listFiles()) {
            new AnonymousClass1(child).start();
        }
    }
    public static boolean deleteThemeDir(String theme) {
        return deleteFile(getImageDirectory(theme));
    }
    public static File getImageDirectory(String theme) {
        File imageDir = new File(TEMP_DIRECTORY, theme);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        return imageDir;
    }

    public static File getImageDirectory(String theme, int iNo) {
        File imageDir = new File(getImageDirectory(theme), String.format("IMG_%03d", new Object[]{Integer.valueOf(iNo)}));
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        return imageDir;
    }

    public FileUtils() {
        mDeleteFileCount = 0;
    }
    public static boolean deleteFile(File mFile) {
        int i = 0;
        boolean idDelete = false;
        if (mFile == null) {
            return false;
        }
        if (mFile.exists()) {
            if (mFile.isDirectory()) {
                File[] children = mFile.listFiles();
                if (children != null && children.length > 0) {
                    int length = children.length;
                    while (i < length) {
                        File child = children[i];
                        mDeleteFileCount += child.length();
                        idDelete = deleteFile(child);
                        i++;
                    }
                }
                mDeleteFileCount += mFile.length();
                idDelete = mFile.delete();
            } else {
                mDeleteFileCount += mFile.length();
                idDelete = mFile.delete();
            }
        }
        return idDelete;
    }

    public static boolean deleteFile(String s) {
        return deleteFile(new File(s));
    }
    static String SHA1(InputStream is) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            for (int read; (read = is.read(buffer)) != -1; ) {
                messageDigest.update(buffer, 0, read);
            }

            Formatter formatter = new Formatter();
            // Convert the byte to hex format
            for (final byte b : messageDigest.digest()) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(e);
        } catch (IOException e) {
            Log.e(e);
        } finally {
            Util.close(is);
        }
        return null;
    }
}