package com.example.cardpager.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by admin on 2017/3/21.
 * 绘制扭曲图片类
 */
public class MeshBitmapDrawable extends Drawable{

    private MeshHelper helper;
    private Bitmap bitmap;
    private Paint paint;

    public MeshBitmapDrawable(@NonNull Bitmap bitmap, @NonNull MeshHelper helper){
        this.bitmap = bitmap;
        this.helper = helper;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public MeshHelper getMeshHelper(){
        return helper;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(helper.getTanslationX(),helper.gettTanslationY());
        canvas.drawBitmapMesh(bitmap,helper.getMeshWidth(),helper.getMeshHeight(),helper.getMeshVerts(),helper.getVertOffset(),helper.getColors(),helper.getColorOffset(),paint);
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }

    public interface MeshHelper{

        public int getMeshWidth();

        public int getMeshHeight();

        public float[] getMeshVerts();

        public void warp(float ...values);

        public int getVertOffset();

        public void restore();

        public int[] getColors();

        public int getColorOffset();

        public float getTanslationX();

        public float gettTanslationY();

        public void setTanslation(float tanslationX,float tanslationY);

        public float getTackPositionY(float x,float positionX,int row,float smalltall);

    }
}
