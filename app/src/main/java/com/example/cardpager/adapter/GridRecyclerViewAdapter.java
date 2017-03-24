package com.example.cardpager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardpager.R;
import com.example.cardpager.info.GridItemInfo;
import com.example.cardpager.viewholder.GridViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/23.
 */
public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridViewHolder>{

    private List<GridItemInfo> listInfos = new ArrayList<>();

    public GridRecyclerViewAdapter(List<GridItemInfo> list) {
        this.listInfos = list;
    }

    @Override
    public int getItemCount() {
        return listInfos.size();
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.grid_item_layout,null);
        int margin = 10;
        int width = parent.getResources().getDisplayMetrics().widthPixels/3 - 2 * margin;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(width,width);
        params.leftMargin = margin;
        params.rightMargin = margin;
        params.topMargin = margin;
        params.bottomMargin = margin;
        v.setLayoutParams(params);
        GridViewHolder holder = new GridViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        GridItemInfo info = listInfos.get(position);
        holder.textView.setText(info.text);
        holder.view.setBackgroundResource(info.icon);
    }
}
