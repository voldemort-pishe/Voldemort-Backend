package hr.pishe.service.dto;

import java.io.Serializable;
import java.util.List;

public class GoogleSearchDTO implements Serializable {
    private List<GoogleSearchItemDTO> items;

    public List<GoogleSearchItemDTO> getItems() {
        return items;
    }

    public void setItems(List<GoogleSearchItemDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "GoogleSearchDTO{" +
            "items=" + items +
            '}';
    }
}
