package com.morbid.game.gameworld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.morbid.game.entities.Block;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;

public class World {
    private BlockType[][] worldBlocks;
    private Chunk[][] chunks;

    public World(BlockType[][] worldBlocks, Chunk[][] chunks) {
        this.worldBlocks = worldBlocks;
        this.chunks = chunks;
    }

    public void renderChunks(Batch batch, int xChunksStart, int xChunksEnd, int yChunksStart, int yChunksEnd) {
        for (int x = xChunksStart; x < xChunksEnd; x++) {
            for (int y = yChunksStart; y < yChunksEnd; y++) {
                chunks[x][y].renderBlocks(batch);
            }
        }
    }

    /**
     * ATTENTION! THIS METHOD IS VERY DANGEROUS! IT WILL RENDER AN ENTIRE MAP NO MATTER HOW BIG IT IS!
     * BE CAREFUL BECAUSE IT CAN RESULT IN MEMORY LEAK.
     * Renders an entire map.
     * @param batch
     */
    public void renderChunks(Batch batch) {
        for (Chunk[] chunkRow : chunks) {
            for (Chunk chunk : chunkRow) {
                chunk.renderBlocks(batch);
            }
        }
    }
}
