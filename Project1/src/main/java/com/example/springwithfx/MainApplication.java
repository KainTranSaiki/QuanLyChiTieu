package com.example.springwithfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import java.util.Objects;


public class MainApplication extends Application {
    private ConfigurableApplicationContext applicationContext;
    private Parent root;

    @Override
    public void init() throws Exception {
        super.init();
        this.applicationContext = new SpringApplicationBuilder(SpringwithfxApplication.class).run();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/Home.fxml"));
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        root = fxmlLoader.load();

    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/frog.jpg")));
        String css =  Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(icon);
        stage.setTitle("Quản lý");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        this.applicationContext.close();
    }
}
