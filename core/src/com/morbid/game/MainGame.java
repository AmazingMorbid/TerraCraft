package com.morbid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.morbid.game.entities.Block;
import com.morbid.game.entities.CameraComponent;
import com.morbid.game.entities.Player;
import com.morbid.game.gameworld.Chunk;
import com.morbid.game.gameworld.World;
import com.morbid.game.gameworld.WorldGenerator;
import com.morbid.game.types.BlockType;
import com.morbid.game.types.Vector2Int;
import com.morbid.game.utils.DebugTools;
import com.morbid.game.utils.VectorMath;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


public class MainGame extends ApplicationAdapter {
	// Camera
	CameraComponent camera;

	// Player
	Player player;

	// World
	WorldGenerator worldGenerator = new WorldGenerator();
	World world;

	SpriteBatch batch;

	@Override
	public void create () {
		// Assets
		AssetLoader.loadTextures();

		// Player
		player = new Player(200, 60);

		// Camera
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new CameraComponent(Settings.CAMERA_VIEWPORT_WIDTH, Settings.CAMERA_VIEWPORT_HEIGHT * (h / w));
		camera.setAnchor(player);
		camera.update();

		// World
		world = worldGenerator.generateWorld();

		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		handleInput();
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		// World
		// Get chunks near player
		VectorMath.NearChunksResult visibleChunksIndexes = VectorMath.getChunksNearPlayer(camera, player.getPosition());
		world.renderChunks(
				batch,
				camera,
				visibleChunksIndexes.startX,
				visibleChunksIndexes.endX,
				visibleChunksIndexes.startY,
				visibleChunksIndexes.endY
		);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		AssetLoader.disposeTextures();
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.translate(-3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.translate(3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.translate(0, 3);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.translate(0, -3);
		}
	}
}
