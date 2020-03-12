package com.morbid.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.morbid.game.Settings;
import com.morbid.game.entities.CameraComponent;

public class VectorMath {
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

        // Calculate index values
        int startX = (int) ((playerPosition.x * Settings.PPM - cameraWidthHalf) / Settings.CHUNK_SIZE_PIXELS.x) - 1;
        int endX = (int) ((playerPosition.x * Settings.PPM + cameraWidthHalf) / Settings.CHUNK_SIZE_PIXELS.x) + 1;

        int startY = (int) ((playerPosition.y * Settings.PPM - cameraHeightHalf) / Settings.CHUNK_SIZE_PIXELS.y) - 1;
        int endY = (int) ((playerPosition.y * Settings.PPM + cameraHeightHalf) / Settings.CHUNK_SIZE_PIXELS.y) + 1;

        int worldChunksX = Settings.WORLD_SIZE.x / Settings.CHUNK_SIZE.x - 1;
        int worldChunksY = Settings.WORLD_SIZE.y / Settings.CHUNK_SIZE.y - 1;

        // Clamp chunks to prevent an index out of bounds
        startX = Math.max(startX, 0);
        startX = Math.min(startX, worldChunksX);
        endX = Math.max(endX, 0);
        endX = Math.min(endX, worldChunksX);
        startY = Math.max(startY, 0);
        startY = Math.min(startY, worldChunksY);
        endY = Math.max(endY, 0);
        endY = Math.min(endY, worldChunksY);

        return new NearChunksResult(
                startX,
                endX,
                startY,
                endY
        );
    }
}
