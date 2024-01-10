package com.example.springwithfx.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SuccesDialog implements Initializable {

    @FXML
    private Label messageLabel;

    public String message;
    public SuccesDialog(String message) throws IOException {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/success.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 450, 100);
        SuccesDialog succesDialog = fxmlLoader.getController();
        succesDialog.messageLabel.setText(message);
        stage1.setScene(scene1);
        stage1.show();
    }

    public SuccesDialog() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
