// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// Home class -- Represents the "Home" location in the game. 
// Extends the Location class and defines behavior when the player enters the home. 
// Currently a placeholder; will later display the player’s pets and allow interactions.

package petgame;

public class Home extends Location {
    public Home() {
        super("Home");
    }

    @Override
    public void enter(GameManager game) {
        // TODO: Display pets at home
    }
}