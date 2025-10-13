// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// Location class -- Abstract base class for places the player can visit with their pets.
// Each location has a name and defines an abstract `enter` method that handles what happens
// when the player visits that location (displaying pets, starting minigames, or interacting with the environment).

package petgame;

public abstract class Location {
    protected String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void enter(GameManager game);
}