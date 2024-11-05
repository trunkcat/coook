package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.CookStatus;
import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.exceptions.UncookableItemException;
import io.trunkcat.cook.interfaces.TimeConstants;

public abstract class FoodCooker extends ImageActor {
    public ItemID currentlyPreparingItem = null;
    private float preparationTime = 0f;
    private float overcookingTime = 0f;

    public CookStatus status = CookStatus.Empty;

    public FoodCooker(final ItemID itemID, final Texture texture,
                      final Stage stage, final DragAndDrop dragAndDrop) {
        super(itemID, texture);

        emptyCooker();

        // Allow putting raw items to cook.
        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (payload == null) return false;

                if (status != CookStatus.Empty) return false;

                if (payload.getDragActor() instanceof FoodItem) {
                    FoodItem foodItem = (FoodItem) payload.getDragActor();
                    if (isCookableItem(foodItem.itemId)) {
                        foodItem.setScale(1.25f);
                        return true;
                    }
                } else if (payload.getDragActor() instanceof FoodHolder) {
                    FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                    if (foodHolder.currentItem != null && isCookableItem(foodHolder.currentItem)) {
                        foodHolder.setScale(1.25f);
                        return true;
                    }
                }

                return false;
            }

            @Override
            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                super.reset(source, payload);
                if (payload == null) return;
                if (payload.getDragActor() instanceof FoodItem) {
                    FoodItem foodItem = (FoodItem) payload.getDragActor();
                    if (isCookableItem(foodItem.itemId)) {
                        foodItem.setScale(1.f);
                    }
                } else if (payload.getDragActor() instanceof FoodHolder) {
                    FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                    if (foodHolder.currentItem != null && isCookableItem(foodHolder.currentItem)) {
                        foodHolder.setScale(1.f);
                    }
                }
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (payload == null) return;

                if (status != CookStatus.Empty) return;

                if (payload.getDragActor() instanceof FoodItem) {
                    FoodItem foodItem = (FoodItem) payload.getDragActor();
                    if (isCookableItem(foodItem.itemId)) {
                        putItemToCook(foodItem.itemId);
                        foodItem.setScale(1.f);
                        foodItem.remove();
                    }
                } else if (payload.getDragActor() instanceof FoodHolder) {
                    FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                    if ((status == CookStatus.Cooked || status == CookStatus.Overcooking) &&
                        foodHolder.canHoldItem(currentlyPreparingItem)) {
                        // If the plate is coming to receive the item from the cooker:
                        foodHolder.currentItem = currentlyPreparingItem;
                        emptyCooker();
                        // foodHolder.updateTexture();
                    } else if (isCookableItem(foodHolder.currentItem)) {
                        // If the plate has something that can be cooked:
                        putItemToCook(foodHolder.currentItem);
                        foodHolder.setScale(1.f);
                        foodHolder.emptyHolder();
                    }
                }
            }
        });

        // Only allow dragging if the cooking is finished.
        dragAndDrop.addSource(new DragAndDrop.Source(this) {
            float startX = 0f, startY = 0f;

            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                // If there's nothing to take, then nothing to drag.
                if (status == CookStatus.Empty || status == CookStatus.Cooking) return null;
//                startX =

                return null;
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                super.dragStop(event, x, y, pointer, payload, target);
            }
        });
    }

    public void putItemToCook(ItemID item) {
        try {
            this.currentlyPreparingItem = getAfterCookedItem(item);
            this.preparationTime = getPreparationTime(currentlyPreparingItem);
            this.overcookingTime = 0f;
            this.status = CookStatus.Cooking;
            //TODO: calculate the update the texture to the cook state.
            // maybe add some smooth steam as well.
        } catch (UncookableItemException e) {
            // Item can't be cooked, so it should return false, which means, it hasn't been
            // put to the cooker for cooking yet. Drag handler should see this value, and should
            // make it go back to it's original place.
        }
    }

    public void emptyCooker() {
        preparationTime = 0f;
        overcookingTime = 0f;
        currentlyPreparingItem = null;
        status = CookStatus.Empty;
        updateTexture(defaultTexture);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Texture statusTexture = getStatusTexture();
        if (statusTexture != currentTexture) {
            System.out.println("changing texture... " + status.name());
            updateTexture(statusTexture);
        }

        if (status == CookStatus.Empty) {
        } else if (status == CookStatus.Cooking) {
            preparationTime -= delta;
            if (preparationTime <= 0f) {
                preparationTime = 0f;
                status = CookStatus.Cooked;
                overcookingTime = 0f;
            }
        } else if (status == CookStatus.Cooked) {
            overcookingTime += delta;
            if (overcookingTime >= TimeConstants.TIME_BEFORE_OVERCOOKING) {
                status = CookStatus.Overcooking;
                overcookingTime = 0f;
            }
        } else if (status == CookStatus.Overcooking) {
            overcookingTime += delta;
            if (overcookingTime >= TimeConstants.TIME_BEFORE_RUINED) {
                status = CookStatus.Ruined;
                overcookingTime = 0f;
            }
        }
    }

    public boolean isCookableItem(ItemID itemID) {
        if (itemID == null) return false;
        try {
            getAfterCookedItem(itemID);
            return true;
        } catch (UncookableItemException e) {
            return false;
        }
    }

    // NOTE: should return the time required for the items that it can cook (in seconds).
    //  Otherwise, it should throw an UncookableItemException, which should be handled by
    //  the drag and drop handler.
    // (after) Cooked Item -> time in seconds.
    abstract float getPreparationTime(ItemID itemID) throws UncookableItemException;

    // Uncooked item ID -> Cooked item ID.
    abstract ItemID getAfterCookedItem(ItemID itemID) throws UncookableItemException;

    // -> Texture
    abstract Texture getStatusTexture();
}
