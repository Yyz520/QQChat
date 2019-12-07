package edu.hbuas.chat.control;

import edu.hbuas.chat.model.ControlCollection;
import edu.hbuas.chat.model.Message;
import edu.hbuas.chat.model.User;
import edu.hbuas.chat.view.AddFriend;
import edu.hbuas.chat.view.Chat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;


public class MainControl implements Initializable {
    @FXML
    private ImageView image;
    @FXML
    private Label nickname, signature;
    @FXML
    private TreeView<User> tree;
    private User user;
    private User selectFriend;
    private Message addResult;
    private String type;
    private ObjectInputStream oIn;
    private ObjectOutputStream oOut;
    private Map<String, Chat> chatMap = new HashMap<>();
    private List<User> familyList = new ArrayList<>();
    private List<User> friendsList = new ArrayList<>();
    private List<User> classmateList = new ArrayList<>();
    private TreeItem family;
    private TreeItem friend;
    private TreeItem classmate;

    private TreeItem fam;
    private TreeItem fri;
    private TreeItem cls;

    public User getUser() {
        return user;
    }

    public User getSelectFriend() {
        return selectFriend;
    }

    public ObjectInputStream getoIn() {
        return oIn;
    }

    public ObjectOutputStream getoOut() {
        return oOut;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 获取LoginControl
        LoginControl loginControl = (LoginControl) ControlCollection.controls.get(LoginControl.class);
        this.user = loginControl.getUser();
        this.oIn = loginControl.getoIn();
        this.oOut = loginControl.getoOut();
        this.selectFriend = new User();
        // System.out.println("获取到的添加的用户对象是：：：：：："+addFriendControl.getAddResult());
        Image touxiang = new Image(user.getImage());
        image.setImage(touxiang);
        nickname.setText(user.getNickname());
        signature.setText(user.getSignature());
        System.out.println("获取到的user" + user);
        TreeItem root = new TreeItem("好友列表", new ImageView("images/friends.png"));
        TreeItem friendList = new TreeItem("好友列表", new ImageView("images/friends.png"));
        family = new TreeItem("家人", new ImageView("images/friends.png"));
        friend = new TreeItem("朋友", new ImageView("images/friends.png"));
        classmate = new TreeItem("同学", new ImageView("images/friends.png"));
        friendList.getChildren().add(friend);
        friendList.getChildren().add(family);
        friendList.getChildren().add(classmate);
        Map<User, String> userMap = new HashMap<>();
        userMap = user.getFriends();
        for (Map.Entry<User, String> vo : userMap.entrySet()) {
            switch (vo.getValue()) {
                case "家人":
                    familyList.add(vo.getKey());
                    break;
                case "朋友":
                    friendsList.add(vo.getKey());
                    break;
                case "同学":
                    classmateList.add(vo.getKey());
                    break;
            }
        }
        System.out.println("家人" + familyList);
        System.out.println("朋友" + friendsList);
        System.out.println("同学" + classmateList);

        // 这里是添加所有的家人
        for (int n = 0; n < familyList.size(); n++) {
            fam = new TreeItem();
            User user = new User();
            user.setNickname(familyList.get(n).getNickname());
            user.setUsername(familyList.get(n).getUsername());
            user.setSignature(familyList.get(n).getSignature());
            fam.setValue(user);
            fam.setGraphic(new ImageView(new Image(familyList.get(n).getImage(), 50, 50, false, false)));
            family.getChildren().add(fam);
        }
        // 这里是添加所有的朋友
        for (int n = 0; n < friendsList.size(); n++) {
            User user = new User();
            fri = new TreeItem();
            user.setNickname(friendsList.get(n).getNickname());
            user.setUsername(friendsList.get(n).getUsername());
            user.setSignature(friendsList.get(n).getSignature());
            fri.setValue(user);
            fri.setGraphic(new ImageView(new Image(friendsList.get(n).getImage(), 50, 50, false, false)));
            friend.getChildren().add(fri);
        }
        // 这里是添加所有的同学
        for (int n = 0; n < classmateList.size(); n++) {
            User user = new User();
            user.setNickname(classmateList.get(n).getNickname());
            user.setUsername(classmateList.get(n).getUsername());
            user.setSignature(classmateList.get(n).getSignature());
            cls = new TreeItem();
            cls.setValue(user);
            cls.setGraphic(new ImageView(new Image(classmateList.get(n).getImage(), 50, 50, false, false)));
            classmate.getChildren().add(cls);
        }
        root.getChildren().add(friendList);
        tree.setRoot(root);
        tree.setShowRoot(false);
    }

