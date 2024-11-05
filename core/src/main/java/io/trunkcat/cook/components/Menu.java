/*
package io.trunkcat.cook.components;

import java.util.ArrayList;
import java.util.Random;

import io.trunkcat.cook.enums.MenuItem;


// Menu class to generate a random menu
public class Menu {

    // Method to generate a random menu with a specified number of items
    public static ArrayList<Object> generateRandomMenu(int numberOfItems) {
        Random random = new Random();
        MenuItem[] allItems = MenuItem.values();  // Get all enum values
        ArrayList<Object> randomMenu = new ArrayList<>();

        // Ensure the number of items doesn't exceed the available items
        numberOfItems = Math.min(numberOfItems, allItems.length);

        while (randomMenu.size() < numberOfItems) {
            MenuItem item = allItems[random.nextInt(allItems.length)];

            // Avoid duplicates in the random menu
            if (!randomMenu.contains(item)) {
                randomMenu.add(item);
            }
        }

        return randomMenu;
    }

    // Method to print the generated menu
    public static void printMenu(ArrayList<Object> menu) {
        System.out.println("Random Menu:");
        for (Object item : menu) {
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Generate a random menu with 5 items
        ArrayList<Object> randomMenu = generateRandomMenu(5);

        // Print the random menu
       // printMenu(randomMenu);
    }
}
*/
