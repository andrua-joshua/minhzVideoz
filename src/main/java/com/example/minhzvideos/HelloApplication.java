package com.example.minhzvideos;

import com.example.minhzvideos.handler.Handler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Scene scene;

    public static Stage stg;
    Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 1100, 550);
        scene.getStylesheets().add(this
                .getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("D-finder - @Drillox Technologies");
        stage.setScene(scene);
        stage.show();
        stage.setMaxWidth(1100);
        stage.setMinWidth(1100);
        stg = stage;
        //stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch();
    }
}