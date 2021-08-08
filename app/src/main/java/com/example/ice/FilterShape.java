package com.example.ice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Objects;

public class FilterShape extends View {
    Paint paint;
    Path path;
    Object[] yoloObjects;
    boolean IsSafe;
    //    test용 변수
    int x;
    int y;
    int w;
    int h;

    private int mViewWidth = 0;
    private int mViewHeight = 0;

    public FilterShape(Context context) {
        super(context);
//        initMyDraw();
    }

    public FilterShape(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        initMyDraw();
    }

    public FilterShape(Context context, Object[] objects, boolean IsSafe) {
        super(context);
        this.yoloObjects = objects;
        this.IsSafe = IsSafe;
//        initMyDraw();
    }

    //    Test용 임시 생성자
    public FilterShape(Context context, int x, int y, int w, int h, boolean IsSafe) {
        super(context);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.IsSafe = IsSafe;
        if (IsSafe) {
            initMySafeDraw();
        } else {
            initMyUnsafeDraw();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    public void initMySafeDraw() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    public void initMyUnsafeDraw() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    public void DrawSafeSign(Canvas canvas) {
        canvas.drawRect(x, y, x + w, y + h, paint);
    }

    public void DrawUnsafeSign(Canvas canvas) {
        canvas.drawLine(x + (w / 3), y + (h / 3), x + (2 * (w / 3)), y + (2 * (h / 3)), paint);
        canvas.drawLine(x + (2 * (w / 3)), y + (h / 3), x + (w / 3), y + (2 * (h / 3)), paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (IsSafe) {
            DrawSafeSign(canvas);
        } else {
            DrawUnsafeSign(canvas);
        }
    }
}
