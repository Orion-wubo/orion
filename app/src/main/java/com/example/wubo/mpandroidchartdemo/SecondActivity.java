package com.example.wubo.mpandroidchartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by wubo on 2017/6/6.
 */

public class SecondActivity extends Activity {
    private PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.second);

        mPieChart = (PieChart) findViewById(R.id.chart1);
        mPieChart.setUsePercentValues(true);//数据以百分比进行绘制
        mPieChart.getDescription().setEnabled(false);//是否显示描述
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        //设置中间文件--就是空心里面的文字
//        mPieChart.setCenterText(generateCenterSpannableText());
        //设置是实心还是空心，实心false,空心true
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setDrawCenterText(true);//中间是否可以添加文字，当为空心

        mPieChart.setTransparentCircleColor(Color.WHITE);//透明圆的颜色
        mPieChart.setTransparentCircleAlpha(110);//设置透明度0-255，默认100

        mPieChart.setHoleRadius(58f);//半径
        mPieChart.setTransparentCircleRadius(61f);//透明圆的半径


        mPieChart.setRotationAngle(0);//设置初始的旋转角度

        mPieChart.setRotationEnabled(true);// 触摸旋转
        mPieChart.setHighlightPerTapEnabled(true);//true为点击高亮显示

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(46, "一键报警"));
        entries.add(new PieEntry(12, "火情火灾"));
        entries.add(new PieEntry(3, "其他"));
        entries.add(new PieEntry(1, "毁林案件"));

        //设置数据
        setData(entries);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColor(Color.BLACK);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(rgb("#5e9de5"));
        colors.add(rgb("#72e65f"));
        colors.add(rgb("#f48741"));
        colors.add(rgb("#6267e1"));
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    //设置中间文字
    private SpannableString generateCenterSpannableText() {
        //原文：MPAndroidChart\ndeveloped by Philipp Jahoda
        SpannableString s = new SpannableString("刘某人程序员\n我仿佛听到有人说我帅");
        //s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        // s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        // s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
}