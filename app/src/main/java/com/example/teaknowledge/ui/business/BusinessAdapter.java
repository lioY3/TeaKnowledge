package com.example.teaknowledge.ui.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.teaknowledge.R;
import com.example.teaknowledge.bean.Business;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

public class BusinessAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private onClickListener mOnClickListener = null;
    private List<Business> listBusiness;

    BusinessAdapter(Context context, List<Business> listBusiness) {
        this.context = context;
        this.listBusiness = listBusiness;
        this.mInflater = LayoutInflater.from(context);
    }

    BusinessAdapter(Context context) {
        this.context = context;
        this.listBusiness = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
//        Log.d(TAG_DEBUG, String.format("getCount: %d", listBusiness.size()));
        return listBusiness.size();
    }

    @Override
    public Object getItem(int i) {
        return listBusiness.get(i);
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
            holder.imageButtonRead = view.findViewById(R.id.button_article_read);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewName.setText(listBusiness.get(i).getTitle());
        holder.textViewYear.setText("");
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view, this.listBusiness, i);
        }
        return view;
    }

    public void updateFromDatabase(DbManager db) throws DbException {
        List<Business> queryResult = db.selector(Business.class).findAll();
        if (queryResult != null) {
            this.listBusiness = queryResult;
        } else {
            this.listBusiness = new ArrayList<>();
        }
    }

    public void setOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface onClickListener {
        void onClick(View view, List<Business> listBusiness, final int pos);
    }

    public final class ViewHolder {
        public TextView textViewName;
        public TextView textViewYear;
        public ImageButton imageButtonShare;
        public ImageButton imageButtonRead;
    }
}
