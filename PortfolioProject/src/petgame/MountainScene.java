// --- Pet Haven ---
// MountainScene -- Environment for pets to explore.

package petgame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MountainScene extends BasePetScene {

    public MountainScene(MainApp mainApp, Pet pet) {
        super(mainApp, pet);
    }

    @Override
    protected String getBackgroundPath() {
        return "/petgame/assets/backgrounds/mountain_background.png";
    }

    @Override
    protected String getSceneTitle() {
        return "Mountains";
    }

    @Override
    protected Pane buildCareButtons() {
        VBox box = new VBox(12);
        box.setAlignment(Pos.TOP_CENTER);

        Button exploreBtn = styledButton("Explore Trail");
        Button sniffBtn = styledButton("Sniff Around");
        Button restBtn = styledButton("Rest on Rock");

        exploreBtn.setOnAction(e -> {
            activePet.increaseEnergy(-6);     // exploring takes energy
            activePet.increaseHappiness(8);   // but it's fun
            activePet.increaseHunger(3);
            updateStatsAndHearts();
        });

        sniffBtn.setOnAction(e -> {
            activePet.increaseHappiness(4);
            activePet.decreaseEnergy(2);
            updateStatsAndHearts();
        });

        restBtn.setOnAction(e -> {
            activePet.increaseEnergy(10);
            activePet.increaseHappiness(2);
            updateStatsAndHearts();
        });

        box.getChildren().addAll(exploreBtn, sniffBtn, restBtn);
        return box;
    }

    @Override
    protected void onEnvironmentTick(Pet p) {
        // Mountains are cold and tiring:
        p.decreaseEnergy(2);
        p.decreaseHunger(2);

        // But pets tend to feel calm/happy in nature
        p.increaseHappiness(1);

        updateStatsAndHearts();
    }
}
