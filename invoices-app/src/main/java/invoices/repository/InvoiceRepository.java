package invoices.repository;

import invoices.repository.entity.Invoice;
import invoices.repository.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("select i.id from Invoice i where i.status = :status and i.creationDateTime <= :referenceDateTime ")
    List<Integer> findCompletedIdsByReferenceDateTime(@Param("status") InvoiceStatus status, @Param("referenceDateTime") Instant referenceDateTime);

    default List<Integer> findCompletedIdsForDelete() {
        return findCompletedIdsByReferenceDateTime(InvoiceStatus.COMPLETED, Instant.now().minusSeconds(60));
    }

    default List<Integer> findErrorIdsForDelete() {
        return findCompletedIdsByReferenceDateTime(InvoiceStatus.ERROR, Instant.now().minusSeconds(60));
    }

    @Modifying
    @Query("update Invoice i set i.status = invoices.repository.entity.InvoiceStatus.COMPLETED where i.id = :id")
    void complete(@Param("id") Integer id);

    @Modifying
    @Query("update Invoice i set i.status = invoices.repository.entity.InvoiceStatus.ERROR where i.id = :id")
    void error(@Param("id") Integer id);

    boolean existsByClientId(Integer id);

    boolean existsByCompanyId(Integer id);

    boolean existsByProductId(Integer id);

}
