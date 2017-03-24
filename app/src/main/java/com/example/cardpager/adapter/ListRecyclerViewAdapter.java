package com.example.cardpager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardpager.R;
import com.example.cardpager.viewholder.ListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/23.
 */
public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListViewHolder>{

    private List<String> strList = new ArrayList<>();


    public ListRecyclerViewAdapter(List<String> list) {
        this.strList = list;
    }

    @Override
    public int getItemCount() {
        return strList.size();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_item_layout,null);
        ListViewHolder holder = new ListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String string = strList.get(position);
        holder.textView.setText(string);
    }
}
