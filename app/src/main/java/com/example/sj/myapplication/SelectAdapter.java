package com.example.sj.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SelectAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;

    public SelectAdapter(Context context, List<String> data){
        this.mContext = context;
        mData = data;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view != null){
            holder = (ViewHolder) view.getTag();
            TextView name = holder.name;
            name.setText(mData.get(i));

        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.name);
            holder.name.setText(mData.get(i));
            view.setTag(holder);
        }
        return view;
    }


    class ViewHolder{
        TextView name;
    }
}
