package com.sunxy.uitestdemo.stretching;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * SunXiaoYu on 2019/1/28.
 * mail: sunxiaoyu@hexinpass.com
 */
public class StretchingListView extends ListView {

    private ImageView imageView;
    private int imageHeight;
    private View parentView;
    private ResetAnimation anim;

    public StretchingListView(Context context) {
        super(context);
    }

    public StretchingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StretchingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }


    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        /**
         * deltaY  轴偏移量
         *   -  下拉过渡
         *   +  上拉过渡
         */
        if (deltaY < 0){    //下拉过度
            imageView.getLayoutParams().height = imageView.getHeight() - deltaY;
            imageView.requestLayout();
        }else{  //上拉过渡
            if(imageView.getHeight() > imageHeight){
                imageView.getLayoutParams().height = imageView.getHeight() - deltaY;
                imageView.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    //上拉图片缩小
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //获取图片高度
        if(imageHeight == 0){
            imageHeight = imageView.getHeight();
        }

        if(parentView == null){
            parentView = (View) imageView.getParent();
        }

        //上拉状态
        int deltaY = parentView.getTop();

        if (deltaY < 0 && imageView.getHeight() > imageHeight){
            imageView.getLayoutParams().height = imageView.getHeight() + deltaY;
            parentView.layout(parentView.getLeft(), 0, parentView.getRight(), parentView.getHeight());
            imageView.requestLayout();
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }

    //手松开回弹
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP){
            if(anim == null){
                anim = new ResetAnimation(imageView);
                anim.setDuration(300);
            }

            if(imageHeight == 0){
                imageHeight = imageView.getHeight();
            }
            anim.setHeightInfo(imageView.getHeight(), imageHeight);
            imageView.startAnimation(anim);

        }
        return super.onTouchEvent(ev);
    }


}
