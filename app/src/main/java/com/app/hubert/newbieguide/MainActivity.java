package com.app.hubert.newbieguide;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.hubert.library.HighLight;
import com.app.hubert.library.NewbieGuide;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private Random random;
    private Paint paint;
    private TextPaint textPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        DashPathEffect effect = new DashPathEffect(new float[]{16, 12}, 0);
        paint.setPathEffect(effect);
        // PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        // paint.setXfermode(xfermode);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(45);
        textPaint.setColor(Color.WHITE);

        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridViewActivity.start(MainActivity.this);
            }
        });
        final Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestFragmentActivity.start(MainActivity.this);
            }
        });

        paint.setStrokeWidth(3);
        //新增多页模式，即一个引导层显示多页引导内容
        NewbieGuide.with(this).setLabel("page")//设置引导层标示区分不同引导层，必传！否则报错
                .alwaysShow(true)//是否每次都显示引导层，默认false，只显示一次
                //-------------以上元素为引导层属性--------------*/

                .addHighLight(genHighlight(textView))
                .asPage()//保存参数为第一页
                //------------- 第一页引导页的属性 --------------*/


                .addHighLight(genHighlight(button))
                //从新设置第二页的参数
                .setLayoutRes(R.layout.view_guide).asPage()
                //------------- 第二页引导页的属性 --------------*/


                .addHighLight(textView).setLayoutRes(R.layout.view_guide_custom, R.id.iv)//引导页布局，点击跳转下一页或者消失引导层的空间id
                .setEveryWhereCancelable(false)//是否点击任意地方跳转下一页或者消失引导层，默认true
                .fullScreen(true)//是否全屏，即是否包含状态栏，默认false，设置为true需要Activity设置为全屏或者沉浸式状态栏
                .setBackgroundColor(getResources().getColor(R.color.testColor))//设置背景色，建议使用有透明度的颜色
                //                .asPage()//只有一页或者最后一页可以省略
                //------------- 第三页引导页的属性 --------------*/

                .show();//显示引导层
    }

    private HighLight genHighlight(View view) {
        HighLight hl = new HighLight(view, HighLight.Type.ROUND_RECTANGLE);
        hl.setRound(12);
        hl.setDesc("这里是文字描述，用来紧跟高亮区域下方进行补充说明的作用，可以不设置");
        hl.setDescPadding(30);
        hl.setPaint(paint);
        hl.setTextPaint(textPaint);
        hl.setTextAlignWithHighlight(random.nextBoolean());
        return hl;
    }

    //简单使用，只显示一页引导页
    //        NewbieGuide.with(this)//传入activity
    //                .setLabel("guide1")//设置引导层标示，必传！否则报错
    //                .addHighLight(button)//添加需要高亮的view
    //                .setLayoutRes(R.layout.view_guide_custom)//自定义的提示layout，不要添加背景色，引导层背景色通过setBackgroundColor()设置
    //                .setBackgroundColor(getResources().getColor(R.color.testColor))
    //                .alwaysShow(true)//总是显示
    //                .show();//直接显示引导层

    //v1.1.1之前版本使用这种方式连续显示多页引导页
    //        Controller controller = NewbieGuide.with(this)
    //                .setLabel("test")
    //                .setOnGuideChangedListener(new OnGuideChangedListener() {//设置监听
    //                    @Override
    //                    public void onShowed(Controller controller) {
    //                        //引导层显示
    //                    }
    //
    //                    @Override
    //                    public void onRemoved(Controller controller) {
    //                        //引导层消失
    //                        NewbieGuide.with(MainActivity.this)//传入activity
    //                                .setLabel("guide1")//设置引导层标示，必传！否则报错
    //                                .addHighLight(button)//添加需要高亮的view
    //                                .setLayoutRes(R.layout.view_guide_custom)
    // 自定义的提示layout，不要添加背景色，引导层背景色通过setBackgroundColor()设置
    //                                .setBackgroundColor(getResources().getColor(R.color.testColor))
    //                                .alwaysShow(true)//总是显示
    //                                .show();//直接显示引导层
    //                    }
    //                })
    //                .addHighLight(textView)
    //                .setEveryWhereCancelable(false)//设置点击任何区域消失，默认为true
    //                .setLayoutRes(R.layout.view_guide, R.id.iv)//自定义的提示layout,第二个可变参数为点击隐藏引导层view的id
    //                .alwaysShow(true)//是否每次都显示引导层，默认false
    //                .build();
    //
    //        controller.resetLabel();
    //        controller.remove();
    //        controller.show();
}
