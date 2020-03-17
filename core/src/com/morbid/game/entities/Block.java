package com.morbid.game.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.morbid.game.AssetLoader;
import com.morbid.game.Settings;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;
import org.w3c.dom.UserDataHandler;

/**
 * Blocks are the basic units of structure in game that can be placed or destroyed.
 * Block is everything you see. Your entire life is built upon them.
 */
public class Block extends Rigidbody {
    private BlockType blockType;
    private Sprite sprite;

    public Block(World world, Vector2 worldPosition, BlockType blockType) {
        super(world, worldPosition);

        this.blockType = blockType;

        createSprite();
        createBody();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public void createBody() {
        BodyDef blockBodyDef = new BodyDef();
        blockBodyDef.type = BodyDef.BodyType.StaticBody;
        blockBodyDef.position.set(
                position.x + sprite.getWidth() / 2 / Settings.PPM,
                position.y + sprite.getHeight() / 2 / Settings.PPM
        );

        body = world.createBody(blockBodyDef);

        PolygonShape blockBox = new PolygonShape();
        blockBox.setAsBox(
                sprite.getWidth() / 2 / Settings.PPM,
                sprite.getHeight() / 2 / Settings.PPM
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockBox;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);

        blockBox.dispose();
    }

    /**
     * Creates sprite
     */
    private void createSprite() {
        sprite = new Sprite(AssetLoader.getTexture(blockType.toString()));
        sprite.setPosition(
                position.x * Settings.BLOCK_SIZE,
                position.y * Settings.BLOCK_SIZE
        );
        sprite.setSize(
                Settings.BLOCK_SIZE,
                Settings.BLOCK_SIZE
        );
    }

    public BlockType getBlockType() {
        return blockType;
    }
}
