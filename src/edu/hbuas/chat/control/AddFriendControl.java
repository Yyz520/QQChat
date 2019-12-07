package edu.hbuas.chat.control;

import edu.hbuas.chat.model.*;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class AddFriendControl implements Initializable {
    private User user;
    private ObjectInputStream oIn;
    private ObjectOutputStream oOut;
    // private Message  addResult;
    private String type;
    @FXML
    private Button addButton;
    @FXML
    private TextField inputUsername;
    @FXML
    private ChoiceBox choiceBox;
    private int a;

    // public Message getAddResult() {
    //     return addResult;
    // }

    public String getType() {
        return type;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginControl loginControl = (LoginControl) ControlCollection.controls.get(LoginControl.class);
        this.user = loginControl.getUser();
        this.oIn = loginControl.getoIn();
        this.oOut = loginControl.getoOut();
        System.out.println("当前请求添加好友的本对象是:"+user);
    }

    @FXML
    public void processAddFriend(ActionEvent event) {
        String friendName = inputUsername.getText();
        String[] types= {"家人","朋友","同学"};
        ReadOnlyIntegerProperty x = choiceBox.getSelectionModel().selectedIndexProperty();
        // System.out.println("您真正选择的是这种类型:"+types[x.getValue()]);
        // choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        //     @Override
        //     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        //        a= oldValue.intValue();
        //     }
        // });
        // 获取点击的朋友类型
        System.out.println("这是要添加的朋友的用户名:"+friendName);
        System.out.println("这是要添加的朋友的朋友类型:"+types[x.getValue()]);
        type = types[x.getValue()];
        ControlCollection.controls.put(getClass(),this);
        // 封装添加的message信息
        Message addFriendMessage = new Message();
        addFriendMessage.setFrom(user);
        Friend friend = new Friend(user.getUsername(),type,friendName);
        addFriendMessage.setFriend(friend);
        addFriendMessage.setMessageType(MessageType.ADDFRIEND);
        addFriendMessage.setFriendType(type);
        // 将消息发送给服务端
        try {
            oOut.writeObject(addFriendMessage);
            oOut.flush();
        }catch (IOException e1){
            e1.printStackTrace();
        }
    }
}
