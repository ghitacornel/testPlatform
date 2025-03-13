package invoices.repository;

import invoices.repository.entity.Invoice;
import invoices.repository.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findByStatus(InvoiceStatus status);

    default List<Invoice> findAllCompleted() {
        return findByStatus(InvoiceStatus.COMPLETED);
    }

    @Modifying
    @Query("update Invoice i set i.status = invoices.repository.entity.InvoiceStatus.COMPLETED where i.id = :id")
    void complete(@Param("id") Integer id);
    
}
