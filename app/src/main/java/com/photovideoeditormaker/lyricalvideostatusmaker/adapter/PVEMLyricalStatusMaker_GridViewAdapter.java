package com.photovideoeditormaker.lyricalvideostatusmaker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.util.ArrayList;
import java.util.List;

public class PVEMLyricalStatusMaker_GridViewAdapter extends BaseAdapter {
    private ArrayList<String> arraylist;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    Context mContext;
    private ArrayList<String> songname;
    private List<String> worldpopulationlist = null;

    public class ViewHolder {
        RelativeLayout frameLayout;
        ImageView ivAlbumPhoto;
        LinearLayout lnr_background;
        RelativeLayout rev_selected;
        TextView song_name;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PVEMLyricalStatusMaker_GridViewAdapter(Context context, ImageLoader imageLoader, ArrayList<String> arrayList) {
        mContext = context;
        initImageLoader();
        imageLoader = imageLoader;
        songname = arrayList;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return songname.size();
    }

    public String getItem(int i) {
        return (String) songname.get(i);
    }

    @SuppressLint({"ResourceAsColor"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View inflate;
        if (view == null) {
            viewHolder = new ViewHolder();
            inflate = inflater.inflate(R.layout.lyrical_gridviewlist, null);
            viewHolder.frameLayout = (RelativeLayout) inflate.findViewById(R.id.flThumbContainer);
            viewHolder.song_name = (TextView) inflate.findViewById(R.id.song_name);
            LayoutParams layoutParams = new LayoutParams((mContext.getResources().getDisplayMetrics().widthPixels * 1024) / 1080, (mContext.getResources().getDisplayMetrics().heightPixels * 123) / 1920);
            inflate.setTag(viewHolder);
        } else {
            inflate = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        TextView textView = viewHolder.song_name;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(((String) songname.get(i)).toString());
        textView.setText(stringBuilder.toString());
        return inflate;
    }

    private void initImageLoader() {
        ImageLoaderConfiguration build = new Builder(mContext).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(build);
    }
}
