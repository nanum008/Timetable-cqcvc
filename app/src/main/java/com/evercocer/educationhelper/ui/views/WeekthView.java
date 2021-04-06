package com.evercocer.educationhelper.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class WeekthView extends View {
    private String weekTH = "null";
    private Paint mPaint;

    private int mWidth;
    private  int mHeight;


    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }


    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public WeekthView(Context context) {
        this(context, null);
    }

    public WeekthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(60);
        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        canvas.drawText(weekTH, getWidth() / 5, getMeasuredHeight()-20, mPaint);

        mPaint.setTextSize(20);
        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        canvas.drawText("th",getWidth()/4+35,getHeight()-20,mPaint);
    }

    public String getWeekTH() {
        return weekTH;
    }

    public void setWeekTH(String weekTH) {
        this.weekTH = weekTH;
    }
}