package orders.repository;

import orders.repository.entity.OrderArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderArchiveRepository extends JpaRepository<OrderArchive, Integer> {
}
