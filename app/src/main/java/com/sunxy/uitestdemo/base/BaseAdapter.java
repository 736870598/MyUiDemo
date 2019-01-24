package com.sunxy.uitestdemo.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * RecyclerView Adapter Helper
 * SunXiaoYu on 2018/12/3.
 * mail: sunxiaoyu@hexinpass.com
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.RViewHolder> {

    protected List<T> list;

    public void setList(List<T> list){
        this.list = list;
        notifyDataSetChanged();
        dataChange();
    }

    public void addList(List<T> addList){
        if (list != null){
            if (addList != null){
                int position = list.size();
                list.addAll(addList);
                notifyItemInserted(position);
            }
            dataChange();
        }else{
            setList(addList);
        }
    }

    public void delItem(int index){
        if (list != null && list.size() > index){
            list.remove(index);
            notifyDataSetChanged();
        }
        dataChange();
    }

    public List<T> getList() {
        return list;
    }

    public void clear(){
        if (list != null){
            list.clear();
            notifyDataSetChanged();
        }
        dataChange();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return createRViewHolder(viewGroup, i);
    }


    @Override
    public void onBindViewHolder(@NonNull RViewHolder baseViewHolder, int i) {
        bindView(baseViewHolder, i, list.get(i));
    }

    protected View createView(ViewGroup parent, int id){
        return LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
    }

    private RViewHolder createRViewHolder(ViewGroup viewGroup, int i){
        return new RViewHolder(createView(viewGroup, getItemLayoutId(getItemViewType(i))));
    }

    public void dataChange(){ }

    protected abstract int getItemLayoutId(int type);
    protected abstract void bindView(RViewHolder viewHolder, final int position, final T model);

    public static class RViewHolder extends RecyclerView.ViewHolder{

        private HashMap<Integer, View> map;

        public RViewHolder(View itemView) {
            super(itemView);
            map = new HashMap<>();
        }

        public <T extends View> T getView(int id){
            View view = map.get(id);
            if (view == null){
                view = itemView.findViewById(id);
                map.put(id, view);
            }
            return (T) view;
        }

        public void setImageSrc(int viewId, int imageId){
            ImageView iv = getView(viewId);
            iv.setImageResource(imageId);
        }

        public void setText(int viewId, int stringId){
            setText(viewId, itemView.getResources().getString(stringId));
        }

        public void setText(int viewId, String text){
            TextView tv = getView(viewId);
            tv.setText(text);
        }

        public void setTextColor(int viewId, int colorId){
            TextView tv = getView(viewId);
            tv.setTextColor(itemView.getResources().getColor(colorId));
        }


        public void setItemOnClick(int ViewId, View.OnClickListener listener){
            setItemOnClick(getView(ViewId), listener);
        }

        public void setItemOnClick(View view, View.OnClickListener listener){
            view.setOnClickListener(listener);
        }

        public void setItemOnLongClick(int ViewId, View.OnLongClickListener listener){
            setItemOnLongClick(getView(ViewId), listener);
        }

        public void setItemOnLongClick(View view, View.OnLongClickListener listener){
            view.setOnLongClickListener(listener);
        }

        public void setBackgroundResource(int viewId, int resourceId){
            getView(viewId).setBackgroundResource(resourceId);
        }

        public void setBackgroundColor(int viewId, int setBackgroundColor){
            getView(viewId).setBackgroundColor(itemView.getResources().getColor(setBackgroundColor));
        }

        public void setVisibility(int viewId, int visibility){
            getView(viewId).setVisibility(visibility);
        }

    }
}
