package com.dnod.tools.floatingmenu;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;


public class FloatingMenu extends ContextMenu {
    private PopupWindow mMenuWindow;
    private ContextMenuPopup mRootView;
    private float mAppearanceX;
    private float mAppearanceY;

    public FloatingMenu(Context context, float appearanceX, float appearanceY){
        mAppearanceX = appearanceX;
        mAppearanceY = appearanceY;
        mRootView = new ContextMenuPopup(context);
        mMenuWindow = new PopupWindow(mRootView, FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT, true);
    }


    @Override
    public void initialize() {
        mRootView.setAppearanceAlgorithm(itemsAppearanceAlgorithm);
        mRootView.setAppearancePosition(mAppearanceX, mAppearanceY);
        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMenuWindow.dismiss();
                return false;
            }
        });
        mMenuWindow.setOutsideTouchable(true);
        mMenuWindow.setFocusable(true);
        mMenuWindow.setAnimationStyle(R.style.ContextMenuAnimation);
        mRootView.setAnimation(mItemsAppearanceAnimation);
    }

    @Override
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mRootView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(MenuItem item) {
                onItemClickListener.onItemClicked(item);
                hide();
            }
        });
    }

    @Override
    public void show() {
        if(isShowing())
            return;
        mRootView.addItems(mItems);
        mMenuWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void hide() {
        if(isShowing()){
            mMenuWindow.dismiss();
        }
    }

    @Override
    public boolean isShowing() {
        return mMenuWindow.isShowing();
    }
}
