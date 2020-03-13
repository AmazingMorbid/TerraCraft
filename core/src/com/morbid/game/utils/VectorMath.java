package com.morbid.game.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.morbid.game.Settings;
import com.morbid.game.entities.CameraComponent;
import com.morbid.game.gameworld.Chunk;
import com.morbid.game.types.Vector2Int;

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
        float cameraWidthHalf = camera.viewportWidth / 2 / Settings.PPM;
        float cameraHeightHalf = camera.viewportHeight / 2 / Settings.PPM;

        // Calculate index values
        int startX = (int) ((playerPosition.x - cameraWidthHalf) / Settings.CHUNK_SIZE.x) - 1;
        int endX = (int) ((playerPosition.x + cameraWidthHalf) / Settings.CHUNK_SIZE.x) + 1;
        int startY = (int) ((playerPosition.y - cameraHeightHalf) / Settings.CHUNK_SIZE.y) - 1;
        int endY = (int) ((playerPosition.y + cameraHeightHalf) / Settings.CHUNK_SIZE.y) + 1;

        // Clamp chunks to prevent an index out of bounds
        startX  = (int) MathUtils.clamp(startX, 0, Settings.CHUNKS_IN_WORLD.x - 1);
        endX  = (int) MathUtils.clamp(endX, 0, Settings.CHUNKS_IN_WORLD.x - 1);
        startY  = (int) MathUtils.clamp(startY, 0, Settings.CHUNKS_IN_WORLD.y - 1);
        endY  = (int) MathUtils.clamp(endY, 0, Settings.CHUNKS_IN_WORLD.y - 1);

        return new NearChunksResult(
                startX,
                endX,
                startY,
                endY
        );
    }

    public static Vector2Int getChunkAt(Vector2 worldPosition) {
        int x = (int) (worldPosition.x / Settings.CHUNK_SIZE.x);
        int y = (int) (worldPosition.y / Settings.CHUNK_SIZE.y);

        x = (int) MathUtils.clamp(x, 0, Settings.CHUNKS_IN_WORLD.x - 1);
        y = (int) MathUtils.clamp(y, 0, Settings.CHUNKS_IN_WORLD.y - 1);

        return new Vector2Int(x, y);
    }
}
