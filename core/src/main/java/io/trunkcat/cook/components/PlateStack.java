package io.trunkcat.cook.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import java.util.Arrays;

import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.Textures;

public class PlateStack extends ImageActor {
    final static ItemID ITEM_ID = ItemID.PLATE_TRAY;
    final static Texture PLATE_STACK = Textures.PlateStack;

    public static float[][] positions = new float[][]{{450, 200}, {450, 400}, {750, 400}, {750, 200}};
    public boolean[] spotsFilled = new boolean[4];

    public PlateStack(final DragAndDrop dragAndDrop, final Stage stage) {
        super(ITEM_ID, PLATE_STACK);

        Arrays.fill(spotsFilled, false);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                int availablePositionIndex = getAvailablePosition();
                if (availablePositionIndex < 0) {
                    return;
                }
                float[] position = positions[availablePositionIndex];
                Plate plate = new Plate(stage, dragAndDrop) {
                    @Override
                    public boolean remove() {
                        markAsAvailable(availablePositionIndex);
                        return super.remove();
                    }
                };
                plate.setSize(250, 150);
                plate.setOrigin(Align.center);
                plate.setPosition(position[0], position[1]);
                spotsFilled[availablePositionIndex] = true;
                stage.addActor(plate);
            }
        });
    }

    public void markAsAvailable(int position) {
        spotsFilled[position] = false;
    }

    public int getAvailablePosition() {
        for (int i = 0; i < spotsFilled.length; i++) {
            if (!spotsFilled[i])
                return i;
        }
        return -1;
    }
}
