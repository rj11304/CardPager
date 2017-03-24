package com.example.cardpager.viewholder;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.cardpager.R;
import com.example.cardpager.anamitor.ScrollerAnimator;

/**
 * Created by admin on 2017/3/23.
 */
public class ListItemViewHolder extends RecyclerView.ViewHolder{

    public TextView textView;
    public TextView cancel;
    public TextView delete;
    private Scroller scroller;
    private Rect rect = new Rect();
    private ScrollerAnimator animator;
    private OnItemClickListener itemClickListener;

    public ListItemViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text);
        cancel = (TextView) itemView.findViewById(R.id.cancel);
        delete = (TextView)itemView.findViewById(R.id.delete);
        scroller = new Scroller(itemView.getContext());
        animator = new ScrollerAnimator(textView,scroller);
    }

    public void scroll(float distans){
        int x = (int)textView.getTranslationX();
        textView.setTranslationX(x - distans);
    }

    public void fing(float VelocityX){
        int x = (int)textView.getTranslationX();
        int width = textView.getWidth();
        scroller.fling(x,0,(int)VelocityX,0,-width,width,0,0);
        animator.startAnimator();
    }

    public void singleTap(float x,float y){
        Rect frame = this.rect;
        View[] views = new View[3];
        views[0] = textView;
        views[1] = cancel;
        views[2] = delete;
        Rect itemViewRect = new Rect();
        itemView.getHitRect(itemViewRect);
        x = x - itemViewRect.left;
        y = y - itemViewRect.top;
        for(int i = 0; i < 3;i ++){
            View child = views[i];
            child.getHitRect(frame);
            if (frame.contains((int)x, (int)y)) {
                if(this.itemClickListener != null)
                    this.itemClickListener.onItemClickListener(this,child);
                return;
            }
        }
    }

    public void reset(){
        textView.setTranslationX(0);
    }

    public void up(){
        float startX = textView.getTranslationX();
        int width = textView.getWidth();
        if(Math.abs(startX) < width * 0.5){
            reset();
        }else{
            int signum = (int) Math.signum(startX);
            int VelocityX = (int)signum * 20000;
            scroller.fling((int)startX,0,VelocityX,0,-width,width,0,0);
            animator.startAnimator();
        }
        textView.setHovered(false);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener{

        public void onItemClickListener(RecyclerView.ViewHolder holder,View view);

    }
}
