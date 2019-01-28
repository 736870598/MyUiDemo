package com.sunxy.uitestdemo.bounce;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunxy.uitestdemo.R;

/**
 * Created by sunxiaoyu on 2017/1/9.
 */

public class BounceViewAdapter extends RecyclerView.Adapter<BounceViewAdapter.MyViewHolder> {

    private int length = 0;

    public BounceViewAdapter(int length) {
        this.length = length;
    }

    @Override
    public int getItemCount() {
        return this.length;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bounce_item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText("这是第 " + position + "项");

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.bounce_item_tv);
        }
    }

}
