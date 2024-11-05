package io.trunkcat.cook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    static public Texture loadAsset(String path) {
        return new Texture(Gdx.files.internal(path));
    }
}
