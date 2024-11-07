package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;

import io.trunkcat.cook.enums.ItemID;

public class OrderBox extends ImageActor {
    public static final ItemID ITEM_ID = ItemID.ORDER_BOX;

    public OrderBox(Texture itemTexture) {
        super(OrderBox.ITEM_ID, itemTexture);
    }
}
