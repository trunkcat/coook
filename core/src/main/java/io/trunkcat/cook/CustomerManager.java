package io.trunkcat.cook;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.trunkcat.cook.components.Customer;
import io.trunkcat.cook.components.OrderBox;
import io.trunkcat.cook.enums.ItemID;
import io.trunkcat.cook.interfaces.OtherConstants;
import io.trunkcat.cook.interfaces.Textures;
import io.trunkcat.cook.interfaces.TimeConstants;

public class CustomerManager extends Actor {
    final int CUSTOMER_FLAVORS = 3;

    private float cooldownPeriod;

    private final float[][] coordinates;
    private final boolean[] spotsFilled = new boolean[OtherConstants.MAX_CUSTOMERS_AT_ONCE];

    List<Customer> customers = new ArrayList<>();

    final private Random random;
    final private DragAndDrop dragAndDrop;
    final private Stage stage;

    public CustomerManager(final float[][] coordinates, final Stage stage, final DragAndDrop dragAndDrop, final Random random) throws Exception {
        if (coordinates.length != OtherConstants.MAX_CUSTOMERS_AT_ONCE) {
            throw new Exception("There should be enough coordinate pairs to contain all the customers that can be present at a time.");
        } else if (Arrays.stream(coordinates).anyMatch(c -> c.length != 2)) {
            throw new Exception("Coordinates must have both x and y.");
        }
        this.coordinates = coordinates;
        Arrays.fill(spotsFilled, false); // They aren't filled at first.

        this.cooldownPeriod = TimeConstants.CUSTOMER_SPAWN_COOLDOWN;

        this.random = random;
        this.stage = stage;
        this.dragAndDrop = dragAndDrop;
    }

    public void spawnCustomer() {
        if (customers.size() >= OtherConstants.MAX_CUSTOMERS_AT_ONCE) {
            return; // Do not spawn more than the limit.
        }
        int availableSpot = getAvailableSpot();
        if (availableSpot < 0) return;

        Customer customer = createRandomCustomer(availableSpot);

        float[] position = coordinates[availableSpot];
        customer.setSize(300 / 1.5f, 450 / 1.5f);
        customer.setPosition(position[0], position[1]);
        customer.setZIndex(0);

        customer.orderBox = new OrderBox(customer.getOrderBoxTexture());
        customer.orderBox.addAction(Actions.fadeIn(2));
        customer.orderBox.setOrigin(Align.bottomLeft);
        customer.orderBox.setPosition(customer.getX() + customer.getWidth() + 20, customer.getY() + 20);
        customer.orderBox.setScale(3);
        stage.addActor(customer.orderBox);

        spotsFilled[availableSpot] = true;
        stage.addActor(customer);
    }

    public void despawnCustomer(Customer customer, int position) {
        customer.orderBox.remove();
        customer.remove();
        spotsFilled[position] = false;
    }

    // Find all available spots, and choose a random one.
    private int getAvailableSpot() {
        List<Integer> spots = new ArrayList<>();
        for (int i = 0; i < spotsFilled.length; i++) {
            if (!spotsFilled[i])
                spots.add(i);
        }
        if (spots.isEmpty()) {
            return -1;
        }
        int randomIndex = random.nextInt(spots.size());
        return spots.get(randomIndex);
    }

    // TODO implement a more random order generation by randoms and logics.
    public List<ItemID> getOrderList() {
        List<ItemID> orders = new ArrayList<>();
        float randomPoint = (float) Math.random();
        if (randomPoint <= 0.33) {
            orders.add(ItemID.BUN_PATTY);
        } else if (randomPoint <= 0.66) {
            orders.add(ItemID.COLA_CUP);
        } else {
            orders.add(ItemID.BUN_PATTY);
            orders.add(ItemID.COLA_CUP);
        }
        return orders;
    }

    public Customer createRandomCustomer(int position) {
        int selection = this.random.nextInt(CUSTOMER_FLAVORS);
        switch (selection) {
            case 0:
                return new Customer(this, position, ItemID.CUSTOMER, Textures.Customers.Muhammad.Happy, stage, dragAndDrop, getOrderList(), random) {
                    @Override
                    public Texture getStatusTexture() {
                        switch (emotion) {
                            case Happy:
                                return Textures.Customers.Muhammad.Happy;
                            case Impatient:
                                return Textures.Customers.Muhammad.Impatient;
                            case Neutral:
                            default:
                                return Textures.Customers.Muhammad.Neutral;
                        }
                    }
                };
            case 1:
                return new Customer(this, position, ItemID.CUSTOMER, Textures.Customers.Ramesh.Happy, stage, dragAndDrop, getOrderList(), random) {
                    @Override
                    public Texture getStatusTexture() {
                        switch (emotion) {
                            case Happy:
                                return Textures.Customers.Ramesh.Happy;
                            case Impatient:
                                return Textures.Customers.Ramesh.Impatient;
                            case Neutral:
                            default:
                                return Textures.Customers.Ramesh.Neutral;
                        }
                    }
                };
            case 2:
                return new Customer(this, position, ItemID.CUSTOMER, Textures.Customers.Sunita.Happy, stage, dragAndDrop, getOrderList(), random) {
                    @Override
                    public Texture getStatusTexture() {
                        switch (emotion) {
                            case Happy:
                                return Textures.Customers.Sunita.Happy;
                            case Impatient:
                                return Textures.Customers.Sunita.Impatient;
                            case Neutral:
                            default:
                                return Textures.Customers.Sunita.Neutral;
                        }
                    }
                };
        }
        return null;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (getAvailableSpot() < 0) return;

        // TODO: how should the manager act?
        if (cooldownPeriod >= 0) {
            cooldownPeriod -= delta;
            return;
        }

        this.spawnCustomer();
        cooldownPeriod = TimeConstants.CUSTOMER_SPAWN_COOLDOWN;
    }
}
