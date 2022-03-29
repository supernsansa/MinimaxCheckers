package com.anoc20.minimaxcheckers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button newGameButton;
    @FXML
    private Button rulesButton;
    @FXML
    private Button exitButton;


    public void initialize() throws InterruptedException {}

    @FXML
    private void closeProgram() {
        Platform.exit();
    }

    //Move from main menu to new game config window
    @FXML
    private void newGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("new_game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        Stage newStage = (Stage) newGameButton.getScene().getWindow();
        newStage.setScene(scene);
    }

    //TODO Display help pop up window with rules of checkers
    @FXML
    private void displayHelp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("new_game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        Stage newStage = (Stage) newGameButton.getScene().getWindow();
        newStage.setScene(scene);
    }

}
