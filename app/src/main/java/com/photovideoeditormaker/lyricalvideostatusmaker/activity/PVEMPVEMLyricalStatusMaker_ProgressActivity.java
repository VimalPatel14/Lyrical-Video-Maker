package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.utill.PVEMLyricalStatusMaker_PrefMnger;
import com.photovideoeditormaker.lyricalvideostatusmaker.webservice.PVEMLyricalStatusMaker_VdoService;
import com.photovideoeditormaker.lyricalvideostatusmaker.webservice.PVEMLyricalStatusMaker_ProgressReceiver;
import com.wang.avi.AVLoadingIndicatorView;


public class PVEMPVEMLyricalStatusMaker_ProgressActivity extends AppCompatActivity implements PVEMLyricalStatusMaker_ProgressReceiver {
    private PVEMLyricalStatusMaker_PrefMnger application;
        private AVLoadingIndicatorView circularProgress;
    private int endColor;
    final float[] from = new float[3];
    final float[] hsv = new float[3];
    boolean isComplate = true;
    float lastProg = 0.0f;
    private int startColor;
    final float[] to = new float[3];
    private TextView tvProgress;
    TextView tx;
    TextView tx1;
    private AdView mAdView;

    private void addListner() {
    }

    public void onBackPressed() {
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lyrical_activity_progress);
        getWindow().addFlags(128);

        application = PVEMLyricalStatusMaker_PrefMnger.getInstance();

        tx = (TextView) findViewById(R.id.tvProgress2);
        tx1 = (TextView) findViewById(R.id.tvProgress3);

        bindView();
        init();
        addListner();
        startAnim();
        showbanner();
    }

    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void bindView() {
        circularProgress =  findViewById(R.id.circularProgress);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
    }

    private void init() {
        startColor = getResources().getColor(R.color.colorPrimary);
        endColor = getResources().getColor(R.color.colorPrimaryDark);
        Color.colorToHSV(startColor, from);
        Color.colorToHSV(endColor, to);
    }

    protected void onResume() {
        super.onResume();
        application.setOnProgressReceiver(this);
    }

    protected void onStop() {
        super.onStop();
        application.setOnProgressReceiver(null);
        PVEMLyricalStatusMaker_PrefMnger.isMyServiceRunning(this, PVEMLyricalStatusMaker_VdoService.class);
    }

    void startAnim() {
        circularProgress.show();
    }

    void stopAnim() {
        circularProgress.hide();
    }

    public Integer evaluate(float f, Integer num, Integer num2) {
        int alpha = Color.alpha(num2.intValue());
        int alpha2 = Color.alpha(num.intValue());
        alpha = (int) (((float) (alpha - alpha2)) * f);
        int red = Color.red(num2.intValue());
        int red2 = Color.red(num.intValue());
        red = (int) (((float) (red - red2)) * f);
        int green = Color.green(num2.intValue());
        int green2 = Color.green(num.intValue());
        green = (int) (((float) (green - green2)) * f);
        int blue = Color.blue(num2.intValue());
        int blue2 = Color.blue(num.intValue());
        return Integer.valueOf(Color.argb(alpha2 + alpha, red2 + red, green2 + green, blue2 + ((int) (((float) (blue - blue2)) * f))));
    }

    private synchronized void changeBgColor(float f) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("progress__");
        stringBuilder.append(f);
        if (isComplate) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{lastProg, f});
            ofFloat.setDuration(300);
            ofFloat.setInterpolator(new LinearInterpolator());
            ofFloat.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    hsv[0] = from[0] + (((to[0] - from[0]) * ((Float) valueAnimator.getAnimatedValue()).floatValue()) / 100.0f);
                    hsv[1] = from[1] + (((to[1] - from[1]) * ((Float) valueAnimator.getAnimatedValue()).floatValue()) / 100.0f);
                    hsv[2] = from[2] + (((to[2] - from[2]) * ((Float) valueAnimator.getAnimatedValue()).floatValue()) / 100.0f);

                float a = (float) 0.04347826;
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue()*-a;
                    tvProgress.setText(String.format(" %05.2f%%", new Object[]{Float.valueOf(floatValue)}));
                    if (((int) floatValue) == 91) {
                        tx.setText(" Video almost Complete.... ");
                        tx1.setText(" ");
                    }
                    ((Float) valueAnimator.getAnimatedValue()).floatValue();
                }
            });
            ofFloat.addListener(new AnimatorListener() {
                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    isComplate = false;
                }

                public void onAnimationEnd(Animator animator) {
                    isComplate = true;
                }

                public void onAnimationCancel(Animator animator) {
                    isComplate = true;
                }
            });
            ofFloat.start();
            lastProg = f;
        }
    }

    public void onImageProgressUpdate(final float f) {
        if (circularProgress != null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    changeBgColor((25.0f * f) / 100.0f);
                }
            });
        }
    }

    public void onProgressFinish(String str) {
        stopAnim();
        startActivity(new Intent(PVEMPVEMLyricalStatusMaker_ProgressActivity.this, PVEMLyricalStatusMaker_MyCreationActivity.class));
        
    }

    public void onVideoProgressUpdate(final int f) {
        if (circularProgress != null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    changeBgColor(25.0f + ((75.0f * f) / 100.0f));
                }
            });
        }
    }
}
