package final_work.repository;

import final_work.models.ShippedGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShippedGoodRepository extends JpaRepository<ShippedGood, Long> {

    boolean existsByShipmentIdAndGoodId(long shipmentId, long goodId);

    ShippedGood findOneByShipmentIdAndGoodId(long shipmentId, long goodId);

    Iterable<ShippedGood> findAllByShipmentId(long shipmentId);
}
