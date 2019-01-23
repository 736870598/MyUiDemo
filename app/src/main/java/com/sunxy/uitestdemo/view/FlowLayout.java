package com.sunxy.uitestdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义流式布局
 */

public class FlowLayout extends ViewGroup implements View.OnClickListener, View.OnLongClickListener{

    private List<List<View>> viewList;
    private List<Integer> heightList;
    private int measuredWidth;
    private int measuredHeight;

    private MarginLayoutParams layoutParams;
    private int textSize;
    private int textColor;
    private int itemBg;
    private int itemBgColor;
    private int paddingLeft, paddingTop, paddingRight, paddingBottom;

    private OnItemClickListener itemClickListener;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setItemTextSize(int textSize){
        this.textSize = textSize;
    }

    public void setItemTextColor(int textColor){
        this.textColor = textColor;
    }

    public void setItemMargin(int left, int top, int right, int bottom){
        layoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = left;
        layoutParams.topMargin = top;
        layoutParams.rightMargin = right;
        layoutParams.bottomMargin = bottom;
    }

    public void setItemBackGroundColor(int itemBgColor) {
        this.itemBgColor = itemBgColor;
    }

    public void setItemPadding(int left, int top, int right, int bottom){
        this.paddingLeft = left;
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
    }

    public void setItemBackgroundResource(int itemBackgroundResource){
        this.itemBg = itemBackgroundResource;
    }

    public void setList(List<String> list){
        for (String text : list){
            addItemView(text);
        }
    }

    public void addItemView(String textStr){

        TextView textView = new TextView(getContext());
        textView.setText(textStr);
        if (layoutParams == null){
            layoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        }
        textView.setLayoutParams(layoutParams);
        if (textSize != 0){
            textView.setTextSize(textSize);
        }
        if (textColor != 0){
            textView.setTextColor(textColor);
        }
        if (itemBgColor != 0){
            textView.setBackgroundColor(itemBgColor);
        }
        if (itemBg != 0){
            textView.setBackgroundResource(itemBg);
        }
        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        textView.setOnClickListener(this);
        textView.setOnLongClickListener(this);
        addView(textView);
    }

    public void removeItemView(int pos){
        removeViewAt(pos);
        invalidate();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(viewList != null){
            setMeasuredDimension(measuredWidth, measuredHeight);
            return ;
        }

        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

        measuredWidth = 0;
        measuredHeight = 0;

        int lineWidth = 0, lineHeight = 0;

        //需要测量
        viewList = new ArrayList<>();
        heightList = new ArrayList<>();
        List<View> itemList = new ArrayList<>();

        int childCount = getChildCount();
        for(int i=0; i<childCount; i++){
            View childView = getChildAt(i);
            childView.setTag(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            int childW = params.leftMargin + childView.getMeasuredWidth() + params.rightMargin;
            int childH = params.topMargin + childView.getMeasuredHeight() + params.bottomMargin;


            if(lineWidth + childW > widthMeasureSize){
                measuredWidth = Math.max(measuredWidth, lineWidth);
                measuredHeight += lineHeight;

                viewList.add(itemList);
                heightList.add(lineHeight);

                lineWidth = childW;
                lineHeight = childH;

                itemList = new ArrayList<>();
                itemList.add(childView);

            }else{
                lineWidth += childW;
                lineHeight = Math.max(lineHeight, childH);
                itemList.add(childView);
            }

            if(i == childCount - 1){
                if(itemList.size() > 0){

                    measuredWidth = Math.max(measuredWidth, lineWidth);
                    measuredHeight += lineHeight;

                    viewList.add(itemList);
                    heightList.add(lineHeight);
                }
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left, right, top, bottom;
        int lineTop = 0, lineLeft = 0;

        for (int i = 0; i < heightList.size(); i++){
            List<View> itemList = this.viewList.get(i);

            for (int j=0; j<itemList.size(); j++){
                View childView = itemList.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

                left = lineLeft + layoutParams.leftMargin;
                top = lineTop + layoutParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();

                childView.layout(left, top, right, bottom);

                lineLeft += layoutParams.leftMargin + childView.getMeasuredWidth() + layoutParams.rightMargin;
            }

            lineTop += heightList.get(i);
            lineLeft = 0;
        }

        viewList = null;
        heightList = null;

    }


    @Override
    public void onClick(View v) {
        if (itemClickListener != null && v instanceof TextView){
            TextView textView = (TextView) v;
            itemClickListener.onItemClick((Integer) textView.getTag(), textView);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (itemClickListener != null && v instanceof TextView){
            TextView textView = (TextView) v;
            itemClickListener.onItemLongClick((Integer) textView.getTag(), textView);
            return true;
        }
        return false;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position, TextView view);
        void onItemLongClick(int position, TextView view);
    }
}
