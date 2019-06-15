package hr.pishe.service.dto;

import java.io.Serializable;
import java.util.List;

public class ProvinceDTO implements Serializable {

    private String name;
    private List<CityDTO> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityDTO> getCities() {
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "ProvinceDTO{" +
            "name='" + name + '\'' +
            ", cities=" + cities +
            '}';
    }
}
