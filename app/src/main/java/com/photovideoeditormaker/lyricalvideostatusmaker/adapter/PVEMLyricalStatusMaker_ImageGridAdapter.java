package com.photovideoeditormaker.lyricalvideostatusmaker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.activity.PVEMLyricalStatusMaker_AlbumNew;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_xtend;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_GridFile;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class PVEMLyricalStatusMaker_ImageGridAdapter extends BaseAdapter {
    private ArrayList<PVEMLyricalStatusMaker_GridFile> arraylist;
    int count = 0;
    int height;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    Context mContext;
    int width;
    private List<PVEMLyricalStatusMaker_GridFile> worldpopulationlist = null;

    public class ViewHolder {
        FrameLayout fl;
        ImageButton imgbtn_select;
        ImageView ivAlbumPhoto;
        LinearLayout lnr_background;
        RelativeLayout rev_selected;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PVEMLyricalStatusMaker_ImageGridAdapter(Context context, List<PVEMLyricalStatusMaker_GridFile> list, ImageLoader imageLoader) {
        mContext = context;
        initImageLoader();
        imageLoader = imageLoader;
        worldpopulationlist = list;
        inflater = LayoutInflater.from(mContext);
        arraylist = new ArrayList();
        arraylist.addAll(list);
        width = mContext.getResources().getDisplayMetrics().widthPixels / 4;
        height = width + 73;
    }

    public void setcount(int i) {
        count = i;
    }

    public int getCount() {
        return worldpopulationlist.size();
    }

    public PVEMLyricalStatusMaker_GridFile getItem(int i) {
        return (PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i);
    }

    @SuppressLint({"ResourceAsColor"})
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        View inflate;
        if (view == null) {
            viewHolder = new ViewHolder();
            inflate = inflater.inflate(R.layout.lyrical_img_pic_row_gridviewlist, null);
            viewHolder.ivAlbumPhoto = (ImageView) inflate.findViewById(R.id.ivAlbumPhoto);
            viewHolder.fl = (FrameLayout) inflate.findViewById(R.id.flThumbContainer);
            viewHolder.rev_selected = (RelativeLayout) inflate.findViewById(R.id.rev_selected);
            viewHolder.imgbtn_select = (ImageButton) inflate.findViewById(R.id.imgbtn_select);
            LayoutParams layoutParams = new LayoutParams(width - 0, width - 0);
            layoutParams.gravity = 17;
            viewHolder.fl.setLayoutParams(layoutParams);
            inflate.setTag(viewHolder);
        } else {
            inflate = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        DisplayImageOptions build = new Builder().displayer(new RoundedBitmapDisplayer(0)).showImageOnLoading(17170445).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).build();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("url=============>>>");
        stringBuilder.append(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri());
        Log.e("", stringBuilder.toString());
        ((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri().toString().replace("content://media/external/images/media/", "");
        imageLoader.displayImage(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri().toString(), viewHolder.ivAlbumPhoto, build);
        if (PVEMLyricalStatusMaker_xtend.Final_Selected_Image.contains(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri().toString())) {
            viewHolder.rev_selected.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rev_selected.setVisibility(View.GONE);
        }
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.e("", "====view ====");
                if (PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size() < 6) {
                    if (!PVEMLyricalStatusMaker_xtend.Final_Selected_Image.contains(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri().toString())) {
                        int[] iArr = new int[2];
                        PVEMLyricalStatusMaker_ImageGridAdapter mRYTCHER_PicImageGridViewAdapter = PVEMLyricalStatusMaker_ImageGridAdapter.this;
                        mRYTCHER_PicImageGridViewAdapter.count++;
                        view.getLocationOnScreen(iArr);
                        ((PVEMLyricalStatusMaker_AlbumNew) mContext).selectImage(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri(), (float) iArr[0], (float) (iArr[1] - view.getHeight()), count);
                        PVEMLyricalStatusMaker_xtend.Final_Selected_Image.add(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri().toString());
                        viewHolder.rev_selected.setVisibility(View.VISIBLE);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Image Url");
                    stringBuilder.append(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri().toString());
                    Log.e("", stringBuilder.toString());
                    return;
                }
                Toast.makeText(mContext, "You can select only six Images", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.imgbtn_select.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.e("", "==== imgbtn ====");
                if (PVEMLyricalStatusMaker_xtend.Final_Selected_Image.size() >= 6) {
                    Toast.makeText(mContext, "You can select only six Images", Toast.LENGTH_SHORT).show();
                } else if (!PVEMLyricalStatusMaker_xtend.Final_Selected_Image.contains(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri().toString())) {
                    int[] iArr = new int[2];
                    PVEMLyricalStatusMaker_ImageGridAdapter mRYTCHER_PicImageGridViewAdapter = PVEMLyricalStatusMaker_ImageGridAdapter.this;
                    mRYTCHER_PicImageGridViewAdapter.count++;
                    view.getLocationOnScreen(iArr);
                    ((PVEMLyricalStatusMaker_AlbumNew) mContext).selectImage(((PVEMLyricalStatusMaker_GridFile) worldpopulationlist.get(i)).getFile_imgUri(), (float) iArr[0], (float) (iArr[1] - view.getHeight()), count);
                    PVEMLyricalStatusMaker_xtend.Final_Selected_Image.add((worldpopulationlist.get(i)).getFile_imgUri().toString());
                    viewHolder.rev_selected.setVisibility(View.VISIBLE);
                }
            }
        });
        return inflate;
    }

    private void initImageLoader() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(mContext).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new Builder().cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(build);
    }
}
