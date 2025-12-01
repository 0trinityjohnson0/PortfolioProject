// --- Pet Haven --- 
// Game created by Trinity Johnson for CS 3250 Portfolio Project
//
// ParkScene -- Environments for pets to explore.

package petgame;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class ParkScene extends BasePetScene {

    public ParkScene(MainApp mainApp, Pet pet) {
        super(mainApp, pet);
    }

    @Override
    protected String getBackgroundPath() {
        return "/petgame/assets/backgrounds/park_background.png";
    }

    @Override
    protected String getSceneTitle() {
        return "Park";
    }

    @Override
    protected Pane buildCareButtons() {
        VBox box = new VBox(12);
        box.setAlignment(Pos.TOP_CENTER);

        Button runBtn = styledButton("Run Around");
        Button sniffBtn = styledButton("Sniff the Grass");
        Button relaxBtn = styledButton("Relax Under Tree");

        // RUN
        runBtn.setOnAction(e -> {
            for (Pet p : mainApp.getAdoptedPets()) {
                p.increaseHappiness(8);
                p.decreaseEnergy(6);
                p.increaseHunger(4);
            }
            updateStatsAndHearts();
        });

        // SNIFF
        sniffBtn.setOnAction(e -> {
            for (Pet p : mainApp.getAdoptedPets()) {
                p.increaseHappiness(5);
                p.decreaseEnergy(2);
            }
            updateStatsAndHearts();
        });

        // RELAX
        relaxBtn.setOnAction(e -> {
            for (Pet p : mainApp.getAdoptedPets()) {
                p.increaseEnergy(6);
                p.increaseHappiness(2);
            }
            updateStatsAndHearts();
        });

        box.getChildren().addAll(runBtn, sniffBtn, relaxBtn);
        return box;
    }

    @Override
    protected void onEnvironmentTick(Pet p) {
        p.decreaseHunger(2);      // Regular energy use
        p.decreaseEnergy(2);      // Walking around
        p.increaseHappiness(1);   // Being outside makes you happy

        updateStatsAndHearts();
    }
}

