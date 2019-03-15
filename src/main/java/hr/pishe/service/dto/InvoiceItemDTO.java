package hr.pishe.service.dto;

import java.io.Serializable;

public class InvoiceItemDTO implements Serializable {
    private Long id;
    private String title;
    private Long count;
    private Long price;
    private Long total;
    private Long invoiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String toString() {
        return "InvoiceItemDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", count=" + count +
            ", price=" + price +
            ", total=" + total +
            ", invoiceId=" + invoiceId +
            '}';
    }
}
