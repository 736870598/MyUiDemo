package com.sunxy.uitestdemo.darg;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by sunxiaoyu on 2017/6/28.
 */
public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private DragActivity.ItemStateCallBack stateCallBack;

    public ItemTouchHelperCallBack(DragActivity.ItemStateCallBack stateCallBack){
        this.stateCallBack = stateCallBack;
    }


    //判断是否需要监听动作
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        //监听上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    //上下移动item
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if(stateCallBack != null){
            return stateCallBack.onItemChange(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return false;
    }

    //左右移动item    右->左  4    左->右  8
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(stateCallBack != null){
            stateCallBack.onItemRemove(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        //return true 表示允许长按拖赘
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            float alpha = 1 - (Math.abs(dX) / viewHolder.itemView.getWidth());
            if(alpha <= 0){
                alpha = 1;
            }
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}