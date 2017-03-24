package com.example.cardpager.viewholder;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.cardpager.helper.OnViewHolderStatChanageListener;

/**
 * Created by admin on 2017/3/23.
 */
public class CardViewHolder extends RecyclerView.ViewHolder implements OnViewHolderStatChanageListener {

    public ImageView img;

    public CardViewHolder(View itemView, int parentWidth, int parentHeight) {
        super(itemView);
        img = (ImageView) itemView;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(parentWidth/2,parentHeight/2);
        params.leftMargin = parentWidth/40;
        params.rightMargin = parentWidth/40;
        params.topMargin = parentHeight/4;
        params.bottomMargin = parentHeight/4;
        itemView.setLayoutParams(params);
    }

    @Override
    public void onItemNormal() {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("ScaleX",itemView.getScaleX(),1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("ScaleY",itemView.getScaleY(),1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(itemView,holder1,holder2);
        objectAnimator.setDuration(200);
        objectAnimator.start();
    }

    @Override
    public void onItemSelectedDrag() {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("ScaleX",1f,1.1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("ScaleY",1f,1.1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(itemView,holder1,holder2);
        objectAnimator.setDuration(200);
        objectAnimator.start();
    }

    @Override
    public void onItemSelectedSwiped() {}
}
