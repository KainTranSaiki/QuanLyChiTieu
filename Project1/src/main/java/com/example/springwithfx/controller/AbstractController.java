package com.example.springwithfx.controller;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class AbstractController {
    protected BorderPane pane;
    protected Scene scene;
    protected Stage stage;

    public void setPane(BorderPane pane) {
        this.pane = pane;
    }
}
