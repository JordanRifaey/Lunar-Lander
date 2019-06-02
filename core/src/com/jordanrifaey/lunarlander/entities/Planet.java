package com.jordanrifaey.lunarlander.entities;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.physics.box2d.CircleShape;
        import com.badlogic.gdx.physics.box2d.PolygonShape;
        import com.badlogic.gdx.physics.box2d.World;

        import static com.jordanrifaey.lunarlander.Constants.PPM;

public class Planet extends Entity {

    private long updateDelay = 30;
    private int force = 3;

    public Planet(World world, float radius, float x, float y) {
        super(world, x, y);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
        //sprite = new Sprite(texture, 81, 22, 9, 10);
        //sprite.setOrigin(0f,0f);
        //body.setTransform(new Vector2(30, 0), body.getAngle());
        body.setGravityScale(0f);
        //body.applyForceToCenter(50000, 0, false);
    }

    public void update() {
    }
}
