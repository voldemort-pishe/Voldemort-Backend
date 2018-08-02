package io.avand.web.rest.vm;

import io.avand.domain.enumeration.JobType;

import java.io.Serializable;

public class CareerPageJobVM implements Serializable {

    private String name;
    private JobType type;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CareerPageJobVM{" +
            "name='" + name + '\'' +
            ", type=" + type +
            ", location='" + location + '\'' +
            '}';
    }
}
