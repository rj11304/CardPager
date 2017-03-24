package com.example.cardpager.manager;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.cardpager.drawable.MeshBitmapDrawable;
import com.example.cardpager.helper.SimpleMeshHelper;
import com.example.cardpager.widget.MeshMenusView;

/**
 * Created by admin on 2017/3/21.
 * 扭曲菜单的布局管理器
 */
public class SimpleMeshLayoutManager implements MeshMenusView.MeshLayoutManager{

    private MeshMenusView meshMenusView;
    private Rect origMenusRect = new Rect();//菜单原始显示区域

    @Override
    public void init(MeshMenusView view) {
        this.meshMenusView = view;
    }

    @Override
    public void onLayout(float x, float y) {//重新布控子菜单的位置
        int count = meshMenusView.getChildCount();
        float offset = meshMenusView.getHeight()-y;
        for(int i = 0;i < count;i++){
            View child = meshMenusView.getChildAt(i);
            float centerX = (child.getRight()+child.getLeft())/2;
            float centerY = (child.getBottom()-child.getTop())/2;
            float calculateY = calculate(x,centerX,y);
            float tanslationY = calculateY - centerY - offset;
            child.setTranslationY(tanslationY);
        }
    }

    public float calculate(float x,float positionX,float y){//进行轨迹运算
        MeshBitmapDrawable drawable = meshMenusView.getDrawable();
        if(drawable == null) return 0;
        int height = meshMenusView.getHeight();
        if(height <= 0) return 0;
        float offset = origMenusRect.height()/2;
        float t = height - y - offset;
        return drawable.getMeshHelper().getTackPositionY(x,positionX,Integer.MAX_VALUE,t);
    }

    @Override
    public void onGlobalLayout() {//布局空间分配完毕后初始化菜单背景
        if(meshMenusView.getDrawable() == null){
            int width = meshMenusView.getWidth();
            int height = width/meshMenusView.getChildCount();
            origMenusRect.set(0,meshMenusView.getHeight()-height,width,meshMenusView.getHeight());
            Bitmap bitmap = getBitmap(width,height);
            SimpleMeshHelper helper = new SimpleMeshHelper(width,height);
            float maxGap = height;
            float minGap = height/2;
            helper.setGap(maxGap,minGap);
            helper.setTanslation(origMenusRect.left,origMenusRect.top);
            meshMenusView.setMeshDrawable(new MeshBitmapDrawable(bitmap,helper));
            meshMenusView.invalidate();
        }
    }

    @Override
    public Rect getorigMenusRect() {
        return origMenusRect;
    }

    private Bitmap getBitmap(int width, int height){//获取背景图片Bitmap
        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#86000000"));
        paint.setStyle(Paint.Style.FILL);
        BlurMaskFilter filter = new BlurMaskFilter(30f,BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(filter);
        Rect rect = new Rect(-30,0,width+30,height);
        c.drawRect(rect,paint);
        return bitmap;
    }
}
