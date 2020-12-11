package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.ads.AdSettings;
import com.google.android.gms.ads.AdRequest;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;


public class PVEMLyricalStatusMaker_SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        AdSettings.addTestDevice(getResources().getString(R.string.device_id));


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
                Intent i = new Intent(PVEMLyricalStatusMaker_SplashActivity.this, PVEMLyricalStatusMaker_StartActivity.class);
                startActivity(i);
                finish();

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                } else {

                    Intent i = new Intent(PVEMLyricalStatusMaker_SplashActivity.this, PVEMLyricalStatusMaker_StartActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }


}