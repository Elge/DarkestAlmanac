package de.sgoral.darkestalmanac.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Optional;

public class SavingAndLoadingGui {

    private SavingAndLoadingGui() {
    }

    /**
     * Returns true if the user would like to choose a different file.
     *
     * @param filePath
     * @return
     */
    public static boolean errorCannotOpenFile(String filePath) {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                String.format("Cannot read storage file \"%s\"", filePath),
                new ButtonType("Try again", ButtonBar.ButtonData.OTHER), ButtonType.OK);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Returns true if the user would like to save.
     * @return
     */
    public static boolean askIfSave() {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "You have unsaved changes. Would you like to save before continuing?"
                , ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    public static File promptForNewFile(Window ownerWindow, File initialDirectory, String initialFileName, String extension,
                                        String extensionDescription) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.setInitialFileName(initialFileName);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(extensionDescription, "*." + extension));

        return fileChooser.showSaveDialog(ownerWindow);
    }

    public static File promptForExistingFile(Window window, File initialDirectory, String initialFileName, String extension,
                                             String extensionDescription) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.setInitialFileName(initialFileName);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(extensionDescription, "*." + extension));

        return fileChooser.showOpenDialog(window);
    }

}
