package de.sgoral.darkestalmanac.ui;

import de.sgoral.darkestalmanac.control.PreferencesUtil;
import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.events.MenuItemActionEvent;
import de.sgoral.darkestalmanac.events.TitleChangeRequestedEvent;
import de.sgoral.darkestalmanac.ui.controllers.MainWindowController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by sebastianprivat on 11.07.17.
 */
public class GuiController {

    private Stage stage;
    private File initialDirectory;
    private String initialFilename;
    private String extension;
    private String extensionDescription;

    public GuiController(Stage stage, File initialDirectory, String initialFilename, String extension, String extensionDescription) {
        this.stage = stage;
        this.initialDirectory = initialDirectory;
        this.initialFilename = initialFilename;
        this.extension = extension;
        this.extensionDescription = extensionDescription;
    }

    public GuiController(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void start() {
        start(true);
    }

    public void start(boolean firstRun) {
        File storageFile = PreferencesUtil.getStorageFile();
        if (storageFile == null) {
            initialiseMainWindow(DataStorage.createNewDataStorage(), firstRun);
        } else if (storageFile.canRead()) {
            initialiseMainWindow(PreferencesUtil.loadStorageData(storageFile), firstRun);
        } else {
            boolean changeFile = SavingAndLoadingGui.errorCannotOpenFile(storageFile.getAbsolutePath());
            if (changeFile) {
                PreferencesUtil.setStorageFile(SavingAndLoadingGui.promptForNewFile(stage.getOwner(),
                        this.initialDirectory, this.initialFilename, this.extension, this.extensionDescription));
            }
            start();
        }
    }

    public void restart() {
        start(false);
    }

    private void initialiseMainWindow(DataStorage dataStorage, boolean firstRun) {
        GuiLoaderUtil.GuiLoader<MainWindowController> loader = GuiLoaderUtil.getInstance().getMainWindow();
        Parent mainWindow = loader.load();
        MainWindowController controller = loader.getController();

        if (firstRun) {
            initialiseEventHandlers(dataStorage, mainWindow);
            this.stage.setScene(new Scene(mainWindow));
        }

        controller.setDataStorage(dataStorage);
        controller.openLocationsList();
        this.stage.show();
    }

    private void initialiseEventHandlers(DataStorage dataStorage, Parent mainWindow) {
        mainWindow.addEventHandler(TitleChangeRequestedEvent.EVENT_TYPE, event -> this.stage.setTitle(event.getTitle()));

        mainWindow.addEventHandler(MenuItemActionEvent.EVENT_TYPE_ROOT, event -> {
            if (event.getEventType() == MenuItemActionEvent.EVENT_TYPE_NEW) {
                if (SavingAndLoadingGui.askIfSave()) {
                    PreferencesUtil.saveStorageData(PreferencesUtil.getStorageFile(), dataStorage);
                }
                PreferencesUtil.setStorageFile(null);
                restart();
            } else if (event.getEventType() == MenuItemActionEvent.EVENT_TYPE_OPEN) {
                if (SavingAndLoadingGui.askIfSave()) {
                    PreferencesUtil.saveStorageData(PreferencesUtil.getStorageFile(), dataStorage);
                }
                File file = SavingAndLoadingGui.promptForExistingFile(stage.getOwner(), initialDirectory,
                        initialFilename, extension, extensionDescription);
                if (file != null) {
                    PreferencesUtil.setStorageFile(file);
                    restart();
                }
            } else if (event.getEventType() == MenuItemActionEvent.EVENT_TYPE_SAVE) {
                if (PreferencesUtil.getStorageFile() == null) {
                    File storageFile = SavingAndLoadingGui.promptForNewFile(stage.getOwner(), initialDirectory, initialFilename, extension, extensionDescription);
                    if (storageFile != null) {
                        PreferencesUtil.setStorageFile(storageFile);
                    }
                }
                PreferencesUtil.saveStorageData(PreferencesUtil.getStorageFile(), dataStorage);
            } else if (event.getEventType() == MenuItemActionEvent.EVENT_TYPE_SAVEAS) {
                File storageFile = SavingAndLoadingGui.promptForNewFile(stage.getOwner(), initialDirectory, initialFilename,
                        extension, extensionDescription);
                if (storageFile != null) {
                    PreferencesUtil.setStorageFile(storageFile);
                    PreferencesUtil.saveStorageData(storageFile, dataStorage);
                }
            } else if (event.getEventType() == MenuItemActionEvent.EVENT_TYPE_EXIT) {
                this.stage.close();
            } else {
                throw new RuntimeException("Event type " + event.getEventType() + " is not implemented");
            }
        });
    }
}
