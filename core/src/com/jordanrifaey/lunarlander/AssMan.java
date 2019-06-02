package com.jordanrifaey.lunarlander;

import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssMan {

    public static final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();

    // Textures
    public static final String texturePack = "gui/star-soldier/skin/star-soldier-ui.atlas";
    public static final String bgTexture = "bg.jpg";

    public void loadImages() {
        manager.load(texturePack, TextureAtlas.class);
        manager.load(bgTexture, Texture.class);
    }

    //Music
    public static final String menuMusic = "music/Earthshake.mp3";
    public static final String gameMusic = "music/The Shape of Things to Come.mp3";

    public void loadMusic() {
        manager.load(menuMusic, Music.class);
        manager.load(gameMusic, Music.class);
    }
    //Sounds
    public static final String rocket = "rocket.mp3";
    public static final String buttonClick = "buttonClick.mp3";

    public void loadSounds() {
        manager.load(rocket, Sound.class);
        manager.load(buttonClick, Sound.class);
    }

    // fonts
    public static final String fontPath = "gui/star-soldier/raw/font-export.fnt";

    public void loadFonts() {
        manager.load(fontPath, BitmapFont.class);
    }

    public static final String video = "video/apollo_11_liftoff.mpg";

    public void loadVideos() {
        //manager.load(video, Video);
    }

    // Particle Effects
    public static final String sparksPE = "particles/sparks.pe";
    public static final String explosionPE = "particles/explosion.pe";
    public static final String lazerPE = "particles/lazer.pe";
    public static final String pupGetEffectPE = "particles/pupGetEffect.pe";
    public static final String laserHitPE = "particles/laserHitSparks.pe";

    public void loadParticleEffects() {
        ParticleEffectParameter pep = new ParticleEffectParameter();
        pep.atlasFile = "images/images.pack";
        manager.load(sparksPE, ParticleEffect.class, pep);
        manager.load(explosionPE, ParticleEffect.class, pep);
        manager.load(lazerPE, ParticleEffect.class, pep);
        manager.load(pupGetEffectPE, ParticleEffect.class, pep);
        manager.load(laserHitPE, ParticleEffect.class, pep);
    }

}