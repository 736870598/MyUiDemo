package com.sunxy.uitestdemo.three_bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 三阶贝赛尔曲线效果
 * Created by sunxiaoyu on 2017/1/9.
 */

public class BezierThreeView extends View {

    //默认伸缩因子 当伸缩因子为该值时通过Path.cubicTo()方法绘制出是一个完整的圆
    //参考 ：  http://spencermortensen.com/articles/bezier-circle/
    private final static float C = 0.55191502449f;

    //中心点向 四个方向的伸缩因子系数
    private float c_l = 1;
    private float c_r = 1;
    private float c_t = 1;
    private float c_b = 1;

    //半径
    private int radius = 100;
    //X轴滑动距离
    private int distanceX = 600;
    //Y轴滑动距离
    private int distanceY = 0;
    //开始是中心点坐标
    private int centerX = radius;
    private int centerY = radius;

    private Paint myPaint;
    private Path mpath;

    //中心点及上、下、左、右 四个点
    private Point cPoint;
    private XPoint lPoint, rPoint;
    private YPoint tPoint, bPoint;

    private boolean isMoveing;


    public BezierThreeView(Context context) {
        super(context);
        initView();
    }

    public BezierThreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BezierThreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化方法，初始化路径，画笔，及各个点
     */
    private void initView(){

        mpath = new Path();

        myPaint = new Paint();
        myPaint.setColor(Color.GREEN);
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setStrokeWidth(1);
        myPaint.setAntiAlias(true);

        cPoint = new Point(centerX,centerY);
        tPoint = new YPoint();
        bPoint = new YPoint();
        lPoint = new XPoint();
        rPoint = new XPoint();

        setPoint();
    }

