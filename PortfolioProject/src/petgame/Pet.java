// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// Pet class -- Represents a pet in the game (dog or cat). 
// Stores attributes like name, species, breed, gender, age, happiness, hunger, and favorites. 
// Contains methods to modify stats and interact with the pet.

package petgame;

public class Pet {

    private String name;
    private String species;
    private String breed;
    private String gender;

    // Core stats
    private int happiness;
    private int hunger;   
    private int energy;
    private int relationship; // 0-100

    private static final int MAX_STAT = 100;

    // ---------- Constructor ----------
    public Pet(String name, String species, String breed, String gender) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.gender = gender;

        this.happiness = 50;
        this.hunger = 50;
        this.energy = 50;
        this.relationship = 0;
    }

    // ---------- Getters ----------
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public String getBreed() { return breed; }
    public String getGender() { return gender; }
    public int getHappiness() { return happiness; }
    public int getHunger() { return hunger; }
    public int getEnergy() { return energy; }
    public int getRelationship() { return relationship; }

    public int getRelationshipHearts() {
        return (int) Math.ceil(relationship / 20.0);
    }

    // ---------- Setters ----------
    public void setName(String name) { this.name = name; }
    public void setSpecies(String species) { this.species = species; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setGender(String gender) { this.gender = gender; }

    // ---------- Stat modification ----------
    private int clamp(int value) {
        return Math.max(0, Math.min(value, MAX_STAT));
    }

    private void changeStat(String stat, int amount) {
        switch (stat.toLowerCase()) {
            case "happiness":
                happiness = clamp(happiness + amount);
                break;
            case "hunger":
                hunger = clamp(hunger + amount);
                break;
            case "energy":
                energy = clamp(energy + amount);
                break;
            case "relationship":
                relationship = clamp(relationship + amount);
                break;
        }
    }

    // ---------- Convenience wrappers ----------
    public void feed() {
        changeStat("hunger", 20);
        changeStat("happiness", 5);
        changeStat("relationship", 5);
    }

    public void play() {
        changeStat("happiness", 10);
        changeStat("relationship", 10);
        changeStat("energy", -5); // Playing reduces energy
    }

    public void groom() {
        changeStat("happiness", 5);
        changeStat("relationship", 5);
    }


    // ---------- Passive changes ----------
    public void decayOverTime() {
        changeStat("hunger", -2);
        changeStat("happiness", -1);
        changeStat("energy", 2);
    }
    
 // ---------- stat methods for readability ----------
    public void decreaseHunger(int amount) { changeStat("hunger", -amount); }
    public void increaseHunger(int amount) { changeStat("hunger", amount); }

    public void decreaseHappiness(int amount) { changeStat("happiness", -amount); }
    public void increaseHappiness(int amount) { changeStat("happiness", amount); }

    public void decreaseEnergy(int amount) { changeStat("energy", -amount); }
    public void increaseEnergy(int amount) { changeStat("energy", amount); }
    

    // ---------- Utility ----------
    @Override
    public String toString() {
        return name + " the " + breed + " " + species +
                " [Happiness: " + happiness +
                ", Hunger: " + hunger +
                ", Energy: " + energy +
                ", Relationship: " + relationship + "]";
    }
}