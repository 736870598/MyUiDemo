package com.sunxy.uitestdemo.praise;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.utils.SystemUtils;

/**
 * SunXiaoYu on 2019/1/2.
 * mail: sunxiaoyu@hexinpass.com
 */
public class PraiseView extends View {

    private int praiseNumber;
    private int textColor;

    private Bitmap selected;
    private Bitmap selectShining;
    private Bitmap unSelected;

    private Rect textRounds;
    private Paint bitmapPaint;
    private Paint textPaint;
    private Paint oldTextPaint;

    private boolean isPraise = false;

    private float handScale = 1;
    private float shiningScale = 1;
    private float textAlpha = 1;

    private int textMaxMove;
    private float textMoveDistance;

    private long duration = 300;

    public PraiseView(Context context) {
        this(context, null);
    }

    public PraiseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PraiseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PraiseView);
        praiseNumber = typedArray.getInteger(R.styleable.PraiseView_praise_number, 0);
        textColor = typedArray.getColor(R.styleable.PraiseView_praise_number_color,Color.GRAY);
        typedArray.recycle();
        init();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                praise();
            }
        });
    }

    private void init(){
        //创建文本显示范围
        textRounds = new Rect();
        //Paint.ANTI_ALIAS_FLAG 属性是位图抗锯齿
        //bitmapPaint是图像画笔
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //这是绘制原来数字的画笔 加入没点赞之前是45 那么点赞后就是46 点赞是46 那么没点赞就是45
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oldTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //文字颜色大小配置 颜色灰色 字体大小为14
        textPaint.setColor(textColor);
        textPaint.setTextSize(SystemUtils.sp2px(getContext(), 14));
        oldTextPaint.setColor(textColor);
        oldTextPaint.setTextSize(SystemUtils.sp2px(getContext(), 14));
        textMaxMove = SystemUtils.dp2px(getContext(), 20);
    }

    /**
     * 点赞 / 取消点赞
     */
    private void praise(){
        isPraise = !isPraise;
        if (isPraise){
            praiseNumber++;
            setPraiseAnimator();
        }else{
            praiseNumber--;
            setCancelPraiseAnimator();
        }
    }


    private void setPraiseAnimator(){
        ObjectAnimator handScaleAnim = ObjectAnimator.ofFloat(this, "handScale", 1f, 0.8f, 1f);
        handScaleAnim.setDuration(duration);
        ObjectAnimator shiningScaleAnim = ObjectAnimator.ofFloat(this, "shiningScale", 0f, 1f);
        shiningScaleAnim.setDuration(duration);
        ObjectAnimator textInAlphaAnim = ObjectAnimator.ofFloat(this, "textAlpha", 0f, 1f);
        textInAlphaAnim.setDuration(duration);
        ObjectAnimator textMoveAnim = ObjectAnimator.ofFloat(this, "textMoveDistance", textMaxMove, 0f);
        textMoveAnim.setDuration(duration);

        //动画集一起播放
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(handScaleAnim, shiningScaleAnim, textInAlphaAnim, textMoveAnim);
        animatorSet.start();
    }

    private void setCancelPraiseAnimator(){
        ObjectAnimator handScaleAnim = ObjectAnimator.ofFloat(this, "handScale", 1f, 0.8f, 1f);
        handScaleAnim.setDuration(duration);
        ObjectAnimator textInAlphaAnim = ObjectAnimator.ofFloat(this, "textAlpha", 0f, 1f);
        textInAlphaAnim.setDuration(duration);
        ObjectAnimator textMoveAnim = ObjectAnimator.ofFloat(this, "textMoveDistance", -textMaxMove, 0f);
        textMoveAnim.setDuration(duration);

        //动画集一起播放
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(handScaleAnim, handScaleAnim, textInAlphaAnim, textMoveAnim);
        animatorSet.start();
    }


    public void setHandScale(float handScale) {
        this.handScale = handScale;
    }

    public void setShiningScale(float shiningScale) {
        this.shiningScale = shiningScale;
    }

    public void setTextAlpha(float textAlpha) {
        this.textAlpha = textAlpha;
    }

    public void setTextMoveDistance(float textTranslate){
        this.textMoveDistance = textTranslate;
        invalidate();
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
        invalidate();
    }

    public void setPraiseNumber(int praiseNumber) {
        this.praiseNumber = praiseNumber;
        invalidate();
    }

    public int getPraiseNumber() {
        return praiseNumber;
    }

    /**
     * 在Activity resume的时候被调用的，Activity对应的window被添加的时候
     * 每个view只会调用一次，可以做一些初始化操作
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Resources resources = getResources();
        selected = BitmapFactory.decodeResource(resources, R.mipmap.ic_messages_like_selected);
        selectShining = BitmapFactory.decodeResource(resources, R.mipmap.ic_messages_like_selected_shining);
        unSelected = BitmapFactory.decodeResource(resources, R.mipmap.ic_messages_like_unselected);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (unSelected.isRecycled())
            unSelected.recycle();
        if (selectShining.isRecycled())
            selectShining.recycle();
        if (selected.isRecycled())
            selected.recycle();
    }

    //onMeasure 测量宽高
    // 高度：手的高度 + 20
    // 宽度：手的宽度 + 文字宽度 + 30
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(unSelected.getHeight() + SystemUtils.dp2px(getContext(),20), MeasureSpec.EXACTLY);
        String textNum = String.valueOf(praiseNumber);
        float measureText = textPaint.measureText(textNum, 0, textNum.length());
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (unSelected.getWidth() + measureText + SystemUtils.dp2px(getContext(), 30)), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Bitmap handBitmap = isPraise ? selected : unSelected;
        int height = getHeight();
        int centerY = height / 2;
        int handBitmapW = handBitmap.getWidth();
        int space = SystemUtils.dp2px(getContext(), 10);

        //画手：
        canvas.save();
        canvas.scale(handScale, handScale, handBitmapW / 2, centerY);
        canvas.drawBitmap(handBitmap, space, space, bitmapPaint);
        canvas.restore();

        //画手上点点
        if (isPraise){
            int shiningTop = space - selectShining.getHeight() + SystemUtils.dp2px(getContext(), 7);
            canvas.save();
            canvas.scale(shiningScale, shiningScale, handBitmapW / 2, space);
            canvas.drawBitmap(selectShining, SystemUtils.dp2px(getContext(), 12), shiningTop, bitmapPaint);
            canvas.restore();
        }

        //画文字：
        String textValue = String.valueOf(praiseNumber);
        String textCancelValue = String.valueOf(isPraise ? praiseNumber - 1 : praiseNumber + 1);
        textPaint.getTextBounds(textValue, 0, textValue.length(), textRounds);
        int textX = handBitmapW + SystemUtils.dp2px(getContext(), 20);
        int textY = height/2 - (textRounds.top + textRounds.bottom) / 2;

        canvas.save();
        if (isPraise){
            oldTextPaint.setAlpha((int) (255 * (1-textAlpha)));
            canvas.drawText(textCancelValue, textX, textY - textMaxMove + textMoveDistance, oldTextPaint);
            textPaint.setAlpha((int) (255 * textAlpha));
            canvas.drawText(textValue, textX, textY + textMoveDistance, textPaint);
        }else{
            oldTextPaint.setAlpha((int) (255 * (1-textAlpha)));
            canvas.drawText(textCancelValue, textX, textY + textMaxMove + textMoveDistance, oldTextPaint);
            textPaint.setAlpha((int) (255 * textAlpha));
            canvas.drawText(textValue, textX, textY + textMoveDistance, textPaint);
        }
        canvas.restore();

    }
}
