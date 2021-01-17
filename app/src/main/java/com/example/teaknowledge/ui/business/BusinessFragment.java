package com.example.teaknowledge.ui.business;

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
import com.example.teaknowledge.bean.Business;
import com.example.teaknowledge.db.DbHelper;
import com.example.teaknowledge.utils.AppUtils;
import com.example.teaknowledge.utils.StringUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

public class BusinessFragment extends Fragment {

    private BusinessAdapter businessAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_business, container, false);
        final Context context = root.getContext();

        businessAdapter = new BusinessAdapter(context);
        businessAdapter.setOnClickListener(new BusinessAdapter.onClickListener() {
            @Override
            public void onClick(View view, final List<Business> listBusiness, final int pos) {
                ImageButton buttonShare = view.findViewById(R.id.button_article_share);
                ImageButton buttonRead = view.findViewById(R.id.button_article_read);

                buttonShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(AppUtils.getShareTextIntent(
                                "请选择分享应用",
                                String.format("名称：%s\n详细信息：%s",
                                        listBusiness.get(pos).getTitle(),
                                        StringUtils.replaceSeparator(listBusiness.get(pos).getDesc()))
                        ));
                    }
                });

                buttonRead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View dialogView = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_article, null, false);
                        final AlertDialog dialog = new AlertDialog.Builder(context)
                                .setView(dialogView)
                                .create();

                        TextView textGoodsTitle = dialogView.findViewById(R.id.text_goods_title);
                        TextView textGoodsDesc = dialogView.findViewById(R.id.text_goods_desc);
                        textGoodsTitle.setText(listBusiness.get(pos).getTitle());
                        // 用replace将数据库字符串中的\n转换成回车符，实现文本换行
                        textGoodsDesc.setText(
                                StringUtils.replaceSeparator(listBusiness.get(pos).getDesc())
                        );

                        dialog.show();
                    }
                });
            }
        });

        try {
            DbManager db = DbHelper.getDbManager();
            if (db != null) {
                businessAdapter.updateFromDatabase(db);
            } else {
                Toast.makeText(context, "数据库连接错误", Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        final ListView listView = root.findViewById(R.id.list_business);
        listView.setAdapter(businessAdapter);

        return root;
    }
}