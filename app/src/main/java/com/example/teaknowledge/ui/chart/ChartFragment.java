package com.example.teaknowledge.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teaknowledge.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart, container, false);

        LineChartView chart = root.findViewById(R.id.chart_sales);
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        chart.setContainerScrollEnabled(true, ContainerScrollType.VERTICAL);
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

        List<PointValue> values = new ArrayList<>();
        values.add(new PointValue(2000, 791));
        values.add(new PointValue(2001, 772));
        values.add(new PointValue(2002, 834));
        values.add(new PointValue(2003, 216));
        values.add(new PointValue(2004, 737));
        values.add(new PointValue(2005, 935));
        values.add(new PointValue(2006, 388));
        values.add(new PointValue(2007, 465));
        values.add(new PointValue(2008, 874));
        values.add(new PointValue(2009, 423));
        values.add(new PointValue(2010, 510));
        values.add(new PointValue(2011, 435));
        values.add(new PointValue(2012, 896));
        values.add(new PointValue(2013, 952));
        values.add(new PointValue(2014, 361));
        values.add(new PointValue(2015, 726));
        values.add(new PointValue(2016, 634));
        values.add(new PointValue(2017, 801));
        values.add(new PointValue(2018, 873));
        values.add(new PointValue(2019, 390));

        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setTextColor(Color.GRAY);
        axisY.setTextColor(Color.GRAY);
        axisX.setHasLines(true);
        axisY.setHasLines(true);

        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        chart.setLineChartData(data);

        return root;
    }
}