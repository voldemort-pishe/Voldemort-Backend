package io.avand.service.dto;

import io.avand.domain.enumeration.EventType;

import java.io.Serializable;

public class EventTypeCountDTO implements Serializable {
    private Long count;
    private EventType type;

    public EventTypeCountDTO(Long count, EventType type) {
        this.count = count;
        this.type = type;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EventTypeCountDTO{" +
            "count=" + count +
            ", type=" + type +
            '}';
    }
}
