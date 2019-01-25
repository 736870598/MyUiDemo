package com.sunxy.uitestdemo.drag_bezier;

import android.view.View;
import android.widget.TextView;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;

/**
 *
 * SunXiaoYu on 2019/1/24.
 * mail: sunxiaoyu@hexinpass.com
 */
public class DragBezierActivity extends BaseActivity {

    private MyDragView dragView;
    private TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.drag_bezier_layout;
    }

    @Override
    protected void initView() {
        super.initView();

        findViewById(R.id.showPop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.VISIBLE);
            }
        });

        dragView = new MyDragView(this);
        textView = findViewById(R.id.textView);
        dragView.attachToView(textView);

    }
}
