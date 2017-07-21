package de.sgoral.darkestalmanac.ui;

import de.sgoral.darkestalmanac.control.PreferencesUtil;
import de.sgoral.darkestalmanac.data.dataobjects.Curio;
import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.events.FileSelectedEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by sebastianprivat on 11.07.17.
 */
public class DataLocationEditorController {

    @FXML
    private TextField fileSelector;

    private File storageFile;
    private File originalStorageFile;

    private void updateFileText() {
        if (storageFile != null) {
            this.fileSelector.setText(storageFile.getAbsolutePath());
        } else if (originalStorageFile != null) {
            this.fileSelector.setText(originalStorageFile.getAbsolutePath());
        }
    }

    public void onClickCancel(ActionEvent actionEvent) {
        FileSelectedEvent event = new FileSelectedEvent(originalStorageFile);
        fileSelector.getParent().fireEvent(event);
    }

    public void onClickSave(ActionEvent actionEvent) {
        FileSelectedEvent event;
        if (storageFile != null) {
            event = new FileSelectedEvent(storageFile);
        } else {
            event = new FileSelectedEvent(originalStorageFile);
        }
        fileSelector.getParent().fireEvent(event);
    }

    public void onTextFieldClicked(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select existing data file or choose folder to create new.");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("darkestalmanac.json");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File file = fileChooser.showSaveDialog(((Node) mouseEvent.getTarget()).getScene().getWindow());

        if (file != null) {

            PreferencesUtil.setStorageFile(file);
            DataStorage dataStorage = PreferencesUtil.loadStorageData(file);

            for (Curio curio : dataStorage.getCurios()) {
                System.out.println(curio.getName());
            }
        }

        this.storageFile = file;
        updateFileText();
    }

    public void setOriginalStorageFile(File file) {
        this.originalStorageFile = file;
        updateFileText();
    }
}
