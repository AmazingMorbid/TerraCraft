package com.morbid.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent extends OrthographicCamera {
    private GameObject anchor;

    public CameraComponent() {
    }

    public CameraComponent(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

    @Override
    public void update() {
        super.update();

        if (anchor != null) {
            position.set(anchor.getPosition().x, anchor.getPosition().y, 0);
        }
    }

    /**
     * Return GameObject to which camera is attached to
     * @return anchored GameObject
     */
    @SuppressWarnings("GrazieInspection")
    public GameObject getAnchor() {
        return anchor;
    }

    /**
     * Set camera anchor - camera will follow this GameObject
     * @param anchor where to attach camera
     */
    public void setAnchor(GameObject anchor) {
        this.anchor = anchor;
    }
}
