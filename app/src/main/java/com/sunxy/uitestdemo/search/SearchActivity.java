package com.sunxy.uitestdemo.search;

import android.view.View;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;

/**
 * SunXiaoYu on 2019/1/28.
 * mail: sunxiaoyu@hexinpass.com
 */
public class SearchActivity extends BaseActivity {

    private SearchView searchView;

    @Override
    protected int getLayoutId() {
        return R.layout.search_layout;
    }

    @Override
    protected void initView() {
        searchView = findViewById(R.id.search_view);
    }

    public void openBtn(View view){
        searchView.open();
    }

    public void closeBtn(View view){
        searchView.close();
    }


}
