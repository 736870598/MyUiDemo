package com.sunxy.uitestdemo.bounce;

import android.view.View;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;

public class BounceActivity extends BaseActivity {

    private BounceMenu bounceMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.bounce_layout;
    }

    @Override
    protected void initView() {
        super.initView();

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bounceMenu = BounceMenu.makeView(v.getContext(), v, new BounceViewAdapter(20)).show();
            }
        });

        findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bounceMenu != null && bounceMenu.isShowing()){
                    bounceMenu.dismiss(false);
                }
            }
        });
    }
}
