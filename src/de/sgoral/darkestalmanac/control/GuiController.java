package de.sgoral.darkestalmanac.control;

import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.events.FileSelectedEvent;
import de.sgoral.darkestalmanac.ui.DataLocationEditorController;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by sebastianprivat on 11.07.17.
 */
public class GuiController {

    private Stage stage;

    public GuiController(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void start() {
        DataStorage dataStorage = validateStorageFileAndLoadData();
        if (dataStorage != null) {
            openMainWindow(dataStorage);
        } else {
            promptForStorageFile();
        }
    }

    private void openMainWindow(DataStorage dataStorage) {
        new MainWindowController(this.stage).openMainWindow(dataStorage);
    }

    private void promptForStorageFile() {
        EventHandler<FileSelectedEvent> eventHandler = event -> {
            PreferencesUtil.setStorageFile(event.getSelectedFile());
            DataStorage dataStorage = validateStorageFileAndLoadData();
            if (dataStorage != null) {
                openMainWindow(dataStorage);
            } else {
                promptForStorageFile();
            }
        };
        openDataLocationEditor(PreferencesUtil.getStorageFile(), eventHandler);
    }

    private DataStorage validateStorageFileAndLoadData() {
        File storageFile = PreferencesUtil.getStorageFile();
        if (storageFile == null) {
            return null;
        }
        return PreferencesUtil.loadStorageData(storageFile);
    }

    private void openDataLocationEditor(File currentStorageFile, EventHandler<? super FileSelectedEvent> eventHandler) {
        GuiLoaderUtil.GuiLoader<DataLocationEditorController> loader = GuiLoaderUtil.getInstance().getDataLocationEditor();
        Parent editor = loader.load();
        DataLocationEditorController controller = loader.getController();

        editor.addEventHandler(FileSelectedEvent.EVENT_TYPE, eventHandler);

        controller.setOriginalStorageFile(currentStorageFile);

        this.stage.setTitle("Data Location");
        this.stage.setScene(new Scene(editor));
        this.stage.show();
    }
}
