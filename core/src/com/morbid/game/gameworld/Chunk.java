package com.morbid.game.gameworld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.morbid.game.entities.Block;

import java.util.Map;

public class Chunk {
    private Map<Integer, Block> blockMap;

    public Chunk(Map<Integer, Block> blockMap) {
        this.blockMap = blockMap;
    }

    /**
     * Renders chunk
     * TODO: Render only when chunk is visible
     * @param batch sprite batch
     */
    public void renderBlocks(Batch batch) {
        for (Block block : blockMap.values()) {
            block.render(batch);
        }
    }
}
