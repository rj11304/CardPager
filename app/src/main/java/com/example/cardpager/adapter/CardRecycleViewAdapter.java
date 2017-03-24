package com.example.cardpager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cardpager.info.CardInfo;
import com.example.cardpager.viewholder.CardViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/3/23.
 */
public class CardRecycleViewAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<CardInfo> strList;

    public CardRecycleViewAdapter(List<CardInfo> strList) {
        this.strList = strList;
    }

    @Override
    public int getItemCount() {
        return strList.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView view = new ImageView(parent.getContext());
        return new CardViewHolder(view,parent.getWidth(),parent.getHeight());
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        CardInfo info = strList.get(position);
        holder.img.setImageBitmap(info.bitmap);
    }
}
