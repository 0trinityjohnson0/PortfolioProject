// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

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