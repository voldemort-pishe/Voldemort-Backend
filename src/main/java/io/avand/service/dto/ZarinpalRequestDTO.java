package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ZarinpalRequestDTO implements Serializable {

    @JsonProperty(value = "MerchantID")
    private String merchantId;

    @JsonProperty(value = "Amount")
    private Long amount;

    @JsonProperty(value = "CallbackURL")
    private String callbackURL;

    @JsonProperty(value = "Description")
    private String description;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ZarinpalRequestDTO{" +
            "merchantId='" + merchantId + '\'' +
            ", amount=" + amount +
            ", callbackURL='" + callbackURL + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
