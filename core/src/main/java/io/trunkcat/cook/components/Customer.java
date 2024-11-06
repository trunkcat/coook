package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.trunkcat.cook.FoodInfo;
import io.trunkcat.cook.enums.CustomerEmotion;
import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.exceptions.FoodNotOrderedException;
import io.trunkcat.cook.interfaces.OtherConstants;
import io.trunkcat.cook.interfaces.TimeConstants;

public abstract class Customer extends ImageActor {
    public CustomerEmotion emotion;
    public List<ItemID> ordersLeft;
    public List<ItemID> ordersFulfilled;
    public float initialTip;
    public int bill;

    public float initialWaitTime;

    public float waitTime; // TODO: progress bar.

    public Customer(ItemID itemID, Texture itemTexture, final Stage stage, final DragAndDrop dragAndDrop, final ItemID[] orders, Random rand) {
        super(itemID, itemTexture);

        this.ordersLeft = new ArrayList<>(Arrays.asList(orders));
        this.ordersFulfilled = new ArrayList<>();
        this.initialWaitTime = orders.length * TimeConstants.WAIT_UNIT_PER_ORDER * TimeConstants.SECOND_IN_SECONDS;
        this.waitTime = initialWaitTime;
        this.emotion = CustomerEmotion.Happy;
        this.initialTip = rand.nextInt(OtherConstants.MAXIMUM_TIP - OtherConstants.MINIMUM_TIP + 1) + OtherConstants.MINIMUM_TIP;

        this.setZIndex(1); // to be at the lowest level cuz they don't matter.

        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (payload == null)
                    return false;

                if (payload.getDragActor() instanceof FoodHolder) {
                    FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                    if (foodHolder.currentItem != null && hasOrderedItem(foodHolder.currentItem)) {
                        // TODO: happy face when hover overs. for now, just scale them a bit.
                        // May be even show angry face if the dragged item isn't the one they ordered.
                        // and if the order isn't complete, then could show the disappointed face.
                        setScale(1.1f);
                        return true;
                    }
                } else if (payload.getDragActor() instanceof ColaCup) {
                    ColaCup colaCup = (ColaCup) payload.getDragActor();
                    if (hasOrderedItem(colaCup.itemId)) {
                        setScale(1.1f);
                        return true;
                    }
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
                if (payload == null)
                    return;

                if (payload.getDragActor() instanceof FoodHolder) {
                    FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                    if (hasOrderedItem(foodHolder.currentItem)) {
                        try {
                            System.out.println("fulfilling " + foodHolder.currentItem.name());
                            fulfillOrder(foodHolder.currentItem);
                            foodHolder.currentItem = null;
                            foodHolder.remove();
                        } catch (FoodNotOrderedException e) {
                            return;
                        }
                    }
                }
//                else if (payload.getDragActor() instanceof ColaCup) {
//                    ColaCup colaCup = (ColaCup) payload.getDragActor();
//                    if (hasOrderedItem(colaCup.itemId)) {
//                        try {
//                            fulfillOrder(colaCup.itemId);
//                            colaCup.remove();
//                        } catch (FoodNotOrderedException e) {
//                            return;
//                        }
//                    }
//                }
            }
        });

    }

    public boolean hasOrderedItem(ItemID itemID) {
        return this.ordersLeft.contains(itemID);
    }

    public void fulfillOrder(ItemID itemID) throws FoodNotOrderedException {
        if (!hasOrderedItem(itemID)) {
            throw new FoodNotOrderedException("Item " + itemID.name() + " wasn't ordered by the customer.");
        }

        int foodValue = FoodInfo.getItemValue(itemID);
        this.bill += foodValue;
        this.ordersLeft.remove(itemID);
        this.ordersFulfilled.add(itemID);

        if (ordersLeft.isEmpty()) {
            this.remove();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.waitTime -= delta; // Update time left.
        // TODO: deduct tip as time goes by.
        // Only start deducting if they've been waiting for more than 5 seconds.
        if (waitTime > 5) {
            this.initialTip -= delta; // TODO: round it afterwards
        }

        // Update texture based on the time elapsed.
        Texture statusTexture = getStatusTexture();
        if (statusTexture != currentTexture) {
            updateTexture(statusTexture);
        }

        float patiencePercent = (waitTime / initialWaitTime) * 100;
        if (patiencePercent >= 60) {
            emotion = CustomerEmotion.Happy;
        } else if (patiencePercent >= 35) {
            emotion = CustomerEmotion.Neutral;
        } else if (patiencePercent > 0) {
            emotion = CustomerEmotion.Impatient;
        } else {
            // When runs out of patience:
            remove();
        }
    }

    abstract public Texture getStatusTexture();
}
