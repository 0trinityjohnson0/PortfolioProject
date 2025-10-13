// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// PetStats class -- this class stores the core statistics for a pet, including hunger, happiness, energy, and affection.
// Each pet will have an instance of PetStats to track its current state. 
// Provides getters and setters to read and update these values, which will affect gameplay interactions like feeding, playing, or grooming the pet

package petgame;

public class PetStats {
    private int hunger;
    private int happiness;
    private int energy;
    private int affection;

    public PetStats() {
        this.hunger = 50;
        this.happiness = 50;
        this.energy = 50;
        this.affection = 50;
    }

    // Getters and setters
    public int getHunger() { return hunger; }
    public void setHunger(int hunger) { this.hunger = hunger; }

    public int getHappiness() { return happiness; }
    public void setHappiness(int happiness) { this.happiness = happiness; }

    public int getEnergy() { return energy; }
    public void setEnergy(int energy) { this.energy = energy; }

    public int getAffection() { return affection; }
    public void setAffection(int affection) { this.affection = affection; }
}