package edu.hbuas.chat.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Chat extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Group g= FXMLLoader.load(getClass().getResource("Chat.fxml"));
            Scene s=new Scene(g,590,398);
            primaryStage.setTitle("聊天窗口");
            primaryStage.setResizable(false);
            primaryStage.setScene(s);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
