package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class Plate extends FoodHolder {
    static final ItemID ITEM_ID = ItemID.PLATE;
    static final Texture TEXTURE = Textures.Plate;

    public Plate(final Stage stage, final DragAndDrop dragAndDrop) {
        //TODO change the array to a abstract method inside `FoodHolder`.
        // so that it supports the complex recipes.
        super(Plate.ITEM_ID, Plate.TEXTURE, new ItemID[]{ItemID.BUN, ItemID.BUN_PATTY}, stage, dragAndDrop);

        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (!(payload.getDragActor() instanceof FoodItem)) {
                    return false;
                }
                FoodItem actor = (FoodItem) payload.getDragActor();
                if (actor.itemId == ItemID.BUN || actor.itemId == ItemID.PATTY) { // TODO: change to abstract.
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
                if (!(payload.getDragActor() instanceof FoodItem)) return;
                FoodItem foodItem = (FoodItem) payload.getDragActor();
                holdItem(foodItem);
                foodItem.remove();
                payload.setDragActor(null);
            }
        });
    }

    @Override
    Texture getDefaultTexture() {
        return Textures.Plate;
    }
}
