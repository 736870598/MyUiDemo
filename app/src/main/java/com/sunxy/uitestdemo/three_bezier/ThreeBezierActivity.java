package com.sunxy.uitestdemo.three_bezier;

import android.view.View;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;

/**
 * 三阶 Bezier 曲线
 * SunXiaoYu on 2019/1/24.
 * mail: sunxiaoyu@hexinpass.com
 */
public class ThreeBezierActivity extends BaseActivity {

    private BezierThreeView bezierThreeView;

    @Override
    protected int getLayoutId() {
        return R.layout.three_bezier_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        bezierThreeView = findViewById(R.id.bezier_view);
    }

    public void topBottom(View view){
        bezierThreeView.startAnimation(true);
    }

    public void leftRight(View view){
        bezierThreeView.startAnimation(false);
    }
}
