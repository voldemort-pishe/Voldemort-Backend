package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PaymentTransactionDTO implements Serializable {

    @NotNull
    private Long id;

    private Long userId;

    private Long refrenceId;

    private Integer amount;

    @NotNull
    private Long invoiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRefrenceId() {
        return refrenceId;
    }

    public void setRefrenceId(Long refrenceId) {
        this.refrenceId = refrenceId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String toString() {
        return "PaymentTransactionDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", refrenceId=" + refrenceId +
            ", amount=" + amount +
            '}';
    }
}
