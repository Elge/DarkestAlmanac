package de.sgoral.darkestalmanac.ui;

import de.sgoral.darkestalmanac.ui.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class GuiLoaderUtil {

    private static GuiLoaderUtil instance = null;
    private final GuiLoader<MainWindowController> mainWindow;
    private final GuiLoader<LocationListController> locationList;
    private final GuiLoader<LocationEditorController> locationEditor;
    private final GuiLoader<CurioListController> curioList;
    private final GuiLoader<CurioController> curio;


    private GuiLoaderUtil() {
        this.mainWindow = new GuiLoader<>("MainWindow.fxml");
        this.locationList = new GuiLoader<>("LocationList.fxml");
        this.locationEditor = new GuiLoader<>("LocationEditor.fxml");
        this.curioList = new GuiLoader<>("CurioList.fxml");
        this.curio = new GuiLoader<>("Curio.fxml");
    }

    public static GuiLoaderUtil getInstance() {
        if (instance == null) {
            instance = new GuiLoaderUtil();
        }

        return instance;
    }

    public GuiLoader<MainWindowController> getMainWindow() {
        return mainWindow;
    }

    public GuiLoader<LocationListController> getLocationList() {
        return locationList;
    }

    public GuiLoader<LocationEditorController> getLocationEditor() {
        return locationEditor;
    }

    public GuiLoader<CurioListController> getCurioList() {
        return curioList;
    }

    public GuiLoader<CurioController> getCurio() {
        return curio;
    }

    public class GuiLoader<ControllerClass> {
        private FXMLLoader loader;
        private String fxmlFile;
        private Parent loaded;

        private GuiLoader(String fxmlFile) {
            this.fxmlFile = '/' + fxmlFile;
        }

        public Parent load() {
            if (loaded == null) {
                try {
                    loaded = getLoader().load();
                } catch (IOException e) {
                    throw new RuntimeException("Fatal error loading fxml file " + fxmlFile, e);
                }
            }
            return loaded;
        }

        public ControllerClass getController() {
            return getLoader().getController();
        }

        private FXMLLoader getLoader() {
            if (loader == null) {
                loader = new FXMLLoader(System.class.getResource(fxmlFile));
            }
            return loader;
        }
    }
}
