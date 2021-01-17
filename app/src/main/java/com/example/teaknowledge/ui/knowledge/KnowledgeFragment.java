package com.example.teaknowledge.ui.knowledge;

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
import androidx.fragment.app.Fragment;

import com.example.teaknowledge.R;
import com.example.teaknowledge.bean.Goods;
import com.example.teaknowledge.db.DbHelper;
import com.example.teaknowledge.utils.AppUtils;
import com.example.teaknowledge.utils.HistoryUtils;
import com.example.teaknowledge.utils.StringUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeFragment extends Fragment {
    private KnowledgeAdapter knowledgeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_knowledge, container, false);
        final Context context = root.getContext();

        knowledgeAdapter = new KnowledgeAdapter(context, new ArrayList<Goods>());
        knowledgeAdapter.setOnClickListener(new KnowledgeAdapter.onClickListener() {
            @Override
            public void onClick(View view, final List<Goods> listGoods, final int pos) {
                ImageButton buttonShare = view.findViewById(R.id.button_article_share);
                ImageButton buttonFavorite = view.findViewById(R.id.button_goods_favorite);
                ImageButton buttonRead = view.findViewById(R.id.button_article_read);

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

                buttonFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DbManager db = DbHelper.getDbManager();
                        if (db != null) {
                            try {
                                Goods goods = listGoods.get(pos);
                                if (goods.getIsFavorite() != 0) {
                                    goods.setIsFavorite(0);
                                    db.saveOrUpdate(goods);
                                } else {
                                    goods.setIsFavorite(1);
                                    db.saveOrUpdate(goods);
                                }
                                knowledgeAdapter.updateFromDatabase(db);
                                knowledgeAdapter.notifyDataSetChanged();
                            } catch (DbException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "数据库操作错误", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        } else {
                            Toast.makeText(context, "数据库连接错误", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

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
                        if (HistoryUtils.getHistory().contains(listGoods.get(pos)))
                        {
                            System.out.println("contains");
                        }else
                        {
                            HistoryUtils.getHistory().add(0,listGoods.get(pos));
                        }

                        dialog.show();
                    }
                });
            }
        });

        try {
            DbManager db = DbHelper.getDbManager();
            if (db != null) {
                knowledgeAdapter.updateFromDatabase(db);
            } else {
                Toast.makeText(context, "数据库连接错误", Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        final ListView listView = root.findViewById(R.id.list_knowledge);
        listView.setAdapter(knowledgeAdapter);

        return root;
    }
}