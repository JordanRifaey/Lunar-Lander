package com.jordanrifaey.lunarlander.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jordanrifaey.lunarlander.Application;

import static com.jordanrifaey.lunarlander.AssMan.*;
import static com.jordanrifaey.lunarlander.Constants.*;

public class Player extends Entity {

    private long nextUpdate;
    private long updateDelay = 30;
    public int fuel = 1000;
    private Music rocketSound;
    private boolean rocketPlaying;

    public Player(World world) {
        super(world, GAME_WORLD_WIDTH / 10, 20);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2, 2);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
        rocketSound = manager.get(rocket);
        rocketSound.setLooping(true);
        //sprite = new Sprite(texture, 81, 22, 9, 10);
        //sprite.setOrigin(0f,0f);
        //body.setTransform(new Vector2(30, 0), body.getAngle());
        //body.setGravityScale(0f);
        //body.applyForceToCenter(50000, 0, false);

        nextUpdate = System.currentTimeMillis() + updateDelay;
    }

    public void update(boolean  thrustLeft, boolean thrustRight, boolean thrustUp) {
        if (System.currentTimeMillis() >= nextUpdate) {
            nextUpdate = System.currentTimeMillis() + updateDelay;
            //System.out.println("applied force to player! System.currentTimeMillis(): " + (int) System.currentTimeMillis() + " nextUpdate: " + nextUpdate);

            if (fuel > 0 && (thrustUp || (Gdx.input.isKeyPressed(Input.Keys.SPACE)))) {
                fuel--;
                // gets x force based on angle
                float x = (float) Math.sin(body.getAngle() - Math.PI); // minus PI as objects start off facing right
                // gets y force based on angle
                float y = (float) Math.cos(body.getAngle());
                //apply force to a point on body (will create rotational force )
                body.applyForce(new Vector2(
                                body.getMass() * (x * playerVerticalThrusterForce),//x force to apply
                                body.getMass() * (y * playerVerticalThrusterForce)), //y force to apply
                        // apply force to body at 0.5f(halfway for 1f wide object) x and -5 y
                        body.getWorldPoint(new Vector2(0f, -5)), true);
                if (!rocketPlaying) {
                    rocketSound.play();
                    rocketPlaying = true;
                }
            } else if (rocketPlaying) {
                rocketSound.pause();
                rocketPlaying = false;
            }
            if (thrustLeft || Gdx.input.isKeyPressed(Input.Keys.A)) {
                body.applyAngularImpulse(playerSideThrusterForce, false);
            }
            if (thrustRight || Gdx.input.isKeyPressed(Input.Keys.D)) {
                body.applyAngularImpulse(-playerSideThrusterForce, false);
            }
        }
        //sprite.draw(spriteBatch);
//        if (body.getPosition().x > 0 && (body.getPosition().x + sprite.getWidth()) < cam.viewportWidth) {
//            body.applyForceToCenter(Gdx.input.getAccelerometerX() * -accelerationSpeed, 0f, true);
//            if (body.getLinearVelocity().x >= topSpeed)
//                body.setLinearVelocity(topSpeed, 0);
//            if (body.getLinearVelocity().x <= -topSpeed)
//                body.setLinearVelocity(-topSpeed, 0);
//        } else {
//            body.setLinearVelocity(body.getLinearVelocity().x * -1, 0);
//        }

        //sprite.setPosition(body.getPosition().x, body.getPosition().y);
        //sprite.setRotation((float)Math.toDegrees((double)body.getAngle()));

    }

    public String getFuel() {
        return String.valueOf(fuel);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
