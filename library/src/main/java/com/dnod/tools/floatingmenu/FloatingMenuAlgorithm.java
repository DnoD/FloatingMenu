package com.dnod.tools.floatingmenu;

import android.graphics.PointF;
import android.graphics.RectF;

public class FloatingMenuAlgorithm implements ContextMenuItemsAppearanceAlgorithm {
    private static final int DEFAULT_PADDING = 50;  //In pixels
    private static final double ITEMS_ROTATION_DEGREE = 45;

    private PointF mCenterPoint = new PointF();
    private PointF mStartPoint;
    private int mItemsCount;
    private int mCurrItem = -1;
    private volatile double mStartDegree;
    private volatile double mLeftItemsDegree;
    private volatile double mRightItemsDegree;
    private RectF mScreenRect = new RectF();
    private RectF mAvailableRect = new RectF();
    private float mMenuRadius;
    private float mDefaultMenuRadius;

    public FloatingMenuAlgorithm(PointF centerPoint, int screenW, int screenH, int itemsCount, int radius) {
        mCenterPoint.set(centerPoint);
        mItemsCount = itemsCount;
        mScreenRect.set(0, 0, screenW, screenH);
        mAvailableRect.set(mScreenRect.left + DEFAULT_PADDING, mScreenRect.top + DEFAULT_PADDING, mScreenRect.right - DEFAULT_PADDING, mScreenRect.bottom - DEFAULT_PADDING);
        mMenuRadius = radius;
        mDefaultMenuRadius = mMenuRadius;
    }

    @Override
    public PointF next() {
        if(mCurrItem >= mItemsCount){
            return null;
        }
        PointF nextPoint = new PointF();
        mCurrItem++;
        if(mStartPoint == null){
            calculateStartPoint();
            nextPoint.set(mStartPoint);
        } else {
            PointF point = generateNextPoint();
            nextPoint.set(point);
        }
        return nextPoint;
    }

    private PointF generateNextRightPoint() {
        PointF point = null;
        mRightItemsDegree += ITEMS_ROTATION_DEGREE;
        float xChange = (float) (mMenuRadius * Math.cos(Math.toRadians(mStartDegree - mRightItemsDegree)));
        float yChange = (float) (mMenuRadius * Math.sin(Math.toRadians(mStartDegree - mRightItemsDegree)));
        float newX = mCenterPoint.x + xChange;
        float newY = mCenterPoint.y - yChange;
        if(mAvailableRect.contains(newX, newY)){
            point = new PointF(newX, newY);
        }
        return point;
    }

    private PointF generateNextLeftPoint() {
        PointF point = null;
        mLeftItemsDegree += ITEMS_ROTATION_DEGREE;
        float xChange = (float) (mMenuRadius * Math.cos(Math.toRadians(mStartDegree + mLeftItemsDegree)));
        float yChange = (float) (mMenuRadius * Math.sin(Math.toRadians(mStartDegree + mLeftItemsDegree)));
        float newX = mCenterPoint.x + xChange;
        float newY = mCenterPoint.y - yChange;
        if(mAvailableRect.contains(newX, newY)){
            point = new PointF(newX, newY);
        }
        return point;
    }

    private PointF generateNextPoint(){
        PointF point = null;
        while (point == null && mLeftItemsDegree - 360 != mStartDegree && mRightItemsDegree - 360 != mStartDegree){
            if(mLeftItemsDegree == mRightItemsDegree) {
                point = generateNextLeftPoint();
            } else {
                point = generateNextRightPoint();
            }
        }
        if(point == null){
            mMenuRadius += mDefaultMenuRadius / 2;
            mLeftItemsDegree = -ITEMS_ROTATION_DEGREE;
            mRightItemsDegree = -ITEMS_ROTATION_DEGREE;
            point = generateNextPoint();
        }
        return point;
    }

    private void calculateStartPoint() {
        float startX = Math.max(mCenterPoint.x, mAvailableRect.left);
        float startY = mCenterPoint.y - mDefaultMenuRadius;
        mStartDegree = 90;
        if(startY <= mAvailableRect.top){
            startY = mCenterPoint.y + mMenuRadius;
            mStartDegree = 270;
        }
        if(startY > mAvailableRect.bottom){
            startY = mScreenRect.bottom - DEFAULT_PADDING;
        }
        if(startX < DEFAULT_PADDING){
            startX = DEFAULT_PADDING;
        }
        if(startX > mAvailableRect.right){
            startX = mAvailableRect.right;
        }
        mStartPoint = new PointF(startX, startY);
    }

    @Override
    public int getCurrPosition() {
        return mCurrItem;
    }
}
