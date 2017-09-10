package com.zhl.paohuzi;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.paohuzi.Zi;

/**
 *
 * Created by  on 2017/9/9.
 */

public class ZiView extends View {

    private Paint mPaint = new Paint();

    private Zi mData;

    public ZiView(Context ctx, ZiView v) {
        super(ctx);
        mData = v.getData();
    }

    public ZiView(Context context, Zi data) {
        super(context);
        mData = data;
    }
    public ZiView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getContext().getResources().getDisplayMetrics().widthPixels;
        int size = w / 10;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mData == null) {
            return;
        }

        mPaint.setColor(Constants.COLOR_PAI);
        if (Build.VERSION.SDK_INT > 20) {
            drawRoundRect(canvas, 0, 0, getWidth(), getHeight(), mPaint);

            canvas.drawRect(0, 40, getWidth(), getHeight(), mPaint);
        } else {
            canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        }

        mPaint.setColor(mData.getColor());
        mPaint.setTextSize(getWidth() - 10);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Zi.ZI[mData.getId()], getWidth()/2, getHeight() - getHeight() / 8, mPaint);

    }

    @TargetApi(21)
    private void drawRoundRect(Canvas canvas, int left, int top, int width, int height, Paint mPaint) {
        canvas.drawRoundRect(left, top, width, height, 6, 6, mPaint);
    }

    public Zi getData() {
        return mData;
    }
}
