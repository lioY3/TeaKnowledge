package com.example.teaknowledge.utils;

import com.example.teaknowledge.bean.Goods;

import java.util.ArrayList;
import java.util.List;

public class HistoryUtils {
    private static List<Goods> listGoods = new ArrayList<>();

    public static List<Goods> getHistory() {
        return listGoods;
    }
}
