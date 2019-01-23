package com.sunxy.uitestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunxy.uitestdemo.adapter.MyAdapter;
import com.sunxy.uitestdemo.model.UiModel;
import com.sunxy.uitestdemo.ui.DelEditViewActivity;
import com.sunxy.uitestdemo.ui.FlowLayoutActivity;
import com.sunxy.uitestdemo.ui.PosRecycleViewActivity;
import com.sunxy.uitestdemo.ui.PraiseViewActivity;
import com.sunxy.uitestdemo.ui.RecordBtnActivity;

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

        return list;
    }
}
