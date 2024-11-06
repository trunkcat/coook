package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class PattyTray extends FoodCollection {
    static final ItemID ITEM_ID = ItemID.PATTY_TRAY;
    static final Texture TEXTURE = Textures.Tray;

    private final DragAndDrop dragAndDrop;

    public PattyTray(final int availableItemCount, final Stage stage, final DragAndDrop dragAndDrop) {
        super(PattyTray.ITEM_ID, PattyTray.TEXTURE, availableItemCount, stage, dragAndDrop);
        this.dragAndDrop = dragAndDrop;
    }

    @Override
    Actor generateActor() {
        Patty patty = new Patty(dragAndDrop);
        patty.setSize(200, 200);
        patty.setOrigin(Align.center);
        return patty;
    }
}
