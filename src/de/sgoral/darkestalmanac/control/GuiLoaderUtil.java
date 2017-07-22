package de.sgoral.darkestalmanac.control;

import de.sgoral.darkestalmanac.ui.CurioListController;
import de.sgoral.darkestalmanac.ui.DataLocationEditorController;
import de.sgoral.darkestalmanac.ui.LocationListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class GuiLoaderUtil {

    private static GuiLoaderUtil instance = null;
    private final GuiLoader<DataLocationEditorController> dataLocationEditor;
    private final GuiLoader<LocationListController> locationList;
    private final GuiLoader<CurioListController> curioList;

    private GuiLoaderUtil() {
        this.dataLocationEditor = new GuiLoader<>("DataLocationEditor.fxml");
        this.locationList = new GuiLoader<>("LocationList.fxml");
        this.curioList = new GuiLoader<>("CurioList.fxml");
    }

    public static GuiLoaderUtil getInstance() {
        if (instance == null) {
            instance = new GuiLoaderUtil();
        }

        return instance;
    }

    public GuiLoader<DataLocationEditorController> getDataLocationEditor() {
        return dataLocationEditor;
    }

    public GuiLoader<LocationListController> getLocationList() {
        return locationList;
    }

    public GuiLoader<CurioListController> getCurioList() {
        return curioList;
    }

    public class GuiLoader<ControllerClass> {
        private FXMLLoader loader;
        private String fxmlFile;

        private GuiLoader(String fxmlFile) {
            this.fxmlFile = '/' + fxmlFile;
        }

        public Parent load() {
            try {
                return getLoader().load();
            } catch (IOException e) {
                throw new RuntimeException("Fatal error loading fxml file " + fxmlFile);
            }
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
