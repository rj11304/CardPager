package com.example.cardpager.anamitor;

import android.view.View;
import android.widget.Scroller;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 2017/3/22.
 * 滚动动画
 */
public class ScrollerAnimator implements Runnable{

    private WeakReference<View> mTag;
    private boolean isCancel;
    private boolean isRuning;
    private Scroller mScroller;

    public ScrollerAnimator(View view, Scroller scroller){
        this.mTag = new WeakReference<View>(view);
        this.mScroller = scroller;
        isCancel = false;
        isRuning = false;
    }

    public void startAnimator(){
        if(!isRuning){
            if(mTag == null){
                return;
            }
            View view = mTag.get();
            if(view == null){
                return ;
            }
            view.post(this);
        }
    }

    public void cancel(){
        isCancel = true;
    }

    @Override
    public void run() {
        isRuning = true;
        if(isCancel) {
            end();
            return;
        }
        if(mScroller.computeScrollOffset()){
            float x = mScroller.getCurrX();
            if(mTag == null){
                end();
                return;
            }
            View view = mTag.get();
            if(view == null){
                end();
                return ;
            }
            view.setTranslationX(x);
            view.post(this);
        }else{
            preEnd();
            end();
        }
    }

    private void preEnd(){//这个类的败笔，应该设置回调比较好（好像效果不太理想）
        if(mTag == null){
            end();
            return;
        }
        View view = mTag.get();
        if(view == null){
            end();
            return ;
        }
        float x  = view.getTranslationX();
        int width = view.getWidth();
        int signum = (int) Math.signum(x);
        float absX = Math.abs(x);
        if(absX > 0.5 * width){
            view.setTranslationX(signum * width);
        }else{
            view.setTranslationX(0);
        }
    }

    private void end(){
        isRuning = false;
        isCancel = false;
    }
}
