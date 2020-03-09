package com.morbid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.morbid.game.entities.Block;
import com.morbid.game.gameworld.Chunk;
import com.morbid.game.gameworld.World;
import com.morbid.game.gameworld.WorldGenerator;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MainGame extends ApplicationAdapter {
	WorldGenerator worldGenerator = new WorldGenerator();
	World world;

	SpriteBatch batch;
	Texture img;
	Texture dirtTexture;
	Sprite dirt;

	@Override
	public void create () {
		AssetLoader.loadTextures();
		world = worldGenerator.generateWorld();

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		dirtTexture = AssetLoader.getTextures().get("stone.png");
		dirt = new Sprite(dirtTexture);
		dirt.setSize(64, 64);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		dirt.draw(batch);
		world.renderChunks(batch, 0, 1, 0, 2);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		AssetLoader.disposeTextures();
	}
}
