package com.yundian.star.ui.main.fragment;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.TimeLineBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.TimeUtil;
import com.yundian.star.wxapi.LineMarkerView;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/6/1.
 * 分时图
 */

public class KChartFragment extends BaseFragment {
    @Bind(R.id.chart)
    CombinedChart mChart;

    @Bind(R.id.imageView_head)
    ImageView imageView_head ;
    @Bind(R.id.tv_preice)
    TextView tv_preice ;
    @Bind(R.id.tv_change)
    TextView tv_change ;

    private int colorHomeBg;
    private int colorDivide;
    private int colorText;
    private int colorLine;
    private MyHandler mHandler;
    private String code;
    private String wid;
    private String head_url;
    private ArrayList<TimeLineBeen.PriceinfoBean> lineEntities;
    private LineMarkerView mvLine;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragmentl_kchart;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments()!=null){
            code = getArguments().getString(AppConstant.STAR_CODE);
            wid = getArguments().getString(AppConstant.STAR_WID);
            head_url = getArguments().getString(AppConstant.STAR_HEAD_URL);
            LogUtils.loge("明星code"+ code);
        }
        initChart();
        mHandler = new MyHandler(this);
        getData();
        initListener();
    }

    private void initListener() {
        ImageLoaderUtils.display(getContext(),imageView_head,head_url);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                entry.setData(TimeUtil.formatData(TimeUtil.dateFormat, lineEntities.get(entry.getXIndex()).getPriceTime()));
                    mvLine.refreshContent(entry, highlight);
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void getData() {
        NetworkAPIFactoryImpl.getInformationAPI().getTimeLine(SharePrefUtil.getInstance().getUserId(),SharePrefUtil.getInstance().getToken(),
                wid,5, new OnAPIListener<TimeLineBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(TimeLineBeen timeLineBeen) {
                LogUtils.loge("分时图" + timeLineBeen.toString());
                if (timeLineBeen.getPriceinfo()==null){
                    return;
                }
                loadChartData(timeLineBeen.getPriceinfo());

            }
        });
    }


    public void initChart() {
        colorHomeBg = getResources().getColor(R.color.white); //背景色
        colorDivide = getResources().getColor(R.color.white);//分割线
        colorText = getResources().getColor(R.color.color_666666);
        colorLine = getResources().getColor(R.color.red);//条目

        mChart.setDescription("");//描述信息
        mChart.setDrawGridBackground(false); //是否显示表格颜色
        mChart.setBackgroundColor(colorHomeBg);
        mChart.setGridBackgroundColor(colorHomeBg);
        mChart.setScaleYEnabled(true);  //Y轴激活
        mChart.setPinchZoom(false); //如果禁用,扩展可以在x轴和y轴分别完成
        mChart.setNoDataText("加载中...");
//        mChart.setAutoScaleMinMaxEnabled(true);
//        mChart.setDragEnabled(false); //可以拖拽
        mChart.setScaleEnabled(false);  //放大缩小
        mChart.getLegend().setEnabled(false);//图例
        mChart.setTouchEnabled(true);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});  //设置绘制顺序

//        mChart.requestDisallowInterceptTouchEvent(true);
        mChart.setExtraLeftOffset(-10);
        mChart.setExtraRightOffset(10);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(colorLine);
        xAxis.setTextColor(colorText);
        xAxis.setSpaceBetweenLabels(4);// 轴刻度间的宽度，默认值是4
        xAxis.setEnabled(false);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setLabelCount(6, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setGridColor(colorLine);
        rightAxis.setTextColor(colorText);
        rightAxis.setDrawGridLines(false);
        rightAxis.setStartAtZero(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
        refreshMarkerView();
    }

    public void loadChartData(ArrayList<TimeLineBeen.PriceinfoBean> currentTimeLineEntities) {
        if (currentTimeLineEntities.get(0).getChange()>=0){
            tv_change.setBackgroundColor(getActivity().getResources().getColor(R.color.color_CB4232));
            tv_preice.setTextColor(getContext().getResources().getColor(R.color.color_CB4232));
        }else {
            tv_change.setBackgroundColor(getActivity().getResources().getColor(R.color.color_18B03F));
            tv_preice.setTextColor(getContext().getResources().getColor(R.color.color_18B03F));
        }
        DecimalFormat format = new DecimalFormat("0.00%");
        String updown = format.format(currentTimeLineEntities.get(0).getPchg()/100);
        tv_change.setText(String.format(getContext().getResources().getString(R.string.star_price_time_share_limit),
                String.format("%.2f",currentTimeLineEntities.get(0).getChange()),updown));
        tv_preice.setText(String.format(getString(R.string.star_price_time_share),String.format("%.2f", currentTimeLineEntities.get(0).getCurrentPrice())));
        mChart.resetTracking();
        if (currentTimeLineEntities.size() == 0) {
            return;
        }
        Collections.reverse(currentTimeLineEntities);
        int itemcount = currentTimeLineEntities.size();
        lineEntities = currentTimeLineEntities;
        ArrayList<String> xVals = new ArrayList<>();  //X集合
        for (int i = 0; i < itemcount; i++) {
            String formatData = TimeUtil.formatData(TimeUtil.dateFormat, currentTimeLineEntities.get(i).getPriceTime());
            xVals.add(formatData);  //日期集合
        }
        CombinedData combinedData = new CombinedData(xVals);
        //分时图
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int index = 0; index < itemcount; index++) {
            float currentPrice = (float) currentTimeLineEntities.get(index).getCurrentPrice();
            yVals.add(new Entry((currentPrice), index));
        }
        LineDataSet lineDataSet = generateLineDataSet(yVals, colorLine, "");
        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData lineData = new LineData(xVals, dataSets);
        combinedData.setData(lineData); //加入MA5\MA10\MA20三种折线图数据
        refreshMarkerView();
        mChart.setData(combinedData);//当前屏幕会显示所有的数据
        //mChart.setVisibleXRange(30, 100);
        mChart.invalidate();
    }


    private static class MyHandler extends Handler {
        final private static int GRT_DATA = 111;
        private final WeakReference<KChartFragment> mFragment;

        public MyHandler(KChartFragment mfragment) {
            mFragment = new WeakReference<KChartFragment>(mfragment);
        }

        @Override
        public void handleMessage(Message msg) {
            KChartFragment fragment = mFragment.get();
            if (fragment != null) {
                switch (msg.what) {
                    case GRT_DATA:
                        fragment.getData();
                        fragment.mHandler.sendEmptyMessageDelayed(GRT_DATA, 3 * 1000);
                        break;
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mHandler != null) {
            mHandler.removeMessages(mHandler.GRT_DATA);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHandler != null) {
            mHandler.removeMessages(mHandler.GRT_DATA);
            mHandler.sendEmptyMessage(mHandler.GRT_DATA);
        }
    }

    private LineDataSet generateLineDataSet(List<Entry> entries, int color, String label) {
        LineDataSet set = new LineDataSet(entries, label);
        set.setColor(color);
        set.setLineWidth(1f);
        set.setDrawCubic(true);//圆滑曲线
        set.setDrawCircles(false);//设置有圆点
        set.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set.setFillColor(R.color.color_cccccc);
        set.setCubicIntensity(0.2f);
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        set.setHighlightEnabled(true);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setHighLightColor(R.color.color_666666);
        return set;
    }

    public void refreshMarkerView() {
        mvLine = new LineMarkerView(getContext(), R.layout.ly_marker_line);
        mChart.setMarkerView(mvLine);
    }
}
