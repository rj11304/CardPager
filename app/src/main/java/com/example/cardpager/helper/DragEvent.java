package com.example.cardpager.helper;

/**
 * Created by admin on 2017/3/19.
 */
public interface DragEvent {

    public void onItemSwap(int fromPosition,int toPosition);//Item交换

    public void onItemRemove(int position);//Item移除
}
