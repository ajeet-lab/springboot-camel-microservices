package com.learn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
public class LogginEvent {
    @Id
    private String messageId;
    private String createdAt;
    private String updatedAt;
    private String reqBody;
    private String resBody;

    public LogginEvent(String messageId) {
        this.messageId = messageId;
    }

    public LogginEvent(String messageId, String createdAt, String updatedAt, String reqBody, String resBody) {
        this.messageId = messageId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reqBody = reqBody;
        this.resBody = resBody;
    }

    public LogginEvent() {}

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getReqBody() {
        return reqBody;
    }

    public void setReqBody(String reqBody) {
        this.reqBody = reqBody;
    }

    public String getResBody() {
        return resBody;
    }

    public void setResBody(String resBody) {
        this.resBody = resBody;
    }
}
