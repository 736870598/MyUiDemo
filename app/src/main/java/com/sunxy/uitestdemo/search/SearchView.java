package com.sunxy.uitestdemo.search;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sunxy.uitestdemo.R;

/**
 * SearchView
 * Created by Administrator on 2017/5/19/019.
 */
public class SearchView extends View {

    private int paintColor;
    private Paint mPaint;

    private int measureW;
    private int measureH = 80;

    private float progress = 1; //进度： 1-0.5 圆弧在变，  0.5-0 尾巴在变

    private int radius = 20;

    private Point arcP, lineP;

    private double cos, sin;

    private RectF rectF;
    private float spaceValue;

    private int status = 2; //0 啥都没干了，1 正在打开中，2 正在关闭中

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置 width，通过和 measureH 比例重新设置 radius
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        if(paintColor == 0){
            paintColor = Color.WHITE;
        }
        mPaint.setColor(paintColor);
        mPaint.setStrokeWidth(5);
        cos = Math.cos(Math.toRadians(45));
        sin = Math.sin(Math.toRadians(45));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        getSize();

        canvas.save();
        if(progress == 1){
            //最开始，还没开始动
            canvas.drawArc(rectF, 0, 360, false, mPaint);
            canvas.drawLine(arcP.x, arcP.y, lineP.x, lineP.y, mPaint);
        }else if(progress <= 0){
            //最后，完了
            canvas.drawLine(lineP.x, lineP.y, 0, lineP.y, mPaint);
        }else{
            if (progress > 0.5){ //圆弧在变
                canvas.drawArc(rectF, 45, (float) (-315 * (progress - 0.5) / 0.5), false, mPaint);
                canvas.drawLine(arcP.x, arcP.y, lineP.x, lineP.y, mPaint);
            }else{ //尾巴在变  (0.5-0)
                int r = (int) (radius * 2 * (0.5f + progress));
                canvas.drawLine(lineP.x, lineP.y,
                        (float) (lineP.x - r * cos), (float) (lineP.y - r * sin), mPaint);
            }
            canvas.drawLine(lineP.x, lineP.y, measureW * progress, lineP.y, mPaint);
        }
        canvas.restore();

        if (progress > 1){
            progress = 1;
            postInvalidate();
        }else if(progress < 0){
            progress = 0;
            postInvalidate();
        }else if (progress != 0 && progress != 1){
            changeValue();
            postInvalidateDelayed(20);
        }
    }

    private void getSize(){
        if (measureW == 0){
            measureW = getWidth();
            measureH = getHeight() - 10;

            radius = 20 * measureH / 80;

            int centerX = (int) (measureW - (radius * 3 ) * cos);
            rectF = new RectF(centerX - radius, measureH / 2 - radius, centerX + radius, measureH / 2 + radius);

            int arcX = (int) (measureW - (radius * 2) * cos);
            int arcY = (int) (measureH / 2 + (radius) * sin);
            arcP = new Point(arcX, arcY);

            int lineX = measureW;
            int lintY = (int) (measureH / 2 + (radius * 3 ) * sin);
            lineP = new Point(lineX, lintY);
        }
    }

    private void changeValue(){
        progress += spaceValue;
    }

    public void open() {
        if (status != 1){
            status = 1;
            progress = 1;
            spaceValue = -0.05f;
            changeValue();
            invalidate();
        }
    }

    public void close() {
        if (status != 2){
            status = 2;
            progress = 0;
            spaceValue = 0.05f;
            changeValue();
            invalidate();
        }
    }
}
