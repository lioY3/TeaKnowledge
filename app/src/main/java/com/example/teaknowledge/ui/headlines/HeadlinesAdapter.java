package com.example.teaknowledge.ui.headlines;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teaknowledge.R;
import com.example.teaknowledge.bean.News;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesAdapter extends BaseAdapter {
    private static final String TAG_DEBUG = "DEBUG";
    private Context context;
    private LayoutInflater mInflater;
    private List<News> listNews;
    private onClickListener mOnClickListener = null;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HeadlinesAdapter(final Context context, List<News> listNews) {
        this.context = context;
        this.listNews = listNews;
        this.mInflater = LayoutInflater.from(context);
    }

    public onClickListener getmOnClickListener() {
        return mOnClickListener;
    }

    @Override
    public int getCount() {
//        Log.d(TAG_DEBUG, String.format("getCount: %d", listNews.size()));
        return listNews.size();
    }

    @Override
    public Object getItem(int i) {
        return listNews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.list_item_news, null);
            holder.textViewTitle = view.findViewById(R.id.text_news_title);
            holder.imageView = view.findViewById(R.id.imgview_news_img);
            holder.textViewDate = view.findViewById(R.id.text_news_date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewTitle.setText(listNews.get(i).getTitle());
        String imgUrl = listNews.get(i).getImg();
        imageLoader.displayImage(imgUrl, holder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                System.out.println("img start");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                System.out.println("img fail");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                System.out.println("img complete");
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                System.out.println("img cancel");
            }
        });
        holder.textViewDate.setText(listNews.get(i).getDate());

        if (mOnClickListener != null) {
            mOnClickListener.onClick(view, this.listNews, i);
        }
        return view;
    }

    public void updateFromDatabase(DbManager db) throws DbException {
        List<News> queryResult = db.selector(News.class).findAll();
        if (queryResult != null) {
//            Log.d(TAG_DEBUG, String.format("updateFromDatabaseForSearch: %d", queryResult.size()));
            this.listNews = queryResult;
        } else {
//            Log.d(TAG_DEBUG, "updateFromDatabaseForSearch: queryResult == null");
            this.listNews = new ArrayList<>();
        }
    }

    public void setOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface onClickListener {
        void onClick(View view, List<News> listNews, final int pos);
    }

    public final class ViewHolder {
        public TextView textViewTitle;
        public TextView textViewDate;
        public ImageView imageView;
    }

}
