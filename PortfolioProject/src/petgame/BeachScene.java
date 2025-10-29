// --- Pet Haven --- 
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class BeachScene extends BasePetScene {

    public BeachScene(MainApp mainApp, Pet Pet) {
        super(mainApp, Pet);
    }

    @Override
    protected String getBackgroundPath() {
       return "/petgame/assets/backgrounds/beach_background.jpeg";
    }

    @Override
    protected String getSceneTitle() {
        return "At the Beach 🌊";
    }

    @Override
    protected HBox buildCareButtons() {
        HBox box = new HBox(12);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);

        // --- Play in Water ---
        Button playBtn = new Button("Play in Water");
        //playBtn.setOnAction(e -> {
        //    activePet.changeHappiness(+10);
        //    activePet.changeEnergy(-5);
        //    updateStatsAndHearts();
        //});

        // --- Dig in Sand ---
        Button digBtn = new Button("Dig in Sand");
        //digBtn.setOnAction(e -> {
        //    activePet.changeHappiness(+6);
        //    activePet.changeEnergy(-4);
        //    activePet.changeHunger(+3);
        //    updateStatsAndHearts();
        //});

        // --- Rest in Shade ---
        Button restBtn = new Button("Rest in Shade");
        //restBtn.setOnAction(e -> {
        //    activePet.changeEnergy(+10);
        //    activePet.changeHappiness(+3);
        //    updateStatsAndHearts();
        //});

        box.getChildren().addAll(playBtn, digBtn, restBtn);
        return box;
    }

    @Override
    protected void onEnvironmentTick(Pet p) {
        // Every 5 seconds, gently lower hunger/energy
        p.decreaseHunger(2);
        p.decreaseEnergy(1);

        // If hunger gets low, happiness dips
        if (p.getHunger() < 30) {
            p.decreaseHappiness(-2);
        }

        updateStatsAndHearts();
    }
}