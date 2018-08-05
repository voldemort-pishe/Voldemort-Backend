package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.avand.domain.enumeration.SubscribeState;

import io.avand.domain.enumeration.PaymentType;

import io.avand.domain.enumeration.InvoiceStatus;

/**
 * A InvoiceEntity.
 */
@Entity
@Table(name = "invoice_entity")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class InvoiceEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription")
    private SubscribeState subscription;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "payment_date")
    private ZonedDateTime paymentDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "amount_with_tax")
    private Integer amountWithTax;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<PaymentTransactionEntity> paymentTransactions = new HashSet<>();

    @ManyToOne
    private UserEntity user;

    @Column(name = "plan_title")
    private String planTitle;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscribeState getSubscription() {
        return subscription;
    }

    public InvoiceEntity subscription(SubscribeState subscription) {
        this.subscription = subscription;
        return this;
    }

    public void setSubscription(SubscribeState subscription) {
        this.subscription = subscription;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public InvoiceEntity paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public InvoiceEntity paymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public InvoiceEntity amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountWithTax() {
        return amountWithTax;
    }

    public InvoiceEntity amountWithTax(Integer amountWithTax) {
        this.amountWithTax = amountWithTax;
        return this;
    }

    public void setAmountWithTax(Integer amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public InvoiceEntity status(InvoiceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Set<PaymentTransactionEntity> getPaymentTransactions() {
        return paymentTransactions;
    }

    public InvoiceEntity paymentTransactions(Set<PaymentTransactionEntity> paymentTransactionEntities) {
        this.paymentTransactions = paymentTransactionEntities;
        return this;
    }

    public InvoiceEntity addPaymentTransaction(PaymentTransactionEntity paymentTransactionEntity) {
        this.paymentTransactions.add(paymentTransactionEntity);
        paymentTransactionEntity.setInvoice(this);
        return this;
    }

    public InvoiceEntity removePaymentTransaction(PaymentTransactionEntity paymentTransactionEntity) {
        this.paymentTransactions.remove(paymentTransactionEntity);
        paymentTransactionEntity.setInvoice(null);
        return this;
    }

    public void setPaymentTransactions(Set<PaymentTransactionEntity> paymentTransactionEntities) {
        this.paymentTransactions = paymentTransactionEntities;
    }

    public UserEntity getUser() {
        return user;
    }

    public InvoiceEntity user(UserEntity userEntity) {
        this.user = userEntity;
        return this;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
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
        InvoiceEntity invoiceEntity = (InvoiceEntity) o;
        if (invoiceEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
            "id=" + getId() +
            ", subscription='" + getSubscription() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", amount=" + getAmount() +
            ", amountWithTax=" + getAmountWithTax() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
