package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.exceptions.FoodNotOrderedException;
import io.trunkcat.cook.interfaces.Textures;
import io.trunkcat.cook.interfaces.TimeConstants;

enum ColaStatus {
    Empty, Full
}

public class Dispenser extends ImageActor {
    float durationLeft = TimeConstants.TIME_FOR_COLA_REFILL;
    ColaStatus colaStatus = ColaStatus.Empty;

    Animation<TextureRegion> fillAnimation;

    public Dispenser(final Stage stage, final DragAndDrop dragAndDrop) {
        super(ItemID.COLA_DISPENSER, Textures.ColaDispenser.Static);

        TextureRegion[][] sheet = TextureRegion.split(Textures.ColaDispenser.Sheet, 40, 43);
        TextureRegion[] frames = new TextureRegion[5];
        int index = 0;
        for (int i = 0; i < 5; i++) {
            frames[index++] = sheet[0][i];
        }
        fillAnimation = new Animation<>(0.05f, frames);

        dragAndDrop.addSource(new DragAndDrop.Source(this) {
            float startX = 0, startY = 0;

            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                // Only allow dragging if the cup is full.
                if (colaStatus == ColaStatus.Empty) return null;

                startX = getX();
                startY = getY();

                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                ColaCup colaCup = getColaCup();
                payload.setDragActor(colaCup);
                dragAndDrop.setDragActorPosition(colaCup.getWidth() / 2, -colaCup.getHeight() / 2);
                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
//                super.dragStop(event, x, y, pointer, payload, target);

                if (payload == null || !(payload.getDragActor() instanceof ColaCup)) {
                    return;
                }

                ColaCup colaCup = (ColaCup) payload.getDragActor();
                if (target == null || !(target.getActor() instanceof Customer)) {
                    colaCup.setPosition(startX, startY);
                    return;
                }

                // The target is a customer, it's okay to fulfill their request now.
                Customer customer = (Customer) target.getActor();
                try {
                    System.out.println("Fulfilling cola");
                    customer.fulfillOrder(colaCup.itemId);
                    colaCup.remove();
                    colaStatus = ColaStatus.Empty;
                } catch (FoodNotOrderedException e) {
                    // The cola cup wasn't ordered. So, it's okay to return.
                    colaCup.setPosition(startX, startY);
                    return;
                }
            }
        });
    }

    ColaCup getColaCup() {
        return new ColaCup();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (colaStatus == ColaStatus.Empty) {
            durationLeft -= delta;

            float timeElapsed = TimeConstants.TIME_FOR_COLA_REFILL - durationLeft;
            TextureRegion frame = fillAnimation.getKeyFrame(timeElapsed, true);
            updateTexture(frame);

            // Once the cup is full (time left for refill reaches ZERO):
            if (durationLeft <= 0) {
                colaStatus = ColaStatus.Full;
                durationLeft = TimeConstants.TIME_FOR_COLA_REFILL;
                updateTexture(Textures.ColaDispenser.Full);
            }
        }
    }
}
