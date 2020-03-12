package com.morbid.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class used for creating game objects. It is meant to be inherited in Entities. such as Player, Block etc.
 * @version 0.1
 */
public abstract class GameObject {
    /**
     * Position of a GameObject in world
     */
    public Vector2 position;

    /**
     * Creates GameObject with specified values.
     * @param position
     */
    public GameObject(Vector2 position) {
        this.position = position;
        System.out.println("PositionX: " + this.position.x);
    }

    /**
     * Here you place logic
     * @param deltaTime
     */
    public abstract void update(float deltaTime);

    /**
     * Here you place render logic
     */
    public abstract void render(Batch batch);
}
