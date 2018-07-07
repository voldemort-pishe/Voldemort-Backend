package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ZarinpalVerifyRequestDTO implements Serializable {

    @JsonProperty(value = "Authority")
    private String authority;

    @JsonProperty(value = "Amount")
    private Long amount;

    @JsonProperty(value = "MerchantID")
    private String merchantId;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "ZarinpalVerifyRequestDTO{" +
            "authority='" + authority + '\'' +
            ", amount=" + amount +
            ", merchantId='" + merchantId + '\'' +
            '}';
    }
}
