package com.example.cardpager.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.cardpager.R;
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
        if(hidden){
            new AsyncTask<String,String,String>(){
                @Override
                protected String doInBackground(String... params) {//这里可能要经常调用，用AsyncTask对线程管理比较好
                    if(hidden){
                        Bitmap bitmap = info.bitmap;
                        info.bitmap = getBitmapCache();
                        EventBus.getDefault().post(new CardPagerEvent(info.UUID));
                    }
                    return null;
                }
            }.execute();
        }
        if(hidden){
            getView().setAnimation(AnimationUtils.loadAnimation(getView().getContext(), R.anim.in_fragment));
        }else{
            getView().setAnimation(AnimationUtils.loadAnimation(getView().getContext(), R.anim.out_fragment));
        }
        super.onHiddenChanged(hidden);
    }

    //提取当前页面截图
    private Bitmap getBitmapCache(){
        View v = getView();
        int width = v.getWidth();
        int height = v.getHeight();
        Bitmap cache = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(cache);
        v.draw(canvas);
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
