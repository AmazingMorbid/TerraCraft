package com.morbid.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.morbid.game.Settings;

import java.util.Set;

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

    @Override
    public void update() {
        super.update();

        if (attachedPlayer != null) {
//            position.set(
//                    attachedPlayer.body.getPosition().x * Settings.PPM,
//                    attachedPlayer.body.getPosition().y * Settings.PPM,
//                    0
//            );
            Vector2 attachedPosition = attachedPlayer.body.getPosition();
            position.lerp(new Vector3(attachedPosition.x * Settings.PPM, attachedPosition.y * Settings.PPM, 0) , Settings.CAMERA_SMOOTH_TIME);
        }
    }

    /**
     * Attach the camera to a player
     * @param player
     */
    public void attachPlayer(Player player) {
        this.attachedPlayer = player;
    }
}
