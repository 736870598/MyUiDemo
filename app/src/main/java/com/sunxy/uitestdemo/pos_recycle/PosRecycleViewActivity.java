package com.sunxy.uitestdemo.pos_recycle;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;

/**
 * SunXiaoYu on 2019/1/23.
 * mail: sunxiaoyu@hexinpass.com
 */
public class PosRecycleViewActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.pos_recycle_view_layout;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView1 = findViewById(R.id.recyclerView1);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(new ItemAdapter());
        //滑动停止是显示item居中一样滑动
        new LinearSnapHelper().attachToRecyclerView(recyclerView1);
        recyclerView1.scrollToPosition(10);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(new ItemAdapter());
        //像pageView一样滑动
        new PagerSnapHelper().attachToRecyclerView(recyclerView2);
    }


    class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_item_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            itemViewHolder.bindView(i);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        public void bindView(int position){
            textView.setText("-- 哈哈哈 -- " + position);
        }
    }
}
