package invoices.repository;

import invoices.repository.entity.Invoice;
import invoices.repository.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findByStatus(InvoiceStatus status);

    default List<Invoice> findAllCompleted(){
        return findByStatus(InvoiceStatus.COMPLETED);
    }

}
