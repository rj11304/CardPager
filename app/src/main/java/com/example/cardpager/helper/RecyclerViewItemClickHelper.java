package com.example.cardpager.helper;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by admin on 2017/3/22.
 * RecylerView中的Item点击事件协助者
 */
public class RecyclerViewItemClickHelper implements RecyclerView.OnItemTouchListener{
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;
    private CallBack callBack;

    public RecyclerViewItemClickHelper(CallBack callBack){
        this.callBack = callBack;
    }

    public void attachToRecyclerView(RecyclerView recyclerView){
        if(recyclerView != null && this.recyclerView != null && recyclerView.equals(this.recyclerView)){
            return;
        }else if(this.recyclerView != null){
            destory();
        }
        this.recyclerView = recyclerView;
        attach();
    }

    private void attach(){
        gestureDetector = new GestureDetector(recyclerView.getContext(),listener);
        recyclerView.addOnItemTouchListener(this);
    }

    private void destory(){
        recyclerView.removeOnItemTouchListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if(callBack != null){
                RecyclerView.ViewHolder holder = findViewHolder(e);
                if(holder != null)
                    callBack.onItemClick(recyclerView,holder,holder.getAdapterPosition());
            }
            return super.onSingleTapUp(e);
        }
    };

    private RecyclerView.ViewHolder findViewHolder(MotionEvent event){
        float x = MotionEventCompat.getX(event,0);
        float y = MotionEventCompat.getY(event,0);
        View view = recyclerView.findChildViewUnder(x,y);
        if(view == null) return null;
        return recyclerView.getChildViewHolder(view);
    }

    public interface CallBack{
        public void onItemClick(RecyclerView parent, RecyclerView.ViewHolder viewHolder,int position);
    }
}
