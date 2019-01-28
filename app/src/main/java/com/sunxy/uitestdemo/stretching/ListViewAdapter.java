package com.sunxy.uitestdemo.stretching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/12/23.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> list = new ArrayList<>();

    public ListViewAdapter(Context context){
        this.context = context;
        list.add("1111111111111111111111111111");
        list.add("2222222222222222222222222222");
        list.add("3333333333333333333333333333");
        list.add("4444444444444444444444444444");
        list.add("5555555555555555555555555555");
        list.add("6666666666666666666666666666");
        list.add("7777777777777777777777777777");
        list.add("8888888888888888888888888888");
        list.add("9999999999999999999999999999");
        list.add("0000000000000000000000000000");
        list.add("1111111111111111111111111111");
        list.add("2222222222222222222222222222");
        list.add("3333333333333333333333333333");
        list.add("4444444444444444444444444444");
        list.add("5555555555555555555555555555");
        list.add("6666666666666666666666666666");
        list.add("7777777777777777777777777777");
        list.add("8888888888888888888888888888");
        list.add("9999999999999999999999999999");
        list.add("0000000000000000000000000000");
        list.add("2222222222222222222222222222");
        list.add("3333333333333333333333333333");
        list.add("4444444444444444444444444444");
        list.add("5555555555555555555555555555");
        list.add("6666666666666666666666666666");
        list.add("7777777777777777777777777777");
        list.add("8888888888888888888888888888");
        list.add("9999999999999999999999999999");
        list.add("0000000000000000000000000000");
        list.add("2222222222222222222222222222");
        list.add("3333333333333333333333333333");
        list.add("4444444444444444444444444444");
        list.add("5555555555555555555555555555");
        list.add("6666666666666666666666666666");
        list.add("7777777777777777777777777777");
        list.add("8888888888888888888888888888");
        list.add("9999999999999999999999999999");
        list.add("0000000000000000000000000000");
        list.add("2222222222222222222222222222");
        list.add("3333333333333333333333333333");
        list.add("4444444444444444444444444444");
        list.add("5555555555555555555555555555");
        list.add("6666666666666666666666666666");
        list.add("7777777777777777777777777777");
        list.add("8888888888888888888888888888");
        list.add("9999999999999999999999999999");
        list.add("0000000000000000000000000000");
        list.add("2222222222222222222222222222");
        list.add("3333333333333333333333333333");
        list.add("4444444444444444444444444444");
        list.add("5555555555555555555555555555");
        list.add("6666666666666666666666666666");
        list.add("7777777777777777777777777777");
        list.add("8888888888888888888888888888");
        list.add("9999999999999999999999999999");
        list.add("0000000000000000000000000000");
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = new TextView(parent.getContext());
        }
        ((TextView)convertView).setText(getItem(position));
        return convertView;
    }
}
