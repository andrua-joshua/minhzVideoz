package com.example.minhzvideos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Player implements Initializable {
    @FXML
    public MediaView mediaView;
    public ProgressBar progressBar;


    private Stage stage;
    private Scene scene;
    Parent root;
    public static Media media; //= new Media(HelloController.movie.getmDir().toURI().toString());
    public static MediaPlayer mediaPlayer; //=

    public static boolean Stop = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaView.setSmooth(true);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);


       // mediaPlayer.
        //start();
    }

    public void start(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Stop){
                    double progress = mediaPlayer.getTotalDuration().toMinutes()
                            /mediaPlayer.getCurrentTime().toMinutes();
                    progressBar.setProgress(progress);
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
