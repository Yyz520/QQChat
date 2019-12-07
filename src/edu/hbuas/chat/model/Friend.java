package edu.hbuas.chat.model;


import java.io.Serializable;

public class Friend implements Serializable {

  private String username;
  private String friendType;
  private String friendname;

  public Friend(String username, String friendType, String friendname) {
    this.username = username;
    this.friendType = friendType;
    this.friendname = friendname;
  }

    public Friend() {
    }

    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getFriendType() {
    return friendType;
  }

  public void setFriendType(String friendType) {
    this.friendType = friendType;
  }


  public String getFriendname() {
    return friendname;
  }

  public void setFriendname(String friendname) {
    this.friendname = friendname;
  }

}
