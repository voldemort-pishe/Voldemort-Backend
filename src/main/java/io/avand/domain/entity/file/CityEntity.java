package io.avand.domain.entity.file;

import java.io.Serializable;

public class CityEntity implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityEntity{" +
            "name='" + name + '\'' +
            '}';
    }
}
