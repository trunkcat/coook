package io.trunkcat.cook.components;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.Image;
import java.util.Random;

import io.trunkcat.cook.enums.ItemID;

public class Minion extends ImageActor {
    final static ItemID ITEM_ID = ItemID.MINION;

    public Minion(final Stage stage, Random rand) {
        super(Minion.ITEM_ID, null);

        //i have zero clue what to initialise
    }

    //TODO: Requested Item is replenished in availableItemCount
}
