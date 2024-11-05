package io.trunkcat.cook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class SettingsScreen implements Screen {

    final CookGame game;
    final private Stage stage;

    public SettingsScreen(final CookGame game) {
        this.game = game;
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {
        // Prepare your screen here.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = Color.WHITE;

        TextButton backButton = new TextButton("BACK", buttonStyle);
        backButton.getLabel().setFontScale(5);
//        resumeButton.setSize(100, 100);
        backButton.setPosition(100, 400);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SettingsScreen.this.game.setScreen(new MenuScreen(SettingsScreen.this.game));
            }
        });

        TextButton plusButton = new TextButton("+", buttonStyle);
        plusButton.getLabel().setFontScale(5);
        plusButton.setPosition(100, 400);
        plusButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        TextButton minusButton = new TextButton("-", buttonStyle);
        minusButton.getLabel().setFontScale(5);
        minusButton.setPosition(100, 400);
        minusButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        table.add(backButton).padBottom(100);
        table.row();
        table.add(plusButton).padTop(100).padRight(20);
        table.row();
        table.add(minusButton).padTop(100).padLeft(20);
        table.row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // stage.getViewport().update(width, height, true);
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        stage.dispose();
    }
}
