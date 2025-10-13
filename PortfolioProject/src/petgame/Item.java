// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// Item class -- Abstract base class representing any object that can be stored in the player's inventory.
// Each item has a name. Specific item types (like Food, Toy, or Clothing) will extend this class.

package petgame;

public abstract class Item {
    protected String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}