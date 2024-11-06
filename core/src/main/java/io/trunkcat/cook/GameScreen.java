package io.trunkcat.cook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

import io.trunkcat.cook.components.BunTray;
import io.trunkcat.cook.components.Customer;
import io.trunkcat.cook.components.Dispenser;
import io.trunkcat.cook.components.FryingPan;
import io.trunkcat.cook.components.PattyTray;
import io.trunkcat.cook.components.Plate;
import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

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

        Texture backgroundTexture = new Texture(Gdx.files.internal("bg.png"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setPosition(0, 0);
        backgroundImage.setWidth(2000);
        backgroundImage.addAction(Actions.alpha(0.75f)); // A little darkness might add some effect.
        stage.addActor(backgroundImage);

        Customer customer1 = new Customer(stage, dragAndDrop, new ItemID[]{ItemID.BUN_PATTY}, rand);
        customer1.setSize(300, 450);
        customer1.setPosition(300, 500);
        stage.addActor(customer1);

        Customer customer2 = new Customer(stage, dragAndDrop, new ItemID[]{ItemID.BUN_PATTY, ItemID.COLA_CUP}, rand);
        customer2.setSize(300, 450);
        customer2.setPosition(900, 500);
        stage.addActor(customer2);

        Image counterTop = new Image(Textures.CounterTop);
        counterTop.setSize(2000, 520);
        stage.addActor(counterTop);

        PattyTray pattyTray = new PattyTray(5, stage, dragAndDrop);
        pattyTray.setPosition(0, 0);
        pattyTray.setSize(400, 400);
        stage.addActor(pattyTray);

        BunTray bunTray = new BunTray(5, stage, dragAndDrop);
        bunTray.setPosition(400, 20);
        bunTray.setSize(400, 400);
        stage.addActor(bunTray);

        /* Table top */
        Image shelf = new Image(Textures.CounterTop);
        shelf.setSize(2000, 520);
        shelf.setY(125);
        stage.addActor(shelf);

        FryingPan pan1 = new FryingPan(stage, dragAndDrop);
        pan1.setSize(250, 250);
        pan1.setPosition(1300, 300);
        pan1.setOrigin(Align.center);
        stage.addActor(pan1);

        FryingPan pan2 = new FryingPan(stage, dragAndDrop);
        pan2.setSize(250, 250);
        pan2.setPosition(1500, 300);
        pan2.setOrigin(Align.center);
        stage.addActor(pan2);

        Dispenser dispenser = new Dispenser(stage, dragAndDrop);
        dispenser.setSize(300, 300);
        dispenser.setPosition(100, 400);
        dispenser.setOrigin(Align.center);
        stage.addActor(dispenser);

        Plate plate1 = new Plate(stage, dragAndDrop);
        plate1.setSize(250, 150);
        plate1.setOrigin(Align.center);
        plate1.setPosition(750, 400);
        stage.addActor(plate1);

        Plate plate2 = new Plate(stage, dragAndDrop);
        plate2.setSize(250, 150);
        plate2.setOrigin(Align.center);
        plate2.setPosition(750, 200);
        stage.addActor(plate2);

        Plate plate3 = new Plate(stage, dragAndDrop);
        plate3.setSize(250, 150);
        plate3.setOrigin(Align.center);
        plate3.setPosition(450, 400);
        stage.addActor(plate3);

        Plate plate4 = new Plate(stage, dragAndDrop);
        plate4.setSize(250, 150);
        plate4.setOrigin(Align.center);
        plate4.setPosition(450, 200);
        stage.addActor(plate4);

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
