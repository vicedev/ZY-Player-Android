package com.vicedev.zy_player_android.common;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author vicedev
 * @email vicedev1001@gmail.com
 * @date 2020/9/6 12:19
 * @desc 分割线
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private int mDividerWidth,mDividerHeight;

    public GridDividerItemDecoration(int height) {
        mDividerWidth = height;
        mDividerHeight = height;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public GridDividerItemDecoration(int height, @ColorInt int color) {
        mDividerWidth = height;
        mDividerHeight = height;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public GridDividerItemDecoration(int width, int height, @ColorInt int color) {
        mDividerWidth = width;
        mDividerHeight = height;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();

        boolean isLastRow = isLastRow(parent, itemPosition, spanCount, childCount);

        int top = 0;
        int left;
        int right;
        int bottom;
        int eachWidth = (spanCount - 1) * mDividerWidth / spanCount;
        int dl = mDividerWidth - eachWidth;

        left = itemPosition % spanCount * dl;
        right = eachWidth - left;
        bottom = mDividerHeight;
        if (isLastRow){
            bottom = 0;
        }
        outRect.set(left, top, right, bottom);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        draw(c, parent);
    }

    //绘制item分割线
    private void draw(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            boolean isLastRow = isLastRow(parent, i, getSpanCount(parent), childCount);
            //画水平分隔线
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDividerHeight;
            if (isLastRow)  bottom = top;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
            //画垂直分割线
            top = child.getTop();
            bottom = child.getBottom() + mDividerHeight;
            left = child.getRight() + layoutParams.rightMargin;
            right = left + mDividerWidth;
            if (isLastRow)  bottom = child.getBottom();
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount,
                                 int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {// 如果是最后一列，则不需要绘制右边
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRow(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
            return lines == pos / spanCount + 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFirstRow(RecyclerView parent, int pos, int spanCount,
                               int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos / spanCount + 1) == 1) {
                return true;
            } else {
                return false;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //获取列数
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }
}