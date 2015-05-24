package com.dnod.tools.floatingmenu.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dnod.tools.floatingmenu.ContextMenu;
import com.dnod.tools.floatingmenu.ContextMenuItemsAppearanceAlgorithm;
import com.dnod.tools.floatingmenu.R;

import java.util.ArrayList;
import java.util.List;

public class ContextMenuPopup extends FrameLayout{
    private static final int MENU_RADIUS = 60;  //In pixels

    private Paint mBackgroundPaint;
    private PointF mAppearancePoint;
    private boolean mIsAppearanceStarted;
    private ContextMenu.OnItemClickListener mOnItemClickListener;
    private ArrayList<ContextMenu.MenuItem> mItems = new ArrayList<>();
    private float mRadius;
    private ContextMenuItemsAppearanceAlgorithm mAppearanceAlgorithm;
    private TypeEvaluator mAnimation;

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClicked((ContextMenu.MenuItem) v.getTag(R.id.menu_items_tag));
            }
        }
    };

    public ContextMenuPopup(Context context) {
        super(context);
        init();
    }

    public ContextMenuPopup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContextMenuPopup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setWillNotDraw(false);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mRadius = MENU_RADIUS * displayMetrics.density;
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStrokeWidth(1);
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setAnimation(TypeEvaluator<PointF> animation){
        mAnimation = animation;
    }

    public void addItem(@NonNull ContextMenu.MenuItem item){
        mItems.add(item);
    }

    public void addItems(@NonNull List<ContextMenu.MenuItem> items){
        mItems.addAll(items);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed && !mIsAppearanceStarted){
            mIsAppearanceStarted = true;
            FrameLayout.LayoutParams itemLayoutParams;
            PointF nextPoint;
            for(ContextMenu.MenuItem item : mItems) {
                itemLayoutParams = new LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                final ImageView itemView = new ImageView(getContext());
                itemView.setLayoutParams(itemLayoutParams);
                itemView.setImageResource(item.getIconRes());
                itemView.setTag(R.id.menu_items_tag, item);
                itemView.setX(mAppearancePoint.x);
                itemView.setY(mAppearancePoint.y);
                itemView.setOnClickListener(mOnClickListener);
                addView(itemView);
                nextPoint = new PointF();
                if(mAppearanceAlgorithm != null){
                    nextPoint.set(mAppearanceAlgorithm.next());
                } else {
                    nextPoint.set(mAppearancePoint.x + mRadius, mAppearancePoint.y + mRadius);
                }
                ValueAnimator itemAnimator = ValueAnimator.ofObject(mAnimation,
                        mAppearancePoint, nextPoint);
                itemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF currPoint = (PointF) animation.getAnimatedValue();
                        itemView.setX(currPoint.x - itemView.getWidth() / 2);
                        itemView.setY(currPoint.y - itemView.getHeight() / 2);
                    }
                });
                itemAnimator.setDuration(item.getAnimationDuration());
                itemAnimator.start();
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBackgroundPaint != null && mBackgroundPaint.getShader() == null) {
            mBackgroundPaint.setShader(new RadialGradient(mAppearancePoint.x, mAppearancePoint.y,
                    mRadius, Color.TRANSPARENT, getResources().getColor(R.color.black_semi_transparent), Shader.TileMode.CLAMP));
        }
        int width = getWidth();
        int height = getHeight();
        canvas.drawRect(0, 0, width, height, mBackgroundPaint);
    }

    public void setAppearanceAlgorithm(ContextMenuItemsAppearanceAlgorithm algorithm) {
        mAppearanceAlgorithm = algorithm;
    }


    public void setOnItemClickListener(ContextMenu.OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void setAppearancePosition(float x, float y){
        mAppearancePoint = new PointF(x, y);
    }
}
