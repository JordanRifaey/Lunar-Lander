package com.jordanrifaey.lunarlander.entities;

import com.badlogic.gdx.physics.box2d.*;

abstract class Entity {

    Body body;
    FixtureDef fixtureDef;

    Entity(World world, float x, float y) {
        createBody(world, x, y);
    }

    private void createBody(World world, float x, float y) {
        BodyDef bDef = new BodyDef();
        bDef.fixedRotation = false;
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(x, y);
        body = world.createBody(bDef);

        fixtureDef = new FixtureDef();
        fixtureDef.density = 0.3f;
        fixtureDef.restitution = 0.2f;
        fixtureDef.friction = 0.8f;

        body.setAngularDamping(1f);
        //body.setLinearDamping(1f);
    }

//    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("bodies/bodies.json"));
//    sprite =new Sprite(texture, 81,22,9,10);
//
//    BodyDef playerBodyDef = new BodyDef();
//    playerBodyDef.type =BodyDef.BodyType.DynamicBody;
//    body = world.createBody(playerBodyDef);
//    body.setUserData(this);
//    FixtureDef fixtureDef = new FixtureDef();
//    fixtureDef.density =.5f;
//    fixtureDef.friction =0.4f;
//    fixtureDef.restitution =0.6f;
//    //fixtureDef.friction = 0f;
//    loader.attachFixture(body,type,fixtureDef,sprite.getWidth());
//    body.setTransform(newVector2(30,0),body.getAngle());
//    body.setGravityScale(0f);
//    body.applyForceToCenter(50000,0,false);
//    sprite.setOrigin(0f,0f);

}
