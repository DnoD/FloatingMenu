package com.dnod.tools.floatingmenu;

import android.graphics.PointF;

/**
 * This is an interface that describe context menu items appearance positions generation.
 */
public interface ContextMenuItemsAppearanceAlgorithm {
    public PointF next();
    public int getCurrPosition();
}
