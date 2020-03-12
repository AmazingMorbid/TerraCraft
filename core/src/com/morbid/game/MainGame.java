package com.morbid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.morbid.game.entities.CameraComponent;
import com.morbid.game.entities.Player;
import com.morbid.game.gameworld.WorldMap;
import com.morbid.game.gameworld.WorldGenerator;
import com.morbid.game.types.WorldType;
import com.morbid.game.utils.VectorMath;


public class MainGame extends ApplicationAdapter {
	// Camera
	CameraComponent camera;
	Viewport viewport;
	Matrix4 cameraBox2D;

	// Player
	Player player;

	// World
	WorldGenerator worldGenerator;
	WorldMap worldMap;
	World world;

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
		world = new World(new Vector2(0, -10), true);

		// Player
		player = new Player(new Vector2(1000, 1000));

		// Camera
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new CameraComponent();
		viewport = new ExtendViewport(Settings.MIN_VIEWPORT_WIDTH, Settings.MIN_VIEWPORT_HEIGHT, camera);

		camera.setAnchor(player);
		camera.update();
		viewport.apply();

		debugRenderer = new Box2DDebugRenderer();


		// World map
		worldMap = worldGenerator.generateWorld(world, WorldType.FLAT);

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

		// World
		renderMap();

		// Debug render
		debugRenderer.render(world, cameraBox2D);

		batch.end();

		// handle
		handleInput();

		// Physics
		updatePhysics();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	private void updatePhysics() {
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		debugRenderer.dispose();
		world.dispose();
		AssetLoader.disposeTextures();
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.position.add(-3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.position.add(3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.position.add(0, 3);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.position.add(0, -3);
		}
	}

	/**
	 * Render visible part of map + some offset
	 * (currently it is hardcoded to one additional chunk in every dimension)
	 */
	private void renderMap() {
		// Get chunks near player
		VectorMath.NearChunksResult visibleChunksIndexes = VectorMath.getChunksNearPlayer(camera, player.position);

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
