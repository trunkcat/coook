package io.trunkcat.cook.interfaces;

import com.badlogic.gdx.graphics.Texture;

import io.trunkcat.cook.TextureManager;

public interface Textures {
    Texture Plate = TextureManager.loadAsset("foodholders/plate.png");
    Texture PlateWithBun = TextureManager.loadAsset("foodholders/base-burger-plate.png");
    Texture PlateWithBunAndPatty = TextureManager.loadAsset("foodholders/patty-burger-plate.png");
    Texture PattyTray = TextureManager.loadAsset("fooditems/pattytray.png");
    Texture BunTray = TextureManager.loadAsset("foodholders/bun-tray.png");
    Texture Bun = TextureManager.loadAsset("fooditems/Burger/baseburger.png");

    Texture CounterTop = TextureManager.loadAsset("environment/counter_top.png");

    interface ColaCup {
        Texture Empty = TextureManager.loadAsset("foodholders/cup.png"),
            Full = TextureManager.loadAsset("foodholders/cup_full.png");
    }

    interface ColaDispenser {
        Texture Sheet = TextureManager.loadAsset("foodmakers/dispenser-Sheet.png"),
            Full = TextureManager.loadAsset("foodmakers/dispenser_full.png"),
            Static = TextureManager.loadAsset("foodmakers/dispenser.png");
    }

    interface Customers {
        interface Sunita {
            Texture
                Happy = TextureManager.loadAsset("customers/sunita_happy.png"),
                Neutral = TextureManager.loadAsset("customers/sunita_neutral.png"),
                Impatient = TextureManager.loadAsset("customers/sunita_impatient.png");
        }

        interface Muhammad {
            Texture
                Happy = TextureManager.loadAsset("customers/muhammad_happy.png"),
                Neutral = TextureManager.loadAsset("customers/muhammad_neutral.png"),
                Impatient = TextureManager.loadAsset("customers/muhammad_impatient.png");
        }

        interface Ramesh {
            Texture
                Happy = TextureManager.loadAsset("customers/ramesh_happy.png"),
                Neutral = TextureManager.loadAsset("customers/ramesh_neutral.png"),
                Impatient = TextureManager.loadAsset("customers/ramesh_impatient.png");
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
