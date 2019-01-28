package com.sunxy.uitestdemo.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * SunXiaoYu on 2019/1/28.
 * mail: sunxiaoyu@hexinpass.com
 */
public class VerticalViewPagerActivity extends BaseActivity {

    VerticalViewPager viewPager;
    private View view1;
    private View view2;

    private TextView tv1,tv2;


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
        viewPager = new VerticalViewPager(this);
        return viewPager;
    }

    @Override
    protected void initView() {
        setAdapter();
    }

    /**
     * 设置adapter
     */
    private void setAdapter() {
        view1 = getLayoutInflater().inflate(R.layout.vertical_item_view, null);
        tv1 = view1.findViewById(R.id.item_view_text);
        tv1.setBackgroundColor(Color.GREEN);
        tv1.setText("view1");
        view2 = getLayoutInflater().inflate(R.layout.vertical_item_view, null);
        tv2 = view2.findViewById(R.id.item_view_text);
        tv2.setBackgroundColor(Color.RED);
        tv2.setText("view2");
        List<View> list = new ArrayList<>();
        list.add(view1);
        list.add(view2);

        ViewPagerAdapter adaper = new ViewPagerAdapter(list);
        viewPager.setAdapter(adaper);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.v("sxy", "onPageSelected : " + position );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}


