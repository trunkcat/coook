package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class Patty extends CookableFoodItem {
    static final ItemID ITEM_ID = ItemID.PATTY;
    static final Texture TEXTURE = Textures.Patty.Raw;

    public Patty(final DragAndDrop dragAndDrop) {
        super(Patty.ITEM_ID, Patty.TEXTURE, dragAndDrop);
    }

    @Override
    Texture getStatusTexture() {
        switch (status) {
            case Cooked:
                return Textures.Patty.Cooked;
            case Cooking:
                return Textures.Patty.Cooking;
            case Overcooking:
                return Textures.Patty.Overcooking;
            case Ruined:
                return Textures.Patty.Ruined;
            case Raw:
            default:
                return Textures.Patty.Raw;
        }
    }
}
