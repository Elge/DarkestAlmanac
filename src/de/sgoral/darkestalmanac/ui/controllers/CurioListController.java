package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.*;
import de.sgoral.darkestalmanac.events.CurioEditingEvent;
import de.sgoral.darkestalmanac.events.CurioSelectedEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

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
        dataTable.setRowFactory(param -> {
            TableRow<Curio> row = new TableRow<>();

            ContextMenu emptyCM = new ContextMenu();
            ContextMenu fullCM = new ContextMenu();

            {
                MenuItem add = new MenuItem("Add curio");
                add.setOnAction(event -> row.fireEvent(new CurioEditingEvent(CurioEditingEvent.EVENT_TYPE_NEW, this.location, new Curio())));
                emptyCM.getItems().add(add);
            }

            {
                MenuItem add = new MenuItem("Add curio");
                add.setOnAction(event -> row.fireEvent(new CurioEditingEvent(CurioEditingEvent.EVENT_TYPE_NEW, this.location, new Curio())));

                MenuItem edit = new MenuItem("Edit curio");
                edit.setOnAction(event -> row.fireEvent(new CurioEditingEvent(CurioEditingEvent.EVENT_TYPE_EDIT, this.location, row.getItem())));

                MenuItem delete = new MenuItem("Delete curio");
                delete.setOnAction(event -> row.fireEvent(new CurioEditingEvent(CurioEditingEvent.EVENT_TYPE_DELETE, this.location, row.getItem())));

                fullCM.getItems().addAll(add, edit, delete);
            }

            row.contextMenuProperty().bind(
                    Bindings.when(
                            Bindings.isNull(row.itemProperty())
                    ).then(emptyCM).otherwise(fullCM)
            );

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    row.fireEvent(new CurioSelectedEvent(row.getItem()));
                }
            });

            return row;
        });

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

    public void setData(DataStorage dataStorage, Location location) {
        this.dataStorage = dataStorage;
        this.location = location;
        forceUiUpdate();
    }

    public void forceUiUpdate() {
        dataTable.getItems().setAll(location.getCurios());
    }
}
