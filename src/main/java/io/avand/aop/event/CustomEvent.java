package io.avand.aop.event;

import io.avand.domain.enumeration.EventType;
import org.springframework.context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {

    private String title;
    private String description;
    private EventType type;
    private String extra;
    private Long owner;

    public CustomEvent(Object source) {
        super(source);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "CustomEvent{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", type=" + type +
            ", extra='" + extra + '\'' +
            ", owner=" + owner +
            '}';
    }
}
