package com.example.wubo.mpandroidchartdemo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by wubo on 2017/6/6.
 */

public class ThirdActivity extends Activity {
    private CombinedChart mChart;
    public static final String[] areaname = new String[]{"A镇", "B镇", "C镇", "D镇", "E镇", "F镇", "G镇", "H镇", "I镇", "J镇",
            "K镇", "L镇", "M镇", "N镇", "O镇", "P镇", "P镇", "R镇", "S镇", "T镇", "U镇", "V镇", "W镇", "X镇", "Y镇", "Z镇",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        setContentView(R.layout.thirdactivity);

        mChart = (CombinedChart) findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.animateXY(1000,1000);


        // 设置组合图都有那个
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        //x轴数据
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(areaname.length,false);
        xAxis.setAxisMaximum(areaname.length);
        xAxis.setLabelRotationAngle(45);//设置x轴变迁的角度
        xAxis.setAxisMinimum(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return areaname[(int) ((value + areaname.length)%areaname.length)];
            }
        });

        //y轴数据
        IAxisValueFormatter custom = new LeftData();
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(4, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(0f);
        /**
         * 如果设置了最大最小，那么不能动态计算轴的值
         */
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        //        leftAxis.setAxisMaximum(160f);  //动态设置最大,

        IAxisValueFormatter custom2 = new RightData();
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(4, false);
        rightAxis.setValueFormatter(custom2);
        rightAxis.setSpaceTop(0f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)f
        //        rightAxis.setAxisMaximum(60f);

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < areaname.length; index++)
        /**
         * 这个加0.5f特别重要，这样可以使第一个点从数据中间开始
         * 不是从y轴开始
         */
            entries.add(new Entry(index + 0.5f, (float) (Math.random() * 20)));

        LineDataSet set = new LineDataSet(entries, "巡护面积");
        set.setColor(rgb("#2d9660"));
        set.setLineWidth(2f);
        set.setCircleColor(rgb("#2d9660"));
        set.setCircleRadius(5f);
        set.setFillColor(rgb("#2d9660"));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(rgb("#2d9660"));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);
        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        /**
         * 给每一条设置数据
         */
        for (int i = 0; i < areaname.length; i++) {
            yVals1.add(new BarEntry(i, (float) (Math.random() * 50)));
            yVals2.add(new BarEntry(i, (float) (Math.random() * 50)));
        }

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "巡护区域");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(rgb("##3498db"));
        set2 = new BarDataSet(yVals2, "护林员个数");
        set2.setColor(rgb("#e74c3c"));
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.32f;
        float barSpace = 0.04f; // x2 DataSet
        float barWidth = 0.3f; // x2 DataSet
        //这个计算公式是，(barwidh + barspace) * groupcount  + groupspace
        // (0.2 + 0.03) * 2 + 0.08 = 1.00 -> interval per "group"

        BarData data = new BarData(set1, set2);
        data.setBarWidth(barWidth);
        data.groupBars(0f, groupSpace, barSpace);
        return data;
    }
}
