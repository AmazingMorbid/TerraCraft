package com.morbid.game.utils;

import com.badlogic.gdx.math.Vector2;

public class VectorMath {
    public static boolean isPointInsideRectangle(Vector2 point, Vector2 rectBottomLeft, Vector2 rectTopRight) {
        if (
                (point.x > rectBottomLeft.x && point.x < rectTopRight.x) &&
                (point.y > rectBottomLeft.y && point.y < rectTopRight.y)
        ) {
            return true;
        }

        return false;
    }

    public static boolean isAnyPointInsideRectangle(Vector2[] points, Vector2 rectBottomLeft, Vector2 rectTopRight) {
        for (Vector2 chunkPoint : points) {
            if (isPointInsideRectangle(chunkPoint, rectBottomLeft, rectTopRight)) {
                return true;
            }
        }

        return false;
    }
}
