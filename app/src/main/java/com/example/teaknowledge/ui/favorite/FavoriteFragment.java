package com.example.teaknowledge.ui.favorite;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teaknowledge.R;
import com.example.teaknowledge.bean.Goods;
import com.example.teaknowledge.db.DbHelper;
import com.example.teaknowledge.ui.GoodsAdapter;
import com.example.teaknowledge.utils.AppUtils;
import com.example.teaknowledge.utils.HistoryUtils;
import com.example.teaknowledge.utils.StringUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

public class FavoriteFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        final Context context = root.getContext();
        GoodsAdapter goodsAdapter = new GoodsAdapter(context);
        goodsAdapter.setOnClickListener(new GoodsAdapter.onClickListener() {
            @Override
            public void onClick(View view, final List<Goods> listGoods, final int pos) {
                ImageButton buttonShare = view.findViewById(R.id.button_article_share);
                ImageButton buttonRead = view.findViewById(R.id.button_article_read);
//                ImageButton buttonFavorite = view.findViewById(R.id.button_goods_favorite);

                buttonShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(AppUtils.getShareTextIntent(
                                "请选择分享应用",
                                String.format("名称：%s\n年份：%s\n描述：%s",
                                        listGoods.get(pos).getName(),
                                        String.valueOf(listGoods.get(pos).getYear()),
                                        StringUtils.replaceSeparator(listGoods.get(pos).getDesc()))
                        ));
                    }
                });

//                buttonFavorite.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        DbManager db = DbHelper.getDbManager();
//                        if (db != null) {
//                            try {
//                                Goods goods = listGoods.get(pos);
//                                if (goods.getIsFavorite() != 0) {
//                                    goods.setIsFavorite(0);
//                                    db.saveOrUpdate(goods);
//                                } else {
//                                    goods.setIsFavorite(1);
//                                    db.saveOrUpdate(goods);
//                                }
//                                goodsAdapter.updateFromDatabseForFavorite(db);
//                                goodsAdapter.notifyDataSetChanged();
//                            } catch (DbException e) {
//                                e.printStackTrace();
//                                Toast.makeText(context, "数据库操作错误", Toast.LENGTH_SHORT)
//                                        .show();
//                            }
//                        } else {
//                            Toast.makeText(context, "数据库连接错误", Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    }
//                });

                buttonRead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View dialogView = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_goods, null, false);
                        final AlertDialog dialog = new AlertDialog.Builder(context)
                                .setView(dialogView)
                                .create();

                        TextView textGoodsTitle = dialogView.findViewById(R.id.text_goods_title);
                        TextView textGoodsYear = dialogView.findViewById(R.id.text_article_year);
                        TextView textGoodsDesc = dialogView.findViewById(R.id.text_goods_desc);
                        textGoodsTitle.setText(listGoods.get(pos).getName());
                        textGoodsYear.setText(String.valueOf(listGoods.get(pos).getYear()));
                        // 用replace将数据库字符串中的\n转换成回车符，实现文本换行
                        textGoodsDesc.setText(
                                StringUtils.replaceSeparator(listGoods.get(pos).getDesc())
                        );

                        // 添加到内存中的历史记录
                        HistoryUtils.getHistory().add(listGoods.get(pos));

                        dialog.show();
                    }
                });
            }
        });

        DbManager db = DbHelper.getDbManager();
        if (db != null) {
            try {
                goodsAdapter.updateFromDatabseForFavorite(db);
            } catch (DbException e) {
                e.printStackTrace();
                Toast.makeText(context, "数据库操作错误", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(context, "数据库连接错误", Toast.LENGTH_SHORT)
                    .show();
        }

        final ListView listView = root.findViewById(R.id.list_favorite_results);
        listView.setAdapter(goodsAdapter);

        return root;
    }
}