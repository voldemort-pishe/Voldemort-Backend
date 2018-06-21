package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PaymentTransactionDTO implements Serializable {

    @NotNull
    private Long id;

    private Long userId;

    private Long refrenceId;

    private Integer amount;

    private InvoiceDTO invoice;

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

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "PaymentTransactionDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", refrenceId=" + refrenceId +
            ", amount=" + amount +
            ", invoice=" + invoice +
            '}';
    }
}
