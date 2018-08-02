package io.avand.domain.entity.jpa;

import io.avand.domain.enumeration.PaymentTransactionStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PaymentTransactionEntity.
 */
@Entity
@Table(name = "payment_transaction_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentTransactionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    private InvoiceEntity invoice;

    @Column(name = "status")
    private PaymentTransactionStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public PaymentTransactionEntity userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public PaymentTransactionEntity refrenceId(Long refrenceId) {
        this.referenceId = refrenceId;
        return this;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getAmount() {
        return amount;
    }

    public PaymentTransactionEntity amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public PaymentTransactionEntity invoice(InvoiceEntity invoiceEntity) {
        this.invoice = invoiceEntity;
        return this;
    }

    public void setInvoice(InvoiceEntity invoiceEntity) {
        this.invoice = invoiceEntity;
    }

    public PaymentTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentTransactionStatus status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentTransactionEntity paymentTransactionEntity = (PaymentTransactionEntity) o;
        if (paymentTransactionEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentTransactionEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentTransactionEntity{" +
            "id=" + id +
            ", userId=" + userId +
            ", referenceId=" + referenceId +
            ", amount=" + amount +
            ", invoice=" + invoice +
            ", status=" + status +
            '}';
    }
}
