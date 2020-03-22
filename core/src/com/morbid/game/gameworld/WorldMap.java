package com.morbid.game.gameworld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;
import com.morbid.game.types.WorldType;

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

                chunk.renderBlocks(batch);
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
