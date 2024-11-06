package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class Bun extends FoodItem {
    static final ItemID ITEM_ID = ItemID.BUN;
    static final Texture TEXTURE = Textures.Bun;

    public Bun() {
        super(Bun.ITEM_ID, Bun.TEXTURE);
    }
}
