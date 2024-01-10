package com.example.springwithfx.config;

import com.example.springwithfx.controller.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Getter
public class StageManager {

    @Autowired
    private SpringContext springContext;

    public void switchScene(FXMLLoader fxmlLoader, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fxmlLoader.setControllerFactory(this.springContext.getApplicationContext()::getBean);
        Scene scene = new Scene(fxmlLoader.getRoot());
        stage.setScene(scene);
        stage.setResizable(false);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("../images/frog.jpg")));
        String css =  Objects.requireNonNull(this.getClass().getResource("../application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(icon);
        stage.setTitle("Quản lý");
        stage.show();
    }

    public void switchScene(FXMLLoader fxmlLoader) throws IOException {
        Stage stage = new Stage();
        fxmlLoader.setControllerFactory(requiredType -> this.springContext.getApplicationContext().getBean(requiredType));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("../images/frog.jpg")));
        String css =  Objects.requireNonNull(this.getClass().getResource("../application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(icon);
        stage.setTitle("Quản lý");
        stage.show();
    }



    public void switchScene(FXMLLoader fxmlLoader, MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fxmlLoader.setControllerFactory(this.springContext.getApplicationContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("../images/frog.jpg")));
        String css =  Objects.requireNonNull(this.getClass().getResource("../application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(icon);
        stage.setTitle("Quản lý");
        stage.show();
    }

    public void switchScene(FXMLLoader fxmlLoader, ActionEvent event, BorderPane pane) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fxmlLoader.setControllerFactory(this.springContext.getApplicationContext()::getBean);
        Pane thongkePane = fxmlLoader.load();
        AbstractController controller = fxmlLoader.getController();
        controller.setPane(pane);
        pane.setCenter(thongkePane);
        stage.show();
    }

    public void switchScene(FXMLLoader fxmlLoader, MouseEvent event, BorderPane pane) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fxmlLoader.setControllerFactory(this.springContext.getApplicationContext()::getBean);
        Pane thongkePane = fxmlLoader.load();
        AbstractController controller = fxmlLoader.getController();
        controller.setPane(pane);
        pane.setCenter(thongkePane);
        stage.show();
    }
    public void switchScene(Stage stage, FXMLLoader fxmlLoader, ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fxmlLoader.setControllerFactory(this.springContext.getApplicationContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("../images/frog.jpg")));
        String css =  Objects.requireNonNull(this.getClass().getResource("../application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(icon);
        stage.setTitle("Pepe The Frog");
        stage.show();
    }







}
