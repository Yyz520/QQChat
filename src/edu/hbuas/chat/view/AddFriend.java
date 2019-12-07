package edu.hbuas.chat.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddFriend extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Group g= FXMLLoader.load(getClass().getResource("AddFriend.fxml"));
            Scene s=new Scene(g,320,260);
            primaryStage.setTitle("添加用户");
            primaryStage.setResizable(false);
            primaryStage.setScene(s);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
