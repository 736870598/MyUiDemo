package com.sunxy.uitestdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sunxy.uitestdemo.R;

/**
 * -
 * <p>
 * Created by Sunxy on 2018/12/1.
 */
public class MyEditText extends AppCompatEditText {

    private Drawable ic_right;
    private Drawable ic_show;
    private Drawable ic_hide;
    private boolean noFocusShowIcon;
    private boolean isInputPass;
    private boolean isHide = true;
    private boolean lastShow;

    public MyEditText(Context context) {
        super(context);
        init(null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    /**
     * 步骤1：初始化属性
     */
    private void init(AttributeSet attrs) {

        int showId, hideId = 0;
        if (attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyEditText);
            showId = typedArray.getResourceId(R.styleable.MyEditText_show_icon, R.mipmap.cancel);
            hideId = typedArray.getResourceId(R.styleable.MyEditText_hide_icon, 0);
            noFocusShowIcon = typedArray.getBoolean(R.styleable.MyEditText_no_focus_show_icon, false);
            typedArray.recycle();
        }else{
            showId = R.mipmap.cancel;
        }

        ic_show = getResources().getDrawable(showId);
        ic_show.setBounds(0, 0, ic_show.getIntrinsicWidth(), ic_show.getIntrinsicHeight());

        if (hideId != 0){
            isInputPass = true;
            ic_hide = getResources().getDrawable(hideId);
            ic_hide.setBounds(0, 0, ic_hide.getIntrinsicWidth(), ic_hide.getIntrinsicHeight());
            ic_right = ic_hide;
        }else{
            isInputPass = false;
            ic_right = ic_show;
        }

        if (isInputPass){
            setType();
        }

        if (noFocusShowIcon){
            setDeleteIconVisible(true);
        }

    }

    private void setType(){
        if (isInputPass){
            if (isHide){
                setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }else{
                setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            Selection.setSelection(getText(), getText().length());
        }
    }

    /**
     * 复写EditText本身的方法：onTextChanged（）
     * 调用时刻：当输入框内容变化时
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setDeleteIconVisible(hasFocus() && text.length() > 0);
    }

    /**
     * 复写EditText本身的方法：onFocusChanged（）
     * 调用时刻：焦点发生变化时
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setDeleteIconVisible(focused && length() > 0);
    }


    /**
     * 作用：对删除图标区域设置为"点击 即 清空搜索框内容"
     * 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
        switch (event.getAction()) {
            // 判断动作 = 手指抬起时
            case MotionEvent.ACTION_UP:
                Drawable drawable =  ic_right;
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    if (!isInputPass){
                        //删除
                        setText("");
                    }else{
                        //密码
                        ic_right = (isHide = !isHide) ? ic_hide : ic_show;
                        lastShow = false;
                        setType();
                        setDeleteIconVisible(true);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     */
    private void setDeleteIconVisible(boolean deleteVisible) {
        if(noFocusShowIcon){
            if (!lastShow){
                lastShow = true;
                setCompoundDrawables(null, null, ic_right, null);
                invalidate();
            }
        }else{
            if (deleteVisible != lastShow){
                lastShow = !lastShow;
                setCompoundDrawables(null, null, deleteVisible ?  ic_right: null, null);
                invalidate();
            }
        }
    }

}
