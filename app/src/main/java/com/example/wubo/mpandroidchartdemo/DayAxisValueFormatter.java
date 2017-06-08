package com.example.wubo.mpandroidchartdemo;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by wubo on 2017/6/6.
 */

public class DayAxisValueFormatter implements IAxisValueFormatter {
    private String[] areaname ;

    public DayAxisValueFormatter(String[] areanames) {
        this.areaname = areanames;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int i = (int) (value );  //-1----26一直循环



        return i + "";
    }

}
