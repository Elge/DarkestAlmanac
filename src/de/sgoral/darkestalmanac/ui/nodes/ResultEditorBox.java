package de.sgoral.darkestalmanac.ui.nodes;

import de.sgoral.darkestalmanac.data.dataobjects.Effect;
import de.sgoral.darkestalmanac.data.dataobjects.Result;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.util.List;

public class ResultEditorBox extends HBox {

    private final SimpleIntegerProperty idProperty;
    private final SimpleIntegerProperty timesProperty;
    private final SimpleStringProperty commentProperty;
    private final SimpleObjectProperty<Effect> effectProperty;
    private final ObservableList<Effect> effects;

    private Button decreaseCountButton;
    private Button increaseCountButton;
    private TextField timesTextField;
    private TextField commentTextField;
    private ChoiceBox<Effect> effectChoiceBox;

    public ResultEditorBox(List<Effect> effects, Result result) {
        super();

        idProperty = new SimpleIntegerProperty(result.getId());
        timesProperty = new SimpleIntegerProperty(result.getTimes());
        commentProperty = new SimpleStringProperty(result.getComment());
        effectProperty = new SimpleObjectProperty<>(result.getEffect());

        this.effects = FXCollections.observableList(effects);

        initialise();
    }

    private void initialise() {
        decreaseCountButton = new Button("-");
        decreaseCountButton.setOnAction(event -> timesProperty.setValue(timesProperty.getValue() - 1));

        increaseCountButton = new Button("+");
        increaseCountButton.setOnAction(event -> timesProperty.setValue(timesProperty.getValue() + 1));

        timesTextField = new TextField();
        timesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                timesTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        timesTextField.textProperty().bindBidirectional(timesProperty, new NumberStringConverter());

        commentTextField = new TextField();
        commentTextField.textProperty().bindBidirectional(commentProperty);

        effectChoiceBox = new ChoiceBox<>(effects);
        effectChoiceBox.setConverter(new StringConverter<Effect>() {
            @Override
            public String toString(Effect object) {
                return object.getName();
            }

            @Override
            public Effect fromString(String string) {
                return effects.stream()
                        .filter(effect -> effect.getName().equals(string))
                        .findFirst().orElse(null);
            }
        });
        effectChoiceBox.valueProperty().bindBidirectional(effectProperty);

        this.getChildren().add(effectChoiceBox);
        this.getChildren().add(decreaseCountButton);
        this.getChildren().add(timesTextField);
        this.getChildren().add(increaseCountButton);
        this.getChildren().add(commentTextField);
    }

    public Result getValue() {
        Result result = new Result(idProperty.getValue(), effectProperty.getValue(), timesProperty.getValue());
        if (!commentProperty.getValue().isEmpty()) {
            result.setComment(commentProperty.getValue());
        }
        return result;
    }
}
