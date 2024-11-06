package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class BunTray extends FoodCollection {
    static final ItemID ITEM_ID = ItemID.BUN_TRAY;
    static final Texture TEXTURE = Textures.BunTray;

    private final DragAndDrop dragAndDrop;

    public BunTray(final int availableItemCount, final Stage stage, final DragAndDrop dragAndDrop) {
        super(BunTray.ITEM_ID, BunTray.TEXTURE, availableItemCount, stage, dragAndDrop);
        this.dragAndDrop = dragAndDrop;
    }

    @Override
    Actor generateActor() {
        Bun bun = new Bun();
        bun.setSize(200, 150);
        bun.setOrigin(Align.center);
        return bun;
    }
}
