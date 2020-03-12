package com.morbid.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.morbid.game.Settings;

public class CameraComponent extends OrthographicCamera {
    private GameObject anchor;
    private Player attachedPlayer;

    public CameraComponent() {
        super(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getWidth()
//            Settings.CAMERA_VIEWPORT_WIDTH,
//            Settings.CAMERA_VIEWPORT_HEIGHT
        );
    }

    public CameraComponent(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

    @Override
    public void update() {
        super.update();

        if (attachedPlayer != null) {
            position.set(
                    attachedPlayer.body.getPosition().x * Settings.PPM,
                    attachedPlayer.body.getPosition().y * Settings.PPM,
                    0
            );
        }
    }

    /**
     * Set camera anchor - camera will follow this GameObject
     * @param anchor where to attach camera
     */
    public void setAnchor(GameObject anchor) {
        this.anchor = anchor;
    }

    /**
     * Attach the camera to a player
     * @param player
     */
    public void attachPlayer(Player player) {
        this.attachedPlayer = player;
    }
}
