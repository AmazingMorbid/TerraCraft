package com.morbid.game.gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.morbid.game.GameManager;
import com.morbid.game.Settings;
import com.morbid.game.entities.Block;
import com.morbid.game.entities.CameraComponent;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;
import com.morbid.game.utils.DebugTools;
import com.morbid.game.utils.VectorMath;

import java.util.HashMap;
import java.util.Map;

public class Chunk {
    private Map<Vector2Int, Block> blockMap;
    private BlockType[][] blockTypes;
    private Vector2 worldPosition;

    public Chunk(Vector2 worldPosition, BlockType[][] blockTypes) {
        this.worldPosition = worldPosition;
        this.blockTypes = blockTypes;
        blockMap = null;
    }

    public Chunk(Map<Vector2Int, Block> blockMap, Vector2 worldPosition) {
        this.blockMap = blockMap;
        this.worldPosition = worldPosition;
    }

    public void update(float deltaTime) {
        if (blockMap != null) {
            // Update blocks
            for (int x = 0; x < Settings.CHUNK_SIZE.x; x++) {
                for (int y = 0; y < Settings.CHUNK_SIZE.y; y++) {
                    Vector2Int worldBlockPosition = new Vector2Int(
                            (int) (worldPosition.x + x),
                            (int) (worldPosition.y + y)
                    );
                    Block block = blockMap.get(worldBlockPosition);

                    // Air is null
                    if (block != null) {
                        block.update(deltaTime);
                    }
                }
            }

            // Unload chunk if player is fat away
//            if (GameManager.getVisibleChunks().startX < worldPosition.x || GameManager.getVisibleChunks().endX > worldPosition.x)

            // Unload chunk if player is far away
//            if (GameManager.getPlayer().body.getPosition().dst(worldPosition) > Settings.CHUNK_UNLOAD_DISTANCE) {
//                System.out.printf("x: %f | y: %f |: I'm soooo far away!\n", worldPosition.x, worldPosition.y);
//                unload();
//            }
        } else {
            // Load chunk if player is nearby
//            if (GameManager.getPlayer().body.getPosition().dst(worldPosition) < Settings.CHUNK_UNLOAD_DISTANCE) {
//                load();
//            }
        }
    }

    public void load() {
        if (blockMap == null) {
            blockMap = new HashMap<>();

            for (int x = 0; x < Settings.CHUNK_SIZE.x; x++) {
                for (int y = 0; y < Settings.CHUNK_SIZE.y; y++) {
                    BlockType blockType = blockTypes[x][y];

                    if (blockType != BlockType.air) {
                        Vector2 worldBlockPosition = new Vector2(
                                worldPosition.x + x,
                                worldPosition.y + y
                        );

                        Block block = new Block(GameManager.getWorld(), worldBlockPosition, blockType);
                        blockMap.put(new Vector2Int((int) worldBlockPosition.x, (int) worldBlockPosition.y), block);
                    }
                }
            }
        }
    }

    public void unload() {
        if (blockMap != null) {
            for (int x = 0; x < Settings.CHUNK_SIZE.x; x++) {
                for (int y = 0; y < Settings.CHUNK_SIZE.y; y++) {
                    Vector2Int worldBlockPosition = new Vector2Int(
                            (int) (worldPosition.x + x),
                            (int) (worldPosition.y + y)
                    );

                    Block block = blockMap.get(worldBlockPosition);

                    // Air is null
                    if (block != null) {
                        block.destroy();
                    }
                }
            }

            blockMap = null;
        }
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

    public void destroyBlock(Vector2Int cellPosition) {
        blockMap.remove(cellPosition);
    }

    public Map<Vector2Int, Block> getBlockMap() {
        return blockMap;
    }
}
