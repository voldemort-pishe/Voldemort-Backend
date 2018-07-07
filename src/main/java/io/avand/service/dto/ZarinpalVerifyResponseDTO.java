package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ZarinpalVerifyResponseDTO implements Serializable {

    @JsonProperty(value = "Status")
    private Integer status;

    @JsonProperty(value = "RefID")
    private String refId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    @Override
    public String toString() {
        return "ZarinpalVerifyResponseDTO{" +
            "status=" + status +
            ", refId='" + refId + '\'' +
            '}';
    }
}
