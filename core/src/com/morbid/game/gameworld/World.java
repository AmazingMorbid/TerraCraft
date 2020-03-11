package com.morbid.game.gameworld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.morbid.game.entities.Block;
import com.morbid.game.entities.CameraComponent;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;
import com.morbid.game.utils.DebugTools;

public class World {
    private BlockType[][] worldBlocks;
    private Chunk[][] chunks;

    public World(BlockType[][] worldBlocks, Chunk[][] chunks) {
        this.worldBlocks = worldBlocks;
        this.chunks = chunks;
    }

    public void renderChunks(Batch batch, CameraComponent camera) {
        for (Chunk[] chunkRow : chunks) {
            for (Chunk chunk : chunkRow) {
                if (chunk.isNearPlayerVision(camera)) {
                    chunk.renderBlocks(batch, camera);
                }
            }
        }
//
    }

    public void renderChunks(Batch batch, CameraComponent camera, int xChunksStart, int xChunksEnd, int yChunksStart, int yChunksEnd) {
        for (int x = xChunksStart; x <= xChunksEnd; x++) {
            for (int y = yChunksStart; y <= yChunksEnd; y++) {
                Chunk chunk = chunks[x][y];

                chunk.renderBlocks(batch, camera);
            }
        }
    }

    public Chunk[][] getChunks() {
        return this.chunks;
    }
}
