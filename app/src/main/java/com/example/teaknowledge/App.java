package com.example.teaknowledge;

import android.app.Application;

import com.example.teaknowledge.utils.AppUtils;
import com.example.teaknowledge.utils.HistoryUtils;
import com.example.teaknowledge.utils.ImageLoaderUtil;
import com.example.teaknowledge.utils.SharedPreferencesUtils;

import org.xutils.x;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);   // 初始化XUtils，XUtils在本程序中主要提供数据库功能
        AppUtils.initDatabase(this);
        ImageLoaderUtil.initImageLoader(this);
        SharedPreferencesUtils.getInstance(this,"history");
        HistoryUtils.setListHistory(SharedPreferencesUtils.getListData1("history"));
//        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
