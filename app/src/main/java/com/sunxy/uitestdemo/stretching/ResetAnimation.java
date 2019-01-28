package com.sunxy.uitestdemo.stretching;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by lenovo on 2016/12/23.
 */

public class ResetAnimation extends Animation {

    private int originHeight;
    private int deltaHeight;
    private View iv;

    public ResetAnimation(View iv){
        this.iv = iv;
    }

    public void setHeightInfo(int originHeight, int targetHeight){
        this.originHeight = originHeight;
        deltaHeight = originHeight - targetHeight;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        iv.getLayoutParams().height = (int) (originHeight - deltaHeight * interpolatedTime);
        iv.requestLayout();
        super.applyTransformation(interpolatedTime, t);
    }

}
