package com.sunxy.uitestdemo.drag_bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxy.uitestdemo.R;

/**
 * 仿qq提示消息个数拖赘效果
 * Created by sunxiaoyu on 2017/1/10.
 */
public class MyDragView extends FrameLayout{

    //原来显示个数的子TextView
    private TextView childView;
    //原来子TextView所在的矩形位置
    private Rect rect;
    //原来子TextView的中心点（开始点）
    private Point startPoint;
    //现在手指移动的点
    private Point nowPoint;

    //是否触摸
    private boolean isTouch;
    //是否在播放动画
    private boolean isAniming;
    //是否需要播放动画
    private boolean isNeedAnim;

    //现在的可以满屏跑的textview
    private TextView mTextView;
    //爆炸动画图片
    private ImageView animView;

    //状态栏高度
    private int statusBarHeight;

    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;

    //最大拖动距离，超出后断开
    private float maxDistance = 300f;
    //默认圆的半径（为控件最小宽高的3/4）
    private int defaultRadius;
    //圆的最小半径
    private int minRadius = 12;
    //当前圆的半径
    private int mRadius;

    //贝赛尔的四个点及控制点
    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;
    private Point controlPoint;


    public MyDragView(Context context) {
        super(context);
        this.statusBarHeight = getStatusBarHeight();
        init();
    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 初始化
     */
    private void init(){
        startPoint = new Point();
        nowPoint = new Point();
        rect = new Rect();

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mTextView = new TextView(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mTextView.setLayoutParams(layoutParams);

        animView = new ImageView(getContext());
        animView.setLayoutParams(layoutParams);
        animView.setImageResource(R.drawable.anim_bg);
        animView.setVisibility(GONE);

        addView(mTextView);
        addView(animView);

    }

    boolean isInit;
    private void initWindowManager(){
        if (isInit){
            return;
        }
        isInit = true;
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.format = PixelFormat.TRANSLUCENT;
        windowManager.addView(this, layoutParams);

    }

    public void attachToView(View view){
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    initWindowManager();
                    setView((TextView) v);
                }
                onTouchEvent(event);
                return true;
            }
        });
    }

    /**
     * 设置控件
     * @param view
     */
    public void setView(TextView view){

        //如果当前在播放动画，就不显示
        if(isAniming){
            return ;
        }

        //否则
        this.childView = view;

        //获取原来控件中心坐标点
        int[] location = new int[2];
        childView.getLocationOnScreen(location);
        rect.set(location[0], location[1], location[0]+childView.getWidth(), location[1]+childView.getHeight());
        startPoint.set(location[0]+childView.getWidth()/2, location[1]+childView.getHeight()/2);

        //设置默认圆的半径
        defaultRadius = childView.getWidth() > childView.getHeight() ? childView.getHeight()/2 : childView.getWidth()/2;
        defaultRadius = defaultRadius * 3 / 4;
        mRadius = defaultRadius;

        //将原来的控件属性赋值到现在的控件上，将原来控件隐藏，现在的控件显示
        mTextView.setText(childView.getText());
        mTextView.setTextColor(childView.getTextColors());
        mTextView.setBackground(childView.getBackground());
        mTextView.setPadding(childView.getPaddingLeft(), childView.getPaddingTop(),
                childView.getPaddingRight(), childView.getPaddingBottom());
        childView.setVisibility(INVISIBLE);
        mTextView.setVisibility(VISIBLE);

        //将整个FrameLayout显示
        setVisibility(View.VISIBLE);

        //设置是在触摸
        isTouch = true;
        //刷新界面
        invalidate();
    }

    /**
     *  touch事件，赋值nowPoint为现在触摸的坐标点，当放开时判断如果触摸点在原来控件矩形内，则不播放动画，
     *  return : true 表示消费该事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        nowPoint.set((int)event.getRawX(), (int)event.getRawY());
        if(event.getAction() == MotionEvent.ACTION_UP){
            isTouch = false;
            if(rect.contains(nowPoint.x, nowPoint.y)){
                isNeedAnim = false;
                isAniming = false;
            }
        }
        invalidate();
        return true;
    }

    /**
     * 刷新界面，在onDrow（）方法后该方法被调用，绘制子控件，如果此处调用onDrow（）方法，则FrameLayout布局的属性将消失
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {

        if (childView == null){
            return;
        }

        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        //移除状态栏高度的影响
        canvas.translate(0, -statusBarHeight);

        //如果在播放动画，啥都不干，否则如果有触摸，则绘制；如果需要播放动画就播放动画。 要是啥都没有就隐藏framelayout布局，显示原理控件
        if(!isAniming){
            if(isTouch) {

                calculatePath();
                canvas.drawPath(mPath, mPaint);
                canvas.drawCircle(startPoint.x, startPoint.y, mRadius, mPaint);
                mTextView.setX(nowPoint.x - mTextView.getWidth() / 2);
                mTextView.setY(nowPoint.y - mTextView.getHeight() / 2 - statusBarHeight);

            }else if(isNeedAnim){

                mTextView.setVisibility(GONE);
                animView.setVisibility(VISIBLE);
                animView.setX(nowPoint.x - mTextView.getWidth() / 2);
                animView.setY(nowPoint.y - mTextView.getHeight() / 2 - statusBarHeight);
                AnimationDrawable AnimationDrawable = (android.graphics.drawable.AnimationDrawable) animView.getDrawable();
                AnimationDrawable.start();
                //是否需要播放设置为false，动画播放中设置为true
                isAniming = true;
                isNeedAnim = false;
                animView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //动画播放完将动画控件及整个framelayout隐藏，
                        animView.setVisibility(GONE);
                        mTextView.setVisibility(GONE);
                        isAniming = false;
                        setVisibility(GONE);
                    }
                }, 740);

            }else{

                childView.setVisibility(VISIBLE);
                animView.setVisibility(GONE);
                mTextView.setVisibility(GONE);
                setVisibility(GONE);
            }
        }

        canvas.restore();

        super.dispatchDraw(canvas);
    }


    /**
     * 计算路径
     * 如果需要播放，说明之前距离已经大于过最大距离，不绘制连线
     * 如果俩点距离大于最大距离的话,同样不绘制连线
     * 否则计算圆坐标信息，最后绘制路径
     */
    private void calculatePath() {
        if(isNeedAnim){
            mRadius = 0;
            mPath.reset();
            return ;
        }
        int distance = (int) Math.sqrt(Math.pow(startPoint.x - nowPoint.x, 2) + Math.pow(startPoint.y - nowPoint.y, 2));

        if(distance > maxDistance){
            isNeedAnim = true;
            mRadius = 0;
            mPath.reset();
            return;
        }

        mRadius = (int) (defaultRadius - (defaultRadius - minRadius) * distance / maxDistance);
        getPointInfo(distance);

        mPath.reset();
        mPath.moveTo(p1.x, p1.y);
        mPath.quadTo(controlPoint.x, controlPoint.y, p3.x, p3.y);
        mPath.lineTo(p4.x, p4.y);
        mPath.quadTo(controlPoint.x, controlPoint.y, p2.x, p2.y);
        mPath.close();

    }


    /**
     * 得到坐标信息
     */
    private void getPointInfo(int distance){
        if (p1 == null) {
            p1 = new Point();
            p2 = new Point();
            p3 = new Point();
            p4 = new Point();
            controlPoint = new Point();
        }


        if (nowPoint.x == startPoint.x) {            //x相同
            p1.x = nowPoint.x - defaultRadius;
            p1.y = nowPoint.y;
            p2.x = nowPoint.x + defaultRadius;
            p2.y = nowPoint.y;
            p3.x = startPoint.x - mRadius;
            p3.y = startPoint.y;
            p4.x = startPoint.x + mRadius;
            p4.y = startPoint.y;
        } else if (nowPoint.y == startPoint.y) {      //y相同
            p1.x = nowPoint.x;
            p1.y = nowPoint.y - defaultRadius;
            p2.x = nowPoint.x;
            p2.y = nowPoint.y + defaultRadius;
            p3.x = startPoint.x;
            p3.y = startPoint.y - mRadius;
            p4.x = startPoint.x;
            p4.y = startPoint.y + mRadius;
        } else {

            double degrees = (Math.atan((float)Math.abs(nowPoint.y - startPoint.y) / Math.abs(nowPoint.x - startPoint.x)));
            int offsetX = (int) Math.abs(mRadius * Math.sin(degrees));
            int offsetY = (int) Math.abs(mRadius * Math.cos(degrees));

            int offsetX1 = (int) Math.abs(defaultRadius * Math.sin(degrees));
            int offsetY1 = (int) Math.abs(defaultRadius * Math.cos(degrees));

            if (startPoint.x > nowPoint.x && startPoint.y > nowPoint.y) { //现在的圆在开始的左上方
                p1.x = nowPoint.x - offsetX1;
                p1.y = nowPoint.y + offsetY1;
                p2.x = nowPoint.x + offsetX1;
                p2.y = nowPoint.y - offsetY1;
                p3.x = startPoint.x - offsetX;
                p3.y = startPoint.y + offsetY;
                p4.x = startPoint.x + offsetX;
                p4.y = startPoint.y - offsetY;
            } else if (startPoint.x > nowPoint.x && startPoint.y < nowPoint.y) { //现在的圆在开始的左下方
                p1.x = nowPoint.x + offsetX1;
                p1.y = nowPoint.y + offsetY1;
                p2.x = nowPoint.x - offsetX1;
                p2.y = nowPoint.y - offsetY1;
                p3.x = startPoint.x + offsetX;
                p3.y = startPoint.y + offsetY;
                p4.x = startPoint.x - offsetX;
                p4.y = startPoint.y - offsetY;
            } else if (startPoint.x < nowPoint.x && startPoint.y > nowPoint.y) { //现在的圆在开始的右上方
                p1.x = nowPoint.x - offsetX1;
                p1.y = nowPoint.y - offsetY1;
                p2.x = nowPoint.x + offsetX1;
                p2.y = nowPoint.y + offsetY1;
                p3.x = startPoint.x - offsetX;
                p3.y = startPoint.y - offsetY;
                p4.x = startPoint.x + offsetX;
                p4.y = startPoint.y + offsetY;
            } else if (startPoint.x < nowPoint.x && startPoint.y < nowPoint.y) { //现在的圆在开始的右下方
                p1.x = nowPoint.x + offsetX1;
                p1.y = nowPoint.y - offsetY1;
                p2.x = nowPoint.x - offsetX1;
                p2.y = nowPoint.y + offsetY1;
                p3.x = startPoint.x + offsetX;
                p3.y = startPoint.y - offsetY;
                p4.x = startPoint.x - offsetX;
                p4.y = startPoint.y + offsetY;
            }

        }

        controlPoint.x = (startPoint.x + nowPoint.x) / 2;
        controlPoint.y = (startPoint.y + nowPoint.y) / 2;

        float sc = distance / maxDistance;
        controlPoint.x = (int) (startPoint.x + (nowPoint.x-startPoint.x)*sc);
        controlPoint.y = (int) (startPoint.y + (nowPoint.y-startPoint.y)*sc);

    }
}
