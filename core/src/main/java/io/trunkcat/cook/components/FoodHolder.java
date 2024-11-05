package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.Arrays;
import java.util.List;

import io.trunkcat.cook.enums.ItemID;

public abstract class FoodHolder extends ImageActor {
    public FoodItem currentItem = null;
    public List<ItemID> holdableItems;
    public boolean canBeServed = false;

    final DragAndDrop dragAndDrop;

    public FoodHolder(final ItemID itemId, final Texture itemTexture, ItemID[] holdableItems, final Stage stage, final DragAndDrop dragAndDrop) {
        super(itemId, itemTexture);
        this.holdableItems = Arrays.asList(holdableItems);
        this.dragAndDrop = dragAndDrop;

        dragAndDrop.addSource(new DragAndDrop.Source(this) {
            float startX = 0, startY = 0;

            @Override
            public DragAndDrop.Payload dragStart(InputEvent inputEvent, float x, float y, int pointer) {
                startX = FoodHolder.this.getX();
                startY = FoodHolder.this.getY();
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                centerDragActorPosition();
                setZIndex(100);
                payload.setDragActor(FoodHolder.this);
                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                super.dragStop(event, x, y, pointer, payload, target);
                setZIndex(5);
                if (target == null || (!(target.getActor() instanceof Customer) && !(target.getActor() instanceof FoodHolder))) { // NOTE: only customer
                    FoodHolder.this.setPosition(startX, startY);
                    return;
                }

                //TODO: Trash?
                // if so, then this.remove(), and deduct the points.
            }
        });
    }

    public void holdItem(FoodItem foodItem) {
        if (!canHoldItem(foodItem.itemId)) {
            return;
        }
        currentItem = foodItem;
        currentItem.setPosition(this.getX(), this.getY());
        currentItem.setZIndex(this.getZIndex() + 1);
        currentItem.setScale(3);
    }

    public void emptyHolder() {
        if (currentItem == null) {
            return; // fast path to not set the texture.
        }
        currentItem = null;
        updateTexture(getDefaultTexture());
    }

    abstract Texture getDefaultTexture();

    public boolean canHoldItem(ItemID itemId) {
        return currentItem == null && holdableItems.contains(itemId);
    }

    // Makes sure that the generated actor is centered by the mouse.
    public void centerDragActorPosition() {
        dragAndDrop.setDragActorPosition(this.getWidth() / 2, -this.getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (currentItem != null) {
            currentItem.setPosition(this.getX(), this.getY());
            currentItem.setZIndex(this.getZIndex() + 1);
        }
    }
}
