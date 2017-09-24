package com.louanimashaun.fattyzgrill.model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public class Notification extends RealmObject {

    private String id;

    private String topic;

    private String message;

    private String category;

    private Date createdAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
