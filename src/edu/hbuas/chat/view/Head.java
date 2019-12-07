package edu.hbuas.chat.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author tengsir
 */
public class Head extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Group g= FXMLLoader.load(getClass().getResource("Head.fxml"));
            Scene s=new Scene(g,400,400,Color.LIGHTGRAY);
            primaryStage.setTitle("选择头像");
            primaryStage.setResizable(false);
            primaryStage.setScene(s);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
