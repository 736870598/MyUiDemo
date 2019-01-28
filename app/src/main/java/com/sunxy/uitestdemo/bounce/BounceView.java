package com.sunxy.uitestdemo.bounce;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 弹性布局
 * Created by sunxiaoyu on 2017/1/6.
 */

public class BounceView extends View {

    private Paint mPaint;
    private Path mPath = new Path();

    private Status mStatus = Status.NONE;

    /**
     * 动画执行百分比
     */
    private float percent;
    /**
     * 布局高度
     */
    private int mMaxHeight = 900;
    /**
     * 辅助控制点最大高度
     */
    private int maxBounceHeight = 300;
    /**
     * 上升动画时间
     */
    private long showTime = 600;
    /**
     * 回弹动画时间
     */
    private long bounceTime = 300;


    public enum Status{
        NONE,STATUS_UP,STATUS_DOWN;
    }

    public BounceView(Context context) {
        super(context);
        init();
    }

    public BounceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BounceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBG(canvas);
    }

    /**
     * 设置平稳后的高度，默认900
     * @param mMaxHeight
     */
    public void setmMaxHeight(int mMaxHeight) {
        this.mMaxHeight = mMaxHeight;
    }

    /**
     * 设置辅助控制点最大高度， 默认为300
     * @param maxBounceHeight
     */
    public void setMaxBounceHeight(int maxBounceHeight) {
        this.maxBounceHeight = maxBounceHeight;
    }

    /**
     * 设置上升动画时间，默认为600ms
     * @param showTime
     */
    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    /**
     * 设置回弹动画时间，默认为300ms
     * @param bounceTime
     */
    public void setBounceTime(long bounceTime) {
        this.bounceTime = bounceTime;
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);

    }

    /**
     * 绘制曲线
     * @param canvas
     */
    private void drawBG(Canvas canvas) {

        int currentPointY = 0;
        int bY = 0;

        switch (mStatus){
            case NONE:
                currentPointY = 0;
                break;
            case STATUS_UP:
                currentPointY = (int) (getHeight() - mMaxHeight * percent);
                bY = currentPointY - (int)(percent * maxBounceHeight);
                break;
            case STATUS_DOWN:
                currentPointY = getHeight() - mMaxHeight;
                bY = currentPointY - (int)((1 - percent) * maxBounceHeight);
                break;
        }


        mPath.reset();
        mPath.moveTo(0, currentPointY);
        mPath.quadTo(getWidth() / 2, bY , getWidth(), currentPointY);
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);

    }

    /**
     * 开始执行上升动画
     */
    public void show() {
        mStatus = Status.STATUS_UP;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mMaxHeight);
        valueAnimator.setDuration(showTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = animation.getAnimatedFraction();
                if(percent == 1.0f){
                    bounce();
                }

                invalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 开始执行回弹动画
     */
    private void bounce() {
        mStatus = Status.STATUS_DOWN;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(mMaxHeight, 0);
        valueAnimator.setDuration(bounceTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = animation.getAnimatedFraction();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
