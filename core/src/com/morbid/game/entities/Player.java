package com.morbid.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.morbid.game.AssetLoader;
import com.morbid.game.Settings;

import java.util.Set;

public class Player extends Rigidbody {
    Sprite sprite;

    public Player(World world, Vector2 pos) {
        super(world, pos);

        createSprite();
        createBody();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(Batch batch) {
        Vector2 newSpritePos = Settings.box2dToSpritePos(body, sprite);
        sprite.setPosition(newSpritePos.x, newSpritePos.y);

        sprite.draw(batch);
    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(
                position.x + sprite.getWidth() / 2 / Settings.PPM,
                position.y + sprite.getHeight() / 2 / Settings.PPM
        );

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                sprite.getWidth() / 2 / Settings.PPM,
                sprite.getHeight() / 2 / Settings.PPM
        );

        body.createFixture(shape, 1.0f);

        shape.dispose();
    }

    private void createSprite() {
        sprite = new Sprite(AssetLoader.getTexture("stone.png"));
        sprite.setPosition(
                position.x * Settings.BLOCK_SIZE,
                position.y * Settings.BLOCK_SIZE
        );
        sprite.setSize(
                Settings.BLOCK_SIZE,
                Settings.BLOCK_SIZE
        );
    }

    public void translate(Vector2 v) {
        body.applyForce(v, body.getWorldPoint(v), true);
    }
}
