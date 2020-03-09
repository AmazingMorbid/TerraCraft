package com.morbid.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.morbid.game.types.BlockType;

/**
 * Blocks are the basic units of structure in game that can be placed or destroyed.
 * Block is everything you see. Your entire life is built upon them.
 */
public class Block extends GameObject {
    private BlockType blockType;

    public Block() {
    }

    public Block(int ID, Vector2 position, Vector2 rotation, Vector2 scale) {
        super(ID, position, rotation, scale);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render() {

    }
}
