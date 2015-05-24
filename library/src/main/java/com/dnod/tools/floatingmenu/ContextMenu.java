package com.dnod.tools.floatingmenu;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public abstract class ContextMenu {


    public static interface OnItemClickListener {
        void onItemClicked(MenuItem item);
    }

    protected TypeEvaluator<PointF> mItemsAppearanceAnimation;
    protected ArrayList<MenuItem> mItems = new ArrayList<>();
    protected ContextMenuItemsAppearanceAlgorithm itemsAppearanceAlgorithm;

    protected void addMenuItem(MenuItem item){
        mItems.add(item);
    }

    protected void addMenuItems(List<MenuItem> menuItems){
        mItems.addAll(menuItems);
    }

    protected void setItemsAppearanceAlgorithm(ContextMenuItemsAppearanceAlgorithm algorithm){
        itemsAppearanceAlgorithm = algorithm;
    }

    protected void setItemsAppearanceAnimation(TypeEvaluator<PointF> itemsAppearanceAnimation){
        mItemsAppearanceAnimation = itemsAppearanceAnimation;
    }

    protected abstract void initialize();
    public abstract void show();
    public abstract void hide();
    public abstract boolean isShowing();
    protected abstract void setOnItemClickListener(OnItemClickListener onItemClickListener);

    public static class MenuItem {
        private static final long DEFAULT_ITEMS_ANIMATION_DURATION = 1_000;    //In milliseconds

        private int mIconRes;
        private int mID;
        private long mAnimationDuration = DEFAULT_ITEMS_ANIMATION_DURATION;

        public MenuItem setIconResID(int resID){
            mIconRes = resID;
            return this;
        }

        public MenuItem setUniqueID(int id){
            mID = id;
            return this;
        }

        public int getIconRes() {
            return mIconRes;
        }

        public int getID() {
            return mID;
        }

        public long getAnimationDuration() {
            return mAnimationDuration;
        }
    }
}
