package com.example.cardpager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.cardpager.R;

/**
 * Created by admin on 2017/3/23.
 */
public class ListViewHolder extends RecyclerView.ViewHolder{
    public TextView textView;

    public ListViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.text);
    }
}
