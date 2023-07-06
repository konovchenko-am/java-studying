package final_work.repository;

import final_work.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShipmentRepository extends JpaRepository<Shipment, Long>{

    boolean existsShipmentByUserLoginAndDate(String userLogin, String date);
    Shipment findOneByUserLoginAndDate(String userLogin, String date);
}