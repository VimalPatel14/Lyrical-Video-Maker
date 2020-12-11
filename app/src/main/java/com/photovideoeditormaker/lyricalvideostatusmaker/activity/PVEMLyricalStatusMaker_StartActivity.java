package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_xtend;
import com.photovideoeditormaker.lyricalvideostatusmaker.utill.PVEMLyricalStatusMaker_PrefMnger;
import com.photovideoeditormaker.lyricalvideostatusmaker.utils.PVEMLyricalStatusMaker_MyCustomUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PVEMLyricalStatusMaker_StartActivity extends AppCompatActivity {

    FFmpeg ffmpeg;
    RelativeLayout iv_gallary;
    RelativeLayout iv_myvideo;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private RelativeLayout banner_layout;
    private NativeAd nativeAd;
    private AdView mAdView;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob, mInterstitialAdMob1;
    private InterstitialAd interstitialAd;
    private InterstitialAd interstitialAd1;
    private com.facebook.ads.AdView adViewexit;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lyrical_activity_main);
        String[] strArr = new String[]{"android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "com.google.android.c2dm.permission.RECEIVE"};
        if (!hasPermissions(this, strArr)) {
            ActivityCompat.requestPermissions(this, strArr, 1);
        }
        CreateFolder();
        ffmpeg = FFmpeg.getInstance(getApplicationContext());
        iv_gallary = findViewById(R.id.start);
        iv_myvideo = findViewById(R.id.creation);

        showbanner();

        PVEMLyricalStatusMaker_xtend.Final_Selected_Image.clear();
        PVEMLyricalStatusMaker_PrefMnger.selectedImages.clear();

        nativeAdLayout = findViewById(R.id.native_ad_container);
        banner_layout = findViewById(R.id.banner_layout);
        if (isOnline()) {
            banner_layout.setVisibility(View.GONE);
            loadNativeAd();

        } else {
            banner_layout.setVisibility(View.VISIBLE);
            nativeAdLayout.setVisibility(View.GONE);
        }


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
                startActivity(new Intent(PVEMLyricalStatusMaker_StartActivity.this, PVEMLyricalStatusMaker_SongLyricsActivity.class));

            }
        });
        interstitialAd1 = new InterstitialAd(this, getResources().getString(R.string.interstitial_full_screen_fb));
        interstitialAd1.loadAd();
        interstitialAd1.setAdListener(new InterstitialAdListener() {
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
                interstitialAd1.loadAd();
                startActivity(new Intent(PVEMLyricalStatusMaker_StartActivity.this, PVEMLyricalStatusMaker_MyCreationActivity.class));
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
                startActivity(new Intent(PVEMLyricalStatusMaker_StartActivity.this, PVEMLyricalStatusMaker_SongLyricsActivity.class));
            }
        });
        mInterstitialAdMob1 = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAdMob1.setAdUnitId(getString(R.string.interstitial_full_screen_ad));
        mInterstitialAdMob1.loadAd(new AdRequest.Builder().build());
        mInterstitialAdMob1.setAdListener(new com.google.android.gms.ads.AdListener() {
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
                mInterstitialAdMob1.loadAd(new AdRequest.Builder().build());
                startActivity(new Intent(PVEMLyricalStatusMaker_StartActivity.this, PVEMLyricalStatusMaker_MyCreationActivity.class));
            }
        });

        iv_gallary.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                } else if (mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                } else {
                    startActivity(new Intent(PVEMLyricalStatusMaker_StartActivity.this, PVEMLyricalStatusMaker_SongLyricsActivity.class));
                }
            }
        });
        iv_myvideo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mInterstitialAdMob1.isLoaded()) {
                    mInterstitialAdMob1.show();
                } else if (interstitialAd1.isAdLoaded()) {
                    interstitialAd1.show();
                } else {
                    startActivity(new Intent(PVEMLyricalStatusMaker_StartActivity.this, PVEMLyricalStatusMaker_MyCreationActivity.class));
                }
            }
        });

    }


    public void CreateFolder() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        File file = new File(stringBuilder.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static boolean hasPermissions(Context context, String... strArr) {
        if (!(VERSION.SDK_INT < 23 || context == null || strArr == null)) {
            for (String checkSelfPermission : strArr) {
                if (ContextCompat.checkSelfPermission(context, checkSelfPermission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOnline() {
        @SuppressLint("WrongConstant") NetworkInfo netInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                if (!isOnline()) {
                    Toast.makeText(this, "No Internet Connection..", Toast.LENGTH_SHORT).show();
                    break;
                }
                moreapp();
                break;
            case R.id.privacy_policy:
                if (!isOnline()) {
                    Toast.makeText(this, "No Internet Connection..", Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity(new Intent(getApplicationContext(), PVEMLyricalStatusMaker_PriavacyPolicyActivity.class));
                break;
            case R.id.rate:
                gotoStore();
                break;
            case R.id.share:
                if (VERSION.SDK_INT >= 23) {
                    share();
                    break;
                }
                share();
                break;
        }
        return true;
    }

    public void gotoStore() {
        Intent myAppLinkToMarket = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "You don't have Google Play installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void moreapp() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(PVEMLyricalStatusMaker_MyCustomUtils.acc_link)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "You don't have Google Play installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void share() {
        Intent shareIntent = new Intent("android.intent.action.SEND");
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra("android.intent.extra.TEXT", PVEMLyricalStatusMaker_MyCustomUtils.app_name + " Created By :" + PVEMLyricalStatusMaker_MyCustomUtils.app_link);
        shareIntent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeResource(getResources(), R.drawable.banner), null, null)));
        startActivity(Intent.createChooser(shareIntent, "Share App"));
    }

    private void loadNativeAd() {
        nativeAd = new NativeAd(this, getResources().getString(R.string.native_fb));
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        });
        nativeAd.loadAd();
    }

    private void inflateAd(NativeAd nativeAd) {
        nativeAd.unregisterView();
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(PVEMLyricalStatusMaker_StartActivity.this);
        adView = (LinearLayout) inflater.inflate(R.layout.ad_unit, nativeAdLayout, false);
        nativeAdLayout.addView(adView);
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(PVEMLyricalStatusMaker_StartActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView adtxt = adView.findViewById(R.id.adtxt);
        adtxt.bringToFront();
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }

    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void onBackPressed() {
//        super.onBackPressed();
        final Dialog dialog1 = new Dialog(PVEMLyricalStatusMaker_StartActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog1.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        dialog1.setContentView(R.layout.exitdialog);

        dialog1.setCanceledOnTouchOutside(true);


        adViewexit = new com.facebook.ads.AdView(this, getString(R.string.medium_fb), AdSize.RECTANGLE_HEIGHT_250);
        LinearLayout adContainer = dialog1.findViewById(R.id.banner_containerexit);
        adContainer.addView(adViewexit);
        adViewexit.loadAd();

        TextView ad = dialog1.findViewById(R.id.ad);
        ad.bringToFront();

        LinearLayout cancel = dialog1.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        LinearLayout exit = dialog1.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO Auto-generated method stub
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                dialog1.dismiss();
            }
        });


        dialog1.show();
    }
}
