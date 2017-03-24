package com.example.cardpager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cardpager.R;
import com.example.cardpager.drawable.MeshBitmapDrawable;
import com.example.cardpager.helper.MeshMenuTouchHelper;
import com.example.cardpager.info.MenuInfo;

import java.util.List;

/**
 * Created by admin on 2017/3/21.
 */
public class MeshMenusView extends LinearLayout{

    private MeshBitmapDrawable drawable = null;
    private MeshLayoutManager manager;//布局管理器
    private MeshMenuTouchHelper helper;//事件处理协助者

    public MeshMenusView(Context context) {
        super(context);
        init();
    }

    public MeshMenusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MeshMenusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MeshMenusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        setWillNotDraw(false);//设置调用绘画
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.BOTTOM);
        helper = new MeshMenuTouchHelper(this);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(manager != null) manager.onGlobalLayout();
            }
        });
    }

    public void setMenuInfos(List<MenuInfo> menus){
        if(menus == null) return;
        removeAllViews();
        int count = menus.size();
        for(int i = 0; i < count;i ++){
            MenuInfo info = menus.get(i);
            ImageView img = new ImageView(getContext());
            img.setImageResource(info.icon);
            img.setBackgroundResource(R.drawable.test_selector);
            int height = getResources().getDisplayMetrics().widthPixels/count-20;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(height,height);
            params.weight = 1;
            params.bottomMargin = 10;
            params.topMargin = 10;
            params.leftMargin = 10;
            params.rightMargin = 10;
            img.setTag(info);
            addView(img,params);
        }
    }

    public MeshLayoutManager getManager(){
        return manager;
    }

    public void setManager(MeshLayoutManager manager){
        this.manager = manager;
        this.manager.init(this);
    }

    public MeshMenuTouchHelper getTouchHelper(){
        return helper;
    }

    public void setMeshDrawable(MeshBitmapDrawable drawable){
        this.drawable = drawable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return helper.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(drawable !=null){
            drawable.draw(canvas);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public MeshBitmapDrawable getDrawable(){
        return drawable;
    }

    public interface MeshLayoutManager{

        public void init(MeshMenusView view);

        public void onLayout(float x,float y);

        public void onGlobalLayout();

        public Rect getorigMenusRect();

    }
}
