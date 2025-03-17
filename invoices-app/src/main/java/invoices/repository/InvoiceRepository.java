package invoices.repository;

import invoices.repository.entity.Invoice;
import invoices.repository.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Modifying
    @Query("delete from Invoice i where i.status = :status and i.creationDateTime <= :referenceDateTime ")
    void deleteByStatusAndReferenceDateTime(@Param("status") InvoiceStatus status, @Param("referenceDateTime") Instant referenceDateTime);

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
