package com.morbid.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Class used for creating game objects. It is meant to be inherited in Entities. such as Player, Block etc.
 * @version 0.1
 */
public abstract class GameObject {
    private int ID;
    private Vector2 position;
    private Vector2 rotation;
    private Vector2 scale;

    /**
     * Creates GameObject.
     */
    public GameObject() {
        this.ID = 0;
        this.position = new Vector2();
        this.rotation = new Vector2();
        this.scale = new Vector2();
    }

    /**
     * Creates GameObject with specified values.
     * @param position
     * @param rotation
     * @param scale
     */
    public GameObject(int ID, Vector2 position, Vector2 rotation, Vector2 scale) {
        this.ID = ID;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    /**
     * Move GameObject by x and y
     * @param x
     * @param y
     * @return translated position vector
     */
    public Vector2 translate(float x, float y) {
        position.add(x, y);

        return position;
    }

    /**
     * Retuns ID of a GameObject
     * @return ID
     */
    public int getID() {
        return ID;
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

    /**
     * @return position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets GameObject's position
     * @param position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * @return rotation
     */
    public Vector2 getRotation() {
        return rotation;
    }

    /**
     * Sets GameObject's rotation
     * @param rotation
     */
    public void setRotation(Vector2 rotation) {
        this.rotation = rotation;
    }

    /**
     * @return scale
     */
    public Vector2 getScale() {
        return scale;
    }

    /**
     * Sets GameObject's scale
     * @param scale
     */
    public void setScale(Vector2 scale) {
        this.scale = scale;
    }
}
