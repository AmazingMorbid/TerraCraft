package com.morbid.game;

import com.badlogic.gdx.math.Vector2;
import com.morbid.game.types.Vector2Int;

public class Settings {
    /* World Settings */
    public static final Vector2Int WORLD_SIZE = new Vector2Int(64, 64);
    public static final Vector2Int CHUNK_SIZE = new Vector2Int(16, 16);
    public static final Vector2 BLOCK_SCALE = new Vector2(1f, 1f);

    /* Render Settings */
    public static final int BLOCK_SIZE = 64;
    public static final Vector2 CHUNK_SIZE_PIXELS = new Vector2(BLOCK_SIZE * CHUNK_SIZE.x, BLOCK_SIZE * CHUNK_SIZE.y);
    public static final int CAMERA_VIEWPORT_WIDTH = 1200;
    public static final int CAMERA_VIEWPORT_HEIGHT = 1200;
}
