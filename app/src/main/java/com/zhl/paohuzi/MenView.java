package com.zhl.paohuzi;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.paohuzi.Men;
import com.paohuzi.Zi;

/**
 *
 * Created by  on 2017/9/10.
 */

public class MenView extends View {

    private Men mData;
    private Paint mPaint = new Paint();

    private int mWidth;

    public MenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mData != null) {
            mPaint.setColor(Constants.COLOR_PAIBIAN);
            mPaint.setTextSize(mWidth);
            Zi z[] = mData.getZi();
            for (int i=0; i<4; i++) {
                if(z[3-i] != null) {
                    if (Build.VERSION.SDK_INT > 20) {
                        drawRoundRect(canvas, 0, mWidth*i, mWidth, getHeight(), mPaint);
                    } else {
                        canvas.drawRect(0, mWidth*i, mWidth, getHeight(), mPaint);
                    }
                    mPaint.setColor(z[3-i].getColor());
                    canvas.drawText(Zi.ZI[z[3-i].getId()], 0, mWidth * (i+1) - mWidth / 8, mPaint);
                    mPaint.setColor(Constants.COLOR_PAIBIAN);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getContext().getResources().getDisplayMetrics().widthPixels;
        mWidth = w / 9;
        setMeasuredDimension(mWidth, mWidth * 4);
    }

    @TargetApi(21)
    private void drawRoundRect(Canvas canvas, int left, int top, int width, int height, Paint mPaint) {
        canvas.drawRoundRect(left, top, width, height, 6, 6, mPaint);
    }

    public void setData(Men m) {
        mData = m;
        invalidate();
    }
}
