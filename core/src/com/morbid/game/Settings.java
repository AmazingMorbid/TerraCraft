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
    public static final Vector2Int WORLD_SIZE = new Vector2Int(512, 256);

    /**
     * Chunk size in blocks
     */
    public static final Vector2Int CHUNK_SIZE = new Vector2Int(16, 16);

    public static final double WORLD_GENERATOR_FEATURE_SIZE = 10;

    public static final double COAL_ORE_FEATURE_SIZE = 6;
    public static final double IRON_ORE_FEATURE_SIZE = 5;
    public static final double GOLD_ORE_FEATURE_SIZE = 3;
    public static final double DIAMOND_ORE_FEATURE_SIZE = 2;


    /**
     * Number of chunks in world by dimension
     */
    public static final Vector2 CHUNKS_IN_WORLD = new Vector2(WORLD_SIZE.x / CHUNK_SIZE.x, WORLD_SIZE.y / CHUNK_SIZE.y);

    public static final Vector2 BLOCK_SCALE = new Vector2(1f, 1f);

    public static final Vector2 PLAYER_SCALE = new Vector2(0.6f, 1.8f);

    /* ---------- Render Settings ---------- */
    /**
     * Block size in pixels
     */
    public static final int BLOCK_SIZE = 64;

    public static final Vector2 CHUNK_SIZE_PIXELS = new Vector2(BLOCK_SIZE * CHUNK_SIZE.x, BLOCK_SIZE * CHUNK_SIZE.y);

    // ---------- Camera Settings ---------- //
    public static final int MIN_VIEWPORT_WIDTH = 2500;
    public static final int MIN_VIEWPORT_HEIGHT = 2500;
    public static final float CAMERA_SMOOTH_TIME = 0.3f;

    /* ---------- Gameplay settings ---------- */
    /**
     * Distance in which player can take action with entities.
     */
    public static final int PLAYER_REACH = 3;

    /**
     * Maximum velocity of player.
     */
    public static final float PLAYER_MAXIMUM_VELOCITY = 3.5f;

    /**
     * Maximum velocity of player in flight.
     */
    public static final float PLAYER_MAXIMUM_FLYING_VELOCITY = 9.0f;

    /**
     * Movement acceleration of player.
     */
    public static final float PLAYER_ACCELERATION = 30f;

    /**
     * Height in blocks on which player can jump
     */
    public static final float PLAYER_JUMP_HEIGHT = 1.2f;

    /* ---------- BOX2D ---------- */
    /**
     * Pixels to Meters
     */
    public static final int PPM = 64;

    /**
     * Calculate Sprite position for Body.
     * Box2D and sprites use different point in world.
     * @param body
     * @param sprite
     * @return position of a Sprite.
     */
    public static Vector2 box2dToSpritePos(Body body, Sprite sprite) {
        return new Vector2(
                body.getPosition().x * Settings.PPM - sprite.getWidth() / 2,
                body.getPosition().y * Settings.PPM - sprite.getHeight() / 2
        );
    }
}
