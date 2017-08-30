package com.zjd.floatbutton.util;

/**
 * Created by 左金栋 on 2017/8/18.
 */

public class Util {

    /**
     * 获取两点距离
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
     */
    public static double getDistance(float startX , float startY , float endX , float endY){
        double x1 = Double.parseDouble(String.valueOf(startX)) ;
        double y1 = Double.parseDouble(String.valueOf(startY)) ;
        double x2 = Double.parseDouble(String.valueOf(endX)) ;
        double y2 = Double.parseDouble(String.valueOf(endY)) ;
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    /**
     * 是否在区域内
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public static boolean isInside(float x , float y , float left , float top , float right , float bottom){
        if(x>left&&x<right&&y>top&&y<bottom){
            return true;
        }
        return false;
    }
}
