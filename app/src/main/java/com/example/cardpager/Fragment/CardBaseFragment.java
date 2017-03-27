package com.example.cardpager.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.cardpager.event.CardPagerEvent;
import com.example.cardpager.info.CardInfo;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

/**
 * Created by admin on 2017/3/23.
 * 卡片Fragment的基类
 */
public class CardBaseFragment extends Fragment{

    private CardInfo info;

    public void setCardInfo(CardInfo info){
        this.info = info;
    }

    public CardInfo getInfo(){
        return info;
    }

    @Override
    public void onHiddenChanged(final boolean hidden) {
        getView().setAnimation(getAnimation(hidden));
        if(hidden){//这里可能要经常调用，用AsyncTask对线程管理比较好
            info.bitmap = getBitmapCache();
            EventBus.getDefault().post(new CardPagerEvent(info.UUID));
        }
        super.onHiddenChanged(hidden);
    }

    public Animation getAnimation(boolean hidden){
        float pivotx;
        if(info.myHolder == null){
            pivotx = 1f;
        }else{
            pivotx = info.myHolder.itemView.getLeft()*2f/getView().getWidth();
        }
        Animation animation;
        if(hidden){
            animation = new ScaleAnimation(1f,0.5f,1f,0.5f,Animation.RELATIVE_TO_SELF,pivotx,Animation.RELATIVE_TO_SELF,0.5f);
        }else{
            animation = new ScaleAnimation(0.5f,1f,0.5f,1f,Animation.RELATIVE_TO_SELF,pivotx,Animation.RELATIVE_TO_SELF,0.5f);
        }
        animation.setDuration(300);
        return animation;
    }

    //提取当前页面截图
    private Bitmap getBitmapCache(){
        View v = getView();
        int width = v.getWidth();
        int height = v.getHeight();
        v.setDrawingCacheEnabled(true);
        Bitmap cache = v.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cache.compress(Bitmap.CompressFormat.JPEG,50,stream);
        byte[] bt = stream.toByteArray();
        cache.recycle();
        cache = BitmapFactory.decodeByteArray(bt,0,bt.length);
        cache = Bitmap.createScaledBitmap(cache,width/2,height/2,false);
        return cache;
    }

    //返回操作
    public boolean backPress(){
        return false;
    }
}
