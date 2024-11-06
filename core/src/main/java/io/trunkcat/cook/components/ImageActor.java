package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import io.trunkcat.cook.enums.ItemID;

public class ImageActor extends Image {
    ItemID itemId;
    protected Texture currentTexture;

    public ImageActor(final ItemID itemId, final Texture itemTexture) {
        super(itemTexture);
        this.itemId = itemId;
        this.setZIndex(5);
        this.setOrigin(Align.center); // Set the origin to the center.
    }

    public void updateTexture(final Texture texture) {
        this.currentTexture = texture;
        this.setDrawable(new TextureRegionDrawable(texture));
    }

    public void updateTexture(final TextureRegion region) {
        this.currentTexture = region.getTexture();
        this.setDrawable(new TextureRegionDrawable(region));
    }
}
