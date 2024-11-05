package io.trunkcat.cook.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;

public class Plate extends FoodHolder {
    static final ItemID ITEM_ID = ItemID.PLATE;
    static final Texture TEXTURE = new Texture(Gdx.files.internal("plate.png"));
    static final Texture TEXTURE_BREAD = new Texture(Gdx.files.internal("butter.png"));

    public Plate(final Stage stage, final DragAndDrop dragAndDrop) {
        //TODO change the array to a abstract method inside `FoodHolder`.
        // so that it supports the complex recipes.
        super(Plate.ITEM_ID, Plate.TEXTURE, new ItemID[]{ItemID.BREAD_SLICE, ItemID.BREAD_SLICE_FRIED}, stage, dragAndDrop);

        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (!(payload.getDragActor() instanceof ImageActor)) {
                    return false;
                }
                ImageActor actor = (ImageActor) payload.getDragActor();
                if (canHoldItem(actor.itemId)) { // TODO: change to abstract.
                    setScale(1.08f);
                    return true;
                }
                return false;
            }

            @Override
            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                super.reset(source, payload);
                setScale(1.0f);
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                ImageActor actor = (ImageActor) payload.getDragActor();
                actor.remove();
                updateTexture(Plate.TEXTURE_BREAD);
                currentItem = actor.itemId;
            }
        });
    }

}
