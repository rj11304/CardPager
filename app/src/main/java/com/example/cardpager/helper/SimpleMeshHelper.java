package com.example.cardpager.helper;

import com.example.cardpager.drawable.MeshBitmapDrawable;

/**
 * Created by admin on 2017/3/21.
 * 扭曲图片绘画类的绘画协助者
 */
public class SimpleMeshHelper implements MeshBitmapDrawable.MeshHelper{

    private final int MESHWIDTH = 50;
    private final int MESHHEIGHT = 10;
    private float minGap = 40;
    private float maxGap = 80;
    private final int COUNT = (MESHWIDTH+1) * (MESHHEIGHT+1);
    private float[] verts = new float[COUNT * 2];
    private float[] orig = new float[COUNT * 2];
    private float tanslationX;
    private float tanslationY;

    private int bitmapWidth = 1;

    public SimpleMeshHelper(int bitmapWidth,int bitmapHeight){
        if(bitmapHeight > 0)
            this.bitmapWidth = bitmapWidth;
        int index = 0;
        for(int i = 0;i <= MESHHEIGHT;i++){//初始化坐标数组
            float fy = bitmapHeight * i / MESHHEIGHT;
            for(int j = 0;j <= MESHWIDTH;j++){
                float fx = bitmapWidth * j / MESHWIDTH;
                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index ++;
            }
        }
    }

    public void setGap(float maxGap,float minGap){//设置两条曲线的最大和最小差
        this.maxGap = maxGap;
        this.minGap = minGap;
    }

    @Override
    public int getColorOffset() {
        return 0;
    }

    @Override
    public int getMeshWidth() {
        return MESHWIDTH;
    }

    @Override
    public int getMeshHeight() {
        return MESHHEIGHT;
    }

    @Override
    public float[] getMeshVerts() {
        return verts;
    }

    @Override
    public void restore() {
        System.arraycopy(orig,0,verts,0,verts.length);
    }

    @Override
    public void warp(float ...values) {//变形
        if(values == null || values.length <= 0) return;
        float x = values[0];//最高点X坐标
        float t = values.length > 1 ? values[1] : minGap;//下面线条的最高值
        int index = 0;
        for(int i = 0;i <= MESHHEIGHT;i++){//运算变化后的坐标
            for(int j = 0;j <= MESHWIDTH;j++){
                verts[index * 2 + 1] = calculate(x,orig[index * 2 + 0],i,t);
                index ++;
            }
        }
    }

    /*
     *正弦波运算
     * @params offsetX   X轴偏移量
     * @params x         实时最高点的X坐标
     * @params row       网格层数
     * @params t         最下面曲线的高度最大值
     * @return           返回对应网格Y坐标
     */
    private float calculate(float offsetX,float x,int row,float t){
        row = row >= MESHHEIGHT ? MESHHEIGHT : row;
        float minT = t >= minGap ? minGap : t;
        float syncCap = (minGap-minT)/minGap*(maxGap-minGap)+minGap;
        float a = ((maxGap-syncCap+2*minT)/2-minT)/MESHHEIGHT*(MESHHEIGHT-row)+minT;
        float b = ((maxGap+syncCap)/2) /MESHHEIGHT*(MESHHEIGHT-row);
        float result = (maxGap+minT) - ((a * (float)Math.cos((Math.PI/bitmapWidth)*(x-offsetX)))+b);
        return result;
    }

    @Override
    public float getTackPositionY(float x, float positionX, int row, float smalltall) {//获取轨道数据
        return calculate(x,positionX,row,smalltall);
    }

    @Override
    public int getVertOffset() {
        return 0;
    }

    @Override
    public int[] getColors() {
        return null;
    }

    @Override
    public void setTanslation(float x, float y) {//设置绘画偏移量
        tanslationX = x;
        tanslationY = y;
    }

    @Override
    public float gettTanslationY() {
        return tanslationY;
    }

    @Override
    public float getTanslationX() {
        return tanslationX;
    }
}
