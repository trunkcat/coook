package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;

public abstract class FoodCollection extends ImageActor {
    final private DragAndDrop dragAndDrop;
    private int availableCount;

    public FoodCollection(final ItemID itemId, final Texture itemTexture, int availableItemCount, final Stage stage, final DragAndDrop dragAndDrop) {
        super(itemId, itemTexture);
        this.availableCount = availableItemCount;
        this.dragAndDrop = dragAndDrop;

        dragAndDrop.addSource(new DragAndDrop.Source(this) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent inputEvent, float x, float y, int pointer) {
                if (FoodCollection.this.availableCount == 0) {
                    return null;
                }

                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                Actor actor = generateActor();
                payload.setDragActor(actor);
                centerDragActorPosition(actor);
                stage.addActor(actor);
                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                super.dragStop(event, x, y, pointer, payload, target);
                if (payload == null || payload.getDragActor() == null) return;

                if (!(payload.getDragActor() instanceof FoodItem)) {
                    System.err.println("DRAGGING BUT NOT A FOOD ITEM? INVESTIGATE!");
                    return;
                }
                FoodItem foodItem = (FoodItem) payload.getDragActor();
                if (target == null) {
                    foodItem.remove();
                    return;
                } else if (target.getActor() instanceof FoodHolder) { // TODO: FoodCooker
                    FoodHolder holder = (FoodHolder) target.getActor();
                    if (!holder.canHoldItem(foodItem.itemId)) {
                        foodItem.remove();
                        return;
                    }

                    // If it can be held by the food holder / utensil, it is handled there
                    // in the logic of the utensil or the base class, `FoodHolder`.

                    // Reduce the item from the inventory.
                    FoodCollection.this.availableCount--;
                }
//                if (payload == null || target == null) {
//                    return;
//                }
//
//                if (!(payload.getDragActor() instanceof FoodItem)) {
//                    System.err.println("DRAGGING but not a food item!?? come on!");
//                    return;
//                }
//
//                if (target.getActor() instanceof FoodHolder) {
//                    FoodItem foodItem = (FoodItem) target.getActor();
//                } else if (target.getActor() instanceof FoodHolder) {
//                    FoodHolder foodHolder = (FoodHolder) target.getActor();
//                }
            }
        });

        // NOTE: Do not add collection as a drag and drop target.
    }

    // Makes sure that the generated actor is centered by the mouse.
    public void centerDragActorPosition(Actor actor) {
        dragAndDrop.setDragActorPosition(actor.getWidth() / 2, -actor.getHeight() / 2);
    }

    abstract Actor generateActor();
}
