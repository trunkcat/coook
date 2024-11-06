package io.trunkcat.cook;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.trunkcat.cook.actors.customers.Muhammad;
import io.trunkcat.cook.actors.customers.Ramesh;
import io.trunkcat.cook.actors.customers.Sunita;
import io.trunkcat.cook.components.Customer;
import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.OtherConstants;

public class CustomerManager {
    final int CUSTOMER_TYPES = 3;

    List<Customer> customers = new ArrayList<>();

    final private Random random;
    final private DragAndDrop dragAndDrop;
    final private Stage stage;

    public CustomerManager(final Stage stage, final DragAndDrop dragAndDrop, final Random random) {
        this.random = random;
        this.stage = stage;
        this.dragAndDrop = dragAndDrop;
    }

    public void spawnCustomer() {
        if (customers.size() >= OtherConstants.MAXIMUM_CUSTOMERS_AT_ONCE) {
            return; // Do not spawn more than the limit.
        }

    }

    public ItemID[] getOrderList() {
        // TODO implement random order generation by randoms and logics.

        return new ItemID[]{};
    }

    public Customer createRandomCustomer() {
        int selection = this.random.nextInt(CUSTOMER_TYPES);
        switch (selection) {
            case 0:
                return new Sunita(stage, dragAndDrop, getOrderList(), random);
            case 1:
                return new Ramesh(stage, dragAndDrop, getOrderList(), random);
            case 2:
            default:
                return new Muhammad(stage, dragAndDrop, getOrderList(), random);
        }
    }
}
