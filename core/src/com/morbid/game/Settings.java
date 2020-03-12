package com.morbid.game;

import com.badlogic.gdx.math.Vector2;
import com.morbid.game.types.Vector2Int;

public class Settings {
    /* ---------- World Settings ---------- */
    /**
     * World size in blocks
     */
    public static final Vector2Int WORLD_SIZE = new Vector2Int(64, 64);

    /**
     * Chunk size in blocks
     */
    public static final Vector2Int CHUNK_SIZE = new Vector2Int(16, 16);

    public static final Vector2 BLOCK_SCALE = new Vector2(1f, 1f);

    /* ---------- Render Settings ---------- */
    /**
     * Block size in pixels
     */
    public static final int BLOCK_SIZE = 64;

    public static final Vector2 CHUNK_SIZE_PIXELS = new Vector2(BLOCK_SIZE * CHUNK_SIZE.x, BLOCK_SIZE * CHUNK_SIZE.y);

    public static final int MIN_VIEWPORT_WIDTH = 800;
    public static final int MIN_VIEWPORT_HEIGHT = 800;

    /* ---------- BOX2D ---------- */
    public static final int PPM = 64;

    public static final float WORLD_TO_BOX = 1f / 64f;

    public static final float BOX_TO_WORLD = 1f / WORLD_TO_BOX;
}
