package com.dnod.tools.floatingmenu.animation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * This is default ContextMenu item animation
 */
public class FloatingMenuItemSpiralAnimation implements TypeEvaluator<PointF> {
    private float mNumTurns = 1;

    public FloatingMenuItemSpiralAnimation(float numTurns){
        mNumTurns = Math.max(mNumTurns, numTurns);
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float startX = startValue.x;
        float startY = startValue.y;
        float endX = endValue.x;
        float endY = endValue.y;
        float lengthX = startX - endX;
        float lengthY = startY - endY;
        float endRadius = (float) Math.sqrt(
                Math.pow(lengthX, 2) +
                        Math.pow(lengthY, 2));
        float a = 0f;
        float b = (endRadius - a) / (mNumTurns * 360f);
        float endAngle = (endRadius - a) / b;
        float angle = endAngle * fraction;
        float radius = a + b * angle;
        float angleOffset = (float) (90f - Math.atan2(lengthX, lengthY));
        float deltaX = (float) (radius * Math.cos(Math.PI * (angle / 180 + angleOffset)));
        float deltaY = (float) (radius * Math.sin(Math.PI * (angle / 180 + angleOffset)));
        float nexX = startX + deltaX;
        float nextY = startY - deltaY;
        return new PointF(nexX, nextY);
    }
}
