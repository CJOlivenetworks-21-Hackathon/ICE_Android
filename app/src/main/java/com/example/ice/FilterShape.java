package com.example.ice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Objects;

public class FilterShape extends View {
    Paint paint;
    Path path;
    Canvas canvas;

    private int mViewWidth = 0;
    private int mViewHeight = 0;

    public FilterShape(Context context) {
        super(context);
        initMyDraw();
    }

    public FilterShape(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initMyDraw();
    }

    public FilterShape(Context context, Object[] objects) {
        super(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    public void initMyDraw() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(100, 100, 200, 200, paint);

//        m_Canvas = canvas;
//        m_Canvas.drawColor(0xFFFFFFFF);
//
//        float x = mViewWidth / 2;
//        float y = mViewHeight / 2;
//        m_Canvas.rotate(45, x, y);
//
//        // Path 갱신 영역
//        m_Canvas.drawPath(m_Path, m_Paint);

    }
}