    @FXML
    public void addFriend(ActionEvent event) throws Exception {
        AddFriend addFriend = new AddFriend();
        addFriend.start(new Stage());
    }

    @FXML
    public void chooseFriend(MouseEvent event) {
        // 获取点击的好友
        if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
            // 判断点击的是否是叶子节点
            if (tree.getSelectionModel().getSelectedItem().isLeaf()) {
                // 获取当前点击的好友对象
                User u = tree.getSelectionModel().getSelectedItem().getValue();
                // 通过帐号找到朋友的完整信息
                for (User f : familyList) {
                    if (f.getUsername().equals(u.getUsername())) {
                        selectFriend = f;
                        break;
                    }
                }
                for (User f : friendsList) {
                    if (f.getUsername().equals(u.getUsername())) {
                        selectFriend = f;
                        break;
                    }
                }
                for (User f : classmateList) {
                    if (f.getUsername().equals(u.getUsername())) {
                        selectFriend = f;
                        break;
                    }
                }
                System.out.println("当前点击的用户:" + selectFriend);
                ControlCollection.controls.put(getClass(), this);
                // 聊天窗口不能重复打开
                Stage stage = new Stage();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        chatMap.remove(user.getUsername());  //窗口关闭，从集合中移除
                    }
                });
                if (!chatMap.containsKey(user.getUsername())) {
                    Chat c = new Chat();
                    c.start(stage);
                    chatMap.put(user.getUsername(), c);
                }
            }
        }
    }

    @FXML
    public void refresh(ActionEvent event) {
        // 读取服务端传来的反馈
        try {
            addResult = (Message) oIn.readObject();
            System.out.println("服务器返回的添加好友的结果：" + addResult.getContentString());
            System.out.println("服务器返回的添加的好友对象是：" + addResult.getFrom());
            switch (addResult.getContentString()) {
                case "noPeople":
                    JOptionPane.showMessageDialog(null, "您要添加的用户还尚未注册哦!",
                            "温馨提示", JOptionPane.ERROR_MESSAGE);
                    break;
                case "existed":
                    JOptionPane.showMessageDialog(null, "您已经添加过该好友!",
                            "温馨提示", JOptionPane.ERROR_MESSAGE);
                    break;
                case "success":
                    JOptionPane.showMessageDialog(null, "添加成功!",
                            "温馨提示", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("当前请求添加的好友的用户类型是:" + addResult.getFriendType());
                    System.out.println("需要挂载在节点上的用户对象信息是:" + addResult.getFrom());
                    TreeItem addNode = new TreeItem();
                    addNode.setValue(addResult.getFrom());
                    addNode.setGraphic(new ImageView(new Image(addResult.getFrom().getImage(), 50, 50, false, false)));
                    switch (addResult.getFriendType()) {
                        case "朋友":
                            friendsList.add(addResult.getFrom());
                            friend.getChildren().add(addNode);
                            break;
                        case "家人":
                            familyList.add(addResult.getFrom());
                            family.getChildren().add(addNode);
                            break;
                        default:
                            classmateList.add(addResult.getFrom());
                            classmate.getChildren().add(addNode);
                            break;
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "添加失败!",
                            "温馨提示", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chatWithAll(ActionEvent event) {
        Chat chat = new Chat();
        chat.start(new Stage());
    }
}

