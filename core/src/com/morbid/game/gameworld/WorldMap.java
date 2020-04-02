package com.morbid.game.gameworld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.morbid.game.Settings;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;
import com.morbid.game.types.WorldType;
import com.morbid.game.utils.VectorMath;

import java.util.List;

public class WorldMap {
    private WorldType worldType;
    private BlockType[][] worldBlocks;
    private Chunk[][] chunks;

    public WorldMap(WorldType worldType, BlockType[][] worldBlocks, Chunk[][] chunks) {
        this.worldBlocks = worldBlocks;
        this.chunks = chunks;
        this.worldType = worldType;
    }

    /**
     * Render visible chunks
     * @param batch sprite batch
     * @param xChunksStart array x index of chunk to start
     * @param xChunksEnd array x index of chunk to end
     * @param yChunksStart array y index of chunk to start
     * @param yChunksEnd array y index of chunk to end
     */
    public void renderChunks(Batch batch, int xChunksStart, int xChunksEnd, int yChunksStart, int yChunksEnd) {
        for (int x = xChunksStart; x <= xChunksEnd; x++) {
            for (int y = yChunksStart; y <= yChunksEnd; y++) {
                Chunk chunk = chunks[x][y];

                if (chunk != null && chunk.getBlockMap() != null) {
                    chunk.renderBlocks(batch);
                }
            }
        }
    }

    /**
     * Load specified chunks.
     * @param xChunksStart
     * @param xChunksEnd
     */
    public void loadChunks(int xChunksStart, int xChunksEnd) {
        for (int x = xChunksStart; x <= xChunksEnd; x++) {
            for (int y = 0; y < Settings.CHUNKS_IN_WORLD.y; y++) {
                Chunk chunk = chunks[x][y];

                if (chunk != null) {
                    chunk.load();
                }
            }
        }
    }

    /**
     * Unloads all chunks at x from 0 to max height.
     * @param chunkXIndexToUnload x index where chunks will be unloaded.
     */
    public void unloadChunks(int chunkXIndexToUnload) {
        for (int y = 0; y < Settings.CHUNKS_IN_WORLD.y; y++) {
            Chunk chunk = chunks[chunkXIndexToUnload][y];

            if (chunk != null) {
                chunk.unload();
            }
        }
    }

    public void destroyBlock(Vector2Int blockCellPosition) {
        this.worldBlocks[blockCellPosition.x][blockCellPosition.y] = BlockType.air;
    }

    public Chunk[][] getChunks() {
        return this.chunks;
    }
}
