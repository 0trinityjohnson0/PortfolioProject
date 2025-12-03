package petgame;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SplashScreen {

    private final MainApp mainApp;

    public SplashScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene() {

        // Load logo
        Image logo = new Image(
            getClass().getResourceAsStream("/petgame/assets/Logo/PetGameLogo.png"),
            350,   // width
            0,     // auto height
            true,  // preserve ratio
            false  // DO NOT smooth → keeps pixel art crisp
        );

        ImageView logoView = new ImageView(logo);
        logoView.setOpacity(0);      // invisible at first
        logoView.setTranslateX(-600); // start far left, slide in

        StackPane root = new StackPane(logoView);
        root.setStyle("-fx-background-color: #fff8f0;");
        root.setAlignment(Pos.CENTER);

        Scene splashScene = new Scene(
                root,
                MainApp.WINDOW_WIDTH,
                MainApp.WINDOW_HEIGHT
        );

        // --- SLIDE ANIMATION ---
        TranslateTransition slide = new TranslateTransition(Duration.seconds(1.2), logoView);
        slide.setFromX(-600);
        slide.setToX(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        // --- FADE IN ---
        FadeTransition fade = new FadeTransition(Duration.seconds(1.2), logoView);
        fade.setFromValue(0);
        fade.setToValue(1);

        ParallelTransition appear = new ParallelTransition(slide, fade);

        // After sliding in, wait 1.2 seconds → go to main menu
        PauseTransition delay = new PauseTransition(Duration.seconds(1.2));

        SequentialTransition sequence = new SequentialTransition(
                appear,
                delay
        );

        sequence.setOnFinished(e -> mainApp.showMainMenu());
        sequence.play();

        return splashScene;
    }
}
