package com.zjd.floatbutton;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.zjd.floatbutton.util.DensityUtil;
import com.zjd.floatbutton.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 左金栋 on 2017/8/28.
 * 悬浮按钮
 */

public class CustomFloatButton extends View {
    private Context context;
    private Paint paintMainBtn,paintContent,paintFloat,paintText;

    private int border;
    private int width;//设置高
    private int height;//设置高

    private int FloatType=0;
    public static int Type_Circle=0;
    public static int Type_Bitmap=1;

    private List<Bitmap> bitmapList;

    public CustomFloatButton(Context context) {
        super(context);
        this.context=context;
        initPaint();
        initData();
    }

    public CustomFloatButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initPaint();
        initData();
    }

    public CustomFloatButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initPaint();
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFloatBtn(canvas);
        drawMainBtn(canvas);
    }

    private int animValueSum;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getMeasuredWidth(), widthMeasureSpec);// 获得控件的宽度
        height = getDefaultSize(getMeasuredHeight(), heightMeasureSpec);//获得控件的高度

        border=width/8;

        animValueSum=border*3/22;

        if(circleX == 0){
            circleX=width-border;
            circleY=height-border;
            moveX=circleX;
            moveY=circleY;
        }
        setMeasuredDimension(width , height);//设置宽和高

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private void initPaint() {
        paintMainBtn=new Paint();
        paintMainBtn.setAntiAlias(true);
        paintMainBtn.setColor(Color.RED);
        paintMainBtn.setStyle(Paint.Style.FILL_AND_STROKE);

        paintContent=new Paint();
        paintContent.setAntiAlias(true);
        paintContent.setColor(Color.WHITE);
        paintContent.setStyle(Paint.Style.FILL_AND_STROKE);
        paintContent.setStrokeWidth(DensityUtil.dip2px(context,3));

        paintFloat=new Paint();
        paintFloat.setAntiAlias(true);
        paintFloat.setColor(0xFF67C0FF);
        paintFloat.setStyle(Paint.Style.FILL_AND_STROKE);
        paintFloat.setShadowLayer(5,3,3,Color.DKGRAY);

        paintText=new Paint();
        paintText.setAntiAlias(true);
        paintText.setColor(Color.WHITE);
        paintText.setStyle(Paint.Style.FILL_AND_STROKE);
        paintText.setTextAlign(Paint.Align.CENTER);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        shadowLayer=DensityUtil.dip2px(context,5);
    }

    private void initData() {
        bitmapList=new ArrayList<>();
        bitmapList.add(((BitmapDrawable)getResources().getDrawable(R.drawable.cake_72px)).getBitmap());
        bitmapList.add(((BitmapDrawable)getResources().getDrawable(R.drawable.hamburger_72px)).getBitmap());
        bitmapList.add(((BitmapDrawable)getResources().getDrawable(R.drawable.icecream_72px)).getBitmap());
        bitmapList.add(((BitmapDrawable)getResources().getDrawable(R.drawable.watermelon_cuts_72px)).getBitmap());
    }

    private float circleX;
    private float circleY;
    private int shadowLayer;

    /**
     * 主按钮
     * @param canvas
     */
    private void drawMainBtn(Canvas canvas) {
        paintMainBtn.setShadowLayer(5,shadowLayer*(circleX-width/2)/(width/2),shadowLayer*(circleY-height/2)/(height/2),Color.DKGRAY);
        canvas.drawCircle(circleX,circleY,border*5/11,paintMainBtn);
        canvas.drawLine(circleX+value,circleY-border*3/11+value,circleX-value,circleY+border*3/11-value,paintContent);
        canvas.drawLine(circleX-border*3/11+value,circleY-value,circleX+border*3/11-value,circleY+value,paintContent);
    }

    /**
     * 浮动按钮
     * @param canvas
     */
    private void drawFloatBtn(Canvas canvas) {
        paintFloat.setShadowLayer(5,shadowLayer*(circleX-width/2)/(width/2),shadowLayer*(circleY-height/2)/(height/2),Color.DKGRAY);
        float btnX=0;
        float btnY=0;
        for (int i = 0; i < 4; i++) {
            if(Util.getDistance(circleX,circleY,width/2,height/2)<width/5){
                //中间
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*i*90)*10*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*i*90)*10*value);
            }else if(Util.isInside(circleX,circleY,0,height/3,width/7,height*2/3)){
                //左
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*i*60)*13*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*i*60)*13*value);
            }else if(Util.isInside(circleX,circleY,width/3,0,width*2/3,height/7)){
                //上
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*60-90))*13*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*60-90))*13*value);
            }else if(Util.isInside(circleX,circleY,width*6/7,height/3,width,height*2/3)){
                //右
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*60+180))*13*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*60+180))*13*value);
            }else if(Util.isInside(circleX,circleY,width/3,height*6/7,width*2/3,height)){
                //下
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*60+90))*13*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*60+90))*13*value);
            }else if(circleX<width/2&&circleY<height/2){
                //左上
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*i*30)*20*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*i*30)*20*value);
            }else if(circleX>=width/2&&circleY<height/2){
                //右上
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*30-90))*20*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*30-90))*20*value);
            }else if(circleX>=width/2&&circleY>=height/2){
                //右下
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*30+180))*20*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*30+180))*20*value);
            }else if(circleX<width/2&&circleY>=height/2){
                //左下
                btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*30+90))*20*value);
                btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*30+90))*20*value);
            }

            canvas.drawCircle(btnX,btnY,(value/animValueSum)*border*5/11,paintFloat);
            if(FloatType==Type_Circle){
                paintText.setTextSize(value/animValueSum*width/12);
                canvas.drawText(i+"",btnX,btnY-border*5/11+getBaseLineY(paintText),paintText);
            }else if(FloatType==Type_Bitmap){
                canvas.drawBitmap(bitmapList.get(i),new Rect(0,0,bitmapList.get(i).getWidth(),bitmapList.get(i).getHeight()),new RectF((int)(btnX-(value/animValueSum)*border*5/15),(int)(btnY-(value/animValueSum)*border*5/15),(int)(btnX+(value/animValueSum)*border*5/15),(int)(btnY+(value/animValueSum)*border*5/15)),paintContent);
            }
        }
    }

    /**
     * 获取基线中间点
     * @param paint
     * @return
     */
    public int getBaseLineY(Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        return (int) (border*5/11 - top/2 - bottom/2);//基线中间点的y轴计算公式
    }

    private float downX,downY;
    private float moveX,moveY;
    private boolean isClick=false;
    private boolean isFloatClick=false;
    private boolean isFloatClickable=false;
    private int clickPosition=0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                downX=event.getX();
                downY=event.getY();
                isFloatClick=false;

                if(Util.getDistance(downX,downY,circleX,circleY) < border*5/11){
                    isClick=true;
                    return true;
                }

                if(isOpen&&!valueAnimatorOpen.isRunning()){
                    float btnX = 0,btnY=0;
                    for (int i = 0; i < 4; i++) {
                        if(Util.getDistance(circleX,circleY,width/2,height/2)<width/5){
                            //中间
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*i*90)*10*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*i*90)*10*value);
                        }else if(Util.isInside(circleX,circleY,0,height/3,width/7,height*2/3)){
                            //左
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*i*60)*13*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*i*60)*13*value);
                        }else if(Util.isInside(circleX,circleY,width/3,0,width*2/3,height/7)){
                            //上
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*60-90))*13*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*60-90))*13*value);
                        }else if(Util.isInside(circleX,circleY,width*6/7,height/3,width,height*2/3)){
                            //右
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*60+180))*13*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*60+180))*13*value);
                        }else if(Util.isInside(circleX,circleY,width/3,height*6/7,width*2/3,height)){
                            //下
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*60+90))*13*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*60+90))*13*value);
                        }else if(circleX<width/2&&circleY<height/2){
                            //左上
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*i*30)*20*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*i*30)*20*value);
                        }else if(circleX>=width/2&&circleY<height/2){
                            //右上
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*30-90))*20*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*30-90))*20*value);
                        }else if(circleX>=width/2&&circleY>=height/2){
                            //右下
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*30+180))*20*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*30+180))*20*value);
                        }else if(circleX<width/2&&circleY>=height/2){
                            //左下
                            btnX=(float)(circleX+Math.sin(2*Math.PI / 360*(i*30+90))*20*value);
                            btnY=(float)(circleY+Math.cos(2*Math.PI / 360*(i*30+90))*20*value);
                        }

                        if(Util.getDistance(downX,downY,btnX,btnY) < border*5/11){
                            clickPosition=i;
                            isFloatClick=true;
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveX=event.getX();
                moveY=event.getY();

                if(!isOpen&&Util.getDistance(moveX,moveY,downX,downY)>border*5/11){
                    if(moveX<border/2){
                        circleX=border/2;
                    }else if(width-moveX<border/2){
                        circleX=width-border/2;
                    }else {
                        circleX=moveX;
                    }

                    if(moveY<border/2){
                        circleY=border/2;
                    }else if(height-moveY<border/2){
                        circleY=height-border/2;
                    }else {
                        circleY=moveY;
                    }

                    invalidate();
                    isClick=false;
                    return false;
                }

                break;
            case MotionEvent.ACTION_UP:
                if(valueAnimatorOpen!=null&&valueAnimatorOpen.isRunning()){
                    return false;
                }

                if(isClick){
                    OpenAmin();
                    if(isFloatClick){
                        if(mOnBtnClickListener!=null&&isClick){
                            mOnBtnClickListener.onBtnClick(clickPosition);
                        }
                    }
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    private ValueAnimator valueAnimatorOpen;
    private float value;
    private boolean isOpen=false;

    private void OpenAmin() {
        if(isOpen){
            isOpen=false;
            isFloatClickable=false;
            valueAnimatorOpen= ValueAnimator.ofFloat(animValueSum,0);
        }else {
            isOpen=true;
            isFloatClickable=true;
            valueAnimatorOpen= ValueAnimator.ofFloat(0f,animValueSum);
        }
        valueAnimatorOpen.setDuration(500);
        valueAnimatorOpen.setInterpolator(new OvershootInterpolator());
        valueAnimatorOpen.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value= (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorOpen.start();
    }

    private OnBtnClickListener mOnBtnClickListener;

    /**
     * 按键点击
     */
    public interface OnBtnClickListener{
        void onBtnClick(int position);
    }

    public void setOnBtnClickListener(OnBtnClickListener listener){
        this.mOnBtnClickListener=listener;
    }

    public void setFloatType(int type){
        FloatType=type;
        invalidate();
    }
}
