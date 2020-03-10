package com.morbid.game.gameworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.morbid.game.Settings;
import com.morbid.game.entities.Block;
import com.morbid.game.entities.CameraComponent;
import com.morbid.game.utils.DebugTools;
import com.morbid.game.utils.VectorMath;

import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Chunk {
    private Map<Integer, Block> blockMap;
    private Vector2 worldPosition;
    private Color debugColor = Color.PINK;

    public Chunk(Map<Integer, Block> blockMap) {
        this.blockMap = blockMap;
    }

    /**
     * Renders chunk
     * TODO: Render only when chunk is visible
     *
     * @param batch sprite batch
     */
    public void renderBlocks(Batch batch, CameraComponent camera) {
        for (Block block : blockMap.values()) {
            block.render(batch);
        }
    }

    public void renderOutlineDebug(CameraComponent camera) {
        DebugTools.rect(
                worldPosition.x,
                worldPosition.y,
                Settings.CHUNK_SIZE.x * Settings.BLOCK_SCALE,
                Settings.CHUNK_SIZE.y * Settings.BLOCK_SCALE,
                1,
                debugColor,
                camera.combined
        );
    }

    /**
     * Check if chunk is inside camera's viewport
     * @param camera
     * @return true if chunk is visible trough camera
     */
    public boolean isNearPlayerVision(CameraComponent camera) {
        // Chunk border coordinates
        Vector2 bottomLeft = this.worldPosition;
        Vector2 bottomRight = new Vector2(bottomLeft.x + (Settings.CHUNK_SIZE.x * Settings.BLOCK_SCALE), bottomLeft.y);
        Vector2 topLeft = new Vector2(bottomLeft.x, bottomLeft.y + (Settings.CHUNK_SIZE.y * Settings.BLOCK_SCALE));
        Vector2 topRight = new Vector2(bottomRight.x, topLeft.y);

        // Viewport border coordinates
        Vector2 viewportMargin = new Vector2(300, 200);

        Vector2 viewportBottomLeft = new Vector2(
                camera.position.x - (camera.viewportWidth / 2) + viewportMargin.x,
                camera.position.y - (camera.viewportHeight / 2) + viewportMargin.y
        );
        Vector2 viewportTopRight = new Vector2(
                camera.position.x + (camera.viewportWidth / 2) - viewportMargin.x,
                camera.position.y + (camera.viewportHeight / 2) - viewportMargin.y
        );

        Vector2[] chunkBorderPoints = new Vector2[]{
                bottomLeft, bottomRight, topLeft, topRight
        };

        return VectorMath.isAnyPointInsideRectangle(chunkBorderPoints, viewportBottomLeft, viewportTopRight);
    }

    public Vector2 getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(Vector2 worldPosition) {
        this.worldPosition = worldPosition;
    }

    public void setWorldPosition(float x, float y) {
        this.worldPosition = new Vector2(x, y);
    }
}
