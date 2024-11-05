package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;

import io.trunkcat.cook.enums.ItemID;

public class FoodItem extends ImageActor {
    public FoodItem(final ItemID itemId, final Texture itemTexture) {
        super(itemId, itemTexture);
    }
}
