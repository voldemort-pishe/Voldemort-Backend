package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.domain.enumeration.PaymentType;
import io.avand.domain.enumeration.SubscribeState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class InvoiceDTO extends AbstractAuditingDTO implements Serializable {

    @NotNull
    private Long id;

    private SubscribeState subscribeState;

    private PaymentType paymentType;

    private ZonedDateTime paymentDate;

    private Integer amount;

    private InvoiceStatus status;

    private Set<PaymentTransactionDTO> paymentTransactions = new HashSet<>();

    @JsonIgnore
    private UserDTO user;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public SubscribeState getSubscribeState() {
        return subscribeState;
    }

    public void setSubscribeState(SubscribeState subscribeState) {
        this.subscribeState = subscribeState;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Set<PaymentTransactionDTO> getPaymentTransactions() {
        return paymentTransactions;
    }

    public void setPaymentTransactions(Set<PaymentTransactionDTO> paymentTransactions) {
        this.paymentTransactions = paymentTransactions;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
            "id=" + id +
            ", subscribeState=" + subscribeState +
            ", paymentType=" + paymentType +
            ", paymentDate=" + paymentDate +
            ", amount=" + amount +
            ", status=" + status +
            ", paymentTransactions=" + paymentTransactions +
            '}';
    }
}
