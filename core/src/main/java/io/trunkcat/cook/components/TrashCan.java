package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class TrashCan extends ImageActor {
    public static final ItemID ITEM_ID = ItemID.TRASH_CAN;
    public static final Texture TEXTURE = Textures.TrashCan.Closed;

    public TrashCan(final DragAndDrop dragAndDrop) {
        super(ITEM_ID, TEXTURE);

        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (payload == null) return false;
                if (payload.getDragActor() instanceof FoodItem) {
                    if (currentTexture != Textures.TrashCan.Opened)
                        updateTexture(Textures.TrashCan.Opened);
                    return true;
                }
                return false;
            }

            @Override
            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                super.reset(source, payload);
                if (currentTexture != Textures.TrashCan.Closed)
                    updateTexture(Textures.TrashCan.Closed);
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                updateTexture(Textures.TrashCan.Closed);

                if (payload == null) {
                    return;
                }

                if (payload.getDragActor() instanceof FoodItem) {
                    FoodItem item = (FoodItem) payload.getDragActor();
                    item.remove();
                }
            }
        });
    }


}
