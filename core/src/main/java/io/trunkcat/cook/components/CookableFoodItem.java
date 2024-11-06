package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.CookStatus;
import io.trunkcat.cook.enums.ItemID;

public abstract class CookableFoodItem extends FoodItem {
    public CookStatus status = CookStatus.Raw;
    public boolean isCookingPaused = true;
    public DragAndDrop.Source dragSource = null;

    public CookableFoodItem(final ItemID itemId, final Texture itemTexture, final DragAndDrop dragAndDrop) {
        super(itemId, itemTexture);
    }

    abstract Texture getStatusTexture();
}
