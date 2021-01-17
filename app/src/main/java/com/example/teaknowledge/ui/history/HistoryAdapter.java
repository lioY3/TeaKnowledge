package com.example.teaknowledge.ui.history;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.example.teaknowledge.R;
import com.example.teaknowledge.bean.Goods;
import com.example.teaknowledge.bean.News;
import com.example.teaknowledge.bean.TeaInfo;
import com.example.teaknowledge.ui.GoodsAdapter;
import com.example.teaknowledge.ui.headlines.HeadlinesAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<TeaInfo> listHistory;
    private HeadlinesAdapter.onClickListener mOnClickListener1 = null;
    private GoodsAdapter.onClickListener mOnClickListener2 = null;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<News> listNews = new ArrayList<>();
    private List<Goods> listGoods = new ArrayList<>();
    private static final String TAG_DEBUG = "DEBUG";
    private static final int HISTORY_NEWS = 0;
    private static final int HISTORY_GOODS = 1;

    public HistoryAdapter(Context context, List<TeaInfo> listHistory) {
        this.context = context;
        this.listHistory = listHistory;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listHistory.size();
    }

    @Override
    public Object getItem(int i) {
        return listHistory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        return listHistory.get(position).type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderNews viewHolderNews = null;
        ViewHolderGoods viewHolderGoods = null;
        for (TeaInfo teaInfo:listHistory)
        {
            if(teaInfo.type == 0)
                listNews.add((News)teaInfo);
            else if(teaInfo.type == 1)
                listGoods.add((Goods)teaInfo);
        }
//        Collections.reverse(listNews);
//        Collections.reverse(listGoods);
        int type = getItemViewType(i);
        if(type == 0)
        {
            if (view == null) {
                viewHolderNews = new ViewHolderNews();
                view = mInflater.inflate(R.layout.list_item_news, null);
                viewHolderNews.textViewTitle = view.findViewById(R.id.text_news_title);
                viewHolderNews.imageView = view.findViewById(R.id.imgview_news_img);
                viewHolderNews.textViewDate = view.findViewById(R.id.text_news_date);
                view.setTag(viewHolderNews);
            } else {
                viewHolderNews = (ViewHolderNews) view.getTag();
            }
            viewHolderNews.textViewTitle.setText(((News)(listHistory.get(i))).getTitle());
            String imgUrl = ((News)(listHistory.get(i))).getImg();
            imageLoader.displayImage(imgUrl, viewHolderNews.imageView, new ImageLoadingListener() {
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
            viewHolderNews.textViewDate.setText(((News)(listHistory.get(i))).getDate());

            if (mOnClickListener1 != null) {
                mOnClickListener1.onClick(view, listNews, i);
            }
        }else if(type == 1)
        {
            if (view == null) {
                viewHolderGoods = new ViewHolderGoods();
                view = mInflater.inflate(R.layout.list_item_article, null);
                viewHolderGoods.textViewName = view.findViewById(R.id.text_article_name);
                viewHolderGoods.textViewYear = view.findViewById(R.id.text_article_year);
                viewHolderGoods.imageButtonShare = view.findViewById(R.id.button_article_share);
                view.setTag(viewHolderGoods);
            } else {
                viewHolderGoods = (ViewHolderGoods) view.getTag();
            }
            viewHolderGoods.textViewName.setText(((Goods)(listHistory.get(i))).getName());
            viewHolderGoods.textViewYear.setText(String.valueOf(((Goods)(listHistory.get(i))).getYear()));

            if (mOnClickListener2 != null) {
                mOnClickListener2.onClick(view, listGoods, i);
            }
        }
        notifyDataSetChanged();
        return view;
    }

    public final class ViewHolderNews {
        public TextView textViewTitle;
        public TextView textViewDate;
        public ImageView imageView;
    }

    public final class ViewHolderGoods {
        public TextView textViewName;
        public TextView textViewYear;
        public ImageButton imageButtonShare;
    }

    public void setOnClickListener1(HeadlinesAdapter.onClickListener mOnClickListener) {
        this.mOnClickListener1 = mOnClickListener;
    }

    public void setOnClickListener2(GoodsAdapter.onClickListener mOnClickListener) {
        this.mOnClickListener2 = mOnClickListener;
    }
}
