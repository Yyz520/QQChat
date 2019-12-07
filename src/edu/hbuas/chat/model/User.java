package edu.hbuas.chat.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String username;
    private String password;
    private String nickname;
    private String sex;
    private int age;
    private String signature;
    private String image;
    private String friendType;
    private int status;
    private Map<User,String> friends = new HashMap<>();

    public User(String username, String password, String nickname, String sex, String signature, String image) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.sex = sex;
        this.signature = signature;
        this.image = image;
    }

    public User(String username, String password, String nickname, String sex, int age, String signature, String image, String friendType, int status, Map<User, String> friends) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.sex = sex;
        this.age = age;
        this.signature = signature;
        this.image = image;
        this.friendType = friendType;
        this.status = status;
        this.friends = friends;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getFriendType() {
        return friendType;
    }

    public void setFriendType(String friendType) {
        this.friendType = friendType;
    }

    public Map<User,String> getFriends() {
        return friends;
    }

    public void setFriends(Map<User,String> friends) {
        this.friends = friends;
    }

    public User(String username, String password, String nickname, String sex, int age, String signature,
                String image) {
        super();
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.sex = sex;
        this.age = age;
        this.signature = signature;
        this.image = image;
    }
    public User() {
        super();
    }
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    // @Override
    // public String toString() {
    //     return "User{" +
    //             "username='" + username + '\'' +
    //             ", password='" + password + '\'' +
    //             ", nickname='" + nickname + '\'' +
    //             ", sex='" + sex + '\'' +
    //             ", age=" + age +
    //             ", signature='" + signature + '\'' +
    //             ", image='" + image + '\'' +
    //             ", friends=" + friends +
    //             '}';
    // }

    @Override
    public String toString() {
        return nickname+"  "+username+"\n"+signature;
    }
}
