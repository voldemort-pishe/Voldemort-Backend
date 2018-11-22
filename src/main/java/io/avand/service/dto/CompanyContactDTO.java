package io.avand.service.dto;

import java.io.Serializable;

public class CompanyContactDTO extends AbstractAuditingDTO implements Serializable {
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CompanyContactDTO{" +
            "address='" + address + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", phone='" + phone + '\'' +
            '}';
    }
}
