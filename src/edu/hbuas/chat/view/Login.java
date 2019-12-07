package edu.hbuas.chat.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene  s=new Scene(root,480,280);
            primaryStage.setTitle("用户登录");
            primaryStage.getIcons().add(new Image("images/icon.png", 256, 256, true, true));
            primaryStage.setScene(s);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
