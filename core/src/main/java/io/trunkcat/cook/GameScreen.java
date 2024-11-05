package io.trunkcat.cook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

import io.trunkcat.cook.components.Customer;
import io.trunkcat.cook.components.FryingPan;
import io.trunkcat.cook.components.PattyTray;
import io.trunkcat.cook.components.Plate;
import io.trunkcat.cook.enums.ItemID;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {
    final CookGame game;

    private Stage stage;
    private DragAndDrop dragAndDrop;
    private final Random rand;

    public GameScreen(final CookGame game) {
        this.game = game;
        stage = new Stage(game.viewport);
        dragAndDrop = new DragAndDrop();
        Gdx.input.setInputProcessor(stage);

        this.rand = new Random();

        Music bgmusic = Gdx.audio.newMusic(Gdx.files.internal("audio/bgmusic.mp3"));
        bgmusic.play();
        bgmusic.setLooping(true);

        Texture Background = new Texture(Gdx.files.internal("bg.png"));
        Actor bg = new Image(Background);
        bg.setPosition(-50, -150); //dont question this placement pls
        stage.addActor(bg);

        Customer customer = new Customer(stage, dragAndDrop, new ItemID[]{ItemID.PATTY}, rand);
        customer.setSize(500, 500);
        customer.setPosition(600, 300);
        stage.addActor(customer);

        Texture tableTexture = new Texture(Gdx.files.internal("table.png"));
        Actor table = new Image(tableTexture);
        table.setSize(2000, 1000);
        table.setZIndex(0);
        stage.addActor(table);

        PattyTray pattyTray = new PattyTray(5, stage, dragAndDrop);
        pattyTray.setPosition(0, 0);
        pattyTray.setSize(400, 400);
        stage.addActor(pattyTray);

        FryingPan pan2 = new FryingPan(stage, dragAndDrop);
        pan2.setSize(200, 200);
        pan2.setPosition(1300, 250);
        pan2.setOrigin(Align.center);
        stage.addActor(pan2);

        Plate plate1 = new Plate(stage, dragAndDrop);
        plate1.setSize(300, 300);
        plate1.setPosition(800, 50);
        plate1.setOrigin(Align.center);
        stage.addActor(plate1);

        stage.act();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {
        // Prepare your screen here.
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
