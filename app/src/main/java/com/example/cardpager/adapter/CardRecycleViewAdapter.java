package com.example.cardpager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

    public CardRecycleViewAdapter(@NonNull List<CardInfo> strList) {
        this.strList = strList;
        strList.clear();
        strList.add(new CardInfo());
        strList.add(new CardInfo());
    }

    @Override
    public int getItemCount() {
        return strList.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            return new CardViewHolder(new View(parent.getContext()),parent.getWidth()/2,parent.getHeight());
        }else{
            ImageView view = new ImageView(parent.getContext());
            return new CardViewHolder(view,parent.getWidth(),parent.getHeight());
        }
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        if(holder.getItemViewType() == 1) return;
        CardInfo info = strList.get(position);
        holder.img.setImageBitmap(info.bitmap);
        info.myHolder = holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 || position == getItemCount()-1){
            return 1;
        }else{
            return 2;
        }
    }
}
