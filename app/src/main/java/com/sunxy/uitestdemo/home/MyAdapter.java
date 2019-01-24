package com.sunxy.uitestdemo.home;

import android.content.Intent;
import android.view.View;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseAdapter;

/**
 * SunXiaoYu on 2019/1/23.
 * mail: sunxiaoyu@hexinpass.com
 */
public class MyAdapter extends BaseAdapter<UiModel> {

    @Override
    protected int getItemLayoutId(int type) {
        return R.layout.adapter_item_layout;
    }

    @Override
    protected void bindView(RViewHolder viewHolder, int position, final UiModel model) {

        viewHolder.setText(R.id.textView, model.getTitle());
        viewHolder.setItemOnClick(viewHolder.itemView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), model.getClazz());
                intent.putExtra("model", model);
                v.getContext().startActivity(intent);
            }
        });

    }
}
