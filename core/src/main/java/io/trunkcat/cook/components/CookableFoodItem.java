package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.CookStatus;
import io.trunkcat.cook.enums.ItemID;

public abstract class CookableFoodItem extends FoodItem {
    public CookStatus status = CookStatus.Raw;
    public boolean isCookingPaused = true;

    public CookableFoodItem(final ItemID itemId, final Texture itemTexture, final DragAndDrop dragAndDrop) {
        super(itemId, itemTexture);

        // Only allow dragging if the cooking is finished.
        dragAndDrop.addSource(new DragAndDrop.Source(this) {
            float startX = 0f, startY = 0f;

            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                startX = getX();
                startY = getY();
                
                // Can't be taken if it is not cooked yet.
                if (status == CookStatus.Raw || status == CookStatus.Cooking)
                    return null;

                System.out.println("done");
                isCookingPaused = true;

                // Set the current food item as the draggable actor.
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                setVisible(true);
                payload.setDragActor(this.getActor());

                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                super.dragStop(event, x, y, pointer, payload, target);

                isCookingPaused = false;
                if (target == null || payload == null) return;
                if (!(target.getActor() instanceof FoodHolder)) return;

                isCookingPaused = true;
                FoodItem item = (FoodItem) payload.getDragActor();
                FoodHolder holder = (FoodHolder) target.getActor();
                holder.currentItem = item;
                item.setVisible(false);
            }
        });
    }

    abstract Texture getStatusTexture();
}
