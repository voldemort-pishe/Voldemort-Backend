package io.avand.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.EventStatus;
import io.avand.domain.enumeration.EventType;

import java.io.Serializable;

public class EventFilterVM implements Serializable {
    private EventType type;
    private EventStatus status;
    private Boolean flag;
    @JsonIgnore
    private Long ownerId;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "EventFilterVM{" +
            "type=" + type +
            ", status=" + status +
            ", flag=" + flag +
            ", ownerId=" + ownerId +
            '}';
    }
}
