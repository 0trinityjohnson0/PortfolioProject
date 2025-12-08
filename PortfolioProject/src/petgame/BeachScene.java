// --- Pet Haven --- 
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BeachScene extends BasePetScene {

    public BeachScene(MainApp mainApp, Pet pet) {
        super(mainApp, pet);
    }

    @Override
    protected String getBackgroundPath() {
        return "/petgame/assets/backgrounds/beach_background.jpeg";
    }

    @Override
    protected String getSceneTitle() {
        return "Beach";
    }

    @Override
    protected Pane buildCareButtons() {
        VBox box = new VBox(12);     // ← vertical buttons
        box.setAlignment(Pos.TOP_CENTER);

        Button playBtn = styledButton("Play in Water");
        Button digBtn = styledButton("Dig in Sand");
        Button restBtn = styledButton("Rest in Shade");

        playBtn.setOnAction(e -> {
            activePet.increaseHappiness(10);
            activePet.decreaseEnergy(5);
            updateStatsAndHearts();
        });

        digBtn.setOnAction(e -> {
            activePet.increaseHappiness(6);
            activePet.decreaseEnergy(4);
            activePet.increaseHunger(3);
            updateStatsAndHearts();
        });

        restBtn.setOnAction(e -> {
            activePet.increaseEnergy(10);
            activePet.increaseHappiness(3);
            updateStatsAndHearts();
        });

        box.getChildren().addAll(playBtn, digBtn, restBtn);
        return box;
    }

    @Override
    protected void onEnvironmentTick(Pet p) {
        p.decreaseEnergy(2); 		// Regular energy used
        p.decreaseHunger(2);		// Walking around
        p.increaseHappiness(1);		// Being outside makes you happy

        updateStatsAndHearts();
    }
}
