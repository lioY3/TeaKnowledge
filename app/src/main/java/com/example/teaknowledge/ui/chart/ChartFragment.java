package com.example.teaknowledge.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teaknowledge.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class ChartFragment extends Fragment {

    private static final int DEFAULT_DATA = 0;
    private static final int SUBCOLUMNS_DATA = 1;
    private static final int DEFAULT_COLUMN = 1;
    private static final int NEGATIVE_SUBCOLUMNS_DATA = 2;

    private ColumnChartView chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = true;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;
    private int num = DEFAULT_DATA;
    private int Xcolumns = DEFAULT_DATA;
    private String AxisX = "Axis X";
    private String AxisY = "Axis y";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Float[] value1;
        String[] labelsX;
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_chart, container, false);

        //图1
        chart = (ColumnChartView) root.findViewById(R.id.chart_sales1);
        chart.setOnValueTouchListener(new ValueTouchListener());
        num = 10;
        value1 = new Float[]{146.25f, 160.76f, 176.15f, 188.72f, 204.93f, 227.66f, 231.33f, 246.04f, 261.04f, 277.72f};
        labelsX = new String[]{"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"};
        AxisX = "2010-2019年中国茶叶产量";
        AxisY = "/万吨";
        generateData(value1, labelsX);

        //图2
        chart = (ColumnChartView) root.findViewById(R.id.chart_sales2);
        chart.setOnValueTouchListener(new ValueTouchListener());
        num = 6;
        value1 = new Float[]{150.25f, 167.91f, 171.06f, 181.7f, 191.05f, 202.56f};
        labelsX = new String[]{"2014", "2015", "2016", "2017", "2018", "2019"};
        AxisX = "2014-2019年中国茶叶销量";
        AxisY = "/亿元";
        generateData(value1, labelsX);

        //图3
        chart = (ColumnChartView) root.findViewById(R.id.chart_sales3);
        chart.setOnValueTouchListener(new ValueTouchListener());
        num = 10;
        value1 = new Float[]{171.28f, 191.33f, 226.17f, 244.33f, 247.09f, 268.95f, 279.04f, 254.9f, 296.92f, 318.9f};
        labelsX = new String[]{"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"};
        AxisX = "2010-2019年中国茶叶市场成交额";
        AxisY = "/万吨";
        generateData(value1, labelsX);

        //图4
        dataType = SUBCOLUMNS_DATA;
        chart = (ColumnChartView) root.findViewById(R.id.chart_sales4);
        chart.setOnValueTouchListener(new ValueTouchListener());
        Xcolumns = 4;
        num = 8;
        value1 = new Float[]{12.62f, 12.16f, 14.71f, 17.80f, 8.37f, 11.15f, 8.53f, 11.81f, 5.23f, 5.62f, 6.29f, 6.87f, 1.99f, 4.47f, 4.55f, 4.14f, 1.21f, 1.17f, 1.14f, 0.95f, 6.96f, 7.16f, 6.12f, 6.17f, 19.54f, 14.74f, 20.20f, 17.52f, 4.33f, 3.52f, 3.07f, 2.84f};
        labelsX = new String[]{"茗皇天然", "茶乾坤", "恒福股份", "京东农业", "白龙茶业", "龙生茶业", "松萝茶业", "雅安茶厂"};
        AxisX = "2016-2019年中国茶叶行业主要企业经营收入";
        AxisY = "/千万元";
        generateData(value1, labelsX);

        return root;
    }

    private void generateDefaultData(Float[] value1, String[] labelsX) {
        int numColumns = num;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(value1[i], ChartUtils.pickColor()));

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        for (int j = 0; j < num; j++) {
            mAxisXValues.add(new AxisValue(j).setLabel(labelsX[j]));
        }
        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            axisX.setValues(mAxisXValues);
            if (hasAxesNames) {
                axisX.setName(AxisX);
                axisY.setName(AxisY);
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }

    private void generateSubcolumnsData(Float[] value1, String[] labelsX) {
        int numSubcolumns = Xcolumns;
        int numColumns = num;
        // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(value1[i + j], ChartUtils.COLORS[j]));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);
        for (int j = 0; j < num; j++) {
            mAxisXValues.add(new AxisValue(j).setLabel(labelsX[j]));
        }
        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            axisX.setValues(mAxisXValues);
            if (hasAxesNames) {
                axisX.setName(AxisX);
                axisY.setName(AxisY);
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }

    private void generateNegativeSubcolumnsData() {

        int numSubcolumns = 4;
        int numColumns = 4;
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                int sign = getSign();
                values.add(new SubcolumnValue((float) Math.random() * 50f * sign + 5 * sign, ChartUtils.pickColor
                        ()));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);
    }

    private int getSign() {
        int[] sign = new int[]{-1, 1};
        return sign[Math.round((float) Math.random())];
    }

    private void generateData(Float[] value1, String[] labelsX) {
        switch (dataType) {
            case DEFAULT_DATA:
                generateDefaultData(value1, labelsX);
                break;
            case SUBCOLUMNS_DATA:
                generateSubcolumnsData(value1, labelsX);
                break;
            case NEGATIVE_SUBCOLUMNS_DATA:
                generateNegativeSubcolumnsData();
                break;
            default:
                generateDefaultData(value1, labelsX);
                break;
        }
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
//            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

}