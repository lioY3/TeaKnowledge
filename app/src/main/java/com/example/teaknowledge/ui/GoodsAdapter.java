package com.example.teaknowledge.ui;

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

public class GoodsAdapter extends BaseAdapter {
    private static final String TAG_DEBUG = "DEBUG";
    private Context context;
    private LayoutInflater mInflater;
    private GoodsAdapter.onClickListener mOnClickListener = null;
    private List<Goods> listGoods;

    public GoodsAdapter(Context context, List<Goods> listGoods) {
        this.context = context;
        this.listGoods = listGoods;
        this.mInflater = LayoutInflater.from(context);
    }

    public GoodsAdapter(Context context) {
        this.context = context;
        this.listGoods = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
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
            view = mInflater.inflate(R.layout.list_item_article, null);
            holder.textViewName = view.findViewById(R.id.text_article_name);
            holder.textViewYear = view.findViewById(R.id.text_article_year);
            holder.imageButtonShare = view.findViewById(R.id.button_article_share);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewName.setText(listGoods.get(i).getName());
        holder.textViewYear.setText(String.valueOf(listGoods.get(i).getYear()));
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view, this.listGoods, i);
        }
        return view;
    }

    /**
     * 获取搜索结果，并用以更新Adapter数据
     *
     * @param db 数据库管理对象
     * @throws DbException 数据库发生异常
     */
    public void updateFromDatabaseForSearch(DbManager db, String keyword) throws DbException {
        List<Goods> queryResult = db.selector(Goods.class)
                .where("name", "like", String.format("%%%s%%", keyword))
                .or("year", "like", String.format("%%%s%%", keyword))
                .findAll();
        if (queryResult != null) {
            this.listGoods = queryResult;
        } else {
            this.listGoods = new ArrayList<>();
        }
    }

    /**
     * 获取收藏列表，并用以更新Adapter数据
     *
     * @param db 数据库管理对象
     * @throws DbException 数据库发生异常
     */
    public void updateFromDatabseForFavorite(DbManager db) throws DbException {
        List<Goods> queryResult = db.selector(Goods.class)
                .where("favorite", "!=", 0)
                .findAll();
        if (queryResult != null) {
            this.listGoods = queryResult;
        } else {
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
    }
}
