package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.Consumable;
import de.sgoral.darkestalmanac.data.dataobjects.DataStorage;
import de.sgoral.darkestalmanac.data.dataobjects.Experiment;
import de.sgoral.darkestalmanac.ui.nodes.ResultEditorBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ExperimentEditorController implements Initializable {

    private static String[] colors = {"lightgray", "gray"};
    @FXML
    private ChoiceBox<Consumable> consumableChoiceBox;
    @FXML
    private VBox resultsVBox;
    private DataStorage dataStorage;
    private Experiment experiment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consumableChoiceBox.setConverter(new StringConverter<Consumable>() {
            @Override
            public String toString(Consumable object) {
                return object.getName();
            }

            @Override
            public Consumable fromString(String string) {
                return consumableChoiceBox.getItems().stream()
                        .filter(consumable -> consumable.getName().equals(string))
                        .findFirst().orElse(null);
            }
        });
    }

    public void setData(DataStorage dataStorage, Experiment experiment) {
        this.dataStorage = dataStorage;
        this.experiment = experiment;

        forceUiUpdate();
    }

    public void forceUiUpdate() {
        consumableChoiceBox.getItems().setAll(dataStorage.getConsumables());

        resultsVBox.getChildren().clear();
        for (int i = 0; i < experiment.getResults().size(); i++) {
            ResultEditorBox e = new ResultEditorBox(dataStorage.getEffects(), experiment.getResults().get(i));
            e.setStyle("-fx-background-color: " + colors[i % 2]);
            resultsVBox.getChildren().add(e);
        }
    }
}
