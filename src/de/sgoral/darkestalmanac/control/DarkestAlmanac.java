package de.sgoral.darkestalmanac.control;

import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.events.FileSelectedEvent;
import de.sgoral.darkestalmanac.ui.UiFactory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by sebastianprivat on 11.07.17.
 */
public class DarkestAlmanac extends Application {

    private UiFactory uiFactory;
    private Stage stage;
    private DataStorage dataStorage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        uiFactory = new UiFactory();
        this.stage = stage;

        if (validateStorageFileAndLoadData()) {
            showMainWindow();
        } else {
            promptForStorageFile();
        }
    }

    private void promptForStorageFile() {
        File storageFile = PreferencesUtil.getStorageFile();
        EventHandler<FileSelectedEvent> eventHandler = event -> {
            PreferencesUtil.setStorageFile(event.getSelectedFile());
            if (validateStorageFileAndLoadData()) {
                showMainWindow();
            } else {
                promptForStorageFile();
            }
        };
        uiFactory.openDataLocationEditor(this.stage, PreferencesUtil.getStorageFile(), eventHandler);
    }

    private boolean validateStorageFileAndLoadData() {
        File storageFile = PreferencesUtil.getStorageFile();
        if (storageFile == null) {
            return false;
        }
        dataStorage = PreferencesUtil.loadStorageData(storageFile);
        return true;
    }

    private void showMainWindow() {
        uiFactory.openMainWindow(stage, dataStorage);
    }
}
