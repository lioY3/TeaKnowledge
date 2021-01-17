package com.example.teaknowledge;

import android.app.Application;

import org.xutils.x;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);   // 初始化XUtils，XUtils在本程序中主要提供数据库功能
//        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
