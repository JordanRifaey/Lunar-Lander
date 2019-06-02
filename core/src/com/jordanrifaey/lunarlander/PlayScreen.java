package com.jordanrifaey.lunarlander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jordanrifaey.lunarlander.entities.Planet;
import com.jordanrifaey.lunarlander.entities.Player;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;

import static com.jordanrifaey.lunarlander.AssMan.*;
import static com.jordanrifaey.lunarlander.Constants.*;

public class PlayScreen implements Screen {

    private OrthographicCamera cam;
    private ExtendViewport viewport;
    private SpriteBatch batch;
    private World world;
    private Player player;
    private Planet planet;
    private Box2DDebugRenderer debugRenderer;
    private Stage stage;
    private Label lbl;
    private Application app;
    private Music music;
    private Sound click;
    TextButton btnMenu;
    Button thrustLeft;
    Button thrustRight;
    Button thrustUp;

    public PlayScreen(Application application) {
        this.app = application;
        stage = new Stage();
        stage.addActor(new Image((Texture)manager.get(bgTexture)));
        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        viewport = new ExtendViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(X_GRAVITY, Y_GRAVITY), false);
        //world.setContactListener(new CollisionListener(texture, world));
        player = new Player(world);
        planet = new Planet(world, 20, (Gdx.graphics.getWidth() / PPM) / 2, -10);
        music = manager.get(gameMusic);
        click = manager.get(buttonClick);
        batch.setProjectionMatrix(cam.combined);
        BitmapFont font = manager.get(fontPath);

        Table table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
        table.top();
        if (debug) table.debug();

        Skin skin = new Skin();
        TextureAtlas textureAtlas = manager.get(texturePack);
        skin.addRegions(textureAtlas);
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = font;
        lbl = new Label("", labelStyle);
        table.add(lbl).expandX().pad(10).align(Align.left).colspan(2);

        TextButtonStyle menuBtnStyle = new TextButton.TextButtonStyle();
        menuBtnStyle.font = font;
        menuBtnStyle.up = skin.getDrawable("button");
        menuBtnStyle.down = skin.getDrawable("button-selected");
        btnMenu = new TextButton("Menu", menuBtnStyle);
        table.add(btnMenu).expandX().align(Align.right);
        btnMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play(0.5f);
                app.showMenuScreen();
            }
        });

        TextButtonStyle leftBtnStyle = new TextButton.TextButtonStyle();
        leftBtnStyle.up = skin.getDrawable("left-arrow");
        leftBtnStyle.down = skin.getDrawable("left-arrow-down");
        thrustLeft = new Button(leftBtnStyle);
        table.row();
        table.add(thrustLeft).align(Align.bottomLeft).width(200).height(200).expandY().pad(40);

        TextButtonStyle rightBtnStyle = new TextButton.TextButtonStyle();
        rightBtnStyle.up = skin.getDrawable("right-arrow");
        rightBtnStyle.down = skin.getDrawable("right-arrow-down");
        thrustRight = new Button(rightBtnStyle);
        table.add(thrustRight).expandX().align(Align.bottomLeft).width(200).height(200).expandY().pad(40).padLeft(80);

        TextButtonStyle upBtnStyle = new TextButton.TextButtonStyle();
        upBtnStyle.up = skin.getDrawable("radio-off");
        upBtnStyle.down = skin.getDrawable("slider-knob");
        thrustUp = new Button(upBtnStyle);
        table.add(thrustUp).expandX().align(Align.bottomRight).width(200).height(200).expandY().colspan(2).pad(40);
        //thrustUp.setTransform(true);
    }

    @Override
    public void show() {
        app.gameStarted = true;
        if (app.musicEnabled) music.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1f / 60f, 6, 2);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        player.update(thrustLeft.isPressed(), thrustRight.isPressed(), thrustUp.isPressed());

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            cam.position.set(player.getPosition().x - viewport.getWorldWidth() / 2, player.getPosition().y - Gdx.graphics.getHeight() / 2, 0);
            System.out.println("viewport.getWorldWidth(): " + viewport.getWorldWidth());
            cam.update();
            System.out.println(cam.position);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            float newGameWorldWidth = GAME_WORLD_WIDTH * 3;
            cam.setToOrtho(false, newGameWorldWidth, newGameWorldWidth * (Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight()));
            cam.update();
            System.out.println(cam.position);
        }
        lbl.setText("Fuel: " + player.getFuel());
        stage.draw();
        debugRenderer.render(world, cam.combined);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        GAME_WORLD_WIDTH = Gdx.graphics.getWidth() / PPM;
        GAME_WORLD_HEIGHT = Gdx.graphics.getHeight() / PPM;
        viewport.update(width, height);
        cam.setToOrtho(false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        stage.getViewport().setWorldSize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        music.pause();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        debugRenderer.dispose();
        stage.dispose();
    }
}
