package com.dnod.tools.floatingmenu.animation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class FloatingMenuItemLinearAnimation implements TypeEvaluator<PointF> {

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float startX = startValue.x;
        float startY = startValue.y;
        float endX = endValue.x;
        float endY = endValue.y;
        float deltaX = (endX - startX) * fraction;
        float deltaY = (endY - startY) * fraction;
        return new PointF(startX + deltaX, startY + deltaY);
    }
}
