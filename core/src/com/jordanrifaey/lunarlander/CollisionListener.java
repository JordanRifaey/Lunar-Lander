package com.jordanrifaey.lunarlander;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {

    World world;
    Texture texture;
    boolean enemyHit = false;

    public CollisionListener(Texture texture, World world) {
        this.world = world;
        this.texture = texture;
    }

    @Override
    public void beginContact(Contact contact) {
        //System.out.println(contact.getFixtureA().getBody().getUserData() + " was hit by a " + contact.getFixtureB().getBody().getUserData());

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getBody().getUserData() == null || fb.getBody().getUserData() == null) return;

//        if ((fa.getBody().getUserData() instanceof Player) && (fb.getBody().getUserData() instanceof Enemy)) {
//            Player player = (Player)fa.getBody().getUserData();
//            player.die();
//        }

        //if (contact.getFixtureA().getBody().getUserData() == "ENEMY" && contact.getFixtureB().getBody().getUserData() == "MISSILE" && enemyHit == false) {
        //x = new Enemy(texture, world);
        //enemyHit = true;
        //}
    }

    @Override
    public void endContact(Contact contact) {
        //System.out.println("endContact");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
