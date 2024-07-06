package mechanic.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mechanic.shop.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {
  
}
