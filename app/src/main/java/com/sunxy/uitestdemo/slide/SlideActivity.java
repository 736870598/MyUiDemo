package com.sunxy.uitestdemo.slide;

import android.widget.TextView;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;

/**
 * SunXiaoYu on 2019/1/25.
 * mail: sunxiaoyu@hexinpass.com
 */
public class SlideActivity extends BaseActivity {

    private TextView textView;
    private SlideView slideView;

    @Override
    protected int getLayoutId() {
        return R.layout.slide_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        textView = findViewById(R.id.text_view);
        slideView = findViewById(R.id.slide_view);
        slideView.setSlideViewListener(new SlideView.SlideViewListener() {
            @Override
            public void onChange(String letter) {
                textView.setText(letter);
            }
        });
        slideView.setArrays(getResources().getStringArray(R.array.letters));
    }
}
