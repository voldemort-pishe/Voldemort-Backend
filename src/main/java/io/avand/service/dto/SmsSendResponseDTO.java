package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SmsSendResponseDTO implements Serializable {

    @JsonProperty("messageid")
    private Long messageId;
    private String message;
    private Integer status;
    @JsonProperty("statustext")
    private String statusText;
    private String sender;
    private String receptor;
    private Integer cost;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "SmsSendResponseDTO{" +
            "messageId=" + messageId +
            ", message='" + message + '\'' +
            ", status=" + status +
            ", statusText='" + statusText + '\'' +
            ", sender='" + sender + '\'' +
            ", receptor='" + receptor + '\'' +
            ", cost=" + cost +
            '}';
    }
}
