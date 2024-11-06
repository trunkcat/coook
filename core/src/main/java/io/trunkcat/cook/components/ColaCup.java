package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class ColaCup extends FoodItem {
    private static final Texture TEXTURE = Textures.ColaCup.Full;
    private static final ItemID ITEM_ID = ItemID.COLA_CUP;

    public ColaCup() {
        super(ITEM_ID, TEXTURE);
        setZIndex(1);
        setSize(100, 100);
    }
}
