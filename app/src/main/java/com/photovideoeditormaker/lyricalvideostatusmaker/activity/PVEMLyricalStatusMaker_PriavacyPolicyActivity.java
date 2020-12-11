package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.utils.PVEMLyricalStatusMaker_MyCustomUtils;

public class PVEMLyricalStatusMaker_PriavacyPolicyActivity extends AppCompatActivity {
    ProgressDialog p;
    String s;
    WebView webView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.lyrical_policy);
        this.webView = (WebView) findViewById(R.id.web);
        this.p = new ProgressDialog(this);
        this.p.setMessage("Loading...");
        this.webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                PVEMLyricalStatusMaker_PriavacyPolicyActivity.this.p.show();
                super.onPageStarted(webView, str, bitmap);
            }

            public void onPageFinished(WebView webView, String str) {
                PVEMLyricalStatusMaker_PriavacyPolicyActivity.this.p.dismiss();
                super.onPageFinished(webView, str);
            }
        });
        this.webView.loadUrl(PVEMLyricalStatusMaker_MyCustomUtils.privacy_link);
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), PVEMLyricalStatusMaker_StartActivity.class);
        startActivity(intent);
        finish();
    }
}
