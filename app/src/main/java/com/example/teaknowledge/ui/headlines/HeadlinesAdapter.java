package com.example.teaknowledge.ui.headlines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.teaknowledge.R;
import com.example.teaknowledge.bean.Goods;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesAdapter extends BaseAdapter {
    private static final String TAG_DEBUG = "DEBUG";
    private Context context;
    private LayoutInflater mInflater;
    private onClickListener mOnClickListener = null;
    private List<Goods> listGoods;

    HeadlinesAdapter(Context context, List<Goods> listGoods) {
        this.context = context;
        this.listGoods = listGoods;
        this.mInflater = LayoutInflater.from(context);
    }

    HeadlinesAdapter(Context context) {
        this.context = context;
        this.listGoods = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
//        Log.d(TAG_DEBUG, String.format("getCount: %d", listGoods.size()));
        return listGoods.size();
    }

    @Override
    public Object getItem(int i) {
        return listGoods.get(i);
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
            view = mInflater.inflate(R.layout.list_item_goods, null);
            holder.textViewName = view.findViewById(R.id.text_article_name);
            holder.textViewYear = view.findViewById(R.id.text_article_year);
            holder.imageButtonShare = view.findViewById(R.id.button_article_share);
            holder.imageButtonFavorite = view.findViewById(R.id.button_goods_favorite);
            holder.imageButtonRead = view.findViewById(R.id.button_article_read);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewName.setText(listGoods.get(i).getName());
        holder.textViewYear.setText(String.valueOf(listGoods.get(i).getYear()));
        if (listGoods.get(i).getIsFavorite() != 0) {
            holder.imageButtonFavorite.setImageDrawable(
                    this.context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        } else {
            holder.imageButtonFavorite.setImageDrawable(
                    this.context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view, this.listGoods, i);
        }
        return view;
    }

    public void updateFromDatabase(DbManager db) throws DbException {
        List<Goods> queryResult = db.selector(Goods.class)
                .where("year", "=", 2019)
                .findAll();
        if (queryResult != null) {
//            Log.d(TAG_DEBUG, String.format("updateFromDatabaseForSearch: %d", queryResult.size()));
            this.listGoods = queryResult;
        } else {
//            Log.d(TAG_DEBUG, "updateFromDatabaseForSearch: queryResult == null");
            this.listGoods = new ArrayList<>();
        }
    }

    public void setOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface onClickListener {
        void onClick(View view, List<Goods> listGoods, final int pos);
    }

    public final class ViewHolder {
        public TextView textViewName;
        public TextView textViewYear;
        public ImageButton imageButtonShare;
        public ImageButton imageButtonFavorite;
        public ImageButton imageButtonRead;
    }
}
