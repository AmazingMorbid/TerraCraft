package com.morbid.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.morbid.game.Settings;
import com.morbid.game.entities.CameraComponent;

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

    public static class NearChunksResult {
        public int startX;
        public int startY;
        public int endX;
        public int endY;

        public NearChunksResult(int startX, int endX, int startY, int endY) {
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
        }
    }

    /**
     * Get chunks indexes that are near player
     * @param playerPosition
     * @return chunk indexes
     */
    public static NearChunksResult getChunksNearPlayer(CameraComponent camera, Vector2 playerPosition) {
        float cameraWidthHalf = camera.viewportWidth / 2;
        float cameraHeightHalf = camera.viewportHeight / 2;

        // Calculate index values and make sure they are always >= 0
        int startX = Math.max((int) ((playerPosition.x - cameraWidthHalf) / Settings.CHUNK_SIZE_PIXELS.x) + 1, 0);
        int endX = Math.max((int) ((playerPosition.x + cameraWidthHalf) / Settings.CHUNK_SIZE_PIXELS.x) - 1, 0);

        int startY = Math.max((int) ((playerPosition.y - cameraHeightHalf) / Settings.CHUNK_SIZE_PIXELS.y) + 1, 0);
        int endY = Math.max((int) ((playerPosition.y + cameraHeightHalf) / Settings.CHUNK_SIZE_PIXELS.y) - 1, 0);

        return new NearChunksResult(
                startX,
                endX,
                startY,
                endY
        );
    }
}
