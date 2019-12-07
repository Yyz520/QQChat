package edu.hbuas.chat.server;

import edu.hbuas.chat.dao.UserDao;
import edu.hbuas.chat.model.Friend;
import edu.hbuas.chat.model.Message;
import edu.hbuas.chat.model.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static ServerSocket serverSocket;
    private UserDao userDao = new UserDao();
    private Map<String, ObjectOutputStream> allClients = new HashMap<>();
    private Message receivedMessage;
    private ObjectInputStream oIn;
    private ObjectOutputStream oOut;

    public static void main(String[] args) {
        new Server();
    }

    {
        try {
            userDao.updateAllUser();
            serverSocket = new ServerSocket(SocketConfig.port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "服务器启动失败");
        }
    }

    public Server() {
        try {
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println(client.getInetAddress().getHostAddress() + " 连接进来了");
                oIn = new ObjectInputStream(client.getInputStream());
                oOut = new ObjectOutputStream(client.getOutputStream());

                ReceivedThread receivedThread = new ReceivedThread(oOut,oIn);
                receivedThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ReceivedThread extends Thread {
        private ObjectInputStream oIn;
        private ObjectOutputStream oOut;

        public ReceivedThread(ObjectOutputStream oOut,ObjectInputStream oIn){
            this.oIn = oIn;
            this.oOut = oOut;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    receivedMessage = (Message) oIn.readObject();
                    switch (receivedMessage.getMessageType()) {
                        case LOGIN: {
                            Message loginResult = new Message();
                            User loginUser = userDao.login(receivedMessage.getFrom());
                            if (allClients.containsKey(receivedMessage.getFrom().getUsername())) {
                                loginResult.setFrom(null);
                                loginResult.setContentString("online");
                            } else {
                                loginResult.setFrom(loginUser);
                                loginResult.setContentString("offline");
                            }
                            if (loginUser != null) {
                                allClients.put(loginUser.getUsername(), oOut);
                            }
                            System.out.println("反馈的东西" + loginResult.toString());
                            // 消息封装好之后，通过当前线程的输出流将登录结果返回给客户端
                            oOut.writeObject(loginResult);
                            oOut.flush();
                            System.out.println("客户端连接进来");
                            break;
                        }
                        case REGISTER: {
                            User user = userDao.checkUser(receivedMessage.getFrom().getUsername());
                            Message registerResult = new Message();
                            if (user == null) {
                                boolean registerUser = userDao.register(receivedMessage.getFrom());
                                registerResult.setContentString("" + registerUser);
                            } else {
                                registerResult.setContentString("registered");
                            }
                            oOut.writeObject(registerResult);
                            oOut.flush();
                            break;
                        }
                        case ADDFRIEND: {
                            Friend friend = receivedMessage.getFriend();
                            String result = userDao.addFriend(friend);
                            User user = userDao.checkUser(friend.getFriendname());
                            Message addResult = new Message();
                            // 根据添加的这个新朋友获取到这个朋友的用户信息
                            addResult.setContentString(result);
                            // 这个来自于新增的朋友
                            addResult.setFrom(user);
                            addResult.setFriendType(receivedMessage.getFriendType());
                            oOut.writeObject(addResult);
                            oOut.flush();
                            break;
                        }
                        case SHAKE: {
                            receivedMessage.setSendTime(new Date().toLocaleString());
                            String to = receivedMessage.getTo().getUsername();
                            if(allClients.containsKey(to)){
                                ObjectOutputStream out = allClients.get(to);
                                out.writeObject(receivedMessage);
                                out.flush();
                                System.out.println("对方在线，服务器转发消息");
                            }else{
                                System.out.println("对方不在线");
                            }
                            break;
                        }
                        case TEXT: {
                            receivedMessage.setSendTime(new Date().toLocaleString());
                            String to = receivedMessage.getTo().getUsername();
                            if (allClients.containsKey(to)) {
                                ObjectOutputStream out = allClients.get(to);
                                out.writeObject(receivedMessage);
                                out.flush();
                                System.out.println("对方在线，服务器转发消息");
                            } else {
                                System.out.println("对方不在线");
                            }
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                } catch (Exception e) {
                    allClients.remove(receivedMessage.getFrom().getUsername());
                    System.out.println("当前用户已下线"+receivedMessage.getFrom().getUsername());
                    return;
                }

            }
        }
    }
}
