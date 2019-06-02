package com.jordanrifaey.lunarlander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static com.jordanrifaey.lunarlander.AssMan.*;
import static com.jordanrifaey.lunarlander.Constants.*;

public class MenuScreen extends InputAdapter implements Screen {

    private Application app;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Music music;
    private Stage stage;
    private Table tblMenu;
    private Table tblSettings;
    private Sound click;
    private TextButton btnPlay;
    private TextButton btnRestart;
    private TextButton btnSettings;
    private TextButton btnExit;

    MenuScreen(Application application) {
        app = application;
        stage = new Stage();
        stage.addActor(new Image((Texture)manager.get(bgTexture)));
        Gdx.input.setInputProcessor(stage);
        music = manager.get(menuMusic, Music.class);
        if (app.musicEnabled) music.play();
        click = manager.get(buttonClick);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        tblMenu = new Table();
        tblMenu.setFillParent(true);
        stage.addActor(tblMenu);
        if (debug) tblMenu.debug();

        font = manager.get(fontPath);
        font.getData().setScale(FONT_SCALE);
        TextureAtlas textureAtlas = manager.get(texturePack);

        Skin skin = new Skin();
        skin.addRegions(textureAtlas);
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button-selected");
        btnPlay = new TextButton(MENU_LABEL, buttonStyle);
        tblMenu.add(btnPlay);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play(0.5f);
                app.showPlayScreen();
            }
        });
        tblMenu.row();
        btnRestart = new TextButton("Restart", buttonStyle);
        btnRestart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play(0.5f);
                app.restartGame();
            }
        });

        btnSettings = new TextButton("Settings", buttonStyle);
        tblMenu.row();
        tblMenu.add(btnSettings);
        btnSettings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play(0.5f);
                tblMenu.setVisible(false);
                tblSettings.setVisible(true);
            }
        });

        tblSettings = new Table();
        stage.addActor(tblSettings);
        tblSettings.setFillParent(true);
        tblSettings.setVisible(false);

        TextButton btnBack = new TextButton("Back", buttonStyle);
        btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play(0.5f);
                tblSettings.setVisible(false);
                tblMenu.setVisible(true);
            }
        });
        tblSettings.add(btnBack);

        //Toggle Music Button
        String toggleMusicTxt;
        if (app.musicEnabled) toggleMusicTxt = "Disable Music";
        else toggleMusicTxt = "Enable Music";
        final TextButton toggleMusic = new TextButton(toggleMusicTxt, buttonStyle);
        toggleMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play(0.5f);
                if (app.musicEnabled) {
                    app.musicEnabled = false;
                    toggleMusic.setText("Enable Music");
                    music.pause();
                } else {
                    app.musicEnabled = true;
                    toggleMusic.setText("Disable Music");
                    music.play();
                }
            }
        });
        tblSettings.row();
        tblSettings.add(toggleMusic);


        //Exit button
//        btnExit = new TextButton("Exit", buttonStyle);
//        tblMenu.row();
//        tblMenu.add(btnExit);
//        btnExit.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                click.play(0.5f);
//                Gdx.app.exit();
//            }
//        });
    }

    @Override
    public void show() {
        if (app.musicEnabled) music.play();
        Gdx.input.setInputProcessor(stage);
        if (app.gameStarted) {
            btnPlay.setText("Resume");
            tblMenu.clearChildren();
            tblMenu.add(btnPlay);
            tblMenu.row();
            tblMenu.add(btnRestart);
            tblMenu.row();
            tblMenu.add(btnSettings);
            tblMenu.row();
            tblMenu.add(btnExit);
        }
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
        font.dispose();
        renderer.dispose();
        stage.dispose();
    }


//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
//        System.out.println(worldTouch);
//        if (worldTouch.dst(MENU_BUTTON_CENTER) < MENU_BUTTON_HEIGHT) {
//            music.stop();
//            app.showPlayScreen();
//        }
//
//        return true;
//    }
}
