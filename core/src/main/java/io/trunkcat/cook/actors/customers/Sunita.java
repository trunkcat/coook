package io.trunkcat.cook.actors.customers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.Random;

import io.trunkcat.cook.components.Customer;
import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class Sunita extends Customer {
    static final Texture TEXTURE = Textures.Customers.Sunita.Happy;
    static final ItemID ITEM_ID = ItemID.FRYING_PAN;

    public Sunita(Stage stage, DragAndDrop dragAndDrop, ItemID[] orders, Random rand) {
        super(ITEM_ID, TEXTURE, stage, dragAndDrop, orders, rand);
    }

    @Override
    public Texture getStatusTexture() {
        switch (emotion) {
            case Happy:
                return Textures.Customers.Sunita.Happy;
            case Impatient:
                return Textures.Customers.Sunita.Impatient;
            case Neutral:
            default:
                return Textures.Customers.Sunita.Neutral;
        }
    }
}