    /**
     * 设置点坐标
     * 设置每个点的x,y值和伸缩因子点
     * 对于t, b等YPoint来说。左右俩个伸缩因子点的坐标中 y值和t/b点的y值相同，x值受伸缩因子及伸缩因子比例影响
     * 对于l, r等XPoint来说。上下俩个伸缩因子点的坐标中 x值和l/r点的x值相同，y值受伸缩因子及伸缩因子比例影响，但是对于x轴移动来说，y的值也不会变
     *
     * 画个图就明白了  http://www.jianshu.com/p/791d3a791ec2
     */
    private void setPoint(){
        tPoint.setInfo(cPoint.x, cPoint.y-radius*c_t, cPoint.x-c_l*C*radius, cPoint.x+c_r*C*radius);
        bPoint.setInfo(cPoint.x, cPoint.y+radius*c_b, cPoint.x-c_l*C*radius, cPoint.x+c_r*C*radius);
        lPoint.setInfo(cPoint.x-radius*c_l, cPoint.y, cPoint.y-c_t*C*radius, cPoint.y+c_b*C*radius);
        rPoint.setInfo(cPoint.x+radius*c_r, cPoint.y, cPoint.y-c_t*C*radius, cPoint.y+c_b*C*radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //清除path
        mpath.reset();

        //移到t
        mpath.moveTo(tPoint.x, tPoint.y);
        //t-r
        mpath.cubicTo(tPoint.r, tPoint.y, rPoint.x, rPoint.t, rPoint.x, rPoint.y);
        //r-b
        mpath.cubicTo(rPoint.x, rPoint.b, bPoint.r, bPoint.y, bPoint.x, bPoint.y);
        //b-l
        mpath.cubicTo(bPoint.l, bPoint.y, lPoint.x, lPoint.b, lPoint.x, lPoint.y);
        //l-t
        mpath.cubicTo(lPoint.x, lPoint.t, tPoint.l, tPoint.y, tPoint.x, tPoint.y);

        //绘制path
        canvas.drawPath(mpath,myPaint);

    }

    /**
     * 情景1  （c_r: 1->2 ）（c_b: 1->2 ）(0-22%)(x,y不变)
     */
    private void status1(float pec){
        cPoint.x = centerX;
        cPoint.y = centerY;

        float cell = 1f + 1f * pec / 0.22f;

        if(distanceX > 0){
            c_r = cell;
        }else if (distanceX < 0){
            c_l = cell;
        }

        if(distanceY > 0){
            c_b = cell;
        }else if (distanceY < 0){
            c_t = cell;
        }
    }

    /**
     * 情景2  （c_l: 1->1.5 ）（c_t: 1->1.5 ）(22-44%)(x,y变化)
     */
    private void status2(float pec){
        cPoint.x = (int) (centerX + distanceX * pec / 0.44f);
        cPoint.y = (int) (centerY + distanceY * pec / 0.44f);

        float cell = 1f + 0.5f * pec / 0.22f;

        if(distanceX > 0){
            c_r = 2;
            c_l = cell;
        }else if(distanceX < 0){
            c_l = 2;
            c_r = cell;
        }

        if(distanceY > 0){
            c_b = 2;
            c_t = cell;
        }else if (distanceY < 0){
            c_t = 2;
            c_b = cell;
        }
    }

    /**
     * 情景3  （c_r: 2->1 ）（c_b: 2->1 ）(44-66%)(x,y变化)
     */
    private void status3(float pec){
        cPoint.x = (int) (centerX + distanceX * (pec + 0.22f) / 0.44f);
        cPoint.y = (int) (centerY + distanceY * (pec + 0.22f) / 0.44f);

        float cell = 2f - 1f * pec / 0.22f;

        if(distanceX > 0){
            c_l = 1.5f;
            c_r = cell;
        }else if (distanceX < 0){
            c_r = 1.5f;
            c_l = cell;
        }

        if(distanceY > 0){
            c_t = 1.5f;
            c_b = cell;
        }else if (distanceY < 0){
            c_b = 1.5f;
            c_t = cell;
        }
    }

    /**
     * 情景4  （c_l: 1.5->0.7 ）（c_t: 1.5->0.7 ） (66-88%)(x,y不变)
     */
    private void status4(float pec){
        cPoint.x = centerX + distanceX;
        cPoint.y = centerY + distanceY;

        float cell = 1.5f - 0.8f * pec / 0.22f;

        if(distanceX > 0){
            c_r = 1;
            c_l = cell;
        }else if (distanceX < 0){
            c_l = 1;
            c_r = cell;
        }

        if(distanceY > 0){
            c_b = 1;
            c_t = cell;
        }else if (distanceY < 0){
            c_t = 1;
            c_b = cell;
        }
    }

    /**
     * 情景5  （c_l: 0.7->1 ）（c_l: 0.7->1 ） (88-100%)(x,y不变)
     */
    private void status5(float pec){
        cPoint.x = centerX + distanceX;
        cPoint.y = centerY + distanceY;

        float cell = 1f;
        if(pec < 0.12f){
            cell = 0.7f + 0.3f * pec / 0.12f;
        }

        if(distanceX > 0){
            c_l = cell;
        }else if(distanceX < 0){
            c_r = cell;
        }

        if(distanceY > 0){
            c_t = cell;
        }else if(distanceY < 0){
            c_b = cell;
        }
    }

    class XPoint{
        float x;
        float y;
        float t;
        float b;

        public void setInfo(float x, float y, float t, float b) {
            this.x = x;
            this.y = y;
            this.t = t;
            this.b = b;
        }
    }

    class YPoint{
        float x;
        float y;
        float l;
        float r;

        public void setInfo(float x, float y, float l, float r) {
            this.x = x;
            this.y = y;
            this.l = l;
            this.r = r;
        }
    }


    public void show(float pec){
        if(pec == 0){
            isMoveing = true;
        }else if(pec == 1){
            isMoveing = false;
        }

        if(pec <= 0.22f){
            status1(pec);
        }else if(pec <= 0.44f){
            status2(pec-0.22f);
        }else if(pec <= 0.66f){
            status3(pec-0.44f);
        }else if(pec <= 0.88f){
            status4(pec-0.66f);
        }else{
            status5(pec-0.88f);
        }
        setPoint();
        invalidate();
    }


    private class MoveAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            show(interpolatedTime);
            invalidate();
        }
    }

    public void startAnimation(boolean isl_r) {
        if(isMoveing) {
            return;
        }

        if(isl_r){
            //左右移动
            distanceY = 0;
            centerY = cPoint.y;
            if (cPoint.x == radius){
                centerX = radius;
                distanceX = 7 * radius;
            }else if(cPoint.x == 8 * radius){
                centerX = 8 * radius;
                distanceX = -7 * radius;
            }
        }else{
            //上下移动
            distanceX = 0;
            centerX = cPoint.x;
            if(cPoint.y == radius){
                centerY = radius;
                distanceY = 7 * radius;
            }else if(cPoint.y == 8 * radius){
                centerY = 8 * radius;
                distanceY = -7 * radius;
            }
        }

        MoveAnimation move = new MoveAnimation();
        move.setDuration(1000);
        move.setInterpolator(new AccelerateDecelerateInterpolator());
        startAnimation(move);
    }

}
