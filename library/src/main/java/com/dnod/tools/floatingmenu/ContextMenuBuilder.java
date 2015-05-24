package com.dnod.tools.floatingmenu;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import java.util.List;

public abstract class ContextMenuBuilder {
    protected ContextMenu menu;

    public ContextMenuBuilder addMenuItem(ContextMenu.MenuItem item){
        menu.addMenuItem(item);
        return this;
    }

    public ContextMenuBuilder addMenuItems(List<ContextMenu.MenuItem> items){
        menu.addMenuItems(items);
        return this;
    }
    public ContextMenuBuilder setOnItemClickListener(ContextMenu.OnItemClickListener onItemClickListener){
        menu.setOnItemClickListener(onItemClickListener);
        return this;
    }

    public ContextMenuBuilder setItemsAppearanceAlgorithm(ContextMenuItemsAppearanceAlgorithm algorithm){
        menu.setItemsAppearanceAlgorithm(algorithm);
        return this;
    }

    public ContextMenuBuilder setAppearanceAnimation(TypeEvaluator<PointF> appearanceAnimation){
        menu.setItemsAppearanceAnimation(appearanceAnimation);
        return this;
    }

    public abstract ContextMenu buildMenu();
}
