package io.trunkcat.cook;

import io.trunkcat.cook.enums.ItemID;

public class FoodInfo {
    public static int getItemValue(ItemID itemID) {
        switch (itemID) {
            case BREAD_SLICE:
                return 10;

            // Internal items with no value.
            case PLATE:
            case CUSTOMER:
            case BREAD_SLICE_TRAY:
            default:
                return 0;
        }
    }

    public static boolean isFood(ItemID itemID) {
        switch (itemID) {
            case BREAD_SLICE:
                return true;

            // Internal items that are not food.
            case PLATE:
            case CUSTOMER:
            case BREAD_SLICE_TRAY:
            default:
                return false;
        }
    }
}
