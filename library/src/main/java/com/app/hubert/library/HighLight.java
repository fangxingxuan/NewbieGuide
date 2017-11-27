package com.app.hubert.library;

import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/27.
 */
public class HighLight {

    private View mHole;
    private Type mType;
    private int round;
    private Paint paint;//自定义画笔;
    private TextPaint textPaint;//自定义文字画笔;
    private String desc;//高亮的引导描述
    private int descPadding;//描述文字距离高亮区域的padding
    private boolean textAlignWithHighlight = false;//desc文字是否两端对齐高亮区域，false:对齐画布，true:对齐高亮
    private int imageRes;//同desc

    public HighLight(View hole, Type type) {
        this.mHole = hole;
        this.mType = type;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getRadius() {
        return mHole != null ? Math.max(mHole.getWidth() / 2, mHole.getHeight() / 2) : 0;
    }

    public RectF getRectF() {
        RectF rectF = new RectF();
        if (mHole != null) {
            int[] location = new int[2];
            mHole.getLocationOnScreen(location);
            rectF.left = location[0];
            rectF.top = location[1];
            rectF.right = location[0] + mHole.getWidth();
            rectF.bottom = location[1] + mHole.getHeight();
        }
        return rectF;
    }

    public Type getType() {
        return mType;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public TextPaint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(TextPaint textPaint) {
        this.textPaint = textPaint;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDescPadding() {
        return descPadding;
    }

    public void setDescPadding(int descPadding) {
        this.descPadding = descPadding;
    }

    public boolean isTextAlignWithHighlight() {
        return textAlignWithHighlight;
    }

    public void setTextAlignWithHighlight(boolean textAlignWithHighlight) {
        this.textAlignWithHighlight = textAlignWithHighlight;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public static enum Type {
        CIRCLE,//圆形
        RECTANGLE, //矩形
        OVAL,//椭圆
        ROUND_RECTANGLE;//圆角矩形
    }
}