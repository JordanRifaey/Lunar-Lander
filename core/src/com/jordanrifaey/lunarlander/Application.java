package com.jordanrifaey.lunarlander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import static com.jordanrifaey.lunarlander.AssMan.manager;

public class Application extends Game {

    private Screen menuScreen;
    private Screen playScreen;
    boolean musicEnabled = false;
    boolean gameStarted = false;

    @Override
    public void create() {
//        MediaPlayer player;
//        player = new MediaPlayer(new Media("android/assets/video/apollo_11_liftoff.mpg"));
//        player.setAutoPlay(true);
        System.out.println("loading assets...");
        AssMan am = new AssMan();
        System.out.println("loading fonts...");
        am.loadFonts();
        System.out.println("loading images...");
        am.loadImages();
        System.out.println("loading music...");
        am.loadMusic();
        System.out.println("loading sounds...");
        am.loadSounds();
        System.out.println("finishing loading...");
        manager.finishLoading();
        System.out.println("assets loaded!");
        menuScreen = new MenuScreen(this);
        playScreen = new PlayScreen(this);
        showMenuScreen();
    }

    void showMenuScreen() {
        setScreen(menuScreen);
    }

    void showPlayScreen() {
        setScreen(playScreen);
    }

    void restartGame() {
        playScreen.dispose();
        playScreen = new PlayScreen(this);
        showPlayScreen();
    }
}