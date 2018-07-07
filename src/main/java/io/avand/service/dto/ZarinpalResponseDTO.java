package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ZarinpalResponseDTO implements Serializable {

    @JsonProperty(value = "Authority")
    private String authority;

    @JsonProperty(value = "Status")
    private String status;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ZarinpalResponseDTO{" +
            "authority='" + authority + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
