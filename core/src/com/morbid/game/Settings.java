package com.morbid.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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

    public static final Vector2 CHUNKS_IN_WORLD = new Vector2(WORLD_SIZE.x / CHUNK_SIZE.x, WORLD_SIZE.y / CHUNK_SIZE.y);

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

    public static Vector2 box2dToSpritePos(Body body, Sprite sprite) {
        return new Vector2(
                body.getPosition().x * Settings.PPM - sprite.getWidth() / 2,
                body.getPosition().y * Settings.PPM - sprite.getHeight() / 2
        );
    }
}
