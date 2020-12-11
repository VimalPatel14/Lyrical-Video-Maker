package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.lyricalvideostatusmaker.adapter.PVEMLyricalStatusMaker_GridViewAdapter;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_xtend;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.R.raw;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.photovideoeditormaker.lyricalvideostatusmaker.utils.PVEMLyricalStatusMaker__utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PVEMLyricalStatusMaker_SongLyricsActivity extends AppCompatActivity implements OnSeekBarChangeListener {
    public static int songposition;
    PVEMLyricalStatusMaker_GridViewAdapter adapter = null;
    public static String audioPath;
    RelativeLayout btn_ok;
    ImageView btn_play;
    RelativeLayout cancel;
    int duration = 0;
    Field[] fields = raw.class.getFields();
    List<String> frame_list = new ArrayList();
    Handler handler = new Handler();
    ImageLoader imageLoader;
    boolean isVgood = true;
    ImageView ivBtnBack;
    RelativeLayout layoutToolbar;
    ListView listview_songs;
    MediaPlayer mPlayer;
    private PopupWindow mPopupWindow;
    String[] names;
    ArrayList<String> r_flie = new ArrayList();
    ArrayList<String> r_fliepath = new ArrayList();
    SeekBar seekVideo;
    Runnable seekrunnable = new Runnable() {
        public void run() {
            if (mPlayer != null && mPlayer.isPlaying()) {
                int currentPosition = mPlayer.getCurrentPosition();
                seekVideo.setProgress(currentPosition);
                try {
                    TextView textView = tvSeekLeft;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(PVEMLyricalStatusMaker_SongLyricsActivity.formatTimeUnit((long) currentPosition));
                    textView.setText(stringBuilder.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                handler.postDelayed(seekrunnable, 100);
            }
        }
    };
    File tempFile;
    TextView tvSeekLeft;
    TextView tvSeekRight;

    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob;
    private InterstitialAd interstitialAd;

    private AdView mAdView;
    private com.facebook.ads.AdView adView;

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lyrical_chose_song__lyrics);
        listview_songs = (ListView) findViewById(R.id.listview_songs);
        layoutToolbar = (RelativeLayout) findViewById(R.id.layoutToolbar);
        ivBtnBack = (ImageView) findViewById(R.id.ivBtnBack);
        ivBtnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initImageLoader();
        listRaw();
        try {
            names = getNames("img");
            for (String str : names) {
                for (int i = 0; i < 10; i++) {
                    List list = frame_list;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("assets://");
                    stringBuilder.append(str);
                    list.add(stringBuilder.toString());
                }
            }
            adapter = new PVEMLyricalStatusMaker_GridViewAdapter(this, imageLoader, r_flie);
            listview_songs.setAdapter(adapter);
        } catch (Exception unused) {
        }
        listview_songs.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                popup(i);
                PVEMLyricalStatusMaker_SongLyricsActivity.songposition = i;
                if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(PVEMLyricalStatusMaker_SongLyricsActivity.this, Uri.parse((String) r_fliepath.get(i)));
                    mPlayer.prepare();
                    mPlayer.seekTo(0);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e2) {
                    e2.printStackTrace();
                } catch (IllegalStateException e3) {
                    e3.printStackTrace();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                initMediaplayer();
            }
        });
        setLayout();
        showfbbanner();
        showbanner();


        interstitialAd = new InterstitialAd(this, getResources().getString(R.string.interstitial_full_screen_fb));
        interstitialAd.loadAd();
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                interstitialAd.loadAd();
                startActivity(new Intent(PVEMLyricalStatusMaker_SongLyricsActivity.this, PVEMLyricalStatusMaker_AlbumNew.class));
            }
        });


        mInterstitialAdMob = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAdMob.setAdUnitId(getString(R.string.interstitial_full_screen_ad));
        mInterstitialAdMob.loadAd(new AdRequest.Builder().build());
        mInterstitialAdMob.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                mInterstitialAdMob.loadAd(new AdRequest.Builder().build());
                startActivity(new Intent(PVEMLyricalStatusMaker_SongLyricsActivity.this, PVEMLyricalStatusMaker_AlbumNew.class));
            }
        });
    }

    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void showfbbanner() {
        adView = new com.facebook.ads.AdView(this, getString(R.string.banner_ad_fb), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            mPlayer.seekTo(i);
            try {
                TextView textView = tvSeekLeft;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(formatTimeUnit((long) i));
                textView.setText(stringBuilder.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onResume() {
        super.onResume();
    }

    public void popup(final int i) {
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.lyrical_popup_song, null);
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                mPopupWindow.dismiss();
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    btn_play.setBackgroundResource(R.drawable.ic_play_button);
                    handler.removeCallbacks(seekrunnable);
                }
            }
        });
        mPopupWindow = new PopupWindow(inflate, -1, -1);
        if (VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ll_pop);
        LayoutParams layoutParams = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 954) / 1080, (getResources().getDisplayMetrics().heightPixels * 604) / 1920);
        layoutParams.addRule(13);
        linearLayout.setLayoutParams(layoutParams);
        tvSeekLeft = (TextView) inflate.findViewById(R.id.tvSeekLeft);
        tvSeekRight = (TextView) inflate.findViewById(R.id.tvSeekRight);
        btn_ok = inflate.findViewById(R.id.btn_ok);
        cancel = inflate.findViewById(R.id.cancle);
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    btn_play.setBackgroundResource(R.drawable.ic_play_button);
                    handler.removeCallbacks(seekrunnable);
                }
                mPopupWindow.dismiss();
            }
        });
        btn_play = (ImageView) inflate.findViewById(R.id.btn_play);
        LayoutParams layoutParams2 = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 308) / 1080, (getResources().getDisplayMetrics().heightPixels * 89) / 1920);
        layoutParams2.addRule(13);
        btn_ok.setLayoutParams(layoutParams2);
        cancel.setLayoutParams(layoutParams2);
        seekVideo = (SeekBar) inflate.findViewById(R.id.seekVideo);
        seekVideo.setOnSeekBarChangeListener(this);
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        btn_play.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mPlayer == null) {
                    return;
                }
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    btn_play.setBackgroundResource(R.drawable.ic_play_button);
                    handler.removeCallbacks(seekrunnable);
                } else {
                    mPlayer.start();
                    btn_play.setBackgroundResource(R.drawable.ic_pause);
                    handler.postDelayed(seekrunnable, 100);
                }

            }
        });
        btn_ok.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                mPopupWindow.dismiss();
                StringBuilder stringBuilder;
                try {
                    PVEMLyricalStatusMaker_SongLyricsActivity mRYTCHER_ChoseSongLyrics = PVEMLyricalStatusMaker_SongLyricsActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(Environment.getExternalStorageDirectory());
                    stringBuilder.append("/");
                    stringBuilder.append(getResources().getString(R.string.app_name));
                    mRYTCHER_ChoseSongLyrics.tempFile = new File(stringBuilder.toString());
                    if (!tempFile.exists()) {
                        tempFile.mkdirs();
                    }
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(Environment.getExternalStorageDirectory());
                    stringBuilder2.append("/");
                    stringBuilder2.append(getResources().getString(R.string.app_name));
                    File file = new File(stringBuilder2.toString(), "temp.mp3");
                    if (file.exists()) {
                        tempFile.delete();
                    }
                    InputStream openRawResource = getResources().openRawResource(fields[i].getInt(fields[i]));
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = openRawResource.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    audioPath = file.getAbsolutePath();
                    PVEMLyricalStatusMaker__utils.audiopath = file.getAbsolutePath();
                    PVEMLyricalStatusMaker_xtend.audiopath = file.getAbsolutePath();
                } catch (Exception e) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(e);
                    stringBuilder.append("");
                    Log.e("hi", stringBuilder.toString());
                }
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
//                if (mInterstitialAdMob.isLoaded()) {
//                    mInterstitialAdMob.show();
//                }
//                else if (interstitialAd.isAdLoaded()) {
//                    interstitialAd.show();
//                }
//                else {
                startActivity(new Intent(PVEMLyricalStatusMaker_SongLyricsActivity.this, PVEMLyricalStatusMaker_AlbumNew.class));
