package invoices.repository;

import invoices.repository.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("select i.id from Invoice i where i.status = invoices.repository.entity.InvoiceStatus.COMPLETED")
    List<Integer> findCompletedIds();

    @Modifying
    @Query("update Invoice i set i.status = invoices.repository.entity.InvoiceStatus.COMPLETED where i.id = :id")
    void complete(@Param("id") Integer id);

    boolean existsById(Integer id);

    boolean existsByClientId(Integer id);

    boolean existsByCompanyId(Integer id);

}
