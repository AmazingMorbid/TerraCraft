package com.morbid.game.types;

public enum BlockType {
    AIR(""),
    DIRT("dirt"),
    GRASS("grass"),
    STONE("stone"),
    COBBLESTONE("cobblestone");

    private final String type;

    BlockType(String s) {
        type = s;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
