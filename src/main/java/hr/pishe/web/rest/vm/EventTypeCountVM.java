package hr.pishe.web.rest.vm;

import hr.pishe.service.dto.EventTypeCountDTO;

import java.io.Serializable;
import java.util.List;

public class EventTypeCountVM implements Serializable {
    private List<EventTypeCountDTO> items;
    private Long count;

    public List<EventTypeCountDTO> getItems() {
        return items;
    }

    public void setItems(List<EventTypeCountDTO> items) {
        this.items = items;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "EventTypeCountVM{" +
            "items=" + items +
            ", count=" + count +
            '}';
    }
}
