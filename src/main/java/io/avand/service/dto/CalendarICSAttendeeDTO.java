package io.avand.service.dto;

import io.avand.domain.enumeration.AttendeeRoleType;

import java.io.Serializable;

public class CalendarICSAttendeeDTO implements Serializable {
    private String name;
    private String email;
    private AttendeeRoleType role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AttendeeRoleType getRole() {
        return role;
    }

    public void setRole(AttendeeRoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CalendarICSAttendeeDTO{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
