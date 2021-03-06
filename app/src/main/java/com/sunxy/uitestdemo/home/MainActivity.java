package com.sunxy.uitestdemo.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.bounce.BounceActivity;
import com.sunxy.uitestdemo.darg.DragActivity;
import com.sunxy.uitestdemo.drag_bezier.DragBezierActivity;
import com.sunxy.uitestdemo.editview.DelEditViewActivity;
import com.sunxy.uitestdemo.flow.FlowLayoutActivity;
import com.sunxy.uitestdemo.pos_recycle.PosRecycleViewActivity;
import com.sunxy.uitestdemo.praise.PraiseViewActivity;
import com.sunxy.uitestdemo.record.RecordBtnActivity;
import com.sunxy.uitestdemo.search.SearchActivity;
import com.sunxy.uitestdemo.slide.SlideActivity;
import com.sunxy.uitestdemo.stretching.StretchingActivity;
import com.sunxy.uitestdemo.three_bezier.ThreeBezierActivity;
import com.sunxy.uitestdemo.utils.LinItemDecoration;
import com.sunxy.uitestdemo.viewpager.VerticalViewPagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycle_view = findViewById(R.id.recycle_view);
        recycle_view.addItemDecoration(new LinItemDecoration(Color.GREEN, 5));
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter();
        recycle_view.setAdapter(adapter);
        adapter.setList(initData());
    }

    private List<UiModel> initData(){
        List<UiModel> list = new ArrayList<>();
        list.add(new UiModel("防微信录制视频按钮效果", RecordBtnActivity.class));
        list.add(new UiModel("防即客APP点赞效果", PraiseViewActivity.class));
        list.add(new UiModel("RecycleView滑动定位", PosRecycleViewActivity.class));
        list.add(new UiModel("带删除和隐藏功能的editView", DelEditViewActivity.class));
        list.add(new UiModel("自定义流式布局", FlowLayoutActivity.class));
        list.add(new UiModel("item拖拽效果", DragActivity.class));
        list.add(new UiModel("三阶Bezier动画效果", ThreeBezierActivity.class));
        list.add(new UiModel("防QQ拖拽消息泡效果", DragBezierActivity.class));
        list.add(new UiModel("防汽车之家炫酷侧边栏效果", SlideActivity.class));
        list.add(new UiModel("弹性Dialog。。", BounceActivity.class));
        list.add(new UiModel("头部可拉伸的listview", StretchingActivity.class));
        list.add(new UiModel("可垂直滑动的viewpager", VerticalViewPagerActivity.class));
        list.add(new UiModel("自定义搜索View", SearchActivity.class));

        return list;
    }
}
