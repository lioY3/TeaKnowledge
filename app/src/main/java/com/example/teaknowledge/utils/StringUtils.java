package com.example.teaknowledge.utils;

public class StringUtils {
    /**
     * 替换字符串中的\n为当前系统的换行符
     *
     * @param str 待替换的字符串
     * @return 已替换好换行符的字符串
     */
    public static String replaceSeparator(String str) {
        return str.replace("\\n", System.getProperty("line.separator"));
    }
}
