package com.example.wubo.mpandroidchartdemo;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by wubo on 2017/6/6.
 */

public class RightData implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int n = (int) value;
        return n + "个";
    }
}
