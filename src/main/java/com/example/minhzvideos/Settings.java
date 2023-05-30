package com.example.minhzvideos;

import com.example.minhzvideos.handler.Handler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable {

    public VBox Admin_options;
    public HBox passCode;
    public TextField firstCode;
    public TextField secondCode;
    public TextField thirdCode;
    public TextField fourthCode;
    public TextField fifthCode;
    public Label wrong_passCode;
    public Label access_granted;
    public Button confirm_btn;
    public CheckBox saveLogsByAdmin;
    public CheckBox backUpLogsByAdmin;
    public CheckBox loadFromDiskByAdmin;
    public CheckBox loadOnTopByAdmin;
    public CheckBox loadFromLogsByAdmin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFromLogsByAdmin.setSelected(Handler.settings.isLoadFromLogsByAdmin());
        loadFromDiskByAdmin.setSelected(Handler.settings.isLoadFromDiskByAdmin());
        loadOnTopByAdmin.setSelected(Handler.settings.isLoadOnTopByAdmin());
        saveLogsByAdmin.setSelected(Handler.settings.isSaveToLogsByAdmin());
        backUpLogsByAdmin.setSelected(Handler.settings.isBackUplogByAdmin());

    }

    public void confirmCredentials(ActionEvent actionEvent) {
        wrong_passCode.setVisible(false);
        String passcode = firstCode.getText()
                +secondCode.getText()+thirdCode.getText()
                +fourthCode.getText()+fifthCode.getText();

        if (passcode.length()!=5){
            wrong_passCode.setText("The Passcode must be 5 digits");
            wrong_passCode.setVisible(true);
        }else {
            if (Handler.settings.getPassCode().equals(passcode)){
                passCode.setVisible(false);
                wrong_passCode.setVisible(false);
                access_granted.setVisible(true);
                confirm_btn.setVisible(false);
                Admin_options.setVisible(true);
            }else {
                firstCode.setText("");
                secondCode.setText("");
                thirdCode.setText("");
                fourthCode.setText("");
                fifthCode.setText("");
                firstCode.requestFocus();
                wrong_passCode.setText("wrong passCode, Pliz enter the right passcode");
                wrong_passCode.setVisible(true);
            }
        }
    }

    public void firstKeyTyped(KeyEvent keyEvent) {
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
                    wrong_passCode.setVisible(false);
                }else {
                    firstCode.setText(String.valueOf(ch));
                    secondCode.requestFocus();
                    wrong_passCode.setVisible(false);
                }
            }else {
                firstCode.setText("");
                wrong_passCode.setText("Only Enter digits");
                wrong_passCode.setVisible(true);
            }
        }
        System.out.println((int)ch);
    }

    public void secondKeyTyped(KeyEvent keyEvent) {
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
                    wrong_passCode.setVisible(false);
                }else {
                    secondCode.setText(String.valueOf(ch));
                    thirdCode.requestFocus();
                    wrong_passCode.setVisible(false);
                }
            }else {
                secondCode.setText("");
                wrong_passCode.setText("Only Enter digits");
                wrong_passCode.setVisible(true);
            }
        }
    }

    public void thirdKeyTyped(KeyEvent keyEvent) {
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
                    wrong_passCode.setVisible(false);
                }else {
                    thirdCode.setText(String.valueOf(ch));
                    fourthCode.requestFocus();
                    wrong_passCode.setVisible(false);
                }
            }else {
                thirdCode.setText("");
                wrong_passCode.setText("Only Enter digits");
                wrong_passCode.setVisible(true);
            }
        }
    }

    public void fourthKeyTyped(KeyEvent keyEvent) {
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
                    wrong_passCode.setVisible(false);
                }else {
                    fourthCode.setText(String.valueOf(ch));
                    fifthCode.requestFocus();
                    wrong_passCode.setVisible(false);
                }
            }else {
                fourthCode.setText("");
                wrong_passCode.setText("Only Enter digits");
                wrong_passCode.setVisible(true);
            }
        }
    }

    public void fifthKeyTyped(KeyEvent keyEvent) {
        char ch = 0;
        if (fifthCode.getText().length()>0){
            ch = fifthCode.getText().charAt(0);

            int start = 47, end = 58;
            int _ch = (int)ch;
            int _lgth = fifthCode.getText().length();

            if (_ch>start && _ch<end){
                if (_lgth>1){
                    fifthCode.setText(String.valueOf(ch));
                    wrong_passCode.setVisible(false);
                }else {
                    fifthCode.setText(String.valueOf(ch));
                    wrong_passCode.setVisible(false);
                }
            }else {
                fifthCode.setText("");
                wrong_passCode.setText("Only Enter digits");
                wrong_passCode.setVisible(true);
            }
        }
    }

    public void saveToLogsByAdmin(ActionEvent actionEvent) {
        Handler.settings.setSaveToLogsByAdmin(saveLogsByAdmin.isSelected());
    }

    public void backUpLogsByAdmin(ActionEvent actionEvent) {
        Handler.settings.setBackUplogByAdmin(backUpLogsByAdmin.isSelected());
    }

    public void loadFromDiskByAdmin(ActionEvent actionEvent) {
        Handler.settings.setLoadFromDiskByAdmin(loadFromDiskByAdmin.isSelected());
    }

    public void loadOnTopByAdmin(ActionEvent actionEvent) {
        Handler.settings.setLoadOnTopByAdmin(loadOnTopByAdmin.isSelected());
    }

    public void loadFromLogsByAdmin(ActionEvent actionEvent) {
        Handler.settings.setLoadFromLogsByAdmin(loadFromLogsByAdmin.isSelected());
    }
}
