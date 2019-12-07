package edu.hbuas.chat.model;

import java.io.Serializable;

public class Message implements Serializable{
    private User from;
    private Friend friend;
    private String friendType;

    public String getFriendType() {
        return friendType;
    }

    public void setFriendType(String friendType) {
        this.friendType = friendType;
    }

    public Message(User from, Friend friend, String friendType, User to, String sendTime, String contentString, edu.hbuas.chat.model.MessageType messageType) {
        this.from = from;
        this.friend = friend;
        this.friendType = friendType;
        this.to = to;
        this.sendTime = sendTime;
        this.contentString = contentString;
        MessageType = messageType;
    }

    public Message(User from, User to, String sendTime, String contentString, MessageType messageType) {
        super();
        this.from = from;
        this.to = to;
        this.sendTime = sendTime;
        this.contentString = contentString;
        MessageType = messageType;
    }

    public Message(User from, Friend friend, User to, String sendTime, String contentString, edu.hbuas.chat.model.MessageType messageType) {
        this.from = from;
        this.friend = friend;
        this.to = to;
        this.sendTime = sendTime;
        this.contentString = contentString;
        MessageType = messageType;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public Message() {
    }
    public User getFrom() {
        return from;
    }
    public void setFrom(User from) {
        this.from = from;
    }
    public User getTo() {
        return to;
    }
    public void setTo(User to) {
        this.to = to;
    }
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    public String getContentString() {
        return contentString;
    }
    public void setContentString(String contentString) {
        this.contentString = contentString;
    }
    public MessageType getMessageType() {
        return MessageType;
    }
    public void setMessageType(MessageType messageType) {
        MessageType = messageType;
    }
    private User to;
    private String sendTime;
    private String contentString;
    private MessageType MessageType;

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", sendTime='" + sendTime + '\'' +
                ", contentString='" + contentString + '\'' +
                ", MessageType=" + MessageType +
                '}';
    }
}
