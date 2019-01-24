package com.sunxy.uitestdemo.darg;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseAdapter;

import java.util.Collections;

/**
 * SunXiaoYu on 2019/1/24.
 * mail: sunxiaoyu@hexinpass.com
 */
public class DragAdapter extends BaseAdapter<Integer> implements DragActivity.ItemStateCallBack {

    private int[] drawableArray = {R.mipmap.item_1,R.mipmap.item_2,R.mipmap.item_3};

    @Override
    protected int getItemLayoutId(int type) {
        return R.layout.drag_item_layout;
    }

    @Override
    protected void bindView(RViewHolder viewHolder, int position, Integer model) {
        viewHolder.setImageSrc(R.id.item_imageview, drawableArray[model % 3]);
        viewHolder.setText(R.id.item_textview, "content: " + model + " , position：" + position);
    }

    @Override
    public void onItemRemove(int pos) {
        getList().remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public boolean onItemChange(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
}
