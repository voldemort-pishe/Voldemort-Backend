package io.avand.service.dto;

import java.io.Serializable;

public class CloudflareResponseDTO implements Serializable {
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CloudflareResponseDTO{" +
            "success=" + success +
            '}';
    }
}
