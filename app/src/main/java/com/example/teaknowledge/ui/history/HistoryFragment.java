package com.example.teaknowledge.ui.history;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.example.teaknowledge.R;
import com.example.teaknowledge.bean.Goods;
import com.example.teaknowledge.bean.News;
import com.example.teaknowledge.bean.TeaInfo;
import com.example.teaknowledge.ui.GoodsAdapter;
import com.example.teaknowledge.ui.headlines.HeadlinesAdapter;
import com.example.teaknowledge.ui.knowledge.KnowledgeAdapter;
import com.example.teaknowledge.utils.AppUtils;
import com.example.teaknowledge.utils.HistoryUtils;
import com.example.teaknowledge.utils.StringUtils;

import java.util.List;

public class HistoryFragment extends Fragment {

    private List<TeaInfo> listHistory;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_history, container, false);
        final Context context = root.getContext();
        listHistory = HistoryUtils.getHistory();
        final HistoryAdapter historyAdapter = new HistoryAdapter(context,listHistory);
        final ListView listView = root.findViewById(R.id.list_history_results);
        historyAdapter.setOnClickListener1(new HeadlinesAdapter.onClickListener() {
            @Override
            public void onClick(View view, final List<News> listNews, final int pos) {
                ImageView imageView = view.findViewById(R.id.imgview_news_img);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View dialogImageView = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_img,null,false);
                        final Dialog dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        dialog.setContentView(dialogImageView);
                        ImageView imageView = dialogImageView.findViewById(R.id.dialog_img);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        ImageLoader imgLoader = ImageLoader.getInstance();
                        imgLoader.displayImage(listNews.get(pos).getImg(),imageView);
                        dialog.show();
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        View dialogView = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_news, null, false);
                        final AlertDialog dialog = new AlertDialog.Builder(root.getContext())
                                .setView(dialogView)
                                .create();

                        TextView textNewsTitle = dialogView.findViewById(R.id.dlg_news_title);
                        TextView textNewsDate = dialogView.findViewById(R.id.dlg_news_date);
                        TextView textNewsContent = dialogView.findViewById(R.id.dlg_news_content);
                        textNewsTitle.setText(listNews.get(i).getTitle());
                        textNewsDate.setText(listNews.get(i).getDate());
                        //                 用replace将数据库字符串中的\n转换成回车符，实现文本换行
                        textNewsContent.setText(
                                StringUtils.replaceSeparator(listNews.get(i).getContent())
                        );

//                      添加到内存中的历史记录
                        if (HistoryUtils.getHistory().contains(listNews.get(pos)))
                        {
                            System.out.println("contains");
                        }else
                        {
                            HistoryUtils.getHistory().add(listNews.get(pos));
                            historyAdapter.notifyDataSetChanged();
                        }


                        ImageButton buttonShare = dialogView.findViewById(R.id.button_news_share);
                        ImageButton buttonFavorite = dialogView.findViewById(R.id.button_news_favorite);
                        buttonShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(AppUtils.getShareTextIntent(
                                        "请选择分享应用",
                                        String.format("来自茶知道的头条分享：\n《%s》:%s",
                                                listNews.get(i).getTitle(),
                                                listNews.get(i).getUrl()
                                        )
                                ));
                            }
                        });
                        dialog.show();
                    }
                });

            }
        });
        historyAdapter.setOnClickListener2(new GoodsAdapter.onClickListener() {

            @Override
            public void onClick(View view, final List<Goods> listGoods, final int pos) {
                final KnowledgeAdapter knowledgeAdapter = new KnowledgeAdapter(context,listGoods);
                ImageButton buttonShare = view.findViewById(R.id.button_article_share);
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
                            HistoryUtils.getHistory().add(listGoods.get(pos));
                        }

                        dialog.show();
                    }
                });
            }
        });

        listView.setAdapter(historyAdapter);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(historyAdapter);
            }
        });

        return root;
    }


}