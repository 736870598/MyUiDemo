package com.sunxy.uitestdemo.ui;

import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * SunXiaoYu on 2019/1/23.
 * mail: sunxiaoyu@hexinpass.com
 */
public class FlowLayoutActivity extends BaseActivity {

    private  FlowLayout flowLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.flow_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        flowLayout = findViewById(R.id.flow_layout);

        List<String> textList = new ArrayList<>();
        textList.add("根据亚足联的规则");
        textList.add("国足");
        textList.add("增加了子布局中的");
        textList.add("视频裁判");
        textList.add("这");
        textList.add("国足迎来亚洲杯淘汰赛第二个对手伊朗");
        textList.add("90");
        textList.add("亚洲杯");
        textList.add("还有红牌的判罚");

        flowLayout.setItemTextColor(Color.WHITE);
        flowLayout.setItemTextSize(16);
//        layout1.setItemBackgroundResource(R.drawable.ic_launcher);
        flowLayout.setItemBackGroundColor(Color.BLUE);
        flowLayout.setItemMargin(20,20,20,20);
        flowLayout.setItemPadding(20,20,20,20);
        flowLayout.setList(textList);

        flowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {

            @Override
            public void onItemClick(int position, TextView view) {
                Toast.makeText(view.getContext(), "index=" + position + ", text=" + view.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position, TextView view) {
                Toast.makeText(view.getContext(), "index=" + position + ", text=" + view.getText(), Toast.LENGTH_SHORT).show();
                flowLayout.removeItemView(position);
            }
        });

    }
}
