package invoices.repository;

import invoices.repository.entity.Invoice;
import invoices.repository.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Modifying
    @Query("delete from Invoice i where i.status = :status and i.creationDateTime <= :referenceDateTime ")
    void deleteByStatusAndReferenceDateTime(@Param("status") Status status, @Param("referenceDateTime") Instant referenceDateTime);

    long countByStatus(Status status);

    boolean existsByClientId(Integer id);

    boolean existsByCompanyId(Integer id);

    boolean existsByProductId(Integer id);

}
