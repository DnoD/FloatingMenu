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
        float offsetX = startValue.x - endValue.x;
        float offsetY = startValue.y - endValue.y;

        float diffY = endValue.y - startValue.y;
        float diffX = endValue.x - startValue.x;
        float angleInDegrees = (float) (Math.atan2(diffY, diffX) * 180 / Math.PI);

        float endRadius = (float) Math.sqrt(
                Math.pow(offsetX, 2) +
                        Math.pow(offsetY, 2));
        float angle = (angleInDegrees + 360 * mNumTurns) * fraction;
        float radius = endRadius * fraction;
        float deltaX = (float) (radius * Math.cos(Math.toRadians(angle)));
        float deltaY = (float) (radius * Math.sin(Math.toRadians(angle)));
        float nexX = startValue.x + deltaX;
        float nextY = startValue.y + deltaY;
        return new PointF(nexX, nextY);
    }
}
