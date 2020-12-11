package com.photovideoeditormaker.lyricalvideostatusmaker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.activity.PVEMLyricalStatusMaker_AlbumNew;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_AllLstFile;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.util.ArrayList;
import java.util.List;

public class PVEMLyricalStatusMaker_ImageListAdapter extends BaseAdapter {
    private ArrayList<PVEMLyricalStatusMaker_AllLstFile> arraylist;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    Context mContext;
    private List<PVEMLyricalStatusMaker_AllLstFile> worldpopulationlist = null;

    public class ViewHolder {
        ImageView img_rightmark;
        ImageView ivAlbumPhoto;
        RelativeLayout llGridCell;
        RelativeLayout rev_al;
        TextView title_list;
        TextView title_list1;
        TextView title_list_size;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PVEMLyricalStatusMaker_ImageListAdapter(Context context, List<PVEMLyricalStatusMaker_AllLstFile> list, ImageLoader imageLoader) {
        mContext = context;
        initImageLoader();
        imageLoader = imageLoader;
        worldpopulationlist = list;
        inflater = LayoutInflater.from(mContext);
        arraylist = new ArrayList();
        arraylist.addAll(list);
    }

    public int getCount() {
        return worldpopulationlist.size();
    }

    public PVEMLyricalStatusMaker_AllLstFile getItem(int i) {
        return (PVEMLyricalStatusMaker_AllLstFile) worldpopulationlist.get(i);
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View inflate;
        if (view == null) {
            viewHolder = new ViewHolder();
            inflate = inflater.inflate(R.layout.lyrical_pic_image_row_album_listview, null);
            viewHolder.ivAlbumPhoto = (ImageView) inflate.findViewById(R.id.ivAlbumPhoto);
            viewHolder.title_list = (TextView) inflate.findViewById(R.id.title_list);
            viewHolder.title_list1 = (TextView) inflate.findViewById(R.id.title_list1);
            viewHolder.llGridCell = (RelativeLayout) inflate.findViewById(R.id.llGridCell);
            viewHolder.img_rightmark = (ImageView) inflate.findViewById(R.id.img_rightmark);
            viewHolder.rev_al = (RelativeLayout) inflate.findViewById(R.id.rev_al);
            viewHolder.title_list_size = (TextView) inflate.findViewById(R.id.title_list_size);
            inflate.setTag(viewHolder);
        } else {
            inflate = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        imageLoader.displayImage(((PVEMLyricalStatusMaker_AllLstFile) worldpopulationlist.get(i)).getFile_imgUri().toString(), viewHolder.ivAlbumPhoto, new Builder().showImageOnLoading(17170445).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).build());
        String file_bucketName = ((PVEMLyricalStatusMaker_AllLstFile) worldpopulationlist.get(i)).getFile_bucketName();
        if (file_bucketName.length() > 9) {
            file_bucketName = file_bucketName.substring(0, 8);
        }
        TextView textView = viewHolder.title_list;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file_bucketName);
        stringBuilder.append("(");
        stringBuilder.append(String.valueOf(((PVEMLyricalStatusMaker_AllLstFile) worldpopulationlist.get(i)).get_Folder_Size()));
        stringBuilder.append(")");
        textView.setText(stringBuilder.toString());
        viewHolder.title_list1.setBackgroundColor(Color.parseColor("#000000"));
        TextView textView2 = viewHolder.title_list_size;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("(");
        stringBuilder2.append(String.valueOf(((PVEMLyricalStatusMaker_AllLstFile) worldpopulationlist.get(i)).get_Folder_Size()));
        stringBuilder2.append(")");
        textView2.setText(stringBuilder2.toString());
        if (i == PVEMLyricalStatusMaker_AlbumNew.Listview_Selected_Pos) {
            viewHolder.title_list.setTextColor(Color.parseColor("#ffffff"));
            viewHolder.rev_al.setBackgroundResource(R.drawable.album_border_sele);
            viewHolder.title_list1.setBackgroundColor(Color.parseColor("#885ff6"));
            viewHolder.title_list_size.setTextColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.rev_al.setBackgroundResource(R.drawable.album_border_non);
            viewHolder.title_list.setTextColor(Color.parseColor("#ffffff"));
            viewHolder.title_list1.setBackgroundColor(Color.parseColor("#000000"));
            viewHolder.title_list_size.setTextColor(Color.parseColor("#ffffff"));
        }
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        PVEMLyricalStatusMaker_AlbumNew.Listview_Selected_Pos = i;
                        ((PVEMLyricalStatusMaker_AlbumNew) mContext).callBucketDisplay(i);
                    }
                }, 1000);
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
