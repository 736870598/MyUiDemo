package com.sunxy.uitestdemo.darg;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sunxy.uitestdemo.R;
import com.sunxy.uitestdemo.base.BaseActivity;
import com.sunxy.uitestdemo.utils.LinItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * 可拖拽的activity
 * SunXiaoYu on 2019/1/24.
 * mail: sunxiaoyu@hexinpass.com
 */
public class DragActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.recycle_view_layout;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DragAdapter adapter = new DragAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setList(createData());

        //添加分割线
        recyclerView.addItemDecoration(new LinItemDecoration(Color.RED, 5));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallBack(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private List<Integer> createData(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }
        return list;
    }


    public interface ItemStateCallBack{
        void onItemRemove(int pos);
        boolean onItemChange(int pos, int targetPos);
    }
}
