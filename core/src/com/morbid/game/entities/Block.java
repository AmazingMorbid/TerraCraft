package com.morbid.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.morbid.game.AssetLoader;
import com.morbid.game.Settings;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;

/**
 * Blocks are the basic units of structure in game that can be placed or destroyed.
 * Block is everything you see. Your entire life is built upon them.
 */
public class Block extends GameObject {
    private BlockType blockType;

    public Block(int ID, Vector2Int position, BlockType blockType) {
        super(ID, Vector2Int.toVector2(position), Vector2.Zero, Vector2.Zero);

        this.blockType = blockType;
    }

    public Block(int ID, Vector2 position, Vector2 rotation, Vector2 scale, BlockType blockType) {
        super(ID, position, rotation, scale);

        this.blockType = blockType;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(Batch batch) {
        Sprite sprite = new Sprite(AssetLoader.getTexture(blockType.toString()));
        sprite.setPosition(getPosition().x * Settings.BLOCK_SIZE, getPosition().y * Settings.BLOCK_SIZE);
        sprite.setSize(Settings.BLOCK_SIZE, Settings.BLOCK_SIZE);

        sprite.draw(batch);
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }
}
