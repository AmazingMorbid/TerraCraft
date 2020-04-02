package com.morbid.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.morbid.game.AssetLoader;
import com.morbid.game.Settings;
import com.morbid.game.types.EntityMovementState;
import com.morbid.game.types.GameMode;

public class Player extends Rigidbody {
    private Sprite sprite;
    private EntityMovementState movementState = EntityMovementState.FALLING;
    private GameMode gameMode = GameMode.CREATIVE;

    /**
     * Determines if player can fly.
     */
    private boolean canFly = false;

    public Player(World world, Vector2 pos) {
        super(world, pos);

        createSprite();
        createBody();
    }

    @Override
    public void update(float deltaTime) {
        updateMovementState();
    }

    @Override
    public void render(Batch batch) {
        Vector2 newSpritePos = Settings.box2dToSpritePos(body, sprite);
        sprite.setPosition(newSpritePos.x, newSpritePos.y);

        sprite.draw(batch);
    }

    /**
     * Handle keyboard and mouse input.
     */
    public void handleInput() {
        // Move player
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            move(-1);

        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            move(1);

        } else {
            // Decelerate player
            move(0);
        }

        // TODO: Flying on double space press
        // Make player jump on space press or fly up when can fly
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (!canFly) {
                jump();

            } else {
                fly(1);
            }

        } else if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            // TODO: Crouching.
            // Make player crouch on shift press or fly down when can fly
            if (canFly) {
                fly(-1);
            }

        } else {
            // Stop player if no key is pressed when in flying mode
            if (canFly) {
                fly(0);
            }
        }

        // Make player fly on P press
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            setFly(!canFly);
        }
    }

    /**
     * Create Box2D physics body.
     */
    @Override
    public void createBody() {
        // Create BodyDef
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        // Convert pixels to meters
        bodyDef.position.set(
                position.x + sprite.getWidth() / 2 / Settings.PPM,
                position.y + sprite.getHeight() / 2 / Settings.PPM
        );

        body = world.createBody(bodyDef);

        // Create player shape
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(-0.3f, -0.9f);
        vertices[1] = new Vector2(0f, -0.91f);
        vertices[2] = new Vector2(0.3f, -0.9f);
        vertices[3] = new Vector2(0.3f, 0.9f);
        vertices[4] = new Vector2(-0.3f, 0.9f);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
//        shape.setAsBox(
//                sprite.getWidth() / 2 / Settings.PPM,
//                sprite.getHeight() / 2 / Settings.PPM
//        );

        body.createFixture(shape, 1.0f);

        shape.dispose();
    }

    /**
     * Create a sprite, set its position and size.
     */
    private void createSprite() {
        sprite = new Sprite(AssetLoader.getTexture("grass.png"));
        sprite.setPosition(
                position.x * Settings.BLOCK_SIZE * Settings.PLAYER_SCALE.x,
                position.y * Settings.BLOCK_SIZE * Settings.PLAYER_SCALE.y
        );
        sprite.setSize(
                Settings.BLOCK_SIZE * Settings.PLAYER_SCALE.x,
                Settings.BLOCK_SIZE * Settings.PLAYER_SCALE.y
        );
    }

    /**
     * Move player in desired direction.
     * @param xDirection of movement. 1 for left, -1 for right.
     */
    private void move(int xDirection) {
        float maxVelocity =  canFly ? Settings.PLAYER_MAXIMUM_FLYING_VELOCITY : Settings.PLAYER_MAXIMUM_VELOCITY;

        if (xDirection == 0) {
            // Stop player when no key is being pressed.
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));

        }
        else if (!checkIsTouchingWall(xDirection) && gameMode != GameMode.SPECTATOR) {
            // Allow walking only when body is not touching walls (to prevent sticking to them)
            body.setLinearVelocity(new Vector2(maxVelocity * xDirection, body.getLinearVelocity().y));
        }
    }

    /**
     * Make player jump
     */
    private void jump() {
        /*
        2as = Vf^2 - Vi^2
        Vi = sqrt(2as)
         */
        if (checkIsOnGround()) {
            body.setLinearVelocity(
                    body.getLinearVelocity().x,
                    (float) Math.sqrt(2 * -world.getGravity().y * Settings.PLAYER_JUMP_HEIGHT)
            );
        }
    }

    /**
     * Move player in desired y direction.
     * @param yDirection of movement. 1 for up, -1 for down.
     */
    private void fly(int yDirection) {
        if (canFly) {
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, Settings.PLAYER_MAXIMUM_FLYING_VELOCITY * yDirection));
        }
    }

    private void updateMovementState() {
        if (canFly) {
            movementState = EntityMovementState.FLYING;

        } else if (body.getLinearVelocity().y > 0.1f) {
            movementState = EntityMovementState.JUMPING;

        } else if (body.getLinearVelocity().y < -0.1f) {
            movementState = EntityMovementState.FALLING;

        } else {
            movementState = EntityMovementState.STANDING;
        }
    }

    /**
     * Set canFly parameter.
     * It also applies downward force so player will fall moment after he exits the flying mode.
     * @param canFly
     */
    private void setFly(boolean canFly) {
        this.canFly = canFly;

        if (!this.canFly) {
            body.setGravityScale(1.0f);

        } else {
            body.setGravityScale(0.0f);
            body.applyForce(0, -10, 0, 1, true);
        }
    }

    private void setGameMode(GameMode gameMode) {
        // SPECTATOR MODE gives you the ability to fly through walls, so set the body type to Kinematic
        if (gameMode == GameMode.SPECTATOR && this.gameMode != GameMode.SPECTATOR) {
            changeBodyType(BodyDef.BodyType.KinematicBody);

        } else {
            changeBodyType(BodyDef.BodyType.DynamicBody);
        }

        this.gameMode = gameMode;
    }

    private void changeBodyType(BodyDef.BodyType bodyType) {
        body.setActive(false);
        body.setType(bodyType);
        body.setActive(true);
    }

    /**
     * Check if player is touching the ground.
     * It sets player's isOnGround accordingly.
     * @return true when player is touching the ground or false when not.
     */
    private boolean checkIsOnGround() {
        boolean[] rays = {false, false};
        float halfWidth = Settings.PLAYER_SCALE.x / 2;

        Vector2 leftRayEnd = new Vector2(
                body.getPosition().x - halfWidth,
                body.getPosition().y - Settings.PLAYER_SCALE.y / 2 - 0.05f
        );

        Vector2 rightRayEnd = new Vector2(
                body.getPosition().x + halfWidth,
                body.getPosition().y - Settings.PLAYER_SCALE.y / 2 - 0.05f
        );

        // Shoot left ray
        shootRay(rays, 0, body.getPosition(), leftRayEnd, 1.0f);

        // Shoot right ray
        shootRay(rays, 1, body.getPosition(), rightRayEnd, 1.0f);

        // If any of those rays are true, player is touching the ground.
        return rays[0] || rays[1];
    }

    /**
     * Check if player is touching wall on specified side.
     * @param xSide -1 fo left, 1 for right.
     * @return true when player is touching the wall or false when not.
     */
    private boolean checkIsTouchingWall(int xSide) {
        float halfWidth = Settings.PLAYER_SCALE.x / 2;
        float halfHeight = Settings.PLAYER_SCALE.y / 2;

        Vector2 rayStart = new Vector2(
            body.getPosition().x,
            body.getPosition().y - halfHeight
        );

        Vector2 leftSideRayEnd = new Vector2(
                body.getPosition().x - halfWidth - 0.05f,
                body.getPosition().y - halfHeight
        );

        Vector2 rightSideRayEnd = new Vector2(
                body.getPosition().x + halfWidth + 0.05f,
                body.getPosition().y - halfHeight
        );


        if (xSide == -1) {
            // Shoot ray to check if player is touching left wall.

            return shootRay(rayStart, leftSideRayEnd, 1.0f);

        } else if (xSide == 1) {
            // Shoot ray to check if player is touching right wall.

            return shootRay(rayStart, rightSideRayEnd, 1.0f);
        }

        throw new IllegalArgumentException("Passed wrong xSide. It should be either -1 or 1");
    }

    /**
     * Shoot ray and save result to results.
     * @param results array where results are stored.
     * @param index to save results into (results[index]).
     * @param start position.
     * @param end position.
     * @param threshold true: fraction < threshold .
     */
    private void shootRay(boolean[] results, int index, Vector2 start, Vector2 end, float threshold) {
        world.rayCast(
                ((fixture, point, normal, fraction) -> {
                    results[index] = fraction < threshold;

                    return 1;
                }),
                start,
                end
        );
    }

    /**
     * Shoot ray.
     * @param start position.
     * @param end position.
     * @param threshold true: fraction < threshold.
     * @return true if ray hits anything.
     */
    private boolean shootRay(Vector2 start, Vector2 end, float threshold) {
        final boolean[] result = {false};

        shootRay(result, 0, start, end, threshold);

        return result[0];
    }
}
