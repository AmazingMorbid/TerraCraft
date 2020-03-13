package com.morbid.game.types;

import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

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

    public Vector2 toVector2() {
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return String.format("x: %d, y: %d", x, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Vector2Int v = (Vector2Int) obj;

        return Objects.equals(this.x, v.x) && Objects.equals(this.y, v.y);
    }
}
