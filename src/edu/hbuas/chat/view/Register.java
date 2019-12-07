package edu.hbuas.chat.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Register extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Group g = FXMLLoader.load(getClass().getResource("Register.fxml"));
            Scene s = new Scene(g, 610, 560);
            primaryStage.setTitle("用户注册");
            primaryStage.getIcons().add(new Image("images/icon.png", 256, 256, true, true));
            primaryStage.setResizable(false);
            primaryStage.setScene(s);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
