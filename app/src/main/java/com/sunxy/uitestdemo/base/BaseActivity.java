package com.sunxy.uitestdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sunxy.uitestdemo.home.UiModel;

/**
 * SunXiaoYu on 2019/1/23.
 * mail: sunxiaoyu@hexinpass.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutView();
        if (view != null){
            setContentView(view);
        }else{
            setContentView(getLayoutId());
        }
        try{
            UiModel uiModel = (UiModel) getIntent().getSerializableExtra("model");
            setTitle(uiModel.getTitle());
        }catch (Exception e){
            e.printStackTrace();
        }
        initView();
    }

    protected abstract int getLayoutId();
    protected void initView(){}
    protected View getLayoutView(){
        return null;
    }
}
