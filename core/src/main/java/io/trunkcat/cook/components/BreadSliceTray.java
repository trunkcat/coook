package io.trunkcat.cook.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;

public class BreadSliceTray extends FoodCollection {
    static final ItemID ITEM_ID = ItemID.BREAD_SLICE_TRAY;
    static final Texture TEXTURE = new Texture(Gdx.files.internal("plate.png"));

    public BreadSliceTray(final int availableItemCount, final Stage stage, final DragAndDrop dragAndDrop) {
        super(BreadSliceTray.ITEM_ID, BreadSliceTray.TEXTURE, availableItemCount, stage, dragAndDrop);
    }

    @Override
    Actor generateActor() {
        return new BreadSlice();
    }
}
