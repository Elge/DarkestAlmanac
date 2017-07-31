package de.sgoral.darkestalmanac.ui.controllers;

import de.sgoral.darkestalmanac.data.dataobjects.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CurioController implements Initializable {

    @FXML
    private ListView<String> locationsList;
    @FXML
    private TableView<Experiment> experimentsTable;
    @FXML
    private TableColumn<Experiment, String> consumableColumn;
    @FXML
    private TableColumn<Experiment, String> effectsColumn;

    private Curio curio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.consumableColumn.setCellValueFactory(param -> {
            Consumable consumable = param.getValue().getConsumable();
            String value = null;
            if (consumable == null) {
                value = "None";
            } else {
                value = consumable.getName();
            }
            return new SimpleObjectProperty<>(value);
        });

        this.effectsColumn.setCellValueFactory(param -> {
            StringBuilder builder = new StringBuilder();
            for (Result result : param.getValue().getResults()) {
                if (builder.length() != 0) {
                    builder.append(',');
                    builder.append(' ');
                }

                if (result.getEffect() == null) {
                    builder.append("None");
                } else {
                    builder.append(result.getEffect().getName());
                }
                if (result.getComment() != null) {
                    builder.append(' ');
                    builder.append('(');
                    builder.append(result.getComment());
                    builder.append(')');
                }
            }
            return new SimpleObjectProperty<>(builder.toString());
        });

        this.experimentsTable.setRowFactory(param -> {
            return new TableRow<Experiment>() {
                @Override
                protected void updateItem(Experiment experiment, boolean empty) {
                    super.updateItem(experiment, empty);

                    if (empty || experiment == null) {
                        return;
                    }

                    boolean isSuccess = false;
                    boolean isFailure = false;

                    for (Result result : experiment.getResults()) {
                        if (result.getEffect() != null) {
                            if (result.getEffect().isPositive()) {
                                isSuccess = true;
                            } else {
                                isFailure = true;
                            }
                        }
                    }

                    String color = null;
                    if (isSuccess && isFailure) {
                        color = "yellow";
                    } else if (isSuccess) {
                        color = "palegreen";
                    } else {
                        color = "red";
                    }

                    this.setStyle("-fx-background-color: " + color);
                }
            };
        });

    }

    public void setCurio(Curio curio) {
        this.curio = curio;
        forceUiUpdate();
    }

    public void forceUiUpdate() {
        ObservableList<String> items = this.locationsList.getItems();
        items.clear();
        if (this.curio != null) {
            for (Location location : this.curio.getLocations()) {
                items.add(location.getName());
            }
        }

        this.experimentsTable.getItems().setAll(curio.getExperiments());

        this.locationsList.setPrefHeight(this.locationsList.getItems().size() * 26);
        this.experimentsTable.setPrefHeight(this.experimentsTable.getItems().size() * 24 + 30);
    }


}
