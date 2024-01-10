package com.example.springwithfx.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class  WarningDialog extends Dialog<ButtonType> {


    public WarningDialog () {

        loadFXML();


        // Set the button types (Yes and No)
        getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

        // Set actions for the buttons
        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.YES) {
                handleYesButton();
            } else if (buttonType == ButtonType.NO) {
                handleNoButton();
            }
            return buttonType;
        });
    }

    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/warning.fxml"));
        loader.setController(this);

        try {
            AnchorPane root = loader.load();
            getDialogPane().setContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleYesButton() {
        setResult(ButtonType.YES);
        close();
    }

    @FXML
    private void handleNoButton() {
        setResult(ButtonType.NO);
        close();
    }
}
