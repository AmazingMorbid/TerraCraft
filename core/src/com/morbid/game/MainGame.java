package com.morbid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MainGame extends ApplicationAdapter {
	AssetLoader assets = new AssetLoader();

	SpriteBatch batch;
	Texture img;
	Texture dirtTexture;
	Sprite dirt;

	@Override
	public void create () {
		assets.loadTextures();

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		dirtTexture = assets.getTextures().get("cobblestone.png");
		dirt = new Sprite(dirtTexture);
		dirt.setSize(64, 64);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		dirt.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		dirtTexture.dispose();
	}
}
