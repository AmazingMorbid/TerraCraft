package com.morbid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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

		World.setVelocityThreshold(0.0f);
		world = new World(new Vector2(0, -17), true);

		// Camera
		camera = new CameraComponent();
		viewport = new ExtendViewport(Settings.MIN_VIEWPORT_WIDTH, Settings.MIN_VIEWPORT_HEIGHT, camera);

		camera.update();
		viewport.apply();

		debugRenderer = new Box2DDebugRenderer();

		// Player
		player = new Player(world, new Vector2(0, 70));
		camera.attachPlayer(player);

		// World map
		worldMap = worldGenerator.generateWorld(world, WorldType.SINUS);

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
		player.render(batch);

		// World
		renderMap();

		// Debug render
//		debugRenderer.render(world, cameraBox2D);

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
		world.step(1/60f, 6, 2);
		player.update(deltaTime);
	}

	@Override
	public void dispose () {
		batch.dispose();
		debugRenderer.dispose();
		world.dispose();
		AssetLoader.disposeTextures();
	}

	private void handleInput() {
		player.handleInput();
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
			Block block = chunk.getBlockMap().get(mouseCellPosition);

			if (block != null) {
				world.destroyBody(block.body);
				worldMap.destroyBlock(mouseCellPosition);
				chunk.destroyBlock(mouseCellPosition);
			}

			System.out.println(mouseCellPosition.toString());
			System.out.println(chunkPosition.toString());
		}
	}

	/**
	 * Render visible part of map + some offset
	 * (currently it is hardcoded to one additional chunk in every dimension)
	 */
	private void renderMap() {
		// Get chunks near player
		VectorMath.NearChunksResult visibleChunksIndexes = VectorMath.getChunksNearPlayer(camera, player.body.getPosition());

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
