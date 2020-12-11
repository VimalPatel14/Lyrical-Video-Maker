package com.photovideoeditormaker.lyricalvideostatusmaker.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.internal.view.SupportMenu;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.PVEMLyricalStatusMaker_TextViewV;
import com.photovideoeditormaker.lyricalvideostatusmaker.text.PVEMLyricalStatusMaker__TextViewVType;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_VItem;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_Utls2;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_xtend;
import com.photovideoeditormaker.lyricalvideostatusmaker.utill.PVEMLyricalStatusMaker_PrefMnger;
import com.photovideoeditormaker.lyricalvideostatusmaker.utils.PVEMLyricalStatusMaker_AnimLay;
import com.photovideoeditormaker.lyricalvideostatusmaker.view.PVEMLyricalStatusMaker_ImgScaUtilities;
import com.photovideoeditormaker.lyricalvideostatusmaker.view.PVEMLyricalStatusMaker_HorizontalLstView;
import com.photovideoeditormaker.lyricalvideostatusmaker.webservice.PVEMLyricalStatusMaker_ImgService;
import com.photovideoeditormaker.lyricalvideostatusmaker.webservice.PVEMLyricalStatusMaker_THEME;
import com.photovideoeditormaker.lyricalvideostatusmaker.webservice.PVEMLyricalStatusMaker_VdoService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

//import com.mrytcher.birthdaylyricalvideostatusmaker.extra.HTextView;

