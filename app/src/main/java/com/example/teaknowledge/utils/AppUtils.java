package com.example.teaknowledge.utils;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AppUtils {
    private static String DB_NAME = "database.db";

    /**
     * 用于初始化应用获取各种数据所需的数据库文件database.db
     * <p>
     * 会将database.db从安装包中复制到App的私有数据目录
     *
     * @param context 程序上下文
     * @return 是否已经存在数据库文件/是否成功初始化
     */
    public static boolean initDatabase(Context context) {
        String path = context.getDatabasePath(DB_NAME).getAbsolutePath();
        File file = new File(path);
        if (!file.exists()) {
            try {
                InputStream in = context.getAssets().open(DB_NAME);
                FileOutputStream out = new FileOutputStream(file);
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.flush();
                out.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 生成用于分享纯文本给其它应用所需的Intent
     *
     * @param title   分享标题
     * @param content 待分享的文本内容
     * @return 生成的Intent
     */
    public static Intent getShareTextIntent(String title, String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        return Intent.createChooser(shareIntent, title);
    }
}
