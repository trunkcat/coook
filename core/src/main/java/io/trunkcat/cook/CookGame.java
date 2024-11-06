package io.trunkcat.cook;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import io.trunkcat.cook.enums.Screens;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class CookGame extends Game {
    public ExtendViewport viewport;

    public Music bgMusic;

    @Override
    public void create() {
        viewport = new ExtendViewport(2000, 1000);
        this.setScreen(new MenuScreen(this));

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/bgmusic.mp3"));
        bgMusic.play();
        bgMusic.setLooping(true);
    }

    public void changeScreen(Screens screen) {
        switch (screen) {
            case GAME:
                this.setScreen(new GameScreen(this));
                break;
            case MAIN_MENU:
                this.setScreen(new MenuScreen(this));
                break;
            case SettingsMenu:
                this.setScreen(new SettingsScreen(this));
        }
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        super.dispose();
    }
}
