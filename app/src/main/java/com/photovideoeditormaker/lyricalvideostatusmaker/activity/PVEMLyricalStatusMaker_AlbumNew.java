package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.adapter.PVEMLyricalStatusMaker_ImageGridAdapter;
import com.photovideoeditormaker.lyricalvideostatusmaker.adapter.PVEMLyricalStatusMaker_ImageListAdapter;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_Album;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_AlbumSelect;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_AllLstFile;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_GalleryAlbm;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_GridFile;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_SelectImage;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_Utls2;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker__BmpComp;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_xtend;
import com.photovideoeditormaker.lyricalvideostatusmaker.view.PVEMLyricalStatusMaker_ImgScaUtilities;
import com.photovideoeditormaker.lyricalvideostatusmaker.view.PVEMLyricalStatusMaker_PgrVier;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;


public class PVEMLyricalStatusMaker_AlbumNew extends AppCompatActivity {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    public static int Listview_Selected_Pos;
    public static AppCompatActivity act;
    public static PVEMLyricalStatusMaker_ImageListAdapter adapter;
    public static ArrayList<PVEMLyricalStatusMaker_AllLstFile> arraylist = new ArrayList();
    public static ArrayList<Bitmap> bitimages = new ArrayList();
    static int count;
    private static String folderPath;
    public static PVEMLyricalStatusMaker_ImageGridAdapter gridadapter;
    public static ArrayList<PVEMLyricalStatusMaker_GridFile> gridlist = new ArrayList();
    public static int i;
    public static TextView textViewselected;
    ArrayList<PVEMLyricalStatusMaker_AlbumSelect> albumdata = null;
    Bitmap bitmap = null;
    Bitmap bitmapblur = null;
    int bucketid = 0;
    int cnti = 1;
    ArrayList<PVEMLyricalStatusMaker_GalleryAlbm> data = null;
    LinearLayout flMoveArea;
    GridView gridView;
    PVEMLyricalStatusMaker_PgrVier hsList;
    int idx = 0;
    ImageLoader imageLoader;
    ImageView imageViewDelete;
    ImageView imageViewNext;
    ImageButton imgbtn_delete;
    ImageButton imgbtn_next;
    int imgsize;
    boolean isBottom = true;
    ImageView ivBtnBack;
    ImageView ivBtnNext;
    String lastBucketID = "";
    RelativeLayout layoutToolbar;
    ListView listviewalbum;
    LinearLayout llHsList;
    FrameLayout lnr_gridview;
    int loopcount = 0;
    Context mContext;
    private PopupWindow mPopupWindow;
    OnClickListener onclickDelate = new OnClickListener() {
        public void onClick(View view) {
            PVEMLyricalStatusMaker_AlbumNew.this.llHsList.removeAllViews();
            PVEMLyricalStatusMaker_xtend.Final_Selected_Image.clear();
            PVEMLyricalStatusMaker_AlbumNew.this.GridDisplay();
            PVEMLyricalStatusMaker_AlbumNew.textViewselected.setText("Select 0 image");
            PVEMLyricalStatusMaker_AlbumNew.this.selctedimagecount = 0;
            PVEMLyricalStatusMaker_AlbumNew.gridadapter.setcount(0);
        }
    };

    OnClickListener onclickDelete = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (i == -1) {
                PVEMLyricalStatusMaker_xtend.Final_Selected_Image.clear();
                PVEMLyricalStatusMaker_AlbumNew.this.llHsList.removeAllViews();
                PVEMLyricalStatusMaker_AlbumNew.this.GridDisplay();
                if (PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size() == 0) {
                    PVEMLyricalStatusMaker_AlbumNew.this.txtview_selected_size.setText("0");
                } else {
                    PVEMLyricalStatusMaker_AlbumNew.this.txtview_selected_size.setText(String.valueOf(PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size()));
                }
            }
        }
    };

