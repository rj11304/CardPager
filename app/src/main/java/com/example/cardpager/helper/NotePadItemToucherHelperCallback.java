package com.example.cardpager.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by admin on 2017/3/27.
 */
public class NotePadItemToucherHelperCallback extends ItemTouchHelper.Callback{
    private DragEvent mEvent;

    public NotePadItemToucherHelperCallback(DragEvent event){
        this.mEvent = event;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        int swipFlags;
        if(viewHolder.getAdapterPosition() == 0){
            dragFlags = 0;
            swipFlags = 0;
        }else{
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        }
        return makeMovementFlags(dragFlags,swipFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if(target.getAdapterPosition() == 0) return false;
        if(viewHolder.getItemViewType() != target.getItemViewType()) return false;
        mEvent.onItemSwap(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mEvent.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder instanceof OnViewHolderStatChanageListener){
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    OnViewHolderStatChanageListener listener = (OnViewHolderStatChanageListener)viewHolder;
                    listener.onItemSelectedSwiped();
                }else if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
                    OnViewHolderStatChanageListener listener = (OnViewHolderStatChanageListener)viewHolder;
                    listener.onItemSelectedDrag();
                }
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if(viewHolder instanceof OnViewHolderStatChanageListener){
            OnViewHolderStatChanageListener listener = (OnViewHolderStatChanageListener)viewHolder;
            listener.onItemNormal();
        }
    }
}
