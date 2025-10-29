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

    private final BorderPane layout;
    private final MainApp mainApp;
    private final Pet pet;

    public MapScene(MainApp mainApp, Pet pet) {
        this.mainApp = mainApp;
        this.pet = pet;
        this.layout = new BorderPane();
        createLayout();
    }

    private void createLayout() {
        layout.setPadding(new Insets(20));

        // ---------- Top: Title (matches style with other scenes) ----------
        BorderPane top = new BorderPane();
        Text title = new Text("Where would you like to go?");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");
        top.setCenter(title);
        layout.setTop(top);

        // ---------- Left: Sidebar (Main Menu / Save / Settings) ----------
        VBox sideBar = new VBox(12);
        sideBar.setAlignment(Pos.TOP_LEFT);
        sideBar.setPadding(new Insets(10, 20, 10, 0));

        Button mainMenuBtn = new Button("Main Menu");
        Button saveBtn = new Button("Save Game");
        Button settingsBtn = new Button("Settings");

        // Keep sizing and style consistent with other scenes’ buttons
        for (Button b : new Button[]{mainMenuBtn, saveBtn, settingsBtn}) {
            b.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        }

        mainMenuBtn.setOnAction(e -> mainApp.showMainMenu());
        saveBtn.setOnAction(e -> {
            // TODO: implement save later
            System.out.println("Save feature coming soon...");
        });
        settingsBtn.setOnAction(e -> {
            // TODO: settings later
            System.out.println("Settings not implemented yet.");
        });

        sideBar.getChildren().addAll(mainMenuBtn, saveBtn, settingsBtn);
        layout.setLeft(sideBar);

        // ---------- Center: Map centered with buttons overlayed at corners ----------
        // Use a StackPane to center the map area; inside it, an AnchorPane to position buttons by corners.
        StackPane centerStack = new StackPane();
        centerStack.setAlignment(Pos.CENTER);

        AnchorPane mapPane = new AnchorPane();
        mapPane.setPrefSize(800, MainApp.WINDOW_HEIGHT - 160); // 800 wide to match other scenes; room for top/side padding
        mapPane.setMaxWidth(800);

        // Map background
        Image mapBackground = loadImageOrPlaceholder("/petgame/assets/backgrounds/map_background.png");
        ImageView mapView = new ImageView(mapBackground);
        mapView.setFitWidth(800);             // match BasePetScene background width
        mapView.setPreserveRatio(true);

        // anchor the map to the pane (0,0)
        AnchorPane.setTopAnchor(mapView, 0.0);
        AnchorPane.setLeftAnchor(mapView, 0.0);
        mapPane.getChildren().add(mapView);

        // ---------- Location Buttons (kept at corners as before) ----------
        Button parkBtn = new Button("Park");
        Button beachBtn = new Button("Beach");
        Button mountainBtn = new Button("Mountains");
        Button adoptionBtn = new Button("Adoption Center");
        Button homeBtn = new Button("Go Home");

        for (Button b : new Button[]{parkBtn, beachBtn, mountainBtn, adoptionBtn, homeBtn}) {
            b.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        }

        // Adoption availability
        boolean canAdopt = mainApp.canAdoptAnotherPet();
        adoptionBtn.setDisable(!canAdopt);
        if (!canAdopt) {
            adoptionBtn.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-opacity: 0.5;");
        }

        // Actions
        parkBtn.setOnAction(e -> mainApp.showParkScene(pet));
        beachBtn.setOnAction(e -> mainApp.showBeachScene(pet));
        mountainBtn.setOnAction(e -> mainApp.showMountainScene(pet));
        adoptionBtn.setOnAction(e -> { if (canAdopt) mainApp.showAdoptionCenter(); });
        homeBtn.setOnAction(e -> mainApp.showHomeScene(pet));

        // Corner-style positions (relative to the 800px mapPane)
        // Top-left
        AnchorPane.setTopAnchor(parkBtn, 30.0);
        AnchorPane.setLeftAnchor(parkBtn, 30.0);

        // Top-right
        AnchorPane.setTopAnchor(beachBtn, 30.0);
        AnchorPane.setRightAnchor(beachBtn, 30.0);

        // Bottom-left
        AnchorPane.setBottomAnchor(mountainBtn, 70.0);
        AnchorPane.setLeftAnchor(mountainBtn, 30.0);

        // Bottom-right
        AnchorPane.setBottomAnchor(adoptionBtn, 70.0);
        AnchorPane.setRightAnchor(adoptionBtn, 30.0);

        // Bottom-center (Home)
        AnchorPane.setBottomAnchor(homeBtn, 70.0);
        // center horizontally by using left anchor = (paneWidth - btnWidth)/2 after layout.
        // Since we don't have btn width now, approximate by anchoring to 50% using translateX:
        // Place in the middle with left= (800/2 - 60) — rough centering that looks good.
        AnchorPane.setLeftAnchor(homeBtn, 800 / 2.0 - 60); // tweak the 60 if your button width changes

        mapPane.getChildren().addAll(parkBtn, beachBtn, mountainBtn, adoptionBtn, homeBtn);

        // Center the whole mapPane (with its buttons) in the window
        centerStack.getChildren().add(mapPane);
        StackPane.setAlignment(mapPane, Pos.CENTER);
        layout.setCenter(centerStack);
    }

    private Image loadImageOrPlaceholder(String path) {
        InputStream bgStream = getClass().getResourceAsStream(path);
        if (bgStream != null) return new Image(bgStream);

        System.err.println("⚠️ Missing image: " + path + " — using placeholder");
        InputStream ph = getClass().getResourceAsStream("/petgame/assets/placeholder.png");
        return ph != null ? new Image(ph) : new Image("data:,"); // ultra-fallback
    }

    public BorderPane getLayout() {
        return layout;
    }
}