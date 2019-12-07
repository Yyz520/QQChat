package edu.hbuas.chat.view;

import edu.hbuas.chat.model.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Message loginMessage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent g= FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene s=new Scene(g,292,590);
            primaryStage.setTitle("主界面");
            primaryStage.setX(100);
            primaryStage.setY(50);
            primaryStage.setResizable(false);
            primaryStage.setScene(s);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
