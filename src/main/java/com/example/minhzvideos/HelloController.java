package com.example.minhzvideos;

import com.example.minhzvideos.handler.Function;
import com.example.minhzvideos.handler.Handler;
import com.example.minhzvideos.handler.MType;
import com.example.minhzvideos.handler.Movie;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class HelloController implements Initializable {
    @FXML
    public Label title;
    public Label D_label;
    public ListView Drive_lists;
    public Label Dir_label;
    public ListView File_lists;
    public TextField searchTxt;
    public ListView search_lists;
    public VBox drives;
    public VBox files;
    public VBox search;
    public ScrollPane scrollFiles;


    public static ObservableList<String> drivesList = FXCollections
            .observableArrayList();
    public CheckBox Series;
    public CheckBox Movies;
    public CheckBox All;
    public CheckBox Music;
    public MenuItem autoSaveId;
    public MenuItem saveToLogs;
    public MenuItem saveToBackUp;
    private ObservableList<String> list = FXCollections
            .observableArrayList();
    private ObservableList<String> searchedList = FXCollections
            .observableArrayList();
    private Label music_name = new Label();

    public static Movie movie;

    private Stage stage;
    private Scene scene;
    Parent root;
    Parent root_settings;

    public void LoadFromDisk(ActionEvent actionEvent) {

        if (!Handler.settings.isLoadFromDiskByAdmin()){
            All.setSelected(true);
            Series.setSelected(false);
            Movies.setSelected(false);
            Music.setSelected(false);

            list.clear();
            drivesList.clear();


            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory to choose through");
            File fl = directoryChooser.showDialog(HelloApplication.stg);

            Dialog<String> d = new Dialog<>();
            d.setTitle("Loading Movies .....");
            d.setContentText("Waiting loading process .....");
            d.setHeight(400);
            d.initModality(Modality.APPLICATION_MODAL);


            if (fl != null) {
                d.show();
                Handler.Reloading(fl, false);
                d.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                d.close();
            }


            Set<String> keySet = Handler.AllMovies.keySet();
            for (String key : keySet) {
                if (!drivesList.contains(key))
                    drivesList.add(key);

                System.out.println(key);
                for (Movie mv : Handler.AllMovies.get(key)) {
                    if (!list.contains(Handler.getMovieDetails(mv)))
                        list.add(Handler.getMovieDetails(mv));
                }
            }

            //to save the search to the dlogs file
            try {
                if (Handler.settings.isAutoSave()){

                    Handler.saveSearchLogs(Handler.AllMovies,true);
                    Handler.saveSearchLogs(Handler.AllMusic,false);
                }else {
                    saveToLogs.setText("Save to logs  [*]");
                }
            } catch (Exception e) {
                System.out.println("@Drillox Exception Running ..");
            }
        }
        else{
            enterPassCode(Function.LOAD_FROM_DISK);
        }

        saveToBackUp.setText("Save to BackUp  [*]");

    }

    public void LoadFromLog(ActionEvent actionEvent) {
        System.out.println("@Drillox : Load from Logs");

        if (!Handler.settings.isLoadFromLogsByAdmin()){
            All.setSelected(true);
            Series.setSelected(false);
            Movies.setSelected(false);
            Music.setSelected(false);

            list.clear();
            drivesList.clear();

            try {
                Handler.loadfromDLogs();
                Set<String> keySet = Handler.AllMovies.keySet();
                for (String key : keySet) {
                    if (!drivesList.contains(key))
                        drivesList.add(key);

                    System.out.println(key);
                    for (Movie mv : Handler.AllMovies.get(key)) {
                        if (!list.contains(Handler.getMovieDetails(mv)))
                            list.add(Handler.getMovieDetails(mv));
                    }
                }

                //----loading music
                Set<String> keySet2 = Handler.AllMusic.keySet();
                for (String key : keySet2) {
                    if (!drivesList.contains(key))
                        drivesList.add(key);
                }

            } catch (Exception e) {
                System.out.println("@Drillox Exception looding from logs:: " + e.getMessage());
            }
        }
        else {
            enterPassCode(Function.LOAD_FROM_LOGS);
        }


        saveToBackUp.setText("Save to BackUp  [*]");
    }

    public void LoadOnTop(ActionEvent actionEvent) {
        if (!Handler.settings.isLoadOnTopByAdmin()){
            All.setSelected(true);
            Series.setSelected(false);
            Movies.setSelected(false);
            Music.setSelected(false);

            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory to choose through");
            File fl = directoryChooser.showDialog(HelloApplication.stg);

            Dialog<String> d = new Dialog<>();
            d.setTitle("Loading Movies .....");
            d.setContentText("Waiting loading process .....");
            d.setHeight(400);
            d.initModality(Modality.APPLICATION_MODAL);

            if (fl != null) {
                d.show();
                Handler.Reloading(fl, true);
                d.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                d.close();
            }

            list.clear();
            Set<String> keySet = Handler.AllMovies.keySet();
            for (String key : keySet) {
                if (!drivesList.contains(key))
                    drivesList.add(key);

                System.out.println(key);
                for (Movie mv : Handler.AllMovies.get(key)) {
                    if (!list.contains(Handler.getMovieDetails(mv)))
                        list.add(Handler.getMovieDetails(mv));
                }
            }

            //to add the search logs to the dlogs.dat file
            try {
                if (Handler.settings.isAutoSave()){
                    Handler.saveSearchLogs(Handler.AllMovies,true);
                    Handler.saveSearchLogs(Handler.AllMusic,false);
                }else {
                    saveToLogs.setText("Save to logs   [*]");
                }
            } catch (Exception e) {
                System.out.println("@Drillox Exception Running ..");
            }
        }
        else {
            enterPassCode(Function.LOAD_ON_TOP);
        }

        saveToBackUp.setText("Save to BackUp  [*]");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Handler.initSettings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        _loadFromLogs();
        if (Handler.settings.isAutoSave())
            autoSaveId.setText("Turn autoSave off");

        File_lists.setItems(list);
        Drive_lists.setItems(drivesList);
        search_lists.setItems(searchedList);


        All.setSelected(true);

        list.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                if (list.size() < 6)
                    File_lists.setPrefHeight(list.size() * 32);
                else File_lists.setPrefHeight(list.size() * 25);
            }
        });

        drivesList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                Drive_lists.setPrefHeight(drivesList.size() * 25);
            }
        });

        searchedList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                if (searchedList.size() < 6)
                    search_lists.setPrefHeight(searchedList.size() * 32);
                else search_lists.setPrefHeight(searchedList.size() * 25);
            }
        });

        /**
         * some view tests happening here
         * **/


        File_lists.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Drag and drop operations Started");
                Dragboard db = File_lists.startDragAndDrop(TransferMode.COPY);

                ClipboardContent content = new ClipboardContent();
                String str = File_lists.getSelectionModel().getSelectedItem().toString();

                Movie MV = null;
                if (Music.isSelected()){
                    Set<String> keySet = Handler.AllMusic.keySet();
                    for (String key : keySet) {
                        for (Movie mv : Handler.AllMusic.get(key)) {
                            if (mv.getName().equals(str))
                                MV = mv;
                        }
                    }
                }else {
                    Set<String> keySet = Handler.AllMovies.keySet();
                    for (String key : keySet) {
                        for (Movie mv : Handler.AllMovies.get(key)) {
                            if (Handler.getMovieDetails(mv).equals(str))
                                MV = mv;
                        }
                    }
                }
                if (MV != null) {
                    ArrayList<File> data = new ArrayList<>();
                    data.add(MV.getmDir());
                    content.putFiles(data);
                }
                db.setContent(content);

                mouseEvent.consume();
            }
        });


    }

    public void FileItemClicked(MouseEvent mouseEvent) throws IOException {
        int x = mouseEvent.getClickCount();
        Movie MV = null;
        if (x == 29) {
            String str = File_lists.getSelectionModel().getSelectedItem().toString();
            System.out.println(str);


            Set<String> keySet = Handler.AllMovies.keySet();
            for (String key : keySet) {
                System.out.println("Key: " + key);
                for (Movie mv : Handler.AllMovies.get(key)) {
                    //System.out.println("Inside key");
                    if (Handler.getMovieDetails(mv).equals(str))
                        MV = mv;
                }
            }

            if (MV.getMovieType() == MType.MOVIE) {
                movie = MV;

                Runtime.getRuntime().exec("dolphin " + movie.getmDir()
                        .getParentFile().getAbsolutePath());

                Player.media = new Media(movie.getmDir().toURI().toString());
                System.out.println(Player.media.getSource());
                Player.mediaPlayer = new MediaPlayer(Player.media);
                Player.Stop = false;
                root = FXMLLoader.load(getClass().getResource("player.fxml"));
                stage = new Stage();//(Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                scene = new Scene(root, 1000, 600);
                scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("@Drillox Technologies :: MediaPlayer");
                stage.setResizable(false);
                stage.show();

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        Player.Stop = true;
                        if (Player.media != null) Player.mediaPlayer.stop();
                        Player.mediaPlayer = null;
                    }
                });
            } else if (MV.getMovieType() == MType.SERIE) {
                movie = MV;
                System.out.println("%%%%%-> movie: " + movie.getmDir());


                String path = Handler.pathRefactor(movie.getmDir().getAbsolutePath());

//                Runtime.getRuntime().exec("dolphin " + path);//movie.getmDir()
//                        //.getAbsolutePath()+"'");
                Runtime.getRuntime().exec("dolphin " + movie.getmDir()
                        .getAbsolutePath());

                System.out.println("%%%%%-> movie: " + path);
            }
        }
    }

    public void sKeyTyped(KeyEvent keyEvent) {
        searchedList.clear();
        String val = searchTxt.getText();

        System.out.println("keyTyped");
        if (Music.isSelected()) {
            list.clear();
            for (Movie mv : Handler.searchMusic(val)) {
                if (mv.getmDir().exists()){
                    searchedList.add(mv.getName());
                    list.add(mv.getName());
                    System.out.println("added--");
                }
            }
        } else {
            for (Movie mv : Handler.searchMovie(val)) {
                if (mv.getmDir().exists())
                    searchedList.add(mv.getName());
                //System.out.println("added--");
            }


            int type = 0;
            if (val.length() != 0) {

                if (Movies.isSelected()) type = 1;
                else if (Series.isSelected()) type = 2;
                list.clear();
                for (Movie mv : Handler.searchMovie(val, type)) {
                    if (mv.getmDir().exists())
                        list.add(Handler.getMovieDetails(mv));
                }
            } else {
                if (Series.isSelected()) type = 2;
                else if (Movies.isSelected()) type = 1;
                list.clear();
                for (Movie mv : Handler.getMovies(type)) {
                    if (mv.getmDir().exists())
                        list.add(Handler.getMovieDetails(mv));
                }
            }
        }

    }

    public void onSeriesClicked(MouseEvent mouseEvent) {
        if (Series.isSelected()) {
            All.setSelected(false);
            Movies.setSelected(false);
            Music.setSelected(false);
            list.clear();
            for (Movie mv : Handler.getMovies(2)) {
                if (mv.getmDir().exists())
                    list.add(Handler.getMovieDetails(mv));
            }
        } else {
            All.setSelected(true);
            Movies.setSelected(false);

            list.clear();
            for (Movie mv : Handler.getMovies(0)) {
                if (mv.getmDir().exists())
                    list.add(Handler.getMovieDetails(mv));
            }
        }
    }

    public void onMoviesClicked(MouseEvent mouseEvent) {
        if (Movies.isSelected()) {
            All.setSelected(false);
            Series.setSelected(false);
            Music.setSelected(false);
            list.clear();
            for (Movie mv : Handler.getMovies(1)) {
                if (mv.getmDir().exists())
                    list.add(Handler.getMovieDetails(mv));
            }
            File_lists.setVisible(false);
            File_lists.setVisible(true);
        } else {
            All.setSelected(true);
            Series.setSelected(false);
            Music.setSelected(false);
            list.clear();
            for (Movie mv : Handler.getMovies(0)) {
                if (mv.getmDir().exists())
                    list.add(Handler.getMovieDetails(mv));
            }
        }
    }

    public void onAllClicked(MouseEvent mouseEvent) {
        if (All.isSelected()) {
            Series.setSelected(false);
            Movies.setSelected(false);
            Music.setSelected(false);
            list.clear();
            for (Movie mv : Handler.getMovies(0)) {
                if (mv.getmDir().exists())
                    list.add(Handler.getMovieDetails(mv));
            }
        } else {
            All.setSelected(true);
        }
    }


    public void onMusicClicked(MouseEvent mouseEvent) {
        if (Music.isSelected()) {
            Series.setSelected(false);
            Movies.setSelected(false);
            All.setSelected(false);
            list.clear();
            for (Movie mv : Handler.getMusic()) {
                if (mv.getmDir().exists())
                    list.add(mv.getName());
            }
        } else {
            Music.setSelected(true);
        }
    }

    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void startSettings(ActionEvent actionEvent) throws IOException {

        root_settings = FXMLLoader.
                load(getClass().getResource("settings.fxml"));
        Stage stage  = new Stage();
        Scene scene1 = new Scene(root_settings,700,500);
        scene1.getStylesheets().add(this
                .getClass().getResource("styles.css").toExternalForm());
        stage.setOpacity(0.9);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("D-finder Settings - @Drillox Technologies");
        stage.setScene(scene1);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    Handler.saveSettings();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Closing ......");
            }
        });
    }

    public void saveToBackup(ActionEvent actionEvent) {
        if (!Handler.settings.isBackUplogByAdmin()){
            try {
                Handler.saveSearchLogsToBackUp(Handler.AllMovies,true);
                Handler.saveSearchLogsToBackUp(Handler.AllMusic,false);
                saveToBackUp.setText("Save to BackUp");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else
            enterPassCode(Function.SAVE_BACKUP);
    }

    public void rollBackToBackUp(ActionEvent actionEvent) {
        if (!Handler.settings.isLoadFromLogsByAdmin()){
            All.setSelected(true);
            Series.setSelected(false);
            Movies.setSelected(false);
            Music.setSelected(false);

            list.clear();
            drivesList.clear();

            try {
                Handler.rollBackToBackUp();
                Set<String> keySet = Handler.AllMovies.keySet();
                for (String key : keySet) {
                    if (!drivesList.contains(key))
                        drivesList.add(key);

                    System.out.println(key);
                    for (Movie mv : Handler.AllMovies.get(key)) {
                        if (!list.contains(Handler.getMovieDetails(mv)))
                            if (mv.getmDir().exists())
                                list.add(Handler.getMovieDetails(mv));
                    }
                }

                //----loading music
                Set<String> keySet2 = Handler.AllMusic.keySet();
                for (String key : keySet2) {
                    if (!drivesList.contains(key))
                        drivesList.add(key);
                }

            } catch (Exception e) {
                System.out.println("@Drillox Exception looding from logs:: " + e.getMessage());
            }


            if (Handler.settings.isAutoSave())
                _autoSave();
            else saveToLogs.setText("Save to logs  [*]");
        }
        else {
            enterPassCode(Function.ROLL_BACK_TO_BACKUP);
        }

    }

    public void saveToLogs(ActionEvent actionEvent) {
        if (!Handler.settings.isSaveToLogsByAdmin()){
            try {
                Handler.saveSearchLogs(Handler.AllMovies,true);
                Handler.saveSearchLogs(Handler.AllMusic,false);
                saveToLogs.setText("Save to logs");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else
            enterPassCode(Function.SAVE_TO_LOGS);
    }

    public void autoSaveTologs(ActionEvent actionEvent) {
        if (!Handler.settings.isSaveToLogsByAdmin()){
            if (Handler.settings.isAutoSave()){
                Handler.settings.setAutoSave(false);
                autoSaveId.setText("Turn autoSave on");
            }
            else {
                Handler.settings.setAutoSave(true);
                autoSaveId.setText("Turn autoSave off");
            }
        }else
            enterPassCode(Function.AUTO_SAVE);
    }

    public boolean enterPassCode(Function function){
        /*
        * 1 -> loadFromDisk
        * 2 -> loadOnTop
        * 3 -> loadFromLogs
        * 4 -> saveToLogs
        * 5 -> saveBackUp
        * 6 -> Auto-Save*/
        final boolean[] _correct = {false};
        Stage stage_pCode = new Stage();
        VBox root_pCode = new VBox(10);
        Scene scene_pCode = new Scene(root_pCode);

        HBox PassCode = new HBox(10);
        TextField firstCode = new TextField();
        TextField secondCode = new TextField();
        TextField thirdCode = new TextField();
        TextField fourthCode = new TextField();
        TextField fifthCode = new TextField();
        Label enterpCode = new Label("Enter PassCode: ");
        Label adminonly = new Label("Admins Only Allowed");
        Label wrongPCode = new Label("wrong passCode");
        Button confirm = new Button("Confirm");

        root_pCode.setAlignment(Pos.CENTER);
        adminonly.setFont(new Font(16));
        wrongPCode.setTextFill(new Color(0.7,0.1,0.1,1));
        wrongPCode.setVisible(false);

        fifthCode.setPrefHeight(25);
        fifthCode.setMinHeight(25);
        fifthCode.setMaxHeight(25);
        fifthCode.setPrefWidth(25);
        fifthCode.setMinWidth(25);
        fifthCode.setMaxWidth(25);

        firstCode.setPrefHeight(25);
        firstCode.setMinHeight(25);
        firstCode.setMaxHeight(25);
        firstCode.setPrefWidth(25);
        firstCode.setMinWidth(25);
        firstCode.setMaxWidth(25);

        secondCode.setPrefHeight(25);
        secondCode.setMinHeight(25);
        secondCode.setMaxHeight(25);
        secondCode.setPrefWidth(25);
        secondCode.setMinWidth(25);
        secondCode.setMaxWidth(25);

        thirdCode.setPrefHeight(25);
        thirdCode.setMinHeight(25);
        thirdCode.setMaxHeight(25);
        thirdCode.setPrefWidth(25);
        thirdCode.setMinWidth(25);
        thirdCode.setMaxWidth(25);

        fourthCode.setPrefHeight(25);
        fourthCode.setMinHeight(25);
        fourthCode.setMaxHeight(25);
        fourthCode.setPrefWidth(25);
        fourthCode.setMinWidth(25);
        fourthCode.setMaxWidth(25);


        PassCode.getChildren().add(enterpCode);
        PassCode.getChildren().add(firstCode);
        PassCode.getChildren().add(secondCode);
        PassCode.getChildren().add(thirdCode);
        PassCode.getChildren().add(fourthCode);
        PassCode.getChildren().add(fifthCode);

        root_pCode.getChildren().add(adminonly);
        root_pCode.getChildren().add(PassCode);
        root_pCode.getChildren().add(wrongPCode);
        root_pCode.getChildren().add(confirm);

        firstCode.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //---------------------------//
                char ch = 0;
                if (firstCode.getText().length()>0){
                    ch = firstCode.getText().charAt(0);

                    int start = 47, end = 58;
                    int _ch = (int)ch;
                    int _lgth = firstCode.getText().length();

                    if (_ch>start && _ch<end){
                        if (_lgth>1){
                            firstCode.setText(String.valueOf(ch));
                            secondCode.requestFocus();
                            wrongPCode.setVisible(false);
                        }else {
                            firstCode.setText(String.valueOf(ch));
                            secondCode.requestFocus();
                            wrongPCode.setVisible(false);
                        }
                    }else {
                        firstCode.setText("");
                        wrongPCode.setText("Only Enter digits");
                        wrongPCode.setVisible(true);
                    }
                }
                System.out.println((int)ch);
                //----------------//
            }
        });
        secondCode.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                ///---------------///
                char ch = 0;
                if (secondCode.getText().length()>0){
                    ch = secondCode.getText().charAt(0);

                    int start = 47, end = 58;
                    int _ch = (int)ch;
                    int _lgth = secondCode.getText().length();

                    if (_ch>start && _ch<end){
                        if (_lgth>1){
                            secondCode.setText(String.valueOf(ch));
                            thirdCode.requestFocus();
                            wrongPCode.setVisible(false);
                        }else {
                            secondCode.setText(String.valueOf(ch));
                            thirdCode.requestFocus();
                            wrongPCode.setVisible(false);
                        }
                    }else {
                        secondCode.setText("");
                        wrongPCode.setText("Only Enter digits");
                        wrongPCode.setVisible(true);
                    }
                }
                //-----------------//
            }
        });
        thirdCode.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //--------------//
                char ch = 0;
                if (thirdCode.getText().length()>0){
                    ch = thirdCode.getText().charAt(0);

                    int start = 47, end = 58;
                    int _ch = (int)ch;
                    int _lgth = thirdCode.getText().length();

                    if (_ch>start && _ch<end){
                        if (_lgth>1){
                            thirdCode.setText(String.valueOf(ch));
                            fourthCode.requestFocus();
                            wrongPCode.setVisible(false);
                        }else {
                            thirdCode.setText(String.valueOf(ch));
                            fourthCode.requestFocus();
                            wrongPCode.setVisible(false);
                        }
                    }else {
                        thirdCode.setText("");
                        wrongPCode.setText("Only Enter digits");
                        wrongPCode.setVisible(true);
                    }
                }
                //--------------//
            }
        });
        fourthCode.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //--------------//
                char ch = 0;
                if (fourthCode.getText().length()>0){
                    ch = fourthCode.getText().charAt(0);

                    int start = 47, end = 58;
                    int _ch = (int)ch;
                    int _lgth = fourthCode.getText().length();

                    if (_ch>start && _ch<end){
                        if (_lgth>1){
                            fourthCode.setText(String.valueOf(ch));
                            fifthCode.requestFocus();
                            wrongPCode.setVisible(false);
                        }else {
                            fourthCode.setText(String.valueOf(ch));
                            fifthCode.requestFocus();
                            wrongPCode.setVisible(false);
                        }
                    }else {
                        fourthCode.setText("");
                        wrongPCode.setText("Only Enter digits");
                        wrongPCode.setVisible(true);
                    }
                }
                //--------------//
            }
        });
        fifthCode.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //--------//
                char ch = 0;
                if (fifthCode.getText().length()>0){
                    ch = fifthCode.getText().charAt(0);

                    int start = 47, end = 58;
                    int _ch = (int)ch;
                    int _lgth = fifthCode.getText().length();

                    if (_ch>start && _ch<end){
                        if (_lgth>1){
                            fifthCode.setText(String.valueOf(ch));
                            wrongPCode.setVisible(false);
                        }else {
                            fifthCode.setText(String.valueOf(ch));
                            wrongPCode.setVisible(false);
                        }
                    }else {
                        fifthCode.setText("");
                        wrongPCode.setText("Only Enter digits");
                        wrongPCode.setVisible(true);
                    }
                }
                //--------//
            }
        });

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //-----//
                wrongPCode.setVisible(false);
                String passcode = firstCode.getText()
                        +secondCode.getText()+thirdCode.getText()
                        +fourthCode.getText()+fifthCode.getText();

                if (passcode.length()!=5){
                    wrongPCode.setText("The Passcode must be 5 digits");
                    wrongPCode.setVisible(true);
                }else {
                    if (Handler.settings.getPassCode().equals(passcode)){
                        _correct[0] = true;

                        switch (function){
                            case AUTO_SAVE -> _autoSave();
                            case LOAD_ON_TOP -> _loadOnTop();
                            case SAVE_BACKUP -> _saveBackUp();
                            case LOAD_FROM_DISK -> _loadFromDisk();
                            case SAVE_TO_LOGS -> _saveToLogs();
                            case LOAD_FROM_LOGS -> _loadFromLogs();
                            case ROLL_BACK_TO_BACKUP -> _rollBackToBackUp();
                        }

                        stage_pCode.close();
                    }else {
                        firstCode.setText("");
                        secondCode.setText("");
                        thirdCode.setText("");
                        fourthCode.setText("");
                        fifthCode.setText("");
                        firstCode.requestFocus();
                        wrongPCode.setText("wrong passCode, Pliz enter the right passcode");
                        wrongPCode.setVisible(true);
                    }
                }
                //-----//
            }
        });

        stage_pCode.setScene(scene_pCode);
        stage_pCode.initModality(Modality.APPLICATION_MODAL);
        stage_pCode.setOpacity(0.9);
        stage_pCode.setResizable(false);
        stage_pCode.show();
        return _correct[0];
    }

    public void _loadFromDisk(){
        All.setSelected(true);
        Series.setSelected(false);
        Movies.setSelected(false);
        Music.setSelected(false);

        list.clear();
        drivesList.clear();


        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to choose through");
        File fl = directoryChooser.showDialog(HelloApplication.stg);

        Dialog<String> d = new Dialog<>();
        d.setTitle("Loading Movies .....");
        d.setContentText("Waiting loading process .....");
        d.setHeight(400);
        d.initModality(Modality.APPLICATION_MODAL);


        if (fl != null) {
            d.show();
            Handler.Reloading(fl, false);
            d.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            d.close();
        }


        Set<String> keySet = Handler.AllMovies.keySet();
        for (String key : keySet) {
            if (!drivesList.contains(key))
                drivesList.add(key);

            System.out.println(key);
            for (Movie mv : Handler.AllMovies.get(key)) {
                if (!list.contains(Handler.getMovieDetails(mv)))
                    list.add(Handler.getMovieDetails(mv));
            }
        }


        //to save the search to the dlogs file
        try {
            if (Handler.settings.isAutoSave()){

                Handler.saveSearchLogs(Handler.AllMovies,true);
                Handler.saveSearchLogs(Handler.AllMusic,false);
            }
        } catch (Exception e) {
            System.out.println("@Drillox Exception Running ..");
        }
    }

    public void _loadOnTop(){
        All.setSelected(true);
        Series.setSelected(false);
        Movies.setSelected(false);
        Music.setSelected(false);

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to choose through");
        File fl = directoryChooser.showDialog(HelloApplication.stg);

        Dialog<String> d = new Dialog<>();
        d.setTitle("Loading Movies .....");
        d.setContentText("Waiting loading process .....");
        d.setHeight(400);
        d.initModality(Modality.APPLICATION_MODAL);

        if (fl != null) {
            d.show();
            Handler.Reloading(fl, true);
            d.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            d.close();
        }

        list.clear();
        Set<String> keySet = Handler.AllMovies.keySet();
        for (String key : keySet) {
            if (!drivesList.contains(key))
                drivesList.add(key);

            System.out.println(key);
            for (Movie mv : Handler.AllMovies.get(key)) {
                if (!list.contains(Handler.getMovieDetails(mv)))
                    list.add(Handler.getMovieDetails(mv));
            }
        }

        //to add the search logs to the dlogs.dat file
        try {
            if (Handler.settings.isAutoSave()){

                Handler.saveSearchLogs(Handler.AllMovies,true);
                Handler.saveSearchLogs(Handler.AllMusic,false);
            }
        } catch (Exception e) {
            System.out.println("@Drillox Exception Running ..");
        }
    }

    public void _loadFromLogs(){
        All.setSelected(true);
        Series.setSelected(false);
        Movies.setSelected(false);
        Music.setSelected(false);

        list.clear();
        drivesList.clear();

        try {
            Handler.loadfromDLogs();
            Set<String> keySet = Handler.AllMovies.keySet();
            for (String key : keySet) {
                if (!drivesList.contains(key))
                    drivesList.add(key);

                //to check for the existance of the root directory
                File _chDir = new File(key);

                if (_chDir.exists()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            if (_chDir.getParentFile().exists() &&
//                                    _chDir.getParentFile()!=null)
//                                Handler.Reloading(_chDir.getParentFile(),true);
//                            else
                                Handler.Reloading(_chDir,true);

                            _serachInBackground();
                        }
                    }).start();
                }

                System.out.println(key);
                for (Movie mv : Handler.AllMovies.get(key)) {
                    if (!list.contains(Handler.getMovieDetails(mv))) {
                        if (mv.getmDir().exists())
                            list.add(Handler.getMovieDetails(mv));
                    }
                }
            }

            //----loading music
            Set<String> keySet2 = Handler.AllMusic.keySet();
            for (String key : keySet2) {
                if (!drivesList.contains(key))
                    drivesList.add(key);
            }

        } catch (Exception e) {
            System.out.println("@Drillox Exception looding from _logs:: ");
            e.printStackTrace();
        }
    }

    public void _autoSave(){
        if (Handler.settings.isAutoSave()){
            Handler.settings.setAutoSave(false);
            autoSaveId.setText("Turn autoSave on");
        }
        else {
            Handler.settings.setAutoSave(true);
            autoSaveId.setText("Turn autoSave off");
        }
    }

    public void _saveBackUp(){
        try {
            Handler.saveSearchLogsToBackUp(Handler.AllMovies,true);
            Handler.saveSearchLogsToBackUp(Handler.AllMusic,false);
            saveToBackUp.setText("Save to BackUp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void _saveToLogs(){
        try {
            Handler.saveSearchLogs(Handler.AllMovies,true);
            Handler.saveSearchLogs(Handler.AllMusic,false);
            saveToLogs.setText("Save to logs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void _rollBackToBackUp(){
        All.setSelected(true);
        Series.setSelected(false);
        Movies.setSelected(false);
        Music.setSelected(false);

        list.clear();
        drivesList.clear();

        try {
            Handler.rollBackToBackUp();
            Set<String> keySet = Handler.AllMovies.keySet();
            for (String key : keySet) {
                if (!drivesList.contains(key))
                    drivesList.add(key);

                System.out.println(key);
                for (Movie mv : Handler.AllMovies.get(key)) {
                    if (!list.contains(Handler.getMovieDetails(mv)))
                        if (mv.getmDir().exists())
                            list.add(Handler.getMovieDetails(mv));
                }
            }

            //----loading music
            Set<String> keySet2 = Handler.AllMusic.keySet();
            for (String key : keySet2) {
                if (!drivesList.contains(key))
                    drivesList.add(key);
            }

        } catch (Exception e) {
            System.out.println("@Drillox Exception looding from logs:: " + e.getMessage());
        }


        if (Handler.settings.isAutoSave())
            _autoSave();
        else saveToLogs.setText("Save to logs  [*]");
    }

    public void _serachInBackground(){
        All.setSelected(true);
        Series.setSelected(false);
        Movies.setSelected(false);
        Music.setSelected(false);

        list.clear();
        drivesList.clear();


        Set<String> keySet = Handler.AllMovies.keySet();
        for (String key : keySet) {
            if (!drivesList.contains(key))
                drivesList.add(key);

            System.out.println(key);
            for (Movie mv : Handler.AllMovies.get(key)) {
                if (!list.contains(Handler.getMovieDetails(mv)))
                    list.add(Handler.getMovieDetails(mv));
            }
        }


        //to save the search to the dlogs file
        try {
            if (Handler.settings.isAutoSave()){

                Handler.saveSearchLogs(Handler.AllMovies,true);
                Handler.saveSearchLogs(Handler.AllMusic,false);
            }
        } catch (Exception e) {
            System.out.println("@Drillox Exception Running ..");
        }
    }
}