package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.exceptions.UncookableItemException;
import io.trunkcat.cook.interfaces.Textures;

public class FryingPan extends FoodCooker {
    static final Texture TEXTURE = Textures.FryingPan.Flameless;
    static final ItemID ITEM_ID = ItemID.FRYING_PAN;

    Animation<TextureRegion> fireAnimation;
    private DragAndDrop dragAndDrop;

    public FryingPan(Stage stage, DragAndDrop dragAndDrop) {
        super(FryingPan.ITEM_ID, FryingPan.TEXTURE, stage, dragAndDrop);

        TextureRegion[][] sheet = TextureRegion.split(Textures.FryingPan.Sheet, 25, 18);
        TextureRegion[] frames = new TextureRegion[3];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            frames[index++] = sheet[0][i];
        }
        fireAnimation = new Animation<>(0.25f, frames);

        this.dragAndDrop = dragAndDrop;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (currentItem == null) {
            Texture texture = getDefaultTexture();
            if (currentTexture != texture) {
                updateTexture(texture);
            }
            return;
        }

        TextureRegion texture = fireAnimation.getKeyFrame(timeElapsed, true);
        updateTexture(texture);
    }

    @Override
    Texture getDefaultTexture() {
        return Textures.FryingPan.Flameless;
    }

    @Override
    public boolean isCookableItem(ItemID item) {
        switch (item) {
            case PATTY:
                return true;
            default:
                return false;
        }
    }

    @Override
    float getPreparationTime(ItemID itemID) throws UncookableItemException {
        switch (itemID) {
            case PATTY:
                return 10;
            default:
                throw new UncookableItemException(itemID.name() + " isn't cookable by " + this.itemId.name());
        }
    }

    @Override
    CookableFoodItem getAfterCookedItem(ItemID itemID) throws UncookableItemException {
        switch (itemID) {
            case PATTY:
                return new CookableFoodItem(ItemID.PATTY, Textures.Patty.Raw, dragAndDrop) {
                    @Override
                    Texture getStatusTexture() {
                        switch (status) {
                            case Cooking:
                                return Textures.Patty.Cooking;
                            case Cooked:
                                return Textures.Patty.Cooked;
                            case Overcooking:
                                return Textures.Patty.Overcooking;
                            case Ruined:
                                return Textures.Patty.Ruined;
                            case Raw:
                            default:
                                return Textures.Patty.Raw;
                        }
                    }
                };
            default:
                throw new UncookableItemException(itemID.name() + " isn't cookable by " + this.itemId.name());
        }
    }
}
