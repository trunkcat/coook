package io.trunkcat.cook.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import io.trunkcat.cook.enums.ItemID;

public class BreadSlice extends FoodItem {
    static final ItemID ITEM_ID = ItemID.BREAD_SLICE;
    static final Texture TEXTURE = new Texture(Gdx.files.internal("bread.png"));

    public BreadSlice() {
        super(BreadSlice.ITEM_ID, BreadSlice.TEXTURE);
    }
}
