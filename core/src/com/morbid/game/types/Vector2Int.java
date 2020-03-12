package com.morbid.game.types;

import com.badlogic.gdx.math.Vector2;

public class Vector2Int {
    // x component of vector
    public int x;

    // y component of vector
    public int y;

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2Int(Vector2Int xy) {
        this.x = xy.x;
        this.y = xy.y;
    }

    /**
     * Converts Vector2Int to Vector2
     * @param v
     * @return converted Vector2
     */
    public static Vector2 toVector2(Vector2Int v) {
        return new Vector2(v.x, v.y);
    }
}
