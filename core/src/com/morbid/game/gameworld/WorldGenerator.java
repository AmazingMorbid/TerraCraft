package com.morbid.game.gameworld;

import com.badlogic.gdx.math.Vector2;
import com.morbid.game.Settings;
import com.morbid.game.entities.Block;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;

import java.util.HashMap;
import java.util.Map;

public class WorldGenerator {
    private BlockType[][] worldBlocks;
    private Chunk[][] worldChunks;

    public World generateWorld() {
        generateWorldBlocks();
        generateChunks();

        return new World(worldBlocks, worldChunks);
    }

    private void generateWorldBlocks() {
        worldBlocks = new BlockType[Settings.WORLD_SIZE.x][Settings.WORLD_SIZE.y];

        for (int x = 0; x < Settings.WORLD_SIZE.x; x++) {
            for (int y = 0; y < Settings.WORLD_SIZE.y; y++) {
                worldBlocks[x][y] = BlockType.STONE;
                // Create air above ground
                if (y > 10) {
                    worldBlocks[x][y] = BlockType.AIR;
                }

                // Create grass
                if (y == 10) {
                    worldBlocks[x][y] = BlockType.GRASS;
                }

                // Create dirt
                if (y < 10 && y > 7) {
                    worldBlocks[x][y] = BlockType.DIRT;
                }

                // Create stone
                if (y <= 7) {
                    worldBlocks[x][y] = BlockType.STONE;
                }
            }
        }
    }

    /**
     * Split map into chunks.
     */
    private void generateChunks() {
        int numOfChunksX = Settings.WORLD_SIZE.x / Settings.CHUNK_SIZE.x;
        int numOfChunksY = Settings.WORLD_SIZE.y / Settings.CHUNK_SIZE.y;

        worldChunks = new Chunk[numOfChunksX][numOfChunksY];

        for (int x = 0; x < Settings.WORLD_SIZE.x; x += Settings.CHUNK_SIZE.x) {
            for (int y = 0; y < Settings.WORLD_SIZE.y; y += Settings.CHUNK_SIZE.y) {
                createChunk(x, y);
            }
        }
    }

    /**
     * Create a chunk:
     * @param startX starting x coordinate of a chunk
     * @param startY starting y coordinate of a chunk
     */
    private void createChunk(int startX, int startY) {
        Map<Integer, Block> blockMap = new HashMap<>();

        // Create blocks inside a chunk based on block types

        for (int x = startX; x < (startX + Settings.CHUNK_SIZE.x); x++) {
            for (int y = startY; y < (startY + Settings.CHUNK_SIZE.y); y++) {
                // Check if block is AIR - AIR is basically empty space, no need for a block here
                if (worldBlocks[x][y] != BlockType.AIR) {
                    // Create ID using the bitwise left shift (basically a big number from coordinates)
                    int ID = x + (y << 10);
                    Vector2Int position = new Vector2Int(x, y);

                    Block block = new Block(ID, position, worldBlocks[x][y]);
                    blockMap.put(ID, block);
                }
            }
        }

        // Add the chunk to array
        int chunkX = startX / Settings.CHUNK_SIZE.x;
        int chunkY = startY / Settings.CHUNK_SIZE.y;
        float worldX = startX * Settings.BLOCK_SIZE;
        float worldY = startY * Settings.BLOCK_SIZE;

        Chunk chunk = new Chunk(blockMap, new Vector2(worldX, worldY));

        worldChunks[chunkX][chunkY] = chunk;
    }
}
