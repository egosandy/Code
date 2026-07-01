package com.rcdriver.dr.utils;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class TimelineDecoration extends RecyclerView.ItemDecoration {


    private int width;//Time axis width
    private int top;//The height of the circle from the top of the item
    private Drawable goingDrawable;//Green checkmark
    private int goingDrawableSize;//The diameter of the green check circle
    private int dividerHeight;//Line thickness

    private int lintColor = 0xff999999;//Line color
    private Paint mPaint;

    private int ovalRadius = 12;//The radius of the gray circle


    public TimelineDecoration(int width, int top, Drawable goingDrawable,int goingDrawableSize, int dividerHeight) {
        this.width = width;
        this.top = top;
        this.goingDrawableSize = goingDrawableSize;
        this.goingDrawable = goingDrawable;
        this.dividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(width, 0, 0, dividerHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            int top = child.getTop();
            int bottom = child.getBottom();

            //Vertical line

            int left = parent.getPaddingLeft() + width / 2;
            c.drawRect(left,
                    i==0?this.top+goingDrawableSize:top,//The first item line is free
                    left + dividerHeight,
                    bottom + dividerHeight,
                    mPaint);

            //Small dots

            int ovalCenterX = top + this.top + ovalRadius;
            if (i == 0) {
                goingDrawable.setBounds(left-goingDrawableSize/2,top+this.top,left+goingDrawableSize/2,top+this.top+goingDrawableSize);
                goingDrawable.draw(c);
            } else {
                c.drawCircle(left, ovalCenterX, ovalRadius, mPaint);
            }


            //split line
            mPaint.setColor(lintColor);
            c.drawRect(parent.getPaddingLeft() + width, bottom, parent.getWidth() - parent.getPaddingRight(), bottom + dividerHeight, mPaint);


        }

    }
}