package com.anoc20.minimaxcheckers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NewGameController {

    @FXML
    ComboBox modeComboBox;
    @FXML
    ComboBox colourComboBox;
    @FXML
    Button startButton;

    String[] modes = {"Easy","Medium","Hard"};
    String[] colours = {"Red","White"};

    public void initialize() throws InterruptedException {
        //Populate ComboBoxes with game options
        modeComboBox.setItems(FXCollections.observableArrayList(modes));
        colourComboBox.setItems(FXCollections.observableArrayList(colours));
        modeComboBox.getSelectionModel().selectFirst();
        colourComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void startGame() throws IOException {
        //Pass options to GameApplication
        if(modeComboBox.getValue() == "Easy") {
            GameApplication.difficulty = Mode.EASY;
        }
        else if(modeComboBox.getValue() == "Medium") {
            GameApplication.difficulty = Mode.MEDIUM;
        }
        else {
            GameApplication.difficulty = Mode.HARD;
        }

        if(colourComboBox.getValue() == "Red") {
            GameApplication.playerColour = PieceColour.DARK;
        }
        else {
            GameApplication.playerColour = PieceColour.WHITE;
        }

        //Move to game scene
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game_gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        Stage newStage = (Stage) startButton.getScene().getWindow();
        newStage.setScene(scene);
    }
}
