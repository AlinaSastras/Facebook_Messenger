package com.company.a_model;

import java.io.Serializable;

public class Message implements Serializable {
    //  private static int count = 0;
    //  private int message_id=0;
    private int sender_id;
    private int receiver_id;
    private String message;
    private long serialVersionUID=1L;

    public Message(int sender_id, int receiver_id, String message) {

        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.message = message;
        // this.message_id=this.count;
        //  count++;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Message{" +
                //"message_id" + message_id +
                "sender_id=" + sender_id +
                ", message='" + message + '\'' +
                '}';
    }
}
