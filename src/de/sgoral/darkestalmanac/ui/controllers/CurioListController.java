package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.*;
import de.sgoral.darkestalmanac.events.CurioSelectedEvent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CurioListController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Curio> dataTable;

    @FXML
    private TableColumn<Curio, String> nameColumn;

    @FXML
    private TableColumn<Curio, String> usefulColumn;

    @FXML
    private TableColumn<Curio, String> uselessColumn;

    @FXML
    private TableColumn<Curio, String> untestedColumn;

    private DataStorage dataStorage;
    private Location location;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> dataTable.getParent().fireEvent(new CurioSelectedEvent(newValue)));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usefulColumn.setCellValueFactory(param -> {
            StringBuilder usefulConsumables = new StringBuilder();
            for (Experiment experiment : param.getValue().getExperiments()) {
                for (Result result : experiment.getResults()) {
                    if (result.getEffect().isPositive()) {
                        if (usefulConsumables.length() != 0) {
                            usefulConsumables.append(", ");
                        }
                        if (experiment.getConsumable() == null) {
                            usefulConsumables.append("None");
                        } else {
                            usefulConsumables.append(experiment.getConsumable().getName());
                        }
                        break;
                    }
                }
            }
            return new SimpleObjectProperty<>(usefulConsumables.toString());
        });
        uselessColumn.setCellValueFactory(param -> {
            StringBuilder uselessConsumables = new StringBuilder();
            for (Experiment experiment : param.getValue().getExperiments()) {
                for (Result result : experiment.getResults()) {
                    if (!result.getEffect().isPositive()) {
                        if (uselessConsumables.length() != 0) {
                            uselessConsumables.append(", ");
                        }
                        if (experiment.getConsumable() == null) {
                            uselessConsumables.append("None");
                        } else {
                            uselessConsumables.append(experiment.getConsumable().getName());
                        }
                        break;
                    }
                }
            }
            return new SimpleObjectProperty<>(uselessConsumables.toString());
        });
        untestedColumn.setCellValueFactory(param -> {
            StringBuilder untestedConsumables = new StringBuilder();
            for (Consumable consumable : findUntestedConsumables(param.getValue().getExperiments())) {
                if (untestedConsumables.length() != 0) {
                    untestedConsumables.append(", ");
                }
                untestedConsumables.append(consumable.getName());
            }
            return new SimpleObjectProperty<>(untestedConsumables.toString());
        });
    }

    private List<Consumable> findUntestedConsumables(List<Experiment> experiments) {
        List<Consumable> consumables = new ArrayList<>(dataStorage.getConsumables());
        for (Experiment experiment : experiments) {
            if (consumables.contains(experiment.getConsumable())) {
                consumables.remove(experiment.getConsumable());
            }
        }
        return consumables;
    }

    public void setDataStorage(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void setLocation(Location location) {
        this.location = location;
        refreshTable();
    }

    private void refreshTable() {
        dataTable.getItems().setAll(location.getCurios());
    }
}
