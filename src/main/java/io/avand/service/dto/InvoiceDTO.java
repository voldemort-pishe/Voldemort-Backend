package io.avand.service.dto;

import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.domain.enumeration.PaymentType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class InvoiceDTO extends AbstractAuditingDTO implements Serializable {

    private PaymentType paymentType;

    private ZonedDateTime paymentDate;

    private Long amount;

    private Long tax;

    private Long discount;

    private Long total;

    private String trackingCode;

    private String referenceId;

    private InvoiceStatus status;

    private Set<InvoiceItemDTO> invoiceItem = new HashSet<>();

    @NotNull
    private Long companyId;

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Set<InvoiceItemDTO> getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(Set<InvoiceItemDTO> invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
            "paymentType=" + paymentType +
            ", paymentDate=" + paymentDate +
            ", amount=" + amount +
            ", tax=" + tax +
            ", discount=" + discount +
            ", total=" + total +
            ", status=" + status +
            ", companyId=" + companyId +
            '}';
    }
}
