package com.sunxy.uitestdemo.stretching;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;

/**
 * 头部可拉伸的listview
 * SunXiaoYu on 2019/1/28.
 * mail: sunxiaoyu@hexinpass.com
 */
public class StretchingActivity extends BaseActivity {

    private StretchingListView stretchingListView;

    @Override
    protected int getLayoutId() {
        return R.layout.stretching_layout;
    }

    @Override
    protected void initView() {
        stretchingListView = findViewById(R.id.stretching_list_view);

        View head_layout = View.inflate(this, R.layout.head_layout, null);
        ImageView imageView = head_layout.findViewById(R.id.head_iv);
        stretchingListView.setImageView(imageView);
        stretchingListView.addHeaderView(head_layout);

        ListViewAdapter adapter = new ListViewAdapter(this);
        stretchingListView.setAdapter(adapter);


    }
}
