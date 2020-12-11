package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.lyricalvideostatusmaker.BuildConfig;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;

import java.io.File;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class PVEMLyricalStatusMaker_VideoPreviewActivity extends AppCompatActivity implements OnSeekBarChangeListener {
    private static final int RESULT_FROM_SHARE = 99;
    ImageView ShareVideo;
    ImageView btnBack;
    ImageView btnDeleteVideo;
    ImageView btnPlayVideo;
    int duration = 0;
    ImageView flVideoView;
    Handler handler = new Handler();
    boolean isFromlist = false;
    boolean isPlay = true;
    ImageView ivScreen;
    RelativeLayout layouttopbar;
    private AdView mAdView;
    private Runnable onEverySecond = new Runnable() {
        public void run() {
            if (seekVideo != null) {
                seekVideo.setProgress(videoview.getCurrentPosition());
                try {
                    TextView textView = tvStartVideo;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(PVEMLyricalStatusMaker_VideoPreviewActivity.formatTimeUnit((long) (seekVideo.getProgress() + 1)));
                    textView.setText(stringBuilder.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (videoview.isPlaying()) {
                seekVideo.postDelayed(onEverySecond, 1000);
            }
        }
    };
    OnClickListener onclickdeletevideo = new OnClickListener() {
        public void onClick(View view) {
            if (videoview != null) {
                videoview.pause();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(PVEMLyricalStatusMaker_VideoPreviewActivity.this);
            builder.setMessage("Are you sure to delete this video?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    File file = new File(videoPath);
                    if (file.exists()) {
                        if (file.exists()) {
                            file.delete();
                        }
                        MediaScannerConnection.scanFile(PVEMLyricalStatusMaker_VideoPreviewActivity.this, new String[]{videoPath}, null, null);
                        pd = new ProgressDialog(PVEMLyricalStatusMaker_VideoPreviewActivity.this);
                        pd.setMessage("Deleting Video...");
                        pd.show();
                        ProgressDialog progressDialog = pd;
                        ProgressDialog progressDialog2 = pd;
                        progressDialog.setProgressStyle(0);
                        pd.setCancelable(false);
                        pd.setCanceledOnTouchOutside(false);
                        pd.getVolumeControlStream();
                        handler.postDelayed(runnable, 500);
                    }
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
    };
    OnClickListener onclickplayvideo = new OnClickListener() {
        public void onClick(View view) {
            if (isPlay) {
                videoview.pause();
                isPlay = false;
                handler.removeCallbacks(onEverySecond);
                flVideoView.setBackgroundResource(R.drawable.ic_play_button);
            } else {
                videoview.seekTo(seekVideo.getProgress());
                videoview.resume();
                isPlay = true;
                videoview.setVisibility(View.VISIBLE);
                handler.postDelayed(onEverySecond, 1000);
                ivScreen.setVisibility(View.GONE);
                flVideoView.setBackgroundResource(R.drawable.ic_pause);
            }
//            LyricalVidMaker_VideoViewActivity.isPlay = LyricalVidMaker_VideoViewActivity.isPlay ^ 1;
        }
    };
    OnClickListener onclicksharevideo = new OnClickListener() {
        public void onClick(View view) {
            if (videoview != null && videoview.isPlaying()) {
                videoview.pause();
                flVideoView.setBackgroundResource(R.drawable.ic_play_button);
                isPlay = false;
                handler.removeCallbacks(onEverySecond);
            }
//            File file = new File(LyricalVidMaker_VideoViewActivity.videoPath);
//            Intent intent = new Intent("android.intent.action.SEND");
//            intent.setFlags(1);
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("Try this awesome application ");
//            stringBuilder.append(LyricalVidMaker_VideoViewActivity.getResources().getString(R.string.app_name));
//            stringBuilder.append(" .click the link to download now http://play.google.com/store/apps/details?id=");
//            stringBuilder.append(LyricalVidMaker_VideoViewActivity.getPackageName());
//            intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
//            Context applicationContext = LyricalVidMaker_VideoViewActivity.getApplicationContext();
//            stringBuilder = new StringBuilder();
//            stringBuilder.append(LyricalVidMaker_VideoViewActivity.getPackageName());
//            stringBuilder.append(".fileProvider");
//            Uri uriForFile = FileProvider.getUriForFile(applicationContext, stringBuilder.toString(), file);
//            intent.setType("video/*");
//            intent.putExtra("android.intent.extra.STREAM", uriForFile);
//            if (intent.resolveActivity(LyricalVidMaker_VideoViewActivity.getApplicationContext().getPackageManager()) != null) {
//                LyricalVidMaker_VideoViewActivity.startActivity(intent);
//            }

            Uri uri = FileProvider.getUriForFile(PVEMLyricalStatusMaker_VideoPreviewActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(videoPath));

            Intent shareIntent = new Intent("android.intent.action.SEND");
            shareIntent.setType("video/*");
            shareIntent.putExtra("android.intent.extra.SUBJECT", new File(videoPath).getName());
            shareIntent.putExtra("android.intent.extra.TITLE", new File(videoPath).getName());
            shareIntent.putExtra("android.intent.extra.STREAM", uri);
            startActivity(Intent.createChooser(shareIntent, "Share Video"));
        }
    };
    ProgressDialog pd = null;
    int pos = 0;
    Runnable runnable = new Runnable() {
        public void run() {
            pd.dismiss();
            handler.removeCallbacks(runnable);
            Intent intent;
            if (isFromlist) {
                intent = new Intent(PVEMLyricalStatusMaker_VideoPreviewActivity.this, PVEMLyricalStatusMaker_MyCreationActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            intent = new Intent(PVEMLyricalStatusMaker_VideoPreviewActivity.this, PVEMLyricalStatusMaker_StartActivity.class);
            startActivity(intent);
            finish();
        }
    };
    SeekBar seekVideo;
    Runnable seekrunnable = new Runnable() {
        public void run() {
            seekVideo.setProgress(videoview.getCurrentPosition());
            try {
                TextView textView = tvStartVideo;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(PVEMLyricalStatusMaker_VideoPreviewActivity.formatTimeUnit((long) (seekVideo.getProgress() + 1)));
                textView.setText(stringBuilder.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };
    TextView tvEndVideo;
    TextView tvStartVideo;
    TextView tvVideoName;
    String videoPath = "";
    VideoView videoview;

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    @SuppressLint("InvalidWakeLockTag")
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 99) {
            Intent intent2 = new Intent(this, PVEMLyricalStatusMaker_StartActivity.class);
            startActivity(intent2);
        }
    }

    protected void onResume() {
        super.onResume();
    }

    @SuppressLint({"InvalidWakeLockTag", "WrongConstant"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lyrical_video_view);
        Intent intent = getIntent();
        btnDeleteVideo = (ImageView) findViewById(R.id.btnDeleteVideo);
        ShareVideo = (ImageView) findViewById(R.id.btnShareVideo);
        layouttopbar = (RelativeLayout) findViewById(R.id.layouttopbar);
        if (intent.getStringExtra("Btn") != null) {
            if (intent.getStringExtra("Btn").equals("Share")) {
                ShareVideo.setOnClickListener(onclicksharevideo);
            } else if (intent.getStringExtra("Btn").equals("Delete")) {
                btnDeleteVideo.setOnClickListener(onclickdeletevideo);
            }
        }
        videoPath = intent.getStringExtra("videourl");
        isFromlist = intent.getBooleanExtra("fromList", false);
        if (isFromlist) {
            new AsyncTask<Void, Void, Void>() {
                @SuppressLint("WrongThread")
                protected Void doInBackground(Void... voidArr) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Environment.getExternalStorageDirectory());
                    stringBuilder.append("/Android/data/");
                    stringBuilder.append(getPackageName());
                    stringBuilder.append("/files");
                    File file = new File(stringBuilder.toString());
                    if (file.exists()) {
                        for (File file2 : file.listFiles()) {
                            for (File delete : file2.listFiles()) {
                                delete.delete();
                            }
                            file2.delete();
                        }
                    }
                    return null;
                }
            }.execute(new Void[0]);
        }
        videoview = (VideoView) findViewById(R.id.vvScreen);
        ivScreen = (ImageView) findViewById(R.id.ivScreen);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        seekVideo = (SeekBar) findViewById(R.id.sbVideo);
        seekVideo.setOnSeekBarChangeListener(this);
        tvStartVideo = (TextView) findViewById(R.id.tvStartVideo);
        tvEndVideo = (TextView) findViewById(R.id.tvEndVideo);
        videoview.setVideoPath(videoPath);
        videoview.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                Toast.makeText(getApplicationContext(), "File Not Supported this Media Player ", 0).show();
                return true;
            }
        });
        videoview.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                duration = mediaPlayer.getDuration();
                seekVideo.setMax(duration);
                try {
                    TextView textView = tvEndVideo;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(PVEMLyricalStatusMaker_VideoPreviewActivity.formatTimeUnit((long) duration));
                    textView.setText(stringBuilder.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                videoview.seekTo(seekVideo.getProgress());
                videoview.start();
                videoview.setVisibility(View.VISIBLE);
                handler.postDelayed(onEverySecond, 1000);
                ivScreen.setVisibility(View.GONE);
                flVideoView.setBackgroundResource(R.drawable.ic_pause);
            }
        });
        videoview.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                ivScreen.setVisibility(View.VISIBLE);
                videoview.seekTo(0);
                seekVideo.setProgress(0);
                try {
                    TextView textView = tvStartVideo;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(PVEMLyricalStatusMaker_VideoPreviewActivity.formatTimeUnit((long) seekVideo.getProgress()));
                    textView.setText(stringBuilder.toString());
                    flVideoView.setBackgroundResource(R.drawable.ic_play_button);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                handler.removeCallbacks(onEverySecond);
                isPlay = false;
            }
        });
        ivScreen.setImageBitmap(ThumbnailUtils.createVideoThumbnail(videoPath, 1));
        btnPlayVideo = (ImageView) findViewById(R.id.play_btn);
        btnDeleteVideo = (ImageView) findViewById(R.id.btnDeleteVideo);
        ShareVideo = (ImageView) findViewById(R.id.btnShareVideo);
        flVideoView = (ImageView) findViewById(R.id.flVideoView);
        tvVideoName = (TextView) findViewById(R.id.video_name);
        btnPlayVideo.setOnClickListener(onclickplayvideo);
        flVideoView.setOnClickListener(onclickplayvideo);
        btnDeleteVideo.setOnClickListener(onclickdeletevideo);
        ShareVideo.setOnClickListener(onclicksharevideo);
        tvVideoName.setText(getFileNameFromUrl(videoPath));
        btnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setLayout();
        showbanner();
    }

    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView =  findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public static String formatTimeUnit(long j) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
    }

    public void onBackPressed() {
        if (videoview != null && videoview.isPlaying()) {
            videoview.pause();
        }
        Intent intent;
        if (isFromlist) {
            finish();
            intent = new Intent(this, PVEMLyricalStatusMaker_MyCreationActivity.class);
            startActivity(intent);
            return;
        }
        finish();
        intent = new Intent(this, PVEMLyricalStatusMaker_StartActivity.class);
        startActivity(intent);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        videoview.seekTo(progress);
        try {
            TextView textView = tvStartVideo;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(formatTimeUnit((long) (progress + 1)));
            textView.setText(stringBuilder.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        videoview.start();
        if (!isPlay) {
            videoview.pause();
            flVideoView.setBackgroundResource(R.drawable.ic_play_button);
            isPlay = false;
        }
    }

    public String getFileNameFromUrl(String str) {
        String[] split = str.split("/");
        return split[split.length - 1];
    }

    void setLayout() {
        LayoutParams layoutParams = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 100) / 1080, (getResources().getDisplayMetrics().heightPixels * 100) / 1920);
        layoutParams.addRule(15);
        layoutParams.leftMargin = 30;
        btnBack.setLayoutParams(layoutParams);
        layoutParams = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 100) / 1080, (getResources().getDisplayMetrics().heightPixels * 100) / 1920);
        layoutParams.addRule(0, R.id.btnDeleteVideo);
        layoutParams.addRule(15);
        layoutParams.rightMargin = 20;
        ShareVideo.setLayoutParams(layoutParams);
        layoutParams = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 100) / 1080, (getResources().getDisplayMetrics().heightPixels * 100) / 1920);
        layoutParams.addRule(15);
        layoutParams.addRule(11);
        layoutParams.rightMargin = 20;
        btnDeleteVideo.setLayoutParams(layoutParams);
    }
}
