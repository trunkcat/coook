package io.trunkcat.cook.interfaces;

import com.badlogic.gdx.graphics.Texture;

import io.trunkcat.cook.TextureManager;

public interface Textures {
    Texture Plate = TextureManager.loadAsset("foodholders/plate.png");
    Texture Tray = TextureManager.loadAsset("foodholders/tray.png");

    interface Customers {
        interface Sunita {
            Texture
                Happy = TextureManager.loadAsset("customers/sunita_happy.png"),
                Neutral = TextureManager.loadAsset("customers/sunita_neutral.png"),
                Impatient = TextureManager.loadAsset("customers/sunita_impatient.png");
        }
    }

    interface Patty {
        Texture
            Raw = TextureManager.loadAsset("fooditems/patty_raw.png"),
            Cooking = TextureManager.loadAsset("fooditems/patty_medium.png"),
            Cooked = TextureManager.loadAsset("fooditems/patty_fried.png"),
            Overcooking = TextureManager.loadAsset("fooditems/patty_fried.png"),
            Ruined = TextureManager.loadAsset("fooditems/patty_burned.png");
    }

    interface FryingPan {
        Texture
            Flameless = TextureManager.loadAsset("foodmakers/frying_pan.png"),
            Flame1 = TextureManager.loadAsset("foodmakers/frying_pan_2.png"),
            Flame2 = TextureManager.loadAsset("foodmakers/frying_pan_3.png");
    }

}
