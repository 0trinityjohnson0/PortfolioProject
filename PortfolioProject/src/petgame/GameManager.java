// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// GameManager class -- Manages the overall state of the game. 
// Keeps track of all adopted pets, the player's current location, and inventory. 
// Handles adding pets, changing locations, and (eventually) saving and loading game data.

package petgame;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private List<Pet> pets;
    private Location currentLocation;
    private Inventory inventory;

    public GameManager() {
        pets = new ArrayList<>();
        inventory = new Inventory();
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void changeLocation(Location newLocation) {
        this.currentLocation = newLocation;
    }

    public void saveGame() {
        // TODO: Implement save logic
    }

    public void loadGame() {
        // TODO: Implement load logic
    }
}