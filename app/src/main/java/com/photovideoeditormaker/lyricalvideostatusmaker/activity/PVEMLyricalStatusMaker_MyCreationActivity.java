package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Media;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.lyricalvideostatusmaker.adapter.PVEMLyricalStatusMaker_CreationAdpter;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.LyricalVidMaker_VideoModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;

import java.io.File;
import java.util.ArrayList;

public class PVEMLyricalStatusMaker_MyCreationActivity extends AppCompatActivity {
    public static PVEMLyricalStatusMaker_CreationAdpter adapter;
    public static Cursor ecursor;
    public static ImageLoader imgLoader;
    public static ArrayList<LyricalVidMaker_VideoModel> mp3_arraylist = new ArrayList();
    GridView GridViewPhoto;
    String albumName;
    ImageView imageViewBack;
    private com.facebook.ads.AdView adView;
    private AdView mAdView;
    TextView textViewTitle;
    LinearLayout lin_vertical_slide_panel2;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lyrical_creation_activity);
        initImageLoader();
        mp3_arraylist.clear();
        GridViewPhoto = (GridView) findViewById(R.id.GridViewPhoto);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        lin_vertical_slide_panel2 = findViewById(R.id.lin_vertical_slide_panel2);
        LayoutParams layoutParams = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 100) / 1080, (getResources().getDisplayMetrics().heightPixels * 100) / 1920);
        layoutParams.addRule(15);
        layoutParams.leftMargin = 30;
        imageViewBack.setLayoutParams(layoutParams);
        GridViewPhoto.setSelector(new ColorDrawable(0));
        imageViewBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        showfbbanner();
        showbanner();

        String[] strArr = new String[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%");
        stringBuilder.append(getResources().getString(R.string.app_name));
        stringBuilder.append("/FinalVideos%");
        strArr[0] = stringBuilder.toString();
        ecursor = managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data", "_display_name", "_size", "duration", "date_added"}, "_data like ? ", strArr, "_id DESC");
        ecursor.moveToFirst();
        if (ecursor.moveToFirst()) {
            do {
                Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, getLong(ecursor));
                String string = ecursor.getString(ecursor.getColumnIndexOrThrow("_display_name"));
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("==Selected Data==");
                stringBuilder2.append(string);
                Log.e("", stringBuilder2.toString());
                LyricalVidMaker_VideoModel mRYTCHER_VideoModel = new LyricalVidMaker_VideoModel(string, withAppendedPath.toString(), getRealPathFromURI(getApplicationContext(), withAppendedPath));
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("File Path");
                stringBuilder2.append(getRealPathFromURI(getApplicationContext(), withAppendedPath));
                Log.e("", stringBuilder2.toString());
                File file = new File(getRealPathFromURI(getApplicationContext(), withAppendedPath));
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("File Exist Or not");
                stringBuilder2.append(file.exists());
                Log.e("", stringBuilder2.toString());
                if (file.exists()) {
                    mp3_arraylist.add(mRYTCHER_VideoModel);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(mp3_arraylist.size());
                    stringBuilder.append("");
                    Log.v("mp3arraylist", stringBuilder.toString());
                }
            } while (ecursor.moveToNext());
        }
        adapter = new PVEMLyricalStatusMaker_CreationAdpter(this, mp3_arraylist);
        GridViewPhoto.setAdapter(adapter);

        if (mp3_arraylist.size() == 0) {
            lin_vertical_slide_panel2.setVisibility(View.VISIBLE);
        } else {
            lin_vertical_slide_panel2.setVisibility(View.GONE);
        }
    }

    private String getLong(Cursor cursor) {
        int columnIndexOrThrow = cursor.getColumnIndexOrThrow("_id");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(cursor.getLong(columnIndexOrThrow));
        return stringBuilder.toString();
    }

    private void initImageLoader() {
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(new Builder(this).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).build()).build());
    }

    public void callIntent(int i) {
        Intent intent = new Intent(this, PVEMLyricalStatusMaker_VideoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("videourl", ((LyricalVidMaker_VideoModel) mp3_arraylist.get(i)).get_Original_Path());
        bundle.putInt("position", i);
        bundle.putBoolean("fromList", true);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public java.lang.String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {

            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void showfbbanner() {
        adView = new com.facebook.ads.AdView(this, getString(R.string.banner_ad_fb), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }
    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView =  findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, PVEMLyricalStatusMaker_StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
