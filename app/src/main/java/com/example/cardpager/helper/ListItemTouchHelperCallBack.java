package com.example.cardpager.helper;

import android.support.v7.widget.RecyclerView;

import com.example.cardpager.viewholder.ListItemViewHolder;

/**
 * Created by admin on 2017/3/23.
 * 菜单列表的触控事件回调
 */
public class ListItemTouchHelperCallBack implements ListItemTouchHelper.CallBack{

    @Override
    public void selectChange(RecyclerView parent, RecyclerView.ViewHolder old_holder, RecyclerView.ViewHolder holder) {
        if(old_holder != null && holder == null){
            ListItemViewHolder myViewHolder = (ListItemViewHolder) old_holder;
            myViewHolder.up();
        }else if(holder != null){
            int count = parent.getChildCount();
            for(int i = 0;i <count;i++){
                RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(parent.getChildAt(i));
                if(!holder.equals(viewHolder)){
                    ListItemViewHolder myViewHolder = (ListItemViewHolder) viewHolder;
                    myViewHolder.reset();
                }
            }
            ListItemViewHolder myViewHolder = (ListItemViewHolder) holder;
            myViewHolder.textView.setHovered(true);
        }
    }

    @Override
    public void scroll(RecyclerView.ViewHolder holder,float distanceX) {
        ListItemViewHolder myViewHolder = (ListItemViewHolder)holder;
        myViewHolder.scroll(distanceX);
    }

    @Override
    public void fing(RecyclerView.ViewHolder holder,float VelocityX) {
        ListItemViewHolder myViewHolder = (ListItemViewHolder)holder;
        myViewHolder.fing(VelocityX);
    }

    @Override
    public void singleTap(RecyclerView.ViewHolder holder, float x, float y) {
        ListItemViewHolder myViewHolder = (ListItemViewHolder)holder;
        myViewHolder.singleTap(x,y);
    }
}
