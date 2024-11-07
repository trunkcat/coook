package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import io.trunkcat.cook.enums.CookStatus;
import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.exceptions.UncookableItemException;
import io.trunkcat.cook.interfaces.TimeConstants;

public abstract class FoodCooker extends ImageActor {
    public CookableFoodItem currentItem = null;
    private float preparationTime = 0f;
    private float overcookingTime = 0f;

    public float timeElapsed;

    private final Stage stage;
    private final DragAndDrop dragAndDrop;

    public FoodCooker(final ItemID itemID, final Texture texture,
                      final Stage stage, final DragAndDrop dragAndDrop) {
        super(itemID, texture);

        this.stage = stage;
        this.dragAndDrop = dragAndDrop;

        emptyCooker();

        // Allow putting raw items to cook.
        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (payload == null) return false;

                if (currentItem != null) return false;

                if (payload.getDragActor() instanceof CookableFoodItem) {
                    CookableFoodItem foodItem = (CookableFoodItem) payload.getDragActor();
                    if (isCookableItem(foodItem.itemId)) {
                        foodItem.setScale(1.2f);
                        return true;
                    }
                } else if (payload.getDragActor() instanceof FoodHolder) {
                    FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                    if (foodHolder.currentItem != null && isCookableItem(foodHolder.currentItem)) {
                        foodHolder.setScale(1.2f);
                        foodHolder.setOrigin(Align.center);
                        return true;
                    }
                }

                return false;
            }

            @Override
            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                super.reset(source, payload);
                if (payload == null) return;

                if (payload.getDragActor() instanceof CookableFoodItem) {
                    CookableFoodItem foodItem = (CookableFoodItem) payload.getDragActor();
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

                if (currentItem != null) return;

                if (payload.getDragActor() instanceof CookableFoodItem) {
                    CookableFoodItem foodItem = (CookableFoodItem) payload.getDragActor();
                    if (isCookableItem(foodItem.itemId)) {
                        putItemToCook(foodItem.itemId);
                        foodItem.remove();
                    }
                } else if (payload.getDragActor() instanceof FoodHolder) {
                    FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                    if ((currentItem.status == CookStatus.Cooked || currentItem.status == CookStatus.Overcooking) &&
                        foodHolder.canHoldItem(currentItem.itemId)) {
                        // If the plate is coming to receive the item from the cooker:
                        foodHolder.holdItem(currentItem);
                        emptyCooker();
                    } else if (isCookableItem(foodHolder.currentItem)) {
                        // If the plate has something that can be cooked:
                        putItemToCook(foodHolder.currentItem);
                        foodHolder.setScale(1.f);
                        foodHolder.emptyHolder();
                    }
                }
            }
        });
    }

    public void putItemToCook(ItemID item) {
        try {
            currentItem = getAfterCookedItem(item);

            // Only allow dragging if the cooking is finished.
            currentItem.dragSource = new DragAndDrop.Source(currentItem) {
                float startX = 0f, startY = 0f;

                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    startX = currentItem.getX();
                    startY = currentItem.getY();

                    // Can't be taken if it is not cooked yet.
                    if (currentItem.status == CookStatus.Raw || currentItem.status == CookStatus.Cooking)
                        return null;

                    currentItem.isCookingPaused = true;

                    // Set the current food item as the draggable actor.
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    dragAndDrop.setDragActorPosition(currentItem.getWidth() / 2, -currentItem.getHeight() / 2);
                    currentItem.setVisible(true);
                    payload.setDragActor(currentItem);

                    return payload;
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                    super.dragStop(event, x, y, pointer, payload, target);

                    currentItem.isCookingPaused = false;
                    if (target == null || payload == null) {
                        currentItem.setPosition(startX, startY);
                        return;
                    }
                    if (!(target.getActor() instanceof FoodHolder)) {
                        currentItem.setPosition(startX, startY);
                        return;
                    }

                    currentItem.isCookingPaused = true;

                    FoodHolder holder = (FoodHolder) target.getActor();

                    if (currentItem.itemId == ItemID.PATTY && currentItem.status.ordinal() >= CookStatus.Cooked.ordinal() &&
                        holder.currentItem == ItemID.BUN) {
                        holder.holdItem(currentItem);
                        currentItem.remove();
                    }

                    emptyCooker();
                }
            };
            dragAndDrop.addSource(currentItem.dragSource);

            currentItem.status = CookStatus.Cooking;
            preparationTime = getPreparationTime(currentItem.itemId);
            currentItem.setOrigin(Align.center);
            currentItem.setPosition(this.getX() + (this.getWidth() / 2) - 20, this.getY() + (this.getHeight() / 2) + 20);
            currentItem.setZIndex(this.getZIndex() + 1);
            currentItem.setSize(100, 75);
            currentItem.isCookingPaused = false;
            stage.addActor(currentItem);

            //TODO: calculate the update the texture to the cook state.
            // maybe add some smooth steam as well. and FIRE!
        } catch (UncookableItemException e) {
            // Item can't be cooked, so it should return false, which means, it hasn't been
            // put to the cooker for cooking yet. Drag handler should see this value, and should
            // make it go back to it's original place.
        }
    }

    public void emptyCooker() {
        currentItem = null;
    }

    abstract Texture getDefaultTexture();

    public abstract boolean isCookableItem(ItemID item);

    @Override
    public void act(float delta) {
        super.act(delta);

        timeElapsed += delta;

        if (currentItem != null) {
            Texture statusTexture = currentItem.getStatusTexture();
            if (statusTexture != currentItem.currentTexture) {
                System.out.println("changing texture... " + currentItem.status.name());
                currentItem.updateTexture(statusTexture);
            }

            if (currentItem.isCookingPaused) return;

            if (currentItem.status == CookStatus.Raw) {
            } else if (currentItem.status == CookStatus.Cooking) {
                preparationTime -= delta;
                if (preparationTime <= 0f) {
                    preparationTime = 0f;
                    currentItem.status = CookStatus.Cooked;
                    overcookingTime = 0f;
                }
            } else if (currentItem.status == CookStatus.Cooked) {
                overcookingTime += delta;
                if (overcookingTime >= TimeConstants.TIME_BEFORE_OVERCOOKING) {
                    currentItem.status = CookStatus.Overcooking;
                    overcookingTime = 0f;
                }
            } else if (currentItem.status == CookStatus.Overcooking) {
                overcookingTime += delta;
                if (overcookingTime >= TimeConstants.TIME_BEFORE_RUINED) {
                    currentItem.status = CookStatus.Ruined;
                    overcookingTime = 0f;
                    currentItem.isCookingPaused = true;
                }
            }
        }
    }

    // NOTE: should return the time required for the items that it can cook (in seconds).
    //  Otherwise, it should throw an UncookableItemException, which should be handled by
    //  the drag and drop handler.
    // (after) Cooked Item -> time in seconds.
    abstract float getPreparationTime(ItemID itemID) throws UncookableItemException;

    // Uncooked item ID -> Cooked item ID.
    abstract CookableFoodItem getAfterCookedItem(ItemID itemID) throws UncookableItemException;
}
