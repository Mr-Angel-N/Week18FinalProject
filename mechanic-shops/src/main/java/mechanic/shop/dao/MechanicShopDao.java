package mechanic.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mechanic.shop.entity.MechanicShop;

public interface MechanicShopDao extends JpaRepository<MechanicShop, Long> {

}
