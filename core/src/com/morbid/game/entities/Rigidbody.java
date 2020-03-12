package com.morbid.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Rigidbody is a GameObject with physics enabled.
 */
public abstract class Rigidbody extends GameObject {
    public World world;
    public Body body;

    /**
     * Creates GameObject with specified values.
     * @param worldPosition
     */
    public Rigidbody(World world, Vector2 worldPosition) {
        super(worldPosition);

        this.world = world;
    }

    /**
     * Create Box2D body for physics.
     */
    public abstract void createBody();
}
