详见：https://blog.csdn.net/androidwubo/article/details/72918612

饼状图可以详见：http://blog.csdn.net/androidwubo/article/details/72922158

组合图可以详见：http://blog.csdn.net/androidwubo/article/details/72922171

该项目的源码：https://github.com/Orion-wubo/orion

步骤：（以柱状图为例）

1、引用（三种方式，不一一介绍了，看github）

2、根据需求选择相应的表

折线图 LineChart
条形图 BarChart
条形折线图 Combined-Chart
圆饼图 PieChart
雷达图 ScatterChart
K线图 CandleStickChart
泡泡图 BubbleChart
网状图 RadarChart
3、获取相应控件
mChart = (LineChart) findViewById(R.id.chart);
注：设置屏幕横向、全屏
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
4、设置图标
mChart.setDrawBarShadow(false);//阴影
        mChart.setPinchZoom(false);//true就是通过两手指滑动，xy轴都变化，false各变化各的
        mChart.setDrawGridBackground(false);//背景
        mChart.animateY(1000);//动画1000毫秒后执行

//设置图标说明（这个通用，比较简单，不注释了）
Legend l = mChart.getLegend();
l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
l.setDrawInside(false);
l.setForm(Legend.LegendForm.CIRCLE);
l.setFormSize(9f);
l.setTextSize(11f);
l.setXEntrySpace(4f);

//设置自定义mark（参考第二个博客）

当点击图表上的点时，将会弹出一个显示详细信息的View，所以需要设计一个MakerView继承默认的 MakerView 重写样式
public class MyMakerView extends MarkerView { 
private TextView tvContent; 
public MyMakerView (Context context, int layoutResource) { 
super(context, layoutResource);
tvContent = (TextView) findViewById(R.id.tvContent); 
} 
/* 每次画 MakerView 时都会触发 Callback 方法，通常会在此方法内更新 View 的內容 */ 
@Override 
public void refreshContent(Entry e, Highlight highlight) { 
tvContent.setText("" + e.getVal()); 
} 
/* * offset 是以点到的那个点(0,0) 中心然后向右下角画出来 * 所以如果要显示在点上方 * X=宽度的一半，负数 * Y=高度的负数 */ 
@Override 
public int getXOffset() { 
// this will center the marker-view horizontally 
return -(getWidth() / 2); 
} 
@Override 
public int getYOffset() { 
// this will cause the marker-view to be above the selected value 
return -getHeight(); 
}
}
然后在chart里加入此makerview


MyMakerView makerview = new MyMakerView(this,xxxxid);
lineChart.setMakerView(makerview);
5、设置限制线（我这里设置的人均）
//设置人均线
        LimitLine ll1 = new LimitLine(average, "人均巡护面积");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);//三个参数，第一个线宽长度，第二个线段之间宽度，第三个一般为0，是个补偿
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);//标签位置
        ll1.setTextSize(10f);
在你设置数据的时候有一个方法
YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.addLimitLine(ll1);//添加进去就可以了

6、设置x、y轴数据（重点，对于我来说，嘎嘎）
//x轴数据
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//位置
        xAxis.setCenterAxisLabels(true);//设置在轴中间
        xAxis.setDrawGridLines(false);//轴线
        xAxis.setLabelCount(areaname.length, false);    //设置显示标签书，FALSE为自动调整
        xAxis.setLabelRotationAngle(45);//设置x轴变迁的角度
        xAxis.setGranularity(1f);//缩放时的最小距离
//        xAxis.setAxisMinimum(0f);//
//        xAxis.setAxisMaximum(areaname.length);
//        xAxis.setValueFormatter(new DayAxisValueFormatter(areaname));
/**
* 发现这个返回的value值是一个从-1开始到数据最大个数的死循环，
* 所以areaname[(int) (value +26)% areaname.length]，否则越界。
* 但是当你没有设置xAxis.setCenterAxisLabels(true);//设置在轴中间
* 那么这个value值是从0开始的到数据的一个循环，
* 我也看不懂源码，有高手可以指点一下。
*/  
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return areaname[(int) (value +26)% areaname.length];
            }
        });
        //y轴数据（设置基本和x轴一样）
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.addLimitLine(ll1);//此处是添加限制线
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(4, false);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "亩/人";
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(0f);//该控件距离上面的距离类似于marginTop
        /**
         * 如果设置了最大最小，那么不能动态计算轴的值
         */
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        //        leftAxis.setAxisMaximum(160f);  //动态设置最大,

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);//这个意思是不显示右边的y轴
7、设置数据 （从字面意思就可以理解了）
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

