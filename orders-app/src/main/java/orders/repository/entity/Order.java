package orders.repository.entity;

import commons.model.Identifiable;
import contracts.orders.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "ORDERS")
public class Order extends Identifiable {

    @NotNull
    @Column(nullable = false)
    private Integer clientId;

    @NotNull
    @Column(nullable = false)
    private Integer productId;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Enumerated
    private Status status = Status.NEW;

    @Column(length = 2000)
    private String rejectReason;

    public void cancel() {
        status = Status.CANCELLED;
    }

    public void complete() {
        status = Status.COMPLETED;
    }

    public void reject(String reason) {
        status = Status.REJECTED;
        rejectReason = reason;
    }

    public void markAsInvoiced() {
        status = Status.INVOICED;
    }

    public void markAsInvoicing() {
        status = Status.INVOICING;
    }

    public boolean isNew() {
        return status == Status.NEW;
    }

    public boolean isCompleted() {
        return status == Status.COMPLETED;
    }

    public boolean isInvoicing() {
        return status == Status.INVOICING;
    }

    public boolean isCancelled() {
        return status == Status.CANCELLED;
    }

    public boolean isRejected() {
        return status == Status.REJECTED;
    }

    public boolean isInvoiced() {
        return status == Status.INVOICED;
    }
}
