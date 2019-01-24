package com.sunxy.uitestdemo.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecycleView的layoutManager位LinLayoutManager时的分割线
 *
 * RecycleView要用margin，不要用padding，padding有坑
 *
 *
 * 使用方法：
 *  recyclerView.addItemDecoration(new LinItemDecoration(Color.BLUE, 5, RecyclerView.VERTICAL));
 *
 * Created by sunxiaoyu on 2017/7/5.
 */
public class LinItemDecoration extends RecyclerView.ItemDecoration {

    private int color;          //分割线颜色
    private int thickness;      //分割线宽度
    private int orientation = RecyclerView.VERTICAL;  //布局方向


    private Rect rect;
    private Paint mPaint;
    private RecyclerView.LayoutParams layoutParams;
    private int left, right, top, bottom;
    private int showChildCount;
    private View childView;


    /**
     * @param color     分割线颜色
     * @param thickness 分割线宽度
     */
    public LinItemDecoration(int color, int thickness) {
        this.color = color;
        this.thickness = thickness;
        init();
    }

    /**
     * @param color         分割线颜色
     * @param thickness     分割线宽度
     * @param orientation   布局方向
     */
    public LinItemDecoration(int color, int thickness, int orientation) {
        this.color = color;
        this.thickness = thickness;
        this.orientation = orientation;
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        rect = new Rect();
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orientation == RecyclerView.VERTICAL){
            drawHorizontalLine(c, parent);
        }else {
            drawVerticalLine(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        itemOffset(outRect, view, parent);
    }


    /**
     * 竖向的布局，画横向的分割线
     */
    private void drawHorizontalLine(Canvas c, RecyclerView parent){
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();
        showChildCount = parent.getChildCount();
        for (int i = 0; i < showChildCount; i++){
            childView = parent.getChildAt(i);
            layoutParams = (RecyclerView.LayoutParams)childView.getLayoutParams();
            top = childView.getBottom() + layoutParams.bottomMargin;
            bottom = top + thickness;
            rect.set(left, top, right, bottom);
            c.drawRect(rect, mPaint);
        }
    }

    /**
     * 横向的布局，画竖向的分割线
     */
    private void drawVerticalLine(Canvas c, RecyclerView parent){
        top = parent.getPaddingTop();
        bottom = parent.getHeight() - parent.getPaddingBottom();
        showChildCount = parent.getChildCount();
        for (int i = 0; i < showChildCount; i++){
            childView = parent.getChildAt(i);
            layoutParams = (RecyclerView.LayoutParams)childView.getLayoutParams();
            left = childView.getRight() + layoutParams.rightMargin;
            right = left + thickness;
            rect.set(left, top, right, bottom);
            c.drawRect(rect, mPaint);
        }
    }

    /**
     * 设置item的偏移
     */
    private void itemOffset(Rect outRect, View view, RecyclerView parent){
        //如果view是RecyclerView的最后一个，则不需要进行位移
        int nowPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int ItemCount = parent.getAdapter().getItemCount();

        if (nowPosition == ItemCount-1){
            outRect.set(0, 0, 0, 0);
            return;
        }

        if(orientation == RecyclerView.VERTICAL){
            //竖向布局，分割线横向，每一个布局向下平移一个分割线宽度
            outRect.set(0, 0, 0, thickness);
        }else {
            //横向布局，分割线竖向，每一个布局向右平移一个分割线的宽度
            outRect.set(0, 0, thickness, 0);
        }
    }

}
