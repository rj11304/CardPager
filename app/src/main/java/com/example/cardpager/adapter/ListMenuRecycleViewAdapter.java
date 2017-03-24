package com.example.cardpager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardpager.R;
import com.example.cardpager.viewholder.ListItemViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/3/23.
 */
public class ListMenuRecycleViewAdapter extends RecyclerView.Adapter<ListItemViewHolder>{

    private Context context;
    private List<String> listData;
    private ListItemViewHolder.OnItemClickListener listener;

    public ListMenuRecycleViewAdapter(Context context, List<String> list) {
        this.listData = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemViewHolder holder = new ListItemViewHolder(View.inflate(context, R.layout.scroll_list_item,null));
        holder.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        String str = listData.get(position);
        holder.textView.setText(str);
        holder.reset();
    }

    public void setOnItemClickListener(ListItemViewHolder.OnItemClickListener listener){
        this.listener = listener;
    }


}