public class PVEMLyricalStatusMaker_EditImageActivity extends AppCompatActivity {
    public static ArrayList<Bitmap> animbitimages = new ArrayList();
    public static ArrayList<Bitmap> animbitimages2 = new ArrayList();
    public static ArrayList<Bitmap> bitfinalimages = new ArrayList();
    private static String folderPath;
    public static ImageView img;
    public static ImageView img1;
    public static ImageView imgblur;
    private static ArrayList<View> mViews;
    public static ImageView next;
    public static ImageView tm;
    PVEMLyricalStatusMaker_HorizontalLstView Horizontal_frameview;
    ImageAdapter adapter;
    PVEMLyricalStatusMaker_Animation anim1 = null;
    Bitmap animbit;
    private PVEMLyricalStatusMaker_PrefMnger application;
    Bitmap bmp;
    Boolean boelSave;
    LinearLayout bottomlayout;
    boolean check1;
    String cmd;
    Button col;
    private int counter;
    private int currentColor;
    float derajat;
    PVEMLyricalStatusMaker_EditImageActivity edT;
    private PVEMLyricalStatusMaker_AnimLay enterAnimLayout;
    int f8i;
    FFmpeg ffmpeg;
    ArrayList<String> filenames;
    ArrayList<Bitmap> finalsix = new ArrayList();
    RelativeLayout fledit;
    String[] folders;
    String[] fontstylearry = null;
    RelativeLayout frame1;
    FrameLayout frameLayout;
    int getRes2;
    PVEMLyricalStatusMaker_TextViewV lyricalVidMakerHTextView;
    PVEMLyricalStatusMaker_TextViewV lyricalVidMakerHTextView2;
    PVEMLyricalStatusMaker_TextViewV lyricalVidMakerHTextView3;
    Handler handler = new Handler();
    Handler handlerSave = new Handler();
    Handler handlershow = new Handler();
    HorizontalScrollView hl;
    LinearLayout hlList;
    private ImageLoader imageLoader;
    int imagecount = 0;
    int incr = 0;
    int intAnim;
    int intAnimModel;
    int intFullScreen;
    int intK;
    int intKlikLayar;
    int intSave;
    int intSentence;
    boolean isFlying;
    int klik;
    ArrayList<PVEMLyricalStatusMaker_VItem> list = new ArrayList();
    ProgressDialog loadingDialogs;
    int mCounter = 80;
    RecyclerView.LayoutManager mLayoutManager;
    private PopupWindow mPopupWindow;
    private Random mRandom = new Random();
    int mx;
    int mx2;
    String[] names;
    Boolean nxt = Boolean.valueOf(true);
    DisplayImageOptions optsFull;
    DisplayImageOptions optsThumb;
    ProgressDialog pd;
    ProgressDialog pd1;
    YoYo.YoYoString rope;
    Runnable runnable = new Runnable() {
        public void run() {
        }
    };
    Runnable runnableSave = new C04941();
    Runnable runnableshow = new C04952();
    Bitmap s2;
    String saveFolder = null;
    private int selectedView;
    String[] sentences = new String[]{"Jaadu sa  ", "Nakhra tera ni", "High Rated Gabru nu mre"};
    ServiceCompleted serviceCompleted = new ServiceCompleted();
    Button stic;
    Button style;
    RecyclerView stylelist;
    ArrayList<String> svtxt = new ArrayList();
    int tText;
    boolean talkCloud;
    Button text;
    int textSize;
    int theam = 0;
    int ti = 0;
    String to = "";
    String to2 = "";
    String to3 = "";
    TextView tv_songbottom;
    TextView tv_songbottom2;
    TextView tv_songtop;
    TextView tv_songtop2;
    FrameLayout txedit;
    File videoIp;
    String videoname = "";


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.lyrical_activity_edit_images);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ffmpeg = FFmpeg.getInstance(getApplicationContext());
        loadFFMpegBinary();
        frame1 = (RelativeLayout) findViewById(R.id.fltext);

        lyricalVidMakerHTextView = (PVEMLyricalStatusMaker_TextViewV) findViewById(R.id.text2);
        lyricalVidMakerHTextView.setTextSize(30.0f);
        lyricalVidMakerHTextView2 = (PVEMLyricalStatusMaker_TextViewV) findViewById(R.id.text3);
        lyricalVidMakerHTextView3 = (PVEMLyricalStatusMaker_TextViewV) findViewById(R.id.text4);
        lyricalVidMakerHTextView2.setTextSize(30.0f);
        lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.RAINBOW);
        lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.RAINBOW);


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        folderPath = stringBuilder.toString();
        fledit = (RelativeLayout) findViewById(R.id.fledit);
        mViews = new ArrayList();
        img = (ImageView) findViewById(R.id.img);
        imgblur = (ImageView) findViewById(R.id.imgblur);
        tv_songtop = (TextView) findViewById(R.id.tv1);
        tv_songtop2 = (TextView) findViewById(R.id.tv2);
        next = (ImageView) findViewById(R.id.ivBtnNext);
        edT = this;
        intK = 3;
        intSave = 0;
        boelSave = Boolean.valueOf(false);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        enterAnimLayout = (PVEMLyricalStatusMaker_AnimLay) findViewById(R.id.anim_layout);
        frameLayout.setVisibility(View.GONE);
        isFlying = false;

        img1 = (ImageView) findViewById(R.id.img1);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder2.append("/");
        stringBuilder2.append(getResources().getString(R.string.app_name));
        File file = new File(stringBuilder2.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        videoIp = new File(file, "video.txt");
        if (!videoIp.exists()) {
            videoIp.delete();
        }
        mCounter = f8i;
        intFullScreen = 0;
        intAnim = 1;
        intAnimModel = 0;
        intKlikLayar = 0;
        textSize = 50;
        klik = 0;
        tText = SupportMenu.CATEGORY_MASK;
        derajat = 0.0f;
        mCounter = sentences.length - 1;
        stic = (Button) findViewById(R.id.sticker);
        text = (Button) findViewById(R.id.text);
        col = (Button) findViewById(R.id.color);
        style = (Button) findViewById(R.id.style);
        stylelist = (RecyclerView) findViewById(R.id.recyclerstyle);
        try {
            fontstylearry = getAssets().list("FontStyle");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mLayoutManager = new LinearLayoutManager(this, 0, false);
        stylelist.setLayoutManager(mLayoutManager);
        initImageLoader();
        imageLoader = ImageLoader.getInstance();
        Horizontal_frameview = (PVEMLyricalStatusMaker_HorizontalLstView) findViewById(R.id.Horizontal_frameview);
        bottomlayout = (LinearLayout) findViewById(R.id.bottomlayout);
        filenames = new ArrayList();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(Environment.getExternalStorageDirectory());
        stringBuilder3.append(File.separator);
        stringBuilder3.append(getResources().getString(R.string.app_name));
        stringBuilder3.append("/temp");
        new File(stringBuilder3.toString()).listFiles();
        img.setImageBitmap(Bitmap.createScaledBitmap((Bitmap) PVEMLyricalStatusMaker_AlbumNew.bitimages.get(incr), PVEMLyricalStatusMaker_Utls2.getScreenWidth(), PVEMLyricalStatusMaker_Utls2.getScreenHeight(), true));
        next.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PVEMLyricalStatusMaker_EditImageActivity.tm = (ImageView) findViewById(R.id.temp);
                popup(0);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
                stringBuilder.append("/");
                stringBuilder.append(getResources().getString(R.string.app_name));
                File file = new File(stringBuilder.toString());
                if (!file.exists()) {
                    file.mkdir();
                }
                File file2 = new File(file, "video.txt");
                if (file2.exists()) {
                    file2.delete();
                }
                if (theam == 0) {
                    frameLayout.setVisibility(View.VISIBLE);
                    handlershow.postDelayed(runnableshow, 0);
                    save(frameLayout);
                } else {
                    new set_theme().execute(new Void[0]);
                }
                if (incr >= 6) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            frameLayout.setVisibility(View.VISIBLE);
                            handlershow.postDelayed(runnableshow, 0);
                            save(frameLayout);
                        }
                    }, 1000);
                    Toast.makeText(getApplicationContext(), "Image over", Toast.LENGTH_SHORT).show();
                }
            }
        });
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("array");
        stringBuilder4.append(PVEMLyricalStatusMaker_SongLyricsActivity.songposition);
        getRes2 = getResources().getIdentifier(stringBuilder4.toString(), "array", getPackageName());
        String[] split = getResources().getStringArray(getRes2)[incr].split(" ");
        int length = split.length / 2;
        int i;
        StringBuilder stringBuilder5;
        if (split.length > 9) {
            int i2;
            length = split.length / 3;
            for (i = 0; i < length; i++) {
                stringBuilder5 = new StringBuilder();
                stringBuilder5.append(to);
                stringBuilder5.append(" ");
                stringBuilder5.append(split[i]);
                to = stringBuilder5.toString();
            }
            i = length;
            while (true) {
                i2 = length + length;
                if (i >= i2) {
                    break;
                }
                stringBuilder5 = new StringBuilder();
                stringBuilder5.append(to2);
                stringBuilder5.append(" ");
                stringBuilder5.append(split[i]);
                to2 = stringBuilder5.toString();
                i++;
            }
            while (i2 < split.length) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(to3);
                stringBuilder.append(" ");
                stringBuilder.append(split[i2]);
                to3 = stringBuilder.toString();
                i2++;
            }
        } else {
            i = split.length / 2;
            for (length = 0; length < i; length++) {
                stringBuilder5 = new StringBuilder();
                stringBuilder5.append(to);
                stringBuilder5.append(" ");
                stringBuilder5.append(split[length]);
                to = stringBuilder5.toString();
            }
            while (i < split.length) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(to2);
                stringBuilder3.append(" ");
                stringBuilder3.append(split[i]);
                to2 = stringBuilder3.toString();
                i++;
            }
            to3 = "";
        }
        Bitmap scaleCenterCrop = PVEMLyricalStatusMaker_ImgScaUtilities.scaleCenterCrop((Bitmap) PVEMLyricalStatusMaker_AlbumNew.bitimages.get(incr), PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT);
        scaleCenterCrop.copy(scaleCenterCrop.getConfig(), true);
        Canvas canvas = new Canvas(scaleCenterCrop);
        Paint paint = new Paint();
        TextView textView = tv_songtop;
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append("");
        stringBuilder4.append(to);
        textView.setText(stringBuilder4.toString());
        textView = tv_songtop2;
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append("");
        stringBuilder4.append(to2);
        textView.setText(stringBuilder4.toString());
        tv_songtop.setTextColor(Color.parseColor("#000000"));
        tv_songtop2.setTextColor(Color.parseColor("#000000"));
        tm = (ImageView) findViewById(R.id.temp);
        tm.setImageBitmap((Bitmap) PVEMLyricalStatusMaker_AlbumNew.bitimages.get(0));

        StringBuilder stringBuilderv = new StringBuilder();
        stringBuilderv.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilderv.append("/");
        stringBuilderv.append(getResources().getString(R.string.app_name));
        File filev = new File(stringBuilder.toString());
        if (!filev.exists()) {
            filev.mkdir();
        }
        File file2 = new File(file, "video.txt");
        if (file2.exists()) {
            file2.delete();
        }


        Intent intent = new Intent(getApplicationContext(),
                PVEMLyricalStatusMaker_ImgService.class);
        startService(intent);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
                stringBuilder.append("/");
                stringBuilder.append(getResources().getString(R.string.app_name));
                File file = new File(stringBuilder.toString());
                if (!file.exists()) {
                    file.mkdir();
                }
                File file2 = new File(file, "video.txt");
                if (file2.exists()) {
                    file2.delete();
                }
                if (theam == 0) {
                    frameLayout.setVisibility(View.VISIBLE);
                    handlershow.postDelayed(runnableshow, 0);
                    save(frameLayout);
                    return;
                }
                new set_theme().execute(new Void[0]);
            }
        }, 500);
    }

    public void setFont(int i) {
        AssetManager assets = getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FontStyle/");
        stringBuilder.append(fontstylearry[i]);
        Typeface.createFromAsset(assets, stringBuilder.toString());
    }

    public static void hideKeyboard(AppCompatActivity activity) {
        try {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 2);
        } catch (Exception unused) {
        }
    }

    private void openDialog(boolean z) {
        new AmbilWarnaDialog(this, currentColor, z, new OnAmbilWarnaListener() {
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                currentColor = i;
            }

            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Toast.makeText(PVEMLyricalStatusMaker_EditImageActivity.this, "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        }).show();
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

    private void saveImage(int i, Bitmap bitmap) {
        StringBuilder stringBuilder;
        if (folderPath == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append("/");
            stringBuilder.append(getResources().getString(R.string.app_name));
            folderPath = stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(folderPath);
        stringBuilder2.append("/temp3");
        File file = new File(stringBuilder2.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("slide_");
        stringBuilder3.append(String.format(Locale.US, "%05d", new Object[]{Integer.valueOf(i)}));
        stringBuilder3.append(".jpg");
        File file2 = new File(file, stringBuilder3.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("File name ====>>");
        stringBuilder.append(file2);
        Log.e("", stringBuilder.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImagelast(int i, Bitmap bitmap) {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(i);
        stringBuilder2.append("");
        Log.i("hi", stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(i);
        stringBuilder2.append("");
        Log.e("hi", stringBuilder2.toString());
        if (folderPath == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append("/");
            stringBuilder.append(getResources().getString(R.string.app_name));
            folderPath = stringBuilder.toString();
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(folderPath);
        stringBuilder2.append("/temp6");
        File file = new File(stringBuilder2.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("slide_");
        stringBuilder3.append(String.format(Locale.US, "%05d", new Object[]{Integer.valueOf(i)}));
        stringBuilder3.append(".jpg");
        File file2 = new File(file, stringBuilder3.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("File name ====>>");
        stringBuilder.append(file2);
        Log.e("", stringBuilder.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String saveImagetext(int i, Bitmap bitmap) {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(i);
        stringBuilder2.append("");
        Log.i("hi", stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(i);
        stringBuilder2.append("");
        Log.e("hi", stringBuilder2.toString());
        if (folderPath == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append("/");
            stringBuilder.append(getResources().getString(R.string.app_name));
            folderPath = stringBuilder.toString();
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(folderPath);
        stringBuilder2.append("/temp2");
        File file = new File(stringBuilder2.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("slide_");
        stringBuilder3.append(String.format(Locale.US, "%05d", new Object[]{Integer.valueOf(i)}));
        stringBuilder3.append(".jpg");
        File file2 = new File(file, stringBuilder3.toString());
        appendVideoLog(String.format("file '%s'", new Object[]{file2.getAbsolutePath()}));
        stringBuilder = new StringBuilder();
        stringBuilder.append("File name ====>>");
        stringBuilder.append(file2);
        Log.e("", stringBuilder.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file2.getAbsolutePath();
    }

    private void loadFFMpegBinary() {
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                public void onSuccess() {
                    super.onSuccess();
                    Log.e("Sucess to Load Libarary", "sdsf");
                }

                public void onFailure() {
                    Log.e("Fail to Load Libarary", "sdsf");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(e);
            Log.e("Fail to Load Libarary", stringBuilder.toString());
        }
    }

    Bitmap BlurImage(Bitmap bitmap) {
        try {
            RenderScript create = RenderScript.create(getApplicationContext());
            Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
            ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
            create2.setRadius(30.0f);
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

    private void execFFmpegBinary(String[] strArr) {
        try {
            ffmpeg.execute(strArr, new ExecuteBinaryResponseHandler() {
                public void onFailure(String str) {
                    Log.e("hi", str);
                }

                public void onSuccess(String str) {
                    removeOnlyMusic();
                    File file = new File(videoname);
                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.getAbsolutePath()}, new String[]{"mp4"}, null);
                    handler.postDelayed(runnable, 500);
                }

                public void onProgress(String str) {
                    Log.e("hi", str);
                }

                public void onStart() {
                    Log.e(" FFMPEG onStart :", "data");
                }

                public void onFinish() {
                    pd.dismiss();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    private void removeOnlyMusic() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        File file = new File(stringBuilder.toString());
        if (file.exists()) {
            String str = "tempmusic";
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().startsWith(str)) {
                        file2.delete();
                    }
                }
            }
        }
    }

    private void initImageLoader() {
        optsFull = new Builder().cacheOnDisk(true).imageScaleType(ImageScaleType.NONE).showImageOnLoading(0).bitmapConfig(Config.ARGB_4444).build();
        optsThumb = new Builder().cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.ARGB_4444).build();
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(optsThumb).memoryCache(new WeakMemoryCache()).build());
    }

    private Bitmap getBitmapFromView(String str, String str2, String str3, String str4, String str5, Typeface typeface, boolean z, boolean z2, boolean z3) {
        Paint paint;
        Bitmap bitmap;
        Paint paint2;
        float f;
        Paint paint3;
        Paint paint4;
        String str6 = str;
        String str7 = str2;
        String str8 = str3;
        String str9 = str5;
        Typeface typeface2 = typeface;
        Bitmap createBitmap = Bitmap.createBitmap(PVEMLyricalStatusMaker_Utls2.getScreenWidth(), PVEMLyricalStatusMaker_Utls2.getScreenHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint5 = new Paint();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(str4);
        paint5.setColor(Color.parseColor(stringBuilder.toString()));
        paint5.setStyle(Style.FILL);
        Paint paint6 = new Paint();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(str9);
        paint6.setColor(Color.parseColor(stringBuilder.toString()));
        paint6.setTextSize((float) (PVEMLyricalStatusMaker_Utls2.getScreenWidth() / 13));
        paint6.setTypeface(typeface2);
        Paint paint7 = new Paint();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(str9);
        paint7.setColor(Color.parseColor(stringBuilder.toString()));
        paint7.setTextSize((float) (PVEMLyricalStatusMaker_Utls2.getScreenWidth() / 13));
        paint7.setTypeface(typeface2);
        if (str8.matches(".*[a-zA-Z]+.*")) {
            Paint paint8;
            paint = new Paint();
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(str9);
            paint.setColor(Color.parseColor(stringBuilder.toString()));
            paint.setTextSize((float) (PVEMLyricalStatusMaker_Utls2.getScreenWidth() / 13));
            paint.setTypeface(typeface2);
            float measureText = paint.measureText(str8);
            if (z) {
                bitmap = createBitmap;
                paint2 = paint;
                f = 0.0f;
                paint3 = paint7;
                paint8 = paint6;
                paint4 = paint5;
                canvas.drawRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 580) / 1920), measureText, (float) ((getResources().getDisplayMetrics().heightPixels * 760) / 1920), paint5);
            } else {
                bitmap = createBitmap;
                paint2 = paint;
                paint3 = paint7;
                paint8 = paint6;
                paint4 = paint5;
                f = 0.0f;
                if (z2) {
                    canvas.drawPath(RoundedRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 580) / 1920), measureText, (float) ((getResources().getDisplayMetrics().heightPixels * 760) / 1920), 50.0f, 50.0f, false, true, true, false), paint4);
                } else if (z3) {
                    canvas.drawRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 580) / 1920), measureText, (float) ((getResources().getDisplayMetrics().heightPixels * 760) / 1920), paint4);
                } else {
                    canvas.drawPath(RoundedRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 580) / 1920), measureText, (float) ((getResources().getDisplayMetrics().heightPixels * 760) / 1920), 50.0f, 50.0f, false, true, true, false), paint4);
                }
            }
            canvas.drawText(str8, f, (float) ((getResources().getDisplayMetrics().heightPixels * 690) / 1920), paint2);
            paint2 = paint8;
        } else {
            bitmap = createBitmap;
            paint3 = paint7;
            paint4 = paint5;
            f = 0.0f;
            paint2 = paint6;
        }
        float measureText2 = paint2.measureText(str6);
        paint2.getTextSize();
        paint = paint3;
        float measureText3 = paint.measureText(str7);
        paint.getTextSize();
        float f2;
        if (z) {
            f2 = f;
            float f3 = (float) ((getResources().getDisplayMetrics().heightPixels * 280) / 1920);
            Paint paint9 = paint4;
            canvas.drawRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 100) / 1920), measureText2, f3, paint9);
            canvas.drawRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 340) / 1920), measureText3, (float) ((getResources().getDisplayMetrics().heightPixels * 520) / 1920), paint9);
            canvas.drawText(str6, f2, (float) ((getResources().getDisplayMetrics().heightPixels * 210) / 1920), paint2);
            canvas.drawText(str7, f2, (float) ((getResources().getDisplayMetrics().heightPixels * 450) / 1920), paint);
        } else {
            f2 = f;
            paint6 = paint4;
            int i;
            int i2;
            if (z2) {
                canvas.drawPath(RoundedRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 100) / 1920), measureText2, (float) ((getResources().getDisplayMetrics().heightPixels * 280) / 1920), 50.0f, 50.0f, false, true, true, false), paint6);
                canvas.drawPath(RoundedRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 340) / 1920), measureText3, (float) ((getResources().getDisplayMetrics().heightPixels * 520) / 1920), 50.0f, 50.0f, false, true, true, false), paint6);
                canvas.drawText(str6, f2, (float) ((getResources().getDisplayMetrics().heightPixels * 210) / 1920), paint2);
                canvas.drawText(str7, f2, (float) ((getResources().getDisplayMetrics().heightPixels * 450) / 1920), paint);
            } else if (!z3) {
                canvas.drawPath(RoundedRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 100) / 1920), measureText2, (float) ((getResources().getDisplayMetrics().heightPixels * 280) / 1920), 50.0f, 50.0f, false, true, true, false), paint6);
                canvas.drawPath(RoundedRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 340) / 1920), measureText3, (float) ((getResources().getDisplayMetrics().heightPixels * 520) / 1920), 50.0f, 50.0f, false, true, true, false), paint6);
                canvas.drawText(str6, f2, (float) ((getResources().getDisplayMetrics().heightPixels * 210) / 1920), paint2);
                canvas.drawText(str7, f2, (float) ((getResources().getDisplayMetrics().heightPixels * 450) / 1920), paint);
            } else if (measureText2 > measureText3) {
                canvas.drawPath(HeartRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 100) / 1920), measureText2, (float) ((getResources().getDisplayMetrics().heightPixels * 280) / 1920), 50.0f, 50.0f, false, true, true, false), paint6);
                i = (int) ((measureText2 - f2) + ((float) (((getResources().getDisplayMetrics().heightPixels * 280) / 1920) - ((getResources().getDisplayMetrics().heightPixels * 100) / 1920))));
                i2 = i / 9;
                i /= 4;
                canvas.drawText(str6, 20.0f, (float) (50 + i), paint2);
                canvas.drawText(str7, 50.0f, (float) (100 + i), paint);
            } else {
                canvas.drawPath(HeartRect(0.0f, (float) ((getResources().getDisplayMetrics().heightPixels * 100) / 1920), measureText3, (float) ((getResources().getDisplayMetrics().heightPixels * 280) / 1920), 50.0f, 50.0f, false, true, true, false), paint6);
                i = (int) ((measureText3 - f2) + ((float) (((getResources().getDisplayMetrics().heightPixels * 280) / 1920) - ((getResources().getDisplayMetrics().heightPixels * 100) / 1920))));
                i2 = i / 9;
                i /= 4;
                canvas.drawText(str6, 20.0f, (float) (100 + i), paint2);
                canvas.drawText(str7, 50.0f, (float) (150 + i), paint);
            }
        }
        return bitmap;
    }

    public static Path RoundedRect(float f, float f2, float f3, float f4, float f5, float f6, boolean z, boolean z2, boolean z3, boolean z4) {
        Path path = new Path();
        if (f5 < 0.0f) {
            f5 = 0.0f;
        }
        if (f6 < 0.0f) {
            f6 = 0.0f;
        }
        f = f3 - f;
        f4 -= f2;
        float f7 = f / 2.0f;
        if (f5 > f7) {
            f5 = f7;
        }
        f7 = f4 / 2.0f;
        if (f6 > f7) {
            f6 = f7;
        }
        f -= 2.0f * f5;
        f4 -= 2.0f * f6;
        path.moveTo(f3, f2 + f6);
        if (z2) {
            f2 = -f6;
            path.rQuadTo(0.0f, f2, -f5, f2);
        } else {
            path.rLineTo(0.0f, -f6);
            path.rLineTo(-f5, 0.0f);
        }
        path.rLineTo(-f, 0.0f);
        if (z) {
            f2 = -f5;
            path.rQuadTo(f2, 0.0f, f2, f6);
        } else {
            path.rLineTo(-f5, 0.0f);
            path.rLineTo(0.0f, f6);
        }
        path.rLineTo(0.0f, f4);
        if (z4) {
            path.rQuadTo(0.0f, f6, f5, f6);
        } else {
            path.rLineTo(0.0f, f6);
            path.rLineTo(f5, 0.0f);
        }
        path.rLineTo(f, 0.0f);
        if (z3) {
            path.rQuadTo(f5, 0.0f, f5, -f6);
        } else {
            path.rLineTo(f5, 0.0f);
            path.rLineTo(0.0f, -f6);
        }
        path.rLineTo(0.0f, -f4);
        path.close();
        return path;
    }

    public Path HeartRect(float f, float f2, float f3, float f4, float f5, float f6, boolean z, boolean z2, boolean z3, boolean z4) {
        float f7 = f6;
        int i = (int) ((f3 - f) + (f4 - f2));
        int i2 = i / 9;
        int i3 = i / 4;
        float f8 = (float) (i / 2);
        float f9 = 0.0f + f8;
        int i4 = (int) f9;
        Point point = new Point(i4, (int) f7);
        float f10 = (float) i2;
        float f11 = (float) i3;
        int i5 = (int) (f7 + f11);
        Point point2 = new Point((int) (f9 + f10), i5);
        float f12 = (float) i;
        Point point3 = new Point((int) (0.0f + f12), i5);
        float f13 = (float) (2 * i2);
        i3 = (int) ((f8 + f7) + ((float) (i3 / 2)));
        Point point4 = new Point((int) (f9 + f13), i3);
        float f14 = (float) (3 * i2);
        f7 += f12;
        i = (int) f7;
        Point point5 = new Point((int) (f9 + f14), i);
        Point point6 = new Point(i4, (int) (f7 - f11));
        Point point7 = new Point((int) (f9 - f14), i);
        Point point8 = new Point((int) (f9 - f13), i3);
        Point point9 = new Point((int) 0.0f, i5);
        Point point10 = new Point((int) (f9 - f10), i5);
        Path path = new Path();
        path.moveTo((float) point.x, (float) point.y);
        path.lineTo((float) point2.x, (float) point2.y);
        path.lineTo((float) point3.x, (float) point3.y);
        path.lineTo((float) point4.x, (float) point4.y);
        path.lineTo((float) point5.x, (float) point5.y);
        path.lineTo((float) point6.x, (float) point6.y);
        path.lineTo((float) point6.x, (float) point6.y);
        path.lineTo((float) point7.x, (float) point7.y);
        path.lineTo((float) point8.x, (float) point8.y);
        path.lineTo((float) point9.x, (float) point9.y);
        path.lineTo((float) point10.x, (float) point10.y);
        path.lineTo((float) point.x, (float) point.y);
        path.close();
        path.close();
        return path;
    }

    private Bitmap getBitmapFromView1(String str, String str2, String str3, String str4, String str5, Typeface typeface, boolean z, boolean z2, boolean z3) {
        float measureText;
        String str6 = str;
        String str7 = str2;
        String str8 = str3;
        String str9 = str5;
        Typeface typeface2 = typeface;
        Bitmap createBitmap = Bitmap.createBitmap(PVEMLyricalStatusMaker_Utls2.getScreenWidth(), PVEMLyricalStatusMaker_Utls2.getScreenHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(str4);
        paint.setColor(Color.parseColor(stringBuilder.toString()));
        paint.setStyle(Style.FILL);
        Paint paint2 = new Paint();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(str9);
        paint2.setColor(Color.parseColor(stringBuilder.toString()));
        paint2.setTextSize((float) (PVEMLyricalStatusMaker_Utls2.getScreenWidth() / 13));
        paint2.setTypeface(typeface2);
        Paint paint3 = new Paint();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(str9);
        paint3.setColor(Color.parseColor(stringBuilder.toString()));
        paint3.setTextSize((float) (PVEMLyricalStatusMaker_Utls2.getScreenWidth() / 13));
        paint3.setTypeface(typeface2);
        if (str8.matches(".*[a-zA-Z]+.*")) {
            Paint paint4 = new Paint();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(str9);
            paint4.setColor(Color.parseColor(stringBuilder2.toString()));
            paint4.setTextSize((float) (PVEMLyricalStatusMaker_Utls2.getScreenWidth() / 13));
            paint4.setTypeface(typeface2);
            measureText = paint4.measureText(str8);
            canvas.drawRect(((float) PVEMLyricalStatusMaker_Utls2.getScreenWidth()) - measureText, (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 320) / 1920)), (float) PVEMLyricalStatusMaker_Utls2.getScreenWidth(), (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 140) / 1920)), paint);
            canvas.drawText(str8, ((float) PVEMLyricalStatusMaker_Utls2.getScreenWidth()) - measureText, (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 220) / 1920)), paint3);
        }
        float measureText2 = paint3.measureText(str7);
        paint3.getTextSize();
        measureText = paint2.measureText(str6);
        paint2.getTextSize();
        Paint paint5 = paint;
        canvas.drawRect(((float) PVEMLyricalStatusMaker_Utls2.getScreenWidth()) - measureText, (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 800) / 1920)), (float) PVEMLyricalStatusMaker_Utls2.getScreenWidth(), (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 620) / 1920)), paint5);
        canvas.drawRect(((float) PVEMLyricalStatusMaker_Utls2.getScreenWidth()) - measureText2, (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 560) / 1920)), (float) PVEMLyricalStatusMaker_Utls2.getScreenWidth(), (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 380) / 1920)), paint5);
        canvas.drawText(str6, ((float) PVEMLyricalStatusMaker_Utls2.getScreenWidth()) - measureText, (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 690) / 1920)), paint2);
        canvas.drawText(str7, ((float) PVEMLyricalStatusMaker_Utls2.getScreenWidth()) - measureText2, (float) (PVEMLyricalStatusMaker_Utls2.getScreenHeight() - ((getResources().getDisplayMetrics().heightPixels * 456) / 1920)), paint3);
        return createBitmap;
    }

    protected int getRandomHSVColor() {
        return Color.HSVToColor(255, new float[]{(float) mRandom.nextInt(361), 1.0f, 1.0f});
    }

    void stopSaveAuto() {
        handlerSave.removeMessages(0);
        loadingDialogs.dismiss();
        new ProcessVideo().execute(new Void[0]);
    }

    public void save(View view) {
        mx = new Random().nextInt(5) + 4;
        mx2 = new Random().nextInt(4) + 0;
        loadingDialogs = ProgressDialog.show(this, "", "Please wait...\nImages are arrange....", true, false);
        new Timer().schedule(new TimerTask() {

            class C04931 implements Runnable {
                public void run() {
                }

                C04931() {
                }
            }

            public void run() {
                new Handler(Looper.getMainLooper()).post(new C04931());
            }
        }, 3000);
        createDirIfNotExists("GIFTextMaker");
        boelSave = Boolean.valueOf(true);
        mCounter = sentences.length - 1;
        lyricalVidMakerHTextView.animateText(sentences[mCounter]);
        handlerSave.postDelayed(runnableSave, 0);
    }

    public boolean createDirIfNotExists(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.app_name));
        stringBuilder.append("/temp2");
        File file = new File(stringBuilder.toString());
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                return false;
            }
        }
        return true;
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

    protected void onResume() {
        super.onResume();
        registerReceiver(serviceCompleted, new IntentFilter("com.servicecomplete"));
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(serviceCompleted);
    }

    public void popup(int i) {
        View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.lyrical_popup_gif, null);
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(inflate, -1, -1);
        if (VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.ll_pop);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 957) / 1080, (getResources().getDisplayMetrics().heightPixels * 317) / 1920);
        layoutParams.addRule(13);
        relativeLayout.setLayoutParams(layoutParams);
        Glide.with((AppCompatActivity) this).load("file:///android_asset/gif2.gif").into((ImageView) inflate.findViewById(R.id.gif));
        mPopupWindow.showAtLocation(next, 17, 0, 0);
    }


    class C04941 implements Runnable {

        class C04921 implements OnScanCompletedListener {
            C04921() {
            }

            public void onScanCompleted(String str, Uri uri) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Scanned ");
                stringBuilder.append(str);
                stringBuilder.append(":");
                Log.i("ExternalStorage", stringBuilder.toString());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("-> uri=");
                stringBuilder2.append(uri);
                Log.i("ExternalStorage", stringBuilder2.toString());
            }
        }

        C04941() {
        }

        public void run() {
            PVEMLyricalStatusMaker_EditImageActivity mRYTCHER_EditImagesNew;
            int i;
            Object obj = "";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append("/");
            stringBuilder.append(getResources().getString(R.string.app_name));
            PVEMLyricalStatusMaker_EditImageActivity.folderPath = stringBuilder.toString();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(PVEMLyricalStatusMaker_EditImageActivity.folderPath);
            stringBuilder2.append("/temp6");
            File file = new File(stringBuilder2.toString());
            frameLayout.setDrawingCacheEnabled(true);
            frameLayout.buildDrawingCache();
            Bitmap drawingCache = frameLayout.getDrawingCache();
            try {
                animbit = Bitmap.createScaledBitmap(drawingCache, 720, 1280, true);
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(ti);
                stringBuilder3.append("");
                Log.i("hi", stringBuilder3.toString());
            } catch (RuntimeException unused) {
            }
            if (ti <= 138) {
                obj = saveImagetext(ti, animbit);
                mRYTCHER_EditImagesNew = PVEMLyricalStatusMaker_EditImageActivity.this;
                mRYTCHER_EditImagesNew.ti++;
            }
            mRYTCHER_EditImagesNew = PVEMLyricalStatusMaker_EditImageActivity.this;
            mRYTCHER_EditImagesNew.imagecount++;
            frameLayout.destroyDrawingCache();
            mRYTCHER_EditImagesNew = PVEMLyricalStatusMaker_EditImageActivity.this;
            mRYTCHER_EditImagesNew.intSave += 41;
            if (imagecount == 22) {
                imagecount = 0;
                handlershow.postDelayed(runnableshow, 0);
                Collections.reverse(svtxt);
                for (i = 0; i < svtxt.size(); i++) {
                    appendVideoLog(String.format("file '%s'", new Object[]{svtxt.get(i)}));
                }
                svtxt.clear();
            } else {
                svtxt.add((String) obj);
            }
            if (intSave > (((intK * 4) * 100) + 700) * 3) {
                Collections.reverse(svtxt);
                for (i = 0; i < svtxt.size(); i++) {
                    appendVideoLog(String.format("file '%s'", new Object[]{svtxt.get(i)}));
                }
                handlerSave.removeMessages(0);
                stopSaveAuto();
                return;
            }
            handlerSave.postDelayed(this, 10);
        }
    }

    class C04952 implements Runnable {
        C04952() {
        }

        public void run() {
            if (intAnimModel >= 6) {
                intAnimModel = 0;
                return;
            }
            StringBuilder stringBuilder;
            LayoutParams layoutParams;
            PVEMLyricalStatusMaker_TextViewV mRYTCHER_PVEM_LyricalStatusMaker_TextViewV;
            StringBuilder stringBuilder2;
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder3.append("/Status Video Maker/temp");
            new File(stringBuilder3.toString()).list();
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder3.append("/Status Video Maker/temp4");
            new File(stringBuilder3.toString()).list();
            Options options = new Options();
            try {
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap((Bitmap) PVEMLyricalStatusMaker_AlbumNew.bitimages.get(intAnimModel), displayMetrics.widthPixels, displayMetrics.heightPixels, false);
                PVEMLyricalStatusMaker_EditImageActivity.img1.setImageBitmap(createScaledBitmap);
                PVEMLyricalStatusMaker_EditImageActivity.tm.setImageBitmap(createScaledBitmap);
            } catch (RuntimeException e) {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append(e);
                stringBuilder4.append("");
                Log.e("hi", stringBuilder4.toString());
            }
            PVEMLyricalStatusMaker_EditImageActivity.img1.startAnimation(AnimationUtils.loadAnimation(PVEMLyricalStatusMaker_EditImageActivity.this, R.anim.zoom));
            PVEMLyricalStatusMaker_EditImageActivity mRYTCHER_EditImagesNew = PVEMLyricalStatusMaker_EditImageActivity.this;
            mRYTCHER_EditImagesNew.intAnimModel++;
            mCounter = mCounter >= sentences.length + -1 ? 0 : mCounter + 1;
            String str = getResources().getStringArray(getRes2)[intAnimModel - 1];
            to = "";
            to2 = "";
            to3 = "";
            String[] split = str.split(" ");
            int length = split.length / 2;
            int i;
            PVEMLyricalStatusMaker_EditImageActivity mRYTCHER_EditImagesNew2;
            StringBuilder stringBuilder5;
            if (split.length > 6) {
                int i2;
                length = split.length / 3;
                for (i = 0; i < length; i++) {
                    mRYTCHER_EditImagesNew2 = PVEMLyricalStatusMaker_EditImageActivity.this;
                    stringBuilder5 = new StringBuilder();
                    stringBuilder5.append(to);
                    stringBuilder5.append(" ");
                    stringBuilder5.append(split[i]);
                    mRYTCHER_EditImagesNew2.to = stringBuilder5.toString();
                }
                i = length;
                while (true) {
                    i2 = length + length;
                    if (i >= i2) {
                        break;
                    }
                    mRYTCHER_EditImagesNew2 = PVEMLyricalStatusMaker_EditImageActivity.this;
                    stringBuilder5 = new StringBuilder();
                    stringBuilder5.append(to2);
                    stringBuilder5.append(" ");
                    stringBuilder5.append(split[i]);
                    mRYTCHER_EditImagesNew2.to2 = stringBuilder5.toString();
                    i++;
                }
                while (i2 < split.length) {
                    PVEMLyricalStatusMaker_EditImageActivity mRYTCHER_EditImagesNew3 = PVEMLyricalStatusMaker_EditImageActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(to3);
                    stringBuilder.append(" ");
                    stringBuilder.append(split[i2]);
                    mRYTCHER_EditImagesNew3.to3 = stringBuilder.toString();
                    i2++;
                }
                lyricalVidMakerHTextView3.setBackgroundColor(Color.parseColor("#50000000"));
            } else {
                length = split.length / 2;
                for (i = 0; i < length; i++) {
                    mRYTCHER_EditImagesNew2 = PVEMLyricalStatusMaker_EditImageActivity.this;
                    stringBuilder5 = new StringBuilder();
                    stringBuilder5.append(to);
                    stringBuilder5.append(" ");
                    stringBuilder5.append(split[i]);
                    mRYTCHER_EditImagesNew2.to = stringBuilder5.toString();
                }
                while (length < split.length) {
                    PVEMLyricalStatusMaker_EditImageActivity mRYTCHER_EditImagesNew4 = PVEMLyricalStatusMaker_EditImageActivity.this;
                    StringBuilder stringBuilder6 = new StringBuilder();
                    stringBuilder6.append(to2);
                    stringBuilder6.append(" ");
                    stringBuilder6.append(split[length]);
                    mRYTCHER_EditImagesNew4.to2 = stringBuilder6.toString();
                    length++;
                }
                to3 = "";
                lyricalVidMakerHTextView3.setBackgroundColor(Color.parseColor("#00000000"));
            }
            AssetManager assets = getApplicationContext().getAssets();
            length = new Random().nextInt(11);
            stringBuilder = new StringBuilder();
            stringBuilder.append("FontStyle/");
            stringBuilder.append(fontstylearry[1]);
            Typeface createFromAsset = Typeface.createFromAsset(assets, stringBuilder.toString());
            lyricalVidMakerHTextView2.setTypeface(createFromAsset);
            lyricalVidMakerHTextView3.setTypeface(createFromAsset);
            lyricalVidMakerHTextView2.setBackgroundColor(Color.parseColor("#50000000"));
            lyricalVidMakerHTextView.setBackgroundColor(Color.parseColor("#00000000"));
            lyricalVidMakerHTextView.setTypeface(createFromAsset);
            if (intAnimModel == 1) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 70;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 180;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 280;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.ANVIL.SCALE);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.SCALE);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.SCALE);
                rope = YoYo.with(Techniques.RotateIn).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.RotateIn).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.RotateIn).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                lyricalVidMakerHTextView2.setTextSize(33.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                StringBuilder stringBuilder7 = new StringBuilder();
                stringBuilder7.append(" ");
                stringBuilder7.append(to2);
                stringBuilder7.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder7.toString());
                lyricalVidMakerHTextView.setTextSize(33.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder7 = new StringBuilder();
                stringBuilder7.append(" ");
                stringBuilder7.append(to);
                stringBuilder7.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder7.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder7 = new StringBuilder();
                stringBuilder7.append(" ");
                stringBuilder7.append(to3);
                stringBuilder7.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder7.toString());
                lyricalVidMakerHTextView3.setTextSize(33.0f);
            }
            if (intAnimModel == 2) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 300;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 90;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                rope = YoYo.with(Techniques.SlideInDown).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.SlideInDown).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.SlideInDown).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to2);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                lyricalVidMakerHTextView.setTextSize(33.0f);
                lyricalVidMakerHTextView2.setTextSize(33.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to3);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                lyricalVidMakerHTextView3.setTextSize(33.0f);
            }
            if (intAnimModel == 3) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 1;
                layoutParams.topMargin = 70;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 1;
                layoutParams.topMargin = 180;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 1;
                layoutParams.topMargin = 280;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                rope = YoYo.with(Techniques.SlideInLeft).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.SlideInLeft).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.SlideInLeft).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to2);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                lyricalVidMakerHTextView.setTextSize(33.0f);
                lyricalVidMakerHTextView2.setTextSize(33.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to3);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                lyricalVidMakerHTextView3.setTextSize(33.0f);
            }
            if (intAnimModel == 4) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 85;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 300;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                rope = YoYo.with(Techniques.SlideInRight).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.SlideInRight).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.SlideInRight).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to2);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                lyricalVidMakerHTextView.setTextSize(33.0f);
                lyricalVidMakerHTextView2.setTextSize(33.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to3);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                lyricalVidMakerHTextView3.setTextSize(33.0f);
            }
            if (intAnimModel == 5) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 280;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 180;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 70;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                rope = YoYo.with(Techniques.SlideInUp).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.SlideInUp).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.SlideInUp).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to2);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                lyricalVidMakerHTextView.setTextSize(33.0f);
                lyricalVidMakerHTextView2.setTextSize(33.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" ");
                stringBuilder3.append(to3);
                stringBuilder3.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder3.toString());
                lyricalVidMakerHTextView3.setTextSize(33.0f);
            }
            if (intAnimModel == 6) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 3;
                layoutParams.topMargin = 85;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 3;
                layoutParams.topMargin = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 3;
                layoutParams.topMargin = 300;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                rope = YoYo.with(Techniques.RotateIn).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.RotateIn).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.RotateIn).duration(700).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to2);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView.setTextSize(33.0f);
                lyricalVidMakerHTextView2.setTextSize(33.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to3);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView3.setTextSize(33.0f);
            }
            if (intAnimModel == 7) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 55;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 190;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 290;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                rope = YoYo.with(Techniques.RotateIn).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.RotateIn).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.RotateIn).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to2);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView.setTextSize(25.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to3);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView3.setTextSize(25.0f);
            }
            if (intAnimModel == 8) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 300;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 150;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 0;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                rope = YoYo.with(Techniques.SlideInDown).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.SlideInDown).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.SlideInDown).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to2);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView.setTextSize(25.0f);
                lyricalVidMakerHTextView2.setTextSize(25.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to3);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView3.setTextSize(25.0f);
            }
            if (intAnimModel == 9) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 1;
                layoutParams.topMargin = 50;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 1;
                layoutParams.topMargin = 170;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 1;
                layoutParams.topMargin = 270;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                rope = YoYo.with(Techniques.SlideInLeft).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.SlideInLeft).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.SlideInLeft).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to2);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView.setTextSize(45.0f);
                lyricalVidMakerHTextView2.setTextSize(45.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to3);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView3.setTextSize(45.0f);
            }
            if (intAnimModel == 10) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 55;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 170;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 51;
                layoutParams.topMargin = 270;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.TYPER);
                rope = YoYo.with(Techniques.SlideInRight).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.SlideInRight).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.SlideInRight).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to2);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView.setTextSize(45.0f);
                lyricalVidMakerHTextView2.setTextSize(45.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to3);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView3.setTextSize(45.0f);
            }
            if (intAnimModel == 11) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 290;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 190;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 85;
                layoutParams.bottomMargin = 60;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.EVAPORATE);
                rope = YoYo.with(Techniques.SlideInUp).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.SlideInUp).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.SlideInUp).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to2);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView.setTextSize(45.0f);
                lyricalVidMakerHTextView2.setTextSize(45.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to3);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView3.setTextSize(45.0f);
            }
            if (intAnimModel == 12) {
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 17;
                layoutParams.topMargin = 50;
                lyricalVidMakerHTextView2.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 17;
                layoutParams.topMargin = 170;
                lyricalVidMakerHTextView.setLayoutParams(layoutParams);
                layoutParams = new LayoutParams(-2, -2);
                layoutParams.gravity = 17;
                layoutParams.topMargin = 270;
                lyricalVidMakerHTextView3.setLayoutParams(layoutParams);
                lyricalVidMakerHTextView2.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                lyricalVidMakerHTextView.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                lyricalVidMakerHTextView3.setAnimateType(PVEMLyricalStatusMaker__TextViewVType.FALL);
                rope = YoYo.with(Techniques.RotateIn).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView);
                rope = YoYo.with(Techniques.RotateIn).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView2);
                rope = YoYo.with(Techniques.RotateIn).duration(1000).interpolate(new AccelerateDecelerateInterpolator()).playOn(lyricalVidMakerHTextView3);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to2);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView.setTextSize(45.0f);
                lyricalVidMakerHTextView2.setTextSize(45.0f);
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView2;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV = lyricalVidMakerHTextView3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" ");
                stringBuilder2.append(to3);
                stringBuilder2.append(" ");
                mRYTCHER_PVEM_LyricalStatusMaker_TextViewV.animateText(stringBuilder2.toString());
                lyricalVidMakerHTextView3.setTextSize(45.0f);
            }
        }
    }

    private class ImageAdapter extends BaseAdapter {
        public AppCompatActivity activity;
        private LayoutInflater inflater = null;
        int width;

        public class ViewHolder {
            ImageView iv;
            LinearLayout ll_border;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public ImageAdapter(AppCompatActivity activity) {
            activity = activity;
            inflater = LayoutInflater.from(activity);
            inflater = activity.getLayoutInflater();
            width = activity.getResources().getDisplayMetrics().widthPixels / 4;
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int i) {
            return list.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            View inflate;
            if (view == null) {
                viewHolder = new ViewHolder();
                inflate = inflater.inflate(R.layout.lyrical_sticker, null);
                inflate.setLayoutParams(new AbsListView.LayoutParams(width, width));
                viewHolder.ll_border = (LinearLayout) inflate.findViewById(R.id.ll_border);
                viewHolder.iv = (ImageView) inflate.findViewById(R.id.ivpip_tiny);
                viewHolder.iv.setScaleType(ScaleType.CENTER_INSIDE);
                inflate.setTag(viewHolder);
            } else {
                inflate = view;
                viewHolder = (ViewHolder) view.getTag();
            }
            PVEMLyricalStatusMaker_VItem mRYTCH_HsItem = (PVEMLyricalStatusMaker_VItem) list.get(i);
            if (mRYTCH_HsItem.isAvailable) {
                imageLoader.displayImage(mRYTCH_HsItem.path, viewHolder.iv, optsFull);
            } else {
                imageLoader.displayImage(mRYTCH_HsItem.path, viewHolder.iv, optsThumb);
            }
            return inflate;
        }
    }

    public class Imagesave1 extends AsyncTask<Void, Integer, Void> {
        File imgDir;

        protected void onPreExecute() {
            pd1 = new ProgressDialog(PVEMLyricalStatusMaker_EditImageActivity.this);
            pd1.setTitle(" Wait...");
            pd1.setCancelable(false);
            pd1.show();
        }

        @SuppressLint({"SdCardPath"})
        protected Void doInBackground(Void... voidArr) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
                stringBuilder.append("/");
                stringBuilder.append(getResources().getString(R.string.app_name));
                stringBuilder.append("/temp1");
                String stringBuilder2 = stringBuilder.toString();
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Path: ");
                stringBuilder3.append(stringBuilder2);
                Log.d("Files", stringBuilder3.toString());
                File[] listFiles = new File(stringBuilder2).listFiles();
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append(Environment.getExternalStorageDirectory().getPath());
                stringBuilder4.append("/");
                stringBuilder4.append(getResources().getString(R.string.app_name));
                stringBuilder4.append("/temp2");
                String stringBuilder5 = stringBuilder4.toString();
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Path: ");
                stringBuilder3.append(stringBuilder5);
                Log.d("Files", stringBuilder3.toString());
                File[] listFiles2 = new File(stringBuilder5).listFiles();
                for (int i = 0; i < listFiles.length; i++) {
                    File file = new File(listFiles[i].toString());
                    File file2 = new File(listFiles2[i].toString());
                    Options options = new Options();
                    Bitmap createScaledBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), options), PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT, true);
                    Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(file2.getAbsolutePath(), options), PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT, true);
                    Bitmap createBitmap = Bitmap.createBitmap(PVEMLyricalStatusMaker_Utls2.getScreenWidth(), PVEMLyricalStatusMaker_Utls2.getScreenHeight(), Config.ARGB_8888);
                    Canvas canvas = new Canvas(createBitmap);
                    canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, null);
                    canvas.drawBitmap(createScaledBitmap2, 0.0f, 0.0f, null);
                    StringBuilder stringBuilder6 = new StringBuilder();
                    stringBuilder6.append("");
                    stringBuilder6.append(i);
                    Log.d("hi", stringBuilder6.toString());
                    saveImagelast(i, createBitmap);
                }
            } catch (RuntimeException e) {
                StringBuilder stringBuilder7 = new StringBuilder();
                stringBuilder7.append("");
                stringBuilder7.append(e);
                Log.d("hi", stringBuilder7.toString());
            }
            return null;
        }

        protected void onPostExecute(Void voidR) {
            pd1.dismiss();
            new ProcessVideo().execute(new Void[0]);
            to = "";
            to2 = "";
        }
    }

    public class Imagesave extends AsyncTask<Void, Integer, Void> {
        File imgDir;

        protected void onPreExecute() {
            pd = new ProgressDialog(PVEMLyricalStatusMaker_EditImageActivity.this);
            pd.setTitle("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressLint({"SdCardPath"})
        protected Void doInBackground(Void... voidArr) {
            try {
                fledit.setDrawingCacheEnabled(true);
                fledit.buildDrawingCache();
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(fledit.getDrawingCache(), PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT, true);
                createScaledBitmap.copy(createScaledBitmap.getConfig(), true);
                Bitmap createBitmap = Bitmap.createBitmap(PVEMLyricalStatusMaker_Utls2.VIDEO_WIDTH, PVEMLyricalStatusMaker_Utls2.VIDEO_HEIGHT, createScaledBitmap.getConfig());
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawBitmap((Bitmap) PVEMLyricalStatusMaker_AlbumNew.bitimages.get(incr), 0.0f, 0.0f, null);
                canvas.drawBitmap(createScaledBitmap, new Matrix(), null);
                finalsix.add(createBitmap);
                saveImage(incr, createBitmap);
            } catch (RuntimeException e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(e);
                Log.d("hi", stringBuilder.toString());
            }
            return null;
        }

        protected void onPostExecute(Void voidR) {
            PVEMLyricalStatusMaker_EditImageActivity mRYTCHER_EditImagesNew = PVEMLyricalStatusMaker_EditImageActivity.this;
            mRYTCHER_EditImagesNew.incr++;
            pd.dismiss();
            to = "";
            to2 = "";
            if (PVEMLyricalStatusMaker_EditImageActivity.mViews.size() > 0) {
                for (int i = 0; i < PVEMLyricalStatusMaker_EditImageActivity.mViews.size(); i++) {
                    fledit.removeView((View) PVEMLyricalStatusMaker_EditImageActivity.mViews.get(i));
                }
                PVEMLyricalStatusMaker_EditImageActivity.mViews.clear();
            }
            if (incr < 6) {
                fledit.setDrawingCacheEnabled(false);
                PVEMLyricalStatusMaker_EditImageActivity.img.setImageBitmap(Bitmap.createScaledBitmap((Bitmap) PVEMLyricalStatusMaker_AlbumNew.bitimages.get(incr), PVEMLyricalStatusMaker_Utls2.getScreenWidth(), PVEMLyricalStatusMaker_Utls2.getScreenHeight(), true));
                return;
            }
            frameLayout.setVisibility(View.VISIBLE);
            handlershow.postDelayed(runnableshow, 0);
            save(frameLayout);
        }
    }

    public class ProcessVideo extends AsyncTask<Void, Integer, Void> {
        File imgDir;

        protected void onPostExecute(Void voidR) {
        }

        protected void onPreExecute() {
            pd = new ProgressDialog(PVEMLyricalStatusMaker_EditImageActivity.this);
            pd.setTitle("Please Wait...");
            pd.setMessage("Creating Video..");
            pd.setCancelable(false);
        }

        @SuppressLint({"SdCardPath"})
        protected Void doInBackground(Void... voidArr) {
            String format = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append("/");
            stringBuilder.append(getResources().getString(R.string.app_name));
            stringBuilder.append("/temp2");
            String stringBuilder2 = stringBuilder.toString();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder3.append("/");
            stringBuilder3.append(getResources().getString(R.string.app_name));
            stringBuilder3.append("/temp2");
            stringBuilder3.toString();
            PVEMLyricalStatusMaker_EditImageActivity mRYTCHER_EditImagesNew = PVEMLyricalStatusMaker_EditImageActivity.this;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder4.append("/");
            stringBuilder4.append(getResources().getString(R.string.app_name));
            stringBuilder4.append("/video_");
            stringBuilder4.append(format);
            stringBuilder4.append(".mp4");
            mRYTCHER_EditImagesNew.videoname = stringBuilder4.toString();
            startService(new Intent(getApplicationContext(), PVEMLyricalStatusMaker_VdoService.class));

            Intent intents = new Intent(PVEMLyricalStatusMaker_EditImageActivity.this, PVEMLyricalStatusMaker_VdoService.class);
            startService(intents);

            PVEMLyricalStatusMaker_EditImageActivity mRYTCHER_EditImagesNew2 = PVEMLyricalStatusMaker_EditImageActivity.this;
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("-y&-r&9.0&-i&");
            stringBuilder3.append(stringBuilder2);
            stringBuilder3.append("/slide_%5d.jpg&-ss&");
            stringBuilder3.append(0);
            stringBuilder3.append("&-i&");
            stringBuilder3.append(PVEMLyricalStatusMaker_xtend.audiopath);
            stringBuilder3.append("&-map&0:0&-map&1:0&-vcodec&libx264&-acodec&aac&-r&30&-t&");
            stringBuilder3.append(30);
            stringBuilder3.append("&-strict&experimental&-preset&ultrafast&");
            stringBuilder3.append(videoname);
            stringBuilder3.append("");
            mRYTCHER_EditImagesNew2.cmd = stringBuilder3.toString();
            cmd.split("&");

            Intent intent = new Intent(getApplicationContext(), PVEMPVEMLyricalStatusMaker_ProgressActivity.class);
            startActivity(intent);
            return null;
        }
    }

    public class ServiceCompleted extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
        }
    }

    public class set_theme extends AsyncTask<Void, Integer, Void> {
        String[] args;
        File imgDir;

        protected void onPostExecute(Void voidR) {
        }

        protected void onPreExecute() {
            ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
        }

        @SuppressLint({"SdCardPath"})
        protected Void doInBackground(Void... voidArr) {
            application = PVEMLyricalStatusMaker_PrefMnger.getInstance();
            try {
                application.videoImages.clear();
            } catch (RuntimeException unused) {
            }
            Log.e("vimal", "Imageservice");
            PVEMLyricalStatusMaker_PrefMnger.isBreak = false;
            Intent intent = new Intent(getApplicationContext(),
                    PVEMLyricalStatusMaker_ImgService.class);
            intent.putExtra("selected_theme", PVEMLyricalStatusMaker_THEME.Shine.toString());
            startService(intent);
//            Intent intent = new Intent(LyricalVidMaker_EditImagesNew.this, PVEMLyricalStatusMaker_ImgService.class);
//            intent.putExtra(PVEMLyricalStatusMaker_ImgService.selectedTheme, PVEMLyricalStatusMaker_THEME.Roll2D_BT.toString());
//            LyricalVidMaker_EditImagesNew.startService(intent);
            return null;
        }
    }
}
