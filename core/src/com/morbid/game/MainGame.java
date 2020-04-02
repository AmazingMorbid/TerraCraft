package com.morbid.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.morbid.game.entities.Block;
import com.morbid.game.entities.CameraComponent;
import com.morbid.game.entities.Player;
import com.morbid.game.gameworld.Chunk;
import com.morbid.game.gameworld.WorldMap;
import com.morbid.game.gameworld.WorldGenerator;
import com.morbid.game.types.Vector2Int;
import com.morbid.game.types.WorldType;
import com.morbid.game.utils.DebugTools;
import com.morbid.game.utils.VectorMath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainGame extends ApplicationAdapter {
	// Camera
	CameraComponent camera;
	Viewport viewport;
	Matrix4 cameraBox2D;

	// World
	WorldGenerator worldGenerator;
	WorldMap worldMap;

	Box2DDebugRenderer debugRenderer;

	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();

		// Assets
		AssetLoader.loadTextures();

		// World
		worldGenerator = new WorldGenerator();

		World.setVelocityThreshold(0.0f);
		GameManager.setWorld(new World(new Vector2(0, -17), true));

		// Camera
		camera = new CameraComponent();
		viewport = new ExtendViewport(Settings.MIN_VIEWPORT_WIDTH, Settings.MIN_VIEWPORT_HEIGHT, camera);

		camera.update();
		viewport.apply();

		debugRenderer = new Box2DDebugRenderer();

		// Player
		GameManager.setPlayer(new Player(GameManager.getWorld(), new Vector2((float) Settings.WORLD_SIZE.x / 2, Settings.WORLD_SIZE.y)));
		camera.attachPlayer(GameManager.getPlayer());

		// World map
		worldMap = worldGenerator.generateWorld(GameManager.getWorld(), WorldType.DEFAULT);

		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {


				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				onMouseClick(button);

				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				return false;
			}
		});
	}

	@Override
	public void render () {

		camera.update();
		cameraBox2D = new Matrix4(camera.combined);
		cameraBox2D.scl(Settings.PPM);

		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.begin();

		// Player
		GameManager.getPlayer().render(batch);

		// World
		loadChunks();
		renderMap();
		updateChunks(Gdx.graphics.getDeltaTime());

		// Debug render
//		debugRenderer.render(GameManager.getWorld(), cameraBox2D);

		batch.end();

		// handle
		handleInput();

		// Physics
		updatePhysics(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	private void updatePhysics(float deltaTime) {
		GameManager.getWorld().step(1/60f, 6, 2);
		GameManager.getPlayer().update(deltaTime);
	}

	@Override
	public void dispose () {
		batch.dispose();
		debugRenderer.dispose();
		GameManager.getWorld().dispose();
		AssetLoader.disposeTextures();
	}

	private void handleInput() {
		GameManager.getPlayer().handleInput();

		if (Gdx.input.isButtonJustPressed(Input.Keys.L)) {
			loadChunks();
		}
	}

	private void onMouseClick(int button) {
		if (button == Input.Buttons.LEFT) {
			Vector2 mouseScreenPosition = new Vector2(
					Gdx.input.getX(),
					Gdx.input.getY()
			);
			Vector2 mousePixelPosition = viewport.unproject(mouseScreenPosition);
			Vector2Int mouseCellPosition = new Vector2Int(
					(int)mousePixelPosition.x / Settings.PPM,
					(int) mousePixelPosition.y / Settings.PPM
			);
			Vector2Int chunkPosition = VectorMath.getChunkAt(mouseCellPosition.toVector2());

			Chunk chunk = worldMap.getChunks()[chunkPosition.x][chunkPosition.y];

			if (chunk != null && chunk.getBlockMap() != null) {


				Block block = chunk.getBlockMap().get(mouseCellPosition);

				if (block != null) {
					GameManager.getWorld().destroyBody(block.body);
					worldMap.destroyBlock(mouseCellPosition);
					chunk.destroyBlock(mouseCellPosition);
				}

				System.out.println(mouseCellPosition.toString());
				System.out.println(chunkPosition.toString());
			}
		}
	}

	/**
	 * Load chunks.
	 */
	private void loadChunks() {
		// Get chunks in player's range.
		VectorMath.NearChunksResult visibleChunksIndexes = VectorMath.getChunksNearPlayer(
				camera,
				GameManager.getPlayer().body.getPosition()
		);

		// Unload unused chunks.
		compareAndUnloadChunks(visibleChunksIndexes, GameManager.getVisibleChunks());

		worldMap.loadChunks(
				visibleChunksIndexes.startX,
				visibleChunksIndexes.endX
		);

		// Store currently loaded chunks, so they can be compared in next call.
		GameManager.setVisibleChunks(visibleChunksIndexes);
	}

	/**
	 * Update chunks (and blocks inside them)
	 * @param deltaTime
	 */
	private void updateChunks(float deltaTime) {
		for (Chunk[] chunks : worldMap.getChunks()) {
			for (Chunk chunk : chunks) {
				chunk.update(deltaTime);
			}
		}
	}

	/**
	 * Compares chunks in player's range with previous ones and unloads unused ones.
	 * @param chunksNow chunks currently in range
	 * @param chunksLast previous chunks
	 */
	private void compareAndUnloadChunks(VectorMath.NearChunksResult chunksNow, VectorMath.NearChunksResult chunksLast) {
		// First run will result in last chunks being null, so return
		if (chunksLast == null) {
			return;
		}

		if (chunksNow.startX > chunksLast.startX) {
			worldMap.unloadChunks(chunksLast.startX);
		} else if (chunksNow.endX < chunksLast.endX) {
			worldMap.unloadChunks(chunksLast.endX);
		}
	}

	/**
	 * Render visible part of map + some offset
	 * (currently it is hardcoded to one additional chunk in every dimension)
	 */
	private void renderMap() {
		// Get chunks near player
		VectorMath.NearChunksResult visibleChunksIndexes = VectorMath.getChunksNearPlayer(camera, GameManager.getPlayer().body.getPosition());

		// Render chunks
		worldMap.renderChunks(
				batch,
				visibleChunksIndexes.startX,
				visibleChunksIndexes.endX,
				visibleChunksIndexes.startY,
				visibleChunksIndexes.endY
		);
	}
}
