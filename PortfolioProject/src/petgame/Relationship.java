// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// Relationship class -- this class tracks the relationship between two pets. 
// It stores a friendshipLevel that increases as the pets interact or play together.
// Provides methods to increase friendship and to check if the pets are compatible (friendshipLevel >= 100)

package petgame;

public class Relationship {
    private Pet petA;
    private Pet petB;
    private int friendshipLevel;

    public Relationship(Pet petA, Pet petB) {
        this.petA = petA;
        this.petB = petB;
        this.friendshipLevel = 0;
    }

    public int getFriendshipLevel() { return friendshipLevel; }

    public void increaseFriendship(int amount) {
        friendshipLevel += amount;
        if (friendshipLevel > 100) friendshipLevel = 100;
    }

    public boolean canBreed() {
        return friendshipLevel >= 100;
    }
}