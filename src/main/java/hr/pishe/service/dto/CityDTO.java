package hr.pishe.service.dto;

import java.io.Serializable;

public class CityDTO implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityDTO{" +
            "name='" + name + '\'' +
            '}';
    }
}
