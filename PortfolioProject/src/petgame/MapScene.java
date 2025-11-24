package petgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.InputStream;

public class MapScene {

    private BorderPane layout;
    private MainApp mainApp;
    private Pet pet;

    public MapScene(MainApp mainApp, Pet pet) {
        this.mainApp = mainApp;
        this.pet = pet;
        createLayout();
    }

    private void createLayout() {
        layout = new BorderPane();
        layout.setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);

        // 🔶 Add padding similar to BasePetScene
        layout.setPadding(new Insets(15, 20, 15, 20));

        // ---------- TOP BAR ----------
        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(10, 20, 10, 20));

        // 🔶 Add bottom separator line (same as BasePetScene)
        topBar.setStyle("""
            -fx-border-color: #d4b483;
            -fx-border-width: 0 0 2 0;
        """);

        Text title = new Text("Choose a Destination");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        StackPane centeredTitle = new StackPane(title);
        topBar.setCenter(centeredTitle);
        layout.setTop(topBar);

        // ---------- CENTER MAP ----------
        AnchorPane mapPane = new AnchorPane();
        mapPane.setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT - 120);

        InputStream bgStream = getClass().getResourceAsStream("/petgame/assets/backgrounds/map_background.png");
        Image mapBackground = (bgStream != null)
                ? new Image(bgStream)
                : new Image(getClass().getResourceAsStream("/petgame/assets/placeholder.png"));

        ImageView mapView = new ImageView(mapBackground);
        mapView.setFitWidth(MainApp.WINDOW_WIDTH - 60);
        mapView.setPreserveRatio(true);

        AnchorPane.setTopAnchor(mapView, 0.0);
        AnchorPane.setLeftAnchor(mapView, 0.0);

        mapPane.getChildren().add(mapView);

        // ---------- BUTTONS ----------
        Button parkBtn = styledButton("Park");
        Button beachBtn = styledButton("Beach");
        Button mountainBtn = styledButton("Mountains");
        Button adoptionBtn = styledButton("Adoption Center");
        Button homeBtn = styledButton("Go Home");

        boolean canAdopt = mainApp.canAdoptAnotherPet();
        adoptionBtn.setDisable(!canAdopt);

        // Button actions
        parkBtn.setOnAction(e -> mainApp.showParkScene(pet));
        beachBtn.setOnAction(e -> mainApp.showBeachScene(pet));
        mountainBtn.setOnAction(e -> mainApp.showMountainScene(pet));
        adoptionBtn.setOnAction(e -> {
            if (canAdopt) mainApp.showAdoptionCenter();
        });
        homeBtn.setOnAction(e -> mainApp.showHomeScene(pet));

        // Position buttons
        AnchorPane.setTopAnchor(parkBtn, 50.0);
        AnchorPane.setLeftAnchor(parkBtn, 90.0);

        AnchorPane.setTopAnchor(beachBtn, 50.0);
        AnchorPane.setRightAnchor(beachBtn, 90.0);

        AnchorPane.setBottomAnchor(mountainBtn, 50.0);
        AnchorPane.setLeftAnchor(mountainBtn, 90.0);

        AnchorPane.setBottomAnchor(adoptionBtn, 50.0);
        AnchorPane.setRightAnchor(adoptionBtn, 90.0);

        AnchorPane.setBottomAnchor(homeBtn, 50.0);
        AnchorPane.setLeftAnchor(homeBtn, MainApp.WINDOW_WIDTH / 2.0 - 70);

        mapPane.getChildren().addAll(parkBtn, beachBtn, mountainBtn, adoptionBtn, homeBtn);

        layout.setCenter(mapPane);

        // ---------- BOTTOM SEPARATOR LINE ----------
        // (Optional, but visually nice)
        Region bottomSpacer = new Region();
        bottomSpacer.setPrefHeight(10);
        bottomSpacer.setStyle("""
            -fx-border-color: #d4b483;
            -fx-border-width: 2 0 0 0;
        """);

        layout.setBottom(bottomSpacer);
    }

    // ---------- Unified button styling (matches BasePetScene) ----------
    private Button styledButton(String text) {
        Button b = new Button(text);
        b.setPrefWidth(160);

        b.setStyle("""
            -fx-font-size: 16px;
            -fx-background-color: #ffd27f;
            -fx-background-radius: 10;
            -fx-padding: 8 18;
            -fx-border-radius: 10;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """);

        b.setOnMouseEntered(e -> b.setStyle("""
            -fx-font-size: 16px;
            -fx-background-color: #ffe2a8;
            -fx-background-radius: 10;
            -fx-padding: 8 18;
            -fx-border-radius: 10;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """));

        b.setOnMouseExited(e -> b.setStyle("""
            -fx-font-size: 16px;
            -fx-background-color: #ffd27f;
            -fx-background-radius: 10;
            -fx-padding: 8 18;
            -fx-border-radius: 10;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """));

        return b;
    }

    public BorderPane getLayout() {
        return layout;
    }
}
