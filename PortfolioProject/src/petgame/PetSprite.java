// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project
//
// PetSprite -- Handles animation and movement for a Pet object in the world.
// Uses AssetCache class to load and reuse sprite sheets efficiently.

package petgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PetSprite {

    private final Pet pet;
    private final ImageView view;
    private final Random random = new Random();

    // Timelines
    private Timeline currentAnim;
    private Timeline aiTimeline;

    // Layout / movement
    private double baseX = 0;
    private double baseYOffset = -90;
    private double xPosition = 0;
    private double moveRange = 150;
    private double pixelsPerStep = 2.0;

    // Timing
    private int idleMsPerFrame = 220;
    private int walkMsPerFrame = 140;

    // Cached animation sheets for this pet (Idle / Walk / Lay)
    private final Map<String, SheetInfo> sheetCache = new HashMap<>();

    // ---------- Constructor ----------
    public PetSprite(Pet pet) {
        this.pet = pet;
        this.view = new ImageView();
        view.setTranslateY(baseYOffset);

        playIdle();
        startRandomBehavior();
    }

    public ImageView getView() {
        return view;
    }

    // ---------- Inner Class ----------
    private static class SheetInfo {
        final Image sheet;
        final int frameSize;
        final int numFrames;

        SheetInfo(Image sheet, int frameSize, int numFrames) {
            this.sheet = sheet;
            this.frameSize = frameSize;
            this.numFrames = Math.max(1, numFrames);
        }
    }

    // ---------- Sprite Sheet Loading ----------
    private SheetInfo loadSheet(String relativePath) {
        // Check if this sheet is already cached locally (per pet)
        if (sheetCache.containsKey(relativePath)) {
            return sheetCache.get(relativePath);
        }

        // Load using global AssetCache
        Image img = AssetCache.getImage(relativePath);
        if (img == null) return null;

        // Compute frame info (sprites are square frames in one row)
        int frameSize = (int) Math.round(img.getHeight());
        if (frameSize <= 0) frameSize = (int) Math.round(img.getWidth());

        int numFrames = (int) Math.floor(img.getWidth() / frameSize);
        if (numFrames <= 0) numFrames = 1;

        SheetInfo info = new SheetInfo(img, frameSize, numFrames);

        // Store in per-pet cache
        sheetCache.put(relativePath, info);
        return info;
    }

    private void stopCurrentAnim() {
        if (currentAnim != null) {
            currentAnim.stop();
            currentAnim = null;
        }
    }

    // ---------- IDLE ----------
    private void playIdle() {
        stopCurrentAnim();

        String base = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/";
        SheetInfo idle = loadSheet(base + "Idle.png");
        if (idle == null) return;

        view.setImage(idle.sheet);
        view.setViewport(new Rectangle2D(0, 0, idle.frameSize, idle.frameSize));
        view.setFitWidth(150);
        view.setPreserveRatio(true);
        view.setTranslateY(baseYOffset);

        final int[] frame = {0};
        currentAnim = new Timeline(new KeyFrame(Duration.millis(idleMsPerFrame), e -> {
            frame[0] = (frame[0] + 1) % idle.numFrames;
            view.setViewport(new Rectangle2D(frame[0] * idle.frameSize, 0, idle.frameSize, idle.frameSize));
        }));
        currentAnim.setCycleCount(Timeline.INDEFINITE);
        currentAnim.play();
    }

    // ---------- WALK ----------
    private void walkLeft() { walk(true); }
    private void walkRight() { walk(false); }

    private void walk(boolean left) {
        stopCurrentAnim();

        String base = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/";
        SheetInfo walk = loadSheet(base + "Walk.png");
        if (walk == null) return;

        view.setImage(walk.sheet);
        view.setViewport(new Rectangle2D(0, 0, walk.frameSize, walk.frameSize));
        view.setFitWidth(150);
        view.setPreserveRatio(true);
        view.setTranslateY(baseYOffset);
        view.setScaleX(left ? -1 : 1);

        final int[] frame = {0};
        final double dx = (left ? -1 : 1) * pixelsPerStep;

        currentAnim = new Timeline(new KeyFrame(Duration.millis(walkMsPerFrame), e -> {
            frame[0] = (frame[0] + 1) % walk.numFrames;
            view.setViewport(new Rectangle2D(frame[0] * walk.frameSize, 0, walk.frameSize, walk.frameSize));

            double nextX = xPosition + dx;
            if (nextX < -moveRange || nextX > moveRange) {
                playIdle();
                return;
            }
            xPosition = nextX;
            view.setTranslateX(baseX + xPosition);
        }));

        int steps = 24 + random.nextInt(9);
        currentAnim.setCycleCount(steps);
        currentAnim.setRate(0.9 + random.nextDouble() * 0.3);
        currentAnim.setOnFinished(ev -> playIdle());
        currentAnim.play();
    }

    // ---------- LAY ----------
    public void layDown() {
        stopCurrentAnim();

        String base = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/";
        SheetInfo lay = loadSheet(base + "Lay.png");
        if (lay == null) {
            playIdle();
            return;
        }

        view.setImage(lay.sheet);
        view.setViewport(new Rectangle2D(0, 0, lay.frameSize, lay.frameSize));
        view.setFitWidth(150);
        view.setPreserveRatio(true);
        view.setTranslateY(baseYOffset);

        currentAnim = new Timeline(new KeyFrame(Duration.seconds(2), e -> playIdle()));
        currentAnim.setCycleCount(1);
        currentAnim.play();
    }

    // ---------- RANDOM BEHAVIOR ----------
    private void startRandomBehavior() {
        aiTimeline = new Timeline(new KeyFrame(Duration.seconds(2.8 + random.nextDouble()), e -> {
            double r = random.nextDouble();
            if (r < 0.25) playIdle();
            else if (r < 0.58) walkLeft();
            else if (r < 0.91) walkRight();
            else layDown();
        }));
        aiTimeline.setCycleCount(Timeline.INDEFINITE);
        aiTimeline.play();
    }

    // ---------- Tweaks API ----------
    public void setBaseYOffset(double y) { this.baseYOffset = y; }
    public void setMoveRange(double r) { this.moveRange = r; }
    public void setPixelsPerStep(double px) { this.pixelsPerStep = px; }
    public void setIdleMsPerFrame(int ms) { this.idleMsPerFrame = ms; }
    public void setWalkMsPerFrame(int ms) { this.walkMsPerFrame = ms; }
}