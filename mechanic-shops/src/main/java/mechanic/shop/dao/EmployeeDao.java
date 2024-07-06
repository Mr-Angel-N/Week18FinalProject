package mechanic.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mechanic.shop.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
