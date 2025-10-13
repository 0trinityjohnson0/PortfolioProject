// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// Pet class -- Represents a pet in the game (dog or cat). 
// Stores attributes like name, species, breed, gender, age, happiness, hunger, and favorites. 
// Contains methods to modify stats and interact with the pet.

package petgame;

public class Pet {
    private String name;
    private String species; // "Dog" or "Cat"
    private String breed;
    private String gender;

    private int happiness;
    private int hunger;
    private int energy = 50;
    
    // Constructor
    public Pet(String name, String species, String breed, String gender) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.gender = gender;
        
     // Initialize stats
        this.happiness = 50;
        this.hunger = 50;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }
    
    public int getHappiness() {
        return happiness;
    }

    public int getHunger() {
        return hunger;
    }
    
    public int getEnergy() {
        return energy;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    
 // ---------- Stat modification methods ----------
    public void increaseHappiness(int amount) {
        happiness += amount;
        if (happiness > 100) happiness = 100;
    }

    public void decreaseHappiness(int amount) {
        happiness -= amount;
        if (happiness < 0) happiness = 0;
    }

    public void increaseHunger(int amount) {
        hunger += amount;
        if (hunger > 100) hunger = 100;
    }

    public void decreaseHunger(int amount) {
        hunger -= amount;
        if (hunger < 0) hunger = 0;
    }

    public void increaseEnergy(int amount) {
        energy += amount;
        if (energy > 100) energy = 100;
    }

    public void decreaseEnergy(int amount) {
        energy -= amount;
        if (energy < 0) energy = 0;
    }
}
