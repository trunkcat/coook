package io.trunkcat.cook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.Random;

import io.trunkcat.cook.components.BreadSliceTray;
import io.trunkcat.cook.components.Customer;
import io.trunkcat.cook.components.FryingPan;
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

        Texture tableTexture = new Texture(Gdx.files.internal("table.png"));
        Actor table = new Image(tableTexture);
        table.setSize(2000, 1000);
        table.setZIndex(0);
        stage.addActor(table);

        BreadSliceTray breadSliceTray = new BreadSliceTray(5, stage, dragAndDrop);
        breadSliceTray.setPosition(100, 200);
        stage.addActor(breadSliceTray);

        Plate plate1 = new Plate(stage, dragAndDrop);
        plate1.setPosition(500, 200);
        stage.addActor(plate1);

        Plate plate2 = new Plate(stage, dragAndDrop);
        plate2.setPosition(700, 200);
        stage.addActor(plate2);

        Customer customer = new Customer(stage, dragAndDrop, new ItemID[]{ItemID.BREAD_SLICE_FRIED, ItemID.BREAD_SLICE_FRIED}, rand);
        customer.setPosition(200, 500);
        stage.addActor(customer);

        FryingPan pan = new FryingPan(stage, dragAndDrop);
        pan.setPosition(1000, 300);
        stage.addActor(pan);

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
