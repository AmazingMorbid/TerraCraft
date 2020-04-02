package com.morbid.game;

import com.badlogic.gdx.physics.box2d.World;
import com.morbid.game.entities.Player;
import com.morbid.game.utils.VectorMath;

public class GameManager {
    private static World world;
    private static Player player;
    private static VectorMath.NearChunksResult visibleChunks;

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        GameManager.world = world;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        GameManager.player = player;
    }

    public static VectorMath.NearChunksResult getVisibleChunks() {
        return visibleChunks;
    }

    public static void setVisibleChunks(VectorMath.NearChunksResult visibleChunks) {
        GameManager.visibleChunks = visibleChunks;
    }
}
