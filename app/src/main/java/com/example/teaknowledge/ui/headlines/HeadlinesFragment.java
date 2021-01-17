package com.example.teaknowledge.ui.headlines;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.example.teaknowledge.R;
import com.example.teaknowledge.bean.News;
import com.example.teaknowledge.db.DbHelper;
import com.example.teaknowledge.utils.AppUtils;
import com.example.teaknowledge.utils.HistoryUtils;
import com.example.teaknowledge.utils.StringUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesFragment extends Fragment {
    private HeadlinesAdapter headlinesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_headlines, container, false);
        final ListView listView = root.findViewById(R.id.list_headlines);
        final Context context = root.getContext();
        headlinesAdapter = new HeadlinesAdapter(context, new ArrayList<News>());
        headlinesAdapter.setOnClickListener(new HeadlinesAdapter.onClickListener() {
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
        //                 添加到内存中的历史记录
                        if (HistoryUtils.getHistory().contains(listNews.get(i)))
                        {
                            System.out.println("contains");
                        }else
                        {
                            HistoryUtils.getHistory().add(0,listNews.get(i));
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

        try {
            DbManager db = DbHelper.getDbManager();
            if (db != null) {
                headlinesAdapter.updateFromDatabase(db);
            } else {
                Toast.makeText(context, "数据库连接错误", Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(headlinesAdapter);
            }
        });

        return root;
    }
}