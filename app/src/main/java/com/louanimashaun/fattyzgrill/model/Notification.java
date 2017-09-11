package com.louanimashaun.fattyzgrill.model;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public class Notification {

    private String topic;

    private String message;

    private String category;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
