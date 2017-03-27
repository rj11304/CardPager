package com.example.cardpager.helper;

import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.cardpager.drawable.MeshBitmapDrawable;
import com.example.cardpager.widget.MeshMenusView;

/**
 * Created by admin on 2017/3/21.
 * 扭曲菜单的事件协助者
 */
public class MeshMenuTouchHelper {

    private MeshMenusView meshMenusView;
    private boolean isDrag = false;
    private Rect mTouchFrame = new Rect();
    private CallBack callBack;
    private GestureDetector gestureDetector;


    public MeshMenuTouchHelper(MeshMenusView view){
        meshMenusView = view;
        gestureDetector = new GestureDetector(view.getContext(),listener);
        callBack = new SimpleCallBack();
    }

    public void setCallBack(CallBack callBack){
        if(callBack != null)
            this.callBack = callBack;
    }

    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                callBack.resetEvent();
                int x = (int)MotionEventCompat.getX(event,0);
                int y = (int)MotionEventCompat.getY(event,0);
                if(meshMenusView.getManager() == null) break;
                if(!meshMenusView.getManager().getorigMenusRect().contains(x,y)){//如果菜单在拖动的时候松手，默认为出发菜单事件
                    View v = findViewByXY(x,y);
                    if(v != null)
                        callBack.onItemSelected(v);
                }
                stopDrag();
        }
        return gestureDetector.onTouchEvent(event);
    }

    private boolean startDrag(float x,float y){//在菜单区域才能够拖动和点击出发菜单事件
        if(meshMenusView.getManager() == null){
            isDrag = false;
        }else{
            isDrag = meshMenusView.getManager().getorigMenusRect().contains((int)x,(int)y);
        }
        return isDrag;
    }

    public boolean isDrag(){
        return isDrag;
    }

    private boolean onDrag(float x,float y){
        if(!isDrag) return false;
        MeshMenusView.MeshLayoutManager manager = meshMenusView.getManager();
        if(manager == null) return false;
        MeshBitmapDrawable.MeshHelper helper = meshMenusView.getDrawable().getMeshHelper();
        int allLength = meshMenusView.getHeight();
        int offset = manager.getorigMenusRect().height()/2;
        float offsetY = allLength - y -offset;
        float offsetTranslationY = y - offset;
        helper.warp(x,offsetY);
        helper.setTanslation(0,offsetTranslationY);
        meshMenusView.invalidate();
        manager.onLayout(x,y);
        selected((int)x,(int)y);
        return false;
    }

    private boolean stopDrag(){
        isDrag = false;
        MeshBitmapDrawable.MeshHelper helper = meshMenusView.getDrawable().getMeshHelper();
        MeshMenusView.MeshLayoutManager manager = meshMenusView.getManager();
        helper.warp(0,0);
        if(manager == null) return false;
        helper.setTanslation(manager.getorigMenusRect().left,manager.getorigMenusRect().top);
        int offsett = manager.getorigMenusRect().height()/2;
        manager.onLayout(0,meshMenusView.getHeight() - offsett);
        clearView();
        meshMenusView.invalidate();
        return false;
    }

    private void selected(int x,int y){//根据坐标进行选中事件
        Rect frame = mTouchFrame;
        final int count = meshMenusView.getChildCount();
        for (int i = 0; i < count; i ++) {
            final View child = meshMenusView.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    child.setHovered(true);
                }else{
                    child.setHovered(false);
                }
            }
        }
    }

    private View findViewByXY(int x,int y){
        Rect frame = mTouchFrame;
        final int count = meshMenusView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = meshMenusView.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return child;
                }
            }
        }
        return null;
    }

    private void clearView(){
        final int count = meshMenusView.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = meshMenusView.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.setHovered(false);
            }
        }
    }

    private GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onDown(MotionEvent e) {
            return startDrag(MotionEventCompat.getX(e,0),MotionEventCompat.getY(e,0));
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            onDrag(MotionEventCompat.getX(e2,0),MotionEventCompat.getY(e2,0));
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View v = findViewByXY((int)MotionEventCompat.getX(e,0),(int)MotionEventCompat.getY(e,0));
            if(v != null)
                callBack.onItemLongClick(v);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View v = findViewByXY((int)MotionEventCompat.getX(e,0),(int)MotionEventCompat.getY(e,0));
            if(v != null)
                return callBack.onItemClick(v);
            else
                return false;
        }
    };

    //事件回调
    public interface CallBack{

        public boolean onItemClick(View view);

        public void onItemSelected(View view);

        public void onItemLongClick(View view);

        public void resetEvent();

    }

    public static class SimpleCallBack implements CallBack{
        @Override
        public boolean onItemClick(View view) {
            return  false;
        }

        @Override
        public void onItemSelected(View view) {}

        @Override
        public void onItemLongClick(View view) {}

        @Override
        public void resetEvent() {}
    }
}