//                LyricalVidMaker_ChoseSongLyrics.startActivity(new Intent(LyricalVidMaker_ChoseSongLyrics.this, MagicVideoMaker_ImageSelectionActivity.class));
//                }
            }
        });
        mPopupWindow.showAtLocation(listview_songs, 17, 0, 0);
    }

    private void initMediaplayer() {
        mPlayer.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                btn_play.setBackgroundResource(R.drawable.ic_play_button);
                seekVideo.setProgress(0);
                tvSeekLeft.setText("00:00");
                duration = mediaPlayer.getDuration();
                seekVideo.setMax(duration);
                try {
                    TextView textView = tvSeekRight;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(PVEMLyricalStatusMaker_SongLyricsActivity.formatTimeUnit((long) duration));
                    textView.setText(stringBuilder.toString());
                } catch (Exception unused) {
                }
                isVgood = true;
                mPlayer.start();
                btn_play.setBackgroundResource(R.drawable.ic_pause);
                handler.postDelayed(seekrunnable, 100);
            }
        });
        mPlayer.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                isVgood = false;
                Toast.makeText(PVEMLyricalStatusMaker_SongLyricsActivity.this, "Video Player Supporting issue.", Toast.LENGTH_SHORT).show();
                try {
                    handler.removeCallbacks(seekrunnable);
                } catch (Exception unused) {
                }
                return true;
            }
        });
        mPlayer.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                btn_play.setBackgroundResource(R.drawable.ic_play_button);
                seekVideo.setProgress(0);
                tvSeekLeft.setText("00:00");
                handler.removeCallbacks(seekrunnable);
            }
        });
    }

    public static String formatTimeUnit(long j) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
    }

    private String[] getNames(String str) {
        String[] list;
        try {
            list = getAssets().list(str);
        } catch (IOException e) {
            e.printStackTrace();
            list = null;
        }
        for (int i = 0; i < list.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("/");
            stringBuilder.append(list[i]);
            list[i] = stringBuilder.toString();
        }
        return list;
    }

    private void initImageLoader() {
        ImageLoaderConfiguration build = new Builder(getApplicationContext()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(build);
    }

    public void listRaw() {
        for (int i = 0; i < fields.length; i++) {
            Log.i("Raw Asset: ", fields[i].getName());
            try {
                ArrayList arrayList = r_fliepath;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("android.resource://");
                stringBuilder.append(getPackageName());
                stringBuilder.append("/");
                stringBuilder.append(fields[i].getInt(fields[i]));
                arrayList.add(stringBuilder.toString());
                r_flie.add(fields[i].getName().replace("_", " "));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    void setLayout() {
        LayoutParams layoutParams = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 100) / 1080, (getResources().getDisplayMetrics().heightPixels * 100) / 1920);
        layoutParams.addRule(15);
        layoutParams.leftMargin = 30;
        ivBtnBack.setLayoutParams(layoutParams);
        layoutToolbar.setLayoutParams(new RelativeLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 1080) / 1080, (getResources().getDisplayMetrics().heightPixels * 150) / 1920));
    }

    public void list() {
        Field[] fields = raw.class.getFields();
        for (Field name : fields) {
            Log.i("Raw Asset: ", name.getName());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }

        Intent intent = new Intent(this, PVEMLyricalStatusMaker_StartActivity.class);
        startActivity(intent);
    }


}
