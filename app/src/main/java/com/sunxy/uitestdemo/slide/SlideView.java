package com.sunxy.uitestdemo.slide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lenovo on 2016/12/7.
 */

public class SlideView extends View {

    private Paint mPaint;
    private String[] mLetters;


    private int mHeight;
    private int mWidth;
    private int mLetterHeight;

    private boolean mIsBeingDragger;

    private RectF mIsDownRect;
    private float mDensity;

    private float mY;
    private int mChoose;

    public SlideView(Context context) {
        super(context);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化变量
     * @param context
     */
    private void init(Context context){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setColor(Color.GRAY);//颜色
        mPaint.setTextAlign(Paint.Align.CENTER);

        mDensity = context.getResources().getDisplayMetrics().density;

        setPadding(0,dip2px(20),0,dip2px(20));
        mIsDownRect = new RectF();

    }

    public void setArrays(String[] mLetters){
        this.mLetters = mLetters;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mLetters == null){
            return;
        }
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h -getPaddingTop() - getPaddingBottom();
        mWidth = w - getPaddingLeft() - getPaddingRight() - dip2px(16);

        //得到字体的高度
        mLetterHeight = mHeight / mLetters.length;
        int testSize = (int)(mLetterHeight * 0.7f); //设置文字大小为高度的0.7
        mPaint.setTextSize(testSize);

        mIsDownRect.set(w - dip2px(35), 0, w, h); //设置感应区域
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLetters == null){
            return;
        }
        for (int i = 0; i < mLetters.length; i++){
            float lettersPos = mLetterHeight*(i+1) + getPaddingTop(); //字母y坐标
            float diffY; //x偏移
            float diffX; //y偏移
            float diff;  //缩放等级

            if(mChoose==i && i!=0 && i!=mLetters.length-1){
                //计算选择字母
                diff = 2.2f;
                diffX = 0;
                diffY = 0;
            }else{
                //计算未选择字母
                float distanseDiff = Math.abs((mY-lettersPos)/mHeight);
                float maxPos = distanseDiff * 7;

                if (distanseDiff < 0.174){
                    diff = 2.2f - maxPos;
                }else{
                    diff = 1f;
                }

                if(!mIsBeingDragger){
                    diff = 1f;
                }

                diffX = maxPos * 50;
                if(mY>lettersPos){
                    diffY = -maxPos*50;
                }else{
                    diffY = maxPos*50;
                }
            }

            if (diff == 1){
                mPaint.setAlpha(255);
                mPaint.setTypeface(Typeface.DEFAULT);
            }else{

                int alhpha = (int) (255*(1-Math.min(0.9,diff-1)));
                if(mChoose == i){
                    alhpha = 255;
                }
                mPaint.setAlpha(alhpha);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            }

            canvas.save();
            canvas.scale(diff,diff,mWidth*1.2f+diffX,lettersPos+diffY);
            canvas.drawText(mLetters[i], mWidth, lettersPos, mPaint);
            canvas.restore();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //判断如果触摸区域是在设置的感应区域内，响应，否则返回false
                if(!mIsDownRect.contains((int)event.getX(),(int)event.getY())){
                    return false;
                }
                mIsBeingDragger = true;
                downAndMoveEvent(event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                downAndMoveEvent(event.getY());
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mChoose = -1;
                mIsBeingDragger = false;
                invalidate();
                break;
        }
        return true;
    }

    private void downAndMoveEvent(float y){
        mY = y;
        float moveY = y - getPaddingTop();
        int charterIndex = (int) (moveY/mHeight*mLetters.length);
        if(mChoose != charterIndex && charterIndex < mLetters.length && charterIndex >=0){
            mChoose = charterIndex;
            if(listener != null ){
                //回调
                listener.onChange(mLetters[mChoose]);
            }
            invalidate();
        }
    }


    private int dip2px(int dip){
        return (int) (mDensity * dip + 0.5f);
    }

    interface SlideViewListener{
        void onChange(String letter);
    }

    private SlideViewListener listener;

    public void setSlideViewListener(SlideViewListener listener){
        this.listener = listener;
    }
}
