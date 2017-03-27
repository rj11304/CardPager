package com.example.cardpager.viewholder;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.cardpager.R;
import com.example.cardpager.helper.OnViewHolderStatChanageListener;

/**
 * Created by admin on 2017/3/23.
 */
public class GridViewHolder extends RecyclerView.ViewHolder implements OnViewHolderStatChanageListener {

    public TextView textView;
    public View view;

    public GridViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text);
        view = itemView.findViewById(R.id.item_bg);
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
    public void onItemSelectedSwiped() {

    }
}
