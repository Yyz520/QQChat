package edu.hbuas.chat.view;

import javafx.scene.control.Alert;

public class ModalWindow {
    public static void showAlertDialog(String title,String text,Alert.AlertType type){
        Alert a=new Alert(type);
        a.setTitle(title);
        a.setContentText(text);
        a.showAndWait();
    }
}
