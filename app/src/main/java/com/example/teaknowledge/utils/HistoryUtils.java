package com.example.teaknowledge.utils;

import com.example.teaknowledge.bean.TeaInfo;

import java.util.ArrayList;
import java.util.List;

public class HistoryUtils {
    private static List<TeaInfo> listHistory = new ArrayList<>();

    public static List<TeaInfo> getHistory() {
        return listHistory;
    }
}
