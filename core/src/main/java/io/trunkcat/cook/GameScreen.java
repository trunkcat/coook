package io.trunkcat.cook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

import io.trunkcat.cook.components.BunTray;
import io.trunkcat.cook.components.Dispenser;
import io.trunkcat.cook.components.FryingPan;
import io.trunkcat.cook.components.PattyTray;
import io.trunkcat.cook.components.PlateStack;
import io.trunkcat.cook.components.TrashCan;
import io.trunkcat.cook.interfaces.Textures;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {
    final CookGame game;

    private Stage stage;
    private DragAndDrop dragAndDrop;
    private final Random rand;
    private final CustomerManager customerManager;

    public GameScreen(final CookGame game) {
        this.game = game;
        stage = new Stage(game.viewport);
        dragAndDrop = new DragAndDrop();
        Gdx.input.setInputProcessor(stage);

        this.rand = new Random();

        Texture backgroundTexture = new Texture(Gdx.files.internal("bg.png"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setPosition(0, 0);
        backgroundImage.setWidth(2000);
        backgroundImage.addAction(Actions.alpha(0.75f)); // A little darkness might add some effect.
        stage.addActor(backgroundImage);

        try {
            this.customerManager = new CustomerManager(new float[][]{{500, 550}, {900, 550}, {1300, 550}}, stage, dragAndDrop, rand);
            stage.addActor(this.customerManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Image shelf = new Image(Textures.CounterTop);
        shelf.setSize(2000, 520);
        shelf.setZIndex(1);
        stage.addActor(shelf);

        Image blackTint = new Image(Textures.CounterTop);
        blackTint.addAction(Actions.color(Color.BLACK));
        blackTint.addAction(Actions.alpha(0.5f));
        blackTint.setSize(2000, 520);
        blackTint.setZIndex(1);
        stage.addActor(blackTint);

        PattyTray pattyTray = new PattyTray(500, stage, dragAndDrop);
        pattyTray.setPosition(0, 0);
        pattyTray.setSize(400, 400);
        pattyTray.setZIndex(2);
        stage.addActor(pattyTray);

        BunTray bunTray = new BunTray(500, stage, dragAndDrop);
        bunTray.setPosition(400, 20);
        bunTray.setSize(300, 400);
        bunTray.setZIndex(3);
        stage.addActor(bunTray);

        PlateStack plateStack = new PlateStack(dragAndDrop, stage);
        plateStack.setPosition(800, 20);
        plateStack.setSize(300, 300);
        stage.addActor(plateStack);

        /* Table top */
        Image counterTop = new Image(Textures.CounterTop);
        counterTop.setSize(2000, 400);
        counterTop.setY(150);
        counterTop.setZIndex(4);
        stage.addActor(counterTop);

        FryingPan pan1 = new FryingPan(stage, dragAndDrop);
        pan1.setSize(250, 250);
        pan1.setPosition(1300, 300);
        pan1.setOrigin(Align.center);
        pan1.setZIndex(5);
        stage.addActor(pan1);

        FryingPan pan2 = new FryingPan(stage, dragAndDrop);
        pan2.setSize(250, 250);
        pan2.setPosition(1500, 300);
        pan2.setOrigin(Align.center);
        pan2.setZIndex(6);
        stage.addActor(pan2);

        Dispenser dispenser = new Dispenser(stage, dragAndDrop);
        dispenser.setSize(300, 350);
        dispenser.setPosition(100, 260);
        dispenser.setOrigin(Align.center);
        dispenser.setZIndex(7);
        stage.addActor(dispenser);

        TrashCan trashCan = new TrashCan(dragAndDrop);
        trashCan.setSize(250, 300);
        trashCan.setPosition(stage.getWidth() - trashCan.getWidth() - 50, -20);
        stage.addActor(trashCan);

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
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = Color.WHITE;

        TextButton PauseButton = new TextButton("Quit", buttonStyle);
        PauseButton.setPosition(1800, 940);
        PauseButton.getLabel().setFontScale(6, 6);

        PauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(GameScreen.this.game));
            }
        });
        stage.addActor(PauseButton);
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
