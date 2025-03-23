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
@Table(name = "T_ORDER")
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

    public void cancel() {
        status = Status.CANCELLED;
    }

    public void complete() {
        status = Status.COMPLETED;
    }

    public void reject() {
        status = Status.REJECTED;
    }

    public void markAsInvoiced() {
        status = Status.INVOICED;
    }

    public void markAsSentToInvoice() {
        status = Status.SENT_TO_INVOICE;
    }

    public boolean isNew() {
        return status == Status.NEW;
    }

    public boolean isCompleted() {
        return status == Status.COMPLETED;
    }

    public boolean isCancelled() {
        return status == Status.CANCELLED;
    }

    public boolean isSentToInvoice() {
        return status == Status.SENT_TO_INVOICE;
    }

}
