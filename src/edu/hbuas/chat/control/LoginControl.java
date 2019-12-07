package edu.hbuas.chat.control;

import edu.hbuas.chat.model.ControlCollection;
import edu.hbuas.chat.model.Message;
import edu.hbuas.chat.model.MessageType;
import edu.hbuas.chat.model.User;
import edu.hbuas.chat.server.SocketConfig;
import edu.hbuas.chat.view.Main;
import edu.hbuas.chat.view.Register;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author tengsir
 */
public class LoginControl implements Initializable {
    private Socket  client;
    private ObjectInputStream oIn;
    private ObjectOutputStream oOut;
    private User user;

    public User getUser() {
        return user;
    }

    public ObjectInputStream getoIn() {
        return oIn;
    }

    public void setoIn(ObjectInputStream oIn) {
        this.oIn = oIn;
    }

    public ObjectOutputStream getoOut() {
        return oOut;
    }

    public void setoOut(ObjectOutputStream oOut) {
        this.oOut = oOut;
    }

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField  usernameText;
    @FXML
    private PasswordField passwordText;

    @FXML
    private void processLogin(ActionEvent e){
        // 1.获取表单上的用户名和密码
        String username = usernameText.getText().trim();
        String password = passwordText.getText().trim();
        // 2.表单验证
        // 3.封装登录的请求消息
        user=new User(username,password);
        Message loginMessage = new Message();
        loginMessage.setMessageType(MessageType.LOGIN);
        loginMessage.setFrom(user);
        System.out.println("登录表单获取到的信息封装称的message "+loginMessage.getFrom());
        // 4.使用socket将登录信息发送给服务器
        try {
            oOut.writeObject(loginMessage);
            oOut.flush();
        }catch (IOException e1){
            e1.printStackTrace();
        }

        // 5.读取服务器返回的登录结果
        try {
            Message loginResult = (Message)oIn.readObject();
            System.out.println("服务器返回的登录结果"+loginResult.getFrom());
            System.out.println("服务器返回的登录用户的当前状态:"+loginResult.getContentString());
            if(loginResult.getContentString().equals("online")){
                JOptionPane.showMessageDialog(null,"登录失败，该用户已在线",
                        "温馨提示",JOptionPane.ERROR_MESSAGE);
            }else if (loginResult.getFrom()!=null){
                user = loginResult.getFrom();
                ControlCollection.controls.put(getClass(),this);
                Main m=new Main();
                m.start(new Stage());
                loginButton.getScene().getWindow().hide();
            } else {
                JOptionPane.showMessageDialog(null,"登录失败，请检查用户名和密码是否输入正确",
                        "温馨提示",JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }


    @FXML
    private void processRegister(ActionEvent e){
        Register r=new Register();
        r.start(new Stage());
        registerButton.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * 控制器默认的初始化方法，用来执行一些组件额外的初始化业务，这个方法会在ui组件渲染打开前执行。
         */
        try {
            client = new Socket(SocketConfig.serverIP, SocketConfig.port);
            System.out.println("连接服务器成功");
            oOut=new ObjectOutputStream(client.getOutputStream());
            oIn=new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("无法连接服务器");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错误提示");
            alert.setContentText("无法连接服务器，请检查网络");
            alert.show();
        }

    }
}
