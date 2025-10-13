// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// Inventory class -- Manages a collection of items the player has collected.
// Provides methods to add items and retrieve the current list of items.

package petgame;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) { items.add(item); }

    public List<Item> getItems() { return items; }
}