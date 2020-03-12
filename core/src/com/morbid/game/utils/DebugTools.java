package com.morbid.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class DebugTools {
    private static ShapeRenderer renderer = new ShapeRenderer();

    public static void line(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix) {
        Gdx.gl.glLineWidth(lineWidth);
        renderer.setProjectionMatrix(projectionMatrix);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.PINK);
        renderer.line(start, end);
        renderer.end();
        Gdx.gl.glLineWidth(1);
    }

    public static void rect(float x, float y, float w, float h, int lineWidth, Color color, Matrix4 projectionMatrix) {
        Gdx.gl.glLineWidth(lineWidth);
        renderer.setProjectionMatrix(projectionMatrix);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(color);
        renderer.rect(x, y, w, h);
        renderer.end();
        Gdx.gl.glLineWidth(1);
    }

    public static void circle(Vector2 pos, Matrix4 projectionMatrix) {
        renderer.setProjectionMatrix(projectionMatrix);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.PINK);
        renderer.circle(pos.x, pos.y, 5);
        renderer.end();
    }
}
