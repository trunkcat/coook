package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.exceptions.UncookableItemException;
import io.trunkcat.cook.interfaces.Textures;

public class FryingPan extends FoodCooker {
    static final Texture TEXTURE = Textures.FryingPan.Steak.Empty;
    static final ItemID ITEM_ID = ItemID.FRYING_PAN;

    public FryingPan(Stage stage, DragAndDrop dragAndDrop) {
        super(FryingPan.ITEM_ID, FryingPan.TEXTURE, stage, dragAndDrop);
    }

    @Override
    float getPreparationTime(ItemID itemID) throws UncookableItemException {
        switch (itemID) {
            case BREAD_SLICE_FRIED:
                return 10;
            default:
                throw new UncookableItemException(itemID.name() + " isn't cookable by " + this.itemId.name());
        }
    }

    @Override
    ItemID getAfterCookedItem(ItemID itemID) throws UncookableItemException {
        switch (itemID) {
            case BREAD_SLICE:
                return ItemID.BREAD_SLICE_FRIED;
            default:
                throw new UncookableItemException(itemID.name() + " isn't cookable by " + this.itemId.name());
        }
    }

    @Override
    Texture getStatusTexture() {
        switch (status) {
            case Empty:
                return Textures.FryingPan.Steak.Empty;
            case Cooking:
                return Textures.FryingPan.Steak.Cooking;
            case Cooked:
                return Textures.FryingPan.Steak.Cooked;
            case Overcooking:
                return Textures.FryingPan.Steak.Overcooking;
            case Ruined:
                return Textures.FryingPan.Steak.Ruined;
            default:
                return Textures.FryingPan.Steak.Default;
        }
    }
}
