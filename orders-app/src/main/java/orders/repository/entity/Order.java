package orders.repository.entity;

import commons.model.Identifiable;
import contracts.orders.OrderStatus;
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
    private OrderStatus status = OrderStatus.NEW;

    public void cancel() {
        status = OrderStatus.CANCELLED;
    }

    public void complete() {
        status = OrderStatus.COMPLETED;
    }

    public void reject() {
        status = OrderStatus.REJECTED;
    }

    public void markAsInvoiced() {
        status = OrderStatus.INVOICED;
    }

    public void markAsSentToInvoice() {
        status = OrderStatus.SENT_TO_INVOICE;
    }

    public boolean isNew() {
        return status == OrderStatus.NEW;
    }

    public boolean isCompleted() {
        return status == OrderStatus.COMPLETED;
    }

    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    public boolean isSentToInvoice() {
        return status == OrderStatus.SENT_TO_INVOICE;
    }

}
