package com.rcdriver.cs.models;

/**
 * Created by otacodes on 12/20/2018.
 */

public class ChatModels {
    String receiver_id, sender_id, chat_id, sender_name, text, pic_url, status, time, timestamp, type;

    public ChatModels() {

    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getPic_url() {
        return pic_url;
    }

}
