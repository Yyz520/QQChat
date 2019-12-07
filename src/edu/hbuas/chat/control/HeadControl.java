package edu.hbuas.chat.control;

import edu.hbuas.chat.model.ControlCollection;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author tengsir
 */
public class HeadControl implements Initializable {
    @FXML
    private GridPane girdpane;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private ImageView image9;

    private String imageUrl;

    private Image image;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        put();
        image1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image1.getImage();
                imageUrl= String.valueOf(image1.getUserData());
                image1.getScene().getWindow().hide();
                System.out.println(imageUrl);
            }
        });
        image2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image2.getImage();
                imageUrl= String.valueOf(image2.getUserData());
                image2.getScene().getWindow().hide();
                System.out.println(imageUrl);
            }
        });
        image3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image3.getImage();
                imageUrl= String.valueOf(image3.getUserData());
                image3.getScene().getWindow().hide();
                System.out.println(imageUrl);
            }
        });
        image4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image4.getImage();
                imageUrl= String.valueOf(image4.getUserData());
                image4.getScene().getWindow().hide();
                put();
                System.out.println(imageUrl);
            }
        });
        image5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image5.getImage();
                imageUrl= String.valueOf(image5.getUserData());
                image5.getScene().getWindow().hide();
                System.out.println(imageUrl);
            }
        });
        image6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image6.getImage();
                imageUrl= String.valueOf(image6.getUserData());
                image6.getScene().getWindow().hide();
                System.out.println(imageUrl);
            }
        });
        image7.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image7.getImage();
                imageUrl= String.valueOf(image7.getUserData());
                image7.getScene().getWindow().hide();
                System.out.println(imageUrl);
            }
        });
        image8.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image8.getImage();
                imageUrl= String.valueOf(image8.getUserData());
                image8.getScene().getWindow().hide();
                System.out.println(imageUrl);
            }
        });
        image9.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image = image9.getImage();
                imageUrl= String.valueOf(image9.getUserData());
                image9.getScene().getWindow().hide();
                System.out.println(imageUrl);
            }
        });
    }

    public void put(){
        ControlCollection.controls.put(getClass(),this);
    }
}
