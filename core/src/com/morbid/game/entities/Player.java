package com.morbid.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {
    public Player(float x, float y) {
        super((MathUtils.random(512) << 10), new Vector2(x, y), Vector2.Zero, new Vector2(1, 1));
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(Batch batch) {

    }
}
