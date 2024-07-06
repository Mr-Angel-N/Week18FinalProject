package mechanic.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mechanic.shop.entity.Vehicle;

public interface VehicleDao extends JpaRepository<Vehicle, Long> {

}
