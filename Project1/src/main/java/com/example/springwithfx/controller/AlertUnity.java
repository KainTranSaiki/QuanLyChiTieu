package com.example.springwithfx.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUnity {

    public static void showInformationAlert(String title, String content) {
        showAlert(title, content, AlertType.INFORMATION);
    }

    public static void showWarningAlert(String title, String content) {
        showAlert(title, content, AlertType.WARNING);
    }

    public static void showErrorAlert(String title, String content) {
        showAlert(title, content, AlertType.ERROR);
    }
    public static void showSuccessAlert(String title, String content) {
        showAlert(title, content, AlertType.CONFIRMATION);
    }
    private static void showAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}