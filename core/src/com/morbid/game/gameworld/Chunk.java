package com.morbid.game.gameworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.morbid.game.Settings;
import com.morbid.game.entities.Block;
import com.morbid.game.entities.CameraComponent;
import com.morbid.game.utils.DebugTools;
import com.morbid.game.utils.VectorMath;

import java.util.Map;

public class Chunk {
    private Map<Vector2, Block> blockMap;
    private Vector2 worldPosition;

    public Chunk(Map<Vector2, Block> blockMap, Vector2 worldPosition) {
        this.blockMap = blockMap;
        this.worldPosition = worldPosition;
    }

    /**
     * Renders chunk
     * @param batch sprite batch
     */
    public void renderBlocks(Batch batch) {
        for (Block block : blockMap.values()) {
            block.render(batch);
        }
    }

    public Vector2 getWorldPosition() {
        return worldPosition;
    }
}
