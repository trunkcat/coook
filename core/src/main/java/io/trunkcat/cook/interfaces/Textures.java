package io.trunkcat.cook.interfaces;

import com.badlogic.gdx.graphics.Texture;

import io.trunkcat.cook.TextureManager;

public interface Textures {
    Texture BREAD_SLICE_TEXTURE = TextureManager.loadAsset("bread.png");

    interface FryingPan {
        interface Steak {
            Texture Default = Steak.Empty,
                Empty = TextureManager.loadAsset("temporary/pan_empty.png"),
                Cooking = TextureManager.loadAsset("temporary/pan_cooking.png"),
                Cooked = TextureManager.loadAsset("temporary/pan_cooked.png"),
                Overcooking = TextureManager.loadAsset("temporary/pan_overcooking.png"),
                Ruined = TextureManager.loadAsset("temporary/pan_ruined.png");
        }
    }

}
