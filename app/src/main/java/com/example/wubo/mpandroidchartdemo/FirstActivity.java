package com.example.wubo.mpandroidchartdemo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by wubo on 2017/6/6.
 */

public class FirstActivity extends Activity {

    public static final String[] areaname = new String[]{"A镇", "B镇", "C镇", "D镇", "E镇", "F镇", "G镇", "H镇", "I镇", "J镇",
            "K镇", "L镇", "M镇", "N镇", "O镇", "P镇", "P镇", "R镇", "S镇", "T镇", "U镇", "V镇", "W镇", "X镇", "Y镇", "Z镇",};
    private List<String> name = new ArrayList<>();
    private BarChart mChart;
    private int average;
    private TextView textaverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        mChart = (BarChart) findViewById(R.id.chart1);
        textaverage = (TextView) findViewById(R.id.average);
        //设置柱状图
        setBarchart();

    }

    private void setBarchart() {
        mChart.setDrawBarShadow(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.animateY(1000);

        //设置说明
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        //设置自定义mark
        //        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        //        mv.setChartView(mChart); // For bounds control
        //        mChart.setMarker(mv); // Set the marker to the chart

        /**
         * 第一个参数是多少条数据+1，相当于多少个乡镇+1
         * 第二个参数是该乡镇的巡护面积、护林员个数、巡护区域个数
         */
        setData(areaname.length, 100);

        //设置人均线
        LimitLine ll1 = new LimitLine(average, "人均巡护面积");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);//三个参数，第一个线宽长度，第二个线段之间宽度，第三个一般为0，是个补偿
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        textaverage.setText("阳山县护林员巡护面积统计图统计图(平均" + average + "亩/人)");

        //x轴数据
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//位置
        xAxis.setCenterAxisLabels(true);//设置在轴中间
        xAxis.setDrawGridLines(false);//轴线
        xAxis.setLabelCount(areaname.length, false);    //设置显示标签书，FALSE为自动调整
        xAxis.setLabelRotationAngle(45);//设置x轴变迁的角度
        xAxis.setGranularity(1f);//缩放时的最小距离
//        xAxis.setAxisMinimum(0f);//
//        xAxis.setAxisMaximum(areaname.length);
//        xAxis.setValueFormatter(new DayAxisValueFormatter(areaname));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return areaname[(int) (value +26)% areaname.length];
            }
        });

        //y轴数据
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.addLimitLine(ll1);
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(4, false);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "亩/人";
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(0f);
        /**
         * 如果设置了最大最小，那么不能动态计算轴的值
         */
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        //        leftAxis.setAxisMaximum(160f);  //动态设置最大,

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setData(int count, float range) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        int total = 0;
        for (int i = 0; i < count; i++) {
            int val = (int) (Math.random() * 20);
            yVals1.add(new BarEntry(i + 0.5f, val));
            total = val + total;
        }

        average = total / count;

        BarDataSet set1 = new BarDataSet(yVals1, "人均巡护面积");
            set1.setDrawIcons(false);

            set1.setColors(rgb("#ff9d00"));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.5f);

            mChart.setData(data);
        }

}