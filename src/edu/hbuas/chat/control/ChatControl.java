package edu.hbuas.chat.control;


import edu.hbuas.chat.model.ControlCollection;
import edu.hbuas.chat.model.Message;
import edu.hbuas.chat.model.MessageType;
import edu.hbuas.chat.model.User;
import edu.hbuas.chat.view.Chat;
import edu.hbuas.chat.view.ModalWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChatControl implements Initializable {
    private User user;
    private User selectFriend;
    private static ObjectInputStream oIn;
    private static ObjectOutputStream oOut;
    private Map<String, Chat> chatMap = new HashMap<>();
    @FXML
    private TextArea editText, messages;
    @FXML
    private Button sendMessageButton, clearText, shakeButton;
    @FXML
    private Accordion panels;
    @FXML
    private Label friendName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainControl mainControl = (MainControl) ControlCollection.controls.get(MainControl.class);
        this.selectFriend = mainControl.getSelectFriend();
        this.user = mainControl.getUser();
        this.oIn = mainControl.getoIn();
        this.oOut = mainControl.getoOut();
        friendName.setText("Chatting with " + selectFriend.getNickname());

        ReceiveThread receive = new ReceiveThread();
        receive.start();
    }

    @FXML
    public void SendMessage(ActionEvent event) {
        String text = editText.getText();
        if (text.length() == 0) {
            ModalWindow.showAlertDialog("温馨提示", "不能发送空消息！", Alert.AlertType.INFORMATION);
        } else {
            messages.appendText(user.getNickname() + "   " + new Date().toLocaleString() + "\n" + text + "\r\n\r\n");
            editText.setText("");
            Message chatMessage = new Message();
            chatMessage.setFrom(user);
            chatMessage.setTo(selectFriend);
            chatMessage.setMessageType(MessageType.TEXT);
            chatMessage.setContentString(text);
            // 底层的socket将消息对象写入网络的另一端
            try {
                oOut.writeObject(chatMessage);
                oOut.flush();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("消息发送失败，请检查网络连接！");
                alert.show();
            }
        }
    }

    @FXML
    public void ClearText(ActionEvent event) {
        editText.setText("");
    }

    @FXML
    public void ShakeWindow(ActionEvent event) {
        String content = user.getNickname() + "   " + new Date().toLocaleString() + "\n" + "你发送了一个窗口抖动"+"\n\n";
        messages.appendText(content);
        Message shakeMessage = new Message();
        shakeMessage.setFrom(user);
        shakeMessage.setTo(selectFriend);
        shakeMessage.setMessageType(MessageType.SHAKE);
        shakeMessage.setContentString("给你发送了一个窗口抖动\n");
        shakeWindow();
        try {
            oOut.writeObject(shakeMessage);
            oOut.flush();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("抖动发送失败，请检查网络连接！");
            alert.show();
        }
    }

    private void shakeWindow() {
        new Thread() {
            @Override
            public void run() {
                double startX = messages.getScene().getWindow().getX();
                double startY = messages.getScene().getWindow().getY();
                int fudu = 2;
                int delay = 30;
                for (int n = 0; n < 8; n++) {
                    messages.getScene().getWindow().setX(startX - fudu);
                    messages.getScene().getWindow().setY(startY);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    messages.getScene().getWindow().setX(startX + fudu);
                    messages.getScene().getWindow().setY(startY);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    messages.getScene().getWindow().setX(startX);
                    messages.getScene().getWindow().setY(startY - fudu);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    messages.getScene().getWindow().setX(startX);
                    messages.getScene().getWindow().setY(startY + fudu);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Message getMessage = (Message) oIn.readObject();
                    String username = getMessage.getFrom().getUsername();
                    System.out.println("服务器转发的消息--" + getMessage.getFrom().getNickname() + "  发给我的消息:" + getMessage.getContentString());
                    switch (getMessage.getMessageType()){
                        case TEXT:{
                            //将我发送的消息写到接受对象的聊天框中
                            String content = getMessage.getFrom().getNickname()+"   "+getMessage.getSendTime()+"\n"+getMessage.getContentString()+"\r\n\n";
                            messages.appendText(content);
                            break;
                        }
                        case SHAKE:{
                            String content = getMessage.getFrom().getNickname()+"   "+getMessage.getSendTime()+"\n"+getMessage.getContentString()+"\r\n\n";
                            messages.appendText(content);
                            System.out.println("抖动消息");
                            shakeWindow();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
