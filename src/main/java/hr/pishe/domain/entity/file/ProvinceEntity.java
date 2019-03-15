package hr.pishe.domain.entity.file;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ProvinceEntity implements Serializable {

    private String name;
    @JsonProperty("Cities")
    private List<CityEntity> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityEntity> getCities() {
        return cities;
    }

    public void setCities(List<CityEntity> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "ProvinceEntity{" +
            "name='" + name + '\'' +
            ", cities=" + cities +
            '}';
    }
}
