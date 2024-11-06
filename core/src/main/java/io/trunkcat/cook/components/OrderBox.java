package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import io.trunkcat.cook.TextureManager;
import io.trunkcat.cook.enums.ItemID;

public class OrderBox extends Actor {
    private ArrayList<ItemID> orderImages;
    private int imageIndex;

    OrderBox(ArrayList<ItemID> orderImages) {
        this.orderImages = orderImages;
        imageIndex = 0;
    }

}
