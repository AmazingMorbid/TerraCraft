package com.morbid.game.gameworld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.morbid.game.Settings;
import com.morbid.game.entities.Block;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;
import com.morbid.game.types.WorldType;
import com.morbid.game.utils.OpenSimplexNoise;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WorldGenerator {
    private WorldType worldType;
    private World world;
    private BlockType[][] worldBlocks;
    private Chunk[][] worldChunks;

    public WorldMap generateWorld(World world, WorldType worldType) {
        this.world = world;
        this.worldType = worldType;

        generateWorldBlocks();
        generateChunks();

        return new WorldMap(worldType, worldBlocks, worldChunks);
    }

    private void generateWorldBlocks() {
        worldBlocks = new BlockType[Settings.WORLD_SIZE.x][Settings.WORLD_SIZE.y];

        switch (worldType) {
            case DEFAULT:
                generateProceduralWorldBlock();
                break;

            case FLAT:
                generateFlatWorldBlocks();
                break;
        }
    }

    /**
     * Generate a world map.
     */
    private void generateProceduralWorldBlock() {
        // World generator
        OpenSimplexNoise noise = new OpenSimplexNoise(new Random().nextLong());

        // Ore generator
        OpenSimplexNoise coalOreNoise = new OpenSimplexNoise(new Random().nextLong());
        OpenSimplexNoise ironOreNoise = new OpenSimplexNoise(new Random().nextLong());
        OpenSimplexNoise goldOreNoise = new OpenSimplexNoise(new Random().nextLong());
        OpenSimplexNoise diamondOreNoise = new OpenSimplexNoise(new Random().nextLong());

        for (int y = 0; y < Settings.WORLD_SIZE.y; y++) {
            for (int x = 0; x < Settings.WORLD_SIZE.x; x++) {
                double worldEval = noise.eval(x / Settings.WORLD_GENERATOR_FEATURE_SIZE, y / Settings.WORLD_GENERATOR_FEATURE_SIZE);

                // Ores
                double coalOreEval = coalOreNoise.eval(x / Settings.COAL_ORE_FEATURE_SIZE, y / Settings.COAL_ORE_FEATURE_SIZE);
                double ironOreEval = ironOreNoise.eval(x / Settings.IRON_ORE_FEATURE_SIZE, y / Settings.IRON_ORE_FEATURE_SIZE);
                double goldOreEval = goldOreNoise.eval(x / Settings.GOLD_ORE_FEATURE_SIZE, y / Settings.GOLD_ORE_FEATURE_SIZE);
                double diamondOreEval = diamondOreNoise.eval(x / Settings.DIAMOND_ORE_FEATURE_SIZE, y / Settings.DIAMOND_ORE_FEATURE_SIZE);

                worldBlocks[x][y] = BlockType.air;

                /* ********* Generate terrain ********* */
                // Air above layer 61
                if (y < 61) {
                    if (worldEval > -0.3) {
                        worldBlocks[x][y] = BlockType.stone;
                    }
                }

                // Generate dirt above layer 45 and below 62
                if (y > 45 && y < 62) {
                    if (worldBlocks[x][y] == BlockType.air) {
                        // if there is air above stone
                        if (worldBlocks[x][y - 1] == BlockType.stone) {
                            // Change a current block to grass
                            worldBlocks[x][y - 1] = BlockType.grass;

                            // if a block below grass is stone, change it to dirt
                            if (worldBlocks[x][y - 2] != BlockType.air) {
                                worldBlocks[x][y - 2] = BlockType.dirt;

                                // if a block below dirt is stone, change it to dirt
                                if (worldBlocks[x][y - 3] != BlockType.air) {
                                    worldBlocks[x][y - 3] = BlockType.dirt;
                                }
                            }
                        }
                    }
                }

                // Generate ores
                if (worldBlocks[x][y] != BlockType.air && worldBlocks[x][y] != BlockType.dirt) {
                    // Coal ore will spawn below y: 55 and above y: 5
                    if (y < 55 && y > 5) {
                        if (coalOreEval > 0.6) {
                            worldBlocks[x][y] = BlockType.coal_ore;
                        }
                    }

                    // Iron ore will spawn below y: 52 and above y: 5
                    if (y < 52 && y > 5) {
                        if (ironOreEval > 0.65) {
                            worldBlocks[x][y] = BlockType.iron_ore;
                        }
                    }

                    // Gold ore will spawn only below Y: 25 and above Y: 5
                    if (y < 30 && y > 5) {
                        // Chance of gold is ... yes
                        if (goldOreEval > 0.8) {
                            worldBlocks[x][y] = BlockType.gold_ore;
                        }
                    }

                    // Diamond ore will spawn only below Y: 20 and above Y: 5
                    if (y < 15 && y > 5) {
                        // Chance of diamond is ... yes
                        if (diamondOreEval > 0.9) {
                            worldBlocks[x][y] = BlockType.diamond_ore;
                        }
                    }
                }
            }
        }
    }

    /**
     * Generates flat world without ores.
     */
    private void generateFlatWorldBlocks() {
        for (int x = 0; x < Settings.WORLD_SIZE.x; x++) {
            for (int y = 0; y < Settings.WORLD_SIZE.y; y++) {
                // Create air above ground
                if (y > 10) {
                    worldBlocks[x][y] = BlockType.air;
                }

                // Create grass
                if (y == 10) {
                    worldBlocks[x][y] = BlockType.grass;
                }

                // Create dirt
                if (y < 10 && y > 7) {
                    worldBlocks[x][y] = BlockType.dirt;
                }

                // Create stone
                if (y <= 7) {
                    worldBlocks[x][y] = BlockType.stone;
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
        Map<Vector2Int, Block> blockMap = new HashMap<>();

        // Create blocks inside a chunk based on block types

        for (int x = startX; x < (startX + Settings.CHUNK_SIZE.x); x++) {
            for (int y = startY; y < (startY + Settings.CHUNK_SIZE.y); y++) {
                // Check if block is AIR - AIR is basically empty space, no need for a block here
                if (worldBlocks[x][y] != BlockType.air) {
                    Vector2Int position = new Vector2Int(x, y);

                    Block block = new Block(world, position.toVector2(), worldBlocks[x][y]);
                    blockMap.put(position, block);
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
