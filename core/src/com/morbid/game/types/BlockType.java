package com.morbid.game.types;

public enum BlockType {
    AIR(""),
    DIRT("dirt.png"),
    GRASS("grass.png"),
    STONE("stone.png"),
    COBBLESTONE("cobblestone.png");

    private final String type;

    BlockType(String s) {
        type = s;
    }

    /**
     * Returns filename of block texture based on type
     * @return filename
     */
    @Override
    public String toString() {
        return this.type;
    }
}
