package com.example.cardpager.helper;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by admin on 2017/3/22.
 * 包含菜单列表的触控协助者类
 */
public class ListItemTouchHelper implements RecyclerView.OnItemTouchListener{

    private CallBack callBack;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    private int touchSlop;
    private RecyclerView.ViewHolder Selected;
    private RecyclerView.ViewHolder PreSelected;

    private float origPointX;
    private float origPointY;
    private float xyProportion;

    private int ActivityPointerID = -1;

    public ListItemTouchHelper(@NonNull CallBack callBack){
        this.callBack = callBack;
    }

    public void attRecyclerView(@NonNull RecyclerView recyclerView){//绑定RecylerView
        if (this.recyclerView != null && recyclerView.equals(this.recyclerView)) {
            return;
        }else if(this.recyclerView != null && !recyclerView.equals(this.recyclerView)){
            destory();
        }
        this.recyclerView = recyclerView;
        this.recyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetector(this.recyclerView.getContext(),listener);
        touchSlop = ViewConfiguration.get(recyclerView.getContext()).getScaledTouchSlop();
        DisplayMetrics d = recyclerView.getContext().getResources().getDisplayMetrics();
        xyProportion = d.widthPixels * 1.0f/d.heightPixels;
    }

    private void destory(){//销毁旧的事件监听
        this.recyclerView.removeOnItemTouchListener(this);
        this.recyclerView = null;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);;
        int action = e.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            ActivityPointerID = MotionEventCompat.getPointerId(e,0);
            origPointX = MotionEventCompat.getX(e,0);
            origPointY = MotionEventCompat.getY(e,0);
        }else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            selected(null);
            ActivityPointerID = -1;
        }else if(action == MotionEvent.ACTION_MOVE){
            canSelect(e);
        }
        return Selected != null;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        if(e.getAction() == MotionEvent.ACTION_UP){
            selected(null);
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}


    //判断Item的选中事件
    private boolean canSelect(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);
        if(action != MotionEvent.ACTION_MOVE) return false;
        if(recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) return false;
        RecyclerView.ViewHolder holder = findViewHolder(event);
        if(holder == null) return false;
        int index = MotionEventCompat.findPointerIndex(event,ActivityPointerID);
        float x = MotionEventCompat.getX(event,index);
        float y = MotionEventCompat.getY(event,index);
        float dx = x - origPointX;
        float dy = y - origPointX;
        float absdx = Math.abs(dx);
        float absdy = Math.abs(dy);
        if(absdx < touchSlop && absdy < touchSlop) return false;
        if(absdx < absdy * xyProportion) return false;
        ActivityPointerID = MotionEventCompat.getPointerId(event,0);
        selected(holder);
        return true;
    }

    private void selected(RecyclerView.ViewHolder holder){
        callBack.selectChange(recyclerView,Selected,holder);
        Selected = holder;
    }

    private GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int x = (int)MotionEventCompat.getX(e,0);
            int y = (int)MotionEventCompat.getY(e,0);
            RecyclerView.ViewHolder holder = findViewHolder(e);
            callBack.singleTap(holder,x,y);
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Selected != null)
                callBack.scroll(Selected,distanceX);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(Selected != null)
                callBack.fing(Selected,velocityX);
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    //根据事件获取事件下的ViewHolder对象
    private RecyclerView.ViewHolder findViewHolder(MotionEvent event){
        float x = MotionEventCompat.getX(event,0);
        float y = MotionEventCompat.getY(event,0);
        View view = recyclerView.findChildViewUnder(x,y);
        if(view == null) return null;
        return recyclerView.getChildViewHolder(view);
    }

    //事件回调接口
    public interface CallBack{

        public void selectChange(RecyclerView parent,RecyclerView.ViewHolder old_holder,RecyclerView.ViewHolder holder);

        public void scroll(RecyclerView.ViewHolder holder,float distanceX);

        public void fing(RecyclerView.ViewHolder holder,float VelocityX);

        public void singleTap(RecyclerView.ViewHolder holder,float x,float y);

    }
}
