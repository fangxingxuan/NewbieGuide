package com.app.hubert.library;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/27.
 */
public class GuideLayout extends RelativeLayout {

    public static final int DEFAULT_BACKGROUND_COLOR = 0xb2000000;
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private Paint mPaint;
    private TextPaint textPaint;
    private StaticLayout staticLayout;
    private List<HighLight> highLights;

    public GuideLayout(Context context) {
        this(context, null);
    }

    public GuideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mPaint.setXfermode(xfermode);

        //设置画笔遮罩滤镜,可以传入BlurMaskFilter或EmbossMaskFilter，前者为模糊遮罩滤镜而后者为浮雕遮罩滤镜
        //这个方法已经被标注为过时的方法了，如果你的应用启用了硬件加速，你是看不到任何阴影效果的
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        //关闭当前view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources()
                .getDisplayMetrics()));
        textPaint.setColor(Color.WHITE);

        setClickable(true);

        //ViewGroup默认设定为true，会使onDraw方法不执行，如果复写了onDraw(Canvas)方法，需要清除此标记
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(0, 0);
        canvas.drawColor(mBackgroundColor);
        if (highLights != null) {
            for (HighLight hl : highLights) {
                Paint paint = hl.getPaint();
                if (paint == null)
                    paint = mPaint;
                RectF rectF = hl.getRectF();
                switch (hl.getType()) {
                    case CIRCLE:
                        canvas.drawCircle(rectF.centerX(), rectF.centerY(), hl.getRadius(), paint);
                        break;
                    case OVAL:
                        canvas.drawOval(rectF, paint);
                        break;
                    case ROUND_RECTANGLE:
                        canvas.drawRoundRect(rectF, hl.getRound(), hl.getRound(), paint);
                        break;
                    case RECTANGLE:
                    default:
                        canvas.drawRect(rectF, paint);
                        break;
                }
                if (!TextUtils.isEmpty(hl.getDesc())) {
                    canvas.save();
                    TextPaint textPaint = hl.getTextPaint();
                    if (textPaint == null)
                        textPaint = this.textPaint;
                    int textWidth = (int) rectF.width();
                    if (hl.isTextAlignWithHighlight()) {
                        canvas.translate(rectF.left, rectF.bottom + hl.getDescPadding());
                    } else {
                        textWidth = canvas.getWidth() - hl.getDescPadding() * 2;
                        canvas.translate(hl.getDescPadding(), rectF.bottom + hl.getDescPadding());
                    }
                    staticLayout = new StaticLayout(hl.getDesc(), textPaint, textWidth, Layout.Alignment
                            .ALIGN_NORMAL, 1.0f, 0.0f, false);
                    staticLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }
    }

    public void setHighLights(List<HighLight> holeList) {
        highLights = holeList;
    }

    public void setBackgroundColor(int backgroundColor) {
        if (backgroundColor != 0) {
            this.mBackgroundColor = backgroundColor;
        } else {
            mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
        }
    }
}
