// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveManager {

    private static final String SAVE_FOLDER =
            System.getProperty("user.home") + File.separator + "PetHaven";

    private static final String SAVE_FILE =
            SAVE_FOLDER + File.separator + "pethaven_save.dat";

    // -------- SAVE GAME --------
    public static void saveGame(Object data) {
        try {
            // Ensure directory exists
            Files.createDirectories(Path.of(SAVE_FOLDER));

            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(SAVE_FILE))) {
                out.writeObject(data);
            }

            System.out.println("Game saved at: " + SAVE_FILE);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error saving game.");
        }
    }

    // -------- LOAD GAME --------
    @SuppressWarnings("unchecked")
    public static <T> T loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(SAVE_FILE))) {
            return (T) in.readObject();
        } catch (Exception e) {
            System.out.println("No save found or error loading save.");
            return null;
        }
    }

    // -------- DELETE SAVE FILE --------
    public static void deleteSave() {
        try {
            Files.deleteIfExists(Path.of(SAVE_FILE));
            System.out.println("Save deleted.");
        } catch (Exception ignored) {}
    }
}
