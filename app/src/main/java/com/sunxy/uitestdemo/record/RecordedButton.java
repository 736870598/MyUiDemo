package com.sunxy.uitestdemo.record;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sunxy.uitestdemo.R;


/**
 * 录制button
 */
public class RecordedButton extends View implements View.OnTouchListener{

    //录制时间
    private int max = 21 * 1000;

    private OnGestureListener onGestureListener;
    private RectF oval;
    private Paint paint;
    private int measuredWidth = -1;
    private int foregroundColor;
    private int backgroundColor;
    private int clickColor;
    private int backgroundRadius;
    private int foregroundRadius;
    private int clickRadius;
    private Handler mHandler;

    private boolean isLongClick;
    private final static int START = 1;
    private ObjectAnimator animator;
    private float progress;

    //是否只能拍照
    private boolean onlyTakePhoto = false;

    /** 当前进度 以角度为单位 */
    private float girthPro;

    public RecordedButton(Context context) {
        super(context);
        init();
    }

    public RecordedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecordedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        foregroundColor = getResources().getColor(R.color.foreground_color);
        backgroundColor = getResources().getColor(R.color.background_color);
        clickColor = getResources().getColor(R.color.click_color);
        paint = new Paint();
        paint.setAntiAlias(true);
        setOnTouchListener(this);
        initHandler();
    }

    public void setOnGestureListener(OnGestureListener onGestureListener){
        this.onGestureListener = onGestureListener;
    }

    private void initHandler(){
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what){
                    case START:
                        isLongClick = true;
                        if (!onlyTakePhoto){
                            if (onGestureListener != null) onGestureListener.onLongClick();
                            startAnim();
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void startAnim(){
        stopAnim();
        animator = ObjectAnimator.ofFloat(RecordedButton.this, "progress", 0f, (float) max);
        animator.setDuration(max);
        animator.start();
    }

    public void stopAnim(){
        if (animator != null &&  animator.isStarted()){
            animator.pause();
            animator.cancel();
        }
        setProgress(0);
    }

    /**
     * 设置进度
     */
    public void setProgress(float progress){
        this.progress = progress;
        float ratio = progress/max;
        girthPro = 360*ratio;
        postInvalidate();

        if(ratio >= 1){
            if(onGestureListener != null) onGestureListener.onOver();
            stopAnim();
        }
    }

    public float getProgress() {
        return progress;
    }

    public void setOnlyTakePhoto(boolean onlyTakePhoto) {
        this.onlyTakePhoto = onlyTakePhoto;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                isLongClick = false;
                mHandler.sendEmptyMessageDelayed(START, 200);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHandler.removeMessages(START);
                if (isLongClick){
                    if (onGestureListener != null) onGestureListener.onOver();
                    stopAnim();
                }else{
                    if (onGestureListener != null) onGestureListener.onClick();
                }
        }
        return true;
    }

    /**
     * 测量 以宽度的一般为圆的半径
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(measuredWidth == -1) {
            measuredWidth = getMeasuredWidth();
            backgroundRadius = measuredWidth / 2;
            foregroundRadius = (int) (backgroundRadius * 0.8f);
            clickRadius = (int) (backgroundRadius * 0.4f);

            oval = new RectF(0,0,measuredWidth,measuredWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景园
        paint.setColor(backgroundColor);
        canvas.drawCircle(measuredWidth/2, measuredWidth/2, backgroundRadius, paint);

        if (girthPro == 0){
            //绘制内圈
            paint.setColor(foregroundColor);
            canvas.drawCircle(measuredWidth/2, measuredWidth/2, foregroundRadius, paint);
        }else{
            //绘制进度
            paint.setColor(clickColor);
            canvas.drawArc(oval, 270, girthPro, true, paint);
            //绘制内圈
            paint.setColor(foregroundColor);
            canvas.drawCircle(measuredWidth/2, measuredWidth/2, clickRadius, paint);
        }
    }

    public interface OnGestureListener {
        void onLongClick();
        void onClick();
        void onOver();
    }
}