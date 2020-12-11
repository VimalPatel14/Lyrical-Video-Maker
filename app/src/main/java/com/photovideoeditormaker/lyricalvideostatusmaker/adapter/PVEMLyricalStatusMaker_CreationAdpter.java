package com.photovideoeditormaker.lyricalvideostatusmaker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.photovideoeditormaker.lyricalvideostatusmaker.R;
import com.photovideoeditormaker.lyricalvideostatusmaker.activity.PVEMLyricalStatusMaker_MyCreationActivity;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.PVEMLyricalStatusMaker_Utls2;
import com.photovideoeditormaker.lyricalvideostatusmaker.model.LyricalVidMaker_VideoModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class PVEMLyricalStatusMaker_CreationAdpter extends BaseAdapter {
    private ArrayList<LyricalVidMaker_VideoModel> arraylist;
    LayoutInflater inflater;
    Context mContext;
    Typeface typefaceTitle;
    private List<LyricalVidMaker_VideoModel> worldpopulationlist = null;

    public class ViewHolder {
        ImageView img_thumbnail;
        LinearLayout ll_background;
        TextView tv_videoName;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PVEMLyricalStatusMaker_CreationAdpter(Context context, List<LyricalVidMaker_VideoModel> list) {
        mContext = context;
        worldpopulationlist = list;
        inflater = LayoutInflater.from(mContext);
        arraylist = new ArrayList();
        arraylist.addAll(list);
    }

    public int getCount() {
        return worldpopulationlist.size();
    }

    public LyricalVidMaker_VideoModel getItem(int i) {
        return (LyricalVidMaker_VideoModel) worldpopulationlist.get(i);
    }

    @SuppressLint({"ResourceAsColor"})
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View inflate;
        if (view == null) {
            viewHolder = new ViewHolder();
            inflate = inflater.inflate(R.layout.lyrical_creation_video_row, null);
            viewHolder.tv_videoName = (TextView) inflate.findViewById(R.id.tv_videoName);
            viewHolder.tv_videoName.setTypeface(typefaceTitle);
            viewHolder.img_thumbnail = (ImageView) inflate.findViewById(R.id.img_thumbnail);
            viewHolder.ll_background = (LinearLayout) inflate.findViewById(R.id.ll_background);
            viewHolder.ll_background.setLayoutParams(new LayoutParams(PVEMLyricalStatusMaker_Utls2.getScreenWidth() / 2, (PVEMLyricalStatusMaker_Utls2.getScreenWidth() / 2) + 100));
            inflate.setTag(viewHolder);
        } else {
            inflate = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_videoName.setText(((LyricalVidMaker_VideoModel) worldpopulationlist.get(i)).getName());
        PVEMLyricalStatusMaker_MyCreationActivity.imgLoader.displayImage(((LyricalVidMaker_VideoModel) worldpopulationlist.get(i)).getContentPath(), viewHolder.img_thumbnail, new Builder().showImageForEmptyUri((int) R.mipmap.ic_launcher).cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Config.ARGB_8888).delayBeforeLoading(100).displayer(new SimpleBitmapDisplayer()).build());
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((PVEMLyricalStatusMaker_MyCreationActivity) mContext).callIntent(i);
            }
        });
        return inflate;
    }

    public void filter(String str) {
        str = str.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (str.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            Iterator it = arraylist.iterator();
            while (it.hasNext()) {
                LyricalVidMaker_VideoModel mRYTCHER_VideoModel = (LyricalVidMaker_VideoModel) it.next();
                if (mRYTCHER_VideoModel.getName().toLowerCase(Locale.getDefault()).contains(str)) {
                    worldpopulationlist.add(mRYTCHER_VideoModel);
                }
            }
        }
        notifyDataSetChanged();
    }
}
