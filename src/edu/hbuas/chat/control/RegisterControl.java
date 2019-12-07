package edu.hbuas.chat.control;

import edu.hbuas.chat.model.ControlCollection;
import edu.hbuas.chat.model.Message;
import edu.hbuas.chat.model.MessageType;
import edu.hbuas.chat.model.User;
import edu.hbuas.chat.server.SocketConfig;
import edu.hbuas.chat.view.Head;
import edu.hbuas.chat.view.Login;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterControl implements Initializable {
    private Socket client;
    private ObjectInputStream oIn;
    private ObjectOutputStream oOut;
    @FXML
    private Button submitRegister, selectImage;
    @FXML
    private Button backLogin;
    @FXML
    private TextField userNameTextField, nickNameTextField;
    @FXML
    private TextArea signatureTextArea;
    @FXML
    private RadioButton maleRadio, femaleRadio;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ImageView MyImage;
    private Image image;
    private String url;
    @FXML
    public void Register(ActionEvent event) {
        HeadControl headControl = (HeadControl) ControlCollection.controls.get(HeadControl.class);
        this.url = headControl.getImageUrl();
        this.image = headControl.getImage();
        System.out.println("获取到的点击的图片的路径");
        // 获取表单输入的数据
        String username = userNameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String nickname = nickNameTextField.getText().trim();
        String signature = signatureTextArea.getText().trim();
        String inputSex = "";
        if(maleRadio.isSelected()){
            inputSex = "m";
        }else if(femaleRadio.isSelected()){
            inputSex = "f";
        }
        System.out.println("你选择的性别"+inputSex);
        User user = new User(username,password,nickname,inputSex,signature,url);
        Message registerMessage = new Message();
        registerMessage.setFrom(user);
        registerMessage.setMessageType(MessageType.REGISTER);
        System.out.println("注册表单获取到的用户信息是："+user);
        System.out.println("注册封装的消息："+registerMessage.toString());
        // 使用socket将登录的信息发送给服务器
        try {
            oOut.writeObject(registerMessage);
            oOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 接收来自服务端的反馈
        try {
            Message registerResult = (Message)oIn.readObject();
            System.out.println("服务端反馈的注册请求结果是"+registerResult.toString());
            switch (registerResult.getContentString()) {
                case "registered":
                    JOptionPane.showMessageDialog(null, "该用户已被注册，请更换用户名",
                            "温馨提示", JOptionPane.ERROR_MESSAGE);
                    userNameTextField.setText("");
                    passwordTextField.setText("");
                    nickNameTextField.setText("");
                    signatureTextArea.setText("");
                    break;
                case "true":
                    System.out.println("注册成功");
                    Login login = new Login();
                    login.start(new Stage());
                    submitRegister.getScene().getWindow().hide();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "注册失败",
                            "温馨提示", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            client = new Socket(SocketConfig.serverIP, SocketConfig.port);
            System.out.println("连接服务器成功");
            oOut=new ObjectOutputStream(client.getOutputStream());
            oIn=new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("无法连接服务器");
        }
        backLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Login l = new Login();
                l.start(new Stage());
                backLogin.getScene().getWindow().hide();
            }
        });
        selectImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                Head h = new Head();
                h.start(stage);
                stage.setOnHidden(new EventHandler<WindowEvent>() {
                   @Override
                   public void handle(WindowEvent event) {
                       HeadControl headControl = (HeadControl) ControlCollection.controls.get(HeadControl.class);
                       image = headControl.getImage();
                       System.out.println("aaaa"+image);
                       MyImage.setImage(image);
                   }
               });

            }
        });
    }
}
