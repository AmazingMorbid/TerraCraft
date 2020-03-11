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

public class Chunk {
    private Map<Integer, Block> blockMap;
    private Vector2 worldPosition;
    private Color debugColor = Color.PINK;

    public Chunk(Map<Integer, Block> blockMap, Vector2 worldPosition) {
        this.blockMap = blockMap;
        this.worldPosition = worldPosition;
    }

    /**
     * Renders chunk
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
                Settings.CHUNK_SIZE.x * Settings.BLOCK_SIZE,
                Settings.CHUNK_SIZE.y * Settings.BLOCK_SIZE,
                1,
                debugColor,
                camera.combined
        );
    }

    public Vector2 getWorldPosition() {
        return worldPosition;
    }
}
