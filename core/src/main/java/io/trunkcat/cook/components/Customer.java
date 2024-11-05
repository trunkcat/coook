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
import io.trunkcat.cook.interfaces.Textures;
import io.trunkcat.cook.interfaces.TimeConstants;

public class Customer extends ImageActor {
    static final ItemID ITEM_ID = ItemID.CUSTOMER;
    static Texture TEXTURE = Textures.Customers.Sunita.Happy;

    public CustomerEmotion emotion;
    public List<ItemID> ordersLeft;
    public List<ItemID> ordersFulfilled;
    public float initialTip;
    public int bill;

    public float waitTime; // TODO: progress bar.

    public Customer(final Stage stage, final DragAndDrop dragAndDrop, final ItemID[] orders, Random rand) {
        super(Customer.ITEM_ID, Customer.TEXTURE);

        this.ordersLeft = new ArrayList<>(Arrays.asList(orders));
        this.ordersFulfilled = new ArrayList<>();
        this.waitTime = orders.length * TimeConstants.WAIT_UNIT_PER_ORDER * TimeConstants.SECOND_IN_SECONDS;
        this.emotion = CustomerEmotion.Happy;
        this.initialTip = rand.nextInt(OtherConstants.MAXIMUM_TIP - OtherConstants.MINIMUM_TIP + 1) + OtherConstants.MINIMUM_TIP;

        this.setZIndex(1); // to be at the lowest level cuz they don't matter.

        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (payload == null || !(payload.getDragActor() instanceof FoodHolder))
                    return false;

                FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                if (foodHolder.currentItem != null && !hasOrderedItem(foodHolder.currentItem.itemId)) {
                    // TODO: happy face when hover overs. for now, just scale them a bit.
                    // May be even show angry face if the dragged item isn't the one they ordered.
                    // and if the order isn't complete, then could show the disappointed face
//                    emotion = CustomerEmotion.Neutral;
                    TEXTURE = Textures.Customers.Sunita.Neutral;
                    updateTexture(TEXTURE);
                    setScale(1.1f);
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
                if (payload == null || !(payload.getDragActor() instanceof FoodHolder))
                    return;
                FoodHolder foodHolder = (FoodHolder) payload.getDragActor();
                if (hasOrderedItem(foodHolder.currentItem.itemId)) {
                    try {
                        fulfillOrder(foodHolder.currentItem.itemId);
                        foodHolder.currentItem = null;
                        foodHolder.remove();
                    } catch (FoodNotOrderedException e) {
                        throw new RuntimeException(e);
                    }
                }
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
    }
}
