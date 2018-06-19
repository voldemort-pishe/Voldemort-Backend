package io.avand.domain;

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

    @Column(name = "refrence_id")
    private Long refrenceId;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    private InvoiceEntity invoice;

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

    public Long getRefrenceId() {
        return refrenceId;
    }

    public PaymentTransactionEntity refrenceId(Long refrenceId) {
        this.refrenceId = refrenceId;
        return this;
    }

    public void setRefrenceId(Long refrenceId) {
        this.refrenceId = refrenceId;
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
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", refrenceId=" + getRefrenceId() +
            ", amount=" + getAmount() +
            "}";
    }
}