//    OnClickListener onclickDelete = new OnClickListener() {
//        public void onClick(View view) {
//            AnonymousClass1 anonymousClass1 = new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    if (i == -1) {
//                        PVEMLyricalStatusMaker_xtend.Final_Selected_Image.clear();
//                        LyricalVidMaker_AlbumNew.this.llHsList.removeAllViews();
//                        LyricalVidMaker_AlbumNew.this.GridDisplay();
//                        if (PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size() == 0) {
//                            LyricalVidMaker_AlbumNew.this.txtview_selected_size.setText("0");
//                        } else {
//                            LyricalVidMaker_AlbumNew.this.txtview_selected_size.setText(String.valueOf(PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size()));
//                        }
//                        dialogInterface.dismiss();
//                    }
//                }
//            };
//            new Builder(LyricalVidMaker_AlbumNew.this).setMessage("Are you sure you want to delete All this  Pictures ?").setPositiveButton("Ok", anonymousClass1).setNegativeButton("Cancel", anonymousClass1).show();
//        }
//    };

    OnClickListener onclickNext = new OnClickListener() {
        public void onClick(View view) {

            if (mInterstitialAdMob.isLoaded()) {
                mInterstitialAdMob.show();
            } else if (interstitialAd.isAdLoaded()) {
                interstitialAd.show();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("===Size ");
                stringBuilder.append(PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size());
                Log.e("vimal", stringBuilder.toString());
                PVEMLyricalStatusMaker_AlbumNew.this.loopcount = PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size();
                int img_size = PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size();
                Log.e("vimal", img_size + "");
                if (PVEMLyricalStatusMaker_AlbumNew.this.loopcount == 0) {
                    PVEMLyricalStatusMaker_AlbumNew.this.popup(0);
                } else if (PVEMLyricalStatusMaker_AlbumNew.this.loopcount < 6) {
                    PVEMLyricalStatusMaker_AlbumNew.this.popup(1);
                } else {
                    PVEMLyricalStatusMaker_AlbumNew.this.idx = 0;
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess = new ProgressDialog(PVEMLyricalStatusMaker_AlbumNew.this.mContext);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setMax(100);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setProgress(0);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setProgressStyle(1);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setCancelable(false);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.show();
                    LayoutParams layoutParams = new LayoutParams();
                    Window window = PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.getWindow();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.height = PVEMLyricalStatusMaker_Utls2.dpToPx(120);
                    layoutParams.gravity = 17;
                    window.setAttributes(layoutParams);
                    PVEMLyricalStatusMaker_AlbumNew.this.cnti = 1;
                    PVEMLyricalStatusMaker_AlbumNew.this.prepareImage(PVEMLyricalStatusMaker_AlbumNew.this.idx);
                }
            }
        }
    };

    ProgressDialog pdProcess;
    ProgressDialog pdSelection = null;
    ProgressDialog pdmedia = null;
    LinearLayout rlParentView;
    String saveFolder = null;
    int selctedimagecount = 0;
    PVEMLyricalStatusMaker_SelectImage selection = null;
    TextView textViewTotal;
    TextView toolbarTitle;
    int totalcount = 0;
    TextView txtview_selected_size;
    int width;
    int x = 0;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob;
    private InterstitialAd interstitialAd;

    class getMediaAsync extends AsyncTask<Void, Void, String> {
        getMediaAsync() {
        }

        protected void onPreExecute() {
            PVEMLyricalStatusMaker_AlbumNew.this.data = new ArrayList();
        }

        protected String doInBackground(Void... voidArr) {
            getMedia();
            return null;
        }

        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            if (PVEMLyricalStatusMaker_AlbumNew.this.data.size() > 0) {
                for (int i = 0; i < PVEMLyricalStatusMaker_AlbumNew.this.data.size(); i++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("===Data");
                    stringBuilder.append(((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).bucketId);
                    Log.e("", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("===Data");
                    stringBuilder.append(((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).bucketName);
                    Log.e("", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("===Data");
                    stringBuilder.append(((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).count);
                    Log.e("", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("===Data");
                    stringBuilder.append(((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).imgId);
                    Log.e("", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("===Data");
                    stringBuilder.append(((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).imgUri);
                    Log.e("", stringBuilder.toString());
                    int size = ((PVEMLyricalStatusMaker_SelectImage) PVEMLyricalStatusMaker_Utls2.imageUri.get(i)).imgUri.size();
                    PVEMLyricalStatusMaker_AlbumNew.arraylist.add(new PVEMLyricalStatusMaker_AllLstFile(((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).bucketId, ((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).bucketName, ((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).count, ((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).imgId, ((PVEMLyricalStatusMaker_GalleryAlbm) PVEMLyricalStatusMaker_AlbumNew.this.data.get(i)).imgUri, size));
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Size of Bucket ");
                    stringBuilder2.append(size);
                    Log.e("", stringBuilder2.toString());
                }
                PVEMLyricalStatusMaker_AlbumNew.adapter = new PVEMLyricalStatusMaker_ImageListAdapter(PVEMLyricalStatusMaker_AlbumNew.this, PVEMLyricalStatusMaker_AlbumNew.arraylist, PVEMLyricalStatusMaker_AlbumNew.this.imageLoader);
                PVEMLyricalStatusMaker_AlbumNew.this.listviewalbum.setAdapter(PVEMLyricalStatusMaker_AlbumNew.adapter);
                TextView textView = PVEMLyricalStatusMaker_AlbumNew.this.textViewTotal;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" / ");
                stringBuilder3.append(((PVEMLyricalStatusMaker_AllLstFile) PVEMLyricalStatusMaker_AlbumNew.arraylist.get(PVEMLyricalStatusMaker_AlbumNew.Listview_Selected_Pos)).get_Folder_Size());
                stringBuilder3.append(" selected");
                textView.setText(stringBuilder3.toString());
                PVEMLyricalStatusMaker_AlbumNew.textViewselected.setText("Select 0 image");
                PVEMLyricalStatusMaker_AlbumNew.this.selctedimagecount = 0;
                new getMediaBasedOnTask(PVEMLyricalStatusMaker_AlbumNew.this).execute(new Void[0]);
                return;
            }
            Toast.makeText(PVEMLyricalStatusMaker_AlbumNew.this, "No Media Record Found", Toast.LENGTH_SHORT).show();
        }
    }

    private class getMediaBasedOnTask extends AsyncTask<Void, Void, Boolean> {
        private getMediaBasedOnTask() {
        }

        getMediaBasedOnTask(PVEMLyricalStatusMaker_AlbumNew mRYTCHER_AlbumNew) {
            this();
        }

        protected void onPreExecute() {
            super.onPreExecute();
            PVEMLyricalStatusMaker_AlbumNew.this.pdmedia = new ProgressDialog(PVEMLyricalStatusMaker_AlbumNew.this.mContext);
            PVEMLyricalStatusMaker_AlbumNew.this.pdmedia.setMessage("Loading...");
            PVEMLyricalStatusMaker_AlbumNew.this.pdmedia.setCancelable(false);
            PVEMLyricalStatusMaker_AlbumNew.this.pdmedia.show();
        }

        protected Boolean doInBackground(Void... voidArr) {
            if (PVEMLyricalStatusMaker_AlbumNew.this.data.size() > 0) {
                PVEMLyricalStatusMaker_AlbumNew.this.data.clear();
            }
            try {
                int size = ((PVEMLyricalStatusMaker_SelectImage) PVEMLyricalStatusMaker_Utls2.imageUri.get(PVEMLyricalStatusMaker_AlbumNew.this.bucketid)).imgUri.size();
                for (int i = 0; i < size; i++) {
                    PVEMLyricalStatusMaker_Album mRYTCHER_AlbumImage = new PVEMLyricalStatusMaker_Album();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Img id");
                    stringBuilder.append(((PVEMLyricalStatusMaker_AlbumSelect) ((PVEMLyricalStatusMaker_SelectImage) PVEMLyricalStatusMaker_Utls2.imageUri.get(PVEMLyricalStatusMaker_AlbumNew.this.bucketid)).imgUri.get(i)).imgId);
                    Log.e("", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Uri");
                    stringBuilder.append(((PVEMLyricalStatusMaker_AlbumSelect) ((PVEMLyricalStatusMaker_SelectImage) PVEMLyricalStatusMaker_Utls2.imageUri.get(PVEMLyricalStatusMaker_AlbumNew.this.bucketid)).imgUri.get(i)).imgUri);
                    Log.e("", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Pos");
                    stringBuilder.append(((PVEMLyricalStatusMaker_AlbumSelect) ((PVEMLyricalStatusMaker_SelectImage) PVEMLyricalStatusMaker_Utls2.imageUri.get(PVEMLyricalStatusMaker_AlbumNew.this.bucketid)).imgUri.get(i)).imgPos);
                    Log.e("", stringBuilder.toString());
                    PVEMLyricalStatusMaker_GridFile mRYTCHER_GridViewFile = new PVEMLyricalStatusMaker_GridFile(((PVEMLyricalStatusMaker_AlbumSelect) ((PVEMLyricalStatusMaker_SelectImage) PVEMLyricalStatusMaker_Utls2.imageUri.get(PVEMLyricalStatusMaker_AlbumNew.this.bucketid)).imgUri.get(i)).imgUri);
                    PVEMLyricalStatusMaker_AlbumNew.this.totalcount = 0;
                    if (PVEMLyricalStatusMaker_xtend.Final_Selected_Image.contains(((PVEMLyricalStatusMaker_AlbumSelect) ((PVEMLyricalStatusMaker_SelectImage) PVEMLyricalStatusMaker_Utls2.imageUri.get(PVEMLyricalStatusMaker_AlbumNew.this.bucketid)).imgUri.get(i)).imgUri.toString())) {
                        PVEMLyricalStatusMaker_AlbumNew mRYTCHER_AlbumNew = PVEMLyricalStatusMaker_AlbumNew.this;
                        mRYTCHER_AlbumNew.totalcount++;
                    }
                    PVEMLyricalStatusMaker_AlbumNew.gridlist.add(mRYTCHER_GridViewFile);
                }
                return Boolean.valueOf(true);
            } catch (Exception unused) {
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                TextView textView = PVEMLyricalStatusMaker_AlbumNew.textViewselected;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Select ");
                stringBuilder.append(PVEMLyricalStatusMaker_AlbumNew.this.totalcount);
                stringBuilder.append(" image");
                textView.setText(stringBuilder.toString());
                PVEMLyricalStatusMaker_AlbumNew.this.selctedimagecount = PVEMLyricalStatusMaker_AlbumNew.this.totalcount;
                PVEMLyricalStatusMaker_AlbumNew.gridadapter = new PVEMLyricalStatusMaker_ImageGridAdapter(PVEMLyricalStatusMaker_AlbumNew.this, PVEMLyricalStatusMaker_AlbumNew.gridlist, PVEMLyricalStatusMaker_AlbumNew.this.imageLoader);
                PVEMLyricalStatusMaker_AlbumNew.gridadapter.setcount(PVEMLyricalStatusMaker_AlbumNew.this.totalcount);
                PVEMLyricalStatusMaker_AlbumNew.this.gridView.setAdapter(PVEMLyricalStatusMaker_AlbumNew.gridadapter);
            }
            if (PVEMLyricalStatusMaker_AlbumNew.this.pdmedia != null && PVEMLyricalStatusMaker_AlbumNew.this.pdmedia.isShowing()) {
                PVEMLyricalStatusMaker_AlbumNew.this.pdmedia.dismiss();
            }
        }
    }

    class saveImageBackground extends AsyncTask<Void, Void, Boolean> {
        saveImageBackground() {
        }

        protected Boolean doInBackground(Void... voidArr) {
            Paint paint = new Paint();
            Bitmap.createBitmap(PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT, Config.ARGB_8888);
            Bitmap scaleCenterCrop = PVEMLyricalStatusMaker_ImgScaUtilities.scaleCenterCrop(PVEMLyricalStatusMaker_AlbumNew.this.bitmap, PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT);
            Bitmap bitmap = PVEMLyricalStatusMaker_AlbumNew.this.bitmap;
            scaleCenterCrop = scaleCenterCrop.copy(scaleCenterCrop.getConfig(), true);
            Canvas canvas = new Canvas(PVEMLyricalStatusMaker_AlbumNew.this.blur(scaleCenterCrop, 25));
            Paint paint2 = new Paint();
            Rect rect = new Rect(0, 0, PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT);
            canvas.drawBitmap(PVEMLyricalStatusMaker_AlbumNew.newscaleBitmap(bitmap, PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT, 1.0f, 0.0f), 0.0f, 0.0f, paint2);
            paint2.setColor(-1996488705);
            paint2.setStrokeWidth(120.0f);
            paint2.setTextSize(120.0f);
            paint2.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
            PVEMLyricalStatusMaker_AlbumNew.this.bitmap = scaleCenterCrop;
            PVEMLyricalStatusMaker_AlbumNew.bitimages.add(scaleCenterCrop);
            if (PVEMLyricalStatusMaker_AlbumNew.this.bitmap != null) {
                return Boolean.valueOf(true);
            }
            return Boolean.valueOf(false);
        }

        protected void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                PVEMLyricalStatusMaker_AlbumNew.this.saveImage();
            } else {
                PVEMLyricalStatusMaker_AlbumNew.this.callIntent();
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.lyrical_pic_image_albumlist);
        getWindow().addFlags(128);
        this.mContext = this;
        act = this;
        initImageLoader();
        PVEMLyricalStatusMaker_xtend.Final_Selected_Image.clear();
        arraylist.clear();
        gridlist.clear();
        bitimages.clear();
        getWindowManager().getDefaultDisplay().getWidth();
        this.imgsize = (int) getResources().getDimension(R.dimen.imgsize);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        folderPath = stringBuilder.toString();
        this.rlParentView = (LinearLayout) findViewById(R.id.rlParentView);
        this.lnr_gridview = (FrameLayout) findViewById(R.id.lnr_gridview);
        this.listviewalbum = (ListView) findViewById(R.id.listview);
        this.gridView = (GridView) findViewById(R.id.gridView);
        this.textViewTotal = (TextView) findViewById(R.id.textViewTotal);
        this.hsList = (PVEMLyricalStatusMaker_PgrVier) findViewById(R.id.hsList);
        this.llHsList = (LinearLayout) findViewById(R.id.llHsList);
        this.layoutToolbar = (RelativeLayout) findViewById(R.id.layoutToolbar);
        int i = getResources().getDisplayMetrics().widthPixels;
        PVEMLyricalStatusMaker_Utls2.dpToPx(100);
        i = 0;
        this.flMoveArea = (LinearLayout) findViewById(R.id.flMoveArea);
        FindByID();
        setLayout();


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
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("===Size ");
                stringBuilder.append(PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size());
                Log.e("vimal", stringBuilder.toString());
                PVEMLyricalStatusMaker_AlbumNew.this.loopcount = PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size();
                int img_size = PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size();
                Log.e("vimal", img_size + "");
                if (PVEMLyricalStatusMaker_AlbumNew.this.loopcount == 0) {
                    PVEMLyricalStatusMaker_AlbumNew.this.popup(0);
                } else if (PVEMLyricalStatusMaker_AlbumNew.this.loopcount < 6) {
                    PVEMLyricalStatusMaker_AlbumNew.this.popup(1);
                } else {
                    PVEMLyricalStatusMaker_AlbumNew.this.idx = 0;
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess = new ProgressDialog(PVEMLyricalStatusMaker_AlbumNew.this.mContext);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setMax(100);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setProgress(0);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setProgressStyle(1);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setCancelable(false);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.show();
                    LayoutParams layoutParams = new LayoutParams();
                    Window window = PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.getWindow();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.height = PVEMLyricalStatusMaker_Utls2.dpToPx(120);
                    layoutParams.gravity = 17;
                    window.setAttributes(layoutParams);
                    PVEMLyricalStatusMaker_AlbumNew.this.cnti = 1;
                    PVEMLyricalStatusMaker_AlbumNew.this.prepareImage(PVEMLyricalStatusMaker_AlbumNew.this.idx);
                }
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
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("===Size ");
                stringBuilder.append(PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size());
                Log.e("vimal", stringBuilder.toString());
                PVEMLyricalStatusMaker_AlbumNew.this.loopcount = PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size();
                int img_size = PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size();
                Log.e("vimal", img_size + "");
                if (PVEMLyricalStatusMaker_AlbumNew.this.loopcount == 0) {
                    PVEMLyricalStatusMaker_AlbumNew.this.popup(0);
                } else if (PVEMLyricalStatusMaker_AlbumNew.this.loopcount < 6) {
                    PVEMLyricalStatusMaker_AlbumNew.this.popup(1);
                } else {
                    PVEMLyricalStatusMaker_AlbumNew.this.idx = 0;
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess = new ProgressDialog(PVEMLyricalStatusMaker_AlbumNew.this.mContext);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setMax(100);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setProgress(0);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setProgressStyle(1);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.setCancelable(false);
                    PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.show();
                    LayoutParams layoutParams = new LayoutParams();
                    Window window = PVEMLyricalStatusMaker_AlbumNew.this.pdProcess.getWindow();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.height = PVEMLyricalStatusMaker_Utls2.dpToPx(120);
                    layoutParams.gravity = 17;
                    window.setAttributes(layoutParams);
                    PVEMLyricalStatusMaker_AlbumNew.this.cnti = 1;
                    PVEMLyricalStatusMaker_AlbumNew.this.prepareImage(PVEMLyricalStatusMaker_AlbumNew.this.idx);
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        initImageLoader();
        arraylist.clear();
        gridlist.clear();
        int i = 0;
        Listview_Selected_Pos = 0;
        new getMediaAsync().execute(new Void[0]);
        if (PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size() != 0) {
            this.llHsList.removeAllViews();
            while (i < PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size()) {
//                selectImage(Uri.parse(((String) PVEMLyricalStatusMaker_xtend.Final_Selected_Image.get(i)).toString()), 0.0f, 0.0f, i);
                i++;
            }
        }
    }


    private void initImageLoader() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(build);
    }

    private void FindByID() {
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.toolbarTitle.setText("Select Images");
        this.ivBtnBack = (ImageView) findViewById(R.id.ivBtnBack);
        this.ivBtnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PVEMLyricalStatusMaker_AlbumNew.this.onBackPressed();
            }
        });
        this.ivBtnNext = (ImageView) findViewById(R.id.ivBtnNext);
        this.ivBtnNext.setOnClickListener(this.onclickNext);
        this.imageViewDelete = (ImageView) findViewById(R.id.imageViewDelete);
        textViewselected = (TextView) findViewById(R.id.textViewselected);
        this.imageViewDelete.setOnClickListener(this.onclickDelate);
        this.imageViewNext = (ImageView) findViewById(R.id.imageViewNext);
        this.imageViewNext.setOnClickListener(this.onclickNext);
    }

    private void removeFrameImage(String str) {
        File file = new File(str);
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().endsWith(".jpg") || file2.getName().endsWith(".png")) {
                        file2.delete();
                    }
                }
            }
        }
    }

    private static Bitmap newscaleBitmap(Bitmap bitmap, int i, int i2, float f, float f2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        Canvas canvas = new Canvas(createBitmap);
        float f3 = (float) i;
        float f4 = f3 / width;
        float f5 = (float) i2;
        float f6 = f5 / height;
        f5 = (f5 - (height * f4)) / 2.0f;
        float f7 = 0.0f;
        if (f5 < 0.0f) {
            f5 = (f3 - (width * f6)) / 2.0f;
            f4 = f6;
        } else {
            f7 = f5;
            f5 = 0.0f;
        }
        if (width == height) {
            Log.v("ifcondition", "ifcondition");
            if (width > f3) {
                i -= 100;
                bitmap = Bitmap.createScaledBitmap(bitmap, i, i, false);
                width = (float) i;
            }
            i = ((int) (f3 - width)) / 2;
            Matrix matrix = new Matrix();
            matrix.postTranslate(2.0f, 2.0f);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("xTranslation :");
            stringBuilder.append(f5);
            stringBuilder.append(" yTranslation :");
            stringBuilder.append(f7);
            Log.d("translation", stringBuilder.toString());
            matrix.preScale(f4, f4);
            float f8 = (float) i;
            canvas.drawBitmap(bitmap, f8, f8, null);
            return createBitmap;
        }
        Matrix matrix2 = new Matrix();
        matrix2.postTranslate(f * f5, f2 + f7);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("xTranslation :");
        stringBuilder2.append(f5);
        stringBuilder2.append(" yTranslation :");
        stringBuilder2.append(f7);
        Log.d("translation", stringBuilder2.toString());
        matrix2.preScale(f4, f4);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, matrix2, paint);
        return createBitmap;
    }

    private void prepareImage(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("====prepare image ===Size");
        stringBuilder.append(PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size());
        Log.e("", stringBuilder.toString());
        this.imageLoader.loadImage((String) PVEMLyricalStatusMaker_xtend.Final_Selected_Image.get(i), new ImageLoadingListener() {
            public void onLoadingCancelled(String str, View view) {
            }

            public void onLoadingStarted(String str, View view) {
                Log.e("", "==== image  1 ==");
            }

            public void onLoadingFailed(String str, View view, FailReason failReason) {
                Log.e("", "=== image 2 ==");
            }

            public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                Log.e("", "===  image  3==");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(str);
                stringBuilder.append(view);
                Log.e("Path =====(((=", stringBuilder.toString());
                int screenWidth = PVEMLyricalStatusMaker_Utls2.getScreenWidth();
                str = PVEMLyricalStatusMaker_AlbumNew.this.getRealPathFromURI(Uri.parse(str));
                Options options = new Options();
                options.inJustDecodeBounds = true;
                PVEMLyricalStatusMaker_AlbumNew.this.bitmap = BitmapFactory.decodeFile(str, options);
                options.inSampleSize = PVEMLyricalStatusMaker_AlbumNew.calculateInSampleSize(options, screenWidth, screenWidth);
                options.inJustDecodeBounds = false;
                PVEMLyricalStatusMaker_AlbumNew.this.bitmap = BitmapFactory.decodeFile(str, options);
                PVEMLyricalStatusMaker_AlbumNew.this.bitmap = PVEMLyricalStatusMaker__BmpComp.adjustImageOrientation(new File(str), PVEMLyricalStatusMaker_AlbumNew.this.bitmap);
                new saveImageBackground().execute(new Void[0]);
            }
        });
    }

    public static int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            i3 /= 2;
            i4 /= 2;
            while (i3 / i5 > i2 && i4 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public Bitmap blur(Bitmap bitmap, int i) {
        RenderScript create = RenderScript.create(this);
        Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
        Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        create2.setRadius((float) i);
        create2.setInput(createFromBitmap);
        create2.forEach(createTyped);
        createTyped.copyTo(bitmap);
        return bitmap;
    }

    Bitmap BlurImage(Bitmap bitmap) {
        try {
            RenderScript create = RenderScript.create(getApplicationContext());
            Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
            ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
            create2.setRadius(21.0f);
            create2.setInput(createFromBitmap);
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
            create2.forEach(createFromBitmap2);
            createFromBitmap2.copyTo(createBitmap);
            create.destroy();
            return createBitmap;
        } catch (Exception unused) {
            return bitmap;
        }
    }

    private void saveImage() {
        File file;
        if (folderPath == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append("/");
            stringBuilder.append(getResources().getString(R.string.app_name));
            folderPath = stringBuilder.toString();
        }
        StringBuilder stringBuilder2;
        if (this.saveFolder == null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(folderPath);
            stringBuilder2.append("/temp");
            file = new File(stringBuilder2.toString());
        } else {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(folderPath);
            stringBuilder2.append("/");
            stringBuilder2.append(this.saveFolder);
            file = new File(stringBuilder2.toString());
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("slide_");
        stringBuilder3.append(String.format(Locale.US, "%05d", new Object[]{Integer.valueOf(this.cnti)}));
        stringBuilder3.append(".jpg");
        File file2 = new File(file, stringBuilder3.toString());
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append("File name ====>>");
        stringBuilder3.append(file2);
        Log.e("", stringBuilder3.toString());
        this.cnti++;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            this.bitmap.compress(CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        callIntent();
    }

    private void saveImage1() {
        File file;
        if (folderPath == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append("/");
            stringBuilder.append(getResources().getString(R.string.app_name));
            folderPath = stringBuilder.toString();
        }
        StringBuilder stringBuilder2;
        if (this.saveFolder == null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(folderPath);
            stringBuilder2.append("/temp4");
            file = new File(stringBuilder2.toString());
        } else {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(folderPath);
            stringBuilder2.append("/");
            stringBuilder2.append(this.saveFolder);
            file = new File(stringBuilder2.toString());
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("slide_");
        stringBuilder3.append(String.format(Locale.US, "%05d", new Object[]{Integer.valueOf(this.cnti)}));
        stringBuilder3.append(".jpg");
        File file2 = new File(file, stringBuilder3.toString());
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append("File name ====>>");
        stringBuilder3.append(file2);
        Log.e("", stringBuilder3.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            this.bitmapblur.compress(CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callIntent() {
        this.pdProcess.setProgress((this.idx * 100) / this.loopcount);
        this.idx++;
        if (this.idx < this.loopcount) {
            prepareImage(this.idx);
            return;
        }
        this.idx = 0;
        if (this.pdProcess != null && this.pdProcess.isShowing()) {
            this.pdProcess.dismiss();
        }
        if (this.imageLoader != null) {
            this.imageLoader.clearDiskCache();
            this.imageLoader.clearMemoryCache();
        }
        if (this.saveFolder != null) {
            Log.e("", "===Main Activity");
            return;
        }
        Log.e("", "===Video Maker");
        Intent intent = new Intent(this, PVEMLyricalStatusMaker_EditImageActivity.class);
        intent.putExtra("fromedit", false);
        startActivity(intent);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = act.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }


    private void getMedia() {

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT};
        ArrayList<String> ids = new ArrayList<String>();
        final String[] selectionArgs = {getResources().getString(
                R.string.app_name)};
        final Cursor cursor;
        cursor = mContext.getContentResolver().query(
                uri,
                projection,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " != ?",
                selectionArgs,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " ASC,"
                        + MediaStore.Images.Media._ID + " DESC");
        if (cursor.getCount() < 0)
            return;
        if (cursor.moveToFirst()) {

            int bucketColumn = cursor
                    .getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int idColumn = cursor
                    .getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            int mediaColumn = cursor
                    .getColumnIndex(MediaStore.Images.Media._ID);
            lastBucketID = cursor.getString(idColumn);
            selection = new PVEMLyricalStatusMaker_SelectImage();
            albumdata = new ArrayList<PVEMLyricalStatusMaker_AlbumSelect>();
            selection.bucketid = lastBucketID;

            do {
                PVEMLyricalStatusMaker_GalleryAlbm gallaryAlbum = new PVEMLyricalStatusMaker_GalleryAlbm();
                gallaryAlbum.bucketName = cursor.getString(bucketColumn);
                gallaryAlbum.bucketId = cursor.getString(idColumn);
                int imageID = cursor.getInt(mediaColumn);
                Uri imguri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        Integer.toString(imageID));
                String iuri = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                File image = new File(iuri);

                if (image.exists()) {
                    int width = 0;
                    int height = 1;
                    try {
                        width = Integer
                                .parseInt(cursor.getString(cursor
                                        .getColumnIndex(MediaStore.Images.Media.WIDTH)));
                        height = Integer
                                .parseInt(cursor.getString(cursor
                                        .getColumnIndex(MediaStore.Images.Media.HEIGHT)));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    if (!ids.contains(gallaryAlbum.bucketId)) {

                        ids.add(gallaryAlbum.bucketId);
                        gallaryAlbum.imgUri = imguri;
                        gallaryAlbum.imgId = imageID;
                        data.add(gallaryAlbum);
                        if (!lastBucketID.equals(gallaryAlbum.bucketId)) {
                            selection.bucketid = lastBucketID;
                            selection.imgUri = new ArrayList<PVEMLyricalStatusMaker_AlbumSelect>();
                            selection.imgUri.addAll(albumdata);
                            PVEMLyricalStatusMaker_Utls2.imageUri.add(selection);
                            lastBucketID = gallaryAlbum.bucketId;
                            selection = new PVEMLyricalStatusMaker_SelectImage();
                            albumdata = new ArrayList<PVEMLyricalStatusMaker_AlbumSelect>();
                        }
                    }

                    PVEMLyricalStatusMaker_AlbumSelect albumimg = new PVEMLyricalStatusMaker_AlbumSelect();
                    albumimg.imgUri = imguri;
                    albumimg.imgId = imageID;
                    albumimg.imgPos = -1;
                    albumimg.width = width;
                    albumimg.height = height;
                    albumdata.add(albumimg);
                }
            } while (cursor.moveToNext());
            selection.bucketid = lastBucketID;
            selection.imgUri = new ArrayList<PVEMLyricalStatusMaker_AlbumSelect>();
            selection.imgUri.addAll(albumdata);
            PVEMLyricalStatusMaker_Utls2.imageUri.add(selection);
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.pdmedia != null) {
            this.pdmedia.dismiss();
        }
    }

    public void callBucketDisplay(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bucket position number");
        stringBuilder.append(i);
        Log.e("", stringBuilder.toString());
        gridlist.clear();
        this.bucketid = i;
        this.listviewalbum.invalidateViews();
        adapter.notifyDataSetChanged();
        gridadapter.notifyDataSetChanged();
        this.lnr_gridview.setVisibility(View.VISIBLE);
        TextView textView = this.textViewTotal;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" / ");
        stringBuilder2.append(((PVEMLyricalStatusMaker_AllLstFile) arraylist.get(i)).get_Folder_Size());
        stringBuilder2.append(" selected");
        textView.setText(stringBuilder2.toString());
        new getMediaBasedOnTask(this).execute(new Void[0]);
    }

    public void GridDisplay() {
        this.gridView.invalidateViews();
        gridadapter.notifyDataSetChanged();
    }

    @SuppressLint({"NewApi"})
    @TargetApi(11)
    public void selectImage(final Uri uri, float f, float f2, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Run");
        stringBuilder.append(uri);
        Log.e("", stringBuilder.toString());
        f -= (float) PVEMLyricalStatusMaker_Utls2.dpToPx(100);
        TextView textView = textViewselected;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Select ");
        stringBuilder2.append(i);
        stringBuilder2.append(" image");
        textView.setText(stringBuilder2.toString());
        this.selctedimagecount = i;
        final ImageView imageView = new ImageView(this.mContext);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(this.imgsize, this.imgsize));
        imageView.setScaleType(ScaleType.FIT_XY);
        this.lnr_gridview.addView(imageView);
        imageView.setLeft((int) f);
        imageView.setTop(((int) f2) + 10);
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("==Selected Image URI");
        stringBuilder2.append(uri.toString());
        Log.e("", stringBuilder2.toString());
        imageView.setImageBitmap(Thumbnails.getThumbnail(this.mContext.getContentResolver(), (long) Integer.parseInt(uri.toString().replace("content://media/external/images/media/", "")), 3, null));
        AnimatorSet animatorSet = new AnimatorSet();
        int height = this.gridView.getHeight() + 5;
        Animator[] animatorArr;
        if (this.llHsList.getWidth() + this.imgsize < this.width + PVEMLyricalStatusMaker_Utls2.dpToPx(100)) {
            animatorArr = new Animator[2];
            animatorArr[0] = ObjectAnimator.ofFloat(imageView, "translationX", new float[]{f, (float) ((this.x * this.imgsize) - PVEMLyricalStatusMaker_Utls2.dpToPx(60))});
            animatorArr[1] = ObjectAnimator.ofFloat(imageView, "translationY", new float[]{f2 + 10.0f, (float) height});
            animatorSet.playTogether(animatorArr);
            this.x++;
        } else {
            this.hsList.computeScroll();
            animatorArr = new Animator[2];
            animatorArr[0] = ObjectAnimator.ofFloat(imageView, "translationX", new float[]{f, (float) (this.width - this.imgsize)});
            animatorArr[1] = ObjectAnimator.ofFloat(imageView, "translationY", new float[]{f2 + 10.0f, (float) height});
            animatorSet.playTogether(animatorArr);
        }
        animatorSet.addListener(new AnimatorListener() {
            ImageButton btndelete;
            ImageView iv;

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                final Bitmap thumbnail = Thumbnails.getThumbnail(PVEMLyricalStatusMaker_AlbumNew.this.mContext.getContentResolver(), (long) Integer.parseInt(uri.toString().replace("content://media/external/images/media/", "")), 3, null);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        iv.setImageBitmap(thumbnail);
                        iv.setBackgroundColor(0);
                        btndelete.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(null);
                        PVEMLyricalStatusMaker_AlbumNew.this.flMoveArea.removeView(imageView);
                    }
                }, 200);
            }

            public void onAnimationStart(Animator animator) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        View inflate = View.inflate(PVEMLyricalStatusMaker_AlbumNew.this.mContext, R.layout.lyrical_row_thumb, null);
                        iv = (ImageView) inflate.findViewById(R.id.ivThumb);
                        btndelete = (ImageButton) inflate.findViewById(R.id.btnDelete);
                        ImageButton imageButton = btndelete;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(PVEMLyricalStatusMaker_AlbumNew.count);
                        imageButton.setTag(stringBuilder.toString());
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("");
                        stringBuilder2.append(PVEMLyricalStatusMaker_AlbumNew.count);
                        inflate.setTag(stringBuilder2.toString());
                        PVEMLyricalStatusMaker_AlbumNew.count++;
                        btndelete.setVisibility(View.GONE);
                        btndelete.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                PVEMLyricalStatusMaker_xtend.Final_Selected_Image.remove(uri.toString());
                                int i = PVEMLyricalStatusMaker_AlbumNew.this.selctedimagecount - 1;
                                PVEMLyricalStatusMaker_AlbumNew.gridadapter.setcount(i);
                                TextView textView = PVEMLyricalStatusMaker_AlbumNew.textViewselected;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Select ");
                                stringBuilder.append(i);
                                stringBuilder.append(" image");
                                textView.setText(stringBuilder.toString());
                                PVEMLyricalStatusMaker_AlbumNew.this.selctedimagecount = i;
                                PVEMLyricalStatusMaker_AlbumNew.this.GridDisplay();
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("");
                                stringBuilder2.append(view.getTag());
                                int parseInt = Integer.parseInt(stringBuilder2.toString());
                                i = PVEMLyricalStatusMaker_AlbumNew.this.llHsList.getChildCount();
                                for (int i2 = 0; i2 < i; i2++) {
                                    StringBuilder stringBuilder3 = new StringBuilder();
                                    stringBuilder3.append("");
                                    stringBuilder3.append(PVEMLyricalStatusMaker_AlbumNew.this.llHsList.getChildAt(i2).getTag());
                                    if (Integer.parseInt(stringBuilder3.toString()) == parseInt) {
                                        final LinearLayout linearLayout = (LinearLayout) PVEMLyricalStatusMaker_AlbumNew.this.llHsList.getChildAt(i2);
                                        ((ImageButton) ((FrameLayout) linearLayout.getChildAt(0)).getChildAt(1)).setVisibility(View.GONE);
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            int childwidth = (PVEMLyricalStatusMaker_AlbumNew.this.imgsize - 30);

                                            public void run() {
                                                if (this.childwidth > 0) {
                                                    int i = this.childwidth - 30;
                                                    this.childwidth = i;
                                                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(i, PVEMLyricalStatusMaker_AlbumNew.this.imgsize));
                                                    if (this.childwidth <= 0) {
                                                        PVEMLyricalStatusMaker_AlbumNew.this.llHsList.removeView(linearLayout);
                                                        handler.removeCallbacks(this);
                                                        return;
                                                    }
                                                    handler.postDelayed(this, 100);
                                                }
                                            }
                                        }, 100);
                                        break;
                                    }
                                }
                                if (PVEMLyricalStatusMaker_AlbumNew.this.imgsize * i < PVEMLyricalStatusMaker_AlbumNew.this.width) {
                                    PVEMLyricalStatusMaker_AlbumNew.this.x = i - 1;
                                }
                            }
                        });
                        PVEMLyricalStatusMaker_AlbumNew.this.llHsList.addView(inflate);
                        PVEMLyricalStatusMaker_AlbumNew.this.hsList.fullScrollOnLayout(66);
                    }
                }, 500);
            }
        });
        animatorSet.setDuration(700).start();
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float f = (float) i;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
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

    private void removeFrameImageNoCrop(String str) {
        File file = new File(str);
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if ((file2.getName().endsWith(".jpg") || file2.getName().endsWith(".png")) && !file2.getName().startsWith("crop")) {
                        file2.delete();
                    }
                }
            }
        }
    }

    public void onBackPressed() {
        PVEMLyricalStatusMaker_xtend.Final_Selected_Image.clear();
        this.llHsList.removeAllViews();
        GridDisplay();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        stringBuilder.append("/tmp");
        folderPath = stringBuilder.toString();
        removeFrameImageNoCrop(folderPath);
        finish();
        Intent intent = new Intent(this, PVEMLyricalStatusMaker_StartActivity.class);
        startActivity(intent);
    }

    void setLayout() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 100) / 1080, (getResources().getDisplayMetrics().heightPixels * 100) / 1920);
        layoutParams.addRule(15);
        layoutParams.leftMargin = 30;
        this.ivBtnBack.setLayoutParams(layoutParams);
    }

    public void popup(int i) {
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.lyrical_popup_gif, null);
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PVEMLyricalStatusMaker_AlbumNew.this.mPopupWindow.dismiss();
            }
        });
        this.mPopupWindow = new PopupWindow(inflate, -1, -1);
        if (VERSION.SDK_INT >= 21) {
            this.mPopupWindow.setElevation(5.0f);
        }
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.ll_pop);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 957) / 1080, (getResources().getDisplayMetrics().heightPixels * 207) / 1920);
        layoutParams.addRule(13);
        relativeLayout.setLayoutParams(layoutParams);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.gif);
        TextView textView = (TextView) inflate.findViewById(R.id.tx);
        if (i == 0) {
            textView.setText("Select atleast 6 images....");
        } else {
            textView.setText("Select atleast 6 images....");
        }
        this.mPopupWindow.showAtLocation(this.imageViewNext, 17, 0, 0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                PVEMLyricalStatusMaker_AlbumNew.this.mPopupWindow.dismiss();
            }
        }, 2000);
    }
}
